package com.maywide.biz.inter.manager;

import java.util.Date;

import com.maywide.biz.inter.entity.OperatorLoginLog;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;

public class LoginLogginTask implements Runnable {

	private PersistentService persistenService;
	
	private final String city;
	
	private final String devStr;
	
	private final String ipAddr;
	
	private final String pkgName;
	
	private final String vertype;
	
	private final Long operid;
	
	private final Long deptid;
	
	
	public LoginLogginTask(String city, String devStr, String ipAddr,
			String pkgName, String vertype, Long operid, Long deptid) {
		super();
		this.city = city;
		this.devStr = devStr;
		this.ipAddr = ipAddr;
		this.pkgName = pkgName;
		this.vertype = vertype;
		this.operid = operid;
		this.deptid = deptid;
		persistenService = SpringContextHolder.getBean(PersistentService.class);
	}

	@Override
	public void run() {
		try {
			OperatorLoginLog loginLog = getOperatorLoginLog();
			persistenService.save(loginLog);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private OperatorLoginLog getOperatorLoginLog() {
		OperatorLoginLog log = new OperatorLoginLog();
		log.setCity(city);
		log.setDeptid(deptid);
		log.setLoginDate(new Date());
		log.setLoginDevstr(devStr);
		log.setLoginIpAddr(ipAddr);
		log.setLoginPkgName(pkgName);
		log.setLoginVertype(vertype);
		log.setOperid(operid);
		return log;
	}

}
