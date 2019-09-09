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

public class DayAssDataStatJob implements Job
{
	/**
	 * 日志记录器
	 */
	private static final Log log = LogFactory.getLog(DayAssDataStatJob.class);

	public void execute(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException{

		if(log.isInfoEnabled())
		{
			log.info("DayAssDataStat Job start!");
		}
		
		try {
			//  跑前一天的数据
			Calendar curCal = Calendar.getInstance();
    		curCal.add(Calendar.DATE, -1);
    		
			AssDataService assDataService = SpringContextHolder.getBean(AssDataService.class);
			assDataService.handleDayProgress(curCal.getTime());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
		if(log.isInfoEnabled())
		{
			log.info("DayAssDataStat Job finish! ");
		}
	}

}
