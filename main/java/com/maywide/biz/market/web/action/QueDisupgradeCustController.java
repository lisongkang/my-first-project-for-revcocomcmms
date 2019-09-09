package com.maywide.biz.market.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoReq;
import com.maywide.biz.market.service.QueDisupgradeCustService;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.web.SimpleController;

@MetaData("查询未升级双向客户资料")
public class QueDisupgradeCustController extends SimpleController {
    @Autowired
    private QueDisupgradeCustService qdcService;
    private QueCustMarktInfoReq      queDisupgradeCustParamBO;

    public QueCustMarktInfoReq getQueDisupgradeCustParamBO() {
        return queDisupgradeCustParamBO;
    }

    public void setQueDisupgradeCustParamBO(QueCustMarktInfoReq queDisupgradeCustParamBO) {
        this.queDisupgradeCustParamBO = queDisupgradeCustParamBO;
    }

    @MetaData("查询未升级双向客户资料")
    public HttpHeaders queryDisupgradeCustInfo() throws Exception {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            setModel(qdcService.queryDisupgradeCustInfo(queDisupgradeCustParamBO, pageable));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
}