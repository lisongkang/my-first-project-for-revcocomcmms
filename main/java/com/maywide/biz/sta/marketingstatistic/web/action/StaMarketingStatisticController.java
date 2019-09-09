package com.maywide.biz.sta.marketingstatistic.web.action;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.sta.gridincome.pojo.StaGridincomeParamBO;
import com.maywide.biz.sta.gridincome.service.StaGridincomeService;
import com.maywide.biz.sta.marketingstatistic.bo.StaMarketStatisticParamBO;
import com.maywide.biz.sta.marketingstatistic.bo.StaMarketingStatisticBO;
import com.maywide.biz.sta.marketingstatistic.service.StaMarketingStatisticService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.SimpleController;
import com.maywide.tool.excel.ExportHelper;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@MetaData("[com.maywide].biz.sta.marketingstatistic统计")
public class StaMarketingStatisticController extends SimpleController {

    @Autowired
    private StaMarketingStatisticService smrService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private StaGridincomeService staGridincomeService;

    private StaMarketStatisticParamBO staMarketStatisticParamBO;

    private Map<String, String> dictDataMap = new LinkedHashMap<String, String>();

    public StaMarketStatisticParamBO getStaMarketStatisticParamBO() {
        return staMarketStatisticParamBO;
    }

    public void setStaMarketStatisticParamBO(StaMarketStatisticParamBO staMarketStatisticParamBO) {
        this.staMarketStatisticParamBO = staMarketStatisticParamBO;
    }

    @MetaData("营销业务单统计查询")
    public HttpHeaders querySaleOrderList() throws Exception {
        try {

            HttpServletRequest request = getRequest();
            System.out.println(request.getQueryString());

            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            setModel(smrService.querySaleOrderList(staMarketStatisticParamBO, pageable));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("导出营销业务单统计查询")
    public void exportExcel() throws Exception {

        try {
            ExportHelper export = new ExportHelper();
            String[] cols = new String[]{"统计日期", "业务区", "所属部门", "网格名称", "网格编码", "网格人员", "业务名称", "金额", "业务数量"};

            Pageable pageable = new PageRequest(0, Integer.MAX_VALUE,
                    PropertyFilter.buildSortFromHttpRequest(getRequest()));

            PageImpl<StaMarketingStatisticBO> page = smrService
                    .querySaleOrderList(staMarketStatisticParamBO, pageable);
            if (page.getContent().size() > 0) {
                List<String[]> rowArr = new ArrayList<String[]>();
                for (StaMarketingStatisticBO bo : page.getContent()) {
                    String[] row = new String[]{
                            DateUtils.getFormatDateString(bo.getStatisticDate(), "yyyy-MM-dd"),
                            bo.getAreaName(),
                            bo.getDepart(),
                            bo.getGridname(),
                            bo.getGridcode(),
                            bo.getOperName(),
//                            bo.getOpcode(),
                            getCacheDictData("BIZ_OPCODE", bo.getOpcode()),
                            (bo.getFees() == null) ? null : bo.getFees().toString(),
                            (bo.getNum() == null) ? null : bo.getNum().toString()
                    };
                    rowArr.add(row);
                }
                export.exportExcel("营销业务单", cols, rowArr, getResponse());
            }

        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
//        return buildDefaultHttpHeaders();
    }


    private String getCacheDictData(String gcode, String mcode) {
        try {
            if (dictDataMap.get(gcode + mcode) != null) {
                return dictDataMap.get(gcode + mcode);//先从缓存里面取
            }
            List<PrvSysparam> list = paramService.getData(gcode, null, null, null);
            if (null != list && list.size() > 0) {
                for (PrvSysparam param : list) {
                    dictDataMap.put(gcode + param.getMcode(), param.getMname());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dictDataMap.get(gcode + mcode);
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
}