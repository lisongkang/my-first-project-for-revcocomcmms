package com.maywide.biz.salary.web.action;

import com.maywide.biz.ass.target.entity.AssTargetTogrid;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.salary.entity.ExplicationConfig;
import com.maywide.biz.salary.entity.OthersKpiTextConfig;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.pubpost.service.AttachmentFileService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;
import com.maywide.tool.excel.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 *
 *<p>
 *  广告配置模块
 *<p>
 */
public abstract class SalaryController<T extends PersistableEntity<ID>, ID extends Serializable>
        extends BaseController<T,ID>{

    @Override
    protected abstract BaseService<T, ID> getEntityService();

    protected ExcelUtil eu =new ExcelUtil();

    private File myFile;
    /**
     * 新增修改页
     * @return
     */
    public HttpHeaders edit(){
        if(StringUtils.isNotEmpty(bindingEntity.getDisplay())) {
            String[] areaids = bindingEntity.getDisplay().split(",");
            bindingEntity.addExtraAttribute("areaids", areaids);
        }else{
            bindingEntity.addExtraAttribute("areaids", "*");
        }
        return buildDefaultHttpHeaders("inputBasic");
    }


    /**
     * 删除
     */
    public HttpHeaders doDelete() {
        Collection<T> entities = this.getEntitiesByParameterIds();
        for (T entity : entities) {
            getEntityService().delete(entity);
        }
        setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + entities.size() + "条"));
        return buildDefaultHttpHeaders();
    }

    public Map<String, String> getCityMap(){
        Map<String, String> cityMap =  new LinkedHashMap();
        cityMap.put("*", "所有");
        cityMap.putAll(getParamByGcode("PRV_CITY",null));
        return cityMap;
    }


    public Map<String, String> getAreaMap() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        Map<String, String> areaMap = new LinkedHashMap();
        areaMap.put("*", "所有");
        areaMap.putAll(getParamByGcode("PRV_AREA_BY_CITY",loginInfo.getCity()));
        return areaMap;
    }

    public Map<String, String> getAreaMap2() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        return getParamByGcode("PRV_AREA_BY_CITY",loginInfo.getCity());
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
    public void othersTemplate() {
        String templateFileName = getParameter("templateFileName");
        if(StringUtils.isEmpty(templateFileName)){
            return;
        }
        String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream ops = null;
        FileInputStream fis = null;
        try {
            String fileName = "导入模板.xls";
            // 针对IE或者以IE为内核的浏览器：
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")|| userAgent.contains("Edge")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
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
            if(mapData.size()<1)
                throw new Exception("导入失败，在文档中未检测到数据，请检查文档是否存在数据！");

            List<T> list = new ArrayList<T>();
            for(Map.Entry<Integer, List<Row>> entry: mapData.entrySet()) {
                List<Row> rows = entry.getValue();
                for (int i = 0; i < rows.size(); i++) {
                    if(entry.getKey()==0) {//第一个sheet
                        T entity=bulidExcelEntity(rows.get(i),list,i+1);
                        if(entity == null) continue;
                        list.add(entity);
                    }
                }
            }
            //数据入库
            excelSave(list);
            result=OperationResult.buildSuccessResult("Excel数据导入成功");
        }
        catch (IOException e) {
            //e.printStackTrace();
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
    ///excel数据入库
    public void excelSave(List<T> list) throws Exception{
        getEntityService().save(list);
    }

    public File getMyFile() {
        return myFile;
    }

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }
}
