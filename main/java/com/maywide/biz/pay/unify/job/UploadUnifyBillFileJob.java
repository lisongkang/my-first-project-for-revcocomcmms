package com.maywide.biz.pay.unify.job;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.pay.unify.entity.PayBillFileLog;
import com.maywide.biz.pay.unify.service.UnifyPayBillService;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.util.DateUtils;

public class UploadUnifyBillFileJob implements Job {

	static final Log log = LogFactory.getLog(UploadUnifyBillFileJob.class);

	private UnifyPayBillService unifyPayBillService;

	public UploadUnifyBillFileJob() {
		unifyPayBillService = SpringContextHolder.getBean(UnifyPayBillService.class);
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
			unifyPayBillService.uploadYesterdayBillFile();
			fileLog.setStatus(1);
			fileLog.setOpertime(new Date());
			unifyPayBillService.getDAO().saveOrUpdate(fileLog);
		} catch (Exception e) {
			handleError(fileLog, e);
		}
		handlerMissingOrErrorFile();
	}

	private void handleError(PayBillFileLog fileLog, Exception e) {
		log.error("上传统一支付对账文件失败", e);
		fileLog.setStatus(0);
		fileLog.setOpertime(new Date());
		fileLog.setMemo(getStackTrace(e));
		try {
			unifyPayBillService.getDAO().saveOrUpdate(fileLog);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private PayBillFileLog findLog(String day) {
		try {
			return (PayBillFileLog) unifyPayBillService.getDAO().find(PayBillFileLog.class, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	
	private void handlerMissingOrErrorFile(){
		try{
			List<PayBillFileLog> unUploadFiles = findLogsByStatus(0);
			if(null == unUploadFiles || unUploadFiles.isEmpty()) return;
			for(PayBillFileLog log:unUploadFiles){
				try{
					unifyPayBillService.uploadDateBillFile(log.getFiledate());
					log.setStatus(1);
					log.setOpertime(new Date());
					unifyPayBillService.getDAO().saveOrUpdate(log);
				}catch(Exception e){
					handleError(log,e);
					continue;
				}
			}
		}catch(Exception e){
			log.error("获取统一支付对账记录出错", e);
		}
	}
	
	private List<PayBillFileLog> findLogsByStatus(Integer status) throws Exception{
		PayBillFileLog log = new PayBillFileLog();
		log.setStatus(status);
		List<PayBillFileLog> logs =  unifyPayBillService.getDAO().find(log);
		return logs;
	}
	

}
