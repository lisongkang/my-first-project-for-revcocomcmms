package com.maywide.biz.ass.assdata.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.ass.assdata.entity.GridTargetDataBean;
import com.maywide.biz.ass.assdata.pojo.griddata.GridBean;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.manager.UserTargetManager;
import com.maywide.biz.inter.pojo.dataReport.DataTarget;
import com.maywide.biz.inter.pojo.dataReport.TargetListResp;
import com.maywide.biz.inter.pojo.queSatis.SatisInfo;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

public class GridDataAssJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

	}
	
	
	private void queAllCityGrids(PersistentService DAO) throws Exception{
		long time1 = System.currentTimeMillis();
		String sql = "select DISTINCT(a.gridcode),a.city FROM biz_grid_info  a WHERE a.gtype = ? ORDER BY city";
		List<GridBean> gridList = DAO.find(sql, GridBean.class, "2");
		if(gridList != null && !gridList.isEmpty()){
			for(GridBean bean:gridList){
				queTargetDataAndSave(bean.getCity(), bean.getGridcode(), DAO);
			}
		}
		long time2 = System.currentTimeMillis() - time1;
		System.out.println("定时任务耗时==========="+time2);
	}
	
	
	/**
	 * 查询日用户指标,并保存到数据库中
	 * @param city
	 * @param gridcode
	 * @throws Exception 
	 */
	private void queTargetDataAndSave(String city,String gridcode,PersistentService DAO) throws Exception{
		Date date = getPreDay(new Date());
		String time = DateUtils.formatDate(date, DateUtils.FORMAT_YYYYMMDD);
		TargetListResp resp = new TargetListResp();
		UserTargetManager.getInstance().queUserKpis(city, 
				gridcode, "D", time,null, DAO,resp);
		List<DataTarget> tagertList = resp.getDatas();
		if(tagertList != null && !tagertList.isEmpty()){
			for(DataTarget target:tagertList){
				GridTargetDataBean bean = new GridTargetDataBean(target, time);
				DAO.save(bean);
			}
		}
	}
	
	
	private void queSatisAndSave(String gridcode,String city) throws Exception{
		Date date = getPreDay(new Date());
		String time = DateUtils.formatDate(date, DateUtils.FORMAT_YYYYMMDD);
		List params = new ArrayList();
		params.add(time);
		params.add(gridcode);
		params.add(city);
		params.add("0");
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT etl.code2name(A.CITY,'PRV_CITY')CITY, A.WHGRIDCODE gridCode,");
		sb.append("		A.WHGRIDNAME,A.Flag flag,etl.code2name(A.FLAG,'REPORT_GRIDRATE') flagName,");
		sb.append("		DECODE(SUM(A.ANUMS),0,0,");
		sb.append("		ROUND(100*SUM(A.NUMS)/SUM(A.ANUMS),2)) scale");
		sb.append("		FROM NEDS.TW2_GRID_RATE A");
		sb.append("		 WHERE  A.TMONTH = ? AND");
		sb.append("		A.WHGRIDCODE = ? AND");
		sb.append("		A.CITY =  ? AND");
		sb.append("		A.STATIDIMENSION = ?");
		sb.append("		GROUP BY A.CITY,A.FLAG, A.WHGRIDCODE,A.WHGRIDNAME");
		sb.append("		ORDER BY A.CITY, A.WHGRIDCODE,A.WHGRIDNAME,A.Flag");
		List<SatisInfo> datas = SpringBeanUtil.getDBPersistentService().find(sb.toString(), SatisInfo.class, params.toArray());
		if(datas != null && !datas.isEmpty()){
			params.clear();
			date = getPreDay(date);
			time = DateUtils.formatDate(date, DateUtils.FORMAT_YYYYMMDD);
			params.add(time);
			params.add(gridcode);
			params.add(city);
			params.add("0");
			List<SatisInfo> predatas = SpringBeanUtil.getDBPersistentService().find(sb.toString(), SatisInfo.class, params.toArray());
			if(predatas != null && !predatas.isEmpty()){
				for(SatisInfo target:datas){
					for(SatisInfo preTarget:predatas){
						if(target.getFlag().equals(preTarget.getFlag())){
							target.setMargin(target.getScale() - preTarget.getScale());
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取前一天
	 * @param date
	 * @return
	 */
	private Date getPreDay(Date date){
		Calendar curlCalendar = Calendar.getInstance();
		curlCalendar.setTime(date);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, curlCalendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, curlCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, curlCalendar.get(Calendar.DAY_OF_MONTH)-1);
		return calendar.getTime();
	}

}
