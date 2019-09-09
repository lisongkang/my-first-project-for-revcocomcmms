package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.problem.ProblemBean;
import com.maywide.biz.inter.pojo.problem.QueProblemListReq;
import com.maywide.biz.inter.pojo.problem.QueProblemListResp;
import com.maywide.biz.inter.pojo.problem.SaveProblemReq;
import com.maywide.biz.manage.problem.entity.SysProblem;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProblemService extends CommonService {

	/**
	 * 查询问题申诉信息
	 */
	public ReturnInfo queProblemList(QueProblemListReq req, QueProblemListResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append(" select   plid id,  pltype,  plname,  pldesc, subtime,   ");
		sql.append(" dealtime,  dealdesc,  plstate, memo from sys_problem  ");
		sql.append(" where 1=1 ");

		sql.append(" and suboperid = ? ");
		paramList.add(loginInfo.getOperid());
		sql.append(" and suboperdept = ? ");
		paramList.add(loginInfo.getDeptid());
		
		if (req.getPlstate() != null) {
			sql.append(" and plstate = ? ");
			paramList.add(req.getPlstate());
		}
		sql.append(" order by subtime desc ");
		List<ProblemBean> datas = getDAO().find(sql.toString(), ProblemBean.class, paramList.toArray());
		resp.setDatas(datas);
		return returnInfo;
	}

	/**
	 * 保存问题申诉
	 */
	public ReturnInfo saveProblem(SaveProblemReq req) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求参数不能为空");
		CheckUtils.checkNull(req.getPlstate(), "问题状态不能为空");
		CheckUtils.checkEmpty(req.getPlname(), "问题标题不能为空");
		CheckUtils.checkNull(req.getPltype(), "问题类型不能为空");
		CheckUtils.checkNull(req.getPldesc(), "问题描述不能为空");

		saveSysProblem(req, loginInfo);

		return returnInfo;
	}

	private void saveSysProblem(SaveProblemReq req, LoginInfo loginInfo) throws Exception {
		SysProblem problem;
		if (req.getId() == null) {
			problem = new SysProblem();
		} else {
			problem = (SysProblem) getDAO().find(SysProblem.class, req.getId());
		}

		problem.setPlname(req.getPlname());
		problem.setPltype(req.getPltype());
		problem.setPlstate(req.getPlstate());
		problem.setPldesc(req.getPldesc());

		problem.setSuboperid(loginInfo.getOperid());
		problem.setSuboperdept(loginInfo.getDeptid());
		if (problem.getSubtime() == null 
				||BizConstant.SYS_PLSTATE.SYS_PLSTATE_SUBMIT.equals(problem.getPlstate().toString())
				||BizConstant.SYS_PLSTATE.SYS_PLSTATE_DEAL.equals(problem.getPlstate().toString())) {
			problem.setSubtime(new Date());
		}

		if (StringUtils.isBlank(problem.getCity())) {
			problem.setCity(loginInfo.getCity());
		}
		if (problem.getAreaid() == null) {
			problem.setAreaid(loginInfo.getAreaid());
		}

		getDAO().save(problem);
	}

	
}
