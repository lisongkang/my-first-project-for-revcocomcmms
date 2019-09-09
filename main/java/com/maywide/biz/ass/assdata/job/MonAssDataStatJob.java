package com.maywide.biz.ass.assdata.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.ass.assdata.service.AssDataService;
import com.maywide.core.context.SpringContextHolder;

public class MonAssDataStatJob implements Job
{
	/**
	 * 日志记录器
	 */
	private static final Log log = LogFactory.getLog(MonAssDataStatJob.class);

	public void execute(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException{

		if(log.isInfoEnabled())
		{
			log.info("MonAssDataStat Job start!");
		}
		
		try {
			// 跑上个月的报表数据
			Calendar curCal = Calendar.getInstance();
    		curCal.add(Calendar.MONTH, -1);
    		
			AssDataService assDataService = SpringContextHolder.getBean(AssDataService.class);
			assDataService.handleMonthProgress(curCal.getTime());
		} catch (Exception e) {
		}
        
		if(log.isInfoEnabled())
		{
			log.info("MonAssDataStat Job finish! ");
		}
	}

}
