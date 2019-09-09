package com.maywide.biz.sta.marketingstatistic.service;

import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.sta.marketingstatistic.bo.StaMarketStatisticParamBO;
import com.maywide.biz.sta.marketingstatistic.bo.StaMarketingStatisticBO;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StaMarketingStatisticService extends CommonService {

    private Logger log = LoggerFactory.getLogger(StaMarketingStatisticService.class);

    @Autowired
    private PersistentService persistentService;

    /**
     * #营销业务单统计查询
     * 技术上，这个统计的实现比较绕，MySQL数据库中有一个存储过程proc_add_statistic_marketing，
     * 当天凌晨执行并统计出前一晚的数据，并插入到statistic_marketing表中，该存储过程的定期执行
     * 是在bash shell脚本调用并设置的
     *
     * 业务上，网格名称与网格编码只有备注作用，不具有与每一条订单统计数据所在的网格一一对应关系
     * 每个网格经理有多个网格名称与网格编码
     *
     * 部署上，这个功能设定在2019年1月1日开始统计，并有具体的SQL脚本，在部署时需要执行shell脚本与sql脚本
     *
     * ## @TODO 存在问题
     * 开发中发现测试库中部分网格经理有两个相同的网格名称，等待进一步验证
     * 这个实现没有做接口层面的权限校验，对于项目其他地方有无校验校验尚且等待验证
     */
    public PageImpl<StaMarketingStatisticBO> querySaleOrderList(StaMarketStatisticParamBO param, Pageable pageable)
            throws Exception {

        // 入参校验
        CheckUtils.checkNull(param, "查询条件不能为空");
        CheckUtils.checkNull(param.getCity(), "地市不能为空");
        CheckUtils.checkNull(param.getStime(), "统计开始时间不能为空");
        CheckUtils.checkNull(param.getEtime(), "统计结束时间不能为空");

        // 分页信息
        PageImpl<StaMarketingStatisticBO> pageResult = null;
        Page<StaMarketingStatisticBO> page = new Page<StaMarketingStatisticBO>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        // 查询网格名称
        persistentService.clear();
        String finGridNameSql = "select gridname from biz_grid_info where gridid = ?";
        String gridName = null;
        if (param.getGridlist() !=null&& !param.getGridlist().isEmpty()){
            gridName = (String) persistentService.findUniqueObject(finGridNameSql,param.getGridlist().get(0));
            log.info("----------->  {}",gridName);
        }

        // 拼接查询统计报表数据SQL
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT * FROM (");
        sql.append("SELECT statistic_date as statisticDate,");
        sql.append("areaid as areaid,");
        sql.append("(select name from prv_area where areaid = o.areaid) as areaName,");
        sql.append("gridname as gridName,");
        sql.append("gridcode as gridCode,");
        sql.append("operid as operid,");
        sql.append("(SELECT pd.NAME FROM PRV_DEPARTMENT pd WHERE pd.DEPTID=o.DEPTID) as depart,");
        sql.append("(select name from prv_operator where operid = o.operid) as operName,");
        sql.append("opcode as opcode,");
        sql.append("opcode as opcode2,");
        sql.append("sum(fees) AS fees,");
        sql.append("sum(num) AS num");
        sql.append(" FROM statistic_marketing o");
        sql.append(" WHERE o.city = ?");
        paramList.add(param.getCity());
        // @TODO 直接拼接网格名称参数，未做注入校验（gridName命名规则未知，等清楚规则后校验）
        if (gridName!=null){
            sql.append(" AND o.gridName like '%");
            sql.append(gridName+"%'");
        }
        String dateFormat = "'%Y-%m-%d'";
        sql.append(" AND o.statistic_date>=STR_TO_DATE(?,").append(dateFormat).append(")");
        paramList.add(param.getStime());
        sql.append(" AND o.statistic_date<=STR_TO_DATE(?,").append(dateFormat).append(")");
        paramList.add(param.getEtime());
        //加訂單狀態和支付狀態查詢
        List<String> orderStatuss = param.getOrderStatuss();
        if (null != orderStatuss && orderStatuss.size() > 0) {
            sql.append(" and o.orderstatus in (");
            for (int i = 0, size = orderStatuss.size(); i < size; i++) {
                sql.append("?");
                paramList.add(orderStatuss.get(i));
                if (i < size - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }
        List<String> payStatuss = param.getPayStatuss();
        if (null != payStatuss && payStatuss.size() > 0) {
            sql.append(" and o.paystatus in (");
            for (int i = 0, size = payStatuss.size(); i < size; i++) {
                sql.append("?");
                paramList.add(payStatuss.get(i));
                if (i < size - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }
        sql.append(" GROUP BY o.statistic_date, o.areaid, o.operid,o.deptid,o.opcode ");
        sql.append(" ORDER BY o.statistic_date DESC) mr");

        // 准备SQL查询参数对象




        // 查询报表数据
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), StaMarketingStatisticBO.class, paramList.toArray());

        // 判断返回报表数据是否为空
        List<StaMarketingStatisticBO> resultList = page.getResult();
        if (null != page && null != resultList) {
            pageResult = new PageImpl<StaMarketingStatisticBO>(resultList, pageable, page.getTotalCount());
        } else {
            pageResult = new PageImpl<StaMarketingStatisticBO>(new ArrayList<StaMarketingStatisticBO>(), pageable, 0);
        }
        return pageResult;
    }
}