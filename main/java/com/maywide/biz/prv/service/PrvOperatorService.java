package com.maywide.biz.prv.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.RIGHTS;
import com.maywide.biz.core.entity.AccessLog;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.pojo.portal.PortalUser;
import com.maywide.biz.inter.service.PortalService;
import com.maywide.biz.prv.bo.PrvOperatorBO;
import com.maywide.biz.prv.dao.PrvOperatorDao;
import com.maywide.biz.prv.entity.AppUser;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.MD5;
import com.maywide.core.util.SimpleSqlCreator;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrvOperatorService extends BaseService<PrvOperator, Long> {
	@Autowired
	private PrvOperatorDao prvOperatorDao;
	@Autowired
	private PersistentService persistentService;

	@Autowired
	private PortalService portalService;

	@Autowired
	private RuleService ruleService;

	@Override
	protected BaseDao<PrvOperator, Long> getEntityDao() {
		return prvOperatorDao;
	}

	public void registerOper(PrvOperator oper) throws Exception {
		checkData(oper);
		oper.setPasswd(MD5.md5(oper.getPasswd()));
		if (oper.getId() == null) {
			String loginName = oper.getLoginname();

			if (findByProperty("loginname", loginName) != null) {
				throw new Exception ("注册账号:" + loginName + " 已被注册使用，请修改使用其他账号");
			}

			Long pk = persistentService.getNextId("PRV_OPERATOR", "operid");

			oper.setId(pk);
			oper.getOperinfo().setId(pk);

			persistentService.save(oper.getOperinfo());
			persistentService.save(oper);
		} else {
			if (StringUtils.isBlank(oper.getPasswd())) {
				oper.setPasswd(null);
			}
			persistentService.update(oper.getOperinfo());
			persistentService.update(oper);
		}
	}

	public void modifyPasswd(Long operid, String passwd, String oldpasswd) {
		PrvOperator oper = findOne(operid);
		if (passwd.equals(oldpasswd)) {
			throw new ServiceException ("设置密码不能和原密码一样！");
		}
		if (!oper.getPasswd().equals(MD5.md5(oldpasswd))) {
			throw new ServiceException ("原密码不正确！");
		}
		oper.setPasswd(MD5.md5(passwd));
		save(oper);
	}

	public boolean validatePassword(Long operid, String passwd) {
		PrvOperator oper = findOne(operid);

		boolean result = MD5.md5(passwd).equals(oper.getPasswd());

		return result;
	}

	public List<PrvDepartment> queryDepartments(String loginName) {
		List<PrvDepartment> depts = Lists.newArrayList();
		try {
			// String hql =
			// "FROM PrvDepartment WHERE id in (SELECT a.deptid FROM PrvOperrole a, PrvOperator b WHERE a.operid = b.id AND b.loginname = ?)";
			// depts = (List<PrvDepartment>) persistentService.find(hql,
			// loginName);

			// 先取操作员
			PrvOperator prvOperatorVO = new PrvOperator();
			prvOperatorVO.setLoginname(loginName);
			List<PrvOperator> list = persistentService.find(prvOperatorVO);
			if (list == null || list.size() < 1){
				return depts;
				//throw new BusinessException("用户名不存在");
			}

			PrvOperator oper = (PrvOperator) list.get(0);

			StringBuffer sql = new StringBuffer();
			List paramList = new ArrayList();

			sql.append(" SELECT * from ( ");
			sql.append(" SELECT d.deptid id, d.* ");
			sql.append("   FROM prv_department d ");
			sql.append("  WHERE d.deptid IN ");
			sql.append("        (SELECT o.deptid ");
			sql.append("           FROM prv_operrole o ");
			sql.append("          WHERE o.operid = ? ");
			paramList.add(oper.getId());
			sql.append("            and exists (select 1 ");
			sql.append("                   from prv_roleprivs r, prv_menudef m ");
			sql.append("                  where r.menuid = m.menuid ");
			sql.append("                    and r.roleid = o.roleid)) ");
			sql.append("  ORDER BY deptid ");
			sql.append(" ) v ");

			depts = persistentService.find(
					sql.toString(), PrvDepartment.class, paramList.toArray());

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return depts;
	}

	public List<PrvOperator> findAllOperators() {
		return Lists.newArrayList(prvOperatorDao.findAllCached());
	}

	private void checkData(PrvOperator oper) {
		if (StringUtils.isNotBlank(oper.getOperinfo().getPhone())) {
			String pattern = "^1\\d{10}$";
			if (!isValidType(pattern, oper.getOperinfo().getPhone())) {
				throw new ServiceException ("请输入正确的手机号");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public PageImpl<PrvOperatorBO> findPageOpersByCity(String queryParam, Pageable pageable, String orderField,
			String sortType) throws Exception {
		PageImpl<PrvOperatorBO> pageResult = null;
		Page<PrvOperatorBO> page = new Page<PrvOperatorBO>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());

		StringBuffer sql = getCommonQueOperSql();

		List<Object> paramList = new ArrayList<Object>();

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();


		String city = loginInfo.getCity();
		if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
			// 超级管理员查询全部地市的部门
			//ywp 增加角色级别对工号的控制,支公司：业务区
			String roleLevel = loginInfo.getRolelevel();
			//分公司：地市
			if(RIGHTS.NORMAL.equals(roleLevel)){
				addByCityFilterSql(sql);
				paramList.add(city);
			}
			//支公司：业务区
			if(RIGHTS.LOW.equals(roleLevel)){
				addByAreaFilterSql(sql);
				paramList.add(loginInfo.getAreaid());
			}
		}

		if (StringUtils.isNotBlank(queryParam)) {
			sql.append(" AND (o.NAME LIKE ? OR o.LOGINNAME LIKE ?)");
			paramList.add("%" + queryParam + "%");
			paramList.add("%" + queryParam + "%");
		}
		if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
			sql.append(" ORDER BY o.").append(orderField);
			sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
		} else {
			sql.append(" ORDER BY o.STATUS) n");
		}

		persistentService.clear();
		page = persistentService.find(page, sql.toString(), PrvOperatorBO.class, paramList.toArray());

		List<PrvOperatorBO> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<PrvOperatorBO>(resultList, pageable, total);
		} else {
			pageResult = new PageImpl<PrvOperatorBO>(new ArrayList<PrvOperatorBO>(), pageable, 0);
		}
		return pageResult;
	}

	@SuppressWarnings("unchecked")
	public List<PrvOperatorBO> findOpersByCity() throws Exception {
		StringBuffer sql = getCommonQueOperSql();
		List<Object> paramList = new ArrayList<Object>();

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String city = loginInfo.getCity();

		if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
			// 超级管理员查询全部地市的部门
			addByCityFilterSql(sql);
			paramList.add(city);
		}
		sql.append(" ORDER BY o.STATUS) n");

		persistentService.clear();
		List<PrvOperatorBO> resultList = persistentService.find(sql.toString(), PrvOperatorBO.class,
				paramList.toArray());

		if (null == resultList) {
			resultList = new ArrayList<PrvOperatorBO>();
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<PrvOperatorBO> findUnbindManagerByGridId(String gridId, String queryParam, Pageable pageable,
			String orderField, String sortType) throws Exception {
		PageImpl<PrvOperatorBO> pageResult = null;
		Page<PrvOperatorBO> page = new Page<PrvOperatorBO>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());

		StringBuffer sql = getCommonQueOperSql();
		List<Object> paramList = new ArrayList<Object>();

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String city = loginInfo.getCity();

		if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
			// 超级管理员查询全部地市的部门
			addByCityFilterSql(sql);
			paramList.add(city);
		}

		// 过滤已关联网格的操作员
		if (StringUtils.isNotBlank(gridId)) {
			sql.append(" AND o.operid NOT IN(");
			sql.append("SELECT m.operid FROM ").append(SimpleSqlCreator.getEntityTableName(BizGridManager.class))
			.append(" m");
			sql.append(" WHERE m.gridid=?");
			sql.append(")");
			paramList.add(Long.parseLong(gridId));
		}

		if (StringUtils.isNotBlank(queryParam)) {
			sql.append(" AND (o.NAME LIKE ? OR o.LOGINNAME LIKE ?)");
			paramList.add("%" + queryParam + "%");
			paramList.add("%" + queryParam + "%");
		}
		if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
			sql.append(" ORDER BY o.").append(orderField);
			sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
		} else {
			sql.append(" ORDER BY o.STATUS) n");
		}

		persistentService.clear();
		page = persistentService.find(page, sql.toString(), PrvOperatorBO.class, paramList.toArray());

		List<PrvOperatorBO> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<PrvOperatorBO>(resultList, pageable, total);
		} else {
			pageResult = new PageImpl<PrvOperatorBO>(new ArrayList<PrvOperatorBO>(), pageable, 0);
		}
		return pageResult;
	}

	private StringBuffer getCommonQueOperSql() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM(");
		sql.append("SELECT o.OPERID AS id,");
		sql.append("o.LOGINNAME AS loginname,");
		sql.append("o.NAME AS name,");
		sql.append("o.STATUS AS status,");
		sql.append("o.STIME AS stime,");
		sql.append("o.ETIME AS etime,");
		sql.append("o.LASTTIME AS lasttime ");
		sql.append(" FROM ").append(SimpleSqlCreator.getEntityTableName(entityClass)).append(" o");
		sql.append(" WHERE 1=1");
		return sql;
	}

	private void addByCityFilterSql(StringBuffer sql) throws Exception {
		sql.append(" AND o.operid IN(");
		sql.append("SELECT t.operid FROM prv_operdept t,");
		sql.append(SimpleSqlCreator.getEntityTableName(PrvDepartment.class)).append(" d");
		sql.append(" WHERE t.depid=d.deptid AND d.city=?");
		sql.append(")");
	}

	private void addByAreaFilterSql(StringBuffer sql) throws Exception {
		sql.append(" AND o.operid IN(");
		sql.append("SELECT t.operid FROM prv_operdept t,");
		sql.append(SimpleSqlCreator.getEntityTableName(PrvDepartment.class)).append(" d");
		sql.append(" WHERE t.depid=d.deptid AND d.areaid=?");
		sql.append(")");
	}

	public void transEntity(PrvOperator entity) throws Exception {
		//ywp 是否启用直接查询portal数据库
		Rule rule = ruleService.getRule("OPER_PORTALDATA");
		boolean flag = false;
		if(null != rule){
			if("Y".equals(rule.getPerMission())){
				flag = true;
			}
		}

		if(flag){
			PersistentService portalPersistentService = SpringBeanUtil.getPortalPersistentService();
			AppUser queParam = new AppUser();
			queParam.setUserid(entity.getLoginname());
			queParam.setClient_id("4");
			List<AppUser> users = portalPersistentService.find(queParam);
			if(users != null &&users.size()==1){
				AppUser user = users.get(0);
				if(user!=null&&user.getOperid()!=null&&!user.getOperid().equals("")){
					entity.addExtraAttribute("portalnum", user.getOperid());
				}
			}
		}
		else{
			PortalUser  user = portalService.queryPortalUserByloginname(entity.getLoginname());
			if(user!=null&&user.getOperid()!=null&&!user.getOperid().equals("")){
				entity.addExtraAttribute("portalnum", user.getOperid());
			}
		}
	}

	public void doSavePortalInfo(PrvOperator entity) throws Exception {
		//ywp 是否启用直接修改portal数据库
		Rule rule = ruleService.getRule("OPER_PORTALDATA");
		boolean flag = false;
		if(null != rule){
			if("Y".equals(rule.getPerMission())){
				flag = true;
			}
		}
		if(flag){
			PersistentService portalPersistentService = SpringBeanUtil.getPortalPersistentService();
			AppUser queParam = new AppUser();
			queParam.setUserid(entity.getLoginname());
			queParam.setClient_id("4");
			List<AppUser> users = portalPersistentService.find(queParam);
			String portalnum = entity.getExtraAttributesValue("portalnum");
			if(users != null &&users.size()==1){
				AppUser appUser = users.get(0);
				//把修改的信息记入到日志表中
				StringBuffer accLogReq = new StringBuffer();
				accLogReq.append("portal原来数据库中prv_app_user:userid="+appUser.getUserid()+";原号="+appUser.getOperid()+";新号="+portalnum+";");
				accLogReq.append("update prv_operator c set c.loginname = "+portalnum+" where c.loginname="+appUser.getOperid()+";");
				accLogReq.append("update prv_app_user c set c.operid = "+portalnum+" where c.operid="+appUser.getOperid());
				AccessLog accessLog = new AccessLog();
				accessLog.setCallTime(new Date());
				accessLog.setRequest(accLogReq.toString());
				accessLog.setResponse(portalnum);
				accessLog.setReturnMsg(appUser.getOperid());
				persistentService.save(accessLog);	
				//修改prv_operator表
				List paramList = new ArrayList();
				StringBuffer sql = new StringBuffer();
				sql.append(" update prv_operator c set c.loginname = ? where c.loginname=?");
				paramList.add(portalnum);
				paramList.add(appUser.getOperid());
				portalPersistentService.executeSql(sql.toString(), paramList.toArray());
				sql.setLength(0);
				paramList.clear();
				//修改prv_app_user表
				sql.append("update prv_app_user c set c.operid = ? where c.operid=?");
				paramList.add(portalnum);
				paramList.add(appUser.getOperid());
				portalPersistentService.executeSql(sql.toString(), paramList.toArray());

			}else{
				throw new BusinessException("根据账号查找portal:prv_app_user表失败");
			}
		}else{
			PortalUser  user = portalService.queryPortalUserByloginname(entity.getLoginname());
			//新增,修改 portal数据
			if(entity.getExtraAttributesValue("portalnum").equals(user.getOperid())){
				return ;
			}
			user.setClient_id("4");
			user.setOperid(entity.getExtraAttributesValue("portalnum"));
			user.setUserid(entity.getLoginname());
			user.setStatus("1");
			portalService.requestPost(BizConstant.PortalInterfaceService.SAVEPORTALUSER, user);
		}
	}

	public  org.springframework.data.domain.Page<Map> findOneCityByPage(Pageable pageable,String name) throws Exception {
		StringBuilder sql = new StringBuilder();
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		sql.append("SELECT  DISTINCT ople.operid,ople.name,ople.loginname  FROM prv_department depart ,(SELECT oper.operid ,oper.name ,role.`deptid`,oper.loginname FROM prv_operator oper ,prv_operrole role WHERE role.`operid` = oper.`operid` ) ople  WHERE depart.`deptid`=ople.deptid AND city='"+loginInfo.getCity()+"' "); 
		if(!"".equals(name)&&null!=name){
			sql.append("AND ople.name LIKE '%"+name+"%'");
		}
		return (org.springframework.data.domain.Page<Map>) this.findByPageNativeSQL(pageable, sql.toString());
	}

}
