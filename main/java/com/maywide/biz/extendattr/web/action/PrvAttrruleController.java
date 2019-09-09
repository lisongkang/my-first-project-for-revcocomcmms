package com.maywide.biz.extendattr.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.google.common.collect.Maps;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.extendattr.entity.PrvAttrrule;
import com.maywide.biz.extendattr.pojo.PrvAttrEditPageInitDataBO;
import com.maywide.biz.extendattr.pojo.PrvAttrruleBo;
import com.maywide.biz.extendattr.pojo.QuePrvAttrruleParams;
import com.maywide.biz.extendattr.service.PrvAttrruleService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.extendattr.entity.PrvAttrrule管理")
public class PrvAttrruleController extends BaseController<PrvAttrrule,Long> {

    @Autowired
    private PrvAttrruleService prvAttrruleService;
    
    @Autowired
    private PersistentService persistentService;

    @Autowired
    private ParamService paramService;
    
    private  QuePrvAttrruleParams quePrvAttrruleParams;
    
    public HttpHeaders inputTabs() {
    	
        return buildDefaultHttpHeaders("inputTabs");
    }

    
    @Override
    protected BaseService<PrvAttrrule, Long> getEntityService() {
        return prvAttrruleService;
    }
    
    @Override
    protected void checkEntityAclPermission(PrvAttrrule entity) {
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        //TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
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
    @MetaData("保存")
    public HttpHeaders doSave() {
    	// json串转换assIndexStore对象
    	try{
    		String prvAttrruleBoJson = getParameter("prvAttrruleBo");
    		
    		PrvAttrruleBo prvAttrruleBo = (PrvAttrruleBo) BeanUtil
    				.jsonToObject(prvAttrruleBoJson, PrvAttrruleBo.class);
    		
            prvAttrruleBo.setCityList(resetCity(prvAttrruleBo));
            
            if(prvAttrruleBo.getId()!=null&&!prvAttrruleBo.getId().trim().equals("")){ //编辑
            	//persistentService.evit(bindingEntity);
            	if(prvAttrruleService.isOktoDelorUpdate(prvAttrruleBo.getId())==1)//不允许修改
            	{
            		setModel(OperationResult.buildFailureResult("该规则已经被使用了，不可以修改"));
           		    return buildDefaultHttpHeaders();
            	}else{
            		
            		 PrvAttrrule prvAttrrule = prvAttrruleService.getAttrrule(prvAttrruleBo.getId());
					 //修改时如果之前的不为空而现在修改为为空 应该删除sys_prv
            		
					 if( prvAttrrule.getValueparam()!=null && ! prvAttrrule.getValueparam().equals("")&&(prvAttrruleBo.getValueparam()==null||prvAttrruleBo.getValueparam().equals(""))){
						 prvAttrruleService.deleteDateByGcode( prvAttrrule.getValueparam());
					 }
					 //如果之前的为空而现在修改为不为空
					 if(( prvAttrrule.getValueparam()==null || prvAttrrule.getValueparam().equals(""))&&(prvAttrruleBo.getValueparam()!=null &&!prvAttrruleBo.getValueparam().equals(""))){
						 if(prvAttrruleService.isExistGcodeInprvSystem(prvAttrruleBo.getValueparam())){
	            			 setModel(OperationResult.buildFailureResult("对应参数："+prvAttrruleBo.getValueparam()+"已经在系统规则表中存在"));
	                		 return buildDefaultHttpHeaders();
	            		 }else{
	            			 //插入PRV_system表
	            			 updateOrsavePrvSystem(prvAttrruleBo);
	            			 
	            			 //删除拓展属性区域表对应的数据
	             			 prvAttrruleService.deleteSysAttrAreaByattId(prvAttrruleBo.getId());
	            			 prvAttrruleService.doSave(prvAttrruleBo);
	            			 
	            			 return buildDefaultHttpHeaders();
	            		 }
					}
            		if(prvAttrruleBo.getValuesrc()!=null&&(prvAttrruleBo.getValuesrc().equals("1")||prvAttrruleBo.getValuesrc().equals("2"))){
                		//删除系统配置表的对应的Gcode
            			prvAttrruleService.deleteDateByGcode(prvAttrruleBo.getValueparam());
            			//添加数据到PRV_System表
            			updateOrsavePrvSystem(prvAttrruleBo);
            			
                	}
            		
            		//删除拓展属性区域表对应的数据
        			prvAttrruleService.deleteSysAttrAreaByattId(prvAttrruleBo.getId());
        			prvAttrruleService.doSave(prvAttrruleBo);
            	}
            }else{//新增
            	  
            	//判断 prvAttrrule表中是否存在属性标识
            	 if(prvAttrruleService.isExistAttrCode(prvAttrruleBo.getAttrcode())){
            		 setModel(OperationResult.buildFailureResult("属性标识："+prvAttrruleBo.getAttrcode()+"已经在扩展属性规则表中存在"));
            		 return buildDefaultHttpHeaders();
            	 }
            	 
            	 if(prvAttrruleBo.getValuesrc().equals("1")||prvAttrruleBo.getValuesrc().equals("2")){
            		 if(prvAttrruleService.isExistGcodeInprvSystem(prvAttrruleBo.getValueparam())){
            			 setModel(OperationResult.buildFailureResult("对应参数："+prvAttrruleBo.getValueparam()+"已经在系统规则表中存在"));
                		 return buildDefaultHttpHeaders();
            		 }else{
            			 //插入PRV_system表
            			 updateOrsavePrvSystem(prvAttrruleBo);
            			 
            			 prvAttrruleService.doSave(prvAttrruleBo);
            		 }
            	 }else{
            		 prvAttrruleService.doSave(prvAttrruleBo);
            		
            	 }
            }
        	
    	}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
    	 return buildDefaultHttpHeaders();
    }
    
    private void updateOrsavePrvSystem(PrvAttrruleBo prvAttrruleBo) throws Exception{
    	//数据
		 String[] mnametemp =  prvAttrruleBo.getMnames().split(",");
		 List<String> mnameList =Arrays.asList(mnametemp);
		 prvAttrruleService.addDatetoPrvSystem(prvAttrruleBo.getValueparam(), mnameList);
    }
   
    private  String combineCitytoString(List<PrvSysparam> prvSysparams){
    	StringBuffer buffer = new StringBuffer();
    	for (int i = 0; i < prvSysparams.size(); i++) {
			buffer.append(prvSysparams.get(i).getMcode());
			if(i!=prvSysparams.size()-1){
				buffer.append(",");
			}
		}
    	return buffer.toString();
    }
    public HttpHeaders edit() {
    	try{
	    	String id = getParameter("id");
	    	if(id != null &&!id.equals("")){
	    		List<PrvSysparam> cityList = prvAttrruleService.getCityMapById(id);
		    	int flag = prvAttrruleService.isOktoDelorUpdate(id);
		    	PrvAttrEditPageInitDataBO bo = new PrvAttrEditPageInitDataBO();
		    	
		    	bo.setCity(combineCitytoString(cityList));
		    	bo.setFlag(flag);
		    	bo.setMnames(prvAttrruleService.getMnameById(id));
		    	setModel(bo);
	    	}
    	}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
        return buildDefaultHttpHeaders("inputBasic");
    }
    //重新设置city
    private List<String> resetCity(PrvAttrruleBo prvAttrruleBo) throws Exception{
    	
    	List<String> list = new ArrayList<String>();
    	if(prvAttrruleBo.getCity().contains("*")){
			List<PrvSysparam> prvSysparams =  paramService.getData("PRV_CITY");
			for (PrvSysparam prvSysparam : prvSysparams) {
				list.add(prvSysparam.getMcode());
			}
		}else{
			
		   String citystr = prvAttrruleBo.getCity().replace("\"","" );
		   citystr = citystr.substring(1,citystr.length()-1);
		   String[] citys =  citystr.split(",");
		   list = Arrays.asList(citys);
		}
    	return list;
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
    	String[] ids = getParameterIds();
      //删除失败的id和对应消息以Map结构返回，可用于前端批量显示错误提示和计算表格组件更新删除行项
        Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
		for (int i = 0; i < ids.length; i++) {
			if(prvAttrruleService.isOktoDelorUpdate(ids[i])== 1){
				  errorMessageMap.put(Long.parseLong(ids[i]), "该规则已经被使用，不可以被删除");
			}else{
				 try {
					    PrvAttrrule prvAttrrule = new PrvAttrrule();
					    prvAttrrule.setId(Long.parseLong(ids[i]));
					    prvAttrrule = (PrvAttrrule) persistentService.find(prvAttrrule).get(0);
		                prvAttrruleService.delete(prvAttrrule);
		                prvAttrruleService.deleteSysAttrAreaByattId(ids[i]);
		                if(prvAttrrule.getValueparam()!=null&&!prvAttrrule.getValueparam().equals("")){
		                	prvAttrruleService.deleteDateByGcode(prvAttrrule.getValueparam());
		                }
		            } catch (Exception e) {
		                errorMessageMap.put(Long.parseLong(ids[i]), e.getMessage());
		         }
			}
		}
		
		int rejectSize = errorMessageMap.size();
        if (rejectSize == 0) {
            setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + ids.length + "条"));
        } else {
            if (rejectSize == ids.length) {
                setModel(OperationResult.buildFailureResult("所有选取记录删除操作失败", errorMessageMap));
            } else {
                setModel(OperationResult.buildWarningResult("删除操作已处理. 成功:" + (ids.length - rejectSize) + "条"
                        + ",失败:" + rejectSize + "条", errorMessageMap));
            }
        }
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	 try {
             Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
             setModel( prvAttrruleService.queryPrvAttrruleList(quePrvAttrruleParams, pageable));
         } catch (Exception e) {
             throw new WebException(e.getMessage(), e);
         }
    	 return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders  isOktoDelorUpdate(){
    	String id = getParameter("id");
    	try {
    	 int flag =  prvAttrruleService.isOktoDelorUpdate(id);
    	 setModel(flag);
		} catch (Exception e) {
	         throw new WebException(e.getMessage(), e);
	    }
    	return buildDefaultHttpHeaders();
    }

    public HttpHeaders getEditPageInitContent(){
    	try {
	    	String id = getParameter("id");
	    	List<PrvSysparam> cityList = prvAttrruleService.getCityMapById(id);
	    	int flag = prvAttrruleService.isOktoDelorUpdate(id);
	    	PrvAttrEditPageInitDataBO bo = new PrvAttrEditPageInitDataBO();
	    	//bo.setCity(cityList);
	    	bo.setFlag(flag);
	    	setModel(bo);
    	} catch (Exception e) {
			 throw new WebException(e.getMessage(), e);
		}
   	return buildDefaultHttpHeaders();
    }
    public HttpHeaders getCityMapById(){
    	String id = getParameter("id");
    	try {
			prvAttrruleService.getCityMapById(id);
		} catch (Exception e) {
			 throw new WebException(e.getMessage(), e);
		}
    	return buildDefaultHttpHeaders();
    }
    //获取地市
    public Map<String, String> getCityMap() throws Exception {
    	return paramService.getSelectData("PRV_CITY");
    }

    public Map<String, String> getExtendsCityMap() throws Exception {
    	Map<String, String> cityMap = new LinkedHashMap<String, String>();
    	cityMap.put("*", "全部城市");
    	cityMap.putAll( paramService.getSelectData("PRV_CITY"));
    	return cityMap;
    }

    //获取属性值来源
    public Map<String, String> getValuesrcMap() throws Exception {
        return paramService.getSelectData("PRD_VALUESRC");
    }
    
    //获取是否必填
    public Map<String, String> getIfnecessaryMap() throws Exception {
        return paramService.getSelectData("SYS_YES_NO");
    }
    
	public QuePrvAttrruleParams getQuePrvAttrruleParams() {
		return quePrvAttrruleParams;
	}

	public void setQuePrvAttrruleParams(QuePrvAttrruleParams quePrvAttrruleParams) {
		this.quePrvAttrruleParams = quePrvAttrruleParams;
	}
	
}