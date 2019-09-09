package com.maywide.biz.inter.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.entity.AccountOpenMsg;
import com.maywide.biz.inter.entity.CustGroupMsg;
import com.maywide.biz.inter.pojo.msgjob.BizGrpArrearDetail;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

public class UpdateAccountOpenCustGroupMsgJob implements Job {

	static final int PAGESIZE = 10;
	
	private int currentpage = 0;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		startQuery(currentpage);
	}
	
	private void startQuery(int start){
		try {
			Page<BizGrpArrearDetail> page = getPageData(start, PAGESIZE);
			if(page.getResult() != null && !page.getResult().isEmpty()){
				currentpage++;
				handlerData(page);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private Page<BizGrpArrearDetail> getPageData(int currentPage,int pageSize) throws Exception {
		PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
		Page<BizGrpArrearDetail> page = new Page();
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from BIZ_GRP_ARREAR_DETAIL order by recid");
		page = persistentService.find(page, sb.toString(), BizGrpArrearDetail.class);
		return page;
	}
	
	
	private void handlerData(Page<BizGrpArrearDetail> page) throws Exception{
		if(page.getResult() == null || page.getResult().isEmpty()) return;
		PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from cust_group_msg");
		sb.append("		where 1 = 1");
		sb.append("		and mtype = ?");
		params.add("1");
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
				List<BizGrpArrearDetail> tmp = new ArrayList<BizGrpArrearDetail>();
				for(CustGroupMsg msg:datas){
					for(BizGrpArrearDetail detail:page.getResult()){
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
	
	/*private void fillAccountMsg(List<BizGrpArrearDetail> datas) throws Exception{
		PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from cust_group_msg");
		sb.append("		where 1 = 1");
		sb.append("		and mtype = ?");
		params.add("1");
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
		if(msgs == null || msgs.isEmpty()){
			insertCustGroupMsg(datas);
			return;
		}else{
			
		}
		startQuery(currentpage);
	}*/
	
	private void insertCustGroupMsg(List<BizGrpArrearDetail> datas){
		if(datas == null || datas.isEmpty()) return;
		PersistentService persistentService =  SpringContextHolder.getBean(PersistentService.class);
		for(BizGrpArrearDetail detail:datas){
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
				msg.setMtype("1");
				msg.setOperid(detail.getGrpmanagerid());
				msg.setUptime(new Date());
				persistentService.save(msg);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		insertAccountOpenMsg(datas);
		startQuery(currentpage);
	}
	
	private void insertAccountOpenMsg(List<BizGrpArrearDetail> datas){
		try{
			PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
			List params = new ArrayList();
			StringBuffer sb = new StringBuffer();
			sb.append("		select * from cust_group_msg");
			sb.append("		where 1 = 1");
			sb.append("		and mtype = ?");
			params.add("1");
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
					BizGrpArrearDetail detail = getBizGrpArrearDetail(msg.getBizid(), datas);
					if(detail == null) continue;
					AccountOpenMsg openMsg = new AccountOpenMsg();
					openMsg.setAccetime(detail.getAccetime());
					openMsg.setAccountrule(detail.getAccountrule());
					openMsg.setAccstime(detail.getAccstime());
					openMsg.setActchartime(detail.getActchartime());
					openMsg.setBilloptime(detail.getBilloptime());
					openMsg.setChargetime(detail.getChargetime());
					openMsg.setDelaycycle(detail.getDelaycycle());
					openMsg.setFee(detail.getFee());
					openMsg.setMid(msg.getId());
					openMsg.setNlevel(detail.getNlevel());
					openMsg.setPayway(detail.getPayway());
					openMsg.setPfeetype(detail.getPfeetype());
					openMsg.setTjmonth(detail.getTjmonth());
					openMsg.setAccstimetoaccetime(DateUtils.formatDate(detail.getAccstime(), DateUtils.FORMAT_YYYYMMDD_2)+"~"+DateUtils.formatDate(detail.getAccetime(), DateUtils.FORMAT_YYYYMMDD_2));
					persistentService.save(openMsg);
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	private BizGrpArrearDetail getBizGrpArrearDetail(Long recid,List<BizGrpArrearDetail> datas){
		for(BizGrpArrearDetail detail:datas){
			if(detail.getRecid().equals(recid)){
				return detail;
			}
		}
		return null;
	}


}
