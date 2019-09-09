package com.maywide.biz.inter.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.pojo.dataReport.DataTarget;
import com.maywide.biz.inter.pojo.dataReport.TargetListResp;
import com.maywide.biz.inter.pojo.queTargetDetail.AddLossDetailBean;
import com.maywide.biz.inter.pojo.queTargetDetail.QueTargetDetailResp;
import com.maywide.biz.inter.pojo.reportDetail.DataTargetDetail;
import com.maywide.biz.inter.pojo.reportDetail.ReportDetailResp;
import com.maywide.biz.manage.targer.entity.SysKpiConfig;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

public class UserTargetManager {

	private Logger logger = LoggerFactory.getLogger(UserTargetManager.class);
	
	private static volatile UserTargetManager instance;
	
	public static UserTargetManager getInstance(){
		if(instance == null){
			synchronized (UserTargetManager.class) {
				if(instance == null){
					instance = new UserTargetManager();
				}
			}
		}
		return instance;
	}
	
	public void queUserKpis(String city,String gridcode,String dateType,
			String dateStr,String kpiType,PersistentService DAO,TargetListResp resp) throws Exception {
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		From SysKpiConfig");
		sb.append("		where isShow = ?");
		if(kpiType != null){
			sb.append("		and kpiType = ?");
		}
		sb.append("		and (city = ? or city = ?)");
		sb.append("		order by SORT");
		params.add("Y");
		if(kpiType != null){
			params.add(kpiType);
		}
		params.add("*");
		params.add(city);
		List<SysKpiConfig> configs = DAO.find(sb.toString(), params.toArray());
		if(configs == null || configs.isEmpty()) CheckUtils.checkNull(null, "该网格暂无配置的客户类标签");
		queUserTagList(city, configs, gridcode, dateType, dateStr,resp);
	}
	
	
	public void queUserTagList(String city,List<SysKpiConfig> configs,String gridcode,String dateType,
			String dateStr,TargetListResp resp) throws Exception{
		List params = new ArrayList();
		String formatStr = "NNN";
		StringBuffer sb = new StringBuffer();
		sb.append("		select etl.code2name(a.city,'PRV_CITY') city,a.whgridcode gridCode,a.whgridname gridName");
		sb.append("		,a.kpiid flag,b.kpiname flagName,a.kpivalue flagSum,a.kpiadd addNums,a.kpiloss lossNums,a.kpinetinc etincNums");
		sb.append("		from dm.dm_kpi_whgrid a");
		sb.append("		inner join frntpc.TR_KPICODE_CFG b");
		sb.append("		on a.kpiid = b.kpiid");
		sb.append(" 	and a.kpiid in (");
		for(int i = 0;i < configs.size();i++){
			params.add(configs.get(i).getKpiCode());
			sb.append("?");
			if(i != configs.size() -1){
				sb.append(",");
			}
		}
		sb.append("	)");
		sb.append("		and a.city = ?");
		sb.append("		and a.whgridcode = ?");
		sb.append("		and a.areaid = ?");
		if(dateType.equalsIgnoreCase("m")){
			sb.append("		and a.monthtype != ?");
			sb.append("		and a.monthtype = ?");
		}else{
			sb.append("		and a.stadate != ?");
			sb.append("		and a.stadate = ?");
		}
		params.add(city);
		params.add(gridcode);
		params.add("NNN");
		params.add(formatStr);
		params.add(dateStr);
		
		List<DataTarget> targets = SpringBeanUtil.getPersistentService().find(sb.toString(), 
				DataTarget.class, params.toArray());
		if(targets == null || targets.isEmpty())
			CheckUtils.checkNull(null, "根据当前网格编码查询不到对应的信息");
		for(DataTarget target:targets){
			for(SysKpiConfig config:configs){
				if(target.getFlag().equalsIgnoreCase(config.getKpiCode())){
					target.setIsDetail(config.getIsDetail());
					target.setSort(config.getSort());
					break;
				}
			}
		}
		fillEmptyTag(targets,configs);
		Collections.sort(targets, dataTarParator);
		resp.setDatas(targets);
	}
	
	private Comparator<DataTarget> dataTarParator = new Comparator<DataTarget>() {

		@Override
		public int compare(DataTarget o1, DataTarget o2) {
			if(o1.getSort() > o2.getSort()){
				return 1;
			}
			return -1;
		}
	};
	
	private void fillEmptyTag(List<DataTarget> datas,List<SysKpiConfig> configs){
		if(datas.size() == configs.size()) return;
		List<String> dataStrs = new ArrayList<String>();
		List<String> configStrs = new ArrayList<String>();
		for(DataTarget detail:datas){
			dataStrs.add(detail.getFlag());
		}
		for(SysKpiConfig config:configs){
			configStrs.add(config.getKpiCode());
		}
		configStrs.removeAll(dataStrs);
		List<DataTarget> fillDatas = new ArrayList<DataTarget>();
		for(String flag:configStrs){
			SysKpiConfig config = getSysKpiConfig(flag, configs);
			DataTarget detail = new DataTarget();
			detail.setCity(datas.get(0).getCity());
			detail.setFlag(config.getKpiCode());
			detail.setFlagName(config.getKpiName());
			detail.setGridCode(datas.get(0).getGridCode());
			detail.setGridName(datas.get(0).getGridName());
			detail.setIsDetail(config.getIsDetail());
			detail.setEtincNums(0d);
			detail.setSort(config.getSort());
			fillDatas.add(detail);
		}
		datas.addAll(fillDatas);
	}
	
	private SysKpiConfig getSysKpiConfig(String kpiCode,List<SysKpiConfig> configs){
		SysKpiConfig tmpConfig = null;
		for(SysKpiConfig config:configs){
			if(kpiCode.equals(config.getKpiCode())){
				tmpConfig = config;
				break;
			}
		}
		return tmpConfig;
	}
	
	
	public void queUserTagDetailList(LoginInfo loginInfo,String gridcode,String startTime,
			String endTime,String flag,String date,ReportDetailResp resp) throws Exception{
			List params  = new ArrayList();
			String formatStr = "NNN";
			String dateType = "";
			if(date.equalsIgnoreCase("D")){
				dateType = "stadate";
			}else{
				dateType = "monthtype";
			}
			StringBuffer sb = new StringBuffer();
			sb.append("		select a."+dateType+" month");
			sb.append("		,a.whgridcode gridCode,a.whgridname gridName,a.kpiid flag");
			sb.append("		,b.kpiname flagName,a.kpivalue flagSum,etl.code2name(a.city,'PRV_CITY') city");
			sb.append("		from dm.dm_kpi_whgrid a,frntpc.TR_KPICODE_CFG b");
			sb.append("		where a.kpiid = b.kpiid ");
			sb.append("		and a.city = ?");
			sb.append("		and a.kpiid = ?");
			sb.append("		and a."+dateType+" != ?");
			sb.append("		and a."+dateType+" >= ?");
			sb.append("		and a."+dateType+" <= ?");
			sb.append("		and a.whgridcode = ?");
			sb.append("		and a.areaid = ?");
			sb.append("		group by a."+dateType+",a.whgridcode,a.whgridname,a.kpiid,b.kpiname,a.kpivalue,a.city");
			sb.append("		order by a."+dateType+",a.whgridcode,a.whgridname,a.kpiid,b.kpiname,a.kpivalue,a.city");
			params.add(loginInfo.getCity());
			params.add(flag);
			params.add(formatStr);
			params.add(startTime);
			params.add(endTime);
			params.add(gridcode);
			params.add(formatStr);
			List<DataTargetDetail> datas =  SpringBeanUtil.getPersistentService().find(sb.toString(), DataTargetDetail.class, params.toArray());
			if(datas == null || datas.isEmpty())
				CheckUtils.checkNull(null, "当前时间段内暂无数据信息");
			resp.setDateStr(date);
			resp.setDatas(datas);
	}
	
	
	public void queAddLossDetailInfo(String city,String dateStr,String gridCode,QueTargetDetailResp resp,
			String flag,String type,int index,int pageSize,PersistentService DAO) throws Exception{
		SysKpiConfig kpiConfig = new SysKpiConfig();
		kpiConfig.setKpiCode(flag);
		List<SysKpiConfig> configs = DAO.find(kpiConfig);
		if(configs == null || configs.isEmpty()){
			CheckUtils.checkNull(null, "无法查找到当前指标,请确认!");
		}
		kpiConfig = configs.get(0);
		if(!kpiConfig.getIsDetail().equalsIgnoreCase("y")) CheckUtils.checkNull(null, "当前指标暂不支持查看新增/流失详情");
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.servid,SUM(a.FLAG) FLAG FROM eds.tw1_serv_addloss_grid a");
		sb.append("		WHERE a.tjdate = ?");
		params.add(dateStr);
		sb.append("		AND a.city = ?");
		params.add(city);
		sb.append("		AND a.whgridcode = ?");
		params.add(gridCode);
		if(StringUtils.isNotBlank(kpiConfig.getTjState())){
			sb.append("		and a.tjstate = ?");
			params.add(kpiConfig.getTjState());
		}
		if(StringUtils.isNotBlank(kpiConfig.getPermark())){
			sb.append("		and a.permark = ?");
			params.add(kpiConfig.getPermark());
		}
		if(StringUtils.isNotBlank(kpiConfig.getServType())){
			sb.append("		and a.servtype = ?");
			params.add(kpiConfig.getServType());
		}
		sb.append("		GROUP BY a.servid");
		sb.append("		HAVING SUM(a.FLAG) = ?");
		params.add(type);
		sb.append("		ORDER BY a.servid");
//		
		
		/*sb.append("		select b.servid,b.CUSTID custid,b.HOUSEID houseid,b.LOGICDEVNO logicdevno");
		sb.append("		,c.NAME custName,c.MOBILE mobile,d.whladdr address,SUM(a.FLAG) FLAG");
		sb.append("		from eds.tw1_serv_addloss_grid a,NODS.TO_SYS_SERVST_DG b,NODS.TO_SYS_CUST_DG c,NODS.TO_RES_HOUSE_DG d");
		sb.append("		where a.servid = b.SERVID");
		sb.append("		and b.CUSTID = c.CUSTID");
		sb.append("		and b.HOUSEID = d.houseid");
		sb.append("		and a.tjdate = ?");
		sb.append("		and a.city = ?");
		sb.append("		and a.whgridcode = ?");*/
//		params.add();
		/*sb.append("		GROUP BY b.SERVID,b.CUSTID,b.HOUSEID,b.LOGICDEVNO,c.NAME,c.MOBILE,d.whladdr");
		sb.append("		HAVING SUM(a.FLAG) = ?");
		sb.append("		ORDER BY b.SERVID,b.CUSTID,b.HOUSEID,b.LOGICDEVNO,c.NAME,c.MOBILE,d.whladdr");*/
	/*	Page page = new Page();
		page.setPageNo(index);
		page.setPageSize(pageSize);
		page = SpringBeanUtil.getPersistentService().find(page, sb.toString(), AddLossDetailBean.class, params.toArray());
		List<AddLossDetailBean> datas = page.getResult();*/
		List<AddLossDetailBean> datas = SpringBeanUtil.getPersistentService().find(sb.toString(), AddLossDetailBean.class, params.toArray());
		if(datas == null || datas.isEmpty()) CheckUtils.checkNull(null,"当前暂无该指标的新增/流失客户信息详情");
		sb = new StringBuffer();
		sb.append("		SELECT b.servid,b.CUSTID custid,b.HOUSEID houseid,b.LOGICDEVNO logicdevno");
		sb.append("		,c.NAME custName,c.MOBILE mobile,d.whladdr address");
		sb.append("		FROM NODS.TO_SYS_SERVST_DG b,NODS.TO_SYS_CUST_DG c,NODS.TO_RES_HOUSE_DG d");
		sb.append("		WHERE b.SERVID in (");
		for(int i = 0; i < datas.size() ;i++){
			sb.append(datas.get(i).getServid());
			if(i != datas.size() -1){
				sb.append(",");
			}
		}
		sb.append(")");
		sb.append("		AND b.CUSTID = c.CUSTID");
		sb.append("		AND b.HOUSEID = d.houseid");
		sb.append("		GROUP BY b.SERVID,b.CUSTID,b.HOUSEID,b.LOGICDEVNO,c.NAME,c.MOBILE,d.whladdr");
		sb.append("		ORDER BY b.SERVID,b.CUSTID,b.HOUSEID,b.LOGICDEVNO,c.NAME,c.MOBILE,d.whladdr");
		datas = SpringBeanUtil.getDBPersistentService().find(sb.toString(), AddLossDetailBean.class);
		resp.setDatas(datas);
		resp.setTotal(datas.size());
	}
}
