package com.maywide.biz.inter.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.pojo.queServOrdDetail.QueRepeatOrderDetailResp;
import com.maywide.biz.inter.pojo.queServOrdDetail.QueServiceOrderDetailReq;
import com.maywide.biz.inter.pojo.queServOrdDetail.QueServiceOrderDetailResp;
import com.maywide.biz.inter.pojo.queServOrdDetail.ServiceOutTimeOrderDetail;
import com.maywide.biz.inter.pojo.queServOrdDetail.ServiceRepeatOrderDetail;
import com.maywide.biz.inter.pojo.queServiceType.QueServiceTypeReq;
import com.maywide.biz.inter.pojo.queServiceType.QueServiceTypeResp;
import com.maywide.biz.inter.pojo.queServiceType.ServiceTypeBean;
import com.maywide.core.util.CheckUtils;

@Service
public class InterPrvDataService extends CommonService {

	
	/**
	 * 获取指标列表界面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queServiceType(QueServiceTypeReq req,QueServiceTypeResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		CheckUtils.checkEmpty(req.getStartTime(), "开始时间不能为空");
		CheckUtils.checkEmpty(req.getEndTime(), "结束时间不能为空");
		CheckUtils.checkEmpty(req.getGridcode(), "网格编码不能为空");
		
		List<ServiceTypeBean> datas = new ArrayList<ServiceTypeBean>();
		datas.addAll(getInstallOutNums(req.getStartTime(),req.getEndTime(),req.getGridcode()));
		datas.addAll(getInstallOutNotHandlerNums(req.getStartTime(),req.getEndTime(),req.getGridcode()));
		datas.addAll(getFaultOutNums(req.getStartTime(),req.getEndTime(),req.getGridcode()));
		datas.addAll(getFaultOutNotHandlerNums(req.getStartTime(),req.getEndTime(),req.getGridcode()));
		datas.addAll(getFaultRepeatNums(req.getStartTime(),req.getEndTime(),req.getGridcode()));
		datas.addAll(getBmyNums(req.getStartTime(),req.getEndTime(),req.getGridcode()));
		datas.addAll(getCompliainNums(req.getStartTime(),req.getEndTime(),req.getGridcode()));
		resp.setDatas(datas);
		return returnInfo;
	}
	
	/**
	 * 获取安装超时工单数
	 * @throws Exception 
	 */
	private List<ServiceTypeBean> getInstallOutNums(String startTime,String endTime,String gridcode) throws Exception{
		String sqlStr = constructSQLStr("OUTTIMENUMS", gridcode, startTime, endTime, "0","1","0","安装超时已处理工单数","1");
		return getDatas(sqlStr);
	}
	
	/**
	 * 获取安装超时未处理工单数
	 * @throws Exception 
	 */
	private List<ServiceTypeBean> getInstallOutNotHandlerNums(String startTime,String endTime,String gridcode) throws Exception{
		String sqlStr = constructSQLStr("OUTIMENOHANDLE ", gridcode, startTime, endTime, "0","1","0","安装超时未处理工单数","2");
		return getDatas(sqlStr);
	}
	
	/**
	 * 获取故障超时工单数
	 * @throws Exception 
	 */
	private List<ServiceTypeBean> getFaultOutNums(String startTime,String endTime,String gridcode) throws Exception{
		String sqlStr = constructSQLStr("OUTTIMENUMS", gridcode, startTime, endTime, "1","1","0","故障超时已处理工单数","1");
		return getDatas(sqlStr);
	}
	
	/**
	 * 获取故障超时未处理工单数
	 * @throws Exception 
	 */
	private List<ServiceTypeBean> getFaultOutNotHandlerNums(String startTime,String endTime,String gridcode) throws Exception{
		String sqlStr = constructSQLStr("OUTIMENOHANDLE ", gridcode, startTime, endTime, "1","1","0","故障超时未处理工单数","2");
		return getDatas(sqlStr);
	}
	
	/**
	 * 获取故障重复工单数
	 * @throws Exception 
	 */
	private List<ServiceTypeBean> getFaultRepeatNums(String startTime,String endTime,String gridcode) throws Exception{
		String sqlStr = constructSQLStr("REPEATNUMS ", gridcode, startTime, endTime, "1","1","1","故障重复工单数","0");
		return getDatas(sqlStr);
	}
	
	/**
	 * 获取不满意工单数
	 * @throws Exception 
	 */
	private List<ServiceTypeBean> getBmyNums(String startTime,String endTime,String gridcode) throws Exception{
		String sqlStr = constructSQLStr2("BMY_NUM",gridcode,startTime,endTime,"01","不满意工单数");
		return getDatas(sqlStr);
	}
	
	/**
	 * 获取投诉工单数
	 * @throws Exception 
	 */
	private List<ServiceTypeBean> getCompliainNums(String startTime,String endTime,String gridcode) throws Exception{
		String sqlStr = constructSQLStr2("COMPLAINNUMS ",gridcode,startTime,endTime,"02","投诉工单数");
		return getDatas(sqlStr);
	}
	
	private List<ServiceTypeBean> getDatas(String sqlStr) throws Exception{
		List<ServiceTypeBean> datas = SpringBeanUtil.getPersistentService().find(sqlStr, ServiceTypeBean.class);
		return datas;
	}
	
	/**
	 * @param fildName
	 * @param gridcode
	 * @param startDate
	 * @param endDate
	 * @param ishitch
	 * @return
	 */
	private String constructSQLStr(String fildName,String gridcode,String startDate,String endDate,String ishitch,String isDetail,
			String isRepeat,String tagName,String flag){
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT distinct(A.GRIDCODE) gridcode,A.GRIDNAME gridname,A.ISHITCH ishitch");
		sb.append("		,COUNT(A."+fildName+") sums");
		sb.append("		,'"+isDetail+"' detail");
		sb.append("		,'"+isRepeat+"' repeat");
		sb.append("		,'"+tagName+"' tagName");
		sb.append("		,'"+flag+"' flag");
		sb.append("		 FROM DM.TM_REPFAULT_MTH_CMMS_D_V A");
		sb.append("		WHERE 1 = 1");
		sb.append("		AND  A.GRIDCODE = '"+gridcode+"'");
		sb.append("		AND A.ISHITCH = '"+ishitch+"'");
		sb.append("		AND A.STADATE >='"+startDate+"'");
		sb.append("		AND A.STADATE <='"+endDate+"'");
		sb.append("		GROUP BY A.GRIDCODE,A.GRIDNAME,A.ISHITCH");
		System.out.println(sb);
		return sb.toString();
	}
	
	
	private String constructSQLStr2(String fildName,String gridcode,String startDate,
			String endDate,String ishitch,String tagName){
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT distinct(A.GRIDCODE) gridcode ,A.GRIDNAME gridname");
		sb.append("		,COUNT(A."+fildName+") sums");
		sb.append("		,'0' detail");
		sb.append("		,'0' repeat");
		sb.append("		,'"+ishitch+"' ishitch");
		sb.append("		,'"+tagName+"' tagName");
		sb.append("		 FROM DM.TM_GRID_SERV_KPI_CMMS_D_V A");
		sb.append("		WHERE 1 = 1");
		sb.append("		AND  A.GRIDCODE = '"+gridcode+"'");
		sb.append("		AND A.STADATE >='"+startDate+"'");
		sb.append("		AND A.STADATE <='"+endDate+"'");
		sb.append("		GROUP BY A.GRIDCODE,A.GRIDNAME");
		System.out.println(sb);
		return sb.toString();
	}
	
	
	/**
	 * 查看超时工单详情接口
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo queServiceOrderDetail(QueServiceOrderDetailReq req,QueServiceOrderDetailResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		List params = new ArrayList();
		String hitchName = req.getIsHitch() == "0"?"安装单":"故障单";
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.STADATE staTime, A.GRIDCODE gridCode, A.GRIDNAME gridName,A.ORDERCODE orderCode,");
		sb.append("		A.ORDER_STATE_NAME orderStatus, A.CUSTID custId,A.ACCEPT_DATE sendTime,");
		sb.append("		A.FINISH_DATE finisTime, A.ARCH_DATE archTime,A.LIMIT_DATE limitTime, A.ISHITCH isHitch,A.FLAG flag");
		sb.append("		FROM DM.TM_OVERTIME_DETAIL_CMMS_D_V A");
		sb.append("		WHERE  1 = 1");
		sb.append("		AND A.STADATE = ?");
		params.add(req.getStatime());
		sb.append("		AND A.GRIDCODE = ?");
		params.add(req.getGridcode());
		sb.append("		AND A.ISHITCH = ?");
		params.add(hitchName);
		sb.append("		AND A.FLAG  = ?");
		params.add(req.getFlag());
		
		List<ServiceOutTimeOrderDetail> datas = SpringBeanUtil.getPersistentService().find(sb.toString(), ServiceOutTimeOrderDetail.class, params.toArray());
		resp.setDatas(datas);
		return returnInfo;
	}
	
	/**
	 * 查看重复工单详情接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queRepeatOrderDetail(QueServiceOrderDetailReq req,QueRepeatOrderDetailResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		List params = new ArrayList();
		String hitchName = req.getIsHitch() == "0"?"安装单":"故障单";
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.STADATE staTime, A.GRIDCODE gridCode, A.GRIDNAME gridName,A.ORDERCODE orderCode,");
		sb.append("		A.ISHITCH isHitch,A.ACCEPT_DATE sendTime,A.FINISH_DATE finisTime,A.ORDER_STATE_NAME orderStatus,");
		sb.append("		A.CUSTID custId, A.NAME custName,A.ADDRESS address,A.TRACK_STAFF_ID trackStaffId");
		sb.append("		FROM DM.TM_REPFAULT_DETAIL_CMMS_D_V A");
		sb.append("		WHERE 1 = 1");
		sb.append("		AND A.STADATE = ?");
		params.add(req.getStatime());
		sb.append("		AND A.GRIDCODE = ?");
		params.add(req.getGridcode());
		sb.append("		AND A.ISHITCH = ?");
		params.add(hitchName);
		
		List<ServiceRepeatOrderDetail> datas = SpringBeanUtil.getPersistentService().find(sb.toString(), ServiceRepeatOrderDetail.class, params.toArray());
		resp.setDatas(datas);
		return returnInfo;
	}
}
