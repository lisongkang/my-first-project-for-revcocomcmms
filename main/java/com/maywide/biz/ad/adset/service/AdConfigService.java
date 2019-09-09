package com.maywide.biz.ad.adset.service;

import java.text.ParseException;
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

import com.maywide.biz.ad.adset.dao.AdConfigDao;
import com.maywide.biz.ad.adset.entity.AdConfig;
import com.maywide.biz.ad.adset.entity.AdConfigHis;
import com.maywide.biz.ad.adset.pojo.AdSearchParamsBo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

/**
 * 
 *<p> 
 * AdConfig 操作服务类
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
@Service
@Transactional
public class AdConfigService extends BaseService<AdConfig,Long>{


	@Autowired
	private AdConfigDao  adConfigDao;
	
	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseDao<AdConfig, Long> getEntityDao() {
		return adConfigDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<AdConfig> findByPage(AdSearchParamsBo adSearchParamsBo,
			Pageable pageable) throws Exception {

		PageImpl<AdConfig> pageResult = null;
        Page<AdConfig> page = new Page<AdConfig>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT * FROM (");
        sql.append("SELECT  a.`ADID` AS id,a.*,");
        sql.append(" ( SELECT k.KNOWNAME FROM PRD_SALESPKG_KNOW k WHERE k.KNOWID = a.`ADOBJ` AND a.`ADTYPE`='1') AS knowname,");
        sql.append(" ( SELECT o.name FROM prv_operator o WHERE  o.operid = a.`OPTID` )  AS optname,");
        sql.append(" ( SELECT o.name FROM prv_operator o WHERE  o.operid = a.AUDITID) AS auditname");
        sql.append(" FROM ad_config a where 1=1");
        
        if(adSearchParamsBo!= null){
        	
        	if(StringUtils.isNotEmpty(adSearchParamsBo.getCity())){
        		sql.append(" AND a.city in('*',?)");
        		paramList.add(adSearchParamsBo.getCity());
        	}else{
        		sql.append(" AND a.city ='*'");
        	}
        	
        	if(StringUtils.isNotEmpty(adSearchParamsBo.getAdstatus())){
        		sql.append(" AND a.adstatus = ?");
        		paramList.add(adSearchParamsBo.getAdstatus());
        	}else{
        		/**如果是评审的限制 //初始化广告类型//展示【待审核/审核通过/已上架】的广告**/
        		//1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
            	if(StringUtils.isNotBlank(adSearchParamsBo.getType())&&adSearchParamsBo.getType().equals("1")){
            		 sql.append(" AND a.adstatus in('2','3','5')");
            	}
        	}
        	
        	if(StringUtils.isNotEmpty(adSearchParamsBo.getAdtype())){
        		sql.append(" AND a.adtype = ?");
        		paramList.add(adSearchParamsBo.getAdtype());
        	}
        	
        	if(StringUtils.isNotBlank(adSearchParamsBo.getOptimeRange())){
   			 List<String> result = paraseTimeRang(adSearchParamsBo.getOptimeRange());
   	    	 sql.append(" AND a.opttime >=?");
   			 paramList.add(result.get(0));
   			 sql.append(" AND a.opttime <?");
   			 paramList.add(result.get(1));
   		   }
        	
        	if(StringUtils.isNotBlank(adSearchParamsBo.getAuditTimeRang())){
      			 // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
      	         // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
      			 List<String> result0 = paraseTimeRang(adSearchParamsBo.getAuditTimeRang());
      	    	 sql.append(" AND a.audittime >=?");
      			 paramList.add(result0.get(0));
      			 sql.append(" AND a.audittime <?");
      			 paramList.add(result0.get(1));
        	}
        	
        
        }
        sql.append(" order by a.`ADID` desc,a.adstatus");
        sql.append(" ) v");
        
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<AdConfig> resultList = page.getResult();
       
        if (null != page && null != resultList) {
        	 List<AdConfig> targetList = new ArrayList<AdConfig>();
        	 //将 商品名赋值给广告对象，页面展示使用
         	 for(AdConfig adConfig : resultList){
   		      if(adConfig.getAdtype().toString().equals(BizConstant.AD_TYPE.AD_TYPE_1)) {
   		    	  adConfig.setAdobj(adConfig.getKnowname());
   		      }
   		      targetList.add(adConfig);
   	         }
            int total = page.getTotalCount();
            pageResult = new PageImpl<AdConfig>(targetList, pageable, total);
        } else {
        	
            pageResult = new PageImpl<AdConfig>(new ArrayList<AdConfig>(), pageable, 0);
        }
        return pageResult;
	}

	/**
	 * 解析时间段
	 * @param timeRange
	 * @return
	 * @throws ParseException
	 */
	private List<String> paraseTimeRang(String timeRange) throws ParseException{
		 List<String> result = new ArrayList<String>();
		 String[] fromAndToTime = timeRange.split("～");
		 String etime = fromAndToTime[1].trim();
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	 Date matchValue = sdf.parse(etime);
    	 DateTime dateTime = new DateTime(((Date) matchValue).getTime());
    	 Date date = dateTime.plusDays(1).toDate();
    	 etime = sdf.format(date);
    	 result.add(fromAndToTime[0].trim());
    	 result.add(etime);
    	 return result;
	}
	public void registerAdConfigHis(AdConfig bindingEntity) throws Exception {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		AdConfigHis adConfigHis = new AdConfigHis();
		adConfigHis.setAdid(bindingEntity.getId());
		adConfigHis.setAdname(bindingEntity.getAdname());
		adConfigHis.setAdobj(bindingEntity.getAdobj());
		adConfigHis.setAdsite(bindingEntity.getAdsite());
		adConfigHis.setAdstatus(bindingEntity.getAdstatus());
		adConfigHis.setAdtype(bindingEntity.getAdtype());
		adConfigHis.setAuditid(bindingEntity.getAuditid());
		adConfigHis.setAudittime(bindingEntity.getAudittime());
		adConfigHis.setCity(bindingEntity.getCity());
		adConfigHis.setMemo(bindingEntity.getMemo());
		adConfigHis.setOpertime(new Date());
		adConfigHis.setOpertor(loginInfo.getOperid());
		adConfigHis.setOpertype(bindingEntity.getOpertype());
		adConfigHis.setOptid(bindingEntity.getOptid());
		adConfigHis.setOpttime(bindingEntity.getOpttime());
		adConfigHis.setPri(bindingEntity.getPri());
		
		persistentService.save(adConfigHis);
	}

}
