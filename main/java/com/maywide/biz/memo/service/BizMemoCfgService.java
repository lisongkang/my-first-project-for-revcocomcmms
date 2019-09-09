package com.maywide.biz.memo.service;

import com.maywide.biz.memo.dao.BizMemoCfgDao;
import com.maywide.biz.memo.entity.BizMemoCfg;
import com.maywide.biz.memo.pojo.BizMemoCfgParamBo;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzy
 */
@Service
@Transactional
public class BizMemoCfgService extends BaseService<BizMemoCfg, Long> {

    @Autowired
    private BizMemoCfgDao bizMemoCfgDao;
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<BizMemoCfg, Long> getEntityDao() {
        return bizMemoCfgDao;
    }


    public PageImpl<BizMemoCfg> queryBizSurveyList(BizMemoCfgParamBo param, Pageable pageable) throws Exception {
        org.springframework.data.domain.PageImpl<BizMemoCfg> pageResult = null;

        Page<BizMemoCfg> page = new Page<BizMemoCfg>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();

        sql.append("SELECT * FROM ("); // 要在最外层加select *，否则框架本身根据构造的语句查总记录数时的sql会错误
        sql.append("SELECT t.id id,");
        sql.append("t.city,");
        sql.append("t.areas,");
        sql.append("t.opcodes,");
        sql.append("t.memotxt,");
        sql.append("t.pri,");
        sql.append("t.intime,");
        sql.append("t.operator,");
        sql.append(" (SELECT GROUP_CONCAT(s.mname) FROM prv_sysparam s WHERE s.gcode='PRV_CITY' AND FIND_IN_SET(s.mcode,t.city)) citynames,");
        sql.append(" (SELECT GROUP_CONCAT(r.name) FROM prv_area r WHERE FIND_IN_SET(r.areaid,t.areas)) areanames,");
        sql.append(" (SELECT o.name FROM prv_operator o WHERE o.operid = t.operator) opername,");
        sql.append(" (SELECT GROUP_CONCAT(s.mname) FROM prv_sysparam s WHERE s.gcode='BIZ_OPCODE' AND FIND_IN_SET(s.mcode,t.opcodes)) opnames ");
        sql.append("FROM biz_memo_cfg t ");

        sql.append(" WHERE 1 = 1 ");

        if (StringUtils.isNotEmpty(param.getCity())) {
            sql.append(" AND t.city = ?");
            paramList.add(param.getCity());

        }

        if (StringUtils.isNotEmpty(param.getOpcodes())) {
            sql.append(" AND t.opcodes = ?");
            paramList.add(param.getOpcodes());
        }
        if (StringUtils.isNotEmpty(param.getMemotxt())) {
            sql.append(" AND t.memotxt like ? ");
            paramList.add("%"+param.getMemotxt()+"%");
        }

        sql.append("order by t.intime desc ) v ");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), BizMemoCfg.class, paramList.toArray());

        if (page != null && page.getResult() != null) {
            int total = page.getTotalCount();
            List<BizMemoCfg> resultList = page.getResult();
            pageResult = new PageImpl(resultList, pageable, total);
        } else {
            List<BizMemoCfg> resultList = new ArrayList<BizMemoCfg>();
            pageResult = new PageImpl(resultList, pageable, 0);
        }

        return pageResult;
    }


    public void deleteBatch(String[] ids) throws Exception {
        for (int i = 0, size = ids.length; i < size; i++) {
            persistentService.executeSql("DELETE FROM biz_memo_cfg WHERE id=?",
                    new Object[] { Long.parseLong(ids[i]) });
        }
    }
}
