package com.maywide.biz.ass.assdata.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maywide.biz.ass.assdata.pojo.CustAnswer;
import com.maywide.biz.survey.entity.StaAnalysis;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;

public class UpdateStaAnalysisJob implements Job{

	PersistentService persistenService;
	
	static final Log log = LogFactory.getLog(UpdateStaAnalysisJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			
			log.error("execute  =====>"+getClass().getSimpleName()+"================================");
			persistenService = SpringContextHolder.getBean(PersistentService.class);
			
			String sql = "SELECT A.QID qId,A.SID sId,A.ANSWERTYPE aType,A.ANSWER answer,A.OPERATOR operator FROM BIZ_CUST_ANS_LIST A ";
			
			List<CustAnswer> answers = persistenService.find(sql, CustAnswer.class);
			if(answers != null && !answers.isEmpty()){
				handlerCustAnswer(answers);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	
	
	private void handlerCustAnswer(List<CustAnswer> answers) throws Exception{
		Map<String, List<CustAnswer>> questMap = new HashMap<String, List<CustAnswer>>();//获得题目分类集合
		for(CustAnswer answer:answers){
			if(!answer.getaType().toString().equals("0")){
				continue;
			}
			//以题目,问卷为分组依据获得某一道题目的回答人数
			String mark = answer.getqId().toString()+"_"+answer.getsId().toString();
			List<CustAnswer> answerList = questMap.get(mark);
			if(answerList == null){
				answerList = new ArrayList<CustAnswer>();
				questMap.put(mark, answerList);
			}
			answerList.add(answer);
		}
		
		Map<String, List<CustAnswer>> answersMap = new HashMap<String, List<CustAnswer>>();//以题目,问卷,答案为分组依据获得某一道题目的回答人数
		for(CustAnswer answer:answers){
			if(!answer.getaType().toString().equals("0")){
				continue;
			}
			String mark = answer.getqId().toString()+"_"+answer.getsId().toString()+"_"+answer.getAnswer();
			List<CustAnswer> answerList = answersMap.get(mark);
			if(answerList == null){
				answerList = new ArrayList<CustAnswer>();
				answersMap.put(mark, answerList);
			}
			answerList.add(answer);
		}
		List<StaAnalysis> analysisList = getStaAnalysisList(questMap,answersMap);
		updateStaAnalysis(analysisList);
	}
	
	
	
	
	/**
	 * 更新或插入新数据
	 * @param analysisList 
	 * @throws Exception
	 */
	private void updateStaAnalysis(List<StaAnalysis> analysisList) throws Exception{
		for(StaAnalysis analysis:analysisList){
			StaAnalysis tmp = new StaAnalysis();
			tmp.setAcode(analysis.getAcode());
			tmp.setSid(analysis.getSid());
			tmp.setQid(analysis.getQid());
			List<StaAnalysis> datas = persistenService.find(tmp);
			if(datas != null && !datas.isEmpty()){
				tmp = datas.get(0);
				tmp.setAnumber(analysis.getAnumber());
				tmp.setUpdatetime(analysis.getUpdatetime());
				tmp.setTotal(analysis.getTotal());
				persistenService.update(tmp);
			}else{
				persistenService.save(analysis);
			}
		}
	}
	
	
	private List<StaAnalysis> getStaAnalysisList(Map<String, List<CustAnswer>> quesMap,Map<String, List<CustAnswer>> answersMap){
		List<StaAnalysis> list = new ArrayList<StaAnalysis>();
		Iterator<Entry<String, List<CustAnswer>>> iterator = answersMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, List<CustAnswer>> entry = iterator.next();
			String key = entry.getKey();//获取到题目跟答案
			Set<String> queSet = quesMap.keySet();
			String queKey = null;
			for(String str:queSet){
				if(key.contains(str)){
					queKey = str;
					break;
				}
			}
			if(queKey != null){
				List<CustAnswer> queList = quesMap.get(queKey);
				if(queList != null){
					List<CustAnswer> answerlist = entry.getValue();
					if(!answerlist.isEmpty()){
						CustAnswer answer = answerlist.get(0);
						if(answer.getAnswer().length() > 1){
							String[] answerStrs = answer.getAnswer().split(",");
							for(String str:answerStrs){
								StaAnalysis analysis = new StaAnalysis();
								analysis.setAcode(str);
								analysis.setAnumber(Long.parseLong(answerlist.size()+""));
								analysis.setIntime(new Date());
								analysis.setOperator(answer.getOperator());
								analysis.setQid(answer.getqId());
								analysis.setSid(answer.getsId());
								analysis.setTotal(Long.parseLong(queList.size()+""));
								analysis.setUpdatetime(new Date());
								int position = -1;
								for(StaAnalysis an:list){
									if(an.getQid().equals(analysis.getQid()) &&
											an.getSid().equals(analysis.getSid())
											&& an.getAcode().equals(analysis.getAcode())){
										position = list.indexOf(an);
											break;
									}
								}
								if(position != -1){
									list.get(position).setAnumber(list.get(position).getAnumber()+analysis.getAnumber());
								}else{
									list.add(analysis);
								}
							}
						}else{
							StaAnalysis analysis = new StaAnalysis();
							analysis.setAcode(answer.getAnswer());
							analysis.setAnumber(Long.parseLong(answerlist.size()+""));
							analysis.setIntime(new Date());
							analysis.setOperator(answer.getOperator());
							analysis.setQid(answer.getqId());
							analysis.setSid(answer.getsId());
							analysis.setTotal(Long.parseLong(queList.size()+""));
							analysis.setUpdatetime(new Date());
							int position = -1;
							for(StaAnalysis an:list){
								if(an.getQid().equals(analysis.getQid()) &&
										an.getSid().equals(analysis.getSid())
										&& an.getAcode().equals(analysis.getAcode())){
									position = list.indexOf(an);
										break;
								}
							}
							if(position != -1){
								list.get(position).setAnumber(list.get(position).getAnumber()+analysis.getAnumber());
							}else{
								list.add(analysis);
							}
						}
					}
				}
			}
		}
		return list;
	}	
	
	/**
	 * 获取相同题目下不同答案的人数
	 * @param datas
	 * @return
	 */
	private List<Map<String, List<CustAnswer>>> getAnswerGroup(Map<String, List<CustAnswer>> datas){
		
		List<Map<String, List<CustAnswer>>> groupDatas = new ArrayList<Map<String,List<CustAnswer>>>();
		
		Iterator<Entry<String, List<CustAnswer>>> iterator =  datas.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, List<CustAnswer>> entry = iterator.next();
			Map<String, List<CustAnswer>> answerList = getAnswerList(entry.getValue());
			groupDatas.add(answerList);
		}
		return groupDatas;
	}

	/**
	 * 获取相同题目下相同答案的列表数组
	 * @param datas
	 * @return
	 */
	private Map<String, List<CustAnswer>> getAnswerList(List<CustAnswer> datas){
		Map<String, List<CustAnswer>> map = new HashMap<String, List<CustAnswer>>();
		for(CustAnswer data:datas){
			String mark = data.getqId().toString()+data.getsId().toString()+data.getAnswer();
			List<CustAnswer> answerList = map.get(mark);
			if(answerList == null){
				answerList = new ArrayList<CustAnswer>();
				map.put(mark, answerList);
			}
			answerList.add(data);
		}
		return map;
	}
}
