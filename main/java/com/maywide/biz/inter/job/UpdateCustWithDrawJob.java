package com.maywide.biz.inter.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.entity.CustGroupMsg;
import com.maywide.biz.inter.entity.CustWithdrawingMsg;
import com.maywide.biz.inter.pojo.msgjob.BizGrpBankpayDetail;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;

public class UpdateCustWithDrawJob implements Job {

	static final int PAGESIZE = 10;
	
	private int currentpage = 0;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		startQuery(currentpage);
	}
	
	private void startQuery(int start){
		try {
			Page<BizGrpBankpayDetail> page = getPageData(start, PAGESIZE);
			if(page.getResult() != null && !page.getResult().isEmpty()){
				currentpage++;
				handlerData(page);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private Page<BizGrpBankpayDetail> getPageData(int currentPage,int pageSize) throws Exception {
		PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
		Page<BizGrpBankpayDetail> page = new Page();
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from BIZ_GRP_BNKPAY_DETAIL order by recid");
		page = persistentService.find(page, sb.toString(), BizGrpBankpayDetail.class);
		return page;
	}
	
	
	private void handlerData(Page<BizGrpBankpayDetail> page) throws Exception{
		if(page.getResult() == null || page.getResult().isEmpty()) return;
		PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from cust_group_msg");
		sb.append("		where 1 = 1");
		sb.append("		and mtype = ?");
		params.add("2");
		sb.append("		and bizid in ( ");
		for(int i = 0; i < page.getResult().size(); i++){
			sb.append("	? ");
			params.add(page.getResult().get(i).getRecid());
			if(i != page.getResult().size() - 1 ){
				sb.append(",");
			}
		}
		sb.append("	)");
		List<CustGroupMsg> datas = persistentService.find(sb.toString(), CustGroupMsg.class, params.toArray());
		if(datas != null && !datas.isEmpty()){
			if(datas.size() == page.getResult().size()){
				startQuery(currentpage);
			}else if(datas.size() < page.getResult().size()){ 
				List<BizGrpBankpayDetail> tmp = new ArrayList<BizGrpBankpayDetail>();
				for(CustGroupMsg msg:datas){
					for(BizGrpBankpayDetail detail:page.getResult()){
						if(msg.getBizid().equals(detail.getRecid())){
							tmp.add(detail);
							break;
						}
					}
				}
				page.getResult().removeAll(tmp);
				insertCustGroupMsg(page.getResult());
			}else{
				startQuery(currentpage);
			}
		}else{
			insertCustGroupMsg(page.getResult());
		}
	}
	
	
	private void insertCustGroupMsg(List<BizGrpBankpayDetail> datas){
		if(datas == null || datas.isEmpty()) return;
		PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
		for(BizGrpBankpayDetail detail:datas){
			try {
				CustGroupMsg msg = new CustGroupMsg();
				msg.setAreaid(detail.getAreaid());
				msg.setBizid(detail.getRecid());
				msg.setCity(detail.getCity());
				msg.setContractid(detail.getContractid());
				msg.setContractseq(detail.getContractseq());
				msg.setCustid(detail.getCustid());
				msg.setCustname(detail.getName());
				msg.setMsgstatus("0");
				msg.setMtype("2");
				msg.setOperid(detail.getGrpmanagerid());
				msg.setUptime(new Date());
				persistentService.save(msg);
			} catch (Exception e) {
				
			}
		}
		insertCustWithDrawMsg(datas);
		startQuery(currentpage);
	}
	
	private void insertCustWithDrawMsg(List<BizGrpBankpayDetail> datas){
		try{
			PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
			List params = new ArrayList();
			StringBuffer sb = new StringBuffer();
			sb.append("		select * from cust_group_msg");
			sb.append("		where 1 = 1");
			sb.append("		and mtype = ?");
			params.add("2");
			sb.append("		and bizid in ( ");
			for(int i = 0; i < datas.size(); i++){
				sb.append("	? ");
				params.add(datas.get(i).getRecid());
				if(i != datas.size() - 1 ){
					sb.append(",");
				}
			}
			sb.append("	)");
			List<CustGroupMsg> msgs = persistentService.find(sb.toString(), CustGroupMsg.class, params.toArray());
			if(msgs != null && !msgs.isEmpty()){
				for(CustGroupMsg msg:msgs){
					BizGrpBankpayDetail detail = getBizGrpArrearDetail(msg.getBizid(), datas);
					if(detail == null) continue;
					CustWithdrawingMsg drawMsg = new CustWithdrawingMsg();
					drawMsg.setFees(detail.getFees());
					drawMsg.setMid(msg.getId());
					drawMsg.setPtime(detail.getPtime());
					persistentService.save(drawMsg);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
	}
	
	private BizGrpBankpayDetail getBizGrpArrearDetail(Long recid,List<BizGrpBankpayDetail> datas){
		for(BizGrpBankpayDetail detail:datas){
			if(detail.getRecid().equals(recid)){
				return detail;
			}
		}
		return null;
	}


}
