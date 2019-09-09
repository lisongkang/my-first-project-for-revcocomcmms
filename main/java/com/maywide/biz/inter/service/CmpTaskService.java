package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.cmptask.CmpBaseResp;
import com.maywide.biz.inter.pojo.cmptask.CmpTaskCountResp;
import com.maywide.biz.inter.pojo.cmptask.QueTaskCountResp;
import com.maywide.biz.inter.pojo.dataReport.GridInfoResp;
import com.maywide.biz.inter.pojo.queactivitylist.CmpActivityInfo;
import com.maywide.biz.inter.pojo.queactivitylist.CmpActivityListResp;
import com.maywide.biz.inter.pojo.queactivitylist.QueActivityListReq;
import com.maywide.biz.inter.pojo.queactivitylist.QueActivityListResp;
import com.maywide.biz.inter.pojo.quetasklist.CmpTaskInfo;
import com.maywide.biz.inter.pojo.quetasklist.CmpTaskListResp;
import com.maywide.biz.inter.pojo.quetasklist.PrdRuleMenu;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListByCustidReq;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListByCustidResp;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListReq;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListResp;
import com.maywide.biz.inter.pojo.taskfeedback.TaskFeedbackReq;
import com.maywide.biz.inter.pojo.taskfeedback.TaskFeedbackResp;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
@SuppressWarnings("unchecked")
public class CmpTaskService extends CommonService {

	@Autowired
	private InterPrdDataService interPrdDataService;

	/**
	 * 查询活动任务数
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queTaskCount(QueTaskCountResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			return returnInfo;
		}
		String gridCodes = getGridCodes();
		Map<String, String> map = new HashMap<String, String>();
		map.put("city", loginInfo.getCity());
		map.put("gridid", gridCodes);

		String dataResultStr = getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.TASK_COUNT, map, true);
		CmpTaskCountResp result = (CmpTaskCountResp) BeanUtil.jsonToObject(dataResultStr, CmpTaskCountResp.class);
		resp.setNums(result.getNums());
		return returnInfo;
	}

	private String getGridCodes() throws Exception {
		ArrayList<GridInfoResp> grids = new ArrayList<GridInfoResp>();
		interPrdDataService.queGridDataInfo(grids);

		StringBuilder sb = new StringBuilder();
		if (grids.size() >= 1) {
			sb.append(grids.get(0).getGridCode());
		}
		for (int i = 1; i < grids.size(); i++) {
			sb.append(",").append(grids.get(i).getGridCode());
		}
		return sb.toString();
	}

	/**
	 * 查询活动列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queActivityList(QueActivityListReq req, QueActivityListResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		Map<String, String> map = new HashMap<String, String>();
		map.put("city", loginInfo.getCity());
		map.put("gridid", req.getGridCode());
		if (req.getIndex() <= -1 || req.getIssum()) {
			map.put("ispage", "0");
		} else {
			map.put("ispage", "1");
		}
		map.put("pageidx", req.getIndex() + "");
		map.put("pagesize", req.getPageSize() + "");
		map.put("status", req.getStatus());

		String dataResultStr = getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.ACTIVITY_LIST, map, true);
		CmpActivityListResp result = (CmpActivityListResp) BeanUtil.jsonToObject(dataResultStr, CmpActivityListResp.class);
		if (req.getIssum()) {
			handleActivityInfo(req, resp, result);
		} else {
			resp.setTotal(result.getTotalcount());
			resp.setDatas(result.getData());
		}

		return returnInfo;
	}

	private void handleActivityInfo(QueActivityListReq req, QueActivityListResp resp, CmpActivityListResp result) {
		if (result.getData() == null || result.getData().size() == 0) {
			resp.setTotal(0);
			resp.setDatas(result.getData());
			return;
		}
		List<CmpActivityInfo> list = new ArrayList<CmpActivityInfo>();

		Map<String, CmpActivityInfo> idMap = new HashMap<String, CmpActivityInfo>();
		for (CmpActivityInfo info : result.getData()) {
			CmpActivityInfo mapInfo = idMap.get(info.getActivityid());
			if (mapInfo == null) {
				idMap.put(info.getActivityid(), info);
				list.add(info);
				info.setTotal(info.getNums());
				if (!BizConstant.CmpTaskStatus.UNTOUCH.equals(info.getStatus())) {
					info.setNums(0);
				}
			} else {
				if (BizConstant.CmpTaskStatus.UNTOUCH.equals(info.getStatus())) {
					mapInfo.setNums(info.getNums());
				}
				mapInfo.setTotal(mapInfo.getTotal() + info.getNums());
			}
		}
		resp.setTotal(list.size());
		resp.setDatas(list);
	}

	/**
	 * 查询网格任务列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queTaskList(QueTaskListReq req, QueTaskListResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getActivityid(), "活动编码不能为空");

		Map<String, String> map = new HashMap<String, String>();
		map.put("gridid", req.getGridCode());
		map.put("activityid", req.getActivityid());
		if (req.getIndex() <= -1) {
			map.put("ispage", "0");
		} else {
			map.put("ispage", "1");
		}
		map.put("pageidx", req.getIndex() + "");
		map.put("pagesize", req.getPageSize() + "");
		map.put("status", req.getStatus());
		if (StringUtils.isNotBlank(req.getAddress())) {
			map.put("address", req.getAddress());
		}

		String dataResultStr = getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.TASK_LIST, map, true);
		CmpTaskListResp result = (CmpTaskListResp) BeanUtil.jsonToObject(dataResultStr, CmpTaskListResp.class);
		resp.setActivityid(result.getActivityid());
		resp.setGridid(result.getGridid());
		resp.setTotal(result.getTotalcount());
		resp.setDatas(result.getData());

		return returnInfo;
	}

	/**
	 * 营销任务列表查询接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queTaskListByCustid(QueTaskListByCustidReq req, QueTaskListByCustidResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(), "客户编码不能为空");

		Map<String, String> map = new HashMap<String, String>();
		map.put("custid", req.getCustid());
		if (req.getIndex() <= -1) {
			map.put("ispage", "0");
		} else {
			map.put("ispage", "1");
		}
		map.put("pageidx", req.getIndex() + "");
		map.put("pagesize", req.getPageSize() + "");
		map.put("status", req.getStatus());
		String areaid = StringUtils.isNotBlank(req.getCustAreaid())?req.getCustAreaid():loginInfo.getAreaid().toString();
		String dataResultStr = getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.TASK_LIST_BY_CUSTID, map, true);
		QueTaskListByCustidResp result = (QueTaskListByCustidResp) BeanUtil.jsonToObject(dataResultStr, QueTaskListByCustidResp.class);
		if (result.getData() != null && !result.getData().isEmpty()) {
			findTaskPrdRuleMenu(result.getData(), areaid);
		}
		resp.setCustid(result.getCustid());
		resp.setTotalcount(result.getTotalcount());
		resp.setData(result.getData());
		return returnInfo;
	}

	private void findTaskPrdRuleMenu(List<CmpTaskInfo> list, String areaid) throws Exception {
		Set<String> set = new HashSet<String>();
		for (CmpTaskInfo info : list) {
			if (StringUtils.isNotBlank(info.getSalespkgid())) {
				set.add(info.getSalespkgid());
			}
		}
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT p.value,p.message,m.name,m.linkurl,m.iconid,  ");
		sql.append("m.oflag1,m.oflag2,m.memo,m.bizcode,m.atype  ");
		sql.append("FROM  prv_prd_rule p  ");
		sql.append("LEFT JOIN prv_menudef m ON m.menuid = p.jumpmenuid ");
		sql.append("WHERE exptime > SYSDATE() AND areaid = ?  ");
		params.add(areaid);
		sql.append("AND value in ( '");
		sql.append(StringUtils.join(set, "','"));
		sql.append("') ");

		List<PrdRuleMenu> results = getDAO().find(sql.toString(), PrdRuleMenu.class, params.toArray());

		for (CmpTaskInfo info : list) {
			info.setPrdrulemenu(findPrdRuleMenu(results, info.getSalespkgid()));
		}
	}

	private PrdRuleMenu findPrdRuleMenu(List<PrdRuleMenu> list, String code) {
		if (list != null && list.size() > 0) {
			for (PrdRuleMenu menu : list) {
				if (menu.getValue().equals(code)) {
					return menu;
				}
			}
		}
		return null;
	}

	/**
	 * 任务反馈
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo taskFeedback(TaskFeedbackReq req, TaskFeedbackResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getActivityid(), "活动编码不能为空");
		CheckUtils.checkEmpty(req.getPolicyid(), "策略编码不能为空");
		CheckUtils.checkEmpty(req.getCompid(), "组件编码不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "客户编码不能为空");
		CheckUtils.checkEmpty(req.getItemid(), "反馈结果不能为空");
		CheckUtils.checkEmpty(req.getItemid(), "原状态不能为空");

		Map<String, String> map = new HashMap<String, String>();
		map.put("activityid", req.getActivityid());
		map.put("policyid", req.getPolicyid());
		map.put("compid", req.getCompid());
		map.put("custid", req.getCustid());
		map.put("itemid", req.getItemid());
		map.put("ostatus", req.getOstatus());
		map.put("rdesc", req.getRdesc());
		map.put("memo", req.getMemo());

		String dataResultStr = getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.TASK_FEEDBACK, map, true);
		CmpBaseResp result = (CmpBaseResp) BeanUtil.jsonToObject(dataResultStr, CmpBaseResp.class);
		resp.setCode(result.getCode());
		resp.setMsg(result.getMsg());
		return returnInfo;
	}

}
