package com.maywide.biz.ass.target.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

@MetaData("[com.maywide].biz.ass.store.entity.AssTargetGridQuery管理")
public class AssTargetTogridQueryController extends BaseController<AssTargetTogrid,Long> {

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
		return buildDefaultHttpHeaders("query");
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
			
			List<AssTargetTogrid> list = new ArrayList<AssTargetTogrid>();
			for(Map.Entry<Integer, List<Row>> entry: mapData.entrySet()) {
				List<Row> rows = entry.getValue();
				for (int i = 0; i < rows.size(); i++) {
					if(entry.getKey()==0) {//第一个sheet
						
						AssTargetTogrid togrid = new AssTargetTogrid();
						boolean tag=row2Data(rows.get(i), togrid,i+1);
						if(!tag) continue;
						
						if(checkExcelDataRepeat(list,togrid)){
							throw new Exception("导入失败，Excel数据存在重复,【地市，指标，网格，考核期】必须唯一，请检查第"+(i+1)+"行数据!");
						}
						if(assTargetTogridService.checkDataExists(togrid)){
							throw new Exception("导入失败，将要导入的数据已存在，请检查第"+(i+1)+"行数据!");
						}
						
						list.add(togrid);
					}
				}
			}
			
			assTargetTogridService.save(list);
			
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
    
    /**
     * 检查excel的网格是否有重复数据
     * @return
     */
    private boolean checkExcelDataRepeat(List<AssTargetTogrid> list,AssTargetTogrid togrid){
    	for(AssTargetTogrid grid : list){
    		if(togrid.getCity().equals(grid.getCity()) && togrid.getAssId().equals(grid.getAssId()) 
    				&& togrid.getGridId().equals(grid.getGridId()) && togrid.getCycleNum().equals(grid.getCycleNum())){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
	 * excel行转Seller对象
	 * @param row
	 * @param seller
	 */
	private boolean row2Data(Row row, AssTargetTogrid toGrid,int rowIndex) throws Exception{
		
		String city = eu.getCellValue(row.getCell(0));//地市
		String gridName = eu.getCellValue(row.getCell(1));//网格
		String targetName = eu.getCellValue(row.getCell(2));//指标
		String cyclnum = eu.getCellValue(row.getCell(3));//考核期
		row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
		String targetValue = row.getCell(4).getStringCellValue();//目标值
		row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
		String currentValue = row.getCell(5).getStringCellValue();//当前值
		row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
		String finalGrade = row.getCell(6).getStringCellValue();//最终评分
		
		if(StringUtils.isBlank(city) && StringUtils.isBlank(gridName) 
				&& StringUtils.isBlank(targetName) && StringUtils.isBlank(cyclnum)){
			return false;
		}
		
		if(StringUtils.isBlank(city) || StringUtils.isBlank(gridName) 
				|| StringUtils.isBlank(targetName) || StringUtils.isBlank(cyclnum)){
			throw new Exception("导入失败！【地市】、【网格】、【指标】、【考核周期】为必填项，请检查第"+rowIndex+"行数据是否存在空值！");
		}
		
		if(!loginInfo.isAdmin() && !city.equals(loginInfo.getCity())){
			throw new Exception("导入失败！无法导入其他地市的指标数据，请检查第"+rowIndex+"行数据！");
		}
		
		//设置指标信息
		AssTargetStore store = checkCtiyTarget(targetName);
		if(store==null){
			throw new Exception("导入失败！指标不存在或者未获取到本市，请检查第"+rowIndex+"行数据！");
		}
		toGrid.setAssId(store.getId());
		
		//设置网格信息
		List<BizGridInfo> grids=gridInfoService.findByFilter(new PropertyFilter(MatchType.EQ, "gridname", gridName));
		if(grids.size()==0){
			throw new Exception("导入失败！无法找到网格信息，请检查第"+rowIndex+"行数据！");
		}
		else if(grids.size()>1){
			throw new Exception("导入失败！数据库存在多个网格信息，请检查第"+rowIndex+"行数据！");
		}
		else if(grids.size()==1){
			if(!city.equalsIgnoreCase(grids.get(0).getCity())){ //地市和网格必须对应
				throw new Exception("导入失败！无法在地市【"+city+"】找到网格【"+gridName+"】,请检查第"+rowIndex+"行数据！");
			}
			toGrid.setGridId(grids.get(0).getId());
		}
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			toGrid.setCycleNum(DateUtils.parseDate(getCyclNum(cyclnum), sdf));
		}catch(Exception e){
			throw new Exception("导入失败！指标考核期数据格式错误，请检查第"+rowIndex+"行数据！");
		}
		
		toGrid.setCity(city);
		toGrid.setAssNum(targetValue);
		toGrid.setCurrentValue(currentValue);
		toGrid.setStatus(0);
		toGrid.setIsDel(0);
		toGrid.setOptime(new Date());
		toGrid.setOperator(loginInfo.getOperid());
		//最终评分
		if(!StringUtils.isBlank(finalGrade)){
			toGrid.setFinalGrade(finalGrade);
		}
		
		return true;
	}
	
	/**
	 * 检查当前指标在toCity表是否存在，不存在则导入失败
	 * @param city
	 * @param target
	 * @return
	 */
	private AssTargetStore checkCtiyTarget(String target){
		
		if(cityTargets==null){
			try {
				PageImpl<AssTargetStore> targets = assTargetTocityService.getTargetTocity(new HashMap<String,String>(), new PageRequest(1, Integer.MAX_VALUE));
				cityTargets = targets.getContent();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(cityTargets!=null && !cityTargets.isEmpty()){
			for(AssTargetStore st : cityTargets){
				if(target.equalsIgnoreCase(st.getName())){
					return st;
				}
			}
		}
		
		return null;
	}
	
	private String getCyclNum(String value){
		
		String[] strs=value.split("/");
		String year=strs[0];
		String month="0"+strs[1];
		String day="01"; //如2018-04-01表示考核期为2018年4月
		
		return year+"-"+month+"-"+day;
	}
    
	@MetaData("下载模板")
	public void downTemplate() {

		String userAgent = ServletActionContext.getRequest().getHeader("User-Agent"); 
		HttpServletResponse response = ServletActionContext.getResponse();

		OutputStream ops = null;
		FileInputStream fis = null;
		try {
			String fileName = "指标数据模板.xls";
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
			String path =realPath+"/targetTemplate.xls";

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