package com.maywide.biz.salary.service;

import com.maywide.biz.salary.dao.BaseWageDao;
import com.maywide.biz.salary.entity.AchievementBonus;
import com.maywide.biz.salary.entity.BaseWage;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *<p> 
 *  基本薪酬
 *<p>
 *
 */
@Service
@Transactional
public class BaseWageService extends BaseService<BaseWage,Long>{


	@Autowired
	private BaseWageDao baseWageDao;

	@Autowired
	private PersistentService persistentService;
	@Autowired
	private AchievementBonusService achievementBonusService;
	@Override
	protected BaseDao<BaseWage, Long> getEntityDao() {
		return baseWageDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<BaseWage> findByPage(BaseWage baseWage,
			Pageable pageable) throws Exception {
		PageImpl<BaseWage> pageResult = null;
        Page<BaseWage> page = new Page<BaseWage>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("select t1.id,t2.id achievementId,t1.city,t1.areaid,t1.grid,t1.operid,t2.date_month dateMonth,t2.amount achievementAmount,t1.amount ");
		sql.append("from salary_base_wage t1 left join salary_achievement_bonus t2 on t1.operid = t2.operid where 1=1 ");
        if(baseWage!=null){
			if(StringUtils.isNotEmpty(baseWage.getCity())){
				sql.append(" and t1.city=?");
				paramList.add(baseWage.getCity());
			}
			if(StringUtils.isNotEmpty(baseWage.getAreaid())){
				sql.append(" and t1.areaid=?");
				paramList.add(baseWage.getAreaid());
			}
			if(StringUtils.isNotEmpty(baseWage.getGrid())){
				sql.append(" and t1.grid=?");
				paramList.add(baseWage.getGrid());
			}
			if(StringUtils.isNotEmpty(baseWage.getDateMonth())){
				sql.append(" and t2.date_month=?");
				paramList.add(baseWage.getDateMonth());
			}
			if(baseWage.getOperid()!=null){
				sql.append(" and t1.operid=?");
				paramList.add(baseWage.getOperid());
			}
        }
        sql.append(" order by date_month desc");

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
		List<BaseWage> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<BaseWage>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<BaseWage>(new ArrayList<BaseWage>(), pageable, 0);
		}
        return pageResult;
	}

	public Boolean exists(String grid,Long operid) throws Exception {
		if(StringUtils.isEmpty(grid) || operid==null){
			throw new Exception("参数不完整,无法校验!");
		}
		String sql="select 1 from salary_base_wage where grid=? and operid=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(grid);
		paramList.add(operid);
		Long count = persistentService.count(sql.toString(), paramList.toArray());
		if(count>0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 基本薪酬，运维薪酬查询
	 * @param operid
	 * @param dateMonth
	 * @return
	 * @throws Exception
	 */
	public JSONArray baseWage(String operid,String dateMonth) throws Exception{
		JSONArray result = new JSONArray();
		if(StringUtils.isEmpty(operid) || StringUtils.isEmpty(dateMonth)){
			throw new Exception("网格人员id和月份不能为空!");
		}
		//查询基本薪酬
		BaseWage bw = new BaseWage();
		String hql=" from BaseWage where operid=? and stime<=now() and etime>=now()";
		List<BaseWage> baseWage = persistentService.find(hql,Long.valueOf(operid));
		if(baseWage!=null && baseWage.size()>0){
			bw = baseWage.get(0);
		}
		//运维薪酬
		AchievementBonus ab = new AchievementBonus();
		String sql="select * from salary_achievement_bonus where operid=? and date_month=?";
		List<AchievementBonus> abs= persistentService.find(sql,AchievementBonus.class,operid,dateMonth);
		if(abs!=null && abs.size()>0){
			ab = abs.get(0);
		}
		//查询其他积分的运维薪酬
		String kpiSql = "select c.context textConfigName,k.score,c.remark from salary_others_kpi k,salary_others_kpi_text_config c where " +
				"k.text_config_id = c.id and k.status='2' and c.type='YWXC' and k.operid=? and k.date_month=? order by c.rank desc";
		List<OthersKpi> kpiList = persistentService.find(kpiSql,OthersKpi.class,operid,dateMonth);

		//-----------------------------组织返回json---------------------------------

		//运维薪酬部分
		JSONObject abJson = new JSONObject();
		abJson.put("achievementBonusAmount",ab.getAmount());
		JSONArray kpiArr = new JSONArray();
		JSONObject kpiJson = null;
		if(kpiList!=null && kpiList.size()>0) {
			for (OthersKpi othersKpi : kpiList) {
				kpiJson = new JSONObject();
				kpiJson.put("key", othersKpi.getTextConfigName());
				kpiJson.put("value", othersKpi.getScore());
				kpiJson.put("remark", othersKpi.getRemark());
				kpiArr.put(kpiJson);
			}
		}
		abJson.put("kpi",kpiArr);
		result.put(abJson); // 运维薪酬
		//基本薪酬部分
		JSONObject bwJson = new JSONObject();
		bwJson.put("amount",bw.getAmount());
		bwJson.put("position",bw.getPosition());
		bwJson.put("positionLevel",bw.getPositionLevel());
		bwJson.put("level",bw.getLevel());
		result.put(bwJson); //基本薪酬
		return result;
	}
}
