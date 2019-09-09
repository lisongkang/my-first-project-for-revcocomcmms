package com.maywide.biz.task.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.ad.adset.entity.AdConfig;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.task.entity.BizTaskInfo;
import com.maywide.biz.task.pojo.BizTaskInfoPO;
import com.maywide.biz.task.pojo.TaskAndGridsRelation;
import com.maywide.biz.task.service.BizTaskInfoService;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.SimpleController;

/**
 * 
 *<p> 
 * 任务管理
 *<p>
 * Create at 2017-11-15
 *
 *@autor zhuangzhitang
 */
public class BizTaskInfoController extends SimpleController{
	private BizTaskInfoPO taskinfo;
	
	@Autowired
	private GridInfoService gridInfoService;
	
	@Autowired
	private BizTaskInfoService bizTaskInfoService;
	public HttpHeaders index (){
		return buildDefaultHttpHeaders("index");
	}
	public HttpHeaders inputBasic() throws Exception{
		String taskid = getParameter("taskid");
		if(taskid!=null){
			setModel(bizTaskInfoService.getTaskInfoByid(taskid));
		}
		return buildDefaultHttpHeaders("inputBasic");
	}
	
	public HttpHeaders showTaskInfo()throws Exception{
		String taskid = getParameter("taskid");
		setModel(bizTaskInfoService.getTaskInfoByid(taskid));
		return buildDefaultHttpHeaders("taskinfo");
	}
	public HttpHeaders showTaskInfoFromMonitor()throws Exception{
		String taskid = getParameter("taskid");
		setModel(bizTaskInfoService.showTaskInfoFromMonitor(taskid));
		return buildDefaultHttpHeaders("taskmonitorinfo");
	}
	public HttpHeaders toDeployPage() throws Exception{
		String taskid = getParameter("taskids");
	    getRequest().setAttribute("taskid", taskid);
	    setModel(bizTaskInfoService.findTasksById(taskid));
		return buildDefaultHttpHeaders("replaytask");
	}
	public HttpHeaders doSave() throws Exception{
		String taskinfoJson = getParameter("taskinfo");
		BizTaskInfo bizTaskInfo = (BizTaskInfo) BeanUtil.jsonToObject(taskinfoJson,
	               BizTaskInfo.class);
		bizTaskInfoService.doSave(bizTaskInfo);
	    setModel("success");
		return buildDefaultHttpHeaders();
	}
	
	public HttpHeaders deleteTask()throws Exception{
		String taskid = getParameter("taskid");
		bizTaskInfoService.deleteTask(taskid);
		setModel("success");
		return buildDefaultHttpHeaders();
	}
	
	public HttpHeaders doSaveRelationTaskAndGrid() throws Exception{
		String taskAndGridRelation = getParameter("taskAndGridRelation");
		TaskAndGridsRelation taskAndGridRelationBo = (TaskAndGridsRelation) BeanUtil.jsonToObject(taskAndGridRelation,
				TaskAndGridsRelation.class);
		
		bizTaskInfoService.doSaveRelationTaskAndGrid(taskAndGridRelationBo);
		setModel("success");
		return buildDefaultHttpHeaders();
	}
	public HttpHeaders doSubmitTask() throws Exception{
		String taskids = getParameter("taskids");
		String errorTask = bizTaskInfoService.doSubmitTask(taskids);
		setModel(errorTask);
		return buildDefaultHttpHeaders();
	}
	public HttpHeaders findByPage(){
		List<BizTaskInfoPO> list = new ArrayList<BizTaskInfoPO>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			setModel(bizTaskInfoService.findByPage(taskinfo,pageable));
			
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<BizTaskInfoPO>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}
	
	public HttpHeaders findPageForMonitor(){
		List<BizTaskInfoPO> list = new ArrayList<BizTaskInfoPO>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			setModel(bizTaskInfoService.findPageForMonitor(taskinfo,pageable));
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<BizTaskInfoPO>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}
	public BizTaskInfoPO getTaskinfo() {
		return taskinfo;
	}
	public void setTaskinfo(BizTaskInfoPO taskinfo) {
		this.taskinfo = taskinfo;
	}
	public Map<String, String> getGridTypeMap() {
        return  gridInfoService.getGridTypeMap();
}
	
}
