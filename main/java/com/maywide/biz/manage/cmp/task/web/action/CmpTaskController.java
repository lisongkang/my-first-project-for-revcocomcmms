package com.maywide.biz.manage.cmp.task.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.inter.pojo.queactivitylist.CmpActivityInfo;
import com.maywide.biz.inter.pojo.queactivitylist.QueActivityListReq;
import com.maywide.biz.inter.pojo.queactivitylist.QueActivityListResp;
import com.maywide.biz.inter.pojo.quetasklist.CmpTaskInfo;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListReq;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListResp;
import com.maywide.biz.manage.cmp.task.service.CmpTaskWebService;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.SimpleController;
/**
 * 
 *<p> 
 *  营销任务管理
 *<p>
 * Create at 2017-3-12
 *
 *@autor zhuangzhitang
 */
@MetaData("[com.maywide].manage.cmp.task管理")
public class CmpTaskController extends SimpleController{

	@Autowired
	private CmpTaskWebService cmpTaskWebService;
	
	private QueTaskListReq queTaskListReq ;
	
	
	/**
	 * 营销任务主页
	 */
	public HttpHeaders index(){
		return buildDefaultHttpHeaders("index");
	}
	
	/**
	 * 活动详情页
	 * @return
	 */
	public HttpHeaders detail(){
		String activityid = getParameter("activityid");
		String gridid = getParameter("gridcode");
		CmpActivityInfo cmpActivityInfo = new CmpActivityInfo();
		cmpActivityInfo.setActivityid(activityid);
		cmpActivityInfo.setGridid(gridid);
		setModel(cmpActivityInfo);
		
		return buildDefaultHttpHeaders("detail");
	}
	
	/**
	 * 活动主页
	 * 查询活动列表
	 * @return
	 */
	public HttpHeaders queActivityList(){
		List<CmpActivityInfo> list = new ArrayList<CmpActivityInfo>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			String reqBo = getParameter("queActivityListReq");
			QueActivityListReq req = (QueActivityListReq) BeanUtil.jsonToObject(reqBo,
					QueActivityListReq.class);
			
			if(null==req){
				setModel(new PageImpl<CmpActivityInfo>(list, pageable, 1));
			}else{
				//处理gridcode
				String gridCode = req.getGridCode();
				if(StringUtils.isEmpty(gridCode)){
					gridCode = "-1" ;
				}else{
					gridCode = gridCode.replace("[", "");
					gridCode = gridCode.replace("]", "");
					gridCode = gridCode.replace("\"", "");
				}
				
				req.setGridCode(gridCode);
				req.setIndex(pageable.getPageNumber() + 1);
				req.setPageSize(pageable.getPageSize());
				QueActivityListResp resp =  new QueActivityListResp();
				//调用接口
				cmpTaskWebService.queActivityList(req, resp);
				//翻译网格名称
				list = cmpTaskWebService.translateToGridCode(resp.getDatas());
				if(resp.getDatas() == null||resp.getDatas().size()==0){
					setModel(new PageImpl<CmpActivityInfo>(list, pageable, 1));
				}else{
					setModel(new PageImpl<CmpActivityInfo>(list, pageable,resp.getTotal()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<CmpActivityInfo>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}
	
	/**
	 * 网格任务列表查询(活动详情页需要)
	 */
	public HttpHeaders queTaskList(){
		List<CmpTaskInfo> list = new ArrayList<CmpTaskInfo>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		QueTaskListReq req =queTaskListReq;
		try{
			if(null==req||req.getActivityid()==""||req.getGridCode()==""){
				setModel(new PageImpl<CmpTaskInfo>(list, pageable, 1));
			}else{
				req.setIndex(pageable.getPageNumber() + 1);
				req.setPageSize(pageable.getPageSize());
				QueTaskListResp resp =  new QueTaskListResp();
				//调用接口
				cmpTaskWebService.queTaskList(req, resp);
				if(resp.getDatas() == null||resp.getDatas().size()==0){
					setModel(new PageImpl<CmpTaskInfo>(list, pageable, 1));
				}else{
					setModel(new PageImpl<CmpTaskInfo>(resp.getDatas(), pageable,resp.getTotal()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<CmpTaskInfo>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}
	
	public QueTaskListReq getQueTaskListReq() {
		return queTaskListReq;
	}

	public void setQueTaskListReq(QueTaskListReq queTaskListReq) {
		this.queTaskListReq = queTaskListReq;
	}
	public static void main(String[] args) {
		String gridcode = "\"[\"123\",\"ee\"]\"";
		gridcode = gridcode.replace("[", "");
		gridcode = gridcode.replace("]", "");
		gridcode = gridcode.replace("\"", "");
		System.out.println(gridcode);
	}

	public CmpTaskWebService getCmpTaskWebService() {
		return cmpTaskWebService;
	}

	public void setCmpTaskWebService(CmpTaskWebService cmpTaskWebService) {
		this.cmpTaskWebService = cmpTaskWebService;
	}
}
