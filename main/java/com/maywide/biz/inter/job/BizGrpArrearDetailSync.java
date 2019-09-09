package com.maywide.biz.inter.job;

import java.text.SimpleDateFormat;
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
import com.maywide.biz.inter.pojo.msgjob.SysGrpNoticeLevel;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

public class BizGrpArrearDetailSync implements Job {
	
	static final int PAGESIZE = 10;
	private int currentpage = 0;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		startSyncBizGrpArrearDetail();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date())+":开始BIZ_GRP_ARREAR_DETAIL表同步==");
	}
	private void startSyncBizGrpArrearDetail(){
		try {
			List<BizGrpArrearDetail> list = getBizGrpArrearDetail();
			System.out.println("allsize=="+list.size());
			//先清数据
			clearData();
			//开始同步分页同步数据
//			startQuery(currentpage);
			insertCustGroupMsg(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void startQuery(int start){
		try {
			Page<BizGrpArrearDetail> page = getPageData(start, PAGESIZE);
			if(page.getResult() != null && !page.getResult().isEmpty()){
				currentpage++;
//				startSyncBizGrpArrearDetail(page.getResult());
				insertCustGroupMsg(page.getResult());
			}else{
				System.out.println("all="+currentpage);
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
	//先清对应的数据
	private void clearData() throws Exception{
		delCustGroupMsgByMtype();
		delAccountOpenMsg();
	}
	
	
	private void insertCustGroupMsg(List<BizGrpArrearDetail> datas){
		if(datas == null || datas.isEmpty()) return;
		PersistentService persistentService =  SpringContextHolder.getBean(PersistentService.class);
		for(BizGrpArrearDetail detail:datas){
			try {
				//保存CustGroupMsg数据
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
				//ywp 这个是提醒人，第一个月集客经理，第二个月总监，第三个月分管领导，第四个月 省领导
				//这个字段因为是long型，无法用find_in_set因此先另外增加一个字段operids来判断发送人
				msg.setOperid(detail.getGrpmanagerid());
				String operids = getOperids(detail.getGrpmanagerid()+"",detail.getNlevel(),detail.getCity());
				msg.setOperids(operids);
				msg.setUptime(new Date());
				persistentService.save(msg);
				//保存完CustGroupMsg对象会获得对应ID，再用openMsg.setMid(msg.getId());保存AccountOpenMsg
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
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
//		startQuery(currentpage);
	}
	/**
	 * 拿到所有要提醒的operid
	 * @param grpmanagerid
	 * @param nlevel
	 * @param city
	 * @return
	 * NLEVEL:
		  	NOT NULL
			1-客户经理
			2-主管/地市总监
			3-分管领导
			4-省领导

	 * @throws Exception
	 */
	private String getOperids(String grpmanagerid,String nlevel,String city) throws Exception{
		String operid = grpmanagerid;
		String operids = "";
		List<String> list = new ArrayList<String>();
		String[] nlevels = nlevel.split(",");
		for(String i:nlevels){
			if("1".equals(i)){
				list.add(grpmanagerid);
				operids += grpmanagerid + ",";
			}else{//如果不是发送给集客经理，operid就要重新赋值去找领导
				operid = getLeaderid(operid,city);
				if(!"".equals(operid)){
					list.add(operid);
					operids += operid + ",";
				}
			}
		}
		operids = operids.substring(0, operids.length()-1);
		return operids;
	}
	private List<String> getOperidlist(String grpmanagerid,String nlevel,String city) throws Exception{
		String operid = grpmanagerid;
		String operids = "";
		List<String> list = new ArrayList<String>();
		String[] nlevels = nlevel.split(",");
		for(String i:nlevels){
			if("1".equals(i)){
				list.add(grpmanagerid);
				operids += grpmanagerid + ",";
			}else{//如果不是发送给集客经理，operid就要重新赋值去找领导
				operid = getLeaderid(operid,city);
				list.add(operid);
				operids += operid + ",";
			}
		}
		operids = operids.substring(0, operids.length()-1);
		return list;
	}
	private String getLeaderid(String operid,String city) throws Exception{
		String leaderid = "";
		PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
		//这个在本地用来测试用的，通知层级正式的要从报表仓库拿
//		PersistentService persistentService =  SpringContextHolder.getBean(PersistentService.class);
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from SYS_GRP_NOTICE_LEVEL s where s.OPERID=? and s.CITY=?");
		params.add(operid);
		params.add(city);
		List<SysGrpNoticeLevel> list = persistentService.find(sqlBuffer.toString(),
				SysGrpNoticeLevel.class, params.toArray());
		if(list.size()>0){
			leaderid = list.get(0).getLeaderid()+"";
		}
		return leaderid;
	}
	
	/**
	 * 获得全部的BIZ_GRP_BNKPAY_DETAIL数据
	 * @return
	 * @throws Exception
	 */
	private List<BizGrpArrearDetail> getBizGrpArrearDetail() throws Exception{
		PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from BIZ_GRP_ARREAR_DETAIL order by recid");
		List<BizGrpArrearDetail> list = persistentService.find(sqlBuffer.toString(),
				BizGrpArrearDetail.class, params.toArray());
		return list;
	}
	/**
	 * 清掉cust_group_msg中mtype=1的数据
	 * @throws Exception
	 */
	private void delCustGroupMsgByMtype() throws Exception{
		PersistentService persistentService =  SpringContextHolder.getBean(PersistentService.class);
		String delMtypeOne = "delete from cust_group_msg where mtype=1";
		persistentService.executeSql(delMtypeOne, null);
	}
	/**
	 * 清掉account_open_msg数据
	 * @throws Exception
	 */
	private void delAccountOpenMsg() throws Exception{
		PersistentService persistentService =  SpringContextHolder.getBean(PersistentService.class);
		String delMtypeOne = "delete from account_open_msg";
		persistentService.executeSql(delMtypeOne, null);
	}
}
