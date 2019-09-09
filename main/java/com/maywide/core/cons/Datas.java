package com.maywide.core.cons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;

public class Datas {
	private static final Logger logger = LoggerFactory.getLogger(Datas.class);
	
	private static Map<Long, PrvDepartment> departmentMap;
	
	public static Map<Long, PrvDepartment> getDepartmentMap() {
		return departmentMap;
	}

	public static void initData() throws Exception {
		logger.error("**************");
		initDepartment();
	}
	
	public static void initDepartment() {
		try {
			PersistentService persistentService = 
				(PersistentService) SpringContextHolder.getBean(PersistentService.class);
			departmentMap = new HashMap<Long, PrvDepartment>();
			
			List<PrvDepartment> depts = (List<PrvDepartment>) persistentService.find(new PrvDepartment());
			for (PrvDepartment dept : depts) {
				departmentMap.put(dept.getId(), dept);
			}
		} catch (Exception e) {
			logger.error("初始化部门信息出错:", e);
		}
		
	}
}
