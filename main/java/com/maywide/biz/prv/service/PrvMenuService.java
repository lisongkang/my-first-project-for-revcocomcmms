package com.maywide.biz.prv.service;

import com.google.common.collect.Lists;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.dao.PrvMenudefDao;
import com.maywide.biz.prv.entity.PrvMenudef;
import com.maywide.common.sys.vo.NavMenuVO;
import com.maywide.common.sys.vo.TreeMenuVO;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrvMenuService extends BaseService<PrvMenudef, Long> {
	private static final String ROOT_MENU_ID = "99";
	private static final String APP_ROOT_MENU = "APP_ROOT_MENU";
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PrvMenudefDao prvMenudefDao;

	@Override
	protected BaseDao<PrvMenudef, Long> getEntityDao() {
		return prvMenudefDao;
	}

	@Autowired
	private PersistentService persistentService;
	@Autowired
	private LoginService loginService;

	@Override
	public void delete(PrvMenudef entity) {
		deleteMenu(entity);
	}

	@Transactional(readOnly = true)
	public List<PrvMenudef> findAll() throws Exception {
		PrvMenudef menuParam = new PrvMenudef();
		menuParam.set_orderby_prefix(Constant.ORDER_ASC);
		menuParam.set_orderby_seqno(Constant.ORDER_ASC);
		String hql = "from PrvMenudef order by sysid desc,id";
		List<PrvMenudef> menus = persistentService.find(hql, null);
		try {
			for (PrvMenudef prvMenudef : menus) { // 给新版本加个（新）
				if (prvMenudef.getSysid() != null && prvMenudef.getSysid().equals(20)) {
					prvMenudef.setName(prvMenudef.getName() + "(新)");
				}
				if(prvMenudef.getSysid() != null && prvMenudef.getSysid().equals(90)){
					prvMenudef.setName(prvMenudef.getName()+"(集客)");
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return menus;
	}
	@Transactional(readOnly = true)
	public List<PrvMenudef> findAllLoginRole() throws Exception {
		PrvMenudef menuParam = new PrvMenudef();
		menuParam.set_orderby_prefix(Constant.ORDER_ASC);
		menuParam.set_orderby_seqno(Constant.ORDER_ASC);
		String hql = "from PrvMenudef order by sysid desc,id";
		List<PrvMenudef> menus = persistentService.find(hql, null);
		try {
			for (PrvMenudef prvMenudef : menus) { // 给新版本加个（新）
				if (prvMenudef.getSysid() != null && prvMenudef.getSysid().equals(20)) {
					prvMenudef.setName(prvMenudef.getName() + "(新)");
				}
				if(prvMenudef.getSysid() != null && prvMenudef.getSysid().equals(90)){
					prvMenudef.setName(prvMenudef.getName()+"(集客)");
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return menus;
	}
	@Transactional(readOnly = true)
	public List<PrvMenudef> findRoots() {
		List<PrvMenudef> menus = Lists.newArrayList();
		Iterable<PrvMenudef> allMenus = prvMenudefDao.findAll();

		for (PrvMenudef menu : allMenus) {
			if (menu.getPremenuid() == 99) {
				menus.add(menu);
			}
		}

		return menus;
	}

	/**
	 * 根据角色编号，构造赋权的菜单树
	 * 
	 * @param roleid
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<TreeMenuVO> findAllTreeMenus(Long roleid) throws Exception {
		List<TreeMenuVO> treeMenus = Lists.newArrayList();
		List<NavMenuVO> menuVOs = Lists.newArrayList();
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		//对于级别=高，不过滤 超级管理员全部菜单出来
		String city = loginInfo.getCity();
		List<PrvMenudef> menus;
		Long loginRoleid = Long.valueOf(loginInfo.getRoleid());
		if (StringUtils.isNotEmpty(city) && Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
			 menus = findAll();
		}else if("9".equals(loginInfo.getRolelevel())){
			 menus = findAll();
		}else {
			 menus =loginService.getOperMenu(0L, loginRoleid, null);
		}
		menuVOs = createTreeMenu(menus);

		List<NavMenuVO> authMenus = findMenus(0L, roleid, null, false);
		Map menuMap = new HashMap();

		putMap(menuMap, authMenus);
		/*
		 * for (NavMenuVO menuVO : authMenus) { menuMap.put(menuVO.getId(),
		 * menuVO);
		 * 
		 * }
		 */

		createTreeVO(treeMenus, menuVOs, menuMap);

		/*
		 * TreeMenuVO treeMenuVO = null; for (NavMenuVO menuVO : menuVOs) {
		 * treeMenuVO = new TreeMenuVO(); treeMenuVO.setId(menuVO.getId());
		 * treeMenuVO.setPid((menuVO.getParent() == null) ? "1" :
		 * menuVO.getParent().getId()); treeMenuVO.setName(menuVO.getName());
		 * 
		 * treeMenuVO.setChecked(menuMap.get(menuVO.getId()) != null);
		 * treeMenuVO.setParent(menuVO.getChildrenSize() > 0);
		 * treeMenuVO.setOpen(treeMenuVO.isParent() && treeMenuVO.isChecked());
		 * 
		 * treeMenus.add(treeMenuVO); }
		 */

		return treeMenus;
	}

	/**
	 * 根据操作员编号/角色编号查询用户菜单，目前只有角色编号有效
	 * 
	 * @param operid
	 * @param roleid
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<NavMenuVO> findMenus(Long operid, Long roleid, Integer sysid, boolean isRemoveVirtualMenu)
			throws Exception {
		List<NavMenuVO> menuVOs = Lists.newArrayList();
		List<PrvMenudef> authMenus = loginService.getOperMenu(operid, roleid, sysid);

		// 虚拟菜单不在前台显示，过滤掉
		/*
		 * if (isRemoveVirtualMenu) { removeVirtualMenu(authMenus); }
		 */

		menuVOs = createTreeMenu(authMenus);

		if (menuVOs.size() > 0)
			menuVOs.get(0).setOpen(true);

		return menuVOs;
	}

	private List<PrvMenudef> removeVirtualMenu(List<PrvMenudef> allMenus) throws Exception {

		if (BeanUtil.isListNull(allMenus)) {
			return allMenus;
		}

		List<Long> removeMenuidList = getRemoveMenuidList(allMenus);

		List<PrvMenudef> retList = Lists.newArrayList(allMenus);
		if (BeanUtil.isListNotNull(removeMenuidList)) {
			for (Long tmpMenuid : removeMenuidList) {
				removeMenuWithEmptyParent(allMenus, tmpMenuid);
			}
		}

		return retList;
	}

	private List<Long> getRemoveMenuidList(List<PrvMenudef> allMenus) {
		List<Long> removeMenuidList = Lists.newArrayList();
		for (PrvMenudef tmpMenu : allMenus) {
			if (APP_ROOT_MENU.equals(tmpMenu.getLinkurl())) {
				removeMenuidList.add(tmpMenu.getId());
			}
		}
		return removeMenuidList;
	}

	private void removeMenuWithEmptyParent(List<PrvMenudef> allMenus, Long removeMenuid) throws Exception {

		if (BeanUtil.isListNull(allMenus) || null == removeMenuid) {
			return;
		}

		PrvMenudef retRemoveMenu = removeMenu(allMenus, removeMenuid);

		if (null != retRemoveMenu && null != retRemoveMenu.getId()) {
			Long tmpMenuid = retRemoveMenu.getPremenuid();

			PrvMenudef tmpMenu = new PrvMenudef();
			tmpMenu.setId(tmpMenuid);
			List<PrvMenudef> children = findChildren(tmpMenu, allMenus);
			if (BeanUtil.isListNull(children)) {
				removeMenuWithEmptyParent(allMenus, tmpMenuid);
			}
		}

	}

	private PrvMenudef removeMenu(List<PrvMenudef> allMenus, Long removeMenuid) throws Exception {

		PrvMenudef retRemovedMenu = new PrvMenudef();
		if (BeanUtil.isListNull(allMenus)) {
			return retRemovedMenu;
		}
		for (int i = 0; i < allMenus.size(); i++) {
			if (removeMenuid.equals(allMenus.get(i).getId())) {

				retRemovedMenu = allMenus.get(i);
				allMenus.remove(i);
			}
		}

		return retRemovedMenu;
	}

	private List<NavMenuVO> createTreeMenu(List<PrvMenudef> menus) throws Exception {
		List<NavMenuVO> menuVOs = Lists.newArrayList();

		if (CollectionUtils.isEmpty(menus)) {
			return menuVOs;
		}

		for (PrvMenudef tmpMenu : menus) {
			if (tmpMenu.getPremenuid().toString().equals(ROOT_MENU_ID)) {
				loopMenu(null, menuVOs, tmpMenu, menus);
			}
		}

		return menuVOs;
	}

	public List<PrvMenudef> findChildren(PrvMenudef menu, List<PrvMenudef> allMenus) {
		List<PrvMenudef> children = Lists.newArrayList();
		for (PrvMenudef prvMenu : allMenus) {
			if (prvMenu.getPremenuid().longValue() == menu.getId().longValue()) {
				children.add(prvMenu);
			}
		}

		return children;
	}

	private void loopMenu(NavMenuVO parent, List<NavMenuVO> menuVOs, PrvMenudef menu, List<PrvMenudef> allMenus)
			throws Exception {

		NavMenuVO item = new NavMenuVO();
		menuVOs.add(item);
		item.setParent(parent);
		item.setId(menu.getId().toString());
		item.setCode(menu.getId().toString());
		item.setName(menu.getName());
		item.setIcon(menu.getIconid());
		item.setOpen(false);
		item.setUrl(menu.getLinkurl());
		item.setShow(true);
		item.setSysid(menu.getSysid());

		List<PrvMenudef> children = findChildren(menu, allMenus);
		if (!CollectionUtils.isEmpty(children)) {
			List<NavMenuVO> childrenList = Lists.newArrayList();
			item.setChildren(childrenList);
			for (PrvMenudef child : children) {
				loopMenu(item, childrenList, child, allMenus);
			}
		}
	}

	public int deleteMenu(PrvMenudef menu) {
		List<PrvMenudef> menus = Lists.newArrayList();
		menus.add(menu);

		PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "premenuid", menu.getId());
		List<PrvMenudef> children = findByFilter(propertyFilter);

		super.delete(menu);
		try {
			persistentService.executeUpdate("DELETE FROM PrvRoleprivs WHERE menuid = ?", menu.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}

		for (PrvMenudef item : children) {
			deleteMenu(item);
		}

		return menus.size();
	}

	private void putMap(Map map, List<NavMenuVO> menus) {
		for (NavMenuVO menu : menus) {
			map.put(menu.getId(), menu);
			if (menu.getChildrenSize() > 0) {
				putMap(map, menu.getChildren());
			}
		}
	}

	public org.springframework.data.domain.Page<Map> mobileMenus(org.springframework.data.domain.Pageable pageable,String name) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT A.* FROM prv_menudef A WHERE ");
		sql.append("	A.sysid = 20");
		sql.append("	AND A.linkurl IS NOT NULL");
		if(StringUtils.isNotBlank(name)){
			sql.append("	AND A.name like '%"+name+"%'");
		}
		return (org.springframework.data.domain.Page<Map>) this.findByPageNativeSQL(pageable, sql.toString());
	}

	public org.springframework.data.domain.Page<Map> menuForJump(org.springframework.data.domain.Pageable pageable,String name) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT A.* FROM prv_menudef A WHERE ");
		sql.append("	A.sysid = 20");
		sql.append("	AND A.linkurl IS NOT NULL");
		if(StringUtils.isNotBlank(name)){
			sql.append("	AND A.name like '%"+name+"%'");
		}
		return (org.springframework.data.domain.Page<Map>) this.findByPageNativeSQL(pageable, sql.toString());
	}

	private void createTreeVO(List<TreeMenuVO> treeMenus, List<NavMenuVO> menus, Map menuMap) {
		TreeMenuVO treeMenuVO = null;
		for (NavMenuVO menuVO : menus) {
			treeMenuVO = new TreeMenuVO();
			treeMenuVO.setId(menuVO.getId());
			treeMenuVO.setPid((menuVO.getParent() == null) ? "1" : menuVO.getParent().getId());
			treeMenuVO.setName(menuVO.getName());

			treeMenuVO.setChecked(menuMap.get(menuVO.getId()) != null);
			treeMenuVO.setParent(menuVO.getChildrenSize() > 0);
			treeMenuVO.setOpen(treeMenuVO.getIsParent() && treeMenuVO.isChecked());

			treeMenus.add(treeMenuVO);

			if (menuVO.getChildrenSize() > 0) {
				createTreeVO(treeMenus, menuVO.getChildren(), menuMap);
			}
		}
	}
}
