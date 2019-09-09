package com.maywide.biz.prv.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.dao.PrvRoleinfoDao;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvOperrole;
import com.maywide.biz.prv.entity.PrvRoleinfo;
import com.maywide.biz.prv.entity.PrvRoleprivs;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.util.SimpleSqlCreator;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrvRoleinfoService extends BaseService<PrvRoleinfo,Long>{
    
    @Autowired
    private PrvRoleinfoDao prvRoleinfoDao;
    
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseDao<PrvRoleinfo, Long> getEntityDao() {
        return prvRoleinfoDao;
    }
    
    public void saveRolePrivs(Long roleid, String menuIds) {
    	try {
    		String hql = "DELETE FROM PrvRoleprivs WHERE roleid = ?";
        	persistentService.executeUpdate(hql, roleid);
        	
        	if (StringUtils.isBlank(menuIds)) return;
        	
        	PrvRoleinfo prinfo = prvRoleinfoDao.findOne(roleid);
        	
        	String[] arrId = menuIds.split(",");
        	PrvRoleprivs rolePriv = null;
        	
        	for (String menuId : arrId) {
        		rolePriv = new PrvRoleprivs();
        		rolePriv.setRoleid(roleid);
        		rolePriv.setMenuid(new Long(menuId));
        		rolePriv.setStarttime(prinfo.getStime());
        		rolePriv.setStoptime(prinfo.getEtime());
        		
        		persist(rolePriv);
        	}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    }
    
    public List<PrvRoleinfo> getOperRoles(Long operId) {
    	List<PrvRoleinfo> roles = Lists.newArrayList();
    	try {
    		roles = (List<PrvRoleinfo>) persistentService.find(new PrvRoleinfo());
    		List<PrvOperrole> operroles = Lists.newArrayList();
    		
    		if (operId != null) {
    			PrvOperrole queryParam = new PrvOperrole();
        		queryParam.setOperid(operId);
        		operroles = (List<PrvOperrole>) persistentService.find(queryParam);
    		}
    		
    		for (PrvRoleinfo role : roles) {
    			role.addExtraAttribute("related", false);
    			for (PrvOperrole operrole : operroles) {
    				if (operrole.getRoleid().longValue() == role.getId()) {
    					role.addExtraAttribute("related", true);
    					break;
    				}
    			}
    		}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return roles;
    }

    @SuppressWarnings("unchecked")
    public PageImpl<PrvRoleinfo> findPageRoleByCity(String queryParam, Pageable pageable, String orderField,
            String sortType) throws Exception {
        PageImpl<PrvRoleinfo> pageResult = null;
        Page<PrvRoleinfo> page = new Page<PrvRoleinfo>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = getCommonQueRoleSql();
        List<Object> paramList = new ArrayList<Object>();

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = loginInfo.getCity();

        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
            // 超级管理员查询全部地市的角色
            addByCityFilterSql(sql);
            paramList.add(city);
        }

        if (StringUtils.isNotBlank(queryParam)) {
            sql.append(" AND o.NAME LIKE ?");
            paramList.add("%" + queryParam + "%");
        }
        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField);
            sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
        } else {
            sql.append(" ORDER BY o.NAME) n");
        }

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<PrvRoleinfo> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<PrvRoleinfo>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<PrvRoleinfo>(new ArrayList<PrvRoleinfo>(), pageable, 0);
        }
        return pageResult;
    }

    @SuppressWarnings("unchecked")
    public List<PrvRoleinfo> findRoleByCity() throws Exception {
        StringBuffer sql = getCommonQueRoleSql();
        List<Object> paramList = new ArrayList<Object>();
        //private String rolelevel;//当角色权限使用，9高，5中，0低 权
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = loginInfo.getCity();
        String rolelevel = loginInfo.getRolelevel();
        Long areaid = loginInfo.getAreaid();

        //对于当前登陆的角色级别=低权，只能查到相同“业务区”的角色；角色=中，可以查到同地市所有业务区的角色；角色=高，可以查到全部角色
        //超级管理员权限不变动   依旧可以配置全部角色  不走if 判断
        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
            if("0".equals(rolelevel)){
                addByCityAreaFilterSql(sql);
                paramList.add(city);
                String areaidTemp = "%" + areaid + "%";
                paramList.add(areaidTemp);
            }else if("5".equals(rolelevel)){
                addByCityRoleFilterSql(sql);
                paramList.add(city);
            }else if("9".equals(rolelevel)||"7".equals(rolelevel)) {
                //高权限什么都不做
            }
        }
        sql.append(" ORDER BY o.NAME) n");

        persistentService.clear();
        List<PrvRoleinfo> resultList = persistentService.find(sql.toString(), entityClass, paramList.toArray());

        if (null == resultList) {
            resultList = new ArrayList<PrvRoleinfo>();
        }
        return resultList;
    }

    private StringBuffer getCommonQueRoleSql() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM(");
        sql.append(SimpleSqlCreator.createSelectAllFieldSql(entityClass));
        sql.append(" WHERE 1=1");
        return sql;
    }

    private void addByCityFilterSql(StringBuffer sql) throws Exception {
        sql.replace(sql.indexOf(" WHERE 1=1"), sql.length(), "");
        sql.append(",").append(SimpleSqlCreator.getEntityTableName(PrvArea.class)).append(" pa");
        sql.append(" WHERE pa.city=? AND split(o.AREAS,',',1)=pa.areaid");
    }
    private void addByCityRoleFilterSql(StringBuffer sql) throws Exception {
        sql.replace(sql.indexOf(" WHERE 1=1"), sql.length(), "");
        sql.append(",").append(SimpleSqlCreator.getEntityTableName(PrvArea.class)).append(" pa");
        sql.append(" WHERE pa.city=? AND split(o.AREAS,',',1)=pa.areaid AND o.ROLELEVEL in ('0','5') ");
    }
    private void addByCityAreaFilterSql(StringBuffer sql) throws Exception {
        sql.replace(sql.indexOf(" WHERE 1=1"), sql.length(), "");
        sql.append(",").append(SimpleSqlCreator.getEntityTableName(PrvArea.class)).append(" pa");
        sql.append(" WHERE pa.city=? AND split(o.AREAS,',',1)=pa.areaid AND o.areas like ? AND o.ROLELEVEL=0  ");
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getAreaMapOrCity(String areas, boolean isGetAreaMap) {
        try {
            Map<String, String> map = new LinkedHashMap<String, String>();
            List<Object> paramList = new ArrayList<Object>();
            if ("*".equals(areas)) {
                if (isGetAreaMap) {
                    map.put("*", "全部");
                } else {
                    map.put("city", "*");
                }
                return map;
            }

            Long areaid = Long.parseLong(areas.split(",")[0]);

            StringBuffer sql = new StringBuffer(SimpleSqlCreator.createSelectAllFieldSql(PrvArea.class));
            sql.append(" WHERE o.city=(SELECT city FROM ");
            sql.append(SimpleSqlCreator.getEntityTableName(PrvArea.class));
            sql.append(" WHERE areaid=?)");
            paramList.add(areaid);
            List<PrvArea> areaList = persistentService.find(sql.toString(), PrvArea.class, paramList.toArray());
            if (null != areaList && areaList.size() > 0) {
                if (isGetAreaMap) {
                    Map<String, String> tempMap = new LinkedHashMap<String, String>();
                    StringBuffer tempStr = new StringBuffer();
                    Collections.sort(areaList, getAreaComparator()); // 要按拼音排序，否则页面中不能正确显示"全部"选项

                    for (PrvArea prvArea : areaList) {
                        String idStr = prvArea.getId() + "";
                        tempMap.put(idStr, prvArea.getName());

                        tempStr.append(idStr).append(",");
                    }
                    map.put(tempStr.substring(0, tempStr.length() - 1), "全部");
                    map.putAll(tempMap);
                } else {
                    map.put("city", areaList.get(0).getCity());
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashMap<String, String>();
        }
    }

    private Comparator<PrvArea> getAreaComparator() {
        return new Comparator<PrvArea>() {
            @Override
            public int compare(PrvArea o1, PrvArea o2) {
                if (o1 == null) {
                    return 1;
                }
                if (o2 == null) {
                    return -1;
                }

                if (StringUtils.isEmpty(o1.getName())) {
                    return 1;
                }
                if (StringUtils.isEmpty(o2.getName())) {
                    return -1;
                }
                String pinyin1 = PinYinUtils.converterToFirstSpell(o1.getName());
                String pinyin2 = PinYinUtils.converterToFirstSpell(o2.getName());

                int rtnval = pinyin1.compareTo(pinyin2);

                return rtnval;
            }
        };
    }
}
