package com.maywide.common.pubpost.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maywide.common.pubpost.entity.PubPost;
import com.maywide.core.dao.BaseDao;

@Repository
public interface PubPostDao extends BaseDao<PubPost, String> {

    @Query("from PubPost t where t.publishTime is not null and t.publishTime<:currentDate and (t.expireTime>:currentDate or t.expireTime is null) order by t.publishTime desc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    List<PubPost> findPublished(@Param("currentDate") Date currentDate);
    
    @Query("select t from PubPost t, PubPostDept a where t.id = a.pubId and t.backendShow = 1 and (a.deptId = 0 or a.deptId = :deptId) and t.publishTime is not null and t.publishTime<:currentDate and (t.expireTime>:currentDate or t.expireTime is null) order by t.publishTime desc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    List<PubPost> findPublished(@Param("deptId") Long deptId, @Param("currentDate") Date currentDate);
    
    @Query("select t from PubPost t, PubPostDept a where t.id = a.pubId and t.frontendShow = 1 and (a.deptId = 0 or a.deptId = :deptId) and t.publishTime is not null and t.publishTime<:currentDate and (t.expireTime>:currentDate or t.expireTime is null) order by t.publishTime desc")
    List<PubPost> findFrontPublished(@Param("deptId") Long deptId, @Param("currentDate") Date currentDate);
}