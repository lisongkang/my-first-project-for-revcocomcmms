package com.maywide.biz.manage.problem.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.manage.problem.dao.SysProblemDao;
import com.maywide.biz.manage.problem.entity.SysProblem;
import com.maywide.biz.manage.problem.pojo.SysProblemPo;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class SysProblemService extends BaseService<SysProblem,Long>{

	@Autowired
	private  SysProblemDao sysProblemDao;
	
	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseDao<SysProblem, Long> getEntityDao() {
		return sysProblemDao;
	}

	public PageImpl<SysProblem> findbyParams(SysProblemPo sysProblemPo,Pageable pageable) throws Exception {
		PageImpl<SysProblem> pageResult = null;
        Page<SysProblem> page = new Page<SysProblem>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append("SELECT * FROM (");
        sql.append(" SELECT t.PLID as id,t.*,");
        sql.append(" (SELECT a.name FROM prv_area a WHERE a.areaid = t.areaid ) AS areaname,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid=t.`SUBOPERID` ) AS subopername ,");
        sql.append(" (SELECT d.name FROM prv_department d WHERE d.deptid = t.`SUBOPERDEPT`) AS suboperdeptname ,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid=t.`DEALOPERID` ) AS dealopername ,");
        sql.append(" (SELECT d.name FROM prv_department d WHERE d.deptid = t.`DEALOPERDEPT`) AS dealoperdeptname ");
        sql.append(" FROM sys_problem t where 1=1 ");
        
        if(sysProblemPo!= null){
        	
        	if(StringUtils.isNotEmpty(sysProblemPo.getCity())){
        		sql.append(" AND t.city = ?");
        		paramList.add(sysProblemPo.getCity());
        	}
        	
        	if(StringUtils.isNotEmpty(sysProblemPo.getArea())){
        		sql.append(" AND t.areaid = ?");
        		paramList.add(sysProblemPo.getArea());
        	}
        	
        	if(StringUtils.isNotEmpty(sysProblemPo.getPltype())){
        		sql.append(" AND t.pltype = ?");
        		paramList.add(sysProblemPo.getPltype());
        	}
        	
        	if(StringUtils.isNotEmpty(sysProblemPo.getPlstate())){
        		sql.append(" AND t.plstate = ?");
        		paramList.add(sysProblemPo.getPlstate());
        	}
        	
        	if(StringUtils.isNotEmpty(sysProblemPo.getSubtime())){
        		 // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
   	         // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
   	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
   	    	 Date matchValue = sdf.parse(sysProblemPo.getSubtime());
   	    	 DateTime dateTime = new DateTime(((Date) matchValue).getTime());
   	    	 Date date = dateTime.plusDays(1).toDate();
   	    	 String etime = sdf.format(date);
   			 sql.append(" AND t.subtime >=?");
   			 paramList.add(sysProblemPo.getSubtime());
   			 sql.append(" AND t.subtime <?");
   			 paramList.add(etime);
        	}
        }
        sql.append(" ) v");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<SysProblem> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<SysProblem>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<SysProblem>(new ArrayList<SysProblem>(), pageable, 0);
        }
        return pageResult;
	}

	public Object findSysProblemById(Long id) throws Exception {
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append("SELECT * FROM (");
        sql.append(" SELECT t.PLID as id,t.*,");
        sql.append(" (SELECT a.name FROM prv_area a WHERE a.areaid = t.areaid ) AS areaname,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid=t.`SUBOPERID` ) AS subopername ,");
        sql.append(" (SELECT d.name FROM prv_department d WHERE d.deptid = t.`SUBOPERDEPT`) AS suboperdeptname ,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid=t.`DEALOPERID` ) AS dealopername ,");
        sql.append(" (SELECT d.name FROM prv_department d WHERE d.deptid = t.`DEALOPERDEPT`) AS dealoperdeptname ");
        sql.append(" FROM sys_problem t where 1=1 ");
	    
        sql.append(" AND t.PLID=?");
        
        sql.append(") v");
        paramList.add(id);
        
        List<SysProblem> list= persistentService.find(sql.toString(), entityClass, paramList.toArray());
		return list.get(0);
	}


}
