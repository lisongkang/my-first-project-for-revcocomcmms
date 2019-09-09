package com.maywide.biz.prv.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.sql.visitor.functions.If;
import com.google.common.collect.Lists;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.prv.dao.PrvDepartmentDao;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.pubpost.entity.PubPostDept;
import com.maywide.common.sys.vo.NavMenuVO;
import com.maywide.common.sys.vo.TreeMenuVO;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.SimpleSqlCreator;

@Service
@Transactional
public class PrvDepartmentService extends BaseService<PrvDepartment,Long>{
	private final static Long ROOT_ID = 0L;
    
    @Autowired
    private PrvDepartmentDao  prvDepartmentDao;

    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private ParamService paramservice;

    @Override
    protected BaseDao<PrvDepartment, Long> getEntityDao() {
        return prvDepartmentDao;
    }
    
    /**
     * 查询常用数据用于缓存
     * @return
     */
    public List<PrvDepartment> findAllDepartments() {
    	return Lists.newArrayList(prvDepartmentDao.findAllCached());
    }
    
    /**
     * 按公告id查询部门目录树
     * @return
     * @throws Exception 
     */
    public List<TreeMenuVO> findTreeListByPubPost(String pubId) throws Exception {
    	List<PrvDepartment> allDepts = findDepartByCity(true);
    	List<TreeMenuVO> treeList = Lists.newArrayList();
    	List<NavMenuVO> allItems = null;
    	
    	Long rootId = ROOT_ID;
    	if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
    		rootId = -1L;
		}
    	
    	allItems = createTree(allDepts, rootId);
    	Map<String, String> map = new HashMap<String, String>();
    	
    	if (StringUtils.isNotBlank(pubId)) {
    		List<PubPostDept> depts = findDepts(pubId);
    		putMap(map, depts);
    	}
    	
    	createTreeVO(treeList, allItems, map);
    	
    	return treeList;
    }
    
    private List<NavMenuVO> createTree(List<PrvDepartment> items, Long rootId) {
    	List<NavMenuVO> menuVOs = Lists.newArrayList();
    	if (CollectionUtils.isEmpty(items)) {
			return menuVOs;
		}
    	
    	for (PrvDepartment dept : items) {
    		if (dept.getPreid() == rootId) {
    			loopItem(null, menuVOs, dept, items);
    		}
    	}
    	
    	return menuVOs;
    }
    
    private void loopItem(NavMenuVO parent, List<NavMenuVO> menuVOs, PrvDepartment department,
            List<PrvDepartment> items) {
        NavMenuVO item = new NavMenuVO();
        menuVOs.add(item);
        item.setParent(parent);
        item.setId(department.getId().toString());
        item.setCode(department.getId().toString());
        item.setName(department.getName());
        item.setOpen(false);
        item.setShow(true);

        List<PrvDepartment> children = findChildren(department, items);

        if (!CollectionUtils.isEmpty(children)) {
            List<NavMenuVO> childrenList = Lists.newArrayList();
            item.setChildren(childrenList);
            for (PrvDepartment child : children) {
                loopItem(item, childrenList, child, items);
            }
        }
    }
    
    private List<PrvDepartment> findChildren(PrvDepartment department, List<PrvDepartment> items) {
        List<PrvDepartment> children = Lists.newArrayList();
        for (PrvDepartment dept : items) {
            if (dept.getPreid()!=null&&department.getId()!=null&&dept.getPreid().longValue() == department.getId().longValue()) {
                children.add(dept);
            }
        }

        return children;
    }
    
    private List<PubPostDept> findDepts(String pubId) {
    	List<PubPostDept> depts = Lists.newArrayList();
    	try {
    		PubPostDept queryParam = new PubPostDept();
    		queryParam.setPubId(pubId);
    		depts = (List<PubPostDept>) persistentService.find(queryParam);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
    	return depts;
    }
    
    private void putMap(Map map, List<PubPostDept> depts) {
		for (PubPostDept dept : depts) {
			map.put(dept.getDeptId().toString(), dept.getPubId());			
		}
	}
    
    private void createTreeVO(List<TreeMenuVO> treeList, List<NavMenuVO> allItems, Map treeMap) {
		TreeMenuVO treeMenuVO = null;
		for (NavMenuVO menuVO : allItems) {
			treeMenuVO = new TreeMenuVO();
			treeMenuVO.setId(menuVO.getId());
			treeMenuVO.setPid((menuVO.getParent() == null) ? "0" : menuVO.getParent().getId());
			treeMenuVO.setName(menuVO.getName());

			treeMenuVO.setChecked(treeMap.get("0") != null || treeMap.get(menuVO.getId()) != null);
			treeMenuVO.setParent(menuVO.getChildrenSize() > 0);
			treeMenuVO.setOpen(treeMenuVO.getIsParent() && treeMenuVO.isChecked());
			
			treeList.add(treeMenuVO);
			
			if (menuVO.getChildrenSize() > 0) {
				createTreeVO(treeList, menuVO.getChildren(), treeMap);
			}
		}
	}

    @SuppressWarnings("unchecked")
    public PageImpl<PrvDepartment> findDepartByOperid(String operid, String deptName, Pageable pageable,
            String orderField, String sortType) throws Exception {
        PageImpl<PrvDepartment> pageResult = null;
        Page<PrvDepartment> page = new Page<PrvDepartment>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = getCommonQueDeptSql();
        List<Object> paramList = new ArrayList<Object>();

        if (StringUtils.isNotBlank(operid)) {
            sql.replace(sql.indexOf(" WHERE 1=1"), sql.length(), "");
            sql.append(",prv_operdept pod WHERE o.DEPTID=pod.depid AND pod.operid=?");
            paramList.add(operid);
        }

        if (StringUtils.isNotBlank(deptName)) {
            sql.append(" AND o.NAME LIKE ?");
            paramList.add("%" + deptName + "%");
        }
        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField);
            sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
        } else {
            sql.append(" ORDER BY o.city) n");
        }

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<PrvDepartment> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<PrvDepartment>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<PrvDepartment>(new ArrayList<PrvDepartment>(), pageable, 0);
        }
        return pageResult;
    }

    @SuppressWarnings("unchecked")
    public PageImpl<PrvDepartment> findPageDepartByCity(String deptName, Pageable pageable, String orderField,
            String sortType) throws Exception {
        PageImpl<PrvDepartment> pageResult = null;
        Page<PrvDepartment> page = new Page<PrvDepartment>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = getCommonQueDeptSql();
        List<Object> paramList = new ArrayList<Object>();

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = loginInfo.getCity();
        String loginName = loginInfo.getLoginname();

        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginName)) {
            // 超级管理员查询全部地市的部门
            sql.append(" AND o.city=?");
            paramList.add(city);
        }

        if (!Constant.ADMINISTRATOR.equals(loginName)) {
            sql.append(" AND o.NAME<>?");
            paramList.add(Constant.CYYYCS_DEPT_NAME);
        }

        if (StringUtils.isNotBlank(deptName)) {
            sql.append(" AND o.NAME LIKE ?");
            paramList.add("%" + deptName + "%");
        }
        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField);
            sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
        } else {
            sql.append(" ORDER BY o.city) n");
        }

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<PrvDepartment> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<PrvDepartment>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<PrvDepartment>(new ArrayList<PrvDepartment>(), pageable, 0);
        }
        return pageResult;
    }

    /**
     * 
     * @param adminNeedFilter 超级管理员是否需要按地市过滤(true表示超级管理员可查询全部地市)
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PrvDepartment> findDepartByCity(boolean adminNeedFilter) throws Exception {
        StringBuffer sql = getCommonQueDeptSql();
        List<Object> paramList = new ArrayList<Object>();

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = loginInfo.getCity();
        String loginName = loginInfo.getLoginname();
        //大连的默认不根据city查询
        if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
		}
        else if (StringUtils.isNotEmpty(city) && (!Constant.ADMINISTRATOR.equals(loginName) || adminNeedFilter)) {
            // 超级管理员查询全部地市的部门
            sql.append(" AND o.city=?");
            paramList.add(city);
        }

        if (!Constant.ADMINISTRATOR.equals(loginName)) {
            sql.append(" AND o.NAME<>?");
            paramList.add(Constant.CYYYCS_DEPT_NAME);
        }
        sql.append(" ORDER BY o.city) n");

        persistentService.clear();
        List<PrvDepartment> resultList = persistentService.find(sql.toString(), entityClass, paramList.toArray());

        if (null == resultList) {
            resultList = new ArrayList<PrvDepartment>();
        }
        return resultList;
    }

    private StringBuffer getCommonQueDeptSql() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM(");
        sql.append(SimpleSqlCreator.createSelectAllFieldSql(entityClass));
        sql.append(" WHERE 1=1");
        return sql;
    }

    @SuppressWarnings("unchecked")
    public List<PrvDepartment> findDeptTree() throws Exception {
        StringBuffer sql = new StringBuffer(SimpleSqlCreator.createSelectAllFieldSql(entityClass));
        sql.append(" WHERE o.PREID IS NOT NULL");

        List<Object> paramList = new ArrayList<Object>();
        LoginInfo logininfo = AuthContextHolder.getLoginInfo();
        String city = logininfo.getCity();
        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(logininfo.getLoginname())) {
            // 超级管理员查询全部地市的部门
            sql.append(" AND o.city = ?");
            paramList.add(city);
        }

        sql.append(" ORDER BY o.city,o.SORTBY DESC,o.NAME");

        List<PrvDepartment> deptTreeList = persistentService.find(sql.toString(), entityClass, paramList.toArray());

        if (null == deptTreeList || deptTreeList.size() == 0) {
            return new ArrayList<PrvDepartment>();
        } else {
            return deptTreeList;
        }
    }
    
    /**
     * 查询第一个preid=0且最高排序优先级的部门
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public PrvDepartment getFirstRootDept() throws Exception {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(0);
        StringBuffer sql = SimpleSqlCreator.createSelectAllFieldSql(entityClass);
        sql.append(" WHERE o.PREID=?");

        LoginInfo logininfo = AuthContextHolder.getLoginInfo();
        String city = logininfo.getCity();
        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(logininfo.getLoginname())) {
            sql.append(" AND o.city=?");
            paramList.add(city);
        }
        sql.append(" ORDER BY o.city,o.SORTBY DESC,o.NAME");
        List<PrvDepartment> deptList = persistentService.find(sql.toString(), entityClass, paramList.toArray());
        if (null != deptList && deptList.size() > 0) {
            return deptList.get(0);
        } else {
            throw new ServiceException("找不到preid=0的根部门！");
        }
    }

    public void transGridList(List<PrvDepartment> list) throws Exception {
        for (PrvDepartment dept : list) {
            dept.addExtraAttribute("areaname", getAreaname(dept.getAreaid()));
        }
    }

    private String getAreaname(Long areaid) throws Exception {
        PrvArea area = (PrvArea) persistentService.find(PrvArea.class, areaid);
        if (null != area) {
            return area.getName();
        } else {
            return null;
        }
    }
    
    public String  findAreaidsByOperid(Pageable pageable,String operid) throws Exception {

    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT DISTINCT(de.areaid) FROM prv_department de  WHERE de.deptid IN ( SELECT deptid FROM prv_operrole o WHERE o.operid= ");
        sql.append(operid);    
        sql.append(")");  
        org.springframework.data.domain.Page<Map> p = this.findByPageNativeSQL(pageable, sql.toString());
        List list = p.getContent();
        StringBuilder s= new StringBuilder();
        for(Object o :list){
        	JSONObject js = new JSONObject(o.toString());
        	String ar = js.getString("areaid");
        	s.append(ar);
        	s.append(",");
        }
        if("".equals(s.toString())||null==s){
        	return null;
        }else{
            String st = s.toString().substring(0, s.length()-1);
            return st;
        }
    }   
    
    public org.springframework.data.domain.Page<Map> findOneCityByPage(Pageable pageable,String name) throws Exception {

    	StringBuilder sql = new StringBuilder();
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	sql.append("SELECT t.name , t.deptid ");
        sql.append("   FROM prv_department t WHERE CITY='"+loginInfo.getCity()+"'");    
        if(!"".equals(name)&&null!=name){
        	sql.append(" AND NAME LIKE '%"+name+"%'");
        }
        return this.findByPageNativeSQL(pageable, sql.toString());
    }
    
    
    public String queCompleteDepartment(Long departmentid) throws Exception{
    	PrvDepartment department = prvDepartmentDao.findOne(departmentid);
    	if(department == null){
    		return "";
    	}
    	if(department.getPreid() == 0l){
    		return department.getName();
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(department.getName());
    	PrvDepartment parentDepartment = prvDepartmentDao.findOne(department.getPreid());
    	while(parentDepartment != null && parentDepartment.getPreid() != 0l){
    		sb.append("-");
    		sb.append(parentDepartment.getName());
    		parentDepartment = prvDepartmentDao.findOne(parentDepartment.getPreid());
    	}
    	String[] strs = sb.toString().split("-");
    	sb = new StringBuffer();
    	for(int length = strs.length -1; length >= 0; length --){
    		sb.append(strs[length]);
    		if(length != 0){
    			sb.append("-");
    		}
    	}
    	String city = paramservice.getMname("PRV_CITY", department.getCity());
    	return city+"-"+sb.toString();
    }
}
