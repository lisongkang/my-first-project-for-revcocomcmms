package com.maywide.biz.prd.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.prd.dao.SalespkgKnowDao;
import com.maywide.biz.prd.entity.Pcode;
import com.maywide.biz.prd.entity.Sales;
import com.maywide.biz.prd.entity.Salespkg;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.system.entity.PrvSysparam;
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
public class SalespkgKnowService extends BaseService<SalespkgKnow, Long> {
    public final static String OBJTYPE_PROD  = "0";
    public final static String OBJTYPE_PKG   = "1";
    public final static String OBJTYPE_SALES = "2";

    @Autowired
    private SalespkgKnowDao    salespkgKnowDao;

    @Autowired
    private PersistentService  persistentService;

    @Autowired
    private ParamService       paramService;

    @Override
    protected BaseDao<SalespkgKnow, Long> getEntityDao() {
        return salespkgKnowDao;
    }

    public List<SalespkgKnow> findAllCached() {
        return salespkgKnowDao.findAllCached();
    }

    public void saveEntity(SalespkgKnow entity, String[] opcodes) {
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if (loginInfo == null) {
                throw new Exception("用户未登录或已过期");
            }
            if (opcodes.length > 0) {
                entity.setOpcodes(StringUtils.join(opcodes, ","));
            }

            if (entity.getId() == null) {
                entity.setCreateoper(loginInfo.getOperid());
                entity.setCreatetime(new Date());
                super.save(entity);
            } else {
                entity.setUpdateoper(loginInfo.getOperid());
                entity.setUpdatetime(new Date());
                persistentService.update(entity);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<PrvSysparam> getOpcodes(SalespkgKnow entity) {
        List<PrvSysparam> opcodes = Lists.newArrayList();
        try {
            opcodes = paramService.getData("BIZ_OPCODE");
            for (PrvSysparam param : opcodes) {
                param.addExtraAttribute("related", false);
                if (StringUtils.isNotBlank(entity.getOpcodes())) {
                    String codes = "," + entity.getOpcodes() + ",";
                    if (codes.indexOf("," + param.getMcode() + ",") > -1) {
                        param.addExtraAttribute("related", true);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        return opcodes;
    }

    public void transEntityList(List<SalespkgKnow> list) {
        for (SalespkgKnow know : list) {
            transEntity(know);
        }
    }

    public void transEntity(SalespkgKnow entity) {
        String[] codeAndName = getObjCodeAndName(entity.getObjtype(), entity.getObjid());
        if (null != codeAndName && codeAndName.length > 0) {
            entity.addExtraAttribute("objcode", codeAndName[0]);
            entity.addExtraAttribute("objname", codeAndName[1]);
        }
    }

    private String[] getObjCodeAndName(String objtype, Long objid) {
        try {
            if (OBJTYPE_PROD.equals(objtype)) {
                Pcode pcode = (Pcode) persistentService.find(Pcode.class, objid);
                if (pcode != null) {
                    return new String[] { pcode.getPcode(), pcode.getPname() };
                }
            } else if (OBJTYPE_PKG.equals(objtype)) {
                Salespkg salespkg = (Salespkg) persistentService.find(Salespkg.class, objid);
                if (salespkg != null) {
                    return new String[] { salespkg.getSalespkgcode(), salespkg.getSalespkgname() };
                }
            } else {
                Sales sales = (Sales) persistentService.find(Sales.class, objid);
                if (sales != null) {
                    return new String[] { sales.getId().toString(), sales.getSalesName() };
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public List<SalespkgKnow> findAllKnowsByCity() throws Exception {
        StringBuffer sql = getCommonQueKnowSql();
        List<Object> paramList = new ArrayList<Object>();

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = loginInfo.getCity();
        boolean isUpdatedCity = checkIsUpdatedCity(city);

        StringBuffer tempSql = new StringBuffer();
        tempSql.append(",CASE o.objtype ");
        tempSql.append("WHEN '0' THEN (SELECT DISTINCT pp.PCODE FROM ")
                .append(SimpleSqlCreator.getEntityTableName(Pcode.class)).append(" pp where pp.PID=o.objid) ");
        tempSql.append("WHEN '1' THEN (SELECT DISTINCT ps.SALESPKGCODE FROM ")
                .append(SimpleSqlCreator.getEntityTableName(Salespkg.class))
                .append(" ps where ps.SALESPKGID=o.objid) ");
        tempSql.append("ELSE (SELECT DISTINCT ns.SALESID FROM ")
                .append(SimpleSqlCreator.getEntityTableName(Sales.class)).append(" ns where ns.SALESID=o.objid) ");
        tempSql.append("END AS objcode");

        sql.insert(sql.lastIndexOf(" FROM"), tempSql);
        if (!isUpdatedCity) {
            sql.append(" AND o.OBJTYPE IN(?,?)");
            paramList.add(BizConstant.PrdSalespkgKnowObjtype.PRD);
            paramList.add(BizConstant.PrdSalespkgKnowObjtype.SALESPKG);
        } else {
            sql.append(" AND o.OBJTYPEIN(?,?)");
            paramList.add(BizConstant.PrdSalespkgKnowObjtype.GOODS);
            paramList.add(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE);
        }

        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
            // 超级管理员查询全部地市的部门
            sql.append(" AND o.city=?");
            paramList.add(city);
        }
        sql.append(" ORDER BY o.city) n");

        persistentService.clear();
        List<SalespkgKnow> resultList = persistentService.find(sql.toString(), entityClass, paramList.toArray());

        if (null == resultList) {
            resultList = new ArrayList<SalespkgKnow>();
        }
        return resultList;
    }

//    @SuppressWarnings("unchecked")
//    public PageImpl<SalespkgKnow> findPageKnowsByCity(String searchField, Pageable pageable, String orderField,
//            String sortType) throws Exception {
//        PageImpl<SalespkgKnow> pageResult = null;
//        Page<SalespkgKnow> page = new Page<SalespkgKnow>();
//        page.setPageNo(pageable.getPageNumber() + 1);
//        page.setPageSize(pageable.getPageSize());
//
//        StringBuffer sql = getCommonQueKnowSql();
//        List<Object> paramList = new ArrayList<Object>();
//
//        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//        String city = loginInfo.getCity();
//        boolean isUpdatedCity = checkIsUpdatedCity(city);
//
//        sql.replace(sql.indexOf(" WHERE 1=1"), sql.length(), "");
//        if (!isUpdatedCity) {
//        	sql.insert(sql.lastIndexOf(" FROM"), ",ps.objcode,ps.objname ");
//        	sql.append(",(");
//            sql.append(" SELECT  p.PID AS objid,p.PCODE AS objcode,p.PNAME AS objname,'0' AS t_name FROM ");
//            sql.append(SimpleSqlCreator.getEntityTableName(Pcode.class)).append(" p");
//            sql.append(" UNION ALL ");
//            sql.append("SELECT  s.SALESPKGID,s.SALESPKGCODE,s.SALESPKGNAME,'3' AS t_name FROM ");
//            sql.append(SimpleSqlCreator.getEntityTableName(Salespkg.class)).append(" s");
//            sql.append(") ps WHERE o.objid=ps.objid ");
//        } else {
//        	 sql.insert(sql.lastIndexOf(" FROM"), ",ps.objcode,ps.objname ");
//        	 sql.append(",(");
//        	 sql.append(" SELECT p.salesid AS objid,p.SALESCODE AS objcode,p.SALESNAME AS objname,'1' AS t_name FROM ");
//             sql.append("  PRD_SALES p");
//             sql.append(" UNION ALL ");
//             sql.append("SELECT  s.SALESPKGID,s.SALESPKGCODE,s.SALESPKGNAME,'3' AS t_name FROM ");
//             sql.append(SimpleSqlCreator.getEntityTableName(Salespkg.class)).append(" s");
//             sql.append(") ps WHERE o.objid=ps.objid ");
//        	/*sql.insert(sql.lastIndexOf(" FROM"), ",ps.SALESCODE as objcode,ps.SALESNAME objname");
//        	sql.append("INNER JOIN PRD_SALES ps ON o.objid = ps.SALESID");*/
//        }
//        if (!isUpdatedCity) {
//        	sql.append(" AND o.OBJTYPE = ps.t_name");
//            sql.append(" AND o.OBJTYPE IN(?,?)");
//            paramList.add(BizConstant.PrdSalespkgKnowObjtype.PRD);
//            paramList.add(BizConstant.PrdSalespkgKnowObjtype.SALESPKG);
//            
////            paramList.add(BizConstant.PrdSalespkgKnowObjtype.PRD);
////            paramList.add(BizConstant.PrdSalespkgKnowObjtype.GOODS);
//        } else {
//            sql.append(" AND o.OBJTYPE IN(?,?,?)");
//            paramList.add(BizConstant.PrdSalespkgKnowObjtype.GOODS);
//            paramList.add(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE);
//            paramList.add(BizConstant.PrdSalespkgKnowObjtype.SALESPKG);
//        }
//        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
//            // 超级管理员查询全部地市的部门
//            sql.append(" AND o.city=?");
//            paramList.add(city);
//        }
//
//        if (StringUtils.isNotBlank(searchField)) {
//            
//            if(!isUpdatedCity){
//        		String searchValue = "%" + searchField + "%";
//        		 sql.append(" AND (o.knowname LIKE ?");
//                 sql.append(" OR ps.objcode LIKE ?");
//                 sql.append(" OR ps.objname LIKE ?)");
//                 paramList.add(searchValue);
//                 paramList.add(searchValue);
//                 paramList.add(searchValue);
//        	}else{
//        		String searchValue = "%" + searchField + "%";
//                sql.append(" AND (o.knowname LIKE ?");
//                sql.append(" OR ps.objcode LIKE ?");
//                sql.append(" OR ps.objname LIKE ?)");
//                paramList.add(searchValue);
//                paramList.add(searchValue);
//                paramList.add(searchValue);
//        	}
//        }
//        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
//            sql.append(" ORDER BY ");
//            if (!"objcode".equals(orderField) && !"objname".equals(orderField)) {
//                sql.append("o.");
//            } else {
//                sql.append("ps.");
//            }
//            sql.append(orderField).append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
//        } else {
//            if(!isUpdatedCity){
//	      		  sql.append(" ORDER BY o.city,ps.objcode) n");
//	      	}else{
//	      		  sql.append(" ORDER BY o.city,ps.objcode) n");
//	      	}
//        }
//
//        persistentService.clear();
//        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
//
//        List<SalespkgKnow> resultList = page.getResult();
//        if (null != page && null != resultList) {
//            int total = page.getTotalCount();
//            pageResult = new PageImpl<SalespkgKnow>(resultList, pageable, total);
//        } else {
//            pageResult = new PageImpl<SalespkgKnow>(new ArrayList<SalespkgKnow>(), pageable, 0);
//        }
//        return pageResult;
//    }
    
    
    @SuppressWarnings("unchecked")
    public PageImpl<SalespkgKnow> findPageKnowsByCity(String searchField, Pageable pageable, String orderField,
            String sortType) throws Exception {
    	
        PageImpl<SalespkgKnow> pageResult = null;
        Page<SalespkgKnow> page = new Page<SalespkgKnow>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        
        List<Object> paramList = new ArrayList<Object>();

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = loginInfo.getCity();
        boolean isUpdatedCity = checkIsUpdatedCity(city);

       
//        if (!isUpdatedCity) {
//			sql.insert(sql.lastIndexOf(" FROM"),",p.PCODE as objcode,p.PNAME as as objname ");
//			sql.append(SimpleSqlCreator.getEntityTableName(Pcode.class)).append(" p");
//			sql.append("where o.objid=p.PID and o.OBJTYPE in ('"+BizConstant.PrdSalespkgKnowObjtype.PRD+"')");
//		} else {
//			sql.insert(sql.lastIndexOf(" FROM"),", p.SALESCODE as objcode,p.SALESNAME as objname");
//			sql.append(",PRD_SALES p");
//			sql.append("where o.objid=p.salesid and o.OBJTYPE in ('"+BizConstant.PrdSalespkgKnowObjtype.GOODS+"','"+BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE+"')");
//        }
        
        StringBuffer sql=new StringBuffer();
        //查询产品
        sql.append(findBaseSQL().insert(findBaseSQL().lastIndexOf(" FROM"),",p.PCODE as objcode,p.PNAME as objname"));
		sql.append(",").append(SimpleSqlCreator.getEntityTableName(Pcode.class)).append(" p ");
		sql.append("where o.objid=p.PID and o.OBJTYPE in ('"+BizConstant.PrdSalespkgKnowObjtype.PRD+"')");
		
		sql.append(" UNION ALL ");
		//查询商品
		sql.append(findBaseSQL().insert(findBaseSQL().lastIndexOf(" FROM"),",p.SALESCODE as objcode,p.SALESNAME as objname"));
		sql.append(",PRD_SALES p ");
		sql.append("where o.objid=p.salesid and o.OBJTYPE in ('"+BizConstant.PrdSalespkgKnowObjtype.GOODS+"','"+BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE+"')");
    
		sql.append(" UNION ALL ");
		//查询营销库
		sql.append(findBaseSQL().insert(findBaseSQL().lastIndexOf(" FROM"),",p.SALESPKGCODE as objcode,p.SALESPKGNAME as objname"));
		sql.append(",").append(SimpleSqlCreator.getEntityTableName(Salespkg.class)).append(" p ");
		sql.append("where o.objid=p.SALESPKGID and o.OBJTYPE in ('"+BizConstant.PrdSalespkgKnowObjtype.SALESPKG+"')");
        
		
		StringBuffer finalSQLString=new StringBuffer();
		finalSQLString.append("select * from (");
		finalSQLString.append(sql);
		finalSQLString.append(") tb where 1=1 ");
		
        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
            // 超级管理员查询全部地市的部门
        	finalSQLString.append(" AND tb.city=?");
            paramList.add(city);
        }

        //查询字段
        if (StringUtils.isNotBlank(searchField)) { //值不为空
        	String searchValue = "%" + searchField + "%";
        	finalSQLString.append(" AND (tb.knowname LIKE ?");
        	finalSQLString.append(" OR tb.objcode LIKE ?");
        	finalSQLString.append(" OR tb.objname LIKE ?)");
            paramList.add(searchValue);
            paramList.add(searchValue);
            paramList.add(searchValue);
        }
        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
        	finalSQLString.append(" ORDER BY ");
        	finalSQLString.append(orderField).append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "");
        } else {
        	finalSQLString.append(" ORDER BY tb.city,tb.objcode");
        }

        persistentService.clear();
        page = persistentService.find(page, finalSQLString.toString(), entityClass, paramList.toArray());

        List<SalespkgKnow> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<SalespkgKnow>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<SalespkgKnow>(new ArrayList<SalespkgKnow>(), pageable, 0);
        }
        return pageResult;
    }

    private StringBuffer findBaseSQL() throws Exception{
    	StringBuffer baseSQL=SimpleSqlCreator.createSelectAllFieldSql(entityClass);
    	return baseSQL;
    }
    private StringBuffer getCommonQueKnowSql() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM(");
        sql.append(SimpleSqlCreator.createSelectAllFieldSql(entityClass));
        sql.append(" WHERE 1=1");
        return sql;
    }

    public Map<String, String> getObjTypeMap() throws Exception {
        Map<String, String> objTypeMap = new LinkedHashMap<String, String>();

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = loginInfo.getCity();

        if (!checkIsUpdatedCity(city)) {
            objTypeMap.put(BizConstant.PrdSalespkgKnowObjtype.PRD, "产品");
            objTypeMap.put(BizConstant.PrdSalespkgKnowObjtype.SALESPKG, "营销方案");
        } else {
            objTypeMap.put(BizConstant.PrdSalespkgKnowObjtype.GOODS, "商品");
            objTypeMap.put(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE, "商品小类");
            objTypeMap.put(BizConstant.PrdSalespkgKnowObjtype.SALESPKG, "营销方案");
        }
        return objTypeMap;
    }

    @SuppressWarnings("unchecked")
    private boolean checkIsUpdatedCity(String city) throws Exception {
        List<Rule> resultList = persistentService.find(
                SimpleSqlCreator.createSelectAllFieldSql(Rule.class).append(" WHERE o.RULE=? AND o.VALUE LIKE ?")
                        .toString(), Rule.class, new Object[] { "UPDATE_CITY", "%" + city + "%" });
        if (null == resultList || resultList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
