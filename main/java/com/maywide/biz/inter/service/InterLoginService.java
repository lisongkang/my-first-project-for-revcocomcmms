package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.filter.SecurityFilter;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.GlobalCacheMgr;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.manager.LoginLogginTask;
import com.maywide.biz.inter.pojo.checkPwd.CheckPwdReq;
import com.maywide.biz.inter.pojo.login.LoginInterReq;
import com.maywide.biz.inter.pojo.login.LoginInterResp;
import com.maywide.biz.inter.pojo.login.LoginOperIdInterReq;
import com.maywide.biz.inter.pojo.queryVersion.QueryVersionReq;
import com.maywide.biz.inter.pojo.queryVersion.QueryVersionResp;
import com.maywide.biz.inter.pojo.querydepartment.DepartmentInterInfo;
import com.maywide.biz.inter.pojo.querydepartment.QueryDepartmentInterReq;
import com.maywide.biz.inter.pojo.queryopermenu.OperMenuInterInfo;
import com.maywide.biz.inter.pojo.queryopermenu.QueryOperMenuInterReq;
import com.maywide.biz.portal.pojo.Am5Result;
import com.maywide.biz.portal.service.Am5Service;
import com.maywide.biz.prv.bo.LoginParam;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.entity.PrvMenudef;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.prv.entity.PrvRoleinfo;
import com.maywide.biz.prv.service.LoginService;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.MD5;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterLoginService extends CommonService {

	@Autowired
	private LoginService loginService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private Am5Service am5Service;

	@Transactional(readOnly = true)
	public ReturnInfo queryDepartment(QueryDepartmentInterReq req,
			ArrayList<DepartmentInterInfo> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		// 先取操作员
		PrvOperator oper = null;
		if (StringUtils.isNotBlank(req.getLoginname())) {
			if (isPhoneNumberLogin(req.getLoginname())) {
				try {
					String loginname = getLoginNameWithPhoneNumber(req.getLoginname());
					if (StringUtils.isNotBlank(loginname)) {
						req.setLoginname(loginname);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PrvOperator prvOperatorVO = new PrvOperator();
			prvOperatorVO.setLoginname(req.getLoginname());
			getDAO().clear();
			List<PrvOperator> list = getDAO().find(prvOperatorVO);
			if (list == null || list.size() < 1)
				throw new BusinessException("用户名不存在");
			
			oper = (PrvOperator) list.get(0);
		}

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();
		sql.append(" SELECT * from ( ");
		sql.append(" SELECT d.deptid AS id, d.name, d.kind, d.areaid, d.preid ");
		sql.append("   FROM prv_department d ");
		sql.append("  WHERE d.deptid IN ");
		sql.append("        (SELECT o.deptid ");
		sql.append("           FROM prv_operrole o ");
		sql.append("          WHERE 1=1 ");
		if (oper != null) {
			sql.append("          and o.operid = ? ");
			paramList.add(oper.getId());
		}
		
		sql.append("            and exists (select 1 ");
		sql.append("                   from prv_roleprivs r, prv_menudef m ");
		sql.append("                  where r.menuid = m.menuid ");
		sql.append("                    and r.roleid = o.roleid)) ");
		
		if (req.getAreaid() != null && req.getAreaid().longValue() > 0) {
			sql.append("          and d.areaid = ? ");
			paramList.add(req.getAreaid());
		}
		sql.append("  ORDER BY deptid ");
		sql.append(" ) v ");
		
		getDAO().clear();
		List<DepartmentInterInfo> departmentList = getDAO().find(
				sql.toString(), DepartmentInterInfo.class, paramList.toArray());

		resp.addAll(departmentList);

		return returnInfo;
	}
	
	/**
	 * 此接口用来AppPortal登录使用
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo loginByOperId(LoginOperIdInterReq req,LoginInterResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		CheckUtils.checkNull(req, "请求对象不能为空");
		
		return returnInfo;
	}
	
	//@Transactional(readOnly = true)
	public ReturnInfo login(LoginInterReq req, LoginInterResp resp)
			throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getLoginname(), "匹配条件[loginname]不能为空");

		checkPhoneNumberLogin(req,resp);
		LoginParam loginParam = new LoginParam();
		loginParam.setLoginname(req.getLoginname());
		loginParam.setPassword(req.getPassword());
		loginParam.setDeptid(Long.valueOf(req.getDeptid()));
		if(StringUtils.isNotBlank(req.getCryptPwd())){
			loginParam.setPassword(req.getCryptPwd());
			loginParam.setIsPwdCrypted(true);
		}

		LoginInfo loginInfo = loginService.login(loginParam,req.getAtype());
		
		
//		System.out.println(new Gson().toJson(loginInfo));

		copyLoginInfo2LoginInterResp(resp, loginInfo);

		AuthContextHolder.setLoginInfo(loginInfo);

		GlobalCacheMgr.getSession().setAttribute(SecurityFilter.LOGIN_INFO,
				loginInfo);
		String ipAddr = AuthContextHolder.getUserIpAddr();
		
		
		SpringBeanUtil.getThreadExcutor().execute(new LoginLogginTask(
				loginInfo.getCity(),req.getDevStr(),
				ipAddr,req.getPkgName(),req.getVerApp(),
				loginInfo.getOperid(),loginInfo.getDeptid()));
		
		/*
		System.out.print("this ipAddr  is ==>" + ipAddr);
		HttpSession s = GlobalCacheMgr.getSession();
		System.out.print("this session id is ==>" + s.getId());*/

		return returnInfo;
	}

	private void checkPhoneNumberLogin(LoginInterReq req, LoginInterResp resp) throws Exception {
		if (!isPhoneNumberLogin(req.getLoginname()) || req.isImplicit()) return;

		final String portalUserId = req.getLoginname();
		String loginname = null;
		try {
			loginname = getLoginNameWithPhoneNumber(portalUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(loginname)) {
			Am5Result result = am5Service.verifyPassword(portalUserId, req.getPassword());
			if (result.code != 0) {
				throw new BusinessException(result.getMessage("密码不正确!"));
			}
			PrvOperator prvOperator = new PrvOperator();
			prvOperator.setLoginname(loginname);
			List<PrvOperator> datas = getDAO().find(prvOperator);
			if (datas != null && !datas.isEmpty()) {
				resp.setPortalUserId(portalUserId);
				prvOperator = datas.get(0);
				req.setLoginname(loginname);
				req.setCryptPwd(prvOperator.getPasswd());
			}
		}
	}
	
	public String getLoginNameWithPhoneNumber(String phone) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("operid", phone);
		params.put("clientid", 4);
		String outPut = getOAuthHttpOutPut(BizConstant.OAuthApi.QUERY_APP_USER, params);
		JSONObject jsonObject = new JSONObject(outPut);
		return jsonObject.getString("userid");
	}

	private boolean isPhoneNumberLogin(String str) {
		return isPhoneNumber(str) && StringUtils.isBlank(SysConfig.getServiceCity());
	}
	
	private boolean isPhoneNumber(String str) {
		Pattern compile = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
		return compile.matcher(str).matches();
	}

	private void copyLoginInfo2LoginInterResp(LoginInterResp loginInterResp,
			LoginInfo loginInfo) throws Exception {
		if (null == loginInterResp || null == loginInfo) {
			return;
		}

		loginInterResp.setAreaid(loginInfo.getAreaid().toString());
		loginInterResp.setAreaname(loginInfo.getAreaname());
		loginInterResp.setCity(loginInfo.getCity());
		loginInterResp.setCityname(loginInfo.getCityname());
		loginInterResp.setDepartCount(loginInfo.getDepartCount().toString());
		loginInterResp.setDepartSet(loginInfo.getDepartSet());
		loginInterResp.setDeptid(loginInfo.getDeptid().toString());
		loginInterResp.setDeptname(loginInfo.getDeptname());
		loginInterResp.setDeplevel(loginInfo.getDeplevel());
		loginInterResp.setLoginname(loginInfo.getLoginname());
		loginInterResp.setName(loginInfo.getName());
		loginInterResp.setOperid(loginInfo.getOperid().toString());
		loginInterResp.setRoleid(loginInfo.getRoleid().toString());
		loginInterResp.setRolename(loginInfo.getRolename());
		loginInterResp.setRootmenuid(loginInfo.getRootmenuid().toString());
		loginInterResp.setHeadPic(loginInfo.getHeadImg());
		
		if (null != loginInfo.getDeptid()) {
			PrvDepartment department = (PrvDepartment) getDAO().find(
					PrvDepartment.class, loginInfo.getDeptid());
			if (null != department) {
				loginInterResp.setDeptname(department.getName());
				loginInterResp.setDeplevel(department.getDeplevel());
			}
		}

		if (null != loginInfo.getAreaid()
		/* && StringUtils.isBlank(loginInfo.getAreaname()) */) {
			PrvArea area = (PrvArea) getDAO().find(PrvArea.class,
					loginInfo.getAreaid());
			if (null != area) {
				loginInterResp.setAreaname(area.getName());
			}
		}

		if (StringUtils.isNotBlank(loginInfo.getCity())) {
			String cityname = paramService.getMname("PRV_CITY",
					loginInfo.getCity());
			loginInterResp.setCityname(cityname);
		}

		if (null != loginInfo.getRoleid()) {
			PrvRoleinfo roleinfo = (PrvRoleinfo) getDAO().find(
					PrvRoleinfo.class, loginInfo.getRoleid());
			if (null != roleinfo) {
				loginInterResp.setRolename(roleinfo.getName());
			}
		}
		Rule citys = ruleService.getRule("*", "UPDATE_CITY");
		if(citys != null){
			String values = citys.getValue();
			String[] datas = values.split(",");
			List<String> updateCitys =  new ArrayList<String>();
			for(int i = 0;i < datas.length; i++){
				updateCitys.add(datas[i]);
			}
			loginInterResp.setCitys(updateCitys);
		}
		
	}

	@Transactional(readOnly = true)
	public ReturnInfo queryOperMenu(QueryOperMenuInterReq req,
			ArrayList<OperMenuInterInfo> resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getOperid(), "匹配条件[operid]不能为空");

		Long roleid = -1L;
		if (StringUtils.isNotBlank(req.getRoleid())) {
			roleid = Long.valueOf(req.getRoleid());
		}

		List<PrvMenudef> menuList = loginService.getOperMenu(
				Long.valueOf(req.getOperid()), roleid, req.getSysid(),loginInfo.getCity());
		
		for (PrvMenudef menu : menuList) {
			if (null != menu) {
				OperMenuInterInfo operMenu = new OperMenuInterInfo();
				copyPrvMenudef2OperMenuInterInfo(operMenu, menu);
				resp.add(operMenu);
			}
			
		}
		return returnInfo;
	}
	

	private void copyPrvMenudef2OperMenuInterInfo(OperMenuInterInfo operMenu,
			PrvMenudef menu) {
		if (null == operMenu || null == menu) {
			return;
		}
		operMenu.setAttr(menu.getAttr());
		operMenu.setBizcode(menu.getBizcode());
		operMenu.setFlag(menu.getFlag());
		operMenu.setIconid(menu.getIconid());
		operMenu.setId(menu.getId().toString());
		operMenu.setLinkurl(menu.getLinkurl());
		operMenu.setName(menu.getName());
		operMenu.setPinyin(menu.getPinyin());
		operMenu.setPremenuid(menu.getPremenuid().toString());
		operMenu.setTarget(menu.getTarget());
		operMenu.setOflag1(menu.getOflag1());
		operMenu.setOflag2(menu.getOflag2());
		operMenu.setMemo(menu.getMemo());
		operMenu.setAtype(menu.getAtype());
	}
	
	public ReturnInfo checkVersion(QueryVersionReq req,QueryVersionResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		CheckUtils.checkEmpty(req.getPkgName(),"当前包信息不能为空");
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT T.ADVERTIMG advertImg,");
		sb.append("		T.FORCE versionForce,T.DOWNLOAD_URL downUrl,");
		sb.append("		T.DESCRIPT descript,T.VERSION lastestVersion");
		sb.append("		FROM PRV_APK_PACKAGE T ");
		sb.append("		WHERE  1 = 1 ");
		sb.append("	AND T.PACKAGES = ?");
		List params = new ArrayList();
		params.add(req.getPkgName());
		if(StringUtils.isNotBlank(req.getOperate())){
			params.add(req.getOperate());
			sb.append(" AND T.OPERATE = ?");
		}
		List<QueryVersionResp> datas = getDAO().find(sb.toString(), QueryVersionResp.class, params.toArray());
		if(datas != null && datas.size() > 0){
			QueryVersionResp data = datas.get(0);
			resp.setAdvertImg(data.getAdvertImg());
			resp.setDescript(data.getDescript());
			resp.setDownUrl(data.getDownUrl());
			resp.setLastestVersion(data.getLastestVersion());
			resp.setTitle(data.getTitle());
			resp.setVersionForce(data.getVersionForce());
			boolean hasUpdate = hasUpdate(req.getVersionName(), data.getLastestVersion());
			resp.setHasVersion(hasUpdate);
			
		}
		return returnInfo;
	}
	
	private boolean hasUpdate(String clientVersion, String serverVersion) {
		String[] clientVersions = clientVersion.split("\\.");
		String[] serverVersions = serverVersion.split("\\.");
		int length = Math.min(clientVersions.length, serverVersions.length);
		for (int i = 0; i < length; i++) {
			int client = Integer.parseInt(clientVersions[i]);
			int server = Integer.parseInt(serverVersions[i]);
			if(client > server){
				return false;
			}
			if(server > client){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * App Portal隐式登录使用接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo loginImplicit(LoginOperIdInterReq req,LoginInterResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getLoginName(), "匹配条件[loginname]不能为空");
		CheckUtils.checkEmpty(req.getDepartmentId(), "匹配条件[departmentId]不能为空");
		
		PrvOperator prvOperator = new PrvOperator();
		prvOperator.setLoginname(req.getLoginName());
		List<PrvOperator> datas = getDAO().find(prvOperator);
		if(datas == null || datas.isEmpty()){
			throw new BusinessException("查询不到该用户的信息");
		}
		prvOperator = datas.get(0);
		LoginInterReq loginReq = getLoginInterReq(prvOperator,req);
		loginReq.setImplicit(true);
		login(loginReq, resp);
		return returnInfo;
	}
	
	private LoginInterReq getLoginInterReq(PrvOperator prvOperator,LoginOperIdInterReq req){
		LoginInterReq loginInterReq = new LoginInterReq();
		loginInterReq.setApi(req.getApi());
		loginInterReq.setBizorderid(req.getBizorderid());
		loginInterReq.setDeptid(req.getDepartmentId());
		loginInterReq.setLoginname(req.getLoginName());
		loginInterReq.setCryptPwd(prvOperator.getPasswd());
		loginInterReq.setRequestBody(req.getRequestBody());
		return loginInterReq;
	}
	
	
	public ReturnInfo checkPwd(CheckPwdReq req) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		CheckUtils.checkEmpty(req.getLoginName(), "帐号登录名不能为空");
		CheckUtils.checkEmpty(req.getPwd(), "帐号登录密码不能为空");
		
		PrvOperator prvOperatorVO = new PrvOperator();
		prvOperatorVO.setLoginname(req.getLoginName());
		
		List<PrvOperator> list = getDAO().find(prvOperatorVO);
		if (list == null || list.isEmpty())
			CheckUtils.checkNull(null, "用户名不存在");
		prvOperatorVO  = list.get(0);
		if (!MD5.md5(req.getPwd()).equals(prvOperatorVO.getPasswd())) {
			CheckUtils.checkNull(null, "密码错误！");
		}
		
		return returnInfo;
	}
	
}
