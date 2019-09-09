package com.maywide.biz.inter.service;

import java.util.ArrayList;
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
import com.maywide.biz.index.achimendetail.entity.AchimenIndexDetail;
import com.maywide.biz.inter.pojo.queIndexDataList.QueIndexDataListResp;
import com.maywide.biz.inter.pojo.queIndexDataList.QueIndexDataObjet;
import com.maywide.biz.inter.pojo.queIndexDataList.QueIndexDataParentObjet;
import com.maywide.biz.inter.pojo.queIndexReportDetail.QueIndexReportDetailReq;
import com.maywide.biz.inter.pojo.queIndexReportDetail.QueIndexReportDetailResp;
import com.maywide.biz.inter.pojo.queIndexSummaryList.QueIndexSummaryBean;
import com.maywide.biz.inter.pojo.queIndexSummaryList.QueIndexSummaryListReq;
import com.maywide.biz.inter.pojo.queIndexSummaryList.QueIndexSummaryListResp;
import com.maywide.core.util.CheckUtils;

@Service
public class InterIndexAchimenService extends CommonService {

	public ReturnInfo queIndexDataList(QueIndexDataListResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add("KPI_TYPE_NAME");
		params.add("1");
		List<QueIndexDataObjet> datas = getDAO().find(constructSql(loginInfo), QueIndexDataObjet.class, params.toArray());
		if(datas == null || !datas.isEmpty()){
			CheckUtils.checkNull(null, "当前未配置任何指标内容");
		}
		initDatas(datas,resp);
		return returnInfo;
	}
	
	private String constructSql(LoginInfo loginInfo){
		StringBuffer sb = new StringBuffer();
		sb.append("		select a.kpicode,a.kpiname,a.iconame,a.kpitype,a.city,a.kpidate,b.mname typename,a.explain");
		sb.append("		from ACHIMEN_INDEX_MODEL a");
		sb.append("		left join prv_sysparams b");
		sb.append("		on a.kpitype = b.mcode");
		sb.append("		and a.city = ?");
		sb.append("		and b.gcode = ?");
		sb.append("		and a.kpistatus = ?");
		return sb.toString();
	}
	
	private void initDatas(List<QueIndexDataObjet> datas,QueIndexDataListResp resp){
		Map<String,List<QueIndexDataObjet>> map = new HashMap<String, List<QueIndexDataObjet>>();
		for(QueIndexDataObjet object:datas){
			List<QueIndexDataObjet> list = map.get(object.getKpitype());
			if(list == null){
				list = new ArrayList<QueIndexDataObjet>();
				map.put(object.getKpitype(), list);
			}
			list.add(object);
		}
		if(!map.isEmpty()){
			List<QueIndexDataParentObjet> list = new ArrayList<QueIndexDataParentObjet>();
			Iterator<Entry<String, List<QueIndexDataObjet>>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, List<QueIndexDataObjet>> entry = iterator.next();
				String typename = entry.getValue().get(0).getTypename();
				list.add(new QueIndexDataParentObjet(entry.getKey(), typename, entry.getValue()));
			}
			resp.setDatas(list);
		}
	}

	public ReturnInfo queIndexSummaryList(QueIndexSummaryListReq req,QueIndexSummaryListResp resp) throws Exception {
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getKpicode(),"指标编码不能为空!");
		CheckUtils.checkEmpty(req.getKpitype(),"指标类型不能为空!");
		
		List params = new ArrayList();
		String sql = constructSql(loginInfo, req, params);
		
		List<QueIndexSummaryBean> datas = getDAO().find(sql, QueIndexSummaryBean.class, params.toArray());
		if(datas == null || datas.isEmpty()){
			CheckUtils.checkNull(null, "当前指标信息为空!");
		}
		resp.setDatas(datas);
		return returnInfo;
	}
	
	private String constructSql(LoginInfo loginInfo,QueIndexSummaryListReq req,List params){
		
		StringBuffer sb = new StringBuffer();
		sb.append("		select a.city,a.gridcode,a.selffield selfname,a.datevalue");
		sb.append("		,a.selfvalue selfvalue,b.gtype gridtype,b.gridname gridname,d.mname cityname");
		sb.append("		from ACHIMEN_INDEX_DETAIL a");
		sb.append("		inner join biz_grid_info b");
		sb.append("		on a.gridcode = b.gridcode");
		sb.append("		inner join ACHIMEN_INDEX_MODEL c");
		sb.append("		on a.kpicode = c.kpicode");
		sb.append("		left join prv_sysparams d");
		sb.append("		on a.city = d.mcode");
		sb.append("		and a.kpicode = ?");
		params.add(req.getKpicode());
		sb.append("		and c.kpitype = ?");
		params.add(req.getKpitype());
		if(StringUtils.isNotBlank(req.getGridcode())){
			sb.append("		and a.city = b.city");
			sb.append("		b.gridcode = ?");
			params.add(req.getGridcode());
			sb.append("		a.city = ?");
			params.add(loginInfo.getCity());
		}else{
			sb.append("		b.previd = ?");
			params.add(-1);
		}
		sb.append("		and a.datetype = (");
		sb.append("		select distinct(max(t.datevalue))");
		sb.append("		from ACHIMEN_INDEX_DETAIL t");
		sb.append("		where t.datetype = ?");
		if(StringUtils.isNotBlank(req.getGridcode())){
			sb.append("		and t.city = ? )");
			params.add(loginInfo.getCity());
			sb.append("		and t.gridcode = ?");
			params.add(req.getGridcode());
		}
		sb.append("	)");
		sb.append("		and d.gcode = ?");
		params.add("PRV_CITY");
		return sb.toString();
	}
	
	
	public ReturnInfo queIndexReportDetail(QueIndexReportDetailReq req,QueIndexReportDetailResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getKpicode(), "指标编码不能为空!");
		CheckUtils.checkEmpty(req.getGridcode(), "网格编码不能为空!");
		
		List params = new ArrayList();
		String sql = constructSql(req, params);
		
		List<AchimenIndexDetail> datas = getDAO().find(sql, AchimenIndexDetail.class, params.toArray());
		if(datas == null || datas.isEmpty()){
			CheckUtils.checkNull(null, "当前网格下指标数据为空!");
		}
		resp.setDatas(datas);
		return returnInfo;
	}
	
	private String constructSql(QueIndexReportDetailReq req,List params){
		StringBuffer sb = new StringBuffer();
		sb.append("		select a.kpicode,a.kpivalue,a.gridcode,a.datetype,a.datevalue");
		sb.append("		a.increase,a.loss,a.growth,a.ringratio");
		sb.append("		from ACHIMEN_INDEX_DETAIL a");
		sb.append("		where a.kpicode = ?");
		params.add(req.getKpicode());
		sb.append("		and a.gridcode = ?");
		params.add(req.getGridcode());
		sb.append("		and a.datetype = ?");
		params.add(req.getDatetype());
		sb.append("		and a.datevalue <= ?");
		params.add(req.getEndtime());
		sb.append("		and a.datevalue >= ?");
		params.add(req.getStarttime());
		sb.append("		order by a.datevalue desc");
		return sb.toString();
	}
}
