package com.maywide.biz.pay.unify.job;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.pay.unify.entity.PayBillFileLog;
import com.maywide.biz.pay.unify.service.PayBillFileService;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

public class ProduceBillfileJob implements Job {

	
	private PayBillFileService payBillFileService;
	
	public ProduceBillfileJob() {
		payBillFileService = SpringContextHolder.getBean(PayBillFileService.class);
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String yestoday = DateUtils.formatDate(DateUtils.getYesterday(), "yyyyMMdd");
		PayBillFileLog fileLog = findLog(yestoday);
		if (fileLog == null) {
			fileLog = new PayBillFileLog();
			fileLog.setFiledate(yestoday);
		}
		try {
			if (fileLog.getStatus() != null && fileLog.getStatus() == 1) return;
			payBillFileService.produceLogFile(yestoday);//此处更改为去生成对账文件
			fileLog.setStatus(1);
			fileLog.setOpertime(new Date());
			payBillFileService.getDAO().saveOrUpdate(fileLog);//此处更新数据库
		} catch (Exception e) {
			handleError(fileLog, e);
		}
		handlerMissingOrErrorFile();
	}
	
	private void handleError(PayBillFileLog fileLog, Exception e) {
		fileLog.setStatus(0);
		fileLog.setOpertime(new Date());
		fileLog.setMemo(getStackTrace(e));
		try {
			payBillFileService.getDAO().saveOrUpdate(fileLog);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void handlerMissingOrErrorFile(){
		try{
			List<PayBillFileLog> unUploadFiles = findLogsByStatus(0);
			if(null == unUploadFiles || unUploadFiles.isEmpty()) return;
			for(PayBillFileLog log:unUploadFiles){
				try{
					payBillFileService.produceLogFile(log.getFiledate());//此处更改为去生成对账文件
					log.setStatus(1);
					log.setOpertime(new Date());
					payBillFileService.getDAO().saveOrUpdate(log);
				}catch(Exception e){
					handleError(log,e);
					continue;
				}
			}
		}catch(Exception e){
		}
	}
	
	private List<PayBillFileLog> findLogsByStatus(Integer status) throws Exception{
		PayBillFileLog log = new PayBillFileLog();
		log.setStatus(status);
		List<PayBillFileLog> logs =  payBillFileService.getDAO().find(log);
		return logs;
	}
	
	private static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	
	private PayBillFileLog findLog(String day) {
		try {
			return (PayBillFileLog) payBillFileService.getDAO().find(PayBillFileLog.class, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
