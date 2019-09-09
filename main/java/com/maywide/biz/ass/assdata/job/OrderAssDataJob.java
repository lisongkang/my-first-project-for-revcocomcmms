package com.maywide.biz.ass.assdata.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.ass.assdata.service.OrderAssService;
import com.maywide.core.context.SpringContextHolder;

public class OrderAssDataJob implements Job{

	private static final Log log = LogFactory.getLog(OrderAssDataJob.class);
	
	@Override
	public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		if(log.isInfoEnabled())
		{
			log.info("OrderAssData Job start!");
		}
		try{
			OrderAssService orderAssService = SpringContextHolder.getBean(OrderAssService.class);
			orderAssService.handlerNoPayOrder();
		}catch(Exception e){
			if(log.isInfoEnabled())
				log.info("OrderAssData Job Exception! "+e.getMessage());
		}
		if(log.isInfoEnabled())
		{
			log.info("OrderAssData Job finish! ");
		}
	}

}
