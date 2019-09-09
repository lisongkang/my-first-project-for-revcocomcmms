package com.maywide.biz.salary.service;

import com.maywide.biz.salary.dao.OthersKpiTextConfigDao;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.biz.salary.entity.OthersKpiTextConfig;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *<p> 
 * 积分项目配置
 *<p>
 *
 */
@Service
@Transactional
public class OthersKpiTextConfigService extends BaseService<OthersKpiTextConfig,Long>{


	@Autowired
	private OthersKpiTextConfigDao othersKpiTextConfigDao;

	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseDao<OthersKpiTextConfig, Long> getEntityDao() {
		return othersKpiTextConfigDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<OthersKpiTextConfig> findByPage(OthersKpiTextConfig othersKpiTextConfig,
			Pageable pageable) throws Exception {
		PageImpl<OthersKpiTextConfig> pageResult = null;
        Page<OthersKpiTextConfig> page = new Page<OthersKpiTextConfig>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("select * from SALARY_OTHERS_KPI_TEXT_CONFIG where 1=1");
        if(othersKpiTextConfig!=null){
			if(StringUtils.isNotEmpty(othersKpiTextConfig.getCity())){
				sql.append(" and city=?");
				paramList.add(othersKpiTextConfig.getCity());
			}
			if(StringUtils.isNotEmpty(othersKpiTextConfig.getAreaid())){
				sql.append(" and instr(CONCAT(',',areaid,','),CONCAT(',',?,','))>0");
				paramList.add(othersKpiTextConfig.getAreaid());
			}
			if(StringUtils.isNotEmpty(othersKpiTextConfig.getType())){
				sql.append(" and type=?");
				paramList.add(othersKpiTextConfig.getType());
			}
			if(StringUtils.isNotEmpty(othersKpiTextConfig.getContext())){
				sql.append(" and context like '%?%'");
				paramList.add(othersKpiTextConfig.getContext());
			}
        }
        sql.append(" order by id desc");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
		List<OthersKpiTextConfig> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<OthersKpiTextConfig>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<OthersKpiTextConfig>(new ArrayList<OthersKpiTextConfig>(), pageable, 0);
		}
        return pageResult;
	}

	public Boolean exists(String city,String areaid,String type,String context) throws Exception {
		if(StringUtils.isEmpty(city) || StringUtils.isEmpty(areaid) ||
				StringUtils.isEmpty(type) || StringUtils.isEmpty(context)){
			throw new Exception("参数不完整,无法校验!");
		}
		String sql="select 1 from SALARY_OTHERS_KPI_TEXT_CONFIG where city=? and areaid=? and type=? and context=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(city);
		paramList.add(areaid);
		paramList.add(type);
		paramList.add(context);
		Long count = persistentService.count(sql.toString(), paramList.toArray());
		if(count>0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<OthersKpiTextConfig> findList(OthersKpiTextConfig config) throws Exception{
		String sql="select * from SALARY_OTHERS_KPI_TEXT_CONFIG where 1=1";
		List<Object> params = new ArrayList<Object>();
		if(config!=null){
			if(StringUtils.isNotEmpty(config.getCity())){
				sql+=" and city=?";
				params.add(config.getCity());
			}
			if(StringUtils.isNotEmpty(config.getAreaid())){
				sql+=" and (areaid=? or areaid='*')";
				params.add(config.getAreaid());
			}
			if(StringUtils.isNotEmpty(config.getType())){
				sql+=" and type=?";
				params.add(config.getType());
			}
			if(StringUtils.isNotEmpty(config.getContext())){
				sql+=" and context=?";
				params.add(config.getContext());
			}
		}
		List<OthersKpiTextConfig> configList = persistentService.find(sql,OthersKpiTextConfig.class,params.toArray());
		return configList;
	}
}
