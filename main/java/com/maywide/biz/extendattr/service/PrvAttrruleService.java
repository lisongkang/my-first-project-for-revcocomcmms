package com.maywide.biz.extendattr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.extendattr.dao.PrvAttrruleDao;
import com.maywide.biz.extendattr.entity.PrvAttrrule;
import com.maywide.biz.extendattr.pojo.PrvAttrruleBo;
import com.maywide.biz.extendattr.pojo.QuePrvAttrruleParams;
import com.maywide.biz.prd.entity.PrvSalesPoint;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;

@Service
@Transactional
public class PrvAttrruleService extends BaseService<PrvAttrrule,Long>{
    
    @Autowired
    private PrvAttrruleDao prvAttrruleDao;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<PrvAttrrule, Long> getEntityDao() {
        return prvAttrruleDao;
    }

    @Transactional(readOnly = true)
	public PageImpl<PrvAttrrule> queryPrvAttrruleList(QuePrvAttrruleParams quePrvAttrruleParams, Pageable pageable) throws Exception {
        PageImpl<PrvAttrrule> pageResult = null;
    	
    	Page<PrvAttrrule> page = new Page<PrvAttrrule>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append("SELECT * FROM ("); // 要在最外层加select *，否则框架本身根据构造的语句查总记录数时的sql会错误
        sql.append("SELECT p.RECID id,");
        sql.append(" p.ATTRNAME attrname,");
        sql.append(" p.ATTRCODE attrcode,");
        sql.append(" p.VALUESRC valuesrc ,");
        sql.append(" p.VALUEPARAM valueparam ,");
        sql.append(" p.IFNECESSARY ifnecessary ,");
        sql.append(" p.DEFAULTVALUE defaultvalue ,");
        sql.append(" (SELECT GROUP_CONCAT(s.mname) FROM prv_sysparam s,sys_attr_area a  WHERE s.gcode='PRV_CITY' ");
        
       /* if(quePrvAttrruleParams.getCity() != null && !quePrvAttrruleParams.getCity().trim().equals("")){
         	 sql.append(" AND a.city=? ");
         	 paramList.add(quePrvAttrruleParams.getCity());
        }*/
        
        sql.append(" AND  a.attrruleid =  p.RECID  AND FIND_IN_SET(s.mcode,a.city)) city ");
        sql.append("FROM prv_attrrule p  WHERE 1=1  ");

        if(quePrvAttrruleParams.getCity() != null && !quePrvAttrruleParams.getCity().trim().equals("")){
          	 sql.append(" AND p.RECID in( select t.attrruleid from sys_attr_area t where t.city=?)");
          	 paramList.add(quePrvAttrruleParams.getCity());
         }
        sql.append(" ORDER BY p.CREATETIME DESC,p.RECID DESC");
        sql.append(" ) v ");
        
        persistentService.clear();
		page = persistentService.find(page, sql.toString(), PrvAttrrule.class, paramList.toArray());
        
		 if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<PrvAttrrule> resultList = page.getResult();
			//重新组装数据
			//recombineDate(resultList);
			pageResult = new PageImpl(resultList, pageable, total);
		 } else {
		    List<PrvAttrrule> resultList = new ArrayList<PrvAttrrule>();
		    pageResult = new PageImpl(resultList, pageable, 0);
		 }
		return pageResult;
	}

	public void doSave(PrvAttrruleBo prvAttrruleBo) throws Exception {
		PrvAttrrule prvAttrrule = new PrvAttrrule();
		if(prvAttrruleBo.getId()!=null&&!prvAttrruleBo.getId().equals(""))
		{
			prvAttrrule.setId(Long.valueOf(prvAttrruleBo.getId()));
			
		}
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		prvAttrrule.setCreateoper(loginInfo.getOperid());
		prvAttrrule.setCreatetime(new Date());
		prvAttrrule.setAttrcode(prvAttrruleBo.getAttrcode());
	    prvAttrrule.setAttrname(prvAttrruleBo.getAttrname());
	    prvAttrrule.setValueparam(prvAttrruleBo.getValueparam());
	    prvAttrrule.setValuesrc(prvAttrruleBo.getValuesrc());
	    prvAttrrule.setIfnecessary(prvAttrruleBo.getIfnecessary());
	    prvAttrrule.setDefaultvalue(prvAttrruleBo.getDefaultvalue());
	    persistentService.clear();
	    if(prvAttrrule.getId()!=null &&!prvAttrrule.getId().equals("")){
	    	String sql = "UPDATE  prv_attrrule  " +
	    			          " SET ATTRCODE=?,ATTRNAME=?,VALUESRC=?,VALUEPARAM=?,IFNECESSARY=?,DEFAULTVALUE=?,UPDATEOPER=?,UPDATETIME=?" +
	    			          " WHERE recid = ?; ";
	    	List<Object> listparms = new ArrayList<Object>();
	    	listparms.add(prvAttrruleBo.getAttrcode());
	    	listparms.add(prvAttrruleBo.getAttrname());
	    	listparms.add(prvAttrruleBo.getValuesrc());
	    	listparms.add(prvAttrruleBo.getValueparam());
	    	listparms.add(prvAttrruleBo.getIfnecessary());
	    	listparms.add(prvAttrruleBo.getDefaultvalue());
	    	listparms.add(loginInfo.getOperid());
	    	listparms.add(new Date());
	    	listparms.add(prvAttrruleBo.getId());
	    	persistentService.executeSql(sql, listparms.toArray());
	    	//persistentService.update(prvAttrrule);
	    }else{
	    	 persistentService.save(prvAttrrule);
	    }
	   
		//String sql = "insert into prv_attrrule(ATTRNAME,ATTRCODE,VALUESRC,VALUEPARAM,IFNECESSARY,DEFAULTVALUE) values(?,?,?,?,?,?)";
		
		//int id = persistentService.executeSql(sql, prvAttrruleBo.getAttrname(),prvAttrruleBo.getAttrcode(),prvAttrruleBo.getValuesrc(),prvAttrruleBo.getValueparam(),prvAttrruleBo.getIfnecessary(),prvAttrruleBo.getDefaultvalue());
		
		saveSysAttrArea(prvAttrrule.getId(), prvAttrruleBo.getCityList());
		
	}
	
	@Transactional(readOnly = true)
	public  void saveSysAttrArea(Long attrruleId,List<String> cityList) throws Exception{
		String sql = "insert into sys_attr_area(ATTRRULEID,CITY) value(?,?)";
		for (String city : cityList) {
			persistentService.executeSql(sql, attrruleId,city);
		}
	}
	@Transactional(readOnly = true)
	public int isOktoDelorUpdate(String id) {
		List list = new ArrayList();
		try{
			String sql = "SELECT 1 FROM sys_cust_attr s WHERE s.ATTRRULEID = ?  LIMIT 1";
			 list = persistentService.findObjectList(sql, id);
		}catch(Exception e){
			throw new WebServiceException(e);
		}
		
		return list.size();
	}
	
	@Transactional(readOnly = true)
	public boolean isExistAttrCode(String attrCode){
		List list = new ArrayList();
		try{
			String sql = "SELECT 1 FROM prv_attrrule s WHERE s.ATTRCODE = ?  LIMIT 1";
			 list = persistentService.findObjectList(sql,attrCode);
		}catch(Exception e){
			throw new WebServiceException(e);
		}
		
		if(list.size()==1){
			return true;
		}else{
			return false;
		}
	}
	
	@Transactional(readOnly = true)
	public boolean isExistGcodeInprvSystem(String gcode){
		List list = new ArrayList();
		try{
			String sql = "SELECT 1 FROM prv_sysparam s WHERE s.gcode = ?  LIMIT 1";
			 list = persistentService.findObjectList(sql,gcode);
		}catch(Exception e){
			throw new WebServiceException(e);
		}
		
		if(list.size()==1){
			return true;
		}else{
			return false;
		}
	}
	
	@Transactional(readOnly = true)
	public PrvAttrrule getAttrrule(String id) throws Exception{
		PrvAttrrule prvAttrrule = new PrvAttrrule();
		prvAttrrule.setId(Long.parseLong(id));
		persistentService.clear();
		return (PrvAttrrule) persistentService.find(prvAttrrule).get(0);
	}
	/**
	 * 插入prv_sysparam 数据
	 * @param gcode
	 * @param gnameList
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void addDatetoPrvSystem(String gcode,List<String> mnameList) throws Exception{
		for (int i = 0; i < mnameList.size(); i++) {
			PrvSysparam prvSysparam = new PrvSysparam();
			prvSysparam.setGcode(gcode);
			prvSysparam.setMcode(String.valueOf(i+1));//从1 开始
			prvSysparam.setMname(mnameList.get(i));
			persistentService.save(prvSysparam);
		}
	}
	
	/**
	 * 根据id 获取对应的参数属性
	 * @throws Exception 
	 */
	
	@Transactional(readOnly = true)
	public  String getMnameById(String id) throws Exception{
		
		String sql = "SELECT (SELECT GROUP_CONCAT(s.mname) FROM prv_sysparam s WHERE s.`gcode`=p.valueparam) mname " +
				      "FROM prv_attrrule p WHERE 1=1 AND p.`VALUESRC` IN(1,2) AND p.recid = ? ";
		List<PrvSysparam> list = persistentService.find(sql, PrvSysparam.class,id);
		if(list.size()>0)
		   return list.get(0).getMname();
		else {
			return null;
		}
	}
	/**
	 * 根据gcode删除prv_sysparam 对应的数据
	 * @param gcode
	 * @throws Exception 
	 */
	@Transactional(readOnly = true)
	public void deleteDateByGcode(String gcode) throws Exception{
		String sql = "delete from prv_sysparam where gcode = ?";
		persistentService.executeSql(sql, gcode);
	}
	
	@Transactional(readOnly = true)
	public void deleteSysAttrAreaByattId(String id) throws Exception{
		String sql = "delete from sys_attr_area where ATTRRULEID  = ?";
		
		persistentService.executeSql(sql, id);
	}

	@Transactional(readOnly = true)
	public List<PrvSysparam> getCityMapById(String id) throws Exception {
		
		  StringBuffer sql = new StringBuffer();
		  sql.append("SELECT * FROM ("); // 要在最外层加select *，否则框架本身根据构造的语句查总记录数时的sql会错误
		  sql.append("SELECT s.CITY mcode,");
          sql.append("(SELECT p.mname FROM prv_sysparam p WHERE p.gcode='PRV_CITY' AND p.mcode = s.CITY) mname");
		  sql.append(" FROM sys_attr_area s WHERE 1=1 AND s.ATTRRULEID = ?");
		  sql.append(") v");
		  
		  List<PrvSysparam> list = persistentService.find(sql.toString(), PrvSysparam.class,id);
		  
		  return list;
	}
	
	
}
