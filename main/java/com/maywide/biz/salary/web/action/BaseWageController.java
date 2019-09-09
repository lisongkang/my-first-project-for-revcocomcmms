package com.maywide.biz.salary.web.action;

import com.maywide.biz.ass.target.entity.AssTargetTogrid;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.entity.AchievementBonus;
import com.maywide.biz.salary.entity.BaseWage;
import com.maywide.biz.salary.service.AchievementBonusService;
import com.maywide.biz.salary.service.BaseWageService;
import com.maywide.common.web.action.AreaController;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 *<p> 
 *  绩效积分说明
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
public class BaseWageController extends SalaryController<BaseWage,Long>{

	@Autowired
	private BaseWageService baseWageService;
	@Autowired
	private AchievementBonusService achievementBonusService;
	@Autowired
	private GridInfoService gridInfoService;

	@Autowired
	private PersistentService persistentService;

	private BaseWage baseWage;  //搜索影射对象

	@Override
	protected BaseService<BaseWage, Long> getEntityService() {
		
		return baseWageService;
	}
	/**
	 * 新增修改页
	 * @return
	 */
	public HttpHeaders edit(){
		String achievementId = getParameter("achievementId");
		try {
			if(bindingEntity.getId()!=null && StringUtils.isNotEmpty(achievementId)) {
				AchievementBonus bean = (AchievementBonus) persistentService.find(AchievementBonus.class, Long.valueOf(achievementId));
				if(bean!=null) {
					bindingEntity.setAchievementAmount(bean.getAmount());
					bindingEntity.setDateMonth(bean.getDateMonth());
					bindingEntity.setAchievementId(Long.valueOf(achievementId));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return buildDefaultHttpHeaders();
		}
		return buildDefaultHttpHeaders("inputBasic");
	}
	/**
	 * 分页搜索
	 */
	public HttpHeaders findByPage(){
		List<BaseWage> list = new ArrayList<BaseWage>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
			if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
				baseWage.setCity(loginInfo.getCity());
			}
			setModel(baseWageService.findByPage(baseWage,pageable));
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<BaseWage>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}

	/**
	 * 新增或者修改
	 * @return
	 */
	@Override
	public HttpHeaders doSave(){
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		try{
			if(null==getId()){
				boolean exist = false;
				if(SalaryConstants.BaseWage.BASE_WAGE_TYPE.equals(bindingEntity.getType())){
					exist = baseWageService.exists(bindingEntity.getGrid(),bindingEntity.getOperid());
				}
				if(SalaryConstants.BaseWage.ACHIEVEMENT_TYPE.equals(bindingEntity.getType())){
					exist = achievementBonusService.exists(bindingEntity.getGrid(),bindingEntity.getOperid(),bindingEntity.getDateMonth());
				}
				if(exist) {
					setModel(OperationResult.buildFailureResult("保存失败，该条数据已存在无法重复创建!", bindingEntity));
					return buildDefaultHttpHeaders();
				}
				bindingEntity.setCreateAt(new Date());
				bindingEntity.setCreateBy(loginInfo.getLoginname());
			}
			bindingEntity.setUpdateAt(new Date());
			bindingEntity.setUpdateBy(loginInfo.getLoginname());
			if(SalaryConstants.BaseWage.BASE_WAGE_TYPE.equals(bindingEntity.getType())) {
				getEntityService().save(bindingEntity);
			}else{
				AchievementBonus ab = new AchievementBonus();
				BeanUtils.copyProperties(bindingEntity,ab);
				ab.setId(bindingEntity.getAchievementId());
				ab.setAmount(bindingEntity.getAchievementAmount());
				achievementBonusService.save(ab);
			}
			setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
		}catch(Exception e){
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult("保存操作失败"));
		}

		return buildDefaultHttpHeaders();
	}


	public Map<String, String> getTypeMap(){
		return getParamByGcode("SALARY_EXPLICATION_TYPE",null);
	}
	/**
	 * 批量导入row转实体类
	 * @param row
	 * @param rowList
	 * @param rowIndex
	 * @return
	 */
	@Override
	public BaseWage bulidExcelEntity(Row row, List<BaseWage> rowList, int rowIndex) throws Exception{
		BaseWage bw = new BaseWage();
		String city = eu.getCellValue(row.getCell(0));//地市
		String areaName = eu.getCellValue(row.getCell(1));//业务区名称
		String gridName = eu.getCellValue(row.getCell(2));//网格名称
		String account = eu.getCellValue(row.getCell(3));//网格人员账号
		String position = eu.getCellValue(row.getCell(4));//岗位
		String positionLevel = eu.getCellValue(row.getCell(5));//职级
		String level = eu.getCellValue(row.getCell(6));//档次
		String amount = eu.getCellValue(row.getCell(7));//基本薪酬
		String stime = eu.getCellValue(row.getCell(8));//生效时间
		String etime = eu.getCellValue(row.getCell(9));//失效时间

		if(StringUtils.isBlank(city) && StringUtils.isBlank(areaName)
				&& StringUtils.isBlank(gridName) && StringUtils.isBlank(account)){
			return null;
		}

		if(StringUtils.isBlank(city) || StringUtils.isBlank(areaName)
				|| StringUtils.isBlank(gridName) || StringUtils.isBlank(account)
				|| StringUtils.isBlank(amount)){
			throw new Exception("导入失败！【地市】、【支公司】、【网格】、【网格人员】、【基本薪酬】为必填项，请检查第"+rowIndex+"行数据是否存在空值！");
		}

		if(!AuthContextHolder.getLoginInfo().isAdmin() && !city.equals(AuthContextHolder.getLoginInfo().getCity())){
			throw new Exception("导入失败！无法导入其他地市的指标数据，请检查第"+rowIndex+"行数据！");
		}
		bw.setCity(city);
		bw.setAmount(new BigDecimal(amount));
		bw.setStime(DateUtils.parseDate(stime));
		bw.setEtime(DateUtils.parseDate(etime));
		bw.setPosition(position);
		bw.setPositionLevel(positionLevel);
		bw.setLevel(level);
		bw.setCreateAt(new Date());
		bw.setCreateBy(AuthContextHolder.getLoginInfo().getLoginname());
		bw.setUpdateAt(new Date());
		bw.setUpdateBy(AuthContextHolder.getLoginInfo().getLoginname());

		//设置支公司信息
		List<PrvArea> areas = persistentService.findByProperty(PrvArea.class,"name",areaName);
		if(areas.size()==0 || areas.size()>1){
			throw new Exception("导入失败！无法找到支公司信息，请检查第"+rowIndex+"行数据！");
		} else{
			if(!city.equalsIgnoreCase(areas.get(0).getCity())){ //地市和网格必须对应
				throw new Exception("导入失败！无法在地市【"+city+"】找到支公司【"+areaName+"】,请检查第"+rowIndex+"行数据！");
			}
			bw.setAreaid(areas.get(0).getId()+"");
		}
		//设置网格信息
		List<BizGridInfo> grids=gridInfoService.findByFilter(new PropertyFilter(PropertyFilter.MatchType.EQ, "gridname", gridName));
		if(grids.size()==0 || grids.size()>1){
			throw new Exception("导入失败！无法找到网格信息，请检查第"+rowIndex+"行数据！");
		} else{
			if(!city.equalsIgnoreCase(grids.get(0).getCity())){ //地市和网格必须对应
				throw new Exception("导入失败！无法在地市【"+city+"】找到网格【"+gridName+"】,请检查第"+rowIndex+"行数据！");
			}
			bw.setGrid(grids.get(0).getId()+"");
		}
		//设置网格人员信息
		List<PrvOperator> operids=persistentService.findByProperty(PrvOperator.class,"loginname",account);
		if(operids.size()==0 || operids.size()>1){
			throw new Exception("导入失败！无法找到网格人员信息，请检查第"+rowIndex+"行数据！");
		} else{
			bw.setOperid(operids.get(0).getId());
		}
		//查看excel是否有重复数据
		for(BaseWage baseWage : rowList){
			if(bw.getCity().equals(baseWage.getCity()) && bw.getAreaid().equals(baseWage.getAreaid())
					&& bw.getGrid().equals(baseWage.getGrid()) && bw.getOperid().longValue()==baseWage.getOperid()){
				throw new Exception("导入失败，Excel数据存在重复,【地市，指标，网格，考核期】必须唯一，请检查第"+rowIndex+"行数据!");
			}
		}
		//查看数据库是否存在该条记录
		long count = persistentService.count("select 1 from salary_base_wage where city=? and areaid=? and grid=? and operid=?",
				bw.getCity(),bw.getAreaid(),bw.getGrid(),bw.getOperid());
		if(count>0){
			throw new Exception("导入失败，将要导入的数据已存在，请检查第"+rowIndex+"行数据!");
		}
		return  bw;
	}

	public BaseWage getBaseWage() {
		return baseWage;
	}

	public void setBaseWage(BaseWage baseWage) {
		this.baseWage = baseWage;
	}
}
