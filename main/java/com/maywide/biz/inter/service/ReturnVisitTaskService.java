package com.maywide.biz.inter.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.maywide.biz.inter.task.ReturnVisitTask;

public class ReturnVisitTaskService {
	
	private final static ScheduledExecutorService  executor = Executors.newScheduledThreadPool(5);
	
	public static void addTask(Long orderid) {
		executor.schedule(new ReturnVisitTask(orderid), 1, TimeUnit.MINUTES);
	}
	
}
