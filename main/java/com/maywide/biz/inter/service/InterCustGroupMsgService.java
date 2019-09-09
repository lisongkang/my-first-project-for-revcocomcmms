package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.entity.AccountOpenMsg;
import com.maywide.biz.inter.entity.CustGroupMsg;
import com.maywide.biz.inter.entity.CustWithdrawingMsg;
import com.maywide.biz.inter.pojo.bizMsgReaded.BizMsgReadedReq;
import com.maywide.biz.inter.pojo.queMsgList.AccountOpenMsgBean;
import com.maywide.biz.inter.pojo.queMsgList.CustWithdrawingMsgBean;
import com.maywide.biz.inter.pojo.queMsgList.DateIndex;
import com.maywide.biz.inter.pojo.queMsgList.MsgBean;
import com.maywide.biz.inter.pojo.queMsgList.MsgCountBean;
import com.maywide.biz.inter.pojo.queMsgList.NoticeMsg;
import com.maywide.biz.inter.pojo.queMsgList.QueMsgListResp;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;

@Service
public class InterCustGroupMsgService extends CommonService {

	/**
	 * 获取全部消息内容列表
	 * 
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queMsgList(QueMsgListResp resp) throws Exception {

		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
//		Long operid = 509530l;
//		 String city = "DG";

		List params = new ArrayList();

		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DISTINCT(a.id) mid,a.city,a.contractid,a.contractseq");
		sb.append("		,a.custid,a.custname,a.msgstatus,a.mtype,a.operid,a.uptime");
		sb.append("		,a.areaid, b.mname cityname,c.name areaname,d.name opername");
		sb.append("		FROM cust_group_msg a");
		sb.append("		inner join prv_sysparam b");
		sb.append("		on a.city = b.mcode");
		sb.append("		INNER JOIN prv_operator d");
		sb.append("		ON a.operid = d.operid");
		sb.append("		AND (a.operid = ?");
		params.add(loginInfo.getOperid());
//		params.add(operid);
		//ywp 前面的不能去掉，因为去掉的话mtype=2就无法查出来
		sb.append("		or FIND_IN_SET(?,a.operids))");
		params.add(loginInfo.getOperid());
//		params.add(operid);
		sb.append("		AND a.city = ?");
		params.add(loginInfo.getCity());
//		params.add(city);
		sb.append("		AND a.msgstatus = ?");
		params.add("0");
		sb.append("		AND b.gcode = ?");
		params.add("PRV_CITY");
		sb.append("		left join prv_area c");
		sb.append("		on a.areaid = c.areaid");
		sb.append("		ORDER BY a.uptime DESC");

		List<MsgBean> msgList = getDAO().find(sb.toString(), MsgBean.class, params.toArray());
		findAllCompleteMsg(msgList);
		screenFalseMsg(msgList);
		resp.setDatas(msgList);
		handlerSameMsg(resp);
		countMsgCount(resp);
		return returnInfo;
	}

	private void handlerSameMsg(QueMsgListResp resp) {
		if (resp == null || resp.getDatas() == null || resp.getDatas().isEmpty()) {
			return;
		}
		List<MsgBean> datas = new ArrayList<MsgBean>();
		Map<String, List<MsgBean>> map = new HashMap<String, List<MsgBean>>();
		for (MsgBean bean : resp.getDatas()) {
			String key = bean.getCustid() + "_" + bean.getContractid() + "_" + bean.getMtype() + "_" + bean.getOperid();
			if(bean.getMtype().equals("1") && null != bean.getOpenMsg() && StringUtils.isNotBlank(bean.getOpenMsg().getPfeetype())){
				key += bean.getOpenMsg().getPfeetype();
			}
			List<MsgBean> tmpList = map.get(key);
			if (tmpList == null) {
				tmpList = new ArrayList<MsgBean>();
				map.put(key, tmpList);
			}
			tmpList.add(bean);
		}
		Iterator<Entry<String, List<MsgBean>>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<MsgBean>> entry = iterator.next();
			MsgBean openMsgBean = handlerAccountOpenMsg(entry.getValue());
			if (null != openMsgBean) {
				datas.add(openMsgBean);
			}
			MsgBean custDrawBean = handlerCustWithDrawMsg(entry.getValue());
			if (null != custDrawBean) {
				datas.add(custDrawBean);
			}
		}
		resp.setDatas(datas);
	}

	private MsgBean handlerAccountOpenMsg(List<MsgBean> openMsgList) {
		if (openMsgList == null || openMsgList.isEmpty()) {
			return null;
		}
		int index = 0;
		MsgBean bean = openMsgList.get(index);
		while (null == bean.getOpenMsg()) {
			index++;
			if(index >= openMsgList.size()){
				return null;
			}
			bean = openMsgList.get(index);
		}
		AccountOpenMsgBean openMsg = bean.getOpenMsg();
		if(null != openMsg){
			for (int i = index+1; i < openMsgList.size(); i++) {
				MsgBean tmp = openMsgList.get(i);
				if (null == tmp.getOpenMsg()) {
					continue;
				}
				openMsg.setFee(Double.parseDouble(openMsg.getFee()) + Double.parseDouble(tmp.getOpenMsg().getFee()) + "");
				openMsg.setDelaycycle(openMsg.getDelaycycle() + tmp.getOpenMsg().getDelaycycle());
				openMsg.setBilloptimeMemo(constructDate(openMsg.getBilloptime(), tmp.getOpenMsg().getBilloptime(),
						openMsg.getBilloptimeMemo()));
				openMsg.setActchartimeMemo(constructDate(openMsg.getActchartime(), tmp.getOpenMsg().getActchartime(),
						openMsg.getActchartimeMemo()));
			}
			openMsg.setAccstimetoaccetime(contructS2ETime(openMsgList) == null?openMsg.getAccstimetoaccetime():contructS2ETime(openMsgList));
		}
		return bean;
	}
	
	
	/***
	 * 问题1
	 * @param msglist
	 * @return
	 */
	private String contructS2ETime(List<MsgBean> msglist){
		StringBuffer sb = new StringBuffer();
		List<MsgBean> datelist = new ArrayList<MsgBean>();
		for(int index = 0; index < msglist.size(); index++){
			MsgBean tmp = msglist.get(index);
			if (null == tmp.getOpenMsg()) { 
				continue;
			}
//			datelist.add(new DateIndex(index,tmp.getOpenMsg().getAccstime()));
			datelist.add(tmp);
		}
		Collections.sort(datelist, new Comparator<MsgBean>() {

			@Override
			public int compare(MsgBean o1, MsgBean o2) {
				if(o1.getOpenMsg().getAccstime().getTime() == o2.getOpenMsg().getAccstime().getTime()){
					return 0;	
				}else if(o1.getOpenMsg().getAccstime().getTime() > o2.getOpenMsg().getAccstime().getTime()){
					return 1;
				}else{
					return -1;
				}
			}
		});
		for(int i = 0; i < datelist.size() ;i++){
//			sb.append(msglist.get(datelist.get(i).getIndex()).getOpenMsg().getAccstimetoaccetime());
			sb.append(datelist.get(i).getOpenMsg().getAccstimetoaccetime());
			if(i != datelist.size() - 1){
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	private MsgBean handlerCustWithDrawMsg(List<MsgBean> openMsgList) {
		if (openMsgList == null || openMsgList.isEmpty()) {
			return null;
		}
		int index = 0;
		MsgBean bean = openMsgList.get(index);
		while (null == bean.getDrawMsg()) {
			index++;
			if(index >= openMsgList.size()){
				return null;
			}
			bean = openMsgList.get(index);
		}
		CustWithdrawingMsgBean drawMsg = bean.getDrawMsg();
		if(null != drawMsg){
			for (int i = index+1; i < openMsgList.size(); i++) {
				MsgBean tmp = openMsgList.get(i);
				if (null == tmp.getDrawMsg()) {
					continue;
				}
				drawMsg.setFees(Double.parseDouble(drawMsg.getFees())+Double.parseDouble(tmp.getDrawMsg().getFees())+"");
				drawMsg.setPtimeMemo(constructDate(drawMsg.getPtime(), tmp.getDrawMsg().getPtime(), tmp.getDrawMsg().getPtimeMemo()));
			}
			return bean;
		}
		return null;
	}

	private String constructDate(Date pre, Date next, String prvcontent) {
		
		StringBuffer sb = new StringBuffer();
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(next);
		if(StringUtils.isNotBlank(prvcontent)){
			String[] dateStrs = prvcontent.split("\n");
			for(int i = 0; i < dateStrs.length ;i++){
				dateList.add(DateUtils.parseTime(dateStrs[i],DateUtils.FORMAT_YYYYMMDD_FORMATER_2));
			}
			
		}else{
			dateList.add(pre);
		}
		Collections.sort(dateList, new Comparator<Date>() {

			@Override
			public int compare(Date o1, Date o2) {
				if(o1.getTime() == o2.getTime()){
					return 0;	
				}else if(o1.getTime() > o2.getTime()){
					return 1;
				}else{
					return -1;
				}
				
			}
		});
		for(int i = 0; i < dateList.size() ;i++){
			sb.append(DateUtils.formatDate(dateList.get(i), DateUtils.FORMAT_YYYYMMDD_2));
			if(i != dateList.size() - 1){
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}

	private void countMsgCount(QueMsgListResp resp) {
		if (resp == null || resp.getDatas() == null || resp.getDatas().isEmpty()) {
			return;
		}
		int accountMsgCount = 0;
		int drawMsgCount = 0;
		for (MsgBean bean : resp.getDatas()) {
			if (bean.getMtype().equals("1")) {
				accountMsgCount++;
			} else if (bean.getMtype().equals("2")) {
				drawMsgCount++;
			}
		}
		MsgCountBean countBean = new MsgCountBean(accountMsgCount, drawMsgCount);
		for(MsgBean bean : resp.getDatas()){
			if(bean.getMtype().equals("1") && null != bean.getOpenMsg()){
				NoticeMsg acountMsg = new NoticeMsg();
				acountMsg.setCustname(bean.getCustname());
				acountMsg.setMsgcount(accountMsgCount);
				acountMsg.setMtype(bean.getMtype());
				acountMsg.setUpdateTime(bean.getUptime());
				acountMsg.setMsgContent("客户"+bean.getCustname()+"有"+bean.getOpenMsg().getFee()+"元未缴，请及时通知客户缴费");
				countBean.setAccountNotice(acountMsg);
				break;
			}
		}
		for(MsgBean bean : resp.getDatas()){
			if(bean.getMtype().equals("2") && null != bean.getDrawMsg()){
				NoticeMsg acountMsg = new NoticeMsg();
				acountMsg.setCustname(bean.getCustname());
				acountMsg.setMsgcount(drawMsgCount);
				acountMsg.setMtype(bean.getMtype());
				acountMsg.setUpdateTime(bean.getUptime());
				acountMsg.setMsgContent("客户"+bean.getCustname()+"扣款"+bean.getDrawMsg().getFees()+"元成功，请知晓！");
				countBean.setCustDrawNotice(acountMsg);
				break;
			}
		}
		resp.setCountBean(countBean);;
	}

	private void screenFalseMsg(List<MsgBean> msgList) {
		if (msgList == null || msgList.isEmpty()) {
			return;
		}
		List<MsgBean> tmp = new ArrayList<MsgBean>();
		for (MsgBean bean : msgList) {
			if ((bean.getDrawMsg() == null) && (bean.getOpenMsg() == null)) {
				tmp.add(bean);
			}
		}
		msgList.removeAll(tmp);
	}

	private void findAllCompleteMsg(List<MsgBean> msgList) throws Exception {
		if (msgList == null || msgList.isEmpty()) {
			return;
		}
		for (MsgBean msg : msgList) {
			if (msg.getMtype().equals("1")) {
				msg.setOpenMsg(getAccountOpenMsg(msg.getMid()));
			} else if (msg.getMtype().equals("2")) {
				msg.setDrawMsg(getCustWithDrawingMsg(msg.getMid()));
			}
		}
	}

	private CustWithdrawingMsgBean getCustWithDrawingMsg(Long mid) throws Exception {
		CustWithdrawingMsg msg = new CustWithdrawingMsg();
		msg.setMid(mid);
		List<CustWithdrawingMsg> msgs = getDAO().find(msg);
		if (msgs == null || msgs.isEmpty()) {
			return null;
		}
		CustWithdrawingMsgBean bean = new CustWithdrawingMsgBean(msgs.get(0));
		return bean;
	}

	private AccountOpenMsgBean getAccountOpenMsg(Long mid) throws Exception {
		AccountOpenMsg openMsg = new AccountOpenMsg();
		openMsg.setMid(mid);
		List<AccountOpenMsg> msgs = getDAO().find(openMsg);
		if (msgs == null || msgs.isEmpty()) {
			return null;
		}
		AccountOpenMsgBean bean = new AccountOpenMsgBean(msgs.get(0));
		return bean;
	}

	/**
	 * 设置消息为已读状态
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizMsgReaded(BizMsgReadedReq req) throws Exception {

		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
//		CheckUtils.checkNull(req.getMids(), "消息编号不能为空!");
		CheckUtils.checkEmpty(req.getMtype(),"消息类型不能为空");
		/*String[] midStr = req.getMids().split(",");
		for (String mid : midStr) {
			
		}*/
		
		CustGroupMsg msg = new CustGroupMsg();
		msg.setOperid(loginInfo.getOperid());
		msg.setCity(loginInfo.getCity());
		msg.setMtype(req.getMtype());
		List<CustGroupMsg> msgList = getDAO().find(msg);
		if(msgList != null && !msgList.isEmpty()){
			for(CustGroupMsg groupMsg:msgList){
				try{
					groupMsg.setReadtime(new Date());
					groupMsg.setMsgstatus("1");
					getDAO().update(groupMsg);
				}catch(Exception e){
					log.error(e.getMessage());
					continue;
				}
			}
		}

		return returnInfo;
	}

}
