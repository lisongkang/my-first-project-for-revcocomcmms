package com.maywide.biz.salary.service;

import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.dao.OthersExchangeConfigDao;
import com.maywide.biz.salary.entity.OthersExchangeConfig;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.applet.Main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *<p> 
 * 积分奖励兑换规则
 *<p>
 *
 */
@Service
@Transactional
public class OthersExchangeConfigService extends BaseService<OthersExchangeConfig,Long>{


	@Autowired
	private OthersExchangeConfigDao othersExchangeConfigDao;

	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseDao<OthersExchangeConfig, Long> getEntityDao() {
		return othersExchangeConfigDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<OthersExchangeConfig> findByPage(OthersExchangeConfig othersExchangeConfig,
			Pageable pageable) throws Exception {
		PageImpl<OthersExchangeConfig> pageResult = null;
        Page<OthersExchangeConfig> page = new Page<OthersExchangeConfig>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("select * from salary_others_exchange_config where status='0'");
        if(othersExchangeConfig!=null){
			if(StringUtils.isNotEmpty(othersExchangeConfig.getCity())){
				sql.append(" and city=?");
				paramList.add(othersExchangeConfig.getCity());
			}
			if(StringUtils.isNotEmpty(othersExchangeConfig.getAreaid())){
				sql.append(" and areaid=?");
				paramList.add(othersExchangeConfig.getAreaid());
			}
			if(StringUtils.isNotEmpty(othersExchangeConfig.getGrid())){
				sql.append(" and grid=?");
				paramList.add(othersExchangeConfig.getGrid());
			}
        }
        sql.append(" order by id desc");

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
		List<OthersExchangeConfig> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<OthersExchangeConfig>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<OthersExchangeConfig>(new ArrayList<OthersExchangeConfig>(), pageable, 0);
		}
        return pageResult;
	}


	public void doSave(OthersExchangeConfig othersExchangeConfig) throws Exception{
		Double scontrol = othersExchangeConfig.getScontrol();
		Double econtrol = othersExchangeConfig.getEcontrol();
		if(scontrol!=null && econtrol!=null){
			othersExchangeConfig.setScontrolStatus(SalaryConstants.OthersExchangeConfig.CONTROL_STATUS_OPEN);
			othersExchangeConfig.setEcontrolStatus(SalaryConstants.OthersExchangeConfig.CONTROL_STATUS_OPEN);
			scontrol = new BigDecimal(scontrol.toString()).divide(new BigDecimal(100)).doubleValue();
			econtrol = new BigDecimal(econtrol.toString()).divide(new BigDecimal(100)).doubleValue();
			othersExchangeConfig.setScontrol(scontrol);
			othersExchangeConfig.setEcontrol(econtrol);
		}else if(scontrol!=null){
			scontrol = new BigDecimal(scontrol.toString()).divide(new BigDecimal(100)).doubleValue();
			othersExchangeConfig.setScontrol(scontrol);
			othersExchangeConfig.setScontrolStatus(SalaryConstants.OthersExchangeConfig.CONTROL_STATUS_CLOSE);
			othersExchangeConfig.setEcontrolStatus(SalaryConstants.OthersExchangeConfig.CONTROL_STATUS_NO);
		}else if(econtrol!=null){
			econtrol = new BigDecimal(econtrol.toString()).divide(new BigDecimal(100)).doubleValue();
			othersExchangeConfig.setEcontrol(econtrol);
			othersExchangeConfig.setScontrolStatus(SalaryConstants.OthersExchangeConfig.CONTROL_STATUS_NO);
			othersExchangeConfig.setEcontrolStatus(SalaryConstants.OthersExchangeConfig.CONTROL_STATUS_CLOSE);
		}
		Double minCentsPrice = othersExchangeConfig.getMinCentsPrice();
		Double maxCentsPrice = othersExchangeConfig.getMaxCentsPrice();
		minCentsPrice = new BigDecimal(minCentsPrice.toString()).divide(new BigDecimal(100)).doubleValue();
		maxCentsPrice = new BigDecimal(maxCentsPrice.toString()).divide(new BigDecimal(100)).doubleValue();
		othersExchangeConfig.setMinCentsPrice(minCentsPrice);
		othersExchangeConfig.setMaxCentsPrice(maxCentsPrice);
		super.save(othersExchangeConfig);
		//同步到billing
		saveBilling(othersExchangeConfig);

	}

	public void saveBilling(OthersExchangeConfig othersExchangeConfig) throws Exception {
		//先删除后保存
		deleteBilling(othersExchangeConfig.getId());
		List<Object> params = new ArrayList<Object>();
		String insert = "insert into nods.salary_others_exchange_config (id,areaid, city, create_at, create_by, econtrol, econtrol_status, edate_month, formula_type, " +
			"max_cents_price, min_cents_price, scontrol, scontrol_status, sdate_month, update_at, update_by,status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		params.add(othersExchangeConfig.getId());
		params.add(othersExchangeConfig.getAreaid());
		params.add(othersExchangeConfig.getCity());
		params.add(othersExchangeConfig.getCreateAt());
		params.add(othersExchangeConfig.getCreateBy());
		params.add(othersExchangeConfig.getEcontrol()==null?"":othersExchangeConfig.getEcontrol());
		params.add(othersExchangeConfig.getEcontrolStatus());
		params.add(othersExchangeConfig.getEdateMonth());
		params.add(othersExchangeConfig.getFormulaType());
		params.add(othersExchangeConfig.getMaxCentsPrice());
		params.add(othersExchangeConfig.getMinCentsPrice());
		params.add(othersExchangeConfig.getScontrol()==null?"":othersExchangeConfig.getScontrol());
		params.add(othersExchangeConfig.getScontrolStatus());
		params.add(othersExchangeConfig.getSdateMonth());
		params.add(othersExchangeConfig.getUpdateAt());
		params.add(othersExchangeConfig.getUpdateBy());
		params.add(othersExchangeConfig.getStatus());
		SpringBeanUtil.getBillingPersistentService().executeSql(insert,params.toArray());
	}

	public void deleteBilling(Long id) throws Exception{
		String delete="delete from  nods.salary_others_exchange_config where id=?";
		SpringBeanUtil.getBillingPersistentService().executeSql(delete,id);
	}

	public List<OthersExchangeConfig> findList(OthersExchangeConfig config) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("select * from SALARY_EXPLICATION_CONFIG where 1=1");
		if(config!=null){
			if(StringUtils.isNotEmpty(config.getCity())){
				sql.append(" and city=?");
				paramList.add(config.getCity());
				if(StringUtils.isNotEmpty(config.getAreaid())){
					sql.append(" and instr(CONCAT(',',areaid,','),CONCAT(',',?,','))>0");
					paramList.add(config.getAreaid());
					if(StringUtils.isNotEmpty(config.getGrid())){
						sql.append(" and grid=?");
						paramList.add(config.getGrid());
					}
				}
			}
		}
		return persistentService.find(sql.toString(),OthersExchangeConfig.class,paramList);
	}


	public void doDelete(OthersExchangeConfig entity) throws Exception{
		long id = entity.getId();
		super.delete(entity);
		//同步删除billing
		deleteBilling(id);

	}

}
