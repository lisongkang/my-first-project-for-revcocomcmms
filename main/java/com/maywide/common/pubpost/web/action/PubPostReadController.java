package com.maywide.common.pubpost.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.common.pubpost.entity.PubPostRead;
import com.maywide.common.pubpost.service.PubPostReadService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.service.BaseService;

@MetaData(value = "公告阅读记录")
public class PubPostReadController extends BaseController<PubPostRead, String> {

    @Autowired
    private PubPostReadService pubPostReadService;

    @Override
    protected BaseService<PubPostRead, String> getEntityService() {
        return pubPostReadService;
    }

    @Override
    protected void checkEntityAclPermission(PubPostRead entity) {
        // TODO Add acl check code logic
    }

    @Override
    @MetaData(value = "查询")
    public HttpHeaders findByPage() {
        return super.findByPage();
    }
}