package com.maywide.biz.salary.service;

import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.dao.OthersKpiAuditDao;
import com.maywide.biz.salary.dao.OthersKpiDao;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.biz.salary.entity.OthersKpiAudit;
import com.maywide.biz.salary.entity.OthersKpiTextConfig;
import com.maywide.biz.salary.pojo.OthersKpiAuditBO;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 
 *<p> 
 * 积分说明配置
 *<p>
 *
 */
@Service
@Transactional
public class OthersKpiAuditService extends BaseService<OthersKpiAudit,Long>{


	@Autowired
	private OthersKpiAuditDao othersKpiAuditDao;
	@Autowired
	private PersistentService persistentService;
	@Autowired
	private OthersKpiService othersKpiService;
	@Override
	protected BaseDao<OthersKpiAudit, Long> getEntityDao() {
		return othersKpiAuditDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<OthersKpiAuditBO> findByPage(OthersKpiAudit othersKpiAudit,
												 Pageable pageable) throws Exception {
		PageImpl<OthersKpiAuditBO> pageResult = null;
		Page<OthersKpiAuditBO> page = new Page<OthersKpiAuditBO>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("select max(t2.id) id,city,areaid,t1.grid,t2.operid,t2.date_month dateMonth,sum(score) scoreCount,");
		sql.append("t2.status,commit_user commitUser,max(t2.create_at) createDate");
		sql.append(" from salary_others_kpi t1,salary_others_kpi_audit t2 where t1.id=t2.others_kpi_id");
		if(othersKpiAudit!=null){
			if(othersKpiAudit.getAuditUser()!=null){
				sql.append(" and audit_user=?");
				paramList.add(othersKpiAudit.getAuditUser());
			}
			if(StringUtils.isNotEmpty(othersKpiAudit.getStatus())){
				sql.append(" and t2.status=?");
				paramList.add(othersKpiAudit.getStatus());
			}
			if(StringUtils.isNotEmpty(othersKpiAudit.getAreaid())){
				sql.append(" and t1.areaid=?");
				paramList.add(othersKpiAudit.getAreaid());
			}
			if(StringUtils.isNotEmpty(othersKpiAudit.getGrid())){
				sql.append(" and t1.grid=?");
				paramList.add(othersKpiAudit.getGrid());
			}
			if(othersKpiAudit.getOperid()!=null){
				sql.append(" and t2.operid=?");
				paramList.add(othersKpiAudit.getOperid());
			}
			if(StringUtils.isNotEmpty(othersKpiAudit.getDateMonth())){
				sql.append(" and t2.date_month=?");
				paramList.add(othersKpiAudit.getDateMonth());
			}
		}
		sql.append(" group by city,areaid,t1.grid,t2.operid,t2.date_month,t2.status,commit_user");

		persistentService.clear();
		page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
		List<OthersKpiAuditBO> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<OthersKpiAuditBO>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<OthersKpiAuditBO>(new ArrayList<OthersKpiAuditBO>(), pageable, 0);
		}
		return pageResult;
	}

	public List<PrvOperator> findAduitOperator() throws Exception {
		String sql="select operid as id,t1.name from prv_operator t1 where t1.operid in(" +
				"select DISTINCT o.operid from prv_roleprivs s,prv_operrole o where s.roleid=o.roleid " +
				" and s.menuid in(select menuid from prv_menudef where name='积分录入审核'))";
		return persistentService.find(sql,PrvOperator.class);
	}


	public void batchAudit(List<OthersKpi> entities,String auditUser) throws Exception{
		if(entities!=null && entities.size()>0){
			String sql = "select s.* from salary_others_kpi s where s.grid=? and s.date_month=? and s.operid=?";
			List<OthersKpiAudit> saves = new ArrayList<OthersKpiAudit>();
			OthersKpiAudit audit = null;
			persistentService.clear();
			for (OthersKpi othersKpi : entities) {
				List<OthersKpi> othersKpis= persistentService.find(sql, OthersKpi.class,
						othersKpi.getGrid(),othersKpi.getDateMonth(),othersKpi.getOperid());
				for (OthersKpi kpi : othersKpis) {
					if ("1".equals(othersKpi.getStatus()) || "2".equals(othersKpi.getStatus())) {
						throw new Exception("待审核，审核通过的状态无法再次提交审核!");
					} else {
						kpi.setStatus(SalaryConstants.OthersKpiAudit.AUDIT_STAUTS);
						audit = new OthersKpiAudit();
						audit.setOthersKpiId(kpi.getId());
						audit.setGrid(kpi.getGrid());
						audit.setDateMonth(kpi.getDateMonth());
						audit.setCommitUser(AuthContextHolder.getLoginInfo().getOperid());
						audit.setAuditUser(Long.valueOf(auditUser));
						audit.setOperid(kpi.getOperid());
						audit.setStatus(SalaryConstants.OthersKpiAudit.AUDIT_STAUTS);
						audit.setCreateAt(new Date());
						audit.setCreateBy(AuthContextHolder.getLoginInfo().getLoginname());
						audit.setUpdateAt(new Date());
						audit.setUpdateBy(AuthContextHolder.getLoginInfo().getLoginname());
						saves.add(audit);
						othersKpiService.save(kpi);
					}

				}

			}
			this.save(saves);
		}
	}

	public void audit(List<OthersKpiAudit> audits,String status) throws Exception{
	    if(audits.isEmpty() || StringUtils.isEmpty(status)){
	        throw new Exception("审批id和状态不能为空!");
        }
        List<OthersKpiAudit> updates = new ArrayList<OthersKpiAudit>();
	    OthersKpi kpi = null;
        for (OthersKpiAudit audit : audits) {
	        String sql="select * from salary_others_kpi_audit where status='1' and grid=? and date_month=? and operid=? and audit_user=?";
            persistentService.clear();
            List<OthersKpiAudit> list = persistentService.find(sql, entityClass,audit.getGrid(), audit.getDateMonth(),audit.getOperid(),
                    AuthContextHolder.getLoginInfo().getOperid());
            for (OthersKpiAudit othersKpiAudit : list) {
                othersKpiAudit.setStatus(status);
                updates.add(othersKpiAudit);
                kpi = new OthersKpi();
                kpi.setId(othersKpiAudit.getOthersKpiId());
                kpi.setStatus(status);
				persistentService.update(kpi,othersKpiAudit.getOthersKpiId(),"id");
            }
        }
        this.save(updates);

    }
}
