package com.maywide.biz.task.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.google.common.collect.Maps;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.task.entity.BizTaskInfo;
import com.maywide.biz.task.entity.BizTaskResources;
import com.maywide.biz.task.pojo.BizTaskInfoPO;
import com.maywide.biz.task.pojo.TaskAndGridsRelation;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtils;

@Service
public class BizTaskInfoService {
	
	@Autowired
	private PersistentService persistentService;

	public PageImpl<BizTaskInfoPO> findByPage(BizTaskInfoPO taskinfo, Pageable pageable) throws Exception {
		PageImpl<BizTaskInfoPO> pageResult = null;
        Page<BizTaskInfoPO> page = new Page<BizTaskInfoPO>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT * FROM (");
        sql.append("SELECT a.TASK_ID as taskid,a.TASK_TITLE as tasktitle,a.TYPE as type");
        sql.append(",a.STATUS as status,a.ISEXPIRED as isexpired,a.CTIME as ctime,a.optime,a.opname ");
        sql.append(" FROM biz_task_info a where 1=1");
        
        if(taskinfo!= null){
        	
        	if(taskinfo.getTaskid()!=null){
        		sql.append(" AND a.`TASK_ID` =?");
        		paramList.add(taskinfo.getTaskid());
        	}
        	
        	if(StringUtils.isNotEmpty(taskinfo.getType())){
        		sql.append(" AND a.TYPE = ?");
        		paramList.add(taskinfo.getType());
        	}
        	
        	if(StringUtils.isNotEmpty(taskinfo.getStatus())){
        		sql.append(" AND a.STATUS = ?");
        		paramList.add(taskinfo.getStatus());
        	}
        	
        	if(StringUtils.isNotBlank(taskinfo.getStarttimerang())){
   			 List<String> result = paraseTimeRang(taskinfo.getStarttimerang());
   	    	 sql.append(" AND a.OPTIME >=?");
   			 paramList.add(result.get(0));
   			 sql.append(" AND a.OPTIME <?");
   			 paramList.add(result.get(1));
   		   }
        	
        	
        
        }
        sql.append(" order by a.optime desc");
        sql.append(" ) v");
        
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), BizTaskInfoPO.class, paramList.toArray());

        List<BizTaskInfoPO> resultList = page.getResult();
       
        if (null != page && null != resultList) {
        	 
            int total = page.getTotalCount();
            pageResult = new PageImpl<BizTaskInfoPO>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<BizTaskInfoPO>(new ArrayList<BizTaskInfoPO>(), pageable, 0);
        }
        return pageResult;
	}
	
	public PageImpl<BizTaskInfoPO> findPageForMonitor(BizTaskInfoPO taskinfo, Pageable pageable) throws Exception {
		PageImpl<BizTaskInfoPO> pageResult = null;
        Page<BizTaskInfoPO> page = new Page<BizTaskInfoPO>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT * FROM (");
        sql.append("SELECT a.TASK_ID as taskid,a.TASK_TITLE as tasktitle,a.TYPE as type");
        sql.append(",a.STATUS as status,a.ISEXPIRED as isexpired,a.CTIME as ctime,a.optime,a.opname ");
        sql.append(" FROM biz_task_info a where 1=1");
        
        if(taskinfo!= null){
        	
        	if(taskinfo.getTaskid()!=null){
        		sql.append(" AND a.`TASK_ID` =?");
        		paramList.add(taskinfo.getTaskid());
        	}
        	
        	if(StringUtils.isNotEmpty(taskinfo.getType())){
        		sql.append(" AND a.TYPE = ?");
        		paramList.add(taskinfo.getType());
        	}
        	
        	if(StringUtils.isNotEmpty(taskinfo.getStatus())){
        		sql.append(" AND a.STATUS = ?");
        		paramList.add(taskinfo.getStatus());
        	}
        	
        	if(StringUtils.isNotBlank(taskinfo.getStarttimerang())){
   			 List<String> result = paraseTimeRang(taskinfo.getStarttimerang());
   	    	 sql.append(" AND a.OPTIME >=?");
   			 paramList.add(result.get(0));
   			 sql.append(" AND a.OPTIME <?");
   			 paramList.add(result.get(1));
   		   }
        	
        	
        
        }
        sql.append(" order by a.optime desc");
        sql.append(" ) v");
        
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), BizTaskInfoPO.class, paramList.toArray());

        List<BizTaskInfoPO> resultList = page.getResult();
        
        //组装是否超时字段
        appendoOvertimeToResult(resultList);
        //组装网格信息
        appendGridRelationToResult(resultList);
       
        if (null != page && null != resultList) {
        	 
            int total = page.getTotalCount();
            pageResult = new PageImpl<BizTaskInfoPO>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<BizTaskInfoPO>(new ArrayList<BizTaskInfoPO>(), pageable, 0);
        }
        return pageResult;
	}
	private void appendoOvertimeToResult(List<BizTaskInfoPO> resultList) {
		if(resultList==null||resultList.size()==0){
			return ;
		}
		for(int i = 0 ; i <resultList.size();i++){
			BizTaskInfoPO po = resultList.get(i);
			if(BizConstant.TaskModel.BIZ_TASK_STATUS_3.equals(po.getStatus())&&(po.getCtime().compareTo(new Date())<0)){
				po.setIsOverTime("Y");
			}else{
				po.setIsOverTime("N");
			}
		}
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
	public void doSave(BizTaskInfo bizTaskInfo) throws Exception {
		
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		bizTaskInfo.setOpname(loginInfo.getLoginname());
		bizTaskInfo.setOptime(new Date());
		bizTaskInfo.setIsexpired(BizConstant.TaskModel.BIZ_TASK_ISEXPIRED_N);//未过期
		bizTaskInfo.setStatus(BizConstant.TaskModel.BIZ_TASK_STATUS_0);//
		if(null==bizTaskInfo.getTaskid()){
			bizTaskInfo.setTaskid(getTaskid());
			persistentService.save(bizTaskInfo);
		}else{
			Serializable pk = (Serializable) BeanUtils.getFieldValue(bizTaskInfo,
					"taskid");
			persistentService.update(bizTaskInfo, pk, "taskid");
		}
		
	}
	private Long getTaskid() throws Exception {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
		Date dt = new Date();  
		String sDateTime = sdf.format(dt);  
		int count = (int) persistentService.count("select 1 from biz_task_info t where t.task_id like '"+sDateTime+"%' ", null);
		count+=1;
		return Long.parseLong(sDateTime+getSequece(count));
	}
	
	private String getSequece(int count){
		String sequence = "";
		if(count<10){
			sequence ="000"+count;
		}else if(count <100){
			sequence ="00"+count;
		}else if (count <1000){
			sequence ="0"+count;
		}else{
			sequence = ""+count;
		}
		return sequence;
		
	}
	public List<BizTaskInfoPO> findTasksById(String taskid) throws Exception {
	
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT * FROM (");
        sql.append("SELECT a.TASK_ID as taskid,a.TASK_TITLE as tasktitle,a.TYPE as type");
        sql.append(",a.STATUS as status,a.ISEXPIRED as isexpired,a.CTIME as ctime,a.optime,a.opname ");
        sql.append(" FROM biz_task_info a where 1=1");
        
        if(taskid!= null){
        	String[] taskids= taskid.split(",");	
        	String taskparams = "";
        	for(int i = 0 ; i <taskids .length ; i ++){
        		taskparams += Long.parseLong(taskids[i]);
        		if(i !=taskids.length-1){
        			taskparams+=",";
        		}
        	}
    		sql.append(" AND a.`TASK_ID` in (" +
    				taskparams+
    				")");
        }
        sql.append(" order by a.optime desc");
        sql.append(" ) v");
        
        persistentService.clear();
        List<BizTaskInfoPO> resultList = persistentService.find(sql.toString(), BizTaskInfoPO.class, paramList.toArray());
        
        //
        appendGridRelationToResult(resultList);
        return resultList;
     
	}
	
	private void appendGridRelationToResult(List<BizTaskInfoPO> resultList) throws Exception {
		for(BizTaskInfoPO po:resultList){
			BizTaskResources resource = new BizTaskResources();
			resource.setTaskid(po.getTaskid());
			List<BizTaskResources> list = persistentService.find(resource);
			if(list.size()>0){
				resource = list.get(0);
				if(StringUtils.isNotBlank(resource.getGridid())){
					String sql = "SELECT GROUP_CONCAT(gridname SEPARATOR ',') as gridname FROM biz_grid_info WHERE gridid IN("+resource.getGridid()+") ";
				    List<BizTaskInfoPO> result = persistentService.find(sql,BizTaskInfoPO.class,null);
				    if(result.size()>0){
				    	po.setGridname(result.get(0).getGridname());
				    }
				}
				
			}
		}
	}
	
	private BizTaskInfoPO appendGridRelationToTaskInfo(BizTaskInfo info) throws Exception {
		BizTaskInfoPO po = new BizTaskInfoPO();
		BizTaskResources resource = new BizTaskResources();
		resource.setTaskid(info.getTaskid());
		List<BizTaskResources> list = persistentService.find(resource);
		if(list.size()>0){
			resource = list.get(0);
			if(StringUtils.isNotBlank(resource.getGridid())){
				String sql = "SELECT GROUP_CONCAT(gridname SEPARATOR ',') as gridname FROM biz_grid_info WHERE gridid IN("+resource.getGridid()+") ";
		        List<BizTaskInfoPO> result = persistentService.find(sql,BizTaskInfoPO.class,null);
			    if(result.size()>0){
			    	po.setGridname(result.get(0).getGridname());
			    }
			}
		    
		}
	    po.setActime(info.getActime());
	    po.setCometype(info.getCometype());
	    po.setCtime(info.getCtime());
	    po.setCusid(info.getCusid());
	    po.setIsexpired(info.getIsexpired());
	    po.setOpername(info.getOpname());
	    po.setOptime(info.getOptime());
	    po.setPri(info.getPri());
	    po.setStatus(info.getStatus());
	    po.setTaskdesc(info.getTaskdesc());
	    po.setTaskid(info.getTaskid());
	    po.setTasktitle(info.getTasktitle());
	    po.setStime(info.getStime());
	    po.setType(info.getType());
		return po;
	}

	public void doSaveRelationTaskAndGrid(
			TaskAndGridsRelation taskAndGridRelationBo) throws Exception {
		//taskid
		String taskids = taskAndGridRelationBo.getTaskids();
		String grids = taskAndGridRelationBo.getGrids();
		String[] taskidArr ;
		if(StringUtils.isNotBlank(taskids)){
			taskidArr =taskids.split(",");
			for(String taskid : taskidArr){
				BizTaskResources bizTaskResources = new BizTaskResources();
				bizTaskResources.setTaskid(Long.parseLong(taskid));
				List<BizTaskResources> list = persistentService.find(bizTaskResources);
			    if(list.size()>0){
			    	bizTaskResources = list.get(0);
			    	bizTaskResources.setGridid(grids);
			    	persistentService.update(bizTaskResources);
			    }else{
			    	bizTaskResources.setIsvalid("Y");//有效状态
			    	bizTaskResources.setOptime(new Date());
			    	bizTaskResources.setGridid(grids);
			    	persistentService.save(bizTaskResources);
			    }
			}
		}
	
			
				
	}
	public String doSubmitTask(String taskids) throws Exception {
		  //删除失败的id和对应消息以Map结构返回，可用于前端批量显示错误提示和计算表格组件更新删除行项
        StringBuilder errorTask = new StringBuilder();
        String[] taskarr = taskids.split(",");
        for(String taskid:taskarr){
        	BizTaskResources resource = new BizTaskResources();
        	resource.setTaskid(Long.parseLong(taskid));
        	List<BizTaskResources> list = persistentService.find(resource);
        	if(list.size()>0){
        		resource = list.get(0);
        		if(resource.getGridid()!=null||resource.getOperatorid()!=null){
        			BizTaskInfo taskInfo = new BizTaskInfo();
        			taskInfo.setTaskid(Long.parseLong(taskid));
        			taskInfo = (BizTaskInfo) persistentService.find(taskInfo).get(0);
        			taskInfo.setStatus(BizConstant.TaskModel.BIZ_TASK_STATUS_2);//已审批状态
        			Serializable pk = (Serializable) BeanUtils.getFieldValue(taskInfo,
        					"taskid");

        			
        			persistentService.update(taskInfo, pk, "taskid");
        		}else{
        			errorTask.append(taskid).append(",");
        		}
        	}else{
        		errorTask.append(taskid).append(",");
        	}
        }
      
        return errorTask.length()==0?"":errorTask.toString().substring(0, errorTask.length()-1);
	}
	public BizTaskInfoPO getTaskInfoByid(String taskid) throws Exception {
		BizTaskInfo taskInfo = new BizTaskInfo();
		taskInfo.setTaskid(Long.parseLong(taskid));
		List<BizTaskInfo> list = persistentService.find(taskInfo);
		if(list.size()>0){
			BizTaskInfo info =  list.get(0);
			
			return appendGridRelationToTaskInfo(info);
		}else{
			return null;
		}
	}
	
	public void deleteTask(String taskid) throws Exception {
		String sql = "delete from biz_task_info where task_id = ?";
		persistentService.executeSql(sql, taskid);
		persistentService.flushSession();
		String sql1 = "delete from biz_task_resources where  task_id = ?";
		persistentService.executeSql(sql1, taskid);
		persistentService.flushSession();
	}

	public BizTaskInfoPO showTaskInfoFromMonitor(String taskid) throws Exception {
		// TODO Auto-generated method stub
		return getTaskInfoByid(taskid);
	}
	
}
