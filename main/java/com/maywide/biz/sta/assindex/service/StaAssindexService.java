package com.maywide.biz.sta.assindex.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.sta.assindex.pojo.StaAssindexBO;
import com.maywide.biz.sta.assindex.pojo.StaAssindexParamBO;
import com.maywide.biz.sta.gridincome.pojo.StaGridincomeBO;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;

@Service
@Transactional
public class StaAssindexService extends CommonService {
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private ParamService paramService;

    public Map<String, String> getCityMap() throws Exception {
        return paramService.getSelectData("PRV_CITY");
    }

    public Map<String, String> getAssparamMap() throws Exception {
        return paramService.getSelectData("BIZ_CHECKPARAM");
    }

    public List getAssMap(String city, String assparam) throws Exception {
        CheckUtils.checkEmpty(city, "地市不能为空");
        CheckUtils.checkEmpty(assparam, "考核方案类型不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" SELECT s.ASSID mcode, s.ASSCONTENT mname ");
        sql.append("   FROM ass_index_store s ");
        sql.append("  WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(city)) {
            sql.append("    AND s.CITY = ? ");
            paramList.add(city);
        }
        if (StringUtils.isNotBlank(assparam)) {
            sql.append("    AND s.assparam = ? ");
            paramList.add(assparam);
        }

        sql.append(" )v1  ");

        List<PrvSysparam> assList = getDAO().find(sql.toString(),
                PrvSysparam.class, paramList.toArray());

        if (BeanUtil.isListNull(assList)) {
            assList = new ArrayList();
        }

        return assList;

    }
    
    public List getAssListByCity(String city) throws Exception {
        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");
        sql.append(" SELECT s.ASSID mcode, s.ASSCONTENT mname ");
        sql.append(" FROM ass_index_store s ");
        sql.append(" WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(city)) {
            sql.append(" AND s.CITY = ? ");
            paramList.add(city);
        }
        
        String curDate = DateUtils.getFormatDateString(new Date(), "yyyy-MM-dd");
        sql.append(" AND s.expdate >= STR_TO_DATE(?, '%Y-%m-%d')");
        paramList.add(curDate);
        
        sql.append(" )v1  ");
        List<PrvSysparam> assList = getDAO().find(sql.toString(), PrvSysparam.class, paramList.toArray());
        if (BeanUtil.isListNull(assList)) {
            assList = new ArrayList();
        }
        return assList;
    }

    // 查询统计列表
    public org.springframework.data.domain.Page queAssindexList(
            StaAssindexParamBO param, Pageable pageable) throws Exception {
        CheckUtils.checkNull(param, "查询条件不能为空");
        // if (BeanUtil.isListNull(param.getGridids())) {
        // throw new BusinessException("统计网格不能为空");
        // }
        CheckUtils.checkEmpty(param.getStime(), "统计开始时间不能为空");
        CheckUtils.checkEmpty(param.getEtime(), "统计结束时间不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" select s.STADATE, ");
        sql.append("        s.ASSID, ");
        sql.append("        code2name((SELECT t1.ASSPARAM FROM ass_index_store t1 WHERE t1.assid = s.ASSID), 'BIZ_CHECKPARAM') assparamname, ");
        sql.append("        (select a.asscontent from ass_index_store a where a.assid = s.ASSID) assname, ");
        sql.append("        s.GRIDID, ");
        sql.append("        (select g.gridname from biz_grid_info g where g.gridid = s.gridid) gridname, ");
        sql.append("        s.SDATE, ");
        sql.append("        s.EDATE, ");
        sql.append("        s.assnum, ");
        sql.append("        s.COMNUM, ");
        sql.append("        s.DIFFNUM ");
        sql.append("   from ASS_INDEX_STAT s ");
        sql.append("  where 1 = 1 ");

        if (StringUtils.isNotBlank(param.getCity())) {
            sql.append("    and exists (select 1 from biz_grid_info a  ");
            sql.append("            where a.gridid = s.gridid  ");
            sql.append("              and a.city = ? ) ");

            paramList.add(param.getCity());
        }

        if (StringUtils.isNotBlank(param.getAssparam())) {
            sql.append("    and exists (select 1 from ass_index_store a  ");
            sql.append("            where a.assid = s.ASSID  ");
            sql.append("              and a.assparam = ? ) ");

            paramList.add(param.getAssparam());
        }

        if (StringUtils.isNotBlank(param.getAssid())) {
            sql.append("    and s.ASSID = ? ");
            paramList.add(param.getAssid());
        }

        sql.append("    and s.STADATE >= STR_TO_DATE(?, '%Y-%m-%d')  ");
        paramList.add(param.getStime());
        sql.append("    and s.STADATE <= STR_TO_DATE(?, '%Y-%m-%d')  ");
        paramList.add(param.getEtime());

        if (BeanUtil.isListNotNull(param.getGridids())) {
            sql.append("    and s.GRIDID in (? ");
            paramList.add(param.getGridids().get(0));

            for (int i = 1; i < param.getGridids().size(); i++) {
                sql.append(", ?");
                paramList.add(param.getGridids().get(i));
            }
            sql.append("     ) ");
        }

        sql.append(" )v1  ");

        org.springframework.data.domain.Page retPage = findPageResult(pageable,
                sql.toString(), StaAssindexBO.class, paramList.toArray());

        return retPage;
    }

}
