package com.maywide.biz.survey.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.survey.dao.BizQuestionListDao;
import com.maywide.biz.survey.entity.BizQaRelation;
import com.maywide.biz.survey.entity.BizQuestionList;
import com.maywide.biz.survey.pojo.BizQuestionBO;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional
public class BizQuestionListService extends BaseService<BizQuestionList,Long> {

	@Autowired
	private BizQuestionListDao bizQuestionListDao;
	
	@Autowired
	private BizQaRelationService bizQaRelationService;
	
	@Autowired
	private PersistentService persistentService;
	    
	
	@Override
	protected BaseDao<BizQuestionList, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return bizQuestionListDao;
	}

	public void saveQuestionByIsOpen(BizQuestionBO bizQuestionBO) {
		if(bizQuestionBO == null){
			return;
		}
		String isOpen = bizQuestionBO.getIsopen();
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		BizQuestionList entity = new BizQuestionList();
		entity.setQcontent(bizQuestionBO.getQcontent());
		entity.setIsopen(bizQuestionBO.getIsopen());
		entity.setIntime(new Date());
		entity.setOperator(loginInfo.getOperid());
		//根据是否为开放式进行数据插入
		if(isOpen.equals("Y")){
			entity.setIsok(bizQuestionBO.getIsok());
			bizQuestionListDao.save(entity);
		}else if(isOpen.equals("N")){
			entity.setIsonly(bizQuestionBO.getIsonly());
			entity =  bizQuestionListDao.save(entity);
			List<BizQaRelation> list = bizQuestionBO.getData();
			for(BizQaRelation bo : list){
				bo.setQid(entity.getId());
				bo.setQnext(1L);
				bo.setIntime(new Date());
				bo.setOperator(loginInfo.getOperid());
			}
			bizQaRelationService.save(list);
		}
		 
		
	}

	public PageImpl queQuestionLists(String queryParam,String intime, Pageable pageable) throws Exception {
	
		PageImpl pageResult = null;
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT q.qid id,");
        sql.append("(SELECT o.name FROM prv_operator o WHERE o.operid = q.`operator`) opername,");
        sql.append(" q.*"); 
        sql.append(" FROM biz_question_list q ");
        sql.append("  WHERE 1=1  "); 
        
        if(StringUtils.isNotBlank(queryParam)){
        	 sql.append(" AND (q.qcontent LIKE ? OR (SELECT o.name FROM prv_operator o WHERE o.operid = q.`operator`) LIKE ?)");
        	 paramList.add("%" + queryParam + "%");
             paramList.add("%" + queryParam + "%");
        }
        
        if(StringUtils.isNotBlank(intime)){
        	 sql.append(" AND (q.intime LIKE ?)");
        	 paramList.add("%" + intime + "%");
        }
        sql.append("order by q.intime desc ");
        sql.append(" ) v ");
     
        persistentService.clear();
		page = persistentService.find(page, sql.toString(), BizQuestionList.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<BizQuestionList> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<BizQuestionList> resultList = new ArrayList<BizQuestionList>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
	}

	//通过问卷id获取问题包括问题的答案
	public List<BizQuestionList> getQuestionListBySid(String sid) throws Exception {
		List<BizQuestionList> result = null;
		if(CheckUtils.checkNull(sid,"问卷ID不能为空"));
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT q.qid id,");
        sql.append("(SELECT o.name FROM prv_operator o WHERE o.operid = q.`operator`) opername,");
        sql.append(" r.qno qno,"); 
        sql.append(" q.*"); 
        sql.append(" FROM biz_sq_relation r ,biz_question_list q ");
        sql.append("  WHERE 1=1  "); 
        sql.append(" AND r.qid = q.qid");
     
        sql.append(" AND r.sid =?");
        paramList.add(sid);
        
        sql.append(" ORDER BY r.qno");
        sql.append(" ) v ");
		//SELECT * FROM biz_sq_relation r ,biz_question_list q WHERE r.qid = q.qid  AND r.sid =1 ORDER BY r.`qno` ";
		
        result = persistentService.find(sql.toString(), BizQuestionList.class, paramList.toArray());
		if(result.size()>0){
			for(BizQuestionList entity : result){
				 List<BizQaRelation>  answers = bizQaRelationService.findAnSwerByQid(entity.getId()+"");
				 entity.setAnswerList(answers);
			}
		     
		}
        return result;
	}

	public boolean isExistSuveryContainQue(Long qid) throws Exception {
		boolean flag = false;
	    String sql = "SELECT * FROM biz_sq_relation b WHERE b.qid=? LIMIT 1;";
	    List<Object> paramList = new ArrayList<Object>();
	    paramList.add(qid);
	   
		List<BizQuestionList> list =  persistentService.find(sql, BizQuestionList.class, paramList.toArray());
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}

}
