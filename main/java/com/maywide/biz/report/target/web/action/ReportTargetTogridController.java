package com.maywide.biz.report.target.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.maywide.biz.ass.target.bo.AssTargetPatchBo;
import com.maywide.biz.ass.target.entity.AssTargetStore;
import com.maywide.biz.ass.target.entity.AssTargetTogrid;
import com.maywide.biz.ass.target.service.AssTargetStoreService;
import com.maywide.biz.ass.target.service.AssTargetTocityService;
import com.maywide.biz.ass.target.service.AssTargetTogridService;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.view.OperationResult;
import com.maywide.tool.excel.ExcelUtil;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

@MetaData("[com.maywide].biz.ass.store.entity.AssTargetGridQuery管理")
public class ReportTargetTogridController extends BaseController<AssTargetTogrid,Long> {

	@Autowired
    private AssTargetStoreService assTargetStoreService;
	
	@Autowired
    private AssTargetTocityService assTargetTocityService;
	
    @Autowired
    private AssTargetTogridService assTargetTogridService;
    
    @Autowired
    private GridInfoService gridInfoService;
    
    @Autowired
    private PersistentService persistentService;
    
    
    /**
     * 全局使用变量
     */
    private LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    
    private ExcelUtil eu =new ExcelUtil();
	
    private File myFile;
    
    private String fileName;
    
    private AssTargetPatchBo assTargetPatchBo;
    
    private List<AssTargetStore> cityTargets=null;//保存地市指标，用于导入时判定
    
   

	@Override
    protected BaseService<AssTargetTogrid, Long> getEntityService() {
        return assTargetTogridService;
    }
    
    @Override
    protected void checkEntityAclPermission(AssTargetTogrid entity) {
        // TODO Add acl check code logic
    }

	public HttpHeaders index(){
		return buildDefaultHttpHeaders("togrid");
	}
	
	
    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        //TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }
    
    
    @Override
    @MetaData("创建")
    public HttpHeaders doCreate() {
        return super.doCreate();
    }

    @Override
    @MetaData("更新")
    public HttpHeaders doUpdate() {
        return super.doUpdate();
    }
    
    /**
     * 进入导入指标Excel页面
     * @author:liaoxiangjun
     * @return
     */
    public HttpHeaders toImportPage(){
		return buildDefaultHttpHeaders("import");
	}
    
    
   
    
    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
		try {
			PageImpl<AssTargetPatchBo> pageResult = assTargetTogridService
					.getTargetTogrid(this.assTargetPatchBo, pageable);
			setModel(pageResult);

			return buildDefaultHttpHeaders("query");

		} catch (Exception e) {
			List<AssTargetPatchBo> list = new ArrayList<AssTargetPatchBo>();
			setModel(new PageImpl<AssTargetPatchBo>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("query");
	
    }
    
    
    public AssTargetPatchBo getAssTargetPatchBo() {
		return assTargetPatchBo;
	}

	public void setAssTargetPatchBo(AssTargetPatchBo assTargetPatchBo) {
		this.assTargetPatchBo = assTargetPatchBo;
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
    
	
/**
 * 修改模板后再下载，相关demo    
 */
//public static void main(String[] args) {
//    	
//		WritableWorkbook workbook=null;
//    	try{
//    		Workbook wb = Workbook.getWorkbook(new File("D:\\worksp1\\demo.xls"));
//    		
//			workbook = Workbook.createWorkbook(new File("D:\\worksp1\\demo1.xls"),wb);
//			
//			WritableSheet sheet=workbook.getSheet(0);
//			
//			Label lblColumn  = new Label(3, 1, "请选择");//生成一个待选择的标签
//	
//	         WritableCellFeatures wcf2 = new WritableCellFeatures();//待选择集合对象，这是jxl的对象
//	
//	         List angerlist = new ArrayList();
//	
//	         angerlist.add("电话");
//	
//	         angerlist.add("手机");
//	
//	         angerlist.add("呼机");
//	
//	         wcf2.setDataValidationList(angerlist);//设置jxl对象要选择的集合
//	
//	         lblColumn.setCellFeatures(wcf2);//设置到单元格里面去
//	
//	         sheet.addCell(lblColumn);//加入sheet表格中
//	         
//	         workbook.write();
//	         workbook.close();
//	         
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//	}
    
//    public static void main(String[] args) {
//    	
//    	try{
//	    	File xlsFile = new File("D:\\worksp1\\demo1.xls");
//
//			WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
//			WritableSheet sheet=workbook.createSheet("s1", 1);
//			Label lblColumn  = new Label(3, 1, "请选择");//生成一个待选择的标签
//	
//	         WritableCellFeatures wcf2 = new WritableCellFeatures();//待选择集合对象，这是jxl的对象
//	
//	         List angerlist = new ArrayList();
//	
//	         angerlist.add("电话");
//	
//	         angerlist.add("手机");
//	
//	         angerlist.add("呼机");
//	
//	         wcf2.setDataValidationList(angerlist);//设置jxl对象要选择的集合
//	
//	         lblColumn.setCellFeatures(wcf2);//设置到单元格里面去
//	
//	         sheet.addCell(lblColumn);//加入sheet表格中
//	         
//	         workbook.write();
//	         workbook.close();
//	         
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//	}
    
}