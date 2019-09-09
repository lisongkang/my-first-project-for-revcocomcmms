package com.maywide.biz.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.survey.dao.BizQaRelationDao;
import com.maywide.biz.survey.entity.BizQaRelation;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class BizQaRelationService extends BaseService<BizQaRelation,Long> {

	@Autowired
	private BizQaRelationDao bizQaRelationDao;
	
    @Autowired
	private PersistentService persistentService;
	@Override
	protected BaseDao<BizQaRelation, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return bizQaRelationDao;
	}
	public List<BizQaRelation> findAnSwerByQid(String qid) {
		StringBuffer sql = new StringBuffer();
		List<BizQaRelation> result = null;
		List<Object> paramList = new ArrayList<Object>();
		sql.append("SELECT * FROM (SELECT q.aid id,q.* FROM biz_qa_relation q WHERE q.qid=? ORDER BY q.acode ) v");
		paramList.add(qid);
		try {
		   result =	persistentService.find(sql.toString(), BizQaRelation.class, paramList.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public void deleteByQid(Long id) throws Exception {
		String sql = "DELETE FROM biz_qa_relation  WHERE qid = "+id;
		persistentService.executeSql(sql, null);
	}


	

}
