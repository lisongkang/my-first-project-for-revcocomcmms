package com.maywide.biz.salary.service;

import com.maywide.biz.ad.adset.entity.AdConfig;
import com.maywide.biz.salary.dao.ExplicationConfigDao;
import com.maywide.biz.salary.entity.ExplicationConfig;
import com.maywide.biz.salary.entity.OthersExchangeConfig;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
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
public class ExplicationConfigService extends BaseService<ExplicationConfig,Long>{


	@Autowired
	private ExplicationConfigDao explicationConfigDao;

	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseDao<ExplicationConfig, Long> getEntityDao() {
		return explicationConfigDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<ExplicationConfig> findByPage(ExplicationConfig explicationConfig,
			Pageable pageable) throws Exception {
		PageImpl<ExplicationConfig> pageResult = null;
        Page<ExplicationConfig> page = new Page<ExplicationConfig>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("select * from SALARY_EXPLICATION_CONFIG where 1=1");
        if(explicationConfig!=null){
			if(StringUtils.isNotEmpty(explicationConfig.getCity())){
				sql.append(" and city=?");
				paramList.add(explicationConfig.getCity());
			}
			if(StringUtils.isNotEmpty(explicationConfig.getAreaid())){
				sql.append(" and instr(CONCAT(',',areaid,','),CONCAT(',',?,','))>0");
				paramList.add(explicationConfig.getAreaid());
			}
			if(StringUtils.isNotEmpty(explicationConfig.getType())){
				sql.append(" and type=?");
				paramList.add(explicationConfig.getType());
			}
        }
        sql.append(" order by id desc");

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
		List<ExplicationConfig> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<ExplicationConfig>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<ExplicationConfig>(new ArrayList<ExplicationConfig>(), pageable, 0);
		}
        return pageResult;
	}

	public Boolean exists(String city,String areaid,String type) throws Exception {
		if(StringUtils.isEmpty(city) || StringUtils.isEmpty(areaid) ||
				StringUtils.isEmpty(type)){
			throw new Exception("参数不完整,无法校验!");
		}
		String sql="select 1 from SALARY_OTHERS_KPI_TEXT_CONFIG where city=? and areaid=? and type=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(city);
		paramList.add(areaid);
		paramList.add(type);
		Long count = persistentService.count(sql.toString(), paramList.toArray());
		if(count>0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 查询全部说明
	 * @return
	 * @throws Exception
	 */
	public JSONArray queryByAreaid(String city,String areaid,String dateMonth,String gridid) throws Exception{
		JSONArray result = new JSONArray();
		String sql="select * from salary_explication_config where stime<=now() and etime>=now() " +
				"and (city='*' or city=?) and (areaid='*' or areaid=?)";
		List<ExplicationConfig> configList = persistentService.find(sql,ExplicationConfig.class,city,areaid);
		if(configList!=null && configList.size()>0){
			JSONObject jb = null;
//			//查询奖励规则
//			String exchangeSql = "select type,user_num userNum,coefficient from salary_others_exchange_config where (city='*' or city=?) " +
//					"and (areaid='*' or areaid=?) and date_month=? and grid=? order by type";
//			List<OthersExchangeConfig> exchangeList = persistentService.find(exchangeSql,OthersExchangeConfig.class,
//					city,areaid,dateMonth,gridid);
//			String remark = "";
//			Integer mixUserNum = 0;
//			Double mixCoefficient = 0D;
//			if(exchangeList!=null && exchangeList.size()>=3){
//				remark=remark+"小于"+exchangeList.get(0).getUserNum()+"户,"+exchangeList.get(0).getCoefficient()+"元 \n";
//				remark=remark+exchangeList.get(0).getUserNum()+"户与"+exchangeList.get(1).getUserNum() +"之间"+exchangeList.get(1).getCoefficient()+"元 \n";
//				remark=remark+"大于"+exchangeList.get(2).getUserNum()+"户,"+exchangeList.get(2).getCoefficient()+"元 \n";
//			}

			for (ExplicationConfig explicationConfig : configList) {
				//提成奖励公式说明
//				if("TCJLGS".equals(explicationConfig.getType())){
//					explicationConfig.setRemark(remark);
//				}
				jb = new JSONObject();
				jb.put("type",explicationConfig.getType());
				JSONObject valueObj = new JSONObject();
				valueObj.put("context",explicationConfig.getContext());
				valueObj.put("remark",explicationConfig.getRemark());
				jb.put("value",valueObj);
				result.put(jb);
			}
		}
		return result;
	}

}
