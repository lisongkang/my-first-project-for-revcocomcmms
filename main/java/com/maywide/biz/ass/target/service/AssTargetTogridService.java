package com.maywide.biz.ass.target.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.target.bo.AssTargetPatchBo;
import com.maywide.biz.ass.target.dao.AssTargetTogridDao;
import com.maywide.biz.ass.target.entity.AssTargetStore;
import com.maywide.biz.ass.target.entity.AssTargetTogrid;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.service.InterPrdService;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

@Service
@Transactional
public class AssTargetTogridService extends BaseService<AssTargetTogrid,Long>{
    
    @Autowired
    private AssTargetTogridDao assTargetTogridDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private InterPrdService prdService;

    @Override
    protected BaseDao<AssTargetTogrid,Long> getEntityDao() {
        return assTargetTogridDao;
    }
 
    /**
     * tocity 主页查询
     * @param flag togrid-下达网格， topatch-下达片区
     * @param map 参数
     * @param pageable 翻页
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public PageImpl<AssTargetPatchBo> getTargetTogrid(AssTargetPatchBo bo,Pageable pageable) throws Exception {
		
		PageImpl<AssTargetPatchBo> pageResult = null;
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		Page<AssTargetPatchBo> page = new Page<AssTargetPatchBo>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append("SELECT t.TOGRID_ID as ID,");
        sql.append(" t.CITY,");
		sql.append(" CODE2NAME(t.city, 'PRV_CITY') CITYNAME,");
		sql.append(" t.ASSID,");
		sql.append(" t.CYCLENUM,");
		sql.append(" t.ASSNUM,");
		sql.append(" t.ASSNUM_UNIT,");
		sql.append(" t.CURRENT_VALUE as currentValue,");
		sql.append(" t.OPTIME_VALUE,");
		sql.append(" t.OPTIME,");
		sql.append(" t.OPERATOR,");
		sql.append(" t.STATUS,");
		sql.append(" t.CURR_GRADE as currGrade,");
		sql.append(" t.FINAL_GRADE as finalGrade,");
		sql.append(" t1.NAME as ASSNAME,");
		sql.append(" t1.ASSCODE,");
		sql.append(" t2.GRIDCODE,");
		sql.append(" t2.GRIDID,");
		sql.append(" t2.GRIDNAME");
		sql.append(" FROM ASS_TARGET_TOGRID t,ASS_TARGET_STORE t1,BIZ_GRID_INFO t2");
		sql.append(" WHERE t.ASSID=t1.ASSID and t.GRIDID=t2.GRIDID and t.ISDEL=0");

		if (bo.getAssId()!=null){
			sql.append(" AND t.ASSID = "+bo.getAssId());
		}
		if (StrToList(bo.getGridids()) != null && StrToList(bo.getGridids()).size()>0){
			String codeJoins=this.join(",", StrToList(bo.getGridids()));
			if(StringUtils.isNotBlank(codeJoins))
				sql.append(" AND t2.GRIDID in ("+codeJoins+") ");
		}
		if (StringUtils.isNotBlank(bo.getCycleNumStr())){
			sql.append(" AND DATE_FORMAT(t.CYCLENUM,'%Y-%m') = '"+bo.getCycleNumStr()+"'");
		}
		if (bo.getStatus()!=null){
			sql.append(" AND t.STATUS = "+bo.getStatus());
		}
		
		// 超级管理员可以查看所有管理考核指标
		if (!loginInfo.isAdmin()) {
			sql.append(" AND t.city in('"+loginInfo.getCity()+"')");
		}
		
		sql.append(" order by t.OPTIME DESC,t.TOGRID_ID DESC");
		
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssTargetPatchBo.class, paramList.toArray());
		
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssTargetPatchBo> resultList = page.getResult();
			pageResult = new PageImpl<AssTargetPatchBo>(resultList, pageable, total);
		} else {
			List<AssTargetPatchBo> resultList = new ArrayList<AssTargetPatchBo>();
			pageResult = new PageImpl<AssTargetPatchBo>(resultList, pageable, 0);
		}
		
		return pageResult;
	
    }
    
    
    
    /**
     * togrid 主要是在保存和删除时，判断是否已存在
     * @param flag togrid-下达网格， topatch-下达片区
     * @param map 参数
     * @param pageable 翻页
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public List<AssTargetPatchBo> checkAssIsExist(AssTargetPatchBo bo) throws Exception {
		
		StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT * from ASS_TARGET_TOGRID t where t.ISDEL=0");
		if (!StringUtils.isEmpty(bo.getAssId()+"")){
			sql.append(" AND t.ASSID = "+bo.getAssId());
		}
		if (!StringUtils.isEmpty(bo.getCity())){
			sql.append(" AND t.CITY= '"+bo.getCity()+"'");
		} 

		persistentService.clear();
		List list = persistentService.find(sql.toString(), AssTargetPatchBo.class);
		
		return list;
	
    }
    
    
    /**
     * 获取当前市已经下发的所有指标信息，主要用来初始化列表页，指标查询Select
     * @param flag togrid-下达网格， topatch-下达片区
     * @param map 参数
     * @param pageable 翻页
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public List<AssTargetStore> getGridStores(Map<String,String> param) throws Exception {
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
		sql.append(" SELECT t.*,t.ASSID as id from ASS_TARGET_STORE t,");
		sql.append(" (select tmp.assid from ASS_TARGET_TOGRID tmp where 1=1 ");
		if (!loginInfo.isAdmin()) {
			sql.append(" AND tmp.city ='"+loginInfo.getCity()+"'");
		}
		sql.append(" GROUP BY tmp.assid) as t1");
		sql.append(" where t.assid=t1.assid and t.ISDEL=0");
		
		sql.append(" ORDER BY t.city");
	
		List<AssTargetStore> list = persistentService.find(sql.toString(), AssTargetStore.class, paramList.toArray());
		
		return list;
	
    }
    
    
    /**
     * t获取可下发的指标
     * @param flag togrid-下达网格， topatch-下达片区
     * @param map 参数
     * @param pageable 翻页
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public PageImpl<AssTargetStore> getPatchStores(Map<String,String> param,Pageable pageable) throws Exception {
		
		PageImpl<AssTargetStore> pageResult = null;
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		Page<AssTargetStore> page = new Page<AssTargetStore>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
		sql.append(" SELECT t.ASSID as id,");
		sql.append(" t.ASSCODE,");
		sql.append(" t.CITY,");
		sql.append(" code2name(t.CITY, 'PRV_CITY') cityName,");//这里必须要用tc.city
		sql.append(" t.NAME,");
		sql.append(" t.FIELDID,");
		sql.append(" t.VISQL,");
		sql.append(" t.ASSCONTENT,");
		sql.append(" t.EXPDATE");
		sql.append(" FROM ASS_TARGET_STORE t where t.ISDEL=0 and  t.ASSID in(");
		sql.append("SELECT tc.assid from ASS_TARGET_TOCITY tc where tc.asscity=t.city and tc.assid=t.ASSID");
		// 超级管理员可以查看所有管理考核指标
		if (!loginInfo.isAdmin()) { //这里用city
			sql.append(" AND tc.city in('"+loginInfo.getCity()+"') ");
		}
		sql.append("  GROUP BY tc.assid )");
		
		//优先以下发状态排序
		sql.append(" ORDER BY t.ASSID desc");
		
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssTargetStore.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssTargetStore> resultList = page.getResult();
			pageResult = new PageImpl<AssTargetStore>(resultList, pageable, total);
		} else {
			List<AssTargetStore> resultList = new ArrayList<AssTargetStore>();
			pageResult = new PageImpl<AssTargetStore>(resultList, pageable, 0);
		}
		
		return pageResult;
	
    }
    
    
    @MetaData("指标下发")
    public boolean targetToPatch(AssTargetPatchBo assTargetPatchBo) throws Exception {
    	
		try {
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
			List<String> ids =StrToList(assTargetPatchBo.getGridids());
			if (ids!=null && ids.size()>0) {
				for(String id : ids){
					if(StringUtils.isBlank(id))continue;
					AssTargetTogrid togrid=new AssTargetTogrid();
					togrid.setAssId(assTargetPatchBo.getAssId());
					togrid.setAssNum(assTargetPatchBo.getAssNum());
					
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					togrid.setCycleNum(DateUtils.parseDate(assTargetPatchBo.getCycleNumStr(), sdf));
					togrid.setGridId(Long.valueOf(id));
					togrid.setStatus(0);
					togrid.setOperator(loginInfo.getOperid());
					togrid.setCity(loginInfo.getCity());
					togrid.setOptime(new Date());
					togrid.setIsDel(0);
					
					persistentService.save(togrid);
				}
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("指标下发失败，请联系管理员");
		}
    }
    
    
    /**
     * 网格下发的时候，检查数据库是否已存在重复数据了
     * @param bo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public boolean checkBeforePatch(AssTargetPatchBo bo) throws Exception {
    	if(StringUtils.isNotBlank(bo.getGridids())){
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
	    	for(String id:bo.getGridids().split(",")){
	    		
	    		AssTargetTogrid grid=new AssTargetTogrid();
	    		grid.setGridId(Long.valueOf(id));
	    		grid.setAssId(bo.getAssId());
	    		grid.setCity(loginInfo.getCity());
	    		grid.setCycleNum(DateUtils.parseDate(bo.getCycleNumStr(), sdf));
	    		
	    		if(checkDataExists(grid)){
	    			return true;
	    		}
	    	}
    	}
    	
    	return false;
    }
    
    /**
     * 检查数据是否重复
     * @param bo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public boolean checkDataExists(AssTargetTogrid grid) throws Exception {
    	
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    		StringBuilder buff = new StringBuilder();
        	buff.append("SELECT GRIDID,CITY,ASSID,CYCLENUM from ASS_TARGET_TOGRID WHERE ISDEL=0");
        	buff.append(" AND GRIDID="+grid.getGridId());
        	buff.append(" AND CITY='"+grid.getCity()+"'");
        	buff.append(" AND ASSID="+grid.getAssId());
        	buff.append(" AND CYCLENUM='"+sdf.format(grid.getCycleNum())+"'");
        	
        	persistentService.clear();
        	List list=persistentService.find(buff.toString(), AssTargetTogrid.class, null);
        	if(list.size()>0) return true;
    	
    	return false;
    }
    
    
    /**
     *  更新状态
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void changeSt(AssTargetPatchBo bo) throws Exception {
		
		StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ASS_TARGET_TOGRID SET STATUS='"+bo.getStatus()+"' WHERE TOGRID_ID="+bo.getId());
		persistentService.clear();
		persistentService.executeSql(sql.toString());
    }
    
    
    /**
     *  逻辑删除
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void doDel(String[] ids) throws Exception {
    	
    	String whereIds=join(",",Arrays.asList(ids));
    	
		StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ASS_TARGET_TOGRID SET ISDEL=1 WHERE TOGRID_ID IN("+whereIds+")");
		persistentService.clear();
		persistentService.executeSql(sql.toString());
    }
    
    
    /**
     *  物理删除
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void doRealDel(String[] ids) throws Exception {
    	
    	String whereIds=join(",",Arrays.asList(ids));
    	
		StringBuffer sql = new StringBuffer();
        sql.append("DELETE from ASS_TARGET_TOGRID  WHERE TOGRID_ID IN("+whereIds+")");
		persistentService.clear();
		persistentService.executeSql(sql.toString());
    }
    
    
    public String join(String join,List<String> listStr){
        StringBuffer sb=new StringBuffer();
        for(int i=0,len =listStr.size();i<len;i++){
        	if(!StringUtils.isNotBlank(listStr.get(i))) continue;
            if(i==(len-1)){
                sb.append(listStr.get(i));
            }else{
                sb.append(listStr.get(i)).append(join);
            }
        }
        return sb.toString();
    }
    
    
    public List<String> StrToList(String ids){
    	if(!StringUtils.isNotBlank(ids)) return new ArrayList<String>();
    	return Arrays.asList(ids.split(","));
    }
}
