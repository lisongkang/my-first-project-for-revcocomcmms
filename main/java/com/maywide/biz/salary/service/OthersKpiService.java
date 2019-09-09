package com.maywide.biz.salary.service;

import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.dao.OthersKpiDao;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.biz.salary.entity.OthersKpiAudit;
import com.maywide.biz.salary.entity.OthersKpiExcelTemp;
import com.maywide.biz.salary.entity.OthersKpiTextConfig;
import com.maywide.biz.salary.repbo.CentSumRep;
import com.maywide.biz.salary.reqbo.CentSumReq;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class OthersKpiService extends BaseService<OthersKpi,Long>{


	@Autowired
	private OthersKpiDao othersKpiDao;
	@Autowired
	private PersistentService persistentService;
	@Autowired
	private OthersKpiTextConfigService  othersKpiTextConfigService;
	@Autowired
	private OthersKpiAuditService othersKpiAuditService;
	@Autowired
	private BeforehandRealService beforehandRealService;
	@Override
	protected BaseDao<OthersKpi, Long> getEntityDao() {
		return othersKpiDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<OthersKpi> findByPage(OthersKpi othersKpi,
			Pageable pageable) throws Exception {
		PageImpl<OthersKpi> pageResult = null;
        Page<OthersKpi> page = new Page<OthersKpi>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("select max(id) id,city,areaid,grid,operid,date_month,sum(score) scoreCount,max(status) status from salary_others_kpi where 1=1");
        if(othersKpi!=null){
			if(StringUtils.isNotEmpty(othersKpi.getCity())){
				sql.append(" and city=?");
				paramList.add(othersKpi.getCity());
			}
			if(StringUtils.isNotEmpty(othersKpi.getAreaid())){
				sql.append(" and areaid=?");
				paramList.add(othersKpi.getAreaid());
			}
			if(StringUtils.isNotEmpty(othersKpi.getGrid())){
				sql.append(" and grid=?");
				paramList.add(othersKpi.getGrid());
			}
			if(othersKpi.getOperid()!=null){
				sql.append(" and operid=?");
				paramList.add(othersKpi.getOperid());
			}
			if(StringUtils.isNotEmpty(othersKpi.getDateMonth())){
				sql.append(" and date_month=?");
				paramList.add(othersKpi.getDateMonth());
			}
        }
        sql.append(" group by city,areaid,grid,operid,date_month");

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
		List<OthersKpi> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<OthersKpi>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<OthersKpi>(new ArrayList<OthersKpi>(), pageable, 0);
		}
        return pageResult;
	}

	public void save(List<OthersKpi> list,Long auditUser) throws Exception{
		//先删除掉网格人员月份下的所有数据在插入
		this.deleteByOperidAndDateMonth(list);
		//在进行保存
		List<OthersKpi> result = super.save(list);

		//是否需要提交审核
		String status = result.get(0).getStatus();
		if(SalaryConstants.OthersKpi.AUDIT_STAUTS.equals(status)){
			List<OthersKpiAudit> audits = new ArrayList<OthersKpiAudit>();
			OthersKpiAudit audit = null;
			for (OthersKpi othersKpi : result) {
				audit = new OthersKpiAudit();
				audit.setOthersKpiId(othersKpi.getId());
				audit.setDateMonth(othersKpi.getDateMonth());
				audit.setCommitUser(AuthContextHolder.getLoginInfo().getOperid());
				audit.setAuditUser(auditUser);
				audit.setGrid(othersKpi.getGrid());
				audit.setOperid(othersKpi.getOperid());
				audit.setStatus(SalaryConstants.OthersKpiAudit.AUDIT_STAUTS);
				audit.setCreateAt(new Date());
				audit.setCreateBy(AuthContextHolder.getLoginInfo().getLoginname());
				audit.setUpdateAt(new Date());
				audit.setUpdateBy(AuthContextHolder.getLoginInfo().getLoginname());
				audits.add(audit);
			}
			othersKpiAuditService.save(audits);
		}
	}

	public boolean exists(String grid,Long operid,String dateMonth,Long textConfigId) throws Exception{
		if(StringUtils.isEmpty(dateMonth) || operid==null || StringUtils.isEmpty(grid)){
			throw new Exception("参数不完整,无法校验!");
		}
		String sql="select 1 from salary_others_kpi where grid=? and operid=? and date_month=? ";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(grid);
		paramList.add(operid);
		paramList.add(dateMonth);
		if(textConfigId!=null){
			sql+=" and text_config_id=?";
			paramList.add(textConfigId);
		}

		Long count = persistentService.count(sql.toString(), paramList.toArray());
		if(count>0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public PageImpl<OthersKpi> findDetail(String grid,Long operid,String dateMonth,Pageable pageable) throws Exception {
		PageImpl<OthersKpi> pageResult = null;
		Page<OthersKpi> page = new Page<OthersKpi>();
		page.setPageNo(1);
		page.setPageSize(Integer.MAX_VALUE);
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select s.* from salary_others_kpi s where s.grid=? and s.date_month=? and s.operid=?";
		paramList.add(grid);
		paramList.add(dateMonth);
		paramList.add(operid);
		persistentService.clear();
		page = persistentService.find(page, sql, entityClass, paramList.toArray());
		List<OthersKpi> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<OthersKpi>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<OthersKpi>(new ArrayList<OthersKpi>(), pageable, 0);
		}
//		List<OthersKpi> resultList = persistentService.find(sql, entityClass, paramList.toArray());
		List<Long> configIds = new ArrayList<Long>();
		if(resultList!=null && resultList.size()>0) {
			for (OthersKpi othersKpi : resultList) {
				configIds.add(othersKpi.getTextConfigId());
			}
		}
		if(configIds.size()>0){
			List<OthersKpiTextConfig> list = othersKpiTextConfigService.findAll(
					configIds.toArray(new Long[configIds.size()]));
			for (OthersKpi othersKpi : resultList) {
				for (OthersKpiTextConfig othersKpiTextConfig : list) {
					if(othersKpi.getTextConfigId().longValue()==othersKpiTextConfig.getId()){
						othersKpi.setTextConfigId(othersKpiTextConfig.getId());
						othersKpi.setTextConfigType(othersKpiTextConfig.getType());
					}
				}
			}
		}

		return pageResult;
	}

	public void deleteByOperidAndDateMonth(List<OthersKpi> kpis) throws Exception{
		if(kpis!=null && kpis.size()>0){
			List<OthersKpi> deletes = new ArrayList<OthersKpi>();
			String sql = "select * from salary_others_kpi where grid=? and operid=? and date_month=?";
			for (OthersKpi kpi : kpis) {
				persistentService.clear();
				List<OthersKpi> kpiList = persistentService.find(sql,OthersKpi.class,kpi.getGrid(),kpi.getOperid(),kpi.getDateMonth());
				if(kpiList!=null && kpiList.size()>0) {
					for (OthersKpi othersKpi : kpiList) {
						if ("1".equals(othersKpi.getStatus()) || "2".equals(othersKpi.getStatus())) {
							throw new Exception("待审核，审核通过的状态无法删除!");
						} else {
							deletes.add(othersKpi);
						}
					}
				}
			}
			if(deletes.size()>0){
				super.delete(deletes);
			}
		}
	}

	/**
	 * 查询其他积分
	 * @param operid
	 * @param dateMonth
	 * @return
	 * @throws Exception
	 */
	public JSONArray othersKpi(String operid,String dateMonth) throws Exception{
		JSONArray othersArr = new JSONArray();
		if(StringUtils.isEmpty(operid) || StringUtils.isEmpty(dateMonth)){
			throw new Exception("网格人员id和月份不能为空!");
		}
		//查询其他积分的提成奖励
		String kpiSql = "select c.show_in_template showInTemplate,c.context textConfigName,k.score,k.remark from salary_others_kpi k," +
				"salary_others_kpi_text_config c where k.text_config_id = c.id and k.status='2' " +
				"and c.type='TCJL' and k.operid=? and k.date_month=? order by c.rank desc";
		List<OthersKpi> kpiList = persistentService.find(kpiSql,OthersKpi.class,operid,dateMonth);
		if(kpiList!=null && kpiList.size()>0){
			JSONObject jsonObject = null;
			for (OthersKpi othersKpi : kpiList) {
				jsonObject = new JSONObject();
				jsonObject.put("label",othersKpi.getTextConfigName());
				jsonObject.put("cents",othersKpi.getScore());
				othersArr.put(jsonObject);
			}
		}
		return othersArr;
	}

	/**
	 * 查询excel临时导入数据
	 * @param temp
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageImpl<OthersKpiExcelTemp> findExcelTempByPage(OthersKpiExcelTemp temp,
												   Pageable pageable) throws Exception {
		PageImpl<OthersKpiExcelTemp> pageResult = null;
		Page<OthersKpiExcelTemp> page = new Page<OthersKpiExcelTemp>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());

		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("select id,gridname,account,date_month dateMonth,config_type configType,");
		sql.append("config_context configContext,score,remark,error_message errorMessage,");
		sql.append("check_flag checkFlag from salary_others_kpi_excel_temp");
		sql.append(" where create_by=?");
		paramList.add(AuthContextHolder.getLoginInfo().getOperid());
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), OthersKpiExcelTemp.class, paramList.toArray());
		List<OthersKpiExcelTemp> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<OthersKpiExcelTemp>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<OthersKpiExcelTemp>(new ArrayList<OthersKpiExcelTemp>(), pageable, 0);
		}
		return pageResult;
	}

	public void uploadExcelTempData( List<OthersKpiExcelTemp> tempList) throws Exception{
		//先清空
		Long operid = AuthContextHolder.getLoginInfo().getOperid();
		persistentService.executeSql("delete from salary_others_kpi_excel_temp where create_by=?",operid);
		//在保存
		persistentService.batchSave(tempList);
	}

}
