package com.maywide.biz.manage.smstemp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.manage.smstemp.dao.SysSmstempConfigDao;
import com.maywide.biz.manage.smstemp.entity.SysSmstempConfig;
import com.maywide.biz.manage.smstemp.pojo.SysSmstempConfigPo;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

/**
 * 
 *<p> 
 *  短信模板配置 服务类
 *<p>
 * Create at 2016-12-30
 *
 *@autor zhuangzhitang
 */
@Service
@Transactional
public class SysSmstempConfigService  extends BaseService<SysSmstempConfig,Long>{

	@Autowired
	private SysSmstempConfigDao sysSmstempConfigDao ;
	
	@Autowired
	private PersistentService persistentService ;
	
	@Override
	protected BaseDao<SysSmstempConfig, Long> getEntityDao() {

		return sysSmstempConfigDao;
	}

	public  PageImpl<SysSmstempConfig> findSmstempbyParams(SysSmstempConfigPo sysSmstempConfigPo,
			Pageable pageable) throws Exception {
		PageImpl<SysSmstempConfig> pageResult = null;
        Page<SysSmstempConfig> page = new Page<SysSmstempConfig>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append("SELECT * FROM (");
        sql.append(" SELECT t.TID as id,t.*,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid=t.`OPID` ) AS opidname ,");
        sql.append(" (SELECT d.name FROM prv_department d WHERE d.deptid = t.`OPDEPT`) AS opdeptname ,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid=t.`AUDITID` ) AS auditidname ,");
        sql.append(" (SELECT d.name FROM prv_department d WHERE d.deptid = t.`AUDITDEPT`) AS auditdeptname ");
        sql.append(" FROM sys_smstemp_config t where 1=1 ");
        
        if(sysSmstempConfigPo!= null){
        	
        	if(StringUtils.isNotEmpty(sysSmstempConfigPo.getCity())){
        		sql.append(" AND t.city = ?");
        		paramList.add(sysSmstempConfigPo.getCity());
        	}
        	if(StringUtils.isNotEmpty(sysSmstempConfigPo.getTname())){
        		sql.append(" AND t.tname like ?");
        		String tnameParam = "%"+sysSmstempConfigPo.getTname()+"%";
        		paramList.add(tnameParam);
        	}
        	
        	if(StringUtils.isNotEmpty(sysSmstempConfigPo.getTstatus())){
        		sql.append(" AND t.tstatus = ?");
        		paramList.add(sysSmstempConfigPo.getTstatus());
        	}
        	
        	
        }
        sql.append(" order by t.tid desc,t.tstatus");
        sql.append(" ) v");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<SysSmstempConfig> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<SysSmstempConfig>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<SysSmstempConfig>(new ArrayList<SysSmstempConfig>(), pageable, 0);
        }
        return pageResult;
	}

}
