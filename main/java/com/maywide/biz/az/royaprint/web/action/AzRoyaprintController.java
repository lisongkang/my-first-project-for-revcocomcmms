package com.maywide.biz.az.royaprint.web.action;

import com.maywide.biz.az.royaprint.entity.BizApplyConstBO;
import com.maywide.biz.az.royaprint.entity.BizApplyFileInfo;
import com.maywide.biz.az.royaprint.entity.BizApplyPrintInfo;
import com.maywide.biz.az.royaprint.pojo.AzRoyaSerchParamsBO;
import com.maywide.biz.az.royaprint.service.AzRoyaprintService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.entity.BizApplication;
import com.maywide.biz.inter.pojo.queApplicationAllinfo.ApplicationAccfileidsBO;
import com.maywide.biz.inter.service.SmallMicroProSettlementService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 小额工程打印表单信息查询
 * Created by lisongkang on 2019/6/12 0001.
 */
@Results({
        @Result(name = "successprint", location="/az/royaprint/az-royaprint-azroyaindex.jsp")
})
public class AzRoyaprintController extends BaseController<BizApplication,Long> {
    @Autowired
    private AzRoyaprintService azRoyaprintService;

    private AzRoyaSerchParamsBO azRoyaSerchParamsBO;

    @Autowired
    private PersistentService persistentService;

    @Autowired
    private ParamService paramService;
    @Autowired
    private SmallMicroProSettlementService smallMicroProSettlementService;
    @Override
    protected BaseService<BizApplication, Long> getEntityService() {
        return azRoyaprintService;
    }

    @Override
    protected void checkEntityAclPermission(BizApplication entity) {
        // TODO Add acl check code logic
    }
    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        // TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }

    /**
     * 查询申请单首页
     */
    public String azroyaindex(){
        return "successprint";
    }
    /**
     *
     * 申请单首页查询
     */

    public HttpHeaders findRoyaByPage(){
        List<BizApplication> list = new ArrayList<BizApplication>();
        azRoyaSerchParamsBO = new AzRoyaSerchParamsBO();
        if(getParameter("AzRoyaSerchParamsBO.city") != null && !"".equals(getParameter("AzRoyaSerchParamsBO.city"))){
            azRoyaSerchParamsBO.setCity(getParameter("AzRoyaSerchParamsBO.city"));
        }
        String aredaid = getParameter("AzRoyaSerchParamsBO.areaid");
        if(!"".equals(aredaid) && aredaid != null){
            azRoyaSerchParamsBO.setAreaid(Long.parseLong(getParameter("AzRoyaSerchParamsBO.areaid")));
        }
        if(getParameter("AzRoyaSerchParamsBO.constructors") != null && !"".equals(getParameter("AzRoyaSerchParamsBO.constructors"))){
            azRoyaSerchParamsBO.setConstructors(getParameter("AzRoyaSerchParamsBO.constructors"));
        }
        if(getParameter("AzRoyaSerchParamsBO.proname")!= null && !"".equals(getParameter("AzRoyaSerchParamsBO.proname"))){
            azRoyaSerchParamsBO.setProname(getParameter("AzRoyaSerchParamsBO.proname"));
        }
        if(getParameter("AzRoyaSerchParamsBO.prostatus") != null && !"".equals(getParameter("AzRoyaSerchParamsBO.prostatus"))){
            azRoyaSerchParamsBO.setProstatus(getParameter("AzRoyaSerchParamsBO.prostatus"));
        }
        if(!"".equals(getParameter("AzRoyaSerchParamsBO.timeRange"))&& getParameter("AzRoyaSerchParamsBO.timeRange") != null){
            azRoyaSerchParamsBO.setTimeRange(getParameter("AzRoyaSerchParamsBO.timeRange"));
        }
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try{
            setModel(azRoyaprintService.querySaleOrderList(azRoyaSerchParamsBO, pageable));
        }catch(Exception e){
            e.printStackTrace();
            setModel(new PageImpl<BizApplication>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }

    //查找打印信息
    public HttpHeaders findPrintInfo(){
        String proid = getParameter("proid");
        getRequest().setAttribute("proid",proid);
        try {
            BizApplication bizApplication = azRoyaprintService.queApplication(proid);
            getRequest().setAttribute("bizApplication", bizApplication);
            List<BizApplyConstBO> bizApplyConstBOList = azRoyaprintService.queBizApplyInfos(String.valueOf(bizApplication.getId()));
            getRequest().setAttribute("bizApplyConstBOList",bizApplyConstBOList);
            BizApplyPrintInfo bizApplyPrintInfo = new BizApplyPrintInfo();
            bizApplyPrintInfo.setBizApplication(bizApplication);
            bizApplyPrintInfo.setBizApplyConstBOList(bizApplyConstBOList);
            setModel(bizApplyPrintInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders("quetionlist");
    }

    //查找验收资料信息 findDeliveryInfo
    public HttpHeaders findDeliveryInfo(){
        String pronum = getParameter("pronum");
        getRequest().setAttribute("pronum",pronum);
        try {
            BizApplication bizApplication = azRoyaprintService.queApplication(pronum);
            getRequest().setAttribute("bizApplication", bizApplication);
            List<ApplicationAccfileidsBO> accfileidsBOLis = azRoyaprintService.queBizappFileInfo(String.valueOf(bizApplication.getId()));
            getRequest().setAttribute("accfileidsBOLis",accfileidsBOLis);
            BizApplyFileInfo bizApplyFileInfo = new BizApplyFileInfo();
            bizApplyFileInfo.setBizApplication(bizApplication);
            bizApplyFileInfo.setAccfileidsBOLis(accfileidsBOLis);
            setModel(bizApplyFileInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders("queApplyFilelist");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, String> getAreaMap() {
        String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
        List<PrvSysparam> areaList;
        Map<String, String> areaMap = new LinkedHashMap<String, String>();
        try {
            areaList = persistentService.find(hql, "PRV_CITY");
            Collections.sort(areaList, new Comparator() {
                public int compare(Object o1, Object o2) {
                    PrvSysparam param1 = (PrvSysparam) o1;
                    PrvSysparam param2 = (PrvSysparam) o2;

                    String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getMname());
                    String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getMname());

                    int rtnval = pinyin1.compareTo(pinyin2);

                    return rtnval;
                }
            });
            areaMap.put("*", "所有");
            for (PrvSysparam param : areaList) {
                areaMap.put(param.getMcode(), param.getDisplay());
            }

            return areaMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaMap;
    }

    @MetaData("下载模板")
    public void downTemplate() {
        String templateFileName = getParameter("templateFileName");
        String filepath = getParameter("filepath");
        if(StringUtils.isEmpty(templateFileName)){
            return;
        }
        String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream ops = null;
        FileInputStream fis = null;
        try {
            String fileName = templateFileName;
            // 针对IE或者以IE为内核的浏览器：
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")|| userAgent.contains("Edge")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            }
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");

            ServletContext servletContext = ServletActionContext.getServletContext();
           /* String realPath = servletContext.getRealPath("/WEB-INF/targetTemplate");
            String path =realPath+"/"+templateFileName;*/
            String imgDirPath = SysConfig.getFileUploadPath();
            String path = imgDirPath + filepath;

            byte[] buffer = new byte[8192];
            int bytesRead = 0;

            ops = response.getOutputStream();
            fis = new FileInputStream(path);
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                ops.write(buffer, 0, bytesRead);
            }
            ops.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ops != null) {
                    ops.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //打印记录转态
    public void printRecord(){
        String pronum = getParameter("pronum");
        getRequest().setAttribute("pronum",pronum);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        try {
            BizApplication bizApplication = azRoyaprintService.queApplication(pronum);
            bizApplication.setProstatus("13");
            persistentService.clear();
            persistentService.update(bizApplication);
            String operationtype = "提成分配打印";
            String operationresult = "1";
            smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(bizApplication.getId())
                    ,"",loginInfo.getOperid(),operationtype,operationresult);
        }catch (Exception e){
            e.printStackTrace();
            throw new WebException(e.getMessage(), e);
        }

    }

    public AzRoyaSerchParamsBO getAzRoyaSerchParamsBO() {
        return azRoyaSerchParamsBO;
    }

    public void setAzRoyaSerchParamsBO(AzRoyaSerchParamsBO azRoyaSerchParamsBO) {
        this.azRoyaSerchParamsBO = azRoyaSerchParamsBO;
    }
}
