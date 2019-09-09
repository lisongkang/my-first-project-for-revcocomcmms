package com.maywide.biz.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.survey.dao.BizSurveyListDao;
import com.maywide.biz.survey.entity.BizSurveyList;
import com.maywide.biz.survey.pojo.BizSurveyListParamBo;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class BizSurveyListService extends BaseService<BizSurveyList, Long> {

    @Autowired
    private BizSurveyListDao  bizSurveyListDao;

    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<BizSurveyList, Long> getEntityDao() {
        return bizSurveyListDao;
    }

    public PageImpl<BizSurveyList> queryBizSurveyList(BizSurveyListParamBo param, Pageable pageable) throws Exception {
        PageImpl<BizSurveyList> pageResult = null;

        Page<BizSurveyList> page = new Page<BizSurveyList>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();

        sql.append("SELECT * FROM ("); // 要在最外层加select *，否则框架本身根据构造的语句查总记录数时的sql会错误
        sql.append("SELECT t.sid id,");
        sql.append("t.sname,");
        sql.append("t.intime,");
        sql.append("t.operator,");
        sql.append("t.isreal,");
        sql.append("t.status,");
        sql.append(" (SELECT GROUP_CONCAT(s.mname) FROM prv_sysparam s WHERE s.gcode='PRV_CITY' AND FIND_IN_SET(s.mcode,t.city)) citynames,");
        sql.append(" (SELECT GROUP_CONCAT(r.name) FROM prv_area r WHERE FIND_IN_SET(r.areaid,t.areaid)) areanames,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid = t.operator) opername,");
        sql.append(" t.memo ");
        sql.append("FROM biz_survey_list t ");

        sql.append(" WHERE 1 = 1 ");

        if (param.getCityid() != null && !"".equals(param.getCityid())) {
            sql.append(" AND t.city = ?");
            paramList.add(param.getCityid());

        }

        if (param.getAreaids() != null && !"".equals(param.getAreaids())) {
            String[] areaArr = param.getAreaids().split(",");
            sql.append("AND (");
            for (int i = 0; i < areaArr.length; i++) {
                if (i != 0) {
                    sql.append(" OR ");
                }
                sql.append("FIND_IN_SET(?,t.areaid)");
                paramList.add(areaArr[i].trim());
            }
            sql.append(" OR t.areaid = '*')");
        }
        if (param.getStatus() != null && !param.getStatus().trim().equals("")) {
            sql.append(" AND t.status = ?");
            paramList.add(param.getStatus());
        }
        if (param.getIsrealName() != null && !param.getIsrealName().equals("")) {
            sql.append(" AND t.isreal = ?");
            paramList.add(param.getIsrealName());
        }

        sql.append("order by t.intime desc ) v ");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), BizSurveyList.class, paramList.toArray());

        if (page != null && page.getResult() != null) {
            int total = page.getTotalCount();
            List<BizSurveyList> resultList = page.getResult();
            pageResult = new PageImpl(resultList, pageable, total);
        } else {
            List<BizSurveyList> resultList = new ArrayList<BizSurveyList>();
            pageResult = new PageImpl(resultList, pageable, 0);
        }

        return pageResult;
    }

    public void bindSurveyQuestion(Long sid, String qids) throws Exception {
        String[] qidStrs = qids.split(",");
        for (int i = 0, size = qidStrs.length; i < size; i++) {
            persistentService.executeSql("INSERT INTO biz_sq_relation VALUES(?,?,?)",
                    new Object[] { Long.parseLong(qidStrs[i]), Long.valueOf(i + 1L), sid });
        }
    }

    public void deleteSurveyQuestion(String[] sidStrs) throws Exception {
        for (int i = 0, size = sidStrs.length; i < size; i++) {
            persistentService.executeSql("DELETE FROM biz_sq_relation WHERE sid=?",
                    new Object[] { Long.parseLong(sidStrs[i]) });
        }
    }

    public void stopSurveyBySid(String sid) throws Exception {
        BizSurveyList surveyList = (BizSurveyList) persistentService.find(BizSurveyList.class, Long.parseLong(sid));
        surveyList.setStatus(1);
        persistentService.update(surveyList);
    }
}
