package com.maywide.biz.sys.service;

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

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.sys.bo.CustVisitRuleBO;
import com.maywide.biz.sys.dao.SysCustVisitRuleDAO;
import com.maywide.biz.sys.entity.SysCustVisitRule;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;

@Service
public class SysCustVisitRuleService extends BaseService<SysCustVisitRule, Long> {

	@Autowired
	private SysCustVisitRuleDAO sysCustVisitRuleDao;
	
	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private ParamService paramService;
	
	@Override
	protected BaseDao<SysCustVisitRule, Long> getEntityDao() {
		return sysCustVisitRuleDao;
	}
	
	public PageImpl<CustVisitRuleBO> findPageByCity(Pageable pageable) throws Exception{
		PageImpl<CustVisitRuleBO> result = null;
		Page<CustVisitRuleBO> page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        List params = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append("		select * from (");
        sql.append("		SELECT a.rule_id id,");
        sql.append("		a.city city,");
        sql.append("		a.area area,");
        sql.append("		a.opcode opcode,");
        sql.append("		a.send_method sendMethod,");
        sql.append("		a.content_template_id contentTemplateId,");
        sql.append("		a.delay_type delayType,");
        sql.append("		a.delay_value delayValue,");
        sql.append("		a.mobile_index mobileIndex,");
        sql.append("		a.max_times maxTimes,");
        sql.append("		(select case when a.area = '*' then '全部' else (select t.`name` from prv_area t where t.areaid = a.area) end) areaName,");
        sql.append("		code2name(a.city,'PRV_CITY') cityName,");
        sql.append("		code2name(a.send_method,'BIZ_SEND_METHOD') sendMethodName,");
        sql.append("		code2name(a.opcode,'BIZ_OPCODE') opcodeName,");
        sql.append("		code2name(a.delay_type,'DELAY_TYPE') delayTypeName,");
        sql.append("		(select case when a.mobile_index = 0 then '第一个' else '最后一个' end) indexName");
        sql.append("		from sys_cust_visit_rule a");
        sql.append("		where 1 = 1");
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        if(loginInfo != null && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
        	sql.append("	and a.city = ?");
        	params.add(loginInfo.getCity());
        }
        sql.append("		) v");
        page = persistentService.find(page, sql.toString(), CustVisitRuleBO.class, params.toArray());
        List<CustVisitRuleBO> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            result = new PageImpl<CustVisitRuleBO>(resultList, pageable, total);
        } else {
        	result = new PageImpl<CustVisitRuleBO>(new ArrayList<CustVisitRuleBO>(), pageable, 0);
        }
		return result;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, String> getCityMap() throws Exception {
        String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
       
        List<PrvSysparam> areaList;
        Map<String, String> cityMap = new LinkedHashMap<String, String>();
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        if(loginInfo != null && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
        	PrvSysparam  cityParam = paramService.getData("PRV_CITY", loginInfo.getCity());
        	if(cityParam != null) {
        		cityMap.put(cityParam.getMcode(),cityParam.getMname());
        		return cityMap;
        	}
        }
        try {
            areaList = persistentService.find(hql, "PRV_CITY");
            Collections.sort(areaList, new Comparator() {
                public int compare(Object o1, Object o2) {
                    PrvSysparam param1 = (PrvSysparam) o1;
                    PrvSysparam param2 = (PrvSysparam) o2;

                    String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getMname());
                    String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getMname());

                    int rtnval = pinyin1.compareTo(pinyin2);

                    return rtnval;
                }
            });
            for (PrvSysparam param : areaList) {
            	cityMap.put(param.getMcode(), param.getDisplay());
            }

            return cityMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityMap;
    }
	
	public Map<String,String> getAreaMap(String city) throws Exception{
		Map<String,String> areaMap = new LinkedHashMap<String,String>();
		areaMap.put("*","所有");
		if(StringUtils.isBlank(city)) {
			return areaMap;
		}
		String sql = "select a.areaid,a.name from prv_area a where a.city = ? order by areaid";
		List<PrvArea> areaList = persistentService.find(sql, PrvArea.class, city);
		 for (PrvArea area : areaList) {
			 areaMap.put(area.getId().toString(), area.getName());
         }

		return areaMap;
	}
	
	public Map<String,String> getOpcodeMap(){
		String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
		Map<String, String> opcodeMap = new LinkedHashMap<String, String>();
		List<PrvSysparam> areaList;
		try {
			areaList = persistentService.find(hql, "BIZ_OPCODE");
			for (PrvSysparam param : areaList) {
				opcodeMap.put(param.getMcode(), param.getDisplay());
            }
			return opcodeMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return opcodeMap;
	}
	
	public Map<String,String> getSendMethodMap(){
		Map<String,String> sendMethodMap = new LinkedHashMap<String,String>();
		String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
		List<PrvSysparam> areaList;
		try {
			areaList = persistentService.find(hql, "BIZ_SEND_METHOD");
			for (PrvSysparam param : areaList) {
				sendMethodMap.put(param.getMcode(), param.getDisplay());
            }
			return sendMethodMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendMethodMap;
	}
	
	public Map<String,String> getTypeMap(){
		Map<String,String> typeMap = new LinkedHashMap<String,String>();
		String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
		List<PrvSysparam> areaList;
		try {
			areaList = persistentService.find(hql, "DELAY_TYPE");
			for (PrvSysparam param : areaList) {
				typeMap.put(param.getMcode(), param.getDisplay());
            }
			return typeMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return typeMap;
	}
	
	public Map<String,String> getIndexMap(){
		Map<String,String> indexMap = new LinkedHashMap<String,String>();
		indexMap.put("0","第一个");
		indexMap.put("1","最后一个");
		return indexMap;
	}

}
