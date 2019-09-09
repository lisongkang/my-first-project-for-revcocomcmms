package com.maywide.biz.salary;

import com.maywide.common.datatransfer.service.MappingConfigService;
import com.maywide.common.task.interfaces.ITask;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoadDataTask implements ITask {
	private static final Logger logger = LoggerFactory.getLogger(LoadDataTask.class);
	
	public String doTask() throws Exception {
		MappingConfigService queryMappingService = SpringContextHolder.getBean(MappingConfigService.class);
		List list = queryMappingService.queryData("com.maywide.biz.prv.entity.PrvArea");
		logger.info("%%%%%%%%%%%%%%%%%%%%%  " + list.size());
		return SysConstant.SysYesNo.YES;
	}
}
