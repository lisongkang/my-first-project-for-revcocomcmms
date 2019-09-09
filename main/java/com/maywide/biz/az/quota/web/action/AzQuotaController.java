package com.maywide.biz.az.quota.web.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.maywide.biz.az.quota.entity.ConstSettlement;
import com.maywide.biz.az.quota.pojo.AzSearchParamsBo;
import com.maywide.biz.az.quota.service.AzQuotaService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.action.FileUploadServlet;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;
import com.maywide.tool.excel.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 
 *<p> 
 *  定额配置模块
 *<p>
 * Create at 2019-4-8
 *
 *@autor lsk
 */
@Results({
		@Result(name = "success", location="/az/quota/az-quota-index.jsp")
})
public class AzQuotaController extends BaseController<ConstSettlement,Long>{
	private static final Logger log = LoggerFactory.getLogger(AzQuotaController.class);
	@Autowired
	private AzQuotaService azQuotaService;
	
	private AzSearchParamsBo azSearchParamsBo;  //搜索条件影射对象

	@Autowired
	private PersistentService persistentService;
	private ObjectMapper jsonMapper = null;
	private String savePath;
	private String imgDirPath;
	@Autowired
	private ParamService paramService;
	/**
	 * 全局使用变量
	 */
	private LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

	private ExcelUtil eu =new ExcelUtil();

	private File myFile;

	private String fileName;

	@Override
	protected BaseService<ConstSettlement, Long> getEntityService() {
		
		return azQuotaService;
	}
	@Override
	protected void checkEntityAclPermission(ConstSettlement entity) {
		// TODO Add acl check code logic
	}
	@MetaData("[TODO方法作用]")
	public HttpHeaders todo() {
		// TODO
		setModel(OperationResult.buildSuccessResult("TODO操作完成"));
		return buildDefaultHttpHeaders();
	}
	/**
	 * 删除定额信息
	 */
	@Override
	@MetaData("删除")
	public HttpHeaders doDelete() {
		return super.doDelete();
	}

	@Override
	@MetaData("保存")
	public HttpHeaders doSave() {
		Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
		String id = getParameter("id");
		if(id == null || id.equals("")){
			String newnumber = getParameter("newnumber");
			List<ConstSettlement> resultList = azQuotaService.getConstSettlementByNewnumber(newnumber);
			if(resultList.size() > 0){
				setModel(OperationResult.buildFailureResult("编号不能与数据库已有编号重复", errorMessageMap));
				return buildDefaultHttpHeaders("inputBasic");
			}else{
				return super.doSave();
			}
		}
		else {
			return super.doSave();
		}

	}

	/**
	 * 定额配置首页
	 */
	public String azindex(){
		return "success";
	}

	/**
	 * 分页搜索定额信息
	 */
	public HttpHeaders findByPage(){
		List<ConstSettlement> list = new ArrayList<ConstSettlement>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		String city = getParameter("AzSearchParamsBo.city");
		String newnumber = getParameter("newnumber");
		String constname = getParameter("constname");
		try{
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
			if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
				azSearchParamsBo = new AzSearchParamsBo();
				azSearchParamsBo.setCity(loginInfo.getCity());
			}
			setModel(azQuotaService.findByPage(azSearchParamsBo,pageable,city,newnumber,constname));
			
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<ConstSettlement>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
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
			List<ConstSettlement> list = new ArrayList<ConstSettlement>();
			for(Map.Entry<Integer, List<Row>> entry: mapData.entrySet()) {
				List<Row> rows = entry.getValue();
				for (int i = 0; i < rows.size(); i++) {
					if(entry.getKey()==0) {//第一个sheet工作空间
						String newnumber = eu.getCellValue(rows.get(i).getCell(0));
						if(newnumber==null|| newnumber.equals("")){
							break;
						}
						ConstSettlement constSettlement = new ConstSettlement();
						boolean tag=row2Data(rows.get(i), constSettlement,i+1);
						if(!tag) {continue;}
						if(checkExcelDataRepeat(list,constSettlement)){
							throw new Exception("导入失败，Excel数据存在重复,请检查第"+(i+1)+"行数据!");
						}
						list.add(constSettlement);
					}
				}
			}
			azQuotaService.save(list);
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

	/**
	 * excel行转Seller对象
	 * @param row
	 * @param
	 */
	private boolean row2Data(Row row, ConstSettlement constSettlement,int rowIndex) throws Exception{
		String city = null;
		String newnumber = eu.getCellValue(row.getCell(0));//新编号
		String oldnumber = eu.getCellValue(row.getCell(1));//原编号
		String constname = eu.getCellValue(row.getCell(2));//项目名称
		String constdetail = eu.getCellValue(row.getCell(3));//项目明细
		String company = eu.getCellValue(row.getCell(4));//单位
		Double oneprice = Double.parseDouble(eu.getCellValue(row.getCell(5)));//一 类价
		BigDecimal b = new BigDecimal(oneprice);
		oneprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double twoprice = Double.parseDouble(eu.getCellValue(row.getCell(6)));//二类价
		BigDecimal b2 = new BigDecimal(twoprice);
		twoprice = b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double threeprice = Double.parseDouble(eu.getCellValue(row.getCell(7)));//三类价
		BigDecimal b3 = new BigDecimal(threeprice);
		threeprice = b3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double fourprice = Double.parseDouble(eu.getCellValue(row.getCell(8)));//四类价
		BigDecimal b4 = new BigDecimal(fourprice);
		fourprice = b4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String constcontent = eu.getCellValue(row.getCell(9));//工作内容
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
			city = "*";
		}else {
			city = loginInfo.getCity();
		}
		//校验数据
		List<ConstSettlement> resultList = azQuotaService.getConstSettlementByNewnumber(newnumber);
		if(resultList.size() >0){
			throw new Exception("导入失败！新编号和数据库已有编号重复，请检查第"+rowIndex+"行数据！");
		}
		if(StringUtils.isBlank(newnumber) || StringUtils.isBlank(constcontent)
				|| StringUtils.isBlank(constname) ){
			throw new Exception("导入失败！【新编号】、【名称】、【工作内容】为必填项，请检查第"+rowIndex+"行数据是否存在空值！");
		}
		//插入对象
		constSettlement.setNewnumber(newnumber);
		constSettlement.setOldnumber(oldnumber);
		constSettlement.setCity(city);
		constSettlement.setCompany(company);
		constSettlement.setConstname(constname);
		constSettlement.setConstdetail(constdetail);
		constSettlement.setOneprice(oneprice);
		constSettlement.setTwoprice(twoprice);
		constSettlement.setThreeprice(threeprice);
		constSettlement.setFourprice(fourprice);
		constSettlement.setConstcontent(constcontent);
		return true;
	}


	/**
	 * 检查excel的网格是否有重复数据
	 * @return
	 */
	private boolean checkExcelDataRepeat(List<ConstSettlement> list,ConstSettlement constSettlement){
		for(ConstSettlement constSettlement1 : list){
			if(constSettlement1.getNewnumber().equals(constSettlement.getNewnumber())){
				return true;
			}
		}
		return false;
	}


	@MetaData("下载模板")
	public void downTemplate() {
		String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream ops = null;
		FileInputStream fis = null;
		try {
			String fileName = "施工定额导入模板.xls";
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
			String path =realPath+"/constSettlement.xls";

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

	public AzSearchParamsBo getAzSearchParamsBo() {
		return azSearchParamsBo;
	}
	public void setAzSearchParamsBo(AzSearchParamsBo azSearchParamsBo) {
		this.azSearchParamsBo = azSearchParamsBo;
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
