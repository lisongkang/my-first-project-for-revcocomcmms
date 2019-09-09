package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.TokenReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.pojo.queAchievementSalary.AchievementSalaryPositionBean;
import com.maywide.biz.inter.pojo.queAchievementSalary.BaseKpiBean;
import com.maywide.biz.inter.pojo.queAchievementSalary.EmployeeSalaryKpiBean;
import com.maywide.biz.inter.pojo.queAchievementSalary.KpiBean;
import com.maywide.biz.inter.pojo.queAchievementSalary.QueAchievementSalaryReq;
import com.maywide.biz.inter.pojo.queAchievementSalary.QueAchievementSalaryResp;
import com.maywide.biz.wages.entity.WagesCase;
import com.maywide.biz.wages.entity.WagesKpiDetail;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
public class MeritPayService extends CommonService {

	public TokenReturnInfo queAchievementSalary(QueAchievementSalaryReq req) throws Exception {
		TokenReturnInfo result = new TokenReturnInfo();
		result.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		result.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, "用户未登录或登录信息已过期!");
		CheckUtils.checkEmpty(req.getGridcode(), "网格编码不能为空!");
		CheckUtils.checkNull(req.getOperid(), "工号信息不能为空!");
		AchievementSalaryPositionBean salartPositionBean = queOperAchievementSalaryPosition(loginInfo.getCity(),
				loginInfo.getAreaid().toString(), req.getGridcode(), req.getMonth(), req.getOperid());
		QueAchievementSalaryResp resp = new QueAchievementSalaryResp();
		resp.setPublishStatus(salartPositionBean.getStatus());
		String type = salartPositionBean.getPositionType();
		String monthAcount = salartPositionBean.getAmount().toString();
		String bonusDescript = queBonusDescript(type);
		if (type.equals("0")) {
			resp.setBaseKpiList(queRespList(salartPositionBean.getCaseId()));
		} else {
			resp.setBaseKpiList(queManageRespList(req.getOperid(),loginInfo.getCity(),req.getMonth(),salartPositionBean.getCaseId()));
			// resp.setManageKpi(queManageKpiBean(req.getMonth(),req.getOperid(),
			// req.getGridcode()));
		}
		resp.setOperType(type);
		resp.setAchievementLevel(salartPositionBean.getLevel());
		resp.setAchievementSalary(monthAcount);
		resp.setAchievementScore(salartPositionBean.getCents().toString());
		resp.setBonusDescript(bonusDescript);
		result.setData(resp);
		return result;
	}
	
	private List<BaseKpiBean> queManageRespList(Long operid,String city,String dateMonth,Long caseId) throws Exception{
		List<BaseKpiBean> datas = new ArrayList();
		datas.addAll(manageOtherKpis(caseId));
		WagesCase wagesCase = new WagesCase();
		wagesCase.setCity(city);
		wagesCase.setCurrentBy(operid);
		wagesCase.setDateMonth(dateMonth);
		/*List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("	select * from wages_case");
		sql.append("	where current_by = ?");
		params.add(operid);
		sql.append("	and date_month = ?");
		params.add(dateMonth);
		sql.append("	and status = ?");
		params.add("1");*/
		List<WagesCase> caseList = getDAO().find(wagesCase);
		if(null != caseList && !caseList.isEmpty()) {
			BaseKpiBean employBeanKpiBean = operScoreKpi(caseList);
			if(null != employBeanKpiBean) {
				datas.add(0, employBeanKpiBean);
			}
		}
		return datas;
	}
	
	private BaseKpiBean operScoreKpi(List<WagesCase> caseList) throws Exception {
		if(null == caseList || caseList.isEmpty()) return null;
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("	select a.*,c.`name`,c.operid,b.gridcode");
		sql.append("	from wages_kpi_header  a,wages_case b,prv_operator c");
		sql.append("	where a.case_id = b.case_id");
		sql.append("	and b.operid = c.operid");
		sql.append("	and b.case_id in (");
		for(int i = 0;i < caseList.size();i++) {
			sql.append("?");
			params.add(caseList.get(i).getId());
			if(i != caseList.size() - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		sql.append("	order by a.cents desc");
		List<EmployeeSalaryKpiBean> employList = getDAO().find(sql.toString(), EmployeeSalaryKpiBean.class, params.toArray());
		if(null == employList || employList.isEmpty()) {
			return null;
		}
		List<KpiBean> employKpiList = new ArrayList<KpiBean>();
		for(EmployeeSalaryKpiBean bean:employList) {
//			KpiBean kpiBean = new KpiBean(bean.getName(),bean.getCents().toString());
			KpiBean kpiBean = new KpiBean(bean.getName(),bean.getCents().toString(),bean.getOperid(),bean.getGridcode());
			employKpiList.add(kpiBean);
		}
		return new BaseKpiBean("网格绩效","85", "0", employKpiList);
	}
	
	private List<BaseKpiBean> manageOtherKpis(Long caseId) throws Exception{
		return queRespList(caseId);
	}
	
	
	
	
	private List<BaseKpiBean> queRespList(Long caseId) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("	select * from wages_kpi_detail where case_id = ?");
		sql.append("	group by kpi_code ");
		sql.append("	order by kpi_parent_sort,kpi_sort");
		List<WagesKpiDetail> datas = getDAO().find(sql.toString(),WagesKpiDetail.class ,caseId);
		if(null == datas || datas.isEmpty()) {
			CheckUtils.checkNull(null, "当前条件下查找不到对应的绩效数据!");
		}
		return getList4WagesKpiDetails(datas);
	}
	
	private List<BaseKpiBean> getList4WagesKpiDetails(List<WagesKpiDetail> detailDatas){
		Map<String,List<KpiBean>> baseKpiMap = new LinkedHashMap<String,List<KpiBean>>();
		for(WagesKpiDetail details: detailDatas) {
			String parentName = details.getKpiParentName();
			List<KpiBean> kpiBeanList = baseKpiMap.get(parentName);
			if(null == kpiBeanList) {
				kpiBeanList = new ArrayList<KpiBean>();
				baseKpiMap.put(parentName, kpiBeanList);
			}
			kpiBeanList.add(getKpiBean(details));
		}
		List<BaseKpiBean> baseKpiBeanList = new ArrayList<BaseKpiBean>();
		Iterator<Entry<String,List<KpiBean>>> iterator = baseKpiMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String,List<KpiBean>> entry = iterator.next();
			String keyValue = entry.getKey();
			BaseKpiBean baseKpiBean = null;
			for(WagesKpiDetail detail: detailDatas) {
				if(detail.getKpiParentName().equalsIgnoreCase(keyValue)) {
					baseKpiBean = new BaseKpiBean(detail.getKpiParentName(), detail.getKpiParentScore(), detail.getKpiParentType());
					break;
				}
			}
			if(baseKpiBean != null) {
				baseKpiBean.setChildKpis(entry.getValue());
				baseKpiBeanList.add(baseKpiBean);
			}
		}
		return baseKpiBeanList;
	}
	
	
	
	
	
	private KpiBean getKpiBean(WagesKpiDetail detail){
		KpiBean kpiBean = new KpiBean(detail.getKpiName(), detail.getKpiTargetValue(), 
				detail.getKpiResultValue(), detail.getKpiWeightValue(), detail.getKpiScore(), detail.getKpiScoreDesc());
		return kpiBean;
	}
	

	private AchievementSalaryPositionBean queOperAchievementSalaryPosition(String city, String areaid, String gridcode,
			String dateMonth, Long operid) throws Exception {
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("	select c.position_id positionId, c.position_name,c.position_type positionType,");
		sql.append("	a.case_id caseId,a.case_type caseType,a.date_month dateMonth,a.status ,a.operid,");
		sql.append("	b.amount,b.notes,b.cents,b.level");
		sql.append("	from wages_case a,wages_kpi_header b,wages_position c");
		sql.append("	where a.case_id = b.case_id");
		sql.append("	and a.position_id = c.position_id");
		sql.append("	and a.city = ?");
		params.add(city);
		sql.append("	and (a.areaid = ? or a.areaid = '*' )");
		params.add(areaid);
//		sql.append("	and a.gridcode = ?");
//		params.add(gridcode);
//		sql.append("	and a.status = ?");
//		params.add("1");
		sql.append("	and a.date_month = ?");
		params.add(dateMonth);
		sql.append("	and a.operid = ?");
		params.add(operid);
		List<AchievementSalaryPositionBean> datas = getDAO().find(sql.toString(), AchievementSalaryPositionBean.class,
				params.toArray());
		if (null == datas || datas.isEmpty()) {
			CheckUtils.checkNull(null, "当前条件下查找不到对应的绩效数据!");
		}
		return datas.get(0);
	}

	private String queBonusDescript(String type) {
		if(type.equals("0")) {
			return "绩效奖金=绩效奖金基数*绩效等级(A,B,C,D,E)/n绩效奖金基数=加权终端数*包干单数*岗位系数/n绩效等级原则=按KPI考核得分排名强制分布/n";
		}else {
			return "绩效奖金=绩效奖金基数*绩效等级(A,B,C,D,E)/n绩效奖金基数=加权终端数*包干单数*岗位系数/n绩效等级原则=按KPI考核得分排名强制分布/nKPI考核得分=所辖网格绩效平均分(占85%)+其他工作考核(占15%)";
		}
	}

}
