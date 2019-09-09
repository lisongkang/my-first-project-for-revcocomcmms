package com.maywide.common.pubpost.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maywide.common.pubpost.entity.AttachmentFile;
import com.maywide.core.dao.BaseDao;

@Repository
public interface AttachmentFileDao extends BaseDao<AttachmentFile, String> {
    List<AttachmentFile> findByEntityClassNameAndEntityIdAndEntityFileCategory(String entityClassName, String entityId,
            String entityFileCategory);

    List<AttachmentFile> findByEntityClassNameAndEntityId(String entityClassName, String entityId);
   
}
