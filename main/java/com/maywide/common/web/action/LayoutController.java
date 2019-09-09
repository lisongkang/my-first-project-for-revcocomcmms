package com.maywide.common.web.action;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.prv.service.LoginService;
import com.maywide.biz.prv.service.PrvMenuService;
import com.maywide.biz.prv.service.PrvOperatorService;
import com.maywide.common.ctx.DynamicConfigService;
import com.maywide.common.pubpost.entity.PubPost;
import com.maywide.common.pubpost.entity.PubPostRead;
import com.maywide.common.pubpost.service.PubPostReadService;
import com.maywide.common.pubpost.service.PubPostService;
import com.maywide.common.sys.vo.NavMenuVO;
import com.maywide.core.cons.Constant;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.security.AuthUserDetails;
import com.maywide.core.service.PersistentService;
import com.maywide.core.service.Validation;
import com.maywide.core.util.PropertyUtil;
import com.maywide.core.web.SimpleController;
import com.maywide.core.web.view.OperationResult;

/**
 * 全局布局处理
 */
public class LayoutController extends SimpleController {
	
	private final static String READED_PUB_POST_IDS = "READED_PUB_POST_IDS";
	private final static String SESSION_KEY_LOCKED  = "SESSION_KEY_LOCKED";

    @Autowired
    private DynamicConfigService dynamicConfigService;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private PrvMenuService prvMenuService;
    
  /*  @Autowired
    private BaseService baseService;*/
    
    @Autowired
    private PubPostService pubPostService;
    
    @Autowired
    private PubPostReadService pubPostReadService;
    
    @Autowired
	private PersistentService persistentService;
    
    @Autowired
	private PrvOperatorService prvOperatorService;

    public String getSystemTitle() {
    	String systemTitle = PropertyUtil.getValueFromProperites("sysconfig", "system.title");
        return StringUtils.isBlank(systemTitle) ? "" : systemTitle;
    }

    public AuthUserDetails getAuthUserDetails() {
        return AuthContextHolder.getAuthUserDetails();
    }

    public String getBaiduMapAppid() {
        return dynamicConfigService.getString("baidu.map.appid");
    }

    public HttpHeaders index() {
        return start();
    }
    
    public HttpHeaders start() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        
        try {
        	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        	if (loginInfo == null) {
        		throw new Exception("用户未登录或已过期");
        	}
        	List<NavMenuVO> menus = prvMenuService.findMenus(loginInfo.getOperid(), 
        			loginInfo.getRoleid(), Constant.DEFAULT_SYSID, true);
        	/*List<NavMenuVO> menus = baseService.findMenus(loginInfo.getOperid(), 
        			loginInfo.getRoleid());*/
    		request.setAttribute("rootMenus", menus);
    		String str = JSONUtil.serialize(loginInfo);
    		request.setAttribute("LOGIN_INFO", JSONUtil.serialize(loginInfo));
    		//获取公告
    		Map<Serializable, Boolean> idMaps = (Map<Serializable, Boolean>) session.getAttribute(READED_PUB_POST_IDS);
            if (idMaps == null) {
                idMaps = Maps.newHashMap();
                session.setAttribute(READED_PUB_POST_IDS, idMaps);
            }
            List<PubPost> pubPosts = pubPostService.findPublished(loginInfo);
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
                request.setAttribute("notifyListSize", notifyList.size());
                request.setAttribute("notifyList", notifyList);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	setModel(OperationResult.buildFailureResult(e.getMessage()));
			return buildDefaultHttpHeaders("start");
		}
        
        return buildDefaultHttpHeaders("start");
    }

    public HttpHeaders dashboard() {
        return buildDefaultHttpHeaders("dashboard");
    }

    public HttpHeaders exception() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        if (type != null) {
            throw new IllegalArgumentException("Mock Exception");
        }
        return buildDefaultHttpHeaders("mock");
    }
    
    public HttpHeaders lock() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        Assert.notNull(AuthContextHolder.getLoginInfo());
        session.setAttribute(SESSION_KEY_LOCKED, true);
        setModel(OperationResult.buildSuccessResult("会话已锁定"));
        
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders unlock() {
    	HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        Assert.isTrue(session.getAttribute(SESSION_KEY_LOCKED) != null, "Session unlocked");
        String password = request.getParameter("password");
        Validation.isTrue(prvOperatorService.validatePassword(AuthContextHolder.getLoginInfo().getOperid(), password), "密码不正确");
        session.removeAttribute(SESSION_KEY_LOCKED);
        setModel(OperationResult.buildSuccessResult("会话已解锁"));
        
        return buildDefaultHttpHeaders();
    }
}
