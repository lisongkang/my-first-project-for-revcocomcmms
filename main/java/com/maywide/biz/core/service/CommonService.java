package com.maywide.biz.core.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.ErrorConvert;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ResponseWrapper;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.pojo.bosshttpinf.BossBizServiceCommResp;
import com.maywide.biz.core.pojo.sta.CntBO;
import com.maywide.biz.core.pojo.sta.TmpStadataBO;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessInterReq;
import com.maywide.biz.iomproj.service.ImoprojHttpService;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.security.encrypt.SecurityFactory;
import com.maywide.core.security.remote.socket.Environment;
import com.maywide.core.security.remote.socket.ServiceResponse;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

public class CommonService {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected PersistentService DAO;
    @Autowired
    protected BossHttpClientService bossHttpClientService;
    @Autowired
    protected UapSocketClientService uapSocketClientService;
    @Autowired
    private ParamService paramService;
    @Autowired
    protected PayPlatformHttpClientService payPlatformHttpClientService;
    @Autowired
    protected CmpHttpClientService cmpHttpClientService;
    @Autowired
    protected McrHttpClientService mcrHttpClientService;
    @Autowired
    protected OAuthHttpClientService oAuthHttpClientService;
    @Autowired
    protected ImoprojHttpService imoprojHttpService;
    @Autowired
    protected AuthHtpClientService authHtpClientService;
    
    @Autowired
	protected OssHttpClientService ossHttpClientService;

    protected static Map<String, String> errorMap = new HashMap<String, String>();
    
    public PersistentService getDAO() {
        return DAO;
    }

    public void setDAO(PersistentService dAO) {
        DAO = dAO;
    }

    // --分页查询 begin
    /**
     * 分页查询--将分页查询再封装一次，方便和前台jqgrid交互。
     * （注意pageable是当前页，默认从0页开始。比如要查询第2页的数据，pageable.PageSize的值应该为2）。
     * 
     * @param pageable
     * @param sql
     * @param cls
     * @param params
     * @return
     * @throws Exception
     */
    public org.springframework.data.domain.Page findPageResult(
            Pageable pageable, String sql, Class cls, Object... params)
            throws Exception {

        if (null == pageable) {
            pageable = new PageRequest(0, 10, null);
        }
        
        // pageable传进来的是当前页，默认从0开始
        pageable = pageable.next();

        Page page = new Page();
        page.setPageSize(10);
        if (pageable.getPageSize() > 0) {
            page.setPageSize(Integer.valueOf(pageable.getPageSize()));
        }
        if (pageable.getPageNumber() > 0) {
            page.setPageNo(Integer.valueOf(pageable.getPageNumber()));
        }

        getDAO().clear();
        page = getDAO().find(page, sql, cls, params);

        List retList = page.getResult();
        if (BeanUtil.isListNull(retList)) {
            retList = new ArrayList();
        }

        org.springframework.data.domain.Page retPage = new PageImpl(retList,
                pageable, page.getTotalCount());

        return retPage;
    }

    // --分页查询 end

    protected void setExceptionInfo(Exception e, ReturnInfo returnInfo) {
        returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_JSON_CODE);
        returnInfo.setMessage(e.getMessage());
    }

    // --处理boss http接口的调用 begin
    protected void parseBossResponse(JsonNode nodeTree) throws Exception {
        parseBossResponse(nodeTree, null);
    }

    @Transactional(readOnly = true)
    public List<ErrorConvert> getErrorConvertList(String infMark)
            throws Exception {
        List<ErrorConvert> list = null;

        try {
            ErrorConvert errorConvertVO = new ErrorConvert();
            if (StringUtils.isNotBlank(infMark)) {
                // 先用remark字段标示是哪类接口,如boss_http、uap_socket等
                errorConvertVO.setRemark(infMark);
            }

            list = getDAO().find(errorConvertVO, true);
            errorMap.clear();
            for (ErrorConvert convert : list) {
                String key = convert.getInterfaceName() + "_"
                        + convert.getErrorCode();
                if (StringUtils.isNotBlank(infMark)) {
                    key = infMark + "_" + key;
                }
                errorMap.put(key, convert.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("查询错误转换信息出错！", e);
            throw e;
        }
        return list;
    }

    public String getErrorConvert(String interfaceName, String errorCode,
            String infMark) throws Exception {
        if (errorMap == null) {
            errorMap = new HashMap<String, String>();
            getErrorConvertList(infMark);
        }
        if (StringUtils.isBlank(infMark)) {
            return errorMap.get(interfaceName + "_" + errorCode);
        }
        return errorMap.get(infMark + "_" + interfaceName + "_" + errorCode);
    }

    public void parseBossResponse(JsonNode nodeTree, String interfaceName)
            throws Exception {
        if (IErrorDefConstant.ERROR_SUCCESS_CODE != nodeTree.get("status")
                .asInt()) {
            String exceptionDesc = "";
            if (interfaceName != null) {
                String infMark = null;
                exceptionDesc = getErrorConvert(interfaceName,
                        nodeTree.get("status").asText(), infMark);
                if (StringUtils.isNotBlank(exceptionDesc)) {
                    throw new BusinessException(exceptionDesc);
                }
            }
            if (nodeTree.get("message") == null) {
                exceptionDesc = "未知错误";
            } else {
                exceptionDesc = nodeTree.get("message").asText();
            }
            throw new BusinessException(exceptionDesc);
        }
    }
    
    public String getBossHttpInfo(String bizorderid, String servicecode,
            Object req2Boss, LoginInfo loginInfo) throws Exception{
    	// 转成json串
        String requestContent = JSONUtil.serialize(req2Boss);
        if(StringUtils.isNotBlank(requestContent)){
        	log.info("=========>servicecode:"+servicecode+"=========>"+"requestContent:"+requestContent);
        }
        String response = bossHttpClientService.requestPost(bizorderid,
                servicecode, requestContent, loginInfo);
        if (StringUtils.isEmpty(response)) {
            throw new BusinessException("BOSS接口调用出错，没有返回数据");
        }
        if(StringUtils.isNotBlank(response)){
        	log.info("=========>response:"+response+"=========>");
        }
        return response;
    }

    public String getBossHttpInfOutput(String bizorderid, String servicecode,
            Object req2Boss, LoginInfo loginInfo) throws Exception {

        // 转成json串
        String requestContent = JSONUtil.serialize(req2Boss);
        if(StringUtils.isNotBlank(requestContent)){
        	log.info("=========>servicecode:"+servicecode+"=========>"+"requestContent:"+requestContent);
        }
        String response = bossHttpClientService.requestPost(bizorderid,
                servicecode, requestContent, loginInfo);
        if (StringUtils.isEmpty(response)) {
            throw new BusinessException("BOSS接口调用出错，没有返回数据");
        }
        if(StringUtils.isNotBlank(response)){
        	log.info("=========>response:"+response+"=========>");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setDateFormat(dateFormat);

        JsonNode nodeTree = objectMapper.readTree(response);
        parseBossResponse(nodeTree, servicecode);

        JsonNode outputNode = nodeTree.get("output");

        String bossRespOutput = null;
        if (null != outputNode) {
            bossRespOutput = outputNode.toString();
        }
        return bossRespOutput;
    }

    public Object copyBossResp2InterResp(Object object, Class cls,
            String jsonStr) throws Exception {

        Object obj = BeanUtil.jsonToObject(jsonStr, cls);
        BeanUtils.copyProperties(object, obj);

        return obj;
    }

    /**
     * 保存boss返回的boss订单公共信息
     * 
     * @param bizorderid
     * @param jsonStr
     * @throws Exception
     */
    public void savePortalOrder(String bizorderid, String jsonStr)
            throws Exception {
        CheckUtils.checkEmpty(bizorderid, "保存boss订单信息:客户订单号不能为空");

        if (null == jsonStr) {
            return;
        }

        BossBizServiceCommResp bossBizServiceCommResp = (BossBizServiceCommResp) BeanUtil
                .jsonToObject(jsonStr, BossBizServiceCommResp.class);

        BizPortalOrder order = new BizPortalOrder();

        copyBossBizServiceCommResp2PortalOrder(bossBizServiceCommResp, order);
        order.setId(Long.valueOf(bizorderid));
        order.setCreatetime(new Date());
        // 更新订单状态
        order.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
        
        try{
        	getDAO().save(order);
        	getDAO().flushSession();
        }catch(Exception e){
        	log.error("插入订单表失败"+e.getMessage());
        }
    }


    /**
     * 保存boss返回的boss订单公共信息
     *
     * @param bizorderid
     * @param jsonStr
     * @throws Exception
     */
    public void savePortalOrdernew(String bizorderid, String jsonStr,BizPreprocessInterReq req)
            throws Exception {
        CheckUtils.checkEmpty(bizorderid, "保存boss订单信息:客户订单号不能为空");

        if (null == jsonStr) {
            return;
        }

        BossBizServiceCommResp bossBizServiceCommResp = (BossBizServiceCommResp) BeanUtil
                .jsonToObject(jsonStr, BossBizServiceCommResp.class);

        BizPortalOrder order = new BizPortalOrder();

        copyBossBizServiceCommResp2PortalOrder(bossBizServiceCommResp, order);
        order.setId(Long.valueOf(bizorderid));
        order.setCreatetime(new Date());
        // 更新订单状态
        order.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);

        try{
            getDAO().save(order);
            getDAO().flushSession();
            //关联组网订单池表
            //将订单号和订单池表关联起来
            if(req.getPreacceptserialno()!= null && StringUtils.isNotBlank(req.getPreacceptserialno())){
                //更新订单池订单对应状态
                StringBuffer sql = new StringBuffer();
                sql.append(" UPDATE biz_networkbus_custorder_pool set preprocessorder = ?,preprocessfees = ? where preserialno = ? ");
                try {
                    DAO.clear();
                    int i = DAO.executeSql(sql.toString(),bizorderid,bossBizServiceCommResp.getSums(),req.getPreacceptserialno());
                    if(i<=0){
                        log.error("关联失败");
                    }

                }catch (Exception e){
                    log.error("关联异常");
                }
            }
        }catch(Exception e){
            log.error("插入订单表失败"+e.getMessage());
        }
    }

    /**
     * 保存boss返回的boss订单公共信息--辅助方法
     * 
     * @param bossBizServiceCommResp
     * @param order
     * @throws Exception
     */
    private void copyBossBizServiceCommResp2PortalOrder(
            BossBizServiceCommResp bossBizServiceCommResp, BizPortalOrder order)
            throws Exception {
        CheckUtils.checkNull(bossBizServiceCommResp, "获取boss订单信息:boss订单对象不能为空");
        CheckUtils.checkNull(order, "获取boss订单信息:订单对象不能为空");
        CheckUtils
                .checkEmpty(bossBizServiceCommResp.getOrderid(), "boss订单id为空");

        order.setFees(bossBizServiceCommResp.getSums());
        order.setFeestr(bossBizServiceCommResp.getFeename());
        // order.setOrderid(orderid);
        order.setOrdertype(bossBizServiceCommResp.getOrdertype());
        order.setResporderid(Long.valueOf(bossBizServiceCommResp.getOrderid()));
        // order.setStatus(status);
    }

    // --处理boss http接口的调用 end

    // --处理统一认证平台 socket接口的调用 begin
    public String getUapSocketInfOutput(String bizorderid, String servicecode,
            Object req2Uap, LoginInfo loginInfo) throws Exception {

        String response = uapSocketClientService.sockectSend(bizorderid,
                req2Uap, servicecode, loginInfo);
        if (StringUtils.isEmpty(response)) {
            throw new BusinessException("认证平台接口调用出错，没有返回数据");
        }

        parseUapResponse(response, servicecode);

        return response;
    }
    
    
    /**
     * 番禺调用短信接口
     * @param bizorderid
     * @param service
     * @param req2Uap
     * @param loginInfo
     * @param clientid
     * @return
     * @throws Exception
     */
    public String getPyUapSocketInfoOutput(String bizorderid,String service,
    		Object req2Uap,LoginInfo loginInfo,String clientid) throws Exception{
    	String response = uapSocketClientService.pySocketSend(bizorderid, req2Uap, service, loginInfo, SysConfig.getUapUrl(), Environment.BOSS_ENCODING, clientid);
    	 if (StringUtils.isEmpty(response)) {
             throw new BusinessException("短信平台接口调用出错，没有返回数据");
         }
    	 ServiceResponse serviceResponse = socketResp2Respobj(response,null);
    	 if(serviceResponse == null){
    		 throw new BusinessException("短信平台接口调用出错，没有返回数据");
    	 }
    	 if(serviceResponse.getStatus() != Environment.UAP_STATUS_SUCCESS){
    		 String message = StringUtils.isNotBlank(serviceResponse.getMessage())?serviceResponse.getMessage():"短信平台接口调用出错，没有返回数据";
    		 throw new BusinessException(message);
    	 }
    	 return response;
    }

    public ServiceResponse socketResp2Respobj(String response, Object output)
            throws Exception {
        ServiceResponse retServiceResponse = uapSocketClientService
                .socketResp2Respobj(response, output);

        return retServiceResponse;
    }

    private void parseUapResponse(String response, String interfaceName)
            throws Exception {
        ServiceResponse resp = uapSocketClientService.socketResp2Respobj(
                response, null);

        if (!Environment.UAP_STATUS_SUCCESS.endsWith(resp.getStatus())) {
            String exceptionDesc = "";
            if (interfaceName != null) {
                String infMark = null;
                exceptionDesc = getErrorConvert(interfaceName,
                        resp.getStatus(), infMark);
                if (StringUtils.isNotBlank(exceptionDesc)) {
                    throw new BusinessException(exceptionDesc);
                }
            }
            if (resp.getMessage() == null) {
                exceptionDesc = "未知错误";
            } else {
                exceptionDesc = resp.getMessage();
            }
            throw new BusinessException(exceptionDesc);
        }

    }

    public Object socketResp2Outputobj(String response, Class outputClass)
            throws Exception {
        Object output = outputClass.newInstance();
        ServiceResponse retServiceResponse = uapSocketClientService
                .socketResp2Respobj(response, output);

        return retServiceResponse.getOutput();
    }
    
    
    public String getSocketSend(String bizorderid, String servicecode,
            Object req2Uap, LoginInfo loginInfo,String uapUrl,String encoding) throws Exception{
    	String response = uapSocketClientService.socketSend(bizorderid, req2Uap, servicecode, loginInfo, uapUrl,encoding);
    	if (StringUtils.isEmpty(response)) {
            throw new BusinessException("认证平台接口调用出错，没有返回数据");
        }

        parseUapResponse(response, servicecode);

        return response;
    }

    // --处理统一认证平台 socket接口的调用 end

    // --排名统计共公方法 begin
    public List<CntBO> getTotalCntdataFromTmpCntdata(String serialno,
            String objid) throws Exception {
        CheckUtils.checkEmpty(serialno, "流水号不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT tt.objid, ");
        sql.append("        tt.objname, ");
        sql.append("        tt.datanum totalcnt, ");
        sql.append("        (SELECT COUNT(1) + 1 ");
        sql.append("           FROM (SELECT objid, objname, SUM(datanum) datanum FROM tmp_stadata  ");
        sql.append("           WHERE serialno=?  ");
        paramList.add(serialno);
        sql.append("           GROUP BY objid,objname) a ");
        sql.append("          WHERE a.datanum > tt.datanum) AS rank ");
        sql.append("   FROM (SELECT objid, objname, SUM(datanum) datanum FROM tmp_stadata  ");
        sql.append(" WHERE serialno=?   ");
        paramList.add(serialno);
        sql.append("   GROUP BY objid, objname) tt ");
        sql.append("   ) v ");
        sql.append(" WHERE 1=1 ");
        if (StringUtils.isNotBlank(objid)) {
            sql.append("    and v.objid=? ");
            paramList.add(objid);
        }
        sql.append("    order by v.rank ");

        List<CntBO> retCntList = getDAO().find(sql.toString(), CntBO.class,
                paramList.toArray());

        return retCntList;
    }

    public void genTmpCntdata(String bizorderid, List<TmpStadataBO> incomeList)
            throws Exception {
        CheckUtils.checkEmpty(bizorderid, "业务订单号不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.setLength(0);
        paramList.clear();
        sql.append(" DELETE FROM tmp_stadata WHERE serialno = ? ");
        paramList.add(bizorderid);
        getDAO().executeSql(sql.toString(), paramList.toArray());

        if (BeanUtil.isListNotNull(incomeList)) {

            sql.setLength(0);
            paramList.clear();
            sql.append(" INSERT INTO tmp_stadata ");
            sql.append("   (recid, objid, objname, datanum, serialno) ");
            sql.append("   SELECT NULL, ?, ?, ?, ? FROM DUAL ");
            for (TmpStadataBO obj : incomeList) {
                // 将数插入临时表
                paramList.clear();
                paramList.add(obj.getObjid());
                paramList.add(obj.getObjname());
                paramList.add(obj.getDatanum());
                paramList.add(bizorderid);

                getDAO().executeSql(sql.toString(), paramList.toArray());
            }
        }
    }

    // --排名统计共公方法 end

    // 翻译
    private String translateMcode(String gcode, String mcode) throws Exception {
        String retMname = "";
        if (StringUtils.isBlank(gcode) || StringUtils.isBlank(mcode)) {
            return retMname;
        }

        retMname = paramService.getMname(gcode, mcode);
        if (StringUtils.isBlank(retMname)) {
            retMname = mcode;
        }

        return retMname;
    }
    
    /**
     * 
     * 方法描述 隐藏银行卡号中间的字符串（使用*号），显示前四后四
     *
     * @param cardNo
     * @return
     * 
     * @author ywp
     * @date 2019-1-29 12:54:52
     */
    public String hideCardNo(String cardNo) {
        if(StringUtils.isBlank(cardNo)) {
            return cardNo;
        }

        int length = cardNo.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i < beforeLength || i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }
    
    // 替换敏感信息
    public String replaceSensitiveInfo(String oldstr, String proctype) {
        if (StringUtils.isBlank(oldstr)) {
            return oldstr;
            // oldstr = oldstr.substring(beginIndex, endIndex);
        }

        int length = oldstr.length();

        if (proctype.equals("cardno")) {
            if (length >= 18) {
                oldstr = oldstr.substring(0, 6) + genStr("*", 8)
                        + oldstr.substring(14, length);
            } else if (length >= 15) {
                oldstr = oldstr.substring(0, 6) + genStr("*", 6)
                        + oldstr.substring(12, length);
            } else if (length > 10) {
                oldstr = genStr("*", 6) + oldstr.substring(6, length);
            } else if (oldstr.length() > 4) {
                oldstr = genStr("*", 3) + oldstr.substring(3, oldstr.length());
            }
        } else if (proctype.equals("addr")) {
            if (length > 18) {
                oldstr = oldstr.substring(0, length - 10) + genStr("*", 10);
            } else if (oldstr.length() > 10) {
                oldstr = oldstr.substring(0, length - 6) + genStr("*", 6);
            } else if (oldstr.length() > 4) {
                oldstr = oldstr.substring(0, length - 4) + genStr("*", 4);
            } else if (oldstr.length() > 3) {
                oldstr = oldstr.substring(0, length - 3) + genStr("*", 3);
            } else if (oldstr.length() > 2) {
                oldstr = oldstr.substring(0, length - 2) + genStr("*", 2);
            } else {
                oldstr = genStr("*", length);
            }
        } else if (proctype.equals("phone")) {
            if (length >= 18) {
                oldstr = oldstr.substring(0, 4) + genStr("*", 10)
                        + oldstr.substring(14, length);
            } else if (length > 13) {
                oldstr = oldstr.substring(0, 6) + genStr("*", 4)
                        + oldstr.substring(10, length);
            } else if (length > 10) {
                oldstr = oldstr.substring(0, 3) + genStr("*", 4)
                        + oldstr.substring(7, length);
            } else if (length > 7) {
                oldstr = oldstr.substring(0, 2) + genStr("*", 4)
                        + oldstr.substring(6, length);
            } else if (length > 4) {
                oldstr = genStr("*", 3) + oldstr.substring(3, length);
            }
        } else if (proctype.equals("acctno")) {
            if (length >= 12) {
                oldstr = oldstr.substring(0, length - 8) + genStr("*", 4)
                        + oldstr.substring(length - 4, length);
            } else if (length > 6) {
                oldstr = oldstr.substring(0, length - 4) + genStr("*", 4);
            } else if (length > 4) {
                oldstr = genStr("*", 3) + oldstr.substring(3, length);
            }
        } else if (proctype.equals("custname")) {

            if (length >= 4) {
                oldstr = oldstr.substring(0, 2)
                        + genStr("*", (length + 1) / 2)
                        + oldstr.substring(2 + (length + 1) / 2,
                                oldstr.length());
            } else if (length > 1) {
                oldstr = oldstr.substring(0, 1) + genStr("*", 1)
                        + oldstr.substring(2, oldstr.length());
                ;
            }
        } else {

            if (length >= 4) {
                oldstr = oldstr.substring(0, 2)
                        + genStr("*", (length + 1) / 2)
                        + oldstr.substring(2 + (length + 1) / 2,
                                oldstr.length());
            } else if (length > 1) {
                oldstr = oldstr.substring(0, 1) + genStr("*", 1)
                        + oldstr.substring(2, oldstr.length());
                ;
            }
        }

        return oldstr;
    }

    private String genStr(String srcStr, int length) {
        String retStr = srcStr;
        if (null != srcStr && "" != srcStr) {
            for (int i = 1; i < length; i++) {
                retStr = retStr + srcStr;
            }
        }
        return retStr;
    };
    
    public String getPayPlatFormInfOutput(String bizOrderid,String serviceCode,Object req2pay) throws Exception{
    	String requestContent = JSONUtil.serialize(req2pay, null, null, true, true);
    	if(StringUtils.isNotBlank(requestContent)){
        	log.info("=========>servicecode:"+serviceCode+"=========>"+"requestContent:"+requestContent);
        }
    	String response = payPlatformHttpClientService.requestPlatformPost(bizOrderid, serviceCode, requestContent);
    	if(StringUtils.isNotBlank(response)){
    		log.info("=========>response:"+response+"=========>");
    	}
    	return response;
    }

    public String getPayPlatFormInfOutput(String serviceCode,Object req2pay) throws Exception{
//        String bizOrderid = getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
    	String bizOrderid = getBizorderid();
        return getPayPlatFormInfOutput(bizOrderid, serviceCode, req2pay);
    }
    
	public String getCmpHttpInfOutput(String bizorderid, String path, List<NameValuePair> params)
			throws Exception {
		String request = JSONUtil.serialize(params);
		if (StringUtils.isNotBlank(request)) {
			log.info("=========>path:" + path + "=========>" + "params:" + request);
		}
		String response = cmpHttpClientService.requestPost(bizorderid, path, params);
		if (StringUtils.isNotBlank(response)) {
			log.info("=========>response:" + response + "=========>");
		}
		return response;
	}

	public String getCmpHttpInfOutput(String path, List<NameValuePair> params) throws Exception {
//		String bizOrderid = getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		String bizOrderid = getBizorderid();
		return getCmpHttpInfOutput(bizOrderid, path, params);
	}

	public String getCmpHttpKeyInfoOutPut(String servName,Map<String,String> params,boolean checkResult) throws Exception{
//		String bizOrderid = getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		String bizOrderid = getBizorderid();
		String response = cmpHttpClientService.requestPost(bizOrderid, servName, params);
		if (checkResult) {
			checkCmpResult(response);
		}
		return response;
	}

	private void checkCmpResult(String response)throws Exception {
		if (StringUtils.isBlank(response)) {
			throw new BusinessException("CMP接口调用失败，没有返回数据");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode nodeTree = objectMapper.readTree(response);
		String code = nodeTree.get("code").asText();
		if (!"0".equals(code)) {
			String msg = nodeTree.get("msg").asText();
			if (StringUtils.isBlank(msg)) {
				msg = "未知错误";
			}
			throw new BusinessException("CMP接口调用失败：" + msg);
		}
	}

	public String getCmpHttpKeyInfoOutPut(String servName, Map<String, String> params) throws Exception {
		return getCmpHttpKeyInfoOutPut(servName, params, false);
	}

	public String getMcrHttpOutPut(String path, Map<String, String> params) throws Exception {
//		String bizOrderid = getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		String bizOrderid = getBizorderid();
		String response = mcrHttpClientService.requestPost(bizOrderid, path, params);
		if (StringUtils.isBlank(response)) {
			throw new BusinessException("微厅接口调用失败，没有返回数据");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode nodeTree = objectMapper.readTree(response);
		String code = nodeTree.get("errcode").asText();
		if (!"0".equals(code)) {
			String msg = nodeTree.get("errmsg").asText();
			if (StringUtils.isBlank(msg)) {
				msg = "未知错误";
			}
			throw new BusinessException("微厅接口调用失败：" + msg);
		}
		return response;
	}
	
	public String getOssHttpOutPut(String path, Object req) throws Exception {
//		String bizOrderid = getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		String bizOrderid = getBizorderid();
		
        String requestContent = JSONUtil.serialize(req);
        if(StringUtils.isNotBlank(requestContent)){
        	log.info("=========>"+"requestContent:"+requestContent);
        }
        String response = ossHttpClientService.requestPost(bizOrderid,
        		path,requestContent);
        if (StringUtils.isEmpty(response)) {
            throw new BusinessException("OSS接口调用出错，没有返回数据");
        }
        if(StringUtils.isNotBlank(response)){
        	log.info("=========>response:"+response+"=========>");
        }
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode nodeTree = objectMapper.readTree(response);
		String statusCode = nodeTree.get("statusCode").asText();
		if (!"200".equals(statusCode)) {
//			String msg = nodeTree.get("message").asText();
			String msg = nodeTree.get("error").asText();
			if (StringUtils.isBlank(msg)) {
				msg = "未知错误";
			}
			throw new BusinessException("OSS接口调用失败：" + msg);
		}
		return response;
	}

	public String getOAuthHttpOutPut(String api, Object params) throws Exception {
		String response = oAuthHttpClientService.requestPost(api, params);
		log.info("=========>response:" + response);
		if (StringUtils.isBlank(response)) {
			throw new BusinessException("oauth接口调用失败，没有返回数据");
		}
		ResponseWrapper wrapper = (ResponseWrapper) BeanUtil.jsonToObject(response, ResponseWrapper.class);
		if (wrapper == null || wrapper.getReturnInfo() == null) {
			throw new BusinessException("oauth接口调用失败，数据解析失败");
		}
		ReturnInfo returnInfo = wrapper.getReturnInfo();
		if (returnInfo.getCode() != 0l) {
			if (StringUtils.isBlank(returnInfo.getMessage())) {
				returnInfo.setMessage("服务调用异常");
			}
			throw new BusinessException("oauth接口调用失败：" + returnInfo.getMessage());
		}
		String decrypt = SecurityFactory.getSecurityService().decrypt(wrapper.getResponseBody());
		if (StringUtils.isBlank(decrypt)) {
			throw new BusinessException("oauth接口数据解析失败");
		}
		return decrypt;
	}
	
	public String getNewBizOrderId() throws Exception {
//		return getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		return getBizorderid();
	}
	
	public String getImoprojRespone(String apiName,Object params) throws Exception{
		String requestStr = new Gson().toJson(params);
//		String requestStr = JSONUtil.serialize(params);
        log.info("request2imoporj==================="+requestStr);
        String url = SysConfig.getIomporjUrl();
        if(StringUtils.isBlank(url)){
            throw new BusinessException("参数配置错误,装维工单服务器地址为空");
        }
        String respone = imoprojHttpService.getResponeStr(apiName,url,20000000, requestStr, "str");
        log.info("imoporj2response"+respone);
        if(respone.contains("error")){
            throw new BusinessException("装维工单异常"+respone);
        }
        return respone;
    }


    /**
     * 请求后台开发组接口
     * @param loginInfo
     * @param bizOrderid
     * @param serviceName
     * @param params
     * @return
     * @throws NumberFormatException
     * @throws Exception
     */
    public String getAuthImplResponse(LoginInfo loginInfo,String bizOrderid,String serviceName,Object params) throws NumberFormatException, Exception{
        String requestStr = new Gson().toJson(params);
        log.info("request2imoporj==================="+requestStr);
        String response = authHtpClientService.requestPost(loginInfo, bizOrderid, serviceName, params);
        return response;
    }


    protected ReturnInfo initReturnInfo(){
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        return returnInfo;
    }

    protected LoginInfo getLoginInfo() throws Exception{
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        return loginInfo;
    }

    protected final List<PrvSysparam> getParams(String gcode, String mcodes) throws Exception {
        if (StringUtils.isBlank(mcodes))
            return null;
        String[] modeList = mcodes.split(",");
        List<PrvSysparam> resultList = new ArrayList<PrvSysparam>();
        for (String mcode : modeList) {
            PrvSysparam param = new PrvSysparam();
            param.setGcode(gcode);
            param.setMcode(mcode);
            List<PrvSysparam> params = getDAO().find(param);
            if (params != null && !params.isEmpty()) {
                resultList.addAll(params);
            }
        }
        return resultList;
    }
    
    protected  String getBizorderid(){
    	String idStr;
		try {
			Thread thread = Thread.currentThread();
			long id = thread.getId();
			idStr = String.valueOf(id);
			if (idStr.length() < 3) {
				StringBuffer idSb = new StringBuffer(idStr);

				int length = idSb.toString().length();
				for (int i = 0; i < 3 - length; i++) {
					idSb.append(new Random().nextInt(10));
				}
				idStr = idSb.toString();
			} else {
				idStr = idStr.substring(idStr.length() - 3, idStr.length());
			}
		} catch (Exception e) {
			idStr = "000";
		}
		
		String bizorderid = System.currentTimeMillis()+idStr;
		return bizorderid;
    }
}
