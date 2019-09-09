package com.maywide.common.pubpost.web.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.prv.service.PrvDepartmentService;
import com.maywide.common.pubpost.entity.PubPost;
import com.maywide.common.pubpost.entity.PubPostRead;
import com.maywide.common.pubpost.service.AttachmentFileService;
import com.maywide.common.pubpost.service.PubPostReadService;
import com.maywide.common.pubpost.service.PubPostService;
import com.maywide.common.sys.vo.TreeMenuVO;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;

@MetaData(value = "公告管理")
public class PubPostController extends BaseController<PubPost, String> {
	
	private final static String READED_PUB_POST_IDS = "READED_PUB_POST_IDS";

    @Autowired
    private PubPostService pubPostService;
    
    @Autowired
    private PubPostReadService pubPostReadService;

    @Autowired
    private AttachmentFileService attachmentFileService;
    
    @Autowired
    private PrvDepartmentService prvDepartmentService;
    
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseService<PubPost, String> getEntityService() {
        return pubPostService;
    }

    @Override
    protected void checkEntityAclPermission(PubPost entity) {
        // Nothing
    }

    @Override
    @MetaData(value = "保存")
    public HttpHeaders doSave() {
    	return super.doSave();
    }
    
    public HttpHeaders savePubPostDept() {
    	String deptIds = getParameter("deptIds");
    	pubPostService.savePubPostDept(bindingEntity.getId(), deptIds);
    	setModel(OperationResult.buildSuccessResult("公告发送部门设置完成"));
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData(value = "删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData(value = "查询")
    public HttpHeaders findByPage() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
            
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if (null != loginInfo && StringUtils.isNotBlank(loginInfo.getCity())) {
                groupFilter.append(new PropertyFilter(MatchType.EQ, "city", loginInfo.getCity()));
            }
            
            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
                Page<PubPost> page = this.getEntityService().findByPage(groupFilter, pageable);
                pubPostService.transGridList(page.getContent());
                setModel(page);
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData(value = "关联附件列表")
    public HttpHeaders attachmentList() {
        return attachmentList(bindingEntity);
    }

    @MetaData(value = "关联附件下载")
    public void attachmentDownload() {
        attachmentDownload(bindingEntity, getRequiredParameter("attachmentId"));
    }
    
    public HttpHeaders depts() {
    	return buildDefaultHttpHeaders("depts");
    }
    
    @MetaData(value = "公告列表")
    public HttpHeaders list() {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	if (loginInfo == null) throw new WebException("用户未登录或已过期");
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        
        Map<Serializable, Boolean> idMaps = (Map<Serializable, Boolean>) session.getAttribute(READED_PUB_POST_IDS);
        if (idMaps == null) {
            idMaps = Maps.newHashMap();
            session.setAttribute(READED_PUB_POST_IDS, idMaps);
        }
        
        List<PubPost> pubPosts = pubPostService.findPublished();
        if (CollectionUtils.isNotEmpty(pubPosts)) {
        	if (loginInfo == null) {
        		pubPosts = new ArrayList();
        	} else {
        		PrvOperator oper = null;
            	try {
            		oper = (PrvOperator) persistentService.find(PrvOperator.class, loginInfo.getOperid());
    			} catch (Exception e) {
    				
    			}
            	
                List<PubPostRead> pubPostReads = pubPostReadService.findReaded(oper, pubPosts);
                for (PubPost pubPost : pubPosts) {
                    pubPost.addExtraAttribute("readed", false);
                    for (PubPostRead pubPostRead : pubPostReads) {
                        if (pubPostRead.getPubPost().equals(pubPost)) {
                            pubPost.addExtraAttribute("readed", true);
                            break;
                        }
                    }
                }
        	}
        	
            setModel(pubPosts);
        }
        return buildDefaultHttpHeaders("list");
    }
    
    @MetaData("用户公告消息列表")
    public HttpHeaders messages() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        //进行Session数据缓存优化处理，避免每次查询数据库
        @SuppressWarnings("unchecked")
        Map<Serializable, Boolean> idMaps = (Map<Serializable, Boolean>) session.getAttribute(READED_PUB_POST_IDS);
        if (idMaps == null) {
            idMaps = Maps.newHashMap();
            session.setAttribute(READED_PUB_POST_IDS, idMaps);
        }
        List<PubPost> pubPosts = pubPostService.findPublished(loginInfo);
        /*for (Iterator<PubPost> it = pubPosts.iterator(); it.hasNext();) {
        	PubPost pubPost = it.next();
        	List<Long> deptIds = pubPostService.getDeptIds(pubPost.getId());
        	
        	if (!deptIds.contains(loginInfo.getDeptid())) {
        		it.remove();
        	}
        }
        */
        List<PubPost> notifyList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(pubPosts)) {

            boolean needRetriveReads = false;
            
            
            for (PubPost pubPost : pubPosts) {
                if (!idMaps.containsKey(pubPost.getId())) {
                    needRetriveReads = true;
                    break;
                }
            }

            if (needRetriveReads) {
            	PrvOperator oper = null;
            	try {
            		oper = (PrvOperator) persistentService.find(PrvOperator.class, loginInfo.getOperid());
    			} catch (Exception e) {
    				
    			}
    			
                List<PubPostRead> pubPostReads = pubPostReadService.findReaded(oper, pubPosts);
                for (PubPost pubPost : pubPosts) {
                    idMaps.put(pubPost.getId(), Boolean.FALSE);
                    for (PubPostRead pubPostRead : pubPostReads) {
                        if (pubPostRead.getPubPost().equals(pubPost)) {
                            idMaps.put(pubPost.getId(), Boolean.TRUE);
                            break;
                        }
                    }
                }
            }

            for (PubPost pubPost : pubPosts) {
                if (Boolean.FALSE.equals(idMaps.get(pubPost.getId()))) {
                    notifyList.add(pubPost);
                }
            }
        }
        setModel(notifyList);
        return buildDefaultHttpHeaders("list");
    }
    
    @Override
    @MetaData(value = "查看")
    public HttpHeaders view() {
    	String id = getParameter("id");
    	Assert.notNull(id, "参数[id]不能为空！");
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	PrvOperator oper = null;
    	try {
    		oper = (PrvOperator) persistentService.find(PrvOperator.class, loginInfo.getOperid());
		} catch (Exception e) {
			
		}
        PubPostRead pubPostRead = pubPostReadService.findReaded(oper, bindingEntity);
        if (pubPostRead == null) {
            pubPostRead = new PubPostRead();
            pubPostRead.setFirstReadTime(new Date());
            pubPostRead.setReadTotalCount(1);
            pubPostRead.setReadUser(oper);
            pubPostRead.setPubPost(bindingEntity);

            if (bindingEntity.getReadUserCount() == null) {
                bindingEntity.setReadUserCount(1);
            } else {
                bindingEntity.setReadUserCount(bindingEntity.getReadUserCount() + 1);
            }
        } else {
            pubPostRead.setLastReadTime(new Date());
            pubPostRead.setReadTotalCount(pubPostRead.getReadTotalCount() + 1);
        }
        pubPostReadService.save(pubPostRead);
        pubPostService.save(bindingEntity);
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<Serializable, Boolean> idMaps = (Map<Serializable, Boolean>) session.getAttribute(READED_PUB_POST_IDS);
        idMaps.put(bindingEntity.getId(), Boolean.TRUE);
        
        return buildDefaultHttpHeaders("view");
    }
    
    public HttpHeaders queryDepts() throws Exception {
        try {
            List<TreeMenuVO> treeList = prvDepartmentService.findTreeListByPubPost(bindingEntity.getId());
            setModel(OperationResult.buildSuccessResult("查询成功", treeList));
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult("查询失败：" + e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }
}
