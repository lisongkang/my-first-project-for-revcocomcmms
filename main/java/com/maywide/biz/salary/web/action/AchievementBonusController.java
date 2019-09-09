package com.maywide.biz.salary.web.action;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.entity.AchievementBonus;
import com.maywide.biz.salary.entity.BaseWage;
import com.maywide.biz.salary.service.AchievementBonusService;
import com.maywide.biz.salary.service.BaseWageService;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 *<p> 
 *  绩效积分说明
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
public class AchievementBonusController extends SalaryController<AchievementBonus,Long>{

	@Autowired
	private AchievementBonusService achievementBonusService;
	@Autowired
	private PersistentService persistentService;
	@Autowired
	private GridInfoService gridInfoService;

	private AchievementBonus achievementBonus;  //搜索影射对象

	@Override
	protected BaseService<AchievementBonus, Long> getEntityService() {
		
		return achievementBonusService;
	}

	/**
	 * 批量导入row转实体类
	 * @param row
	 * @param rowList
	 * @param rowIndex
	 * @return
	 */
	@Override
	public AchievementBonus bulidExcelEntity(Row row, List<AchievementBonus> rowList, int rowIndex) throws Exception{
		AchievementBonus ab = new AchievementBonus();
		String city = eu.getCellValue(row.getCell(0));//地市
		String areaName = eu.getCellValue(row.getCell(1));//业务区名称
		String gridName = eu.getCellValue(row.getCell(2));//网格名称
		String account = eu.getCellValue(row.getCell(3));//网格人员账号
		String dateMonth = eu.getCellValue(row.getCell(4));//月份
		String amount = eu.getCellValue(row.getCell(5));//运维薪酬

		if(StringUtils.isBlank(city) && StringUtils.isBlank(areaName)
				&& StringUtils.isBlank(gridName) && StringUtils.isBlank(account)){
			return null;
		}

		if(StringUtils.isBlank(city) || StringUtils.isBlank(areaName)
				|| StringUtils.isBlank(gridName) || StringUtils.isBlank(account)
				|| StringUtils.isBlank(dateMonth) || StringUtils.isBlank(amount)){
			throw new Exception("导入失败！【地市】、【支公司】、【网格】、【网格人员】、【月份】、【运维薪酬】为必填项，请检查第"+rowIndex+"行数据是否存在空值！");
		}

		if(!AuthContextHolder.getLoginInfo().isAdmin() && !city.equals(AuthContextHolder.getLoginInfo().getCity())){
			throw new Exception("导入失败！无法导入其他地市的指标数据，请检查第"+rowIndex+"行数据！");
		}
		ab.setCity(city);
		ab.setAmount(new BigDecimal(amount));
		ab.setDateMonth(dateMonth);
		ab.setCreateAt(new Date());
		ab.setCreateBy(AuthContextHolder.getLoginInfo().getLoginname());
		ab.setUpdateAt(new Date());
		ab.setUpdateBy(AuthContextHolder.getLoginInfo().getLoginname());

		//设置支公司信息
		List<PrvArea> areas = persistentService.findByProperty(PrvArea.class,"name",areaName);
		if(areas.size()==0 || areas.size()>1){
			throw new Exception("导入失败！无法找到支公司信息，请检查第"+rowIndex+"行数据！");
		} else{
			if(!city.equalsIgnoreCase(areas.get(0).getCity())){ //地市和网格必须对应
				throw new Exception("导入失败！无法在地市【"+city+"】找到支公司【"+areaName+"】,请检查第"+rowIndex+"行数据！");
			}
			ab.setAreaid(areas.get(0).getId()+"");
		}
		//设置网格信息
		List<BizGridInfo> grids=gridInfoService.findByFilter(new PropertyFilter(PropertyFilter.MatchType.EQ, "gridname", gridName));
		if(grids.size()==0 || grids.size()>1){
			throw new Exception("导入失败！无法找到网格信息，请检查第"+rowIndex+"行数据！");
		} else{
			if(!city.equalsIgnoreCase(grids.get(0).getCity())){ //地市和网格必须对应
				throw new Exception("导入失败！无法在地市【"+city+"】找到网格【"+gridName+"】,请检查第"+rowIndex+"行数据！");
			}
			ab.setGrid(grids.get(0).getId()+"");
		}
		//设置网格人员信息
		List<PrvOperator> operids=persistentService.findByProperty(PrvOperator.class,"loginname",account);
		if(operids.size()==0 || operids.size()>1){
			throw new Exception("导入失败！无法找到网格人员信息，请检查第"+rowIndex+"行数据！");
		} else{
			ab.setOperid(operids.get(0).getId());
		}
		//查看excel是否有重复数据
		for(AchievementBonus bean : rowList){
			if(ab.getCity().equals(bean.getCity()) && ab.getAreaid().equals(bean.getAreaid())
					&& ab.getGrid().equals(bean.getGrid()) && ab.getOperid().longValue()==bean.getOperid()
					&& ab.getDateMonth().equals(bean.getDateMonth()) ){
				throw new Exception("导入失败，Excel数据存在重复,【地市，指标，网格，考核期】必须唯一，请检查第"+rowIndex+"行数据!");
			}
		}
		//查看数据库是否存在该条记录
		String sql="select 1 from salary_achievement_bonus where city=? and areaid=? and grid=? and operid=? and date_month=?";
		long count = persistentService.count(sql, ab.getCity(),ab.getAreaid(),ab.getGrid(),ab.getOperid(),ab.getDateMonth());
		if(count>0){
			throw new Exception("导入失败，将要导入的数据已存在，请检查第"+rowIndex+"行数据!");
		}
		return  ab;
	}
	public AchievementBonus getAchievementBonus() {
		return achievementBonus;
	}

	public void setAchievementBonus(AchievementBonus achievementBonus) {
		this.achievementBonus = achievementBonus;
	}
}
