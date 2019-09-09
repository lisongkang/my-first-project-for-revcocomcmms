package com.maywide.biz.ass.assdata.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.ass.assdata.pojo.achievements.AchievementBean;
import com.maywide.biz.ass.assdata.pojo.achievements.AchievenTmpBean;
import com.maywide.biz.ass.assdata.pojo.achievements.ResultBean;
import com.maywide.biz.ass.target.entity.AssTargetTogrid;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

public class UpdateAchievementsJob implements Job{

	static final Log log = LogFactory.getLog(UpdateAchievementsJob.class);
	
	static final int PAGESIZE = 10;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			Page<AchievementBean> page = getPageData(0, PAGESIZE);
			if(page.getResult() != null && !page.getResult().isEmpty()){
				handlerData(page);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	
	private Page<AchievementBean> getPageData(int currentPage,int pageSize) throws Exception{
		PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
		Page<AchievementBean> page = new Page();
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT * FROM (");
		sb.append("		SELECT a.TOGRID_ID recid, a.CITY city,a.CYCLENUM cyclenum,b.FIELDID fielid");
		sb.append("		,b.VISQL viSql,c.gridcode gridcode,b.ass_stt_cycle assDate");
		sb.append("		FROM ass_target_togrid a");
		sb.append("		INNER JOIN ass_target_store b");
		sb.append("		ON a.ASSID = b.ASSID");
		sb.append("		INNER JOIN biz_grid_info c");
		sb.append("		ON a.GRIDID = c.gridid");
		sb.append("		AND a.STATUS = 1");
		sb.append("		UNION ALL ");
		sb.append("		SELECT a1.TOGRID_ID recid,a1.CITY city,a1.CYCLENUM cyclenum,b1.FIELDID fielid");
		sb.append("		,b1.VISQL viSql,c1.gridcode gridcode,b1.ass_stt_cycle assDate ");
		sb.append("		FROM ass_target_togrid a1 ");
		sb.append("		INNER JOIN ass_target_store b1");
		sb.append("		ON a1.ASSID = b1.ASSID ");
		sb.append("		AND b1.CITY = '*' ");
		sb.append("		INNER JOIN biz_grid_info c1 ");
		sb.append("		ON a1.GRIDID = c1.gridid ");
		sb.append("		AND a1.STATUS = 1");
		sb.append("	) t");
		page = persistentService.find(page, sb.toString(),AchievementBean.class);
		return page;
	}
	
	private void handlerData(Page<AchievementBean> page) throws Exception{
		List<AchievementBean> fieldList = null;
		List<AchievementBean> viSqlList = null;
		for(AchievementBean data:page.getResult()){
			if(data == null){
				continue;
			}
			if(StringUtils.isNotBlank(data.getFielid())){
				if(fieldList == null){
					fieldList = new ArrayList<AchievementBean>();
				}
				fieldList.add(data);
			}
			if(StringUtils.isNotBlank(data.getViSql())){
				if(viSqlList == null){
					viSqlList = new ArrayList<AchievementBean>();
				}
				viSqlList.add(data);
			}
		}
		if(fieldList != null){
			queFieldDataAndUpdate(fieldList);
		}
		if(viSqlList != null){
			queViSqlDataAndUpdate(viSqlList);
		}
		boolean hasNext = page.isHasNext();
		int nextPage = page.getNextPage();
		if(hasNext){
			page = getPageData(nextPage, PAGESIZE);
			handlerData(page);
		}
	}
	
	private void queFieldDataAndUpdate(List<AchievementBean> list) throws Exception{
		if(list == null || list.isEmpty()){
			return;
		}
		PersistentService persistentService = SpringBeanUtil.getPersistentService();
		Map<Long, AchievenTmpBean> map = null;
		for(AchievementBean bean:list){
			try{
				StringBuffer sb = new StringBuffer();
				List params = new ArrayList();
				sb.append("		select distinct (a.kpiid) kpiid, a.kpivalue,a.city,a.STADATE kpidate");
				sb.append("		from DM.DM_KPI_WHGRID a ");
				sb.append("		where a.city = ?");
				params.add(bean.getCity());
				sb.append("		and a.kpiid = ?");
				params.add(bean.getFielid());
				sb.append("		and a.whgridcode = ?");
				params.add(bean.getGridcode());
				/*sb.append("		and a.areaid = ?");
				params.add("NNN");*/
				if(bean.getAssDate().intValue() == 0){//按日查询
					/*sb.append("		and a.monthtype = ?");
					params.add("NNN");*/
					sb.append("		and a.stadate = ");
					sb.append("		( select max(distinct(t.stadate)) from DM.DM_KPI_WHGRID t");
					sb.append("		where t.stadate between ? and ?)");
					params.add(DateUtils.getMonthFirst(0, bean.getCyclenum()));
					params.add(DateUtils.getMonthLastest(0, bean.getCyclenum()));
					sb.append("		and rownum <= 1");
				}else if(bean.getAssDate().intValue() == 2){//按月查询
					/*sb.append("		and a.stadate = ?");
					params.add("NNN");*/
					sb.append("		and a.monthtype = ?");
					params.add(DateUtils.formatDate(bean.getCyclenum(), DateUtils.FORMAT_YYYYMM));
					sb.append("		 and rownum <= 1");
				}
				
				List<ResultBean> beans = persistentService.find(sb.toString(), ResultBean.class, params.toArray());
				if(beans == null || beans.isEmpty() 
						|| beans.get(0) == null || StringUtils.isBlank(beans.get(0).getKpiid())){
					continue;
				}
				if(map == null){
					map = new HashMap<Long, AchievenTmpBean>();
				}
				AchievenTmpBean tmpBean = new AchievenTmpBean(beans.get(0).getKpivalue(), beans.get(0).getKpidate());
				map.put(bean.getRecid(), tmpBean);
			}catch(Exception e){
				log.error(e.getMessage());
				continue;
			}
		}
		updatePrimary(map);
	}
	
	private void updatePrimary(Map<Long, AchievenTmpBean> map){
		if(map == null || map.isEmpty()){
			return;
		}
		PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
		Iterator<Entry<Long, AchievenTmpBean>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			try{
				Entry<Long, AchievenTmpBean> entry = iterator.next();
				Long id = entry.getKey();
				AchievenTmpBean value = entry.getValue();
				if(id == null || StringUtils.isBlank(value.getKpivalue())){
					continue;
				}
				AssTargetTogrid  togrid = new AssTargetTogrid();
				togrid.setId(id);
				List<AssTargetTogrid> datas = persistentService.find(togrid);
				if(datas == null || datas.isEmpty() || datas.get(0) == null){
					continue;
				}
				togrid = datas.get(0);
				togrid.setCurrentValue(value.getKpivalue());
				togrid.setKpidate(value.getKpidate());
				togrid.setOptimeValue(new Date());
				persistentService.update(togrid);
			}catch(Exception e){
				log.error(e.getMessage());
				continue;
			}
		}
		try {
			persistentService.flushSession();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	
	private void queViSqlDataAndUpdate(List<AchievementBean> list) throws Exception{
		
	}
	
}
