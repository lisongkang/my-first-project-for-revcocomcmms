package com.maywide.biz.prd.web.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.market.entity.GridManager;
import com.maywide.biz.prd.entity.Catalog;
import com.maywide.biz.prd.entity.CatalogCondtion;
import com.maywide.biz.prd.entity.CatalogItem;
import com.maywide.biz.prd.service.CatalogCondtionService;
import com.maywide.biz.prd.service.CatalogItemService;
import com.maywide.biz.prd.service.CatalogService;
import com.maywide.biz.prd.service.SalespkgKnowService;
import com.maywide.biz.prv.entity.PrvArea;
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

@MetaData(value = "目录管理")
public class CatalogController extends BaseController<Catalog, Long> {
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private CatalogCondtionService catalogCondtionService;
	
	@Autowired
	private CatalogItemService catalogItemService;
	
	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private SalespkgKnowService salespkgKnowService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private RuleService ruleService;
	
	@Override
    protected BaseService<Catalog, Long> getEntityService() {
        return catalogService;
    }
	
	public HttpHeaders condtion() {
    	return buildDefaultHttpHeaders("condtion");
    }
	
	public HttpHeaders item() {
    	return buildDefaultHttpHeaders("item");
    }
	
    @Override
    @MetaData(value = "查询")
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
                // Page l = this.getEntityService().findByPage(groupFilter, pageable);
                
            	setModel( this.getEntityService().findByPage(groupFilter, pageable));
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

  /*  private Page<Catalog>  trainsientContent(Page<Catalog> page, Pageable pageable) throws Exception {
    	List<Catalog> content = page.getContent();
    	List<PrvSysparam> params = paramService.getData(BizConstant.SysparamGcode.BIZ_OPCODE);
    	List<Catalog> newContent =new ArrayList<Catalog>();
    	for(Catalog catalog:content){
    		if(catalog.getBizopcodes()!=null){
	    		String[] pcodes =catalog.getBizopcodes().split(",");
	    		String pcodeStr="";
	    		for(String pcode :pcodes){
	    			for(PrvSysparam param:params){
	    				if(pcode.equals(param.getMcode())){
	    					pcodeStr+=param.getMname();
	    					pcodeStr+=" ";
	        				break;
	    				}
	    			}
	    		}
	    		catalog.setOpcodesname(pcodeStr);
    		}
    		newContent.add(catalog);
    	}
    	
    	return new PageImpl<Catalog>(newContent,pageable, page.getSize());
	}*/

	@Override
    @MetaData(value = "删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData(value = "保存")
    public HttpHeaders doSave() {
    	catalogService.clear();
        return super.doSave();
    }
    
    
    @Override
    @MetaData("打开编辑表单")
    public HttpHeaders edit() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders("inputBasic");
    }
    
    public HttpHeaders findAllKnows() {
    	setModel(salespkgKnowService.findAllCached());
    	return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders selectAreaOptions() {
    	List<PrvSysparam> areaList;
		try {
			areaList = paramService.getData("PRV_CITY");
			setModel(areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new DefaultHttpHeaders();
    }
    
    public HttpHeaders selectConditionTypeOptions() {
    	List<PrvSysparam> areaList;
		try {
			areaList = paramService.getData("CATALOG_CONDITION_TYPE");
			setModel(areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	return new DefaultHttpHeaders();
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
    
    public HttpHeaders queryAllTown() {
    	List<PrvSysparam> areaList;
		try {
			areaList = paramService.getData("PRV_TOWN");
			setModel(areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new DefaultHttpHeaders();
    }
    
    public HttpHeaders getTown() {
    	Assert.notNull(getParameter("areaid"));
    	if (StringUtils.isEmpty(getParameter("areaid"))) {
    		return new DefaultHttpHeaders();
    	}
    	Long areaId = new Long(getParameter("areaid"));
    	List<PrvSysparam> areaList;
		try {
			areaList = paramService.getSubData("PRV_TOWN", areaId);
			setModel(areaList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return new DefaultHttpHeaders();
    }
    
    public Map<Long, String> getConditionMap() {
    	String hql = "FROM PrvSysparam WHERE gcode = ? ORDER BY id";
    	List<PrvSysparam> areaList;
		try {
			areaList = persistentService.find(hql, "CATALOG_CONDITION_TYPE");
			Map<Long, String> conditionMap = new HashMap<Long, String>();
	    	
	    	for (PrvSysparam param : areaList) {
	    		conditionMap.put(param.getId(), param.getDisplay());
	    	}
	    	
	    	return conditionMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    	
    }
    
    public Map<String,String> getBizopcodesMap(){
    	Map<String, String> bizOpcodesMap = new LinkedHashMap<String, String>();
    	try {
    	  bizOpcodesMap = catalogService.findBizopcodesMap();
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	return bizOpcodesMap;
    }
    
    public HttpHeaders findBizopcodesMap(){
    	Map<String, String> bizOpcodesMap = new LinkedHashMap<String, String>();
    	try {
    	  bizOpcodesMap = catalogService.findBizopcodesMap();
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	setModel(bizOpcodesMap);
    	return buildDefaultHttpHeaders();
    }
    public HttpHeaders queryCondtion() {
    	try {
    		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(GridManager.class, getRequest());
            PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "catalogid", bindingEntity.getId());
            groupFilter.append(propertyFilter);
            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
            	Page<CatalogCondtion> page = catalogCondtionService.findByPage(groupFilter, pageable);
            	catalogService.transCondtionList(page.getContent());
            	setModel(page);
            }
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
    	
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders queryItem() {
    	try {
    		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(GridManager.class, getRequest());
            PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "catalogid", bindingEntity.getId());
            groupFilter.append(propertyFilter);
            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
            	Page<CatalogItem> page = catalogItemService.findByPage(groupFilter, pageable);
            	catalogService.transKnowList(page.getContent());
            	setModel(page);
            }
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
    	
        return buildDefaultHttpHeaders();
    }
}
