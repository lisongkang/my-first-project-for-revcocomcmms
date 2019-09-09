package com.maywide.biz.sta.gridincome.web.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.sta.gridincome.pojo.ChartDatasBO;
import com.maywide.biz.sta.gridincome.pojo.StaGridincomeChartBO;
import com.maywide.biz.sta.gridincome.pojo.StaGridincomeParamBO;
import com.maywide.biz.sta.gridincome.service.StaGridincomeService;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.SimpleController;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.sta.gridincome统计")
public class StaGridincomeController  extends SimpleController {

    @Autowired
    private StaGridincomeService staGridincomeService;
    private StaGridincomeParamBO staGridincomeParamBo;
    
    public StaGridincomeParamBO getStaGridincomeParamBo() {
        return staGridincomeParamBo;
    }

    public void setStaGridincomeParamBo(StaGridincomeParamBO staGridincomeParamBo) {
        this.staGridincomeParamBo = staGridincomeParamBo;
    }


    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        //TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }
    
    
    public Map<String, String> getCityMap() {
        Map<String, String> cityMap = new LinkedHashMap<String, String>();
        try {
            cityMap = staGridincomeService.getCityMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cityMap;
        
    }
    
    public HttpHeaders queGridIncome() throws Exception {
        String bo = getParameter("staGridincomeParamBo");
        StaGridincomeParamBO staGridincomeParamBo = (StaGridincomeParamBO) BeanUtil.jsonToObject(bo,
                StaGridincomeParamBO.class);
        
        //将通常的列表转换成echarts的option对象有难度
        
        StaGridincomeChartBO echartBo = new StaGridincomeChartBO();
        
        List<String> legendData = new ArrayList();
        legendData.add("测试网格1");legendData.add("测试网格3");legendData.add("测试网格5");legendData.add("测试网格2");
        legendData.add("测试网格11");legendData.add("测试网格31");legendData.add("测试网格51");legendData.add("测试网格21");
        legendData.add("测试网格12");legendData.add("测试网格32");legendData.add("测试网格52");
        
        echartBo.setLegendData(legendData);
        
        List<ChartDatasBO> seriesData = new ArrayList();
        ChartDatasBO chartData = new ChartDatasBO();
        chartData.setName("测试网格1");
        chartData.setType("bar");
        List<String> data = new ArrayList();
        data.add("23");data.add("2");data.add("17");data.add("45");data.add("31");
        chartData.setData(data);
        
        seriesData.add(chartData);
        
        echartBo.setSeriesData(seriesData);
        
        List<String> xAxisData = new ArrayList();
        xAxisData.add("周一");xAxisData.add("周二");xAxisData.add("周三");xAxisData.add("周四");xAxisData.add("周五");
        
        echartBo.setxAxisData(xAxisData);
        
        //String retData = JSONUtil.serialize(echartBo);
        
        //staGridincomeService.queGridIncome(staGridincomeParamBo);
        //setModel(echartBo);
        
        setModel(staGridincomeService.queGridIncome(staGridincomeParamBo));
        
        
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders queGridIncomeList() throws Exception {
        Pageable pageable = PropertyFilter
                .buildPageableFromHttpRequest(getRequest());
        
        String statime = getRequiredParameter("que_statime");
        String[] statimeArry = statime.split("～");
        
        staGridincomeParamBo.setStime(statimeArry[0].trim());
        staGridincomeParamBo.setEtime(statimeArry[1].trim());
        
        setModel(staGridincomeService.queGridIncomeList(staGridincomeParamBo, pageable));
        
        return buildDefaultHttpHeaders();
    }
    
}