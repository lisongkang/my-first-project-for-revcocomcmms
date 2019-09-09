package com.maywide.biz.inter.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizCustorderOrderstatus;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.entity.VisitCommentRecord;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.sys.entity.ReturnVisitTemplate;
import com.maywide.biz.sys.entity.SysCustVisitRule;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.biz.task.pojo.TaskMessageQueueBean;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;
import com.maywide.tool.mq.impl.MQProducerImpl;

public class ReturnVisitTask implements Runnable{
	
	private final static Logger logger = LoggerFactory.getLogger(ReturnVisitTask.class);

	private final PersistentService DAO; 
	
	private ParamService paramService;
	
	private final Long orderid;
	
	private MQProducerImpl mqProducerImpl;
	
	public ReturnVisitTask(Long orderid) {
		this.orderid = orderid;
		DAO = (PersistentService) SpringContextHolder
				.getBean(PersistentService.class);
		mqProducerImpl = SpringContextHolder.getBean(MQProducerImpl.class);
		paramService = SpringContextHolder.getBean(ParamService.class);
	}
	
	@Override
	public void run() {
		try {
			CustOrder custOrder = (CustOrder) DAO.find(CustOrder.class, orderid);
			if(null == custOrder) {
				logger.error("orderid ["+orderid+"] is null!!!");
				return;
			}
			if(custOrder.getOrderstatus().equals(BizCustorderOrderstatus.SYNC)) {//不是已同步到BOSS的状态 不需要进行回访
				SysCustVisitRule visitRule = getCustVisitRule(custOrder);
				if(null == visitRule) {
					//如果没有对应的回访规则配置,默认不需要进行回访
					return;
				}else {
					ReturnVisitTemplate visitTemplate = getReturnVisitTemplate(visitRule.getContentTemplateId());
					if(visitTemplate == null) {
						return;
					}
					if(isCurrentlyDayMax(custOrder,visitRule,mergeSendContentByTemplate(visitTemplate))) {
						return;
					}else {
						PrvSysparam param = paramService.getData("QUARTZ_JOB_PATH", "CUST_RETURN_VISIT");
						String filePath = param.getData();
//						String filePath = "D:\\test\\jars\\quartz-cust-visit.jar";
						sendMqToQuartzQueue(visitRule,custOrder,filePath);
					}
				}
			}else {
				logger.error("orderid ["+orderid+"] orderstatus is"+custOrder.getOrderstatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private SysCustVisitRule getCustVisitRule(CustOrder custOrder) throws Exception {
		String areaid = custOrder.getAreaid() == null? "*":custOrder.getAreaid().toString();
		return getCustVisitRule(custOrder.getCity(), areaid, custOrder.getOpcode());
	}
	
	
	private SysCustVisitRule getCustVisitRule(String city,String area,String opcode) throws Exception {
		SysCustVisitRule custVisitRule = new SysCustVisitRule();
		custVisitRule.setCity(city);
		custVisitRule.setOpcode(opcode);
		custVisitRule.setArea(area);
		List<SysCustVisitRule> visitRuleList = DAO.find(custVisitRule);
		if(null == visitRuleList || visitRuleList.isEmpty()) {
			if(area.equals("*")) {
				return null;
			}else {
				return getCustVisitRule(city, "*", opcode);
			}
		}else {
			return visitRuleList.get(0);
		}
	}
	
	/**
	 * 查询是否已达到当天发送的最大次数上限
	 * @param maxTimes
	 * @return
	 * @throws Exception 
	 */
	private boolean isCurrentlyDayMax(CustOrder custOrder,SysCustVisitRule visitRule,String content) throws Exception {
		
		synchronized(ReturnVisitTask.class) {
			long count = DAO.count("select orderid from visit_comment_record where custid = ?",custOrder.getCustid());
			if(count < visitRule.getMaxTimes().longValue()) {
				insertRecord(custOrder,visitRule,content);
				return false;
			}else{
				return true;
			}
		}
	}
	
	private void insertRecord(CustOrder custOrder,SysCustVisitRule visitRule,String sendContent) throws Exception {
		VisitCommentRecord commentRecord = new VisitCommentRecord();
		commentRecord.setCity(custOrder.getCity());
		commentRecord.setCustid(custOrder.getCustid());
		commentRecord.setDeptid(custOrder.getOprdep());
		commentRecord.setMobileIndex(visitRule.getMobileIndex());
		commentRecord.setBossSerinol(custOrder.getBossserialno());
		commentRecord.setOperid(custOrder.getOperator());
		commentRecord.setSendContent(sendContent);
		commentRecord.setCustName(custOrder.getName());
		commentRecord.setId(orderid);
		commentRecord.setSendSystem("GRID");
		commentRecord.setVisitMethod(visitRule.getSendMethod());
		commentRecord.setSendStatus(BizConstant.VisitCommentRecordStatus.VISIT_UNSEND);
		DAO.save(commentRecord);
	}
	
	private void sendMqToQuartzQueue(SysCustVisitRule rule,CustOrder custorder,String filePath) {
		TaskMessageQueueBean messageBean = new TaskMessageQueueBean();
		messageBean.setTimeValue(rule.getDelayValue());
		messageBean.setDescrption("order["+custorder.getOrdercode()+"] visit task");
		messageBean.setFilePath(filePath);
		messageBean.setJobGroup(custorder.getCity()+" visit task");
		messageBean.setJobType("simple");
		messageBean.setJobName(custorder.getOpcode()+"-"+custorder.getId()+"-"+DateUtils.formatDateNow());
		messageBean.setParamsInstruct(custorder.getId().toString());//暂不配置 配置文件选择
		messageBean.setTimeType(rule.getDelayType());
		mqProducerImpl.sendDataToQueue("quartz."+custorder.getOrdercode(), messageBean);
	}
	
	private ReturnVisitTemplate getReturnVisitTemplate(Integer contentTemplateId) throws Exception {
		return (ReturnVisitTemplate) DAO.find(ReturnVisitTemplate.class, contentTemplateId);
	}
	
	private String mergeSendContentByTemplate(ReturnVisitTemplate visitTemplate) {
		String title = visitTemplate.getTemplateTitle();
		String content = visitTemplate.getTemplateContent();
		StringBuffer sb = new StringBuffer();
		sb.append(title);
		sb.append("/n");
		sb.append(content);
		return sb.toString();
	}

}
