package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.answerQues.QuestionAndAnswer;
import com.maywide.biz.inter.pojo.answerQues.SumbitQuestionReq;
import com.maywide.biz.inter.pojo.answerQues.SumbitQuestionResp;
import com.maywide.biz.inter.pojo.queQuestionList.AnswerBean;
import com.maywide.biz.inter.pojo.queQuestionList.QueQuestionListReq;
import com.maywide.biz.inter.pojo.queQuestionList.QueQuestionListResp;
import com.maywide.biz.inter.pojo.queQuestionList.QuestionWithAnswerBean;
import com.maywide.biz.inter.pojo.queRankInfo.QueRankInfoReq;
import com.maywide.biz.inter.pojo.queRankInfo.QueRankInfoResp;
import com.maywide.biz.inter.pojo.queRankInfo.RankSalePointInfo;
import com.maywide.biz.inter.pojo.queResult.QueResultBean;
import com.maywide.biz.inter.pojo.queResult.QueSurveyResReq;
import com.maywide.biz.inter.pojo.queResult.QueSurveyResResp;
import com.maywide.biz.inter.pojo.queResult.ResultHandlerBean;
import com.maywide.biz.inter.pojo.queSalePoint.QueSalePointReq;
import com.maywide.biz.inter.pojo.queSalePoint.QueSalePointResp;
import com.maywide.biz.inter.pojo.queSurveylist.QueSurveyListReq;
import com.maywide.biz.inter.pojo.queSurveylist.QueSurveyResp;
import com.maywide.biz.inter.pojo.queSurveylist.SurveyBean;
import com.maywide.biz.prd.entity.StaSalesPointOper;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.survey.entity.BizCustAnsList;
import com.maywide.biz.survey.entity.BizSurveyList;
import com.maywide.biz.survey.entity.StaAnalysis;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class SurveyService extends CommonService {

	/**
	 * 查询问卷列表
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSurveyResultList(QueSurveyListReq req,QueSurveyResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
       
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        
        List params = new ArrayList();
        params.add("%"+loginInfo.getCity()+"%");
        params.add("*");
        params.add("%"+loginInfo.getAreaid()+"%");
        params.add("*");
        
        StringBuffer sb = new StringBuffer();
        sb.append("		SELECT A.SID sId,A.ISREAL isReal,A.INTIME inTime,A.SNAME sName,A.STATUS status ");
        sb.append("		FROM BIZ_SURVEY_LIST A");
        sb.append("		WHERE 1 = 1 AND ");
        sb.append("		(A.city LIKE ? OR A.city = ?) ");
        sb.append("		AND (A.AREAID LIKE ? OR A.AREAID = ?)");
        sb.append("		ORDER BY A.INTIME DESC");
        
        List<SurveyBean> surveyDatas = getDAO().find(sb.toString(), SurveyBean.class, params.toArray());
        if(surveyDatas != null && !surveyDatas.isEmpty()){
        	for(SurveyBean bean:surveyDatas){
        		List childParams = new ArrayList();
        		childParams.add(bean.getsId());
        		StringBuffer child = new StringBuffer();
				child.append("		FROM StaAnalysis ");
            	child.append("		where sid = ?");
            	List<StaAnalysis> analysis = getDAO().find(child.toString(), childParams.toArray());
            	if(analysis != null && !analysis.isEmpty()){
            		bean.setTotal(analysis.get(0).getTotal());
            	}
        	}
        	resp.setDatas(surveyDatas);
        }
        
        return returnInfo;
	}
	
	
	/**
	 * 查询问卷下的试题跟答案
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSurveyQuestionAndAnswerList(QueQuestionListReq req,QueQuestionListResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
       LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkEmpty(req.getsId(), "问卷id不能为空");
        CheckUtils.checkEmpty(req.getIsReal(),"当前问卷类型不能为空");
        if(req.getIsReal().equalsIgnoreCase("y")){
        	CheckUtils.checkEmpty(req.getsId(), "实名制问卷客户信息不能为空");
        }
        
        List params = new ArrayList();
        params.add(req.getsId());
        
        StringBuffer sb = new StringBuffer();
        sb.append("		SELECT DISTINCT (A.SID) sId, B.QID qId,A.QNO qNo,B.QCONTENT content,");
        sb.append("		B.ISOPEN isOpen,B.ISONLY isOnly,B.ISOK isImg ");
        sb.append("		FROM BIZ_SQ_RELATION A,BIZ_QUESTION_LIST B ");
        sb.append("		WHERE 1 = 1 AND ");
        sb.append("		A.QID = B.QID AND ");
        sb.append("		A.SID = ?");
        sb.append("		ORDER BY A.QNO");
        
        List<QuestionWithAnswerBean> qDatas = getDAO().find(sb.toString(), QuestionWithAnswerBean.class, params.toArray());
        if(qDatas != null && !qDatas.isEmpty()){
        	resp.setDatas(getAnswerForQuestion(qDatas));
        }
        return returnInfo;
	}
	
	private List<QuestionWithAnswerBean> getAnswerForQuestion(List<QuestionWithAnswerBean> qDatas) throws Exception{
		List<QuestionWithAnswerBean> openQuestions = new ArrayList<QuestionWithAnswerBean>();
		List<QuestionWithAnswerBean> closeQuestion = new ArrayList<QuestionWithAnswerBean>();
		
		for(QuestionWithAnswerBean bean:qDatas){
			if(bean.getIsOpen().equalsIgnoreCase("y")){
				openQuestions.add(bean);
			}else{
				closeQuestion.add(bean);
			}
		}
		
		if(!closeQuestion.isEmpty()){
			for(QuestionWithAnswerBean bean:closeQuestion){
				bean.setAnswers(getAnswer4Question(bean.getqId()));
			}
		}
		closeQuestion.addAll(openQuestions);
		Collections.sort(closeQuestion, comparator);
		return closeQuestion;
	}
	
	private List<AnswerBean> getAnswer4Question(Long qId) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT P.AID answerId,P.ACONTENT answerContent,P.ACODE answerCode,P.QNEXT nextQuestion ");
		sb.append("		FROM BIZ_QA_RELATION P");
		sb.append("		WHERE 1 = 1 AND");
		sb.append("		 P.QID = ?");
		sb.append("		ORDER BY P.ACODE");
		List<AnswerBean> datas = getDAO().find(sb.toString(), AnswerBean.class, qId);
		return datas;
	}
	
	
	private Comparator<QuestionWithAnswerBean> comparator = new Comparator<QuestionWithAnswerBean>() {

		@Override
		public int compare(QuestionWithAnswerBean next, QuestionWithAnswerBean last) {
			int nNumber = Integer.parseInt(next.getqNo());
			int lNumber = Integer.parseInt(last.getqNo());
			if(nNumber > lNumber){
				return 1;
			}
			if(nNumber < lNumber){
				return -1;
			}
			return 0;
		}
	};
	
	
	/**
	 * 提交问卷及答案
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo AnswerQuestion(SumbitQuestionReq req,SumbitQuestionResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req.getsId(), "问卷信息不能为空");
        CheckUtils.checkNull(req.getAnswers(), "题目信息内容不能为空");
        
        boolean success = true;
        String notice = "提交成功";
        
        if(checkIsReal(req.getsId()) && StringUtils.isBlank(req.getMarkNo())){
        	CheckUtils.checkNull(null,"当前实名制问卷客户信息不能为空");
        }
        try{
        	fillQuestion(req, loginInfo.getOperid());
        }catch(Exception e){
        	success = false;
        	notice = "提交失败:"+e.getMessage();
        }
        resp.setNotice(notice);
        resp.setSuccess(success);
        return returnInfo;
	}
	
	private boolean checkIsReal(Long sId) throws Exception{
		boolean isReal = false;
		
		BizSurveyList bizSurveyList = new BizSurveyList();
		bizSurveyList.setId(sId);
		
		List<BizSurveyList> datas = getDAO().find(bizSurveyList);
		if(datas == null || datas.isEmpty()){
			CheckUtils.checkNull(null,"根据信息查找不到该问卷信息");
		}
		bizSurveyList = datas.get(0);
		if(bizSurveyList.getIsreal().equalsIgnoreCase("y")){
			isReal = true;
		}
		return isReal;
	}
	
	private void fillQuestion(SumbitQuestionReq req,Long operid) throws Exception{
		if(req.getAnswers() == null || req.getAnswers().isEmpty()){
			CheckUtils.checkNull(null,"题目信息内容不能为空");
		}
		Date date = new Date();
		Random random = new Random();
		Random random2 = new Random(random.nextLong());
		long batchNo = random2.nextLong();
		for(QuestionAndAnswer answer:req.getAnswers()){
			BizCustAnsList ansList = new BizCustAnsList();
			ansList.setAnswertype(ansList.getAnswertype());
			ansList.setIntime(date);
			if(StringUtils.isNotBlank(req.getMarkNo())){
				ansList.setMarkno(req.getMarkNo());
			}
			ansList.setOperator(operid);
			ansList.setQid(answer.getqId());
			ansList.setSid(req.getsId());
			ansList.setAnswer(answer.getAnswer());
			ansList.setAnswertype(answer.getAnswerType());
			ansList.setBatchNo(batchNo);
			getDAO().save(ansList);
		}
	}
	
	/**
	 *  查询问卷调查结果
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo querySurveyResult(QueSurveyResReq req,QueSurveyResResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo,CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkEmpty(req.getsId(), "问卷id不能为空");
        List params = new ArrayList();
        params.add(req.getsId());
        params.add("N");
        
        
       //先通过sid获取题目跟题目的答案
        StringBuffer sb = new StringBuffer();
        sb.append("		SELECT DISTINCT (A.QID) qId,B.SID sId,A.ISONLY isOnly,A.QCONTENT qContent, ");
        sb.append("		C.ACODE aCode ,C.ACONTENT aContent,B.QNO qNo FROM");
        sb.append("		BIZ_QUESTION_LIST A,BIZ_SQ_RELATION B,BIZ_QA_RELATION C ");
        sb.append("		WHERE 1 = 1 AND ");
        sb.append("		A.QID = B.QID AND");
        sb.append("		C.QID = B.QID AND");
        sb.append("		B.SID = ? AND");
        sb.append("		A.ISOPEN = ?");
        sb.append("		ORDER BY B.QNO , C.ACODE");
        List<ResultHandlerBean> answerList = getDAO().find(sb.toString(), ResultHandlerBean.class, params.toArray());
        
		if(answerList == null || answerList.isEmpty()){
			CheckUtils.checkNull(null,"该问卷暂无数据记录");
		}
		params.clear();
		params.add(req.getsId());
		sb = new StringBuffer();
		sb.append("		SELECT A.SID sId,A.QID qId,A.TOTAL total,A.ANUMBER number,A.acode sCode FROM STA_ANALYSIS A");
		sb.append("		WHERE 1 = 1 AND ");
		sb.append("		A.SID = ?");
        
        List<ResultHandlerBean> staDatas = getDAO().find(sb.toString(), ResultHandlerBean.class, params.toArray());
        if(staDatas == null || staDatas.isEmpty()){
        	CheckUtils.checkNull(null,"该问卷暂无数据记录");
        }
        handlerResult(answerList,staDatas,resp);
        return returnInfo;
	}
	
	private void handlerResult(List<ResultHandlerBean> answerList,List<ResultHandlerBean> datas,QueSurveyResResp resp) throws Exception{
		List<List<QueResultBean>> results = new ArrayList<List<QueResultBean>>();
		Map<String, List<ResultHandlerBean>> map = new HashMap<String, List<ResultHandlerBean>>();
		for(ResultHandlerBean answer:answerList){
			String mark = answer.getsId()+""+answer.getqId();
			List<ResultHandlerBean> qList = map.get(mark);//获取同一张问卷同一个试题里的集合
			if(qList == null){
				qList = new ArrayList<ResultHandlerBean>();
				map.put(mark, qList);
			}
			qList.add(answer);
		}
		
		
//		Map<String, List<ResultHandlerBean>> sqda = new HashMap<String, List<ResultHandlerBean>>();
		//将有答案的题目分别规划到该对应的目录下
		Map<String,List<ResultHandlerBean>> dataMap = new HashMap<String, List<ResultHandlerBean>>();//将获取到的统计答案根据题目进行划分
		for(ResultHandlerBean data:datas){
			String mark = data.getsId()+""+data.getqId();//筛选出跟答案列表里相同题目的那些统计结果
			List<ResultHandlerBean> qList = dataMap.get(mark);
			if(qList == null){
				qList = new ArrayList<ResultHandlerBean>();
				dataMap.put(mark, qList);
			}
			qList.add(data);
		}
		
		Iterator<Entry<String, List<ResultHandlerBean>>> iterator = map.entrySet().iterator();
		//对题目的答案进行划分,根据答案长度划分不同数量的集合
		while(iterator.hasNext()){
			Entry<String, List<ResultHandlerBean>> entry = iterator.next();
			String key = entry.getKey();
			List<ResultHandlerBean> answerLs = entry.getValue();
			List<ResultHandlerBean> dataList = dataMap.get(key);
			List<QueResultBean> queList = getQueResultBeanData(answerLs,dataList);
			results.add(queList);
		}
		Collections.sort(results, resultComparator);
		resp.setDatas(results);
	}
	 
	
	private List<QueResultBean> getQueResultBeanData(List<ResultHandlerBean> answerlist,List<ResultHandlerBean> dataList){
		List<QueResultBean> tmpDatas = new ArrayList<QueResultBean>();
		if(dataList == null || dataList.isEmpty()){
			for(ResultHandlerBean bean:answerlist){
				tmpDatas.add(new QueResultBean(bean));
			}
		}else{
			for(ResultHandlerBean bean:answerlist){
				int position = -1;
				for(ResultHandlerBean data:dataList){
					if(data.getsCode().equals(bean.getaCode())){//筛选出答案跟选项相同的实例
						position = dataList.indexOf(data);
						break;
					}
				}
				QueResultBean tmp = new QueResultBean(bean);
				if(position != -1){
					tmp.setsCode(dataList.get(position).getsCode());
					tmp.setNumber(dataList.get(position).getNumber());
					tmp.setTotal(dataList.get(position).getTotal());
					tmp.setAnswerNumber(dataList.get(position).getNumber());
				}else{
					tmp.setsCode(bean.getaCode());
					tmp.setNumber(0l);
					tmp.setTotal(dataList.get(0).getTotal());
				}
				tmpDatas.add(tmp);
			}
		}
		
		return tmpDatas;
	}
	
	private void setAnswerCount(QueResultBean bean) throws Exception{
		List params = new ArrayList();
		params.add(bean.getqId());
		params.add("%"+bean.getsCode()+"%");
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT COUNT(*) aTotal FROM BIZ_CUST_ANS_LIST A");
		sb.append("		WHERE 1 = 1 AND");
		sb.append("		A.QID = ?");
		sb.append("		AND A.ANSWER LIKE ?");
		long count = getDAO().count(sb.toString(),params.toArray());
		bean.setAnswerNumber(count);
	}
	
	/**
	 * 查询操作员销售积分
	 * @return
	 * @throws Exception 
//	 */ 
	public ReturnInfo queSalePoint(QueSalePointReq req,QueSalePointResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        Date stime = null;
    	Date etime = null;
    	int month = 0;
        if(StringUtils.isBlank(req.getEtime()) && StringUtils.isBlank(req.getStime())){
        	if(isFirstDayofMonth()){  //今天是本月的第一天,获取时间为上个月的所有绩效总和
        		stime = getPreviousMonthFirstDay(-1);  //开始时间为上个月的第一天
        		etime = getPrviousMonthLastDay(-1);    //结束时间为上个月的最后一天
        		month = Calendar.getInstance().get(Calendar.MONTH) ;
        	}else{ 
        		month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        		stime = getPreviousMonthFirstDay(0); //开始时间为本月第一天
        		etime = new Date();
        	}
        }
        resp.setMonth(month+"");
        
        List params = new ArrayList();
        params.add(loginInfo.getOperid());
        params.add(loginInfo.getCity());
        params.add(stime);
        params.add(etime);
        
        //筛选时间段
        StringBuffer sb = new StringBuffer();
        sb.append("		FROM StaSalesPointOper WHERE ");
        sb.append("		operid = ? AND");
        sb.append("		city = ? AND");
        sb.append("		sday >=  STR_TO_DATE(?, '%Y-%m-%d') ");
        sb.append("		AND sday <= STR_TO_DATE(?, '%Y-%m-%d')");
        sb.append("		ORDER BY sday DESC");
//        String hql = "FROM StaSalesPointOper WHERE sday >= ?";
        
        List<StaSalesPointOper> datas = getDAO().find(sb.toString(), params.toArray());
        if(datas != null && !datas.isEmpty()){
        	handlerPointDatas(datas,resp);
        }
        return returnInfo;
	}
	
	
	private void handlerPointDatas(List<StaSalesPointOper> datas,QueSalePointResp resp){
		long total = 0;
		for(StaSalesPointOper oper:datas){
			total += oper.getTotal();
		}
		resp.setPoint(total);
	}
	
	
	private boolean isFirstDayofMonth(){
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(calendar.DAY_OF_MONTH);
		return today == 1;
	}
	
	/**
	 * 获取某个月的第一天(负数为之前的月份,正数为接下来的月份)
	 * @return
	 */
	private Date getPreviousMonthFirstDay(int number){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month+number);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 获取某个月最后一天(负数为之前的月份,正数为接下来的月份)
	 * @return
	 */
	private Date getPrviousMonthLastDay(int number){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, month+number);
		calendar.add(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
	
	/**
	 * 查询排名信息
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo queRankInfo(QueRankInfoReq req,QueRankInfoResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        Date stime = null;
    	Date etime = null;
    	if(StringUtils.isBlank(req.getEtime()) && StringUtils.isBlank(req.getStime())){
        	if(isFirstDayofMonth()){  //今天是本月的第一天,获取时间为上个月的所有绩效总和
        		stime = getPreviousMonthFirstDay(-1);  //开始时间为上个月的第一天
        		etime = getPrviousMonthLastDay(-1);    //结束时间为上个月的最后一天
        	}else{ 
        		stime = getPreviousMonthFirstDay(0); //开始时间为本月第一天
        		etime = new Date();
        	}
        }
    	
    	List params = new ArrayList();
    	params.add(loginInfo.getCity());
        params.add(stime);
        params.add(etime);
        
        
    	StringBuffer sb = new StringBuffer();
        sb.append("		FROM StaSalesPointOper WHERE ");
        sb.append("		city = ? AND");
        sb.append("		sday >=  STR_TO_DATE(?, '%Y-%m-%d') ");
        sb.append("		AND sday <= STR_TO_DATE(?, '%Y-%m-%d')");
        sb.append("		ORDER BY sday DESC");
    	
        List<StaSalesPointOper> datas = getDAO().find(sb.toString(), params.toArray());
        
        if(datas != null && !datas.isEmpty()){
        	hanlderRankSalePoints(datas,resp,loginInfo);
        }else{
        	resp.setNotice("当前暂无排名数据");
        }
        
        return returnInfo;
	}
	
	private void hanlderRankSalePoints(List<StaSalesPointOper> datas,QueRankInfoResp resp,LoginInfo loginInfo) throws Exception{
		Map<Long, List<StaSalesPointOper>> map = new HashMap<Long, List<StaSalesPointOper>>();
		for(StaSalesPointOper oper:datas){
			Long operator = oper.getOperid();
			List<StaSalesPointOper> childList = map.get(operator);
			if(childList == null){
				childList = new ArrayList<StaSalesPointOper>();
				map.put(operator, childList);
			}
			childList.add(oper);
		}
		List<RankSalePointInfo> infos = new ArrayList<RankSalePointInfo>();
		Iterator<Entry<Long, List<StaSalesPointOper>>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Long, List<StaSalesPointOper>> entry = iterator.next();
			RankSalePointInfo info = makeRankInfo(entry.getValue());
			handlerSamePoint(infos,info);
		}
		Collections.sort(infos, pointComparator);
		int position = -1;
		for(int i = 0; i < infos.size() ;i++){
			RankSalePointInfo info = infos.get(i);
			if(info.getOperid().equals(loginInfo.getOperid())){
				position = i;
				break;
			}
		}
		if(position != -1){
			resp.setNotice("当前排名为"+(position+1)+"名");
		}else{
			resp.setNotice("当前客户暂无排名信息");
		}
		if(infos.size() > 10){
			infos.subList(0, 9);
		}
		resp.setDatas(infos);
	}
	
	private void handlerSamePoint(List<RankSalePointInfo> infos,RankSalePointInfo info){
		int position = -1;
		for(int i = 0; i<infos.size(); i++){
			RankSalePointInfo tmp = infos.get(i);
			if(tmp.getPoints() == info.getPoints()){
				position = i;
				break;
			}
		}
		if(position != -1){
			RankSalePointInfo tmp = infos.get(position);
			StringBuffer sb = new StringBuffer();
			sb.append(tmp.getName());
			sb.append(",");
			sb.append(info.getName());
			tmp.setName(sb.toString());
		}else{
			infos.add(info);
		}
	}
	
	private RankSalePointInfo makeRankInfo(List<StaSalesPointOper> datas) throws Exception{
		RankSalePointInfo info = new RankSalePointInfo();
		info.setName(getOperatorName(datas.get(0).getOperid()));
		info.setOperid(datas.get(0).getOperid());
		long points = 0;
		for(StaSalesPointOper data:datas){
			points += data.getTotal();
		}
		info.setPoints(points);
		return info;
	}
	
	private String getOperatorName(Long operid) throws Exception{
		PrvOperator operator = new PrvOperator();
		operator.setId(operid);
		List<PrvOperator> prvOperators = getDAO().find(operator);
		if(prvOperators == null || prvOperators.isEmpty()) return "";
		return prvOperators.get(0).getName();
	}
	
	private Comparator<RankSalePointInfo> pointComparator = new Comparator<RankSalePointInfo>() {

		@Override
		public int compare(RankSalePointInfo next, RankSalePointInfo pre) {
			if(next.getPoints() < pre.getPoints()){
				return 1;
			}
			if(next.getPoints() > pre.getPoints()){
				return -1;
			}
			return 0;
		}
	};
	
	private Comparator<List<QueResultBean>> resultComparator = new Comparator<List<QueResultBean>>() {

		@Override
		public int compare(List<QueResultBean> nexts, List<QueResultBean> pres) {
			QueResultBean next = nexts.get(0);
			QueResultBean pre = pres.get(0);
			if(next.getqNo() > pre.getqNo()){
				return 1;
			}
			return 0;
		}

	};
}
