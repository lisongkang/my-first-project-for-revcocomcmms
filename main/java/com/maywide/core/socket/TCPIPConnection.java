package com.maywide.core.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maywide.core.util.PubUtil;

public class TCPIPConnection
  implements Connection
{
  protected Socket socket;
  protected InputStream in;
  protected OutputStream out;
  protected int socketTimeout = 60000;
  protected long receiveTimeout = 300L;
  private boolean opening = false;
  private int bufferSize = 10240;
  private String encoding = "gb2312";
  public static final String ERRDESC = "11";
  public static final String SUCCEED = "00";
  public static final String ENDFLAG = "\n";
  public static final String SEPARATOR = "|";
  protected static Log logger = LogFactory.getLog(ClientHandler.class);
  public static final long RECEIVER_TIMEOUT = 60000L;
  public static final long CONNECTION_RECEIVE_TIMEOUT = 10000L;
  public static final long COMMS_TIMEOUT = 60000L;
  public static final long QUEUE_TIMEOUT = 10000L;
  public static final long ACCEPT_TIMEOUT = 5000L;
  
  public String getEncoding()
  {
    return this.encoding;
  }
  
  public void setEncoding(String encoding)
  {
    this.encoding = encoding;
  }
  
  public int getBufferSize()
  {
    return this.bufferSize;
  }
  
  public void setBufferSize(int bufferSize)
  {
    this.bufferSize = bufferSize;
  }
  
  public void setSocket(Socket socket)
  {
    this.socket = socket;
  }
  
  public int getSocketTimeout()
  {
    return this.socketTimeout;
  }
  
  public void setSocketTimeout(int socketTimeout)
  {
    this.socketTimeout = socketTimeout;
  }
  
  public TCPIPConnection() {}
  
  public TCPIPConnection(Socket socket)
    throws IOException
  {
    this.socket = socket;
    initSocket();
  }
  
  public TCPIPConnection(String host, int port)
    throws IOException
  {
    this.socket = new Socket(host, port);
    initSocket();
  }
  
  private void initSocket()
    throws IOException
  {
    if ((this.socket == null) || (this.opening)) {
      return;
    }
    this.socket.setTcpNoDelay(true);
    this.socket.setSoTimeout(this.socketTimeout);
    


    this.in = this.socket.getInputStream();
    this.out = this.socket.getOutputStream();
    this.opening = true;
  }
  
  public void open()
    throws IOException
  {
    initSocket();
  }
  
  public void close()
    throws IOException
  {
    if (!this.opening) {
      return;
    }
    this.socket.shutdownInput();
    this.socket.shutdownOutput();
    this.in.close();
    this.out.close();
    this.socket.close();
    this.socket = null;
    this.opening = false;
    logger.debug("close...");
  }
  
  public byte[] read(String separator)
    throws IOException, InterruptedException
  {
    byte[] data = (byte[])null;
    int bytesToRead = 0;
    int bytesRead = 0;
    boolean isend = false;
    String tmpseparator = separator == null ? "|" : separator;
    
    this.socket.setSoTimeout(this.socketTimeout);
    bytesToRead = this.bufferSize;
    byte[] receiveBuffer = new byte[this.bufferSize];
    long startTime = System.currentTimeMillis();
    do
    {
      if (System.currentTimeMillis() - startTime > this.receiveTimeout) {
        isend = true;
      }
      bytesRead = 0;
      bytesRead = this.in.read(receiveBuffer, 0, bytesToRead);
      if (bytesRead > 0)
      {
        logger.info("read " + bytesRead + " bytes from socket");
        data = appendBytes(data, receiveBuffer, bytesRead);
        if (isend(new String(data), tmpseparator)) {
          isend = true;
        }
      }
      bytesToRead = this.in.available();
      if (bytesToRead > this.bufferSize) {
        bytesToRead = this.bufferSize;
      }
      if (bytesToRead == 0) {
        isend = true;
      }
    } while (!
    

















      isend);
    return data;
  }
  
  public byte[] read()
    throws IOException, InterruptedException
  {
    return read(null);
  }
  
  private boolean isend(String str, String separator)
  {
    if (PubUtil.isEmpty(str)) {
      return false;
    }
    return str.indexOf("\n") > 0;
  }
  
  private byte[] appendBytes(byte[] data, byte[] bytes, int count)
  {
    int len = data == null ? 0 : data.length;
    byte[] newBuf = new byte[len + count];
    if (len > 0) {
      System.arraycopy(data, 0, newBuf, 0, len);
    }
    System.arraycopy(bytes, 0, newBuf, len, count);
    return newBuf;
  }
  
  public void write(byte[] content)
    throws IOException
  {
    this.out.write(content);
    this.out.flush();
  }
  
  public void write(String content)
    throws IOException
  {
    byte[] bytes = content.getBytes(this.encoding);
    write(bytes);
  }
}
