package com.maywide.biz.salary.web.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.salary.entity.BaseWageHonus;
import com.maywide.biz.salary.service.BaseWageHonusService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;
import com.maywide.tool.excel.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lisongkang on 2019/8/22 0001.
 */
public class BaseWageHonusController extends BaseController<BaseWageHonus,Long> {

    @Autowired
    private PersistentService persistentService;
    private ObjectMapper jsonMapper = null;
    private String savePath;
    private String imgDirPath;
    @Autowired
    private BaseWageHonusService baseWageHonusService;
    /**
     * 全局使用变量
     */
    private LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

    private ExcelUtil eu =new ExcelUtil();

    private File myFile;

    private String fileName;


    @Autowired
    private ParamService paramService;
    private BaseWageHonus baseWageHonus;//搜索映射对象
    @Override
    protected BaseService<BaseWageHonus, Long> getEntityService() {
        return baseWageHonusService;
    }

    @Override
    protected void checkEntityAclPermission(BaseWageHonus entity) {
        // TODO Add acl check code logic
    }
    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        // TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }
    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
        Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
        String areaid = getParameter("areaid");
        String loginname = getParameter("loginname");
        String name = getParameter("name");
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT name  from prv_area where areaid  = ?  ");
        paramList.add(areaid);
        try {
            StringBuffer sqlTemp = new StringBuffer();
            List<Object> paramListTemp = new ArrayList<Object>();
            sqlTemp.append("SELECT operid from prv_operator where loginname = ?  and name = ? ");
            paramListTemp.add(loginname);
            paramListTemp.add(name);
            String operid =  persistentService.findUniqueObject(sqlTemp.toString(),paramListTemp.toArray()).toString();
            persistentService.clear();
            bindingEntity.setOperid(Long.valueOf(operid));
            String areaname =  persistentService.findUniqueObject(sql.toString(),paramList.toArray()).toString();
            bindingEntity.setAreaidname(areaname);
        }catch (Exception e){
            setModel(OperationResult.buildFailureResult("请核对你添加的信息", errorMessageMap));
            return buildDefaultHttpHeaders("inputBasic");
        }
        return super.doSave();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }
    /**
     * 编辑
     * @return
     */
    @Override
    @MetaData("打开编辑表单")
    public HttpHeaders edit() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders("inputBasic");
    }

    /**
     * 分页搜索
     */
    public HttpHeaders findByPage(){
        List<BaseWageHonus> list = new ArrayList<BaseWageHonus>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        baseWageHonus = new BaseWageHonus();
        if(getParameter("BaseWageHonus.name")!= null && !"".equals(getParameter("BaseWageHonus.name"))){
            baseWageHonus.setName(getParameter("BaseWageHonus.name"));
        }
        if(getParameter("BaseWageHonus.loginname")!= null && !"".equals(getParameter("BaseWageHonus.loginname"))){
            baseWageHonus.setLoginname(getParameter("BaseWageHonus.loginname"));
        }
        if(getParameter("BaseWageHonus.dateMonth")!= null && !"".equals(getParameter("BaseWageHonus.dateMonth"))){
            baseWageHonus.setDateMonth(getParameter("BaseWageHonus.dateMonth"));
        }
        if(getParameter("BaseWageHonus.areaid")!= null && !"".equals(getParameter("BaseWageHonus.areaid"))){
            baseWageHonus.setAreaid(getParameter("BaseWageHonus.areaid"));
        }
        try{
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR) && !loginInfo.getRolelevel().equals("9")){
                baseWageHonus.setCity(loginInfo.getCity());
            }
            setModel(baseWageHonusService.findByPage(baseWageHonus,pageable));
        }catch(Exception e){
            e.printStackTrace();
            setModel(new PageImpl<BaseWageHonus>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }

    public Map<String, String> getCityMap(){
        Map<String, String> cityMap =  new LinkedHashMap();
        cityMap.put("*", "所有");
        cityMap.putAll(getParamByGcode("PRV_CITY",null));
        return cityMap;
    }

    public Map<String, String> getAreaMap2() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        return getParamByGcode("PRV_AREA_BY_CITY",loginInfo.getCity());
    }
    public Map<String, String> getAreaMap() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        Map<String, String> areaMap = new LinkedHashMap();
        areaMap.put("*", "所有");
        areaMap.putAll(getParamByGcode("PRV_AREA_BY_CITY",loginInfo.getCity()));
        return areaMap;
    }

    public Map<String,String> getParamByGcode(String gcode,String mcode){
        ParamService paramService = SpringContextHolder.getBean(ParamService.class);
        Map<String, String> adtypeMap = new LinkedHashMap<String, String>();
        try {
            List<PrvSysparam> params = null;
            if(StringUtils.isNotEmpty(mcode)){
                params = paramService.getData(gcode, mcode, null, null);
            }else{
                params =  paramService.getData(gcode);
            }
            for(PrvSysparam param :params){
                adtypeMap.put(param.getMcode(), param.getMname());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return adtypeMap;
    }

    @MetaData("下载模板")
    public void downTemplate() {
        String templateFileName = getParameter("templateFileName");
        if(StringUtils.isEmpty(templateFileName)){
            return;
        }
        String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream ops = null;
        FileInputStream fis = null;
        try {
            String fileName = "基础工资及奖金导入模板.xls";
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
            String realPath = servletContext.getRealPath("/WEB-INF/targetTemplate");
            String path =realPath+"/"+templateFileName;

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

    @MetaData("导入Excel数据")
    public HttpHeaders importExcelData()  {
        OperationResult result=null;
        //1.拿到ServletContext
        ServletContext servletContext = ServletActionContext.getServletContext();
        //2.调用realPath方法，获取根据一个虚拟目录得到的真实目录
        String realPath = servletContext.getRealPath("/WEB-INF/targetTemplate");
        //3.如果这个真实的目录不存在，需要创建
        File file = new File(realPath );
        if(!file.exists()){
            file.mkdirs();
        }
        //拷贝：把文件的临时文件复制到指定的位置。注意：临时文件还在
        //FileUtils.copyFile(myfile, new File(file,myfileFileName));
        if(new File(realPath+"/tmp.xls").exists()){
            new File(realPath+"/tmp.xls").delete();
        }
        //剪切：把临时文件剪切指定的位置，并且给他重命名。 注意：临时文件没有了
        myFile.renameTo(new File(file,"tmp.xls"));
        Map<Integer, List<Row>> mapData;
        try {
            mapData = eu.readExcel(realPath+"/tmp.xls");
            if(mapData.size()<1){
                throw new Exception("导入失败，在文档中未检测到数据，请检查文档是否存在数据！");
            }
            List<BaseWageHonus> list = new ArrayList<BaseWageHonus>();
            for(Map.Entry<Integer, List<Row>> entry: mapData.entrySet()) {
                List<Row> rows = entry.getValue();
                for (int i = 0; i < rows.size(); i++) {
                    if(entry.getKey()==0) {//第一个sheet工作空间
                        String areaidname = eu.getCellValue(rows.get(i).getCell(1));
                        if(areaidname==null|| areaidname.equals("")){
                            break;
                        }
                        BaseWageHonus baseWageHonus = new BaseWageHonus();
                        boolean tag=row2Data(rows.get(i), baseWageHonus,i+1);
                        if(!tag) {continue;}
                        list.add(baseWageHonus);
                    }
                }
            }
            baseWageHonusService.save(list);
            result=OperationResult.buildSuccessResult("Excel数据导入成功");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            result=OperationResult.buildFailureResult(e.getMessage());
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            result=OperationResult.buildFailureResult("违反唯一约束，您导入的数据已存在，请检查数据！");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result=OperationResult.buildFailureResult(e.getMessage());
        }
        setModel(result);
        return buildDefaultHttpHeaders();
    }

    //excel行转实体类
    public T bulidExcelEntity(Row row,List<T> rowList,int rowIndex) throws Exception{
        return null;
    }
    /**
     * excel行转Seller对象
     * @param row
     * @param
     */
    private boolean row2Data(Row row, BaseWageHonus baseWageHonus,int rowIndex) throws Exception{
        String city = eu.getCellValue(row.getCell(0));
        String areaidname = eu.getCellValue(row.getCell(1));
        String loginname = eu.getCellValue(row.getCell(2));
        String name = eu.getCellValue(row.getCell(3));
        Double amount = Double.parseDouble(eu.getCellValue(row.getCell(4)));
        BigDecimal b = new BigDecimal(amount);
        amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double honus = Double.parseDouble(eu.getCellValue(row.getCell(5)));//二类价
        BigDecimal b2 = new BigDecimal(honus);
        honus = b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String sdateMonth =  eu.getCellValue(row.getCell(6));
        String edateMonth =  eu.getCellValue(row.getCell(7));
        //校验数据
        List<BaseWageHonus> resultList = baseWageHonusService.getBaseWageHouns(loginname,sdateMonth);

        if(resultList.size() >0){
            throw new Exception("导入失败！有操作员生效月份数据重复，请检查第"+rowIndex+"行数据！");
        }
        List<BaseWageHonus> resultListe = baseWageHonusService.getBaseWageHouns(loginname,edateMonth);
        if(resultListe.size() >0){
            throw new Exception("导入失败！有操作员失效月份数据重复，请检查第"+rowIndex+"行数据！");
        }
        if(StringUtils.isBlank(city) || StringUtils.isBlank(areaidname)
                || StringUtils.isBlank(loginname) ){
            throw new Exception("导入失败！【地市】、【支公司】、【操作员】为必填项，请检查第"+rowIndex+"行数据是否存在空值！");
        }
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT areaid  from prv_area where name = ?  ");
        paramList.add(areaidname);
        persistentService.clear();
        String areaid =  persistentService.findUniqueObject(sql.toString(),paramList.toArray()).toString();


        StringBuffer sqlTemp = new StringBuffer();
        List<Object> paramListTemp = new ArrayList<Object>();
        sqlTemp.append("SELECT operid from prv_operator where loginname = ?  and name = ? ");
        paramListTemp.add(loginname);
        paramListTemp.add(name);
        persistentService.clear();
        String operid =  persistentService.findUniqueObject(sqlTemp.toString(),paramListTemp.toArray()).toString();

        //插入对象
        baseWageHonus.setAreaidname(areaidname);
        baseWageHonus.setCity(city);
        baseWageHonus.setLoginname(loginname);
        baseWageHonus.setName(name);
        baseWageHonus.setAmount(amount);
        baseWageHonus.setHonus(honus);
        baseWageHonus.setSdateMonth(sdateMonth);
        baseWageHonus.setEdateMonth(edateMonth);
        baseWageHonus.setAreaid(areaid);
        baseWageHonus.setOperid(Long.valueOf(operid));
        return true;
    }

    public BaseWageHonus getBaseWageHonus() {
        return baseWageHonus;
    }

    public void setBaseWageHonus(BaseWageHonus baseWageHonus) {
        this.baseWageHonus = baseWageHonus;
    }

    public File getMyFile() {
        return myFile;
    }

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
