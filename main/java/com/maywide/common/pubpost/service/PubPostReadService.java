package com.maywide.common.pubpost.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.common.pubpost.dao.PubPostReadDao;
import com.maywide.common.pubpost.entity.PubPost;
import com.maywide.common.pubpost.entity.PubPostRead;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional
public class PubPostReadService extends BaseService<PubPostRead, String> {

    @Autowired
    private PubPostReadDao pubPostReadDao;

    @Override
    protected BaseDao<PubPostRead, String> getEntityDao() {
        return pubPostReadDao;
    }

    public List<PubPostRead> findReaded(PrvOperator readUser, List<PubPost> pubPosts) {
        return pubPostReadDao.findReaded(readUser, pubPosts);
    }

    public PubPostRead findReaded(PrvOperator readUser, PubPost pubPost) {
        return pubPostReadDao.findByReadUserAndPubPost(readUser, pubPost);
    }
}
