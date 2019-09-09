package com.maywide.biz.survey.pojo;

import com.maywide.biz.survey.entity.BizSurveyList;

/**
 * 创建问卷的缓存类，存储待提交的问卷基本信息和问卷的题型和答案
 * @author zhuangzhitang
 *
 */
public class BizSurveyListCacheBo {

	private BizSurveyList surveyList;//问卷缓存
	
	
	
	public BizSurveyList getSurveyList() {
		return surveyList;
	}
	public void setSurveyList(BizSurveyList surveyList) {
		this.surveyList = surveyList;
	}
	

}
