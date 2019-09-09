package com.maywide.biz.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.survey.dao.StaAnalysisDao;
import com.maywide.biz.survey.entity.StaAnalysis;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class StaAnalysisService extends BaseService<StaAnalysis,Long> {

	@Autowired
	private StaAnalysisDao staAnalysisDao;
    
	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseDao<StaAnalysis, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return staAnalysisDao;
	}

	public List<StaAnalysis> findEchartDataBySidAndQid(String sid, String qid) {
		StringBuffer sql = new StringBuffer();
		List<StaAnalysis> result = null;
		List<Object> paramList = new ArrayList<Object>();
		sql.append("SELECT * FROM (SELECT * FROM sta_analysis s WHERE 1=1 AND s.sid =? AND s.qid=? ) v");
		paramList.add(sid);
		paramList.add(qid);
		try {
		   result =	persistentService.find(sql.toString(),StaAnalysis.class, paramList.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
