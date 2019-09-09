package com.maywide.biz.prv.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.OperatorInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.pojo.tempsql.HeadImgBean;
import com.maywide.biz.prv.bo.LoginParam;
import com.maywide.biz.prv.entity.LogPrvLogin;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.entity.PrvMenudef;
import com.maywide.biz.prv.entity.PrvOperLogin;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.prv.entity.PrvOperrole;
import com.maywide.biz.prv.entity.PrvRoleinfo;
import com.maywide.biz.prv.entity.PrvShortcut;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.MD5;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private ParamService paramService;

	public void uptOperOnline(Long operid, Date lasttime, String memo)
			throws Exception {
		if (operid == null || lasttime == null)
			return;
		PrvOperLogin preOperLogin = new PrvOperLogin();

		preOperLogin.setId(operid);
		/* 如果还没进入子系统时候可能会被后台子程序删除,所以更新时也可能插入记录 */
		if (persistentService.count(preOperLogin) == 0) {
			preOperLogin.setRefreshtime(lasttime);
			preOperLogin.setMemo(memo);
			persistentService.save(preOperLogin);
		} else {
			preOperLogin.setRefreshtime(lasttime);
			preOperLogin.setMemo(memo);
			persistentService.save(preOperLogin);
		}
	}

	public OperatorInfo checkUser(String loginname, String password,Boolean isPwdCrypt)
			throws Exception {
		PrvOperator operator = new PrvOperator();
		operator.setLoginname(loginname);

		return checkUser(operator, password,isPwdCrypt);
	}

	public OperatorInfo checkUser(PrvOperator operator, String password,
			Boolean isPwdCrypt) throws Exception {

		List list = persistentService.find(operator);
		if (list == null || list.isEmpty())
			throw new BusinessException("用户名不存在！");

		PrvOperator oper = (PrvOperator) list.get(0);

		if (null!=isPwdCrypt && isPwdCrypt) {
			if (!password.equals(oper.getPasswd())) {
				throw new BusinessException("密码错误！");
			}
		} else {
			if (!MD5.md5(password).equals(oper.getPasswd())) {
				throw new BusinessException("密码错误！");
			}
		}

		Date nowtime = new Date();
		if (!SysConstant.Operator.PRV_USE_STATUS_ENABLED.equals(oper
				.getStatus())) {
			throw new BusinessException("该用户已停用");
		}

		// 更新最近登录时间 和 登陆次数
		Double logtimes;
		if (oper.getLogtimes() == null)
			logtimes = 0D;
		else
			logtimes = oper.getLogtimes();

		oper.setLogtimes(logtimes + 1);
		oper.setLasttime(nowtime);
		persistentService.save(oper);

		OperatorInfo bo = new OperatorInfo();
		bo.setId(oper.getId());
		bo.setName(oper.getName());
		bo.setLoginname(oper.getLoginname());

		// 获取操作员角色
		PrvOperrole role = new PrvOperrole();
		role.setOperid(oper.getId());
		List<PrvOperrole> oproleList = persistentService.find(role);
		bo.setRoles(new HashSet(oproleList));

		// 获取操作员部门
		String sql = "SELECT deptid id,name,kind,areaid,preid,deplevel FROM prv_department where deptid in(select deptid from prv_operrole where operid=?) order by deptid";
		List opdepList = persistentService.find(sql, PrvDepartment.class,
				oper.getId());
		bo.setDepts(new LinkedHashSet(opdepList));

		return bo;
	}

	public void logPrvLogin(Long operid, String ipaddr, String macaddr)
			throws Exception {
		LogPrvLogin log = new LogPrvLogin();
		log.setIpaddr(ipaddr);
		log.setMacaddr(macaddr);
		log.setOperid(operid);
		log.setOptime(new Date());
		persistentService.save(log);
	}

	public OperatorInfo getOperatorInfo(String loginname) throws Exception {
		PrvOperator operator = new PrvOperator();
		operator.setLoginname(loginname);
		List list = persistentService.find(operator);
		if (list == null || list.size() < 1)
			throw new BusinessException("用户名不存在！");

		PrvOperator oper = (PrvOperator) list.get(0);

		persistentService.clear();

		OperatorInfo bo = new OperatorInfo();
		BeanUtils.copyProperties(bo, oper);

		// 获取操作员部门
		String sql = "SELECT deptid id,kind,name,preid,areaid,sortby,deplevel FROM prv_department where deptid in("
				+ "SELECT deptid FROM prv_operrole a where exists(select 1 from prv_roleinfo where roleid=a.roleid) "
				+ "and a.stime<sysdate and a.etime>sysdate and a.operid=?)";
		List<PrvDepartment> opdepList = persistentService.find(sql,
				PrvDepartment.class, oper.getId());
		bo.setDepts(new LinkedHashSet(opdepList));
		return bo;
	}

	public LoginInfo login(LoginParam param,String atype) throws Exception {
		return createLoginInfo(param.getLoginname(), param.getPassword(),
				param.getDeptid(),param.getIsPwdCrypt(),atype);
	}

	public LoginInfo createLoginInfo(String loginname, String password,
			Long deptid,Boolean isPwdCrypt,String atype) throws Exception {
		LoginInfo loginInfo = new LoginInfo();
		OperatorInfo operator = checkUser(loginname, password,isPwdCrypt);
		loginInfo.setOperid(operator.getId());
		loginInfo.setName(operator.getName());
		loginInfo.setLoginname(operator.getLoginname());

		Set departSet = operator.getDepts();

		if (departSet == null)
			throw new BusinessException("创建SESSION出错：该操作员没有定义部门");
		loginInfo.setDepartCount(departSet.size());
		loginInfo.setDepartSet(departSet);

		PrvDepartment mydepart = null;

		for (Iterator iterator = departSet.iterator(); iterator.hasNext();) {
			PrvDepartment depart = (PrvDepartment) iterator.next();
			if (depart.getId().equals(deptid))
				mydepart = depart;
		}

		if (mydepart == null) {
			throw new BusinessException("创建SESSION出错：找不到对应的部门");
		}

		PrvOperrole operrole = getOperRole(operator.getId(), deptid,atype);

		if (null == operrole) {
			throw new BusinessException("操作员角色不匹配或已过期或未生效");
		}

		loginInfo.setDeptid(mydepart.getId());
		loginInfo.setDeptname(mydepart.getName());
		loginInfo.setDeplevel(mydepart.getDeplevel());
		loginInfo.setDeptkind(mydepart.getKind());
		
		loginInfo.setRoleid(operrole.getRoleid());
		PrvRoleinfo roleinfo = (PrvRoleinfo)persistentService.find(PrvRoleinfo.class,operrole.getRoleid());
		loginInfo.setRolelevel(roleinfo.getRolelevel());
		loginInfo.setAreaid(mydepart.getAreaid());
		PrvArea area = (PrvArea) persistentService.find(PrvArea.class,
				mydepart.getAreaid());
		
		if(null==area){
		    throw new BusinessException("获取业务区为空");
		}
		loginInfo.setCity(area.getCity());
		loginInfo.setCityname(paramService.getMname("PRV_CITY", area.getCity()));
		loginInfo.setHeadImg(getHeadImg(operator.getId()));
		// setOperRights(loginInfo);

		List<PrvMenudef> rootMenuList = getOperMenu(loginInfo.getOperid(),
				operrole.getRoleid());
		List<Long> rootMenu = new ArrayList();
		for (PrvMenudef tmpMenu : rootMenuList) {
			if (tmpMenu.getPremenuid().toString().equals("-1")) {
				rootMenu.add(tmpMenu.getId());
			}
		}

		loginInfo.setRootmenuid(rootMenu);
		return loginInfo;
	}
	
	private String getHeadImg(Long operid) throws Exception{
		String sql = "select head_img headImg from prv_oper_photo where operid = ?";
		List<HeadImgBean> datas = persistentService.find(sql, HeadImgBean.class, operid);
		if(datas == null || datas.isEmpty()){
			return "";
		}
		return datas.get(0).getHeadImg();
	}

	public PrvDepartment getDepartment(PrvDepartment depart) throws Exception {
		PrvDepartment department = new PrvDepartment();
		List list = persistentService.find(depart);
		if (list == null || list.size() < 1)
			return null;

		department = (PrvDepartment) list.get(0);
		return department;
	}

	public PrvOperrole getOperRole(Long operid, Long deptid,String atype) throws Exception {

		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t1.operroleid		id, ");
		sqlBuffer.append(" t1.operid			  operid,			");
		sqlBuffer.append(" t1.roleid			  roleid,			");
		sqlBuffer.append(" t1.priority	    priority,	  ");
		sqlBuffer.append(" t1.stime	      stime,	    ");
		sqlBuffer.append(" t1.etime	      etime,	    ");
		sqlBuffer.append(" t1.deptid	      deptid	    ");
		sqlBuffer
				.append(" from prv_operrole t1,prv_roleinfo t2 where 1=1 and t1.roleid = t2.roleid ");
		sqlBuffer.append(" and t1.operid=? ");
		params.add(operid);
		sqlBuffer.append(" and t1.deptid=? ");
		params.add(deptid);
		if(StringUtils.isNotBlank(atype)){
			sqlBuffer.append(" and t2.atype = ?");
			params.add(atype);
		}
		sqlBuffer.append(" and t1.stime <= ? ");
		params.add(new Date());
		sqlBuffer.append(" and t1.etime >= ? ");
		params.add(new Date());
		sqlBuffer.append(" and t2.stime <= ? ");
		params.add(new Date());
		sqlBuffer.append(" and t2.etime >= ? ");
		params.add(new Date());
		

		List list = persistentService.find(sqlBuffer.toString(),
				PrvOperrole.class, params.toArray());
		if (list == null || list.size() < 1)
			return null;

		PrvOperrole operrole = (PrvOperrole) list.get(0);
		return operrole;
	}
	
	public List<PrvMenudef> getOperMenu(Long operid, Long roleid)
	throws Exception {
		return getOperMenu(operid, roleid, null);
	}
	
	public List<PrvMenudef> getOperMenu(Long operid,Long roleid,Integer sysid,String city) throws Exception{
		List params = new ArrayList();
		params.add(SysConstant.SysYesNo.YES);
		params.add("%"+sysid+"%");
		params.add(roleid);
		params.add(SysConstant.SysYesNo.YES);
		params.add("%"+sysid+"%");
		params.add(operid);
		params.add(operid);
		params.add(city);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,       premenuid,       name,       linkurl,       iconid,");
		sql.append("       flag,       prefix,       attr,       pinyin,       memo,");
		sql.append("       bizcode,       target,       seqno, oflag1, oflag2,atype ");
		sql.append("  FROM (SELECT a.menuid    id,               a.premenuid premenuid,");
		sql.append("               a.name      name,               a.linkurl   linkurl,");
		sql.append("               a.iconid    iconid,               a.flag      flag,");
		sql.append("               a.prefix    prefix,               a.attr      attr,");
		sql.append("               a.pinyin    pinyin,               a.memo      memo,");
		sql.append("               a.bizcode   bizcode,               a.target    target,");
		sql.append("               a.seqno     seqno,                a.oflag1    oflag1,");
		sql.append("               a.oflag2    oflag2,                a.atype    atype ");
		sql.append("          FROM prv_menudef a, prv_roleprivs b");
		sql.append("         WHERE a.menuid = b.menuid");
		sql.append("           AND a.attr = ?");
		if (sysid != null) {
			sql.append("           AND a.sysid like ?" );
		}
		sql.append("           AND b.roleid = ?");
		sql.append("        UNION");
		sql.append("        SELECT a.menuid    id,               a.premenuid premenuid,");
		sql.append("               a.name      name,               a.linkurl   linkurl,");
		sql.append("               a.iconid    iconid,               a.flag      flag,");
		sql.append("               a.prefix    prefix,               a.attr      attr,");
		sql.append("               a.pinyin    pinyin,               a.memo      memo,");
		sql.append("               a.bizcode   bizcode,               a.target    target,");
		sql.append("               a.seqno     seqno,                a.oflag1    oflag1,");
		sql.append("               a.oflag2    oflag2,                a.atype    atype ");
		sql.append("          FROM prv_menudef a, prv_operprivs b");
		sql.append("         WHERE a.menuid = b.menuid");
		sql.append("           AND a.attr = ?");
		if (sysid != null) {
			sql.append("           AND a.sysid like ?" );
		}
		sql.append("           AND b.operid = ?) as menu ");
		sql.append("	 LEFT JOIN OPT_ACTION_RECORD C ON bizcode = C.OPCODE");
		sql.append("	 	AND C.OPTID = ?");
		sql.append("		AND C.CITY = ?");
		sql.append(" ORDER BY C.NUMS DESC,seqno ASC");
		return persistentService.find(sql.toString(), PrvMenudef.class,params.toArray());
	}

	public List<PrvMenudef> getOperMenu(Long operid, Long roleid, Integer sysid)
			throws Exception {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT id,       premenuid,       name,       linkurl,       iconid,");
		sql.append("       flag,       prefix,       attr,       pinyin,       memo,");
		sql.append("       bizcode,       target,       seqno, oflag1, oflag2 ");
		sql.append("  FROM (SELECT a.menuid    id,               a.premenuid premenuid,");
		sql.append("               a.name      name,               a.linkurl   linkurl,");
		sql.append("               a.iconid    iconid,               a.flag      flag,");
		sql.append("               a.prefix    prefix,               a.attr      attr,");
		sql.append("               a.pinyin    pinyin,               a.memo      memo,");
		sql.append("               a.bizcode   bizcode,               a.target    target,");
		sql.append("               a.seqno     seqno,                a.oflag1    oflag1,");
		sql.append("               a.oflag2    oflag2 ");
		sql.append("          FROM prv_menudef a, prv_roleprivs b");
		sql.append("         WHERE a.menuid = b.menuid");
		sql.append("           AND a.attr = '" + SysConstant.SysYesNo.YES + "'");
		if (sysid != null) {
			sql.append("	AND a.sysid = ");
			sql.append(sysid);
		}
		sql.append("           AND b.roleid = ?");
		sql.append("        UNION");
		sql.append("        SELECT a.menuid    id,               a.premenuid premenuid,");
		sql.append("               a.name      name,               a.linkurl   linkurl,");
		sql.append("               a.iconid    iconid,               a.flag      flag,");
		sql.append("               a.prefix    prefix,               a.attr      attr,");
		sql.append("               a.pinyin    pinyin,               a.memo      memo,");
		sql.append("               a.bizcode   bizcode,               a.target    target,");
		sql.append("               a.seqno     seqno,                a.oflag1    oflag1,");
		sql.append("               a.oflag2    oflag2 ");
		sql.append("          FROM prv_menudef a, prv_operprivs b");
		sql.append("         WHERE a.menuid = b.menuid");
		sql.append("           AND a.attr = '" + SysConstant.SysYesNo.YES + "'");
		if (sysid != null) {
			sql.append("	AND a.sysid = ");
			sql.append(sysid);
		}
		sql.append("           AND b.operid = ?) as menu ");
		sql.append(" order by seqno");
		return persistentService.find(sql.toString(), PrvMenudef.class, roleid,
				operid);
	}

	public void delOperShortcut(Long operid, Long roleid, Long menuid)
			throws Exception {
		PrvShortcut opershortcut = new PrvShortcut();
		opershortcut.setMenuid(menuid);
		opershortcut.setOperid(operid);
		opershortcut.setRoleid(roleid);
		persistentService.delete(opershortcut);
	}

	public List<PrvMenudef> getOperShortcut(Long operid, Long roleid, Long sysid)
			throws Exception {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT t2.menuid    id,");
		sql.append("        t2.premenuid premenuid,");
		sql.append("        t2.name      name,");
		sql.append("        t2.linkurl   linkurl,");
		sql.append("        t2.iconid    iconid,");
		sql.append("        t2.flag      flag,");
		sql.append("        t2.prefix    prefix,");
		sql.append("        t2.attr      attr,");
		sql.append("        t2.pinyin    pinyin,");
		sql.append("        t2.memo      memo,");
		sql.append("        t2.oflag1    oflag1,");
		sql.append("        t2.oflag2    oflag2 ");
		sql.append("   FROM prv_shortcut t1, prv_menudef t2");
		sql.append("  WHERE t1.menuid = t2.menuid");
		sql.append("    and t1.sysid = ?");
		sql.append("    and t1.roleid = ?");
		sql.append("    and t1.operid = ?");
		sql.append("  order by t1.recid");
		return persistentService.find(sql.toString(), PrvMenudef.class, sysid,
				roleid, operid);
	}
}
