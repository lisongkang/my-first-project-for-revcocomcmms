package com.maywide.biz.remind.service;

import java.util.List;

import com.maywide.biz.remind.entity.BizRemRulecfg;
import com.maywide.biz.remind.dao.BizRemRulecfgDao;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BizRemRulecfgService extends BaseService<BizRemRulecfg,Long>{
    
    @Autowired
    private BizRemRulecfgDao bizRemRulecfgDao;
    
    @Autowired
    private PersistentService persistenService;

    @Override
    protected BaseDao<BizRemRulecfg, Long> getEntityDao() {
        return bizRemRulecfgDao;
    }
    
    public BizRemRulecfg getBizRemRulecfg(Long remid) throws Exception{
    	BizRemRulecfg cfg = new BizRemRulecfg();
    	cfg.setRemid(remid);
    	List<BizRemRulecfg> cfglist = persistenService.find(cfg);
    	
    	if(cfglist != null && cfglist.size() > 0) {
    		return cfglist.get(0);
    	}
    	return null;
    }
}
