package com.maywide.common.pubpost.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.common.pubpost.dao.PubPostDao;
import com.maywide.common.pubpost.entity.PubPost;
import com.maywide.common.pubpost.entity.PubPostDept;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.exception.WebException;
import com.maywide.core.nio.NettyServerBootstrap;
import com.maywide.core.nio.msg.CommonMsg;
import com.maywide.core.nio.msg.MsgType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class PubPostService extends BaseService<PubPost, String> {

    private final Logger logger = LoggerFactory.getLogger(PubPostService.class);
    
    @Autowired
    private PubPostDao pubPostDao;
    
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseDao<PubPost, String> getEntityDao() {
        return pubPostDao;
    }
    
    @Transactional(readOnly = true)
    @Cacheable("PubPostSpringCache")
    public List<PubPost> findPublished() {
        logger.debug("Finding published post message...");
        return pubPostDao.findPublished(new Date());
    }

    @Transactional(readOnly = true)
    @Cacheable("PubPostSpringCache")
    public List<PubPost> findPublished(LoginInfo loginInfo) {
        logger.debug("Finding published post message...");
        return pubPostDao.findPublished(loginInfo.getDeptid(), new Date());
    }
    
    @Transactional(readOnly = true)
    @Cacheable("PubPostSpringCache")
    public List<PubPost> findFrontPublished(LoginInfo loginInfo) {
        return pubPostDao.findFrontPublished(loginInfo.getDeptid(), new Date());
    }
    
    @Override
    @CacheEvict(value = "PubPostSpringCache", allEntries = true)
    public PubPost save(PubPost entity) {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	if (loginInfo == null) throw new WebException("用户未登录或已过期");
    	
    	boolean isnew = entity.getId() == null;
    	if (isnew) {
    	    entity.setOperid(loginInfo.getOperid());
    	    entity.setCity(loginInfo.getCity());
    	}
    	PubPost pubPost = super.save(entity);   
		
    	return pubPost;
    }

    @Override
    @CacheEvict(value = "PubPostSpringCache", allEntries = true)
    public List<PubPost> save(Iterable<PubPost> entities) {
        return super.save(entities);
    }

    @Override
    @CacheEvict(value = "PubPostSpringCache", allEntries = true)
    public void delete(PubPost entity) {
    	try {
    		persistentService.executeUpdate("DELETE FROM PubPostDept WHERE pubId = ?", entity.getId());
            super.delete(entity);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    }

    @Override
    @CacheEvict(value = "PubPostSpringCache", allEntries = true)
    public void delete(Iterable<PubPost> entities) {
        super.delete(entities);
    }

    @CacheEvict(value = "PubPostSpringCache", allEntries = true)
    public void evictCache() {
        //Just trigger spring cache evict
    }
    
    public List<Long> getDeptIds(String pubId) {
    	PubPostDept param = new PubPostDept();
    	param.setPubId(pubId);
    	List<Long> ids = Lists.newArrayList();
    	try {
    		List<PubPostDept> depts = (List<PubPostDept>) persistentService.find(param);
    		for (PubPostDept dept : depts) {
    			ids.add(dept.getDeptId());
    		}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
		return ids;
    }
    
    public void savePubPostDept(String pubId, String deptIds) {
    	try {
    		String hql = "DELETE FROM PubPostDept WHERE pubId = ?";
        	persistentService.executeUpdate(hql, pubId);
        	
        	String[] arrId = deptIds.split(",");
        	PubPostDept pubPostDept = null;
        	
        	for (String id : arrId) {
        		pubPostDept = new PubPostDept();
        		pubPostDept.setDeptId(new Long(id));
        		pubPostDept.setPubId(pubId);
        		
        		persist(pubPostDept);
        	}
        	
            CommonMsg askMsg = new CommonMsg();
            askMsg.setType(MsgType.ASK.toString());
            askMsg.setCmdtype(SysConstant.CmdType.MESSAGE_NEW);
            NettyServerBootstrap.sendAllClient(askMsg);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    }

    public void transGridList(List<PubPost> pubPostList) throws Exception {
        for (PubPost pubPost : pubPostList) {
            pubPost.addExtraAttribute("opername", getOperName(pubPost));
        }
    }

    @SuppressWarnings("unchecked")
    private String getOperName(PubPost pubPost) throws Exception {
        List<PrvOperator> opList = persistentService.find("FROM PrvOperator WHERE id=?", pubPost.getOperid());
        if (null != opList && opList.size() > 0) {
            return opList.get(0).getName();
        } else {
            return null;
        }
    }
}
