package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.BizBossorderImage;
import com.maywide.biz.inter.pojo.exempt.BizExemptApplyReq;
import com.maywide.biz.inter.pojo.exempt.BizExemptDealReq;
import com.maywide.biz.inter.pojo.exempt.ExemptOrderBean;
import com.maywide.biz.inter.pojo.exempt.QueExemptApplyReq;
import com.maywide.biz.inter.pojo.exempt.QueExemptApplyResp;
import com.maywide.biz.inter.pojo.quebossorderimg.QueBossOrderImgReq;
import com.maywide.biz.inter.pojo.quebossorderimg.QueBossOrderImgResp;
import com.maywide.biz.inter.pojo.querydepartment.DepartmentInterInfo;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class ExemptService extends CommonService {

	@Autowired
	InterServeOrderService interServeOrderService;

	/**
	 * 豁免申请
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizExemptApply(BizExemptApplyReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustorderid(), "客户订单号不能为空");
		CheckUtils.checkEmpty(req.getServorderid(), "服务订单号不能为空");
		CheckUtils.checkEmpty(req.getType(), "豁免类别不能为空");
		CheckUtils.checkEmpty(req.getAction(), "豁免动作不能为空");

		BizBossorderImage order = (BizBossorderImage) getDAO().find(BizBossorderImage.class, Long.parseLong(req.getServorderid()));
		if (order != null) {
			throw new BusinessException("服务单号" + req.getServorderid() + "已申请豁免");
		}
		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_EXEMPT_APPLY, req, loginInfo);
		saveBizBossorderImage(req, loginInfo);
		interServeOrderService.saveCustOrder(req, loginInfo, BizConstant.BizOpcode.OSS_EXEMPTION);
		return returnInfo;
	}

	private void saveBizBossorderImage(BizExemptApplyReq req, LoginInfo loginInfo) throws Exception {
		BizBossorderImage orderImg = new BizBossorderImage();
		orderImg.setAreaid(loginInfo.getAreaid());
		orderImg.setCity(loginInfo.getCity());
		orderImg.setDeptid(loginInfo.getDeptid());
		orderImg.setOperid(loginInfo.getOperid());
		orderImg.setOptime(new Date());
		orderImg.setId(Long.parseLong(req.getServorderid()));
		orderImg.setBossorderid(Long.parseLong(req.getCustorderid()));
		List<String> paths = req.getImgpaths();
		if (paths != null) {
			if (paths.size() > 0) {
				orderImg.setImagecatalog1(paths.get(0));
			}
			if (paths.size() > 1) {
				orderImg.setImagecatalog2(paths.get(1));
			}
			if (paths.size() > 2) {
				orderImg.setImagecatalog3(paths.get(2));
			}
		}
		getDAO().save(orderImg);
	}

	/**
	 * 豁免工单查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queExemptApply(QueExemptApplyReq req, QueExemptApplyResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");

		if (StringUtils.isBlank(req.getDeptid())) {
			req.setDeptid(interServeOrderService.getDeptsStrForLevel(loginInfo));
		}

		String result = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_EXEMPT_APPLY, req, loginInfo);
		QueExemptApplyResp bossResp = (QueExemptApplyResp) BeanUtil.jsonToObject(result, QueExemptApplyResp.class);
		BeanUtils.copyProperties(resp, bossResp);
		queExemptImg(resp.getOrders());
		return returnInfo;
	}

	private void queExemptImg(List<ExemptOrderBean> orders) throws Exception {
		if (orders == null || orders.isEmpty()) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (ExemptOrderBean order : orders) {
			sb.append(order.getServorderid()).append(",");
		}
		sb.setLength(sb.length() - 1);
		List<BizBossorderImage> imgs = interServeOrderService.queExemptImgs(sb.toString());
		if (imgs != null) {
			for (ExemptOrderBean order : orders) {
				for (BizBossorderImage img : imgs) {
					if (order.getServorderid().equals(String.valueOf(img.getServorderid()))) {
						List<String> paths = new ArrayList<String>();
						if (StringUtils.isNotBlank(img.getImagecatalog1())) {
							paths.add(img.getImagecatalog1());
						}
						if (StringUtils.isNotBlank(img.getImagecatalog2())) {
							paths.add(img.getImagecatalog2());
						}
						if (StringUtils.isNotBlank(img.getImagecatalog3())) {
							paths.add(img.getImagecatalog3());
						}
						order.setImgPaths(paths);
						break;
					}
				}
			}

		}
	}

	/**
	 * 豁免审批
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizExemptDeal(BizExemptDealReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustorderid(), "客户订单号不能为空");
		CheckUtils.checkEmpty(req.getServorderid(), "服务订单号不能为空");
		CheckUtils.checkEmpty(req.getType(), "豁免类别不能为空");
		CheckUtils.checkEmpty(req.getAction(), "豁免动作不能为空");
		CheckUtils.checkEmpty(req.getDealresult(), "审批结果不能为空");

		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_EXEMPT_DEAL, req, loginInfo);
		return returnInfo;
	}

	/**
	 * 查询当前及下级部门
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queLevelDept(ArrayList<DepartmentInterInfo> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		List<DepartmentInterInfo> depts = interServeOrderService.getDeptsForLevel(loginInfo);
		if (depts != null) {
			resp.addAll(depts);
		}
		return returnInfo;
	}

	/**
	 * 查询豁免图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBossOrderImg(QueBossOrderImgReq req, QueBossOrderImgResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getServorderid(), "客户订单号不能为空");

		BizBossorderImage order = (BizBossorderImage) getDAO().find(BizBossorderImage.class, Long.parseLong(req.getServorderid()));
		if (order != null) {
			BeanUtils.copyProperties(resp, order);
			resp.setServorderid(order.getId());
		}
		return returnInfo;
	}

}
