package com.maywide.biz.inter.service;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.TokenReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.pojo.queInterUnitPrice.*;
import com.maywide.biz.inter.pojo.quegridtree.GridTreeNode;
import com.maywide.biz.inter.pojo.quegridtree.QueGridTreeNodeReq;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lisongkang on 2019/8/12 0001.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralUnitPriceService extends CommonService {
    @Autowired
    private ParamService paramService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private SalaryCentsService salaryCentsService;

    /**
     * ZS获取操作员主网格gridcode信息
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo queMainGridinfo(QueInterUnitPriceLineReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        result.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        result.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        CheckUtils.checkNull(req.getOperid(), "操作异常,操作员不能为空");
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, "用户未登录或登录信息已过期!");
        BizGridInfo grid = salaryCentsService.getGrids();
        result.setData(grid);
        return result;
    }

    /**
     * 积分单价相关线查询
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo queInterUnitPriceLine(QueInterUnitPriceLineReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        result.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        result.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        if(StringUtils.isEmpty(req.getMonth())){
            throw new Exception("月份不能为空!");
        }
        CheckUtils.checkNull(req.getOperid(), "操作异常,操作员不能为空");
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, "用户未登录或登录信息已过期!");
        //处理下传过来的月份   当前月就取前一天  往月就取往月最后一天
        req.setData(getDataForMonth(req.getMonth()));

        BizGridInfo grid = salaryCentsService.getGrids();
        req.setGridcode(grid.getGridcode());
        //通过gridcode 取地市和业务区
        List params = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT city,areaid from biz_grid_info where GRIDCODE= ? ");
        params.add(req.getGridcode());
        List<GridInfo> gridInfos = getDAO().find(sql.toString(),GridInfo.class,params.toArray());
        //去查询单价线
        List paramsList = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT DISTINCT(min_cents_price) as bottomprice,max_cents_price as topprice, type type from ");
        sqlBuffer.append(" salary_others_exchange_config where city = ? and areaid in ('*',?) ");
        sqlBuffer.append(" and type in('0','1','2') and sdate_month <= ? and  edate_month >= ? order by min_cents_price ");
        paramsList.add(gridInfos.get(0).getCity());
        String month = req.getMonth().substring(0,4) + "-" + req.getMonth().substring(4,6);
        paramsList.add(gridInfos.get(0).getAreaid());
        paramsList.add(month);
        paramsList.add(month);
        List<PriceInfo> priceInfos = getDAO().find(sqlBuffer.toString(),PriceInfo.class,paramsList.toArray());
        if(priceInfos==null){
            CheckUtils.checkNull(null, "未配置积分单价兑换奖励规则");
        }
        if(priceInfos.size()<3){
            CheckUtils.checkNull(null, "积分兑换奖励规则('基础线','标准线','挑战线')配置不全,请再次配置");
        }
        //再去ODS查当前数据组装
        //积分单价线数组
        JSONArray ja = getJsonArrayPriceLine(priceInfos,req);
        result.setData(ja);
        return result;
    }
    //ODS查当前数据组装
    public JSONArray getJsonArrayPriceLine(List<PriceInfo> priceInfos,QueInterUnitPriceLineReq req) throws Exception {
        //组装单价线的数组
        JSONArray ja = new JSONArray();
        //最低单价对象
        JSONObject lowestobj = new JSONObject();
        lowestobj.put("numberHostsUse", "");
        lowestobj.put("integralPrice", priceInfos.get(0).getBottomprice());
        lowestobj.put("gradeLine", "");
        ja.put(lowestobj);
        //标准线对象
        List params = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append("select whgridcode as whgridcode,kpi kpi,cycleid cycleid,LAST_YEAR_USERS lastyearusers, ");
        sql.append(" cur_users curusers,base_level baselevel,chall_level challlevel from NODS.ZS_ALLGRID_SORT where ");
        sql.append(" cycleid = ? and whgridcode = ? ");
        params.add(req.getData());
        params.add(req.getGridcode());
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<CurrentDatePriceInfo> currentDatePriceInfoList = persistentService.find(sql.toString(), CurrentDatePriceInfo.class,
                params.toArray());
        JSONObject standardobj = new JSONObject();
        if(currentDatePriceInfoList.size()>0){
            if(currentDatePriceInfoList.get(0).getBaselevel() != null){
                standardobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getBaselevel());
            }else {
                standardobj.put("numberHostsUse","");
            }
        }else {
            standardobj.put("numberHostsUse","");
        }
        standardobj.put("integralPrice", priceInfos.get(0).getTopprice());
        standardobj.put("gradeLine", "标准线");
        ja.put(standardobj);
        //挑战线对象
        JSONObject challengeobj = new JSONObject();
        if(currentDatePriceInfoList.size()>0){
            if(currentDatePriceInfoList.get(0).getChalllevel() != null){
                challengeobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getChalllevel());
            }else {
                challengeobj.put("numberHostsUse", "");
            }
        }else {
            challengeobj.put("numberHostsUse", "");
        }
        challengeobj.put("integralPrice", priceInfos.get(2).getBottomprice());
        challengeobj.put("gradeLine", "挑战线");
        ja.put(challengeobj);
        //最高单价对象
        JSONObject highobj = new JSONObject();
        highobj.put("numberHostsUse", "");
        highobj.put("integralPrice", priceInfos.get(2).getTopprice());
        highobj.put("gradeLine", "");
        ja.put(highobj);
        //处理当期值 共有7种情况
        if(currentDatePriceInfoList.size()<=0){
            return ja;
        }
        if(currentDatePriceInfoList.get(0).getKpi()==null && currentDatePriceInfoList.get(0).getKpi() == 0.0d ){
            return ja;
        }
        Double kpi = currentDatePriceInfoList.get(0).getKpi();
        if (kpi.equals(priceInfos.get(0).getBottomprice())) {
            JSONObject currentobj = new JSONObject();
            if(currentDatePriceInfoList.get(0).getCurusers() != null){

                currentobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getCurusers());
            }else {
                currentobj.put("numberHostsUse", "");
            }
            currentobj.put("gradeLine", "当前值");
            currentobj.put("currentValue", "Y");
            currentobj.put("integralPrice",kpi);
            ja.put(1,currentobj);
            ja.put(2,standardobj);
            ja.put(3,challengeobj);
            ja.put(4,highobj);

        }
        //介于最低和标准之间
        else if (kpi > priceInfos.get(0).getBottomprice() && kpi < priceInfos.get(1).getBottomprice()) {
            JSONObject currentobj = new JSONObject();
            currentobj.put("currentValue", "Y");
            if(currentDatePriceInfoList.get(0).getCurusers() != null){

                currentobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getCurusers());
            }else {
                currentobj.put("numberHostsUse", "");
            }
            currentobj.put("integralPrice", kpi);
            currentobj.put("gradeLine", "当前值");
            ja.put(1,currentobj);
            ja.put(2,standardobj);
            ja.put(3,challengeobj);
            ja.put(4,highobj);

        } else if (kpi.equals(priceInfos.get(1).getBottomprice())) {
            JSONObject currentobj = new JSONObject();
            currentobj.put("currentValue", "Y");
            if(currentDatePriceInfoList.get(0).getCurusers() != null){

                currentobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getCurusers());
            }else {
                currentobj.put("numberHostsUse", "");
            }
            currentobj.put("integralPrice", kpi);
            currentobj.put("gradeLine", "当前值");
            ja.put(2,currentobj);
            ja.put(3,challengeobj);
            ja.put(4,highobj);

        }
        //介于tiaozhan和标准之间
        else if (kpi > priceInfos.get(1).getBottomprice() && kpi < priceInfos.get(1).getTopprice()) {
            JSONObject currentobj = new JSONObject();
            currentobj.put("currentValue", "Y");
            if(currentDatePriceInfoList.get(0).getCurusers() != null){

                currentobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getCurusers());
            }else {
                currentobj.put("numberHostsUse", "");
            }
            currentobj.put("integralPrice", kpi);
            currentobj.put("gradeLine", "当前值");
            ja.put(2,currentobj);
            ja.put(3,challengeobj);
            ja.put(4,highobj);

        } else if (kpi.equals(priceInfos.get(1).getTopprice())) {
            JSONObject currentobj = new JSONObject();
            currentobj.put("currentValue", "Y");
            if(currentDatePriceInfoList.get(0).getCurusers() != null){

                currentobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getCurusers());
            }else {
                currentobj.put("numberHostsUse", "");
            }
            currentobj.put("integralPrice", kpi);
            currentobj.put("gradeLine", "当前值");
            ja.put(2,currentobj);
            ja.put(3,challengeobj);
            ja.put(4,highobj);
        }
        //介于tiaozhan和最高之间
        else if (kpi > priceInfos.get(1).getTopprice() && kpi < priceInfos.get(2).getTopprice()) {
            JSONObject currentobj = new JSONObject();
            currentobj.put("currentValue", "Y");
            if(currentDatePriceInfoList.get(0).getCurusers() != null){

                currentobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getCurusers());
            }else {
                currentobj.put("numberHostsUse", "");
            }
            currentobj.put("integralPrice", kpi);
            currentobj.put("gradeLine", "当前值");
            ja.put(3,currentobj);
            ja.put(4,highobj);
        } else if (kpi.equals(priceInfos.get(2).getTopprice())){
            JSONObject currentobj = new JSONObject();
            currentobj.put("currentValue", "Y");
            if(currentDatePriceInfoList.get(0).getCurusers() != null){

                currentobj.put("numberHostsUse", currentDatePriceInfoList.get(0).getCurusers());
            }else {
                currentobj.put("numberHostsUse", "");
            }
            currentobj.put("integralPrice", kpi);
            currentobj.put("gradeLine", "当前值");
            ja.put(3,currentobj);
            ja.put(4,highobj);

        }
        return ja;
    }

    public static void main(String[] args) {

            String ss = "AP审计设备融合套餐协议书.pdf";
            String sss = ss.substring(ss.lastIndexOf("."));
            System.out.println(sss);

    }
    /**
     * 获取当前月的前一天或者往月的最后一天
     * @param
     * @return
     */
    public static String getDataForMonth(String monthreq){
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String month = monthreq;
        //获取当前月份
        Calendar cale = Calendar.getInstance();
        int monthSys = cale.get(Calendar.MONTH) + 1;
        String monthSysTwo;
        if(monthSys < 10){
            monthSysTwo = 0 + "" + monthSys;
        }else {
            monthSysTwo = String.valueOf(monthSys);
        }
        String date;
        if(month.substring(4,6).equals(monthSysTwo)){
            Date today = new Date();//获取今天的日期
            cale.setTime(today);
            cale.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterday = cale.getTime();//这是昨天
            date = sdf.format(yesterday);
        }else {
            //获取选定月份的最后一天
            int year = Integer.parseInt(monthreq.substring(0,4));  //年
            int monthToMonth = Integer.parseInt(monthreq.substring(4,6)); //月
            Calendar cal = Calendar.getInstance();
            // 设置年份
            cal.set(Calendar.YEAR, year);
            // 设置月份
            cal.set(Calendar.MONTH, monthToMonth - 1);
            // 获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            // 设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
            date = sdf.format(cal.getTime());
        }
        return date;
    }
    /**
     *查询网格积分区间单价排名情况
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo queInterUnitPriceRanking(QueInterUnitPriceRankingReq req) throws Exception {
        TokenReturnInfo result = new TokenReturnInfo();
        result.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        result.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        CheckUtils.checkEmpty(req.getOperid(), "操作员不能为空!");
        //处理下传过来的月份   当前月就取前一天  往月就取往月最后一天
        req.setData(getDataForMonth(req.getMonth()));
        BizGridInfo grid = salaryCentsService.getGrids();
        req.setGridcode(grid.getGridcode());
        //查询区间编码
        JSONArray ja = new JSONArray();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT range,kpi,sortid from NODS.ZS_ALLGRID_SORT where cycleid = ? and whgridcode = ? ");
        params.add(req.getData());
        params.add(req.getGridcode());
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<RangePriceRanking> rangePriceRankingList = persistentService.find(sqlBuffer.toString(),
                RangePriceRanking.class,params.toArray());
        //查询该区段所有排名
        if(rangePriceRankingList == null || rangePriceRankingList.size() == 0 ){
            CheckUtils.checkEmpty(null, "ODS找不到当前日期网格的区间单价排名情况");
        }
        if(rangePriceRankingList.get(0).getRange() == null){
            CheckUtils.checkEmpty(null, "ODS找不到当前网格的区间编码");
        }
        if(rangePriceRankingList.get(0).getKpi() == null){
            CheckUtils.checkEmpty(null, "ODS找不到当前网格的积分单价");
        }
        if(rangePriceRankingList!= null && rangePriceRankingList.size() > 0 ){
            List<Object> paramsList = new ArrayList();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT range,kpi,sortid from NODS.ZS_ALLGRID_SORT where cycleid = ? and RANGE = ? ORDER BY sortid ");
            paramsList.add(req.getData());
            paramsList.add(rangePriceRankingList.get(0).getRange());
            List<RangePriceRanking> rangePriceRankingSList = persistentService.find(sql.toString(),
                    RangePriceRanking.class,paramsList.toArray());
            for (RangePriceRanking rangePriceRanking:rangePriceRankingSList
                 ) {
                JSONObject object = new JSONObject();
                object.put("ranking", rangePriceRanking.getSortid());
                object.put("integralPrice", rangePriceRanking.getKpi());
                if(rangePriceRankingList.get(0).getKpi().equals(rangePriceRanking.getKpi())){
                    object.put("myRanking", "Y");
                }
                ja.put(object);
            }
        }
        result.setData(ja);
        return result;
    }

    /**
     * 写给H5带TOKEN 的网格树插件
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo queGridTokenTree(QueGridTreeNodeReq req) throws Exception {
        TokenReturnInfo result = new TokenReturnInfo();
        result.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        result.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, "用户未登录或登录信息已过期!");
        CheckUtils.checkEmpty(req.getCity(), "操作员地市不能为空!");
        CheckUtils.checkNull(req.getOperid(), "工号信息不能为空!");

        ArrayList<GridTreeNode> resp = new ArrayList<GridTreeNode>();

        String sql = "SELECT gridid,gridcode,gridname,gtype,previd FROM biz_grid_info WHERE city= ? AND gtype IN (0,2) AND previd IS NOT NULL ";
        List<GridTreeNode> gridList = getDAO().find(sql, GridTreeNode.class, loginInfo.getCity());

        sql += " AND gridid IN (SELECT gridid FROM biz_grid_manager WHERE operid = ? ) ";
        List<GridTreeNode> operGrids = getDAO().find(sql, GridTreeNode.class, loginInfo.getCity(), loginInfo.getOperid());

        List<GridTreeNode> nodeList = new ArrayList<GridTreeNode>();
        for (GridTreeNode gridInfo : operGrids) {
            GridTreeNode node = buildChain(gridList, gridInfo);
            if (!isExistNode(node, nodeList)) {
                nodeList.add(node);
            }
        }
        if (nodeList != null) {
            for (GridTreeNode node : nodeList) {
                setLevel4Node(node, 1);
            }
            resp.addAll(nodeList);
        }
        result.setData(resp);
        return result;
    }

    private GridTreeNode buildChain(List<GridTreeNode> gridList, GridTreeNode gridInfo) {
        buildPrevChain(gridList, gridInfo);
        GridTreeNode rootNode = gridInfo;
        while (rootNode.getPrev() != null) {
            rootNode = rootNode.getPrev();
        }
        if (BizConstant.BizGridObjObjtype.PATCH.equals(gridInfo.getgType())) {
            buildNextChain(gridList, gridInfo);
        }
        return rootNode;
    }

    private void setLevel4Node(GridTreeNode node, int level) {
        node.setLevel(level);
        List<GridTreeNode> childNodes = node.getChildNodes();
        if (childNodes != null && !childNodes.isEmpty()) {
            int nextLevel = level + 1;
            for (GridTreeNode childNode : childNodes) {
                setLevel4Node(childNode, nextLevel);
            }
        }
    }

    private void buildNextChain(List<GridTreeNode> gridList, GridTreeNode node) {
        List<GridTreeNode> nextNodes = findNextNodes(gridList, node);
        if (nextNodes != null && !nextNodes.isEmpty()) {
            List<GridTreeNode> childNodes = node.getChildNodes();
            if (childNodes == null) {
                childNodes = new ArrayList<GridTreeNode>();
                node.setChildNodes(childNodes);
            }
            for (GridTreeNode nextNode : nextNodes) {
                if (!isExistNode(nextNode, childNodes)) {
                    childNodes.add(nextNode);
                }
                buildNextChain(gridList, nextNode);
            }
        }
    }

    private List<GridTreeNode> findNextNodes(List<GridTreeNode> gridList, GridTreeNode grid) {
        if (BizConstant.BizGridObjObjtype.ADDR.equals(grid.getgType())) {
            return null;
        }
        List<GridTreeNode> list = new ArrayList<GridTreeNode>();
        for (GridTreeNode gridInfo : gridList) {
            if (grid.getGridId().equals(gridInfo.getPrevId())) {
                list.add(gridInfo);
            }
        }
        return list;
    }

    private void buildPrevChain(List<GridTreeNode> gridList, GridTreeNode grid) {
        if (grid.getPrev() != null || grid.getPrevId().equals(-1L)) {
            return;
        }
        GridTreeNode prevGrid = findPrevGrid(gridList, grid);
        if (prevGrid != null) {
            grid.setPrev(prevGrid);
            List<GridTreeNode> childNodes = prevGrid.getChildNodes();
            if (childNodes == null) {
                childNodes = new ArrayList<GridTreeNode>();
                prevGrid.setChildNodes(childNodes);
            }
            if (!isExistNode(grid, childNodes)) {
                childNodes.add(grid);
            }
            buildPrevChain(gridList, prevGrid);
        }
    }

    private boolean isExistNode(GridTreeNode node, List<GridTreeNode> nodes) {
        for (GridTreeNode gridTreeNode : nodes) {
            if (gridTreeNode.getGridId().equals(node.getGridId())) {
                return true;
            }
        }
        return false;
    }

    private GridTreeNode findPrevGrid(List<GridTreeNode> gridList, GridTreeNode grid) {
        if (grid.getPrevId() == null || grid.getPrevId().equals(-1L)) {
            return null;
        }
        for (GridTreeNode gridInfo : gridList) {
            if (gridInfo.getGridId().equals(grid.getPrevId())) {
                return gridInfo;
            }
        }
        return null;
    }



}
