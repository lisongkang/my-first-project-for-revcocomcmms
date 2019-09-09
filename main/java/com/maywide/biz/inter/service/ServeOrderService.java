package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizOpcode;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.BizBossorderImage;
import com.maywide.biz.inter.pojo.bizservorder.BizChangeOrderReq;
import com.maywide.biz.inter.pojo.bizservorder.BizDealOrderReq;
import com.maywide.biz.inter.pojo.bizservorder.BizDelayOrderReq;
import com.maywide.biz.inter.pojo.bizservorder.BizGridApplyReq;
import com.maywide.biz.inter.pojo.bizservorder.BizOrderOperMemoReq;
import com.maywide.biz.inter.pojo.bizservorder.ResInfo;
import com.maywide.biz.inter.pojo.quedealorderreason.DealOrderReason;
import com.maywide.biz.inter.pojo.quedealorderreason.QueDealOrderReasonReq;
import com.maywide.biz.inter.pojo.quegridapply.GridApplyRecord;
import com.maywide.biz.inter.pojo.quegridapply.QueGridApplyInfoResp;
import com.maywide.biz.inter.pojo.quegridapply.QueGridApplyReq;
import com.maywide.biz.inter.pojo.queordertotal.QueOrderTotalReq;
import com.maywide.biz.inter.pojo.queordertotal.QueOrderTotalResp;
import com.maywide.biz.inter.pojo.querydepartment.DepartmentInterInfo;
import com.maywide.biz.inter.pojo.queservorder.QueServOrderReq;
import com.maywide.biz.inter.pojo.queservorder.QueServOrderResp;
import com.maywide.biz.inter.pojo.queservorder.ServOrderBean;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
@SuppressWarnings("unchecked")
public class ServeOrderService extends CommonService {

	@Autowired
	InterServeOrderService interServeOrderService;

	/**
	 * 装维工单查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queServOrder(QueServOrderReq req, QueServOrderResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");

		if (StringUtils.isNotBlank(req.getSearchTxt())) {
			if (Pattern.compile("^\\d+$").matcher(req.getSearchTxt()).matches()) {
				req.setCustorderid(req.getSearchTxt());
			} else {
				req.setName(req.getSearchTxt());
			}
		}
		req.setDeptid(interServeOrderService.getDeptsStrForLevel(loginInfo));

		String result = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_SERVORDER, req, loginInfo);
		QueServOrderResp bossResp = (QueServOrderResp) BeanUtil.jsonToObject(result, QueServOrderResp.class);
		BeanUtils.copyProperties(resp, bossResp);
		queExemptStatus(resp.getOrders());
		return returnInfo;
	}

	private void queExemptStatus(List<ServOrderBean> orders) throws Exception {
		if (orders == null || orders.isEmpty()) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (ServOrderBean order : orders) {
			sb.append(order.getServorderid()).append(",");
		}
		sb.setLength(sb.length() - 1);
		List<BizBossorderImage> imgs = interServeOrderService.queExemptImgs(sb.toString());
		if (imgs != null) {
			for (ServOrderBean order : orders) {
				for (BizBossorderImage img : imgs) {
					if (order.getServorderid().equals(String.valueOf(img.getServorderid()))) {
						order.setExempt(true);
						break;
					}
				}
			}

		}
	}

	/**
	 * 查询竣工工单接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queServOrderLog(QueServOrderReq req, QueServOrderResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");

		CheckUtils.checkEmpty(req.getFromdate(), "开始时间不能为空");
		CheckUtils.checkEmpty(req.getTodate(), "结束时间不能为空");

		req.setDeptid(String.valueOf(loginInfo.getDeptid()));
		req.setOperid(String.valueOf(loginInfo.getOperid()));

		String result = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_SERVORDER_LOG, req, loginInfo);
		QueServOrderResp bossResp = (QueServOrderResp) BeanUtil.jsonToObject(result, QueServOrderResp.class);
		BeanUtils.copyProperties(resp, bossResp);

		return returnInfo;
	}

	/**
	 * 查询配件信息
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queResInfo(ArrayList<ResInfo> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t.kind,t.subkind,t.name, a.attrvalue support ");
		sql.append("FROM res_class t,res_attribute a ");
		sql.append("WHERE t.subkind=a.subkind AND a.attrcode='RES_SUPPER' ");
		sql.append("AND t.subkind IN (  ");
		sql.append("SELECT e.subkind FROM RES_ATTRIBUTE e WHERE e.attrcode='RES_USEAREA' ");
		sql.append("AND (e.attrvalue =? OR e.attrvalue ='*')  ); ");

		Object[] params = { loginInfo.getAreaid() };
		List<ResInfo> list = getDAO().find(sql.toString(), ResInfo.class, params);
		if (list != null) {
			resp.addAll(list);
		}
		return returnInfo;
	}

	/**
	 * 回单
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizDealOrder(BizDealOrderReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getStepcode(), "处理环节不能为空");
		// 处理环节 4派单；5 回单；7 设备分配；
		if (!"4".equals(req.getStepcode()) && !"5".equals(req.getStepcode()) && !"7".equals(req.getStepcode())) {
			throw new BusinessException("处理环节有误");
		}
		CheckUtils.checkEmpty(req.getResultcode(), "处理结果不能为空");
		CheckUtils.checkEmpty(req.getCustorderid(), "客户订单号不能为空");
		CheckUtils.checkEmpty(req.getServorderid(), "服务订单号不能为空");
		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_DEAL_ORDER, req, loginInfo);

		String bizcode = getBizCodeByStepcode(req.getStepcode());
		interServeOrderService.saveCustOrder(req, loginInfo, bizcode);
		return returnInfo;
	}
	
	private String getBizCodeByStepcode(String stepcode) {
		if ("4".equals(stepcode)) {
			return BizOpcode.OSS_ORDERS;
		} else if ("5".equals(stepcode)) {
			return BizOpcode.OSS_RECEIPT;
		} else {
			return BizOpcode.OSS_DEV_ALLOCATE;
		}
	}

	/**
	 * 转单
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizChangeOrder(BizChangeOrderReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustorderid(), "客户订单号不能为空");
		CheckUtils.checkEmpty(req.getServorderid(), "服务订单号不能为空");
		if (StringUtils.isBlank(req.getNewdeptid()) && StringUtils.isBlank(req.getNgridcode())) {
			throw new BusinessException("新部门编号和新网格编号不能同时为空");
		}
		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_CHANGE_ORDER, req, loginInfo);
		interServeOrderService.saveCustOrder(req, loginInfo, BizOpcode.OSS_RECEIPT_TURN);
		return returnInfo;
	}

	/**
	 * 延单
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizDelayOrder(BizDelayOrderReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustorderid(), "客户订单号不能为空");
		CheckUtils.checkEmpty(req.getServorderid(), "服务订单号不能为空");
		CheckUtils.checkEmpty(req.getAppointmenttime(), "新预约时间不能为空");
		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_DELAY_ORDER, req, loginInfo);
		interServeOrderService.saveCustOrder(req, loginInfo, BizOpcode.OSS_DELAY);
		return returnInfo;
	}

	/**
	 * 查询转单部门
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queChangeDept(ArrayList<DepartmentInterInfo> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		List<DepartmentInterInfo> depts = interServeOrderService.getDeptsForChangeDept(loginInfo);
		if (depts != null) {
			resp.addAll(depts);
		}
		return returnInfo;
	}

	/**
	 * 查询工单委托信息
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGridApplyInfo(QueGridApplyInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		resp.setGridName(findMyGridName(loginInfo));
		resp.setDepts(interServeOrderService.getDeptsForGridApply(loginInfo));
		return returnInfo;
	}

	private String findMyGridName(LoginInfo loginInfo) throws Exception {
		String sql = "SELECT gi.gridname FROM biz_grid_manager gm JOIN biz_grid_info gi "
				+ "ON gi.gridid = gm.gridid WHERE gi.city = ? AND operid = ? LIMIT 1 ;";
		String gridName = (String) getDAO().findUniqueObject(sql, loginInfo.getCity(), loginInfo.getOperid());
		return gridName;
	}

	/**
	 * 查询工单委托记录
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGridApply(QueGridApplyReq req, ArrayList<GridApplyRecord> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");

		req.setOperid(String.valueOf(loginInfo.getOperid()));
		req.setStatus("Y");
		String result = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_GRID_APPLY, req, loginInfo);
		List<GridApplyRecord> datas = new Gson().fromJson(result, new TypeToken<List<GridApplyRecord>>() {
		}.getType());
		if (req.getSize() > 0 && datas != null) {
			if (datas.size() > req.getSize()) {
				datas = datas.subList(0, req.getSize());
			}
		}
		if (datas != null && datas.size() > 0) {
			resp.addAll(datas);
			findGridApplyDeptName(resp);
		}
		return returnInfo;
	}

	private void findGridApplyDeptName(ArrayList<GridApplyRecord> resp) throws Exception {
		Set<String> idSet = new HashSet<String>();
		for (GridApplyRecord record : resp) {
			if (record.getNgridcode() != null) {
				idSet.add(record.getNgridcode());
			}
			if (record.getOgridcode() != null) {
				idSet.add(record.getOgridcode());
			}
		}
		String ids = StringUtils.join(idSet, ",");
		String sql = String.format("SELECT deptid AS id, name FROM prv_department WHERE deptid in (%s) ;", ids);

		List<DepartmentInterInfo> depts = getDAO().find(sql, DepartmentInterInfo.class);

		for (GridApplyRecord record : resp) {
			record.setNgridname(findDeptName(depts, record.getNgridcode()));
			record.setOgridname(findDeptName(depts, record.getOgridcode()));
		}
	}

	private String findDeptName(List<DepartmentInterInfo> depts, String deptid) {
		if (depts != null && depts.size() > 0) {
			for (DepartmentInterInfo info : depts) {
				if (info.getId().equals(deptid)) {
					return info.getName();
				}
			}
		}
		return null;
	}

	/**
	 * 工单委托申请
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizGridApply(BizGridApplyReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getStatus(), "状态不能为空");
		CheckUtils.checkEmpty(req.getOgridcode(), "原网格编号不能为空");
		CheckUtils.checkEmpty(req.getStime(), "开始时间不能为空");
		CheckUtils.checkEmpty(req.getEtime(), "结束时间不能为空");
		CheckUtils.checkEmpty(req.getNgridcode(), "新网格编号不能为空");
		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_GRID_APPLY, req, loginInfo);

		return returnInfo;
	}

	/**
	 * 工单统计查询
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queOrderTotal(QueOrderTotalReq req, QueOrderTotalResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");

		req.setDeptid(interServeOrderService.getDeptsStrForLevel(loginInfo));
		String result = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_ORDER_TOTAL, req, loginInfo);
		QueOrderTotalResp bossResp = (QueOrderTotalResp) BeanUtil.jsonToObject(result, QueOrderTotalResp.class);
		BeanUtils.copyProperties(resp, bossResp);
		return returnInfo;
	}

	/**
	 * 查询回单异常原因
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queDealOrderReason(QueDealOrderReasonReq req, ArrayList<DealOrderReason> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getText(), "搜索文本不能为空");

		String sql = "SELECT reasoncode `code`, reasonname `name` FROM WFL_REASON WHERE reasoncode != 300  and reasonname LIKE ?;";
		List<DealOrderReason> datas = getDAO().find(sql, DealOrderReason.class, "%" + req.getText() + "%");
		if (datas != null && datas.size() > 0) {
			resp.addAll(datas);
		}
		return returnInfo;
	}

	/**
	 * 装维员备注填写接口
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizOrderOperMemo(BizOrderOperMemoReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getServorderid(), "服务单号不能为空");
		CheckUtils.checkEmpty(req.getOpermemo(), "备注内容不能为空");
		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_ORDER_OPERMEMO, req, loginInfo);

		return returnInfo;
	}

}
