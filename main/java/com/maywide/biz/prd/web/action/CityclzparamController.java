package com.maywide.biz.prd.web.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.entity.CityClzParam;
import com.maywide.biz.prd.service.CityClzParamService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;

public class CityclzparamController extends BaseController<CityClzParam, Long> {

	@Autowired
	private CityClzParamService cityClzParamService;
	
	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseService<CityClzParam, Long> getEntityService() {
		return cityClzParamService;
	}
	
	@Override
    @MetaData("创建")
    public HttpHeaders doCreate() {
        return super.doCreate();
    }

    @Override
    @MetaData("更新")
    public HttpHeaders doUpdate() {
        return super.doUpdate();
    }
    
    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());

            LoginInfo logininfo = AuthContextHolder.getLoginInfo();
            String city = logininfo.getCity();
            if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(logininfo.getLoginname())) {
                // 超级管理员查询全部地市的记录
                groupFilter.append(new PropertyFilter(MatchType.EQ, "city", city));
            }

            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
            	Page<CityClzParam> page = this.getEntityService().findByPage(groupFilter, pageable);
            	cityClzParamService.transGridList(page.getContent());
            	setModel(page);
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
        return super.doSave();
    }

    public HttpHeaders edit(){
    	return buildDefaultHttpHeaders("inputBasic");
    }
    
    public Map<String, String> getAreaMap() {
    	String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
    	List<PrvSysparam> areaList;
    	Map<String, String> areaMap = new LinkedHashMap<String, String>();
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
	    		areaMap.put(param.getMcode(), param.getDisplay());
	    	}
	    	
	    	return areaMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaMap;
    	
    }
    
    public Map<String, String> getObjtype(){
    	Map<String, String> objtype = new LinkedHashMap<String, String>();
    	String hql = "FROM PrvSysparam WHERE gcode = ? ORDER BY mcode";
    	List<PrvSysparam> resList;
    	
    	try {
			resList = persistentService.find(hql,"RES_KIND");
			for (PrvSysparam param : resList) {
				objtype.put(param.getMcode(), param.getMname());
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return objtype;
    }
    
    public Map<String, String> getTownMap() {
    	Map<String, String> townMap = new LinkedHashMap<String, String>();
    	String hql = "FROM PrvArea WHERE city = ? ORDER BY id";
    	List<PrvArea> areaList;
		try {
			areaList = persistentService.find(hql, bindingEntity.getCity());
			
			Collections.sort(areaList, new Comparator() {
				public int compare(Object o1, Object o2) {
					PrvArea param1 = (PrvArea) o1;
					PrvArea param2 = (PrvArea) o2;
					
					String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getName());
					String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getName());
					
					int rtnval = pinyin1.compareTo(pinyin2);
					
					return rtnval;
				}
			});
	    	
	    	for (PrvArea param : areaList) {
	    		townMap.put(param.getId().toString(), param.getName());
	    	}
	    	
	    	return townMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return townMap;
    	
    }
    
}
