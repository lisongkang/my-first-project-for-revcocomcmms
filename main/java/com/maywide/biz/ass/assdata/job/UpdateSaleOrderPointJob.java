package com.maywide.biz.ass.assdata.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.ass.assdata.pojo.SaleOrderPointBean;
import com.maywide.biz.cons.BizConstant.PORTAL_ORDER_STATUS;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.prd.entity.PrvSalesPoint;
import com.maywide.biz.prd.entity.StaSalesPointOper;
import com.maywide.biz.prd.entity.StaSalesPointOrder;
import com.maywide.biz.prv.entity.PrvOperinfo;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;

public class UpdateSaleOrderPointJob implements Job {

	static final Log log = LogFactory.getLog(UpdateSaleOrderPointJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			
			log.error("execute======================>"+System.currentTimeMillis());
			PersistentService persistentService = SpringContextHolder.getBean(PersistentService.class);
			
			Date nowDate = new Date();
			//筛选出BIZ_CUSTORDER表里所有昨天的订单
			List params = new ArrayList();
			params.add(getYesterDay());
			params.add(nowDate);
			StringBuffer bs = new StringBuffer();
			bs.append("		FROM CustOrder WHERE ");
			bs.append("		OPTIME >= STR_TO_DATE(?, '%Y-%m-%d') AND");
			bs.append("		OPTIME <= STR_TO_DATE(?, '%Y-%m-%d')");
			bs.append("		GROUP BY OPTIME");
			
			List<CustOrder> custorderlist = persistentService.find(bs.toString(), params.toArray());
			if(custorderlist != null && !custorderlist.isEmpty()){
				params.clear();
				params.add(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
				params.add(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_BOSSPAD);
				params.add(getYesterDay());
				params.add(nowDate);
				
				StringBuffer sb = new StringBuffer();
				sb.append("		SELECT A.ORDERID orderId,A.SYNCMODE sMode,A.OPCODE opCode,A.OPERATOR operator");
				sb.append("		,A.OPTIME opTime,W.OBJID salesId,A.CITY city");
				sb.append("		FROM BIZ_CUSTORDER A,BIZ_PORTAL_ORDER B,BIZ_APPLY_PRODUCT T,PRD_SALESPKG_KNOW W");
				sb.append("		WHERE 1 = 1 AND");
				sb.append("		A.ORDERID = B.ORDERID AND");
//				sb.append("		A.OPERATOR = P.OPERID AND");
				sb.append("		A.ORDERID = T.ORDERID AND");
				sb.append("		B.ORDERID = T.ORDERID AND");
				sb.append("		T.KNOWID = W.KNOWID AND");
				sb.append("		(B.STATUS = ? OR B.STATUS = ? ) AND");
				sb.append("		A.OPTIME >= STR_TO_DATE(?, '%Y-%m-%d') AND");
				sb.append("		A.OPTIME <= STR_TO_DATE(?, '%Y-%m-%d')");
				sb.append("		ORDER BY A.OPTIME DESC ");
				
				List<SaleOrderPointBean> datas = persistentService.find(sb.toString(), SaleOrderPointBean.class, params.toArray());
				if(datas != null && !datas.isEmpty()){
					hanlderData(datas,persistentService,nowDate);
				}
				handlerCustorder(custorderlist,persistentService,nowDate);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	
	private void handlerCustorder(List<CustOrder> orderlist,PersistentService persistentService,Date nowDate) throws Exception{
		for(CustOrder order:orderlist){
			PrvSalesPoint prvSalesPoint = new PrvSalesPoint();
			prvSalesPoint.setCity(order.getCity());
			prvSalesPoint.setOpcode(order.getOpcode());
			prvSalesPoint.setIsvalid(0l);
			Long wType = getWtype(order.getOperator(),persistentService);
			if(wType == null) continue;
			prvSalesPoint.setWtype(wType);
			List<PrvSalesPoint> tmps = persistentService.find(prvSalesPoint);
			if(tmps == null || tmps.isEmpty()) continue;
			for(PrvSalesPoint point:tmps){
				if(point.getSalesid() == null){
					prvSalesPoint = point;
					break;
				}
			}
			if(prvSalesPoint.getPoints() == null) continue;
			insertStaSalesPoint(prvSalesPoint,order,nowDate,persistentService);
		}
	}
	
	private void insertStaSalesPoint(PrvSalesPoint point,CustOrder order,Date nowDate,PersistentService persistentService) throws Exception{
		StaSalesPointOrder staSalesPointOrder = new StaSalesPointOrder();
		staSalesPointOrder.setOrderid(order.getId());
		List<StaSalesPointOrder> list = persistentService.find(staSalesPointOrder);
		if(list != null && !list.isEmpty()){
			staSalesPointOrder = list.get(0);
			staSalesPointOrder.setPoints(staSalesPointOrder.getPoints()+point.getPoints());
			persistentService.update(staSalesPointOrder);
		}else{
			staSalesPointOrder.setCity(order.getCity());
			staSalesPointOrder.setOperid(order.getOperator());
			staSalesPointOrder.setPoints(point.getPoints());
			staSalesPointOrder.setSday(nowDate);
			persistentService.save(staSalesPointOrder);
		}
		insertStaSalesPointOper(nowDate,persistentService,staSalesPointOrder);
	}
	
	private Long getWtype(Long operid,PersistentService persistentService) throws Exception{
		PrvOperinfo info = new PrvOperinfo();
		info.setId(operid);
		List<PrvOperinfo> list = persistentService.find(info);
		if(list == null || list.isEmpty()) return null;
		return Long.parseLong(list.get(0).getType());
	}
	
	private Date getYesterDay(){
		Calendar current = Calendar.getInstance();
		Calendar yesterday = Calendar.getInstance(); //昨天
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		return yesterday.getTime();
	}
	
	private void hanlderData(List<SaleOrderPointBean> datas,PersistentService persistentService,Date nowDate) throws Exception{
		//若存在昨天的操作订单,对其进行操作处理
		if(!datas.isEmpty()){
			for(SaleOrderPointBean data:datas){
				PrvSalesPoint prvSalesPoint = new PrvSalesPoint();
				if(StringUtils.isNotBlank(data.getOpCode())){
					prvSalesPoint.setOpcode(data.getOpCode());
				}
				if(data.getSalesId() != null){
					prvSalesPoint.setSalesid(data.getSalesId());
				}
				if(StringUtils.isNotBlank(data.getCity())){
					prvSalesPoint.setCity(data.getCity());
				}
				if(data.getOperator() != null){
					prvSalesPoint.setWtype(getWtype(data.getOperator(), persistentService));
				}
				prvSalesPoint.setIsvalid(0l);
				List<PrvSalesPoint> tmps = persistentService.find(prvSalesPoint);
				if(tmps == null || tmps.isEmpty()) continue;
				prvSalesPoint = tmps.get(0);
				//根据拿到的点数 对sta_sales_point_order进行插入
				StaSalesPointOrder order = insertStaSalesPoint(data,prvSalesPoint,nowDate,persistentService);
				insertStaSalesPointOper(nowDate,persistentService,order);
			}
			
		}
	}
	
	/**
	 * 插入sta_sales_point_order表
	 * @param data
	 * @param point
	 * @throws Exception 
	*/
	private StaSalesPointOrder insertStaSalesPoint(SaleOrderPointBean data,PrvSalesPoint point,Date date,PersistentService persistentService) throws Exception{
		StaSalesPointOrder staSalesPointOrder = new StaSalesPointOrder();
		staSalesPointOrder.setOrderid(data.getOrderId());
		List<StaSalesPointOrder> list = persistentService.find(staSalesPointOrder);
		if(list != null && !list.isEmpty()){
			staSalesPointOrder = list.get(0);
			staSalesPointOrder.setPoints(staSalesPointOrder.getPoints()+point.getPoints());
			persistentService.update(staSalesPointOrder);
		}else{
			staSalesPointOrder.setOperid(data.getOperator());
			staSalesPointOrder.setPoints(point.getPoints());
			staSalesPointOrder.setSday(date);
			staSalesPointOrder.setCity(data.getCity());
			persistentService.save(staSalesPointOrder);
		}
		
		return staSalesPointOrder;
	} 
	
	/**
	 * 将数据插入到sta_sales_point_oper表
	 * @param date
	 * @param persistentService
	 * @param order
	 * @throws Exception
	 */
	private void insertStaSalesPointOper(Date date,PersistentService persistentService,StaSalesPointOrder order) throws Exception{
		StaSalesPointOper oper = new StaSalesPointOper();
		oper.setCity(order.getCity());
		oper.setOperid(order.getOperid());
		oper.setSday(date);
		oper.setTotal(order.getPoints());
		persistentService.save(oper);
	}
}
