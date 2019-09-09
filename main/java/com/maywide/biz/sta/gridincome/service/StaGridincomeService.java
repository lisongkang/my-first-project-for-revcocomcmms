package com.maywide.biz.sta.gridincome.service;

import java.util.ArrayList;
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
import com.maywide.biz.inter.pojo.quecustorder.CustordersBO;
import com.maywide.biz.market.entity.Grid;
import com.maywide.biz.sta.gridincome.pojo.ChartDatasBO;
import com.maywide.biz.sta.gridincome.pojo.StaGridincomeBO;
import com.maywide.biz.sta.gridincome.pojo.StaGridincomeChartBO;
import com.maywide.biz.sta.gridincome.pojo.StaGridincomeParamBO;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional
public class StaGridincomeService extends CommonService {
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private ParamService paramService;

    private String getMname(String gcode, String mcode) throws Exception {
        String retMname = "";
        if (StringUtils.isBlank(gcode) || StringUtils.isBlank(mcode)) {
            return retMname;
        }

        retMname = paramService.getMname(gcode, mcode);
        if (StringUtils.isBlank(retMname)) {
            retMname = mcode;
        }

        return retMname;
    }

    public Map<String, String> getCityMap() throws Exception {
        return paramService.getSelectData("PRV_CITY");
    }

    // 查询统计图表
    public StaGridincomeChartBO queGridIncome(StaGridincomeParamBO param)
            throws Exception {
        // TODO Auto-generated method stub

        // StringBuffer sql = new StringBuffer();
        // List paramList = new ArrayList();
        //
        // //初始化统计日期参数--只用执行一次
        // Date sd = DateTimeUtil.parseDate("2000-01-01");
        // Date ed = DateTimeUtil.parseDate("2099-12-31");
        //
        // int l = DateTimeUtil.CompareTo(sd, ed);
        //
        // sql.append(" insert into STA_TIMEDATA_CFG(recid, date, datestr) ");
        // sql.append(" select NULL,");
        // sql.append(" DATE_ADD(STR_TO_DATE('2000-01-01', '%Y-%m-%d'), INTERVAL + ? DAY), ");
        // sql.append(" DATE_FORMAT(DATE_ADD(STR_TO_DATE('2000-01-01', '%Y-%m-%d'),INTERVAL + ? DAY),'%Y%m%d') ");
        // sql.append(" from dual ");
        //
        // for (int i = 0; i < l; i++) {
        // paramList.clear();
        // paramList.add(i);
        // paramList.add(i);
        //
        // getDAO().executeSql(sql.toString(), paramList.toArray());
        //
        // }

        // 0.检查参数
        // 1.根据查询条件生成图例名
        // 2.根据查询条件生成x轴轴标
        // 3.生成统计数据
        // 3.1为每个图例匹配x轴轴标,插入"运算临时表"
        // 3.2将“统计表”右外联接"运算临时表"进数据统计

        checkParam4queGridIncome(param);

//        String bizorderid = getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID")
//                .toString();
        String bizorderid = getBizorderid();

        StaGridincomeChartBO retEchartBo = new StaGridincomeChartBO();

        List<String> legendData = getLegendData4queGridIncome(param, bizorderid);
        retEchartBo.setLegendData(legendData);

        List<String> xAxisData = getXaxisData4queGridIncome(param, bizorderid);
        retEchartBo.setxAxisData(xAxisData);

        retEchartBo
                .setSeriesData(getSeriesData4queGridIncome(param, bizorderid));

        return retEchartBo;
    }

    private List<ChartDatasBO> getSeriesData4queGridIncome(
            StaGridincomeParamBO param, String bizorderid) throws Exception {

        genLegendXaxisTmpdata4queGridIncome(param, bizorderid);

        List<ChartDatasBO> seriesData = queSeriesData4queGridIncome(param,
                bizorderid);

        return seriesData;
    }

    private List<ChartDatasBO> queSeriesData4queGridIncome(
            StaGridincomeParamBO param, String bizorderid) throws Exception {

        CheckUtils.checkEmpty(bizorderid, "业务订单号不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" SELECT (SELECT g.gridname FROM biz_grid_info g WHERE g.gridid = v.gridid) gridname, ");
        sql.append("        gridid, datestr statime, SUM(income) income ");
        sql.append("   FROM ( ");
        sql.append("         SELECT s.objid GRIDID, s.datestr, IFNULL(i.income, 0) income ");
        sql.append("           FROM sta_income i ");
        sql.append("          RIGHT JOIN tmp_stadata s ");
        sql.append("             ON (i.STADATE, i.gridid) = (s.DATESTR, s.objid) ");
        sql.append("          WHERE 1 = 1 ");
        sql.append("            AND s.serialno = ? ");
        paramList.add(bizorderid);

        // 由sta_income表可能无记录，这里不能使用city条件
        // if(StringUtils.isNotBlank(param.getCity())){
        // sql.append("            AND i.city = ? ");
        // paramList.add(param.getCity());
        // }

        sql.append("         ) v ");
        sql.append("  GROUP BY v.GRIDID, v.datestr ");

        sql.append(" )v1  ");

        List<StaGridincomeBO> statimeList = getDAO().find(sql.toString(),
                StaGridincomeBO.class, paramList.toArray());

        List<ChartDatasBO> retList = new ArrayList();
        if (BeanUtil.isListNotNull(statimeList)) {

            List<String> emptyData = getDefaultEmptyData();

            String tmpGridid = "";
            boolean isAllZero = true;// 是否全0标识
            List<String> data = null;
            ChartDatasBO chartDatasBO = null;

            for (StaGridincomeBO tmpStadata : statimeList) {
                if (StringUtils.isBlank(tmpStadata.getGridid())) {
                    throw new BusinessException("数据格式不正确");
                }

                if (StringUtils.isEmpty(tmpGridid)) {
                    // 首次进来赋初值
                    isAllZero = true;
                    tmpGridid = tmpStadata.getGridid();
                    chartDatasBO = new ChartDatasBO();
                    chartDatasBO.setName(tmpStadata.getGridname());
                    chartDatasBO.setType("bar");

                    data = new ArrayList();
                    data.add(tmpStadata.getIncome());
                    if (isNotZerodata(tmpStadata.getIncome())) {
                        isAllZero = false;
                    }
                }

                // 由于已经是有序列表了，这里只需简单判断不相同就已经到下一图例对象了
                if (tmpGridid.equals(tmpStadata.getGridid())) {

                    data.add(tmpStadata.getIncome());
                    if (isNotZerodata(tmpStadata.getIncome())) {
                        isAllZero = false;
                    }
                    continue;
                }

                if (!isAllZero) {
                    chartDatasBO.setData(data);
                    retList.add(chartDatasBO);
                } else {
                    chartDatasBO.setData(emptyData);
                    retList.add(chartDatasBO);
                }

                // 初使化，为下一图例做准备
                isAllZero = true;
                tmpGridid = tmpStadata.getGridid();
                chartDatasBO = new ChartDatasBO();
                chartDatasBO.setName(tmpStadata.getGridname());
                chartDatasBO.setType("bar");

                data = new ArrayList();
                data.add(tmpStadata.getIncome());
                if (isNotZerodata(tmpStadata.getIncome())) {
                    isAllZero = false;
                }
            }

            // 最后一组数据
            if (!isAllZero) {
                chartDatasBO.setData(data);
                retList.add(chartDatasBO);
            } else {
                chartDatasBO.setData(emptyData);
                retList.add(chartDatasBO);
            }

        }

        return retList;

    }

    /**
     * 为图例生成空数据
     */
    private List<String> getDefaultEmptyData() throws Exception {

        List<String> retEmptyData = new ArrayList();

        retEmptyData.add("-");
        return retEmptyData;
    }

    private boolean isNotZerodata(String datastr) {
        if (StringUtils.isNotBlank(datastr) && !"0.0".equals(datastr)) {
            return true;
        }
        return false;
    }

    /*
     * 为每个图例匹配x轴轴标,插入"运算临时表"
     */
    private void genLegendXaxisTmpdata4queGridIncome(
            StaGridincomeParamBO param, String bizorderid) throws Exception {

        CheckUtils.checkNull(param, "请求参数不能为空");
        CheckUtils.checkEmpty(bizorderid, "业务订单号不能为空");
        if (BeanUtil.isListNull(param.getGridids())) {
            // throw new BusinessException("统计网格不能为空");
        }
        CheckUtils.checkEmpty(param.getStime(), "统计开始时间不能为空");
        CheckUtils.checkEmpty(param.getEtime(), "统计结束时间不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" INSERT INTO tmp_stadata ");
        sql.append("   (recid, objid, datestr, serialno) ");
        sql.append("   SELECT NULL recid, g.GRIDID objid, c.DATESTR, ? serialno ");
        paramList.add(bizorderid);
        sql.append("     FROM biz_grid_info g, STA_TIMEDATA_CFG c ");
        sql.append("    WHERE 1 = 1 ");
        sql.append("      AND c.DATESTR >= ? ");
        paramList.add(param.getStime().replace("-", ""));
        sql.append("      AND c.DATESTR <= ? ");
        paramList.add(param.getEtime().replace("-", ""));

        if (StringUtils.isNotBlank(param.getCity())) {
            sql.append("            AND g.city = ? ");
            paramList.add(param.getCity());
        }

        if (BeanUtil.isListNotNull(param.getGridids())) {
            sql.append("    and g.GRIDID in (? ");
            paramList.add(param.getGridids().get(0));

            for (int i = 1; i < param.getGridids().size(); i++) {
                sql.append(", ?");
                paramList.add(param.getGridids().get(i));
            }
            sql.append("    ) ");
        }

        sql.append("    ORDER BY g.GRIDID, c.DATESTR ");

        getDAO().executeSql(sql.toString(), paramList.toArray());

    }

    /*
     * 根据查询条件生成x轴轴标
     */
    private List<String> getXaxisData4queGridIncome(StaGridincomeParamBO param,
            String bizorderid) throws Exception {
        CheckUtils.checkNull(param, "请求参数不能为空");
        CheckUtils.checkEmpty(param.getStime(), "统计开始时间不能为空");
        CheckUtils.checkEmpty(param.getEtime(), "统计结束时间不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" select * from (");

        sql.append(" select distinct date_format(t.datestr,'%Y-%m-%d') statime ");
        sql.append("   from STA_TIMEDATA_CFG t ");
        sql.append("  where 1 = 1 ");
        sql.append("    and t.DATESTR >= ? ");
        paramList.add(param.getStime().replace("-", ""));
        sql.append("    and t.DATESTR <= ? ");
        paramList.add(param.getEtime().replace("-", ""));

        sql.append("    ) v ");

        List<StaGridincomeBO> statimeList = getDAO().find(sql.toString(),
                StaGridincomeBO.class, paramList.toArray());

        List<String> retList = new ArrayList();
        if (BeanUtil.isListNotNull(statimeList)) {
            for (StaGridincomeBO tmpStadata : statimeList) {
                retList.add(tmpStadata.getStatime());
            }
        }

        return retList;
    }

    /*
     * 根据查询条件生成图例名
     */
    private List<String> getLegendData4queGridIncome(
            StaGridincomeParamBO param, String bizorderid) throws Exception {
        CheckUtils.checkNull(param, "请求参数不能为空");
        if (BeanUtil.isListNull(param.getGridids())) {
            // throw new BusinessException("统计网格不能为空");
        }

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" select * from (");

        sql.append(" SELECT distinct g.gridname gridname ");
        sql.append("   FROM biz_grid_info g ");
        sql.append("  where 1 = 1 ");

        if (StringUtils.isNotBlank(param.getCity())) {
            sql.append("            AND g.city = ? ");
            paramList.add(param.getCity());
        }

        if (BeanUtil.isListNotNull(param.getGridids())) {
            sql.append("    and g.GRIDID in (? ");
            paramList.add(param.getGridids().get(0));

            for (int i = 1; i < param.getGridids().size(); i++) {
                sql.append(", ?");
                paramList.add(param.getGridids().get(i));
            }
            sql.append("    ) ");
        }

        sql.append("    ) v");

        List<Grid> gridList = getDAO().find(sql.toString(), Grid.class,
                paramList.toArray());

        List<String> retList = new ArrayList();
        if (BeanUtil.isListNotNull(gridList)) {
            for (Grid grid : gridList) {
                retList.add(grid.getGridname());
            }
        }

        return retList;
    }

    private void checkParam4queGridIncome(StaGridincomeParamBO param)
            throws Exception {
        CheckUtils.checkNull(param, "查询条件不能为空");
        // CheckUtils.checkEmpty(param.getStatype(), "统计方式不能为空");
        if (BeanUtil.isListNull(param.getGridids())) {
            // throw new BusinessException("统计网格不能为空");
        }
        CheckUtils.checkEmpty(param.getStime(), "统计开始时间不能为空");
        CheckUtils.checkEmpty(param.getEtime(), "统计结束时间不能为空");

    }

    // 查询统计列表
    public Object queGridIncomeList(StaGridincomeParamBO param,
            Pageable pageable) throws Exception {
        CheckUtils.checkNull(param, "查询条件不能为空");
        // CheckUtils.checkEmpty(param.getStatype(), "统计方式不能为空");
        if (BeanUtil.isListNull(param.getGridids())) {
            // throw new BusinessException("统计网格不能为空");
        }
        CheckUtils.checkEmpty(param.getStime(), "统计开始时间不能为空");
        CheckUtils.checkEmpty(param.getEtime(), "统计结束时间不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" SELECT (SELECT g.gridname FROM biz_grid_info g WHERE g.gridid = v.gridid) gridname, ");
        sql.append("        gridid, statime, SUM(income) income ");
        sql.append("   FROM ( ");
        sql.append("         SELECT i.GRIDID, i.STADATE statime, IFNULL(i.income, 0) income ");
        sql.append("           FROM sta_income i ");
        sql.append("          WHERE 1 = 1 ");
        sql.append("    and i.STADATE >= ? ");
        paramList.add(param.getStime().replace("-", ""));
        sql.append("    and i.STADATE <= ? ");
        paramList.add(param.getEtime().replace("-", ""));

        if (StringUtils.isNotBlank(param.getCity())) {
            sql.append("            AND i.city = ? ");
            paramList.add(param.getCity());
        }

        if (BeanUtil.isListNotNull(param.getGridids())) {
            sql.append("    and i.GRIDID in (? ");
            paramList.add(param.getGridids().get(0));

            for (int i = 1; i < param.getGridids().size(); i++) {
                sql.append(", ?");
                paramList.add(param.getGridids().get(i));
            }
            sql.append("     ) ");
        }

        sql.append("         ) v ");
        sql.append("  GROUP BY v.GRIDID, v.statime ");

        sql.append(" )v1  ");

        org.springframework.data.domain.Page retPage = findPageResult(pageable,
                sql.toString(), StaGridincomeBO.class, paramList.toArray());

        return retPage;
    }

}
