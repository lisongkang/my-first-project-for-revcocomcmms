package com.maywide.biz.sta.gridincome.pojo;

import java.util.List;

import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.queservstinfo.CustInfosBO;

public class StaGridincomeChartBO implements java.io.Serializable {
  
    private List<String> legendData;//图例数组
    private List<String> xAxisData;//x轴轴标数据
    private List<ChartDatasBO> seriesData;//统计数据
    
    public List<String> getLegendData() {
        return legendData;
    }
    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }
    public List<String> getxAxisData() {
        return xAxisData;
    }
    public void setxAxisData(List<String> xAxisData) {
        this.xAxisData = xAxisData;
    }
    public List<ChartDatasBO> getSeriesData() {
        return seriesData;
    }
    public void setSeriesData(List<ChartDatasBO> seriesData) {
        this.seriesData = seriesData;
    }
    
    
}
