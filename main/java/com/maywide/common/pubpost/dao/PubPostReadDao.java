package com.maywide.common.pubpost.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.common.pubpost.entity.PubPost;
import com.maywide.common.pubpost.entity.PubPostRead;
import com.maywide.core.dao.BaseDao;

@Repository
public interface PubPostReadDao extends BaseDao<PubPostRead, String> {

    @Query("from PubPostRead t where t.readUser=:readUser and t.pubPost in (:pubPosts)")
    List<PubPostRead> findReaded(@Param("readUser") PrvOperator readUser, @Param("pubPosts") List<PubPost> pubPosts);

    PubPostRead findByReadUserAndPubPost(PrvOperator readUser, PubPost pubPost);
}