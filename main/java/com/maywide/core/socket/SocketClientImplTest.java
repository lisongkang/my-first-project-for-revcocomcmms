package com.maywide.core.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.core.util.CheckUtils;
import com.thoughtworks.xstream.XStreamException;

/**
 * socket接口
 */
public class SocketClientImplTest {
    public static final int DEFAULT_MAX_LENGTH = 8 * 1024 * 1024; // 8M
    public static final int READ_TIME_OUT = 3 * 1000;

    public RemotecallLog sockectSend(String _ip, int _port,
            ServiceRequest request, String environmentEncodeing)
            throws Exception {
        if (StringUtils.isBlank(environmentEncodeing)) {
            // Environment.UAP_ENCODING
            throw new Exception("编码格式不能为空");
        }

        // 1.转换请求对象为xml串
        String req = socketReq2CompactXml(request, environmentEncodeing);

        // 2.记录RemotecallLog
        RemotecallLog retRemotecallLog = new RemotecallLog();
        retRemotecallLog.setCalltime(new Date());
        retRemotecallLog.setServicecode(request.getService());
        retRemotecallLog.setServiceurl(_ip + ":" + _port);
        retRemotecallLog.setClientcode(request.getClientid());
        retRemotecallLog.setRequest(req);
        // copySocketReqInfo2callLog(request, retRemotecallLog);

        String status = "";
        String retmsg = "";
        String responseStr = null;
        try {

            responseStr = sendXML(_ip, _port, req, environmentEncodeing);

            // 6.组装返回信息
            retRemotecallLog.setEndtime(new Date());
            retRemotecallLog.setRetcode(status);
            retRemotecallLog.setRetmsg(retmsg);
            retRemotecallLog.setResponse(responseStr);
            copySocketBaseRespInfo2callLog(responseStr, retRemotecallLog);

        } catch (XStreamException e) {
            e.printStackTrace();
            status = Environment.STATUS_ERROR_FORMAT;
            retmsg = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.print(e.getMessage());
            status = Environment.STATUS_GENERAL_EXCEPTION;
            retmsg = e.getMessage();
        } finally {

            // 6.组装返回信息
            retRemotecallLog.setEndtime(new Date());
            retRemotecallLog.setRetcode(status);
            retRemotecallLog.setRetmsg(retmsg);
            copySocketBaseRespInfo2callLog(responseStr, retRemotecallLog);
        }

        System.out.println("response===>" + responseStr);

        return retRemotecallLog;
    }

    private String socketReq2CompactXml(Object req, String encoding)
            throws Exception {
        CheckUtils.checkNull(req, "请求对象为空");

        XmlConverter converter = new XmlConverter();
        String retXml = converter.toCompactXML(req);
        // header
        retXml = converter.formatLengthHeader(retXml.getBytes(encoding).length)
                + retXml;

        System.out.println("request===>" + retXml);

        return retXml;
    }

    // 将返回串的共公信息解析并保存到日志对象
    private void copySocketBaseRespInfo2callLog(String responseStr,
            RemotecallLog retRemotecallLog) {
        // TODO Auto-generated method stub

        // --TODO

    }

    public ServiceResponse socketResp2Respobj(String resp, Object output)
            throws Exception {
        ServiceResponse retServiceResponse = new ServiceResponse();
        retServiceResponse.setOutput(output);

        XmlConverter converter = new XmlConverter();
        converter.fromXml(resp, retServiceResponse);

        return retServiceResponse;
    }

    public String sendXML(String _ip, int _port, String request,
            String environmentEncoding) throws Exception, InterruptedException {

        ClientHandler socket = null;
        try {
            if (request.getBytes().length > DEFAULT_MAX_LENGTH) {
                throw new Exception("需发送的请求超过规定的长度：8M");
            }
            socket = ConnectionPool.getInstance().getConnection(_ip, _port);

            /* ClientHandler */
            byte[] response = null;
            try {

                
                byte[] reqBytes = request.getBytes(environmentEncoding);
                response = socket.sendBytes(reqBytes);

                System.out.println("response1=====" + response);
            } catch (Exception e) {
                System.out.println("response222=====");
            } finally {
                System.out.println("response=====" + "");
            }

            System.out.println("response==="
                    + new String(response, environmentEncoding) + "==");

            byte[] header = new byte[8];
            for (int i = 0; i < 8; i++) {
                header[i] = response[i];
                response[i] = ' ';
            }
            int length = Integer.parseInt(new String(header));
            long startTime = System.currentTimeMillis();
            while (response.length != length) {
                long currTime = System.currentTimeMillis();
                if (currTime - startTime > READ_TIME_OUT) {
                    throw new Exception("读取超时：消息不完整");
                }
                byte[] bytesLeft = socket.read();
                response = appendBytes(response, bytesLeft, bytesLeft.length);

            }

            String result = new String(response, environmentEncoding).trim();
            return result;
        } catch (Exception e) {
            ConnectionPool.getInstance().handleException(socket);
            throw new Exception("Socket 请求出错：" + e.getMessage(), e);
        } finally {
            ConnectionPool.getInstance().release(socket);
        }
    }

    protected byte[] appendBytes(byte[] data, byte[] bytes, int count) {
        int len = (data == null) ? 0 : data.length;
        byte[] newBuf = new byte[len + count];
        if (len > 0) {
            System.arraycopy(data, 0, newBuf, 0, len);
        }
        System.arraycopy(bytes, 0, newBuf, len, count);
        return newBuf;
    }
}
