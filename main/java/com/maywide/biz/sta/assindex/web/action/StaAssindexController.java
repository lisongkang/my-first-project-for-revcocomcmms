package com.maywide.biz.sta.assindex.web.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.sta.assindex.pojo.StaAssindexParamBO;
import com.maywide.biz.sta.assindex.service.StaAssindexService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.web.SimpleController;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.sta.gridincome统计")
public class StaAssindexController extends SimpleController {

    @Autowired
    private StaAssindexService staAssindexService;
    private StaAssindexParamBO staAssindexParamBo;

    public StaAssindexParamBO getStaAssindexParamBo() {
        return staAssindexParamBo;
    }

    public void setStaAssindexParamBo(StaAssindexParamBO staAssindexParamBo) {
        this.staAssindexParamBo = staAssindexParamBo;
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        // TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }

    public Map<String, String> getCityMap() {
        Map<String, String> cityMap = new LinkedHashMap<String, String>();
        try {
            cityMap = staAssindexService.getCityMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cityMap;

    }

    public Map<String, String> getAssparamMap() {
        Map<String, String> assparamMap = new LinkedHashMap<String, String>();
        try {
            assparamMap = staAssindexService.getAssparamMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return assparamMap;

    }

    public HttpHeaders getAssList() {
        String city = getParameter("city");
        String assparam = getParameter("assparam");
        
        List<PrvSysparam> assList = new ArrayList();
        try {
            assList = staAssindexService.getAssMap(city, assparam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setModel(assList);

        return buildDefaultHttpHeaders();

    }
    
    public HttpHeaders getAssListByCity() {
        String city = getParameter("city");
        if (null == city || "".equals(city)) {
            city = AuthContextHolder.getLoginInfo().getCity();
        }

        List<PrvSysparam> assList = new ArrayList();
        try {
            assList = staAssindexService.getAssListByCity(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setModel(assList);
        return buildDefaultHttpHeaders();
    }

    public HttpHeaders queGridIncomeList() throws Exception {
        Pageable pageable = PropertyFilter
                .buildPageableFromHttpRequest(getRequest());

        String statime = getRequiredParameter("que_statime");
        String[] statimeArry = statime.split("～");

        staAssindexParamBo.setStime(statimeArry[0].trim());
        staAssindexParamBo.setEtime(statimeArry[1].trim());

        setModel(staAssindexService.queAssindexList(staAssindexParamBo,
                pageable));

        return buildDefaultHttpHeaders();
    }

}