package com.maywide.biz.survey.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.rest.HttpHeaders;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.survey.entity.BizQuestionList;
import com.maywide.biz.survey.entity.StaAnalysis;
import com.maywide.biz.survey.service.BizQuestionListService;
import com.maywide.biz.survey.service.StaAnalysisService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.survey.entity.StaAnalysis管理")
public class StaAnalysisController extends BaseController<StaAnalysis,Long> {

	@Autowired
	private StaAnalysisService staAnalysisService;
	
	@Autowired
	private BizQuestionListService bizQuestionListService;
	
	@Override
	protected BaseService<StaAnalysis, Long> getEntityService() {
		return staAnalysisService;
	}

	 @MetaData("[TODO方法作用]")
	    public HttpHeaders todo() {
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
	       
	        return super.doSave();
	    }

		public HttpHeaders toSuveryEchart(){
			String surveyId = getParameter("surveyId");
			try {
				List<BizQuestionList> list = bizQuestionListService.getQuestionListBySid(surveyId);
			    getRequest().setAttribute("sid",surveyId);
				setModel(filterQueByIsopen(list));
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return buildDefaultHttpHeaders("echartBasic");
		}
        
		public HttpHeaders toResultDetail(){
			String surveyId = getParameter("surveyId");
			try {
				List<BizQuestionList> list = bizQuestionListService.getQuestionListBySid(surveyId);
				 JSONObject result = new JSONObject();
				 result.put("dataJson", list);
				getRequest().setAttribute("sid",surveyId);
				getRequest().setAttribute("dataJson",result.toString());
				JSONArray arr =new  JSONArray(list);
				
				System.out.println(arr.toString());
			   // getRequest()
				//setModel(filterQueByIsopen(list));
			   
			} catch (Exception e) {
				e.printStackTrace();
			}
			return buildDefaultHttpHeaders("resultDetail");
		}
		private List<BizQuestionList> filterQueByIsopen(List<BizQuestionList> ques){
			List<BizQuestionList> result = new ArrayList<BizQuestionList>();
			if(ques == null)
				return null;
			for(BizQuestionList que : ques){
				if(que.getIsopen().equals("N") ){
					result.add(que);
				}
			}
			return result;
		}
		
		public HttpHeaders findEchartData(){
			String sid = getParameter("sid");
			String qid = getParameter("qid");
			setModel(staAnalysisService.findEchartDataBySidAndQid(sid,qid)) ;
			return buildDefaultHttpHeaders();
		}
}
