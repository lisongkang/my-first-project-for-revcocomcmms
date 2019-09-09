package com.maywide.biz.index.achimendetail.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAchimenDetailJob implements Job {

	static final Logger logging = LoggerFactory.getLogger(UpdateAchimenDetailJob.class);
	
	static final int PAGESIZE = 10;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
	}
	
	

	/*private List<AchimenIndex> getAllAchimenIndex(int currentpage,int pagesize){
		
	}*/
}
