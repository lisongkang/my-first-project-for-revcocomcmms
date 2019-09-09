package com.maywide.biz.az.constructors.web.action;

import com.google.common.collect.Maps;
import com.maywide.biz.az.constructors.entity.PrvOperatorInfo;
import com.maywide.biz.az.constructors.pojo.AzConstParamsBo;
import com.maywide.biz.az.constructors.service.AzConstructorsService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lisongkang on 2019/4/16 0001.
 */
@Results({
        @Result(name = "success", location="/az/constructors/az-constructors-index.jsp")
})
public class AzConstructorsController extends BaseController<PrvOperatorInfo,Long> {
    @Autowired
    private AzConstructorsService azConstructorsService;
    private AzConstParamsBo azConstParamsBo;
    @Autowired
    private PersistentService persistentService;

    @Autowired
    private ParamService paramService;
    @Override
    protected BaseService<PrvOperatorInfo, Long> getEntityService() {

        return azConstructorsService;
    }

    @Override
    protected void checkEntityAclPermission(PrvOperatorInfo entity) {
        // TODO Add acl check code logic
    }
    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        // TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }
    /**
     * 删除定额信息
     */
    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }
    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
        return super.doSave();
    }
    /**
     * 编辑
     * @return
     */
    @Override
    @MetaData("打开编辑表单")
    public HttpHeaders edit() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders("inputBasic");
    }

    /**
     * 施工人信息首页
     * @return
     */
    public String constindex(){
        return "success";
    }


    @Override
    public HttpHeaders findByPage(){
        List<PrvOperatorInfo> list = new ArrayList<PrvOperatorInfo>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        String loginname = getParameter("AzConstParamsBo.loginname");
        String name = getParameter("AzConstParamsBo.name");
        try{
            Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if(loginInfo.getOperid() == null || loginInfo.getOperid().equals("")){
                setModel(OperationResult.buildFailureResult("操作员未登录", errorMessageMap));
            }
            azConstParamsBo = new AzConstParamsBo();
            if(StringUtils.isNotEmpty(loginname)){
                azConstParamsBo.setLoginname(loginname);
            }
            if(StringUtils.isNotEmpty(name)){
                azConstParamsBo.setName(name);
            }
            setModel(azConstructorsService.findByPage(azConstParamsBo,pageable));

        }catch(Exception e){
            e.printStackTrace();
            setModel(new PageImpl<PrvOperatorInfo>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }

    public AzConstParamsBo getAzConstParamsBo() {
        return azConstParamsBo;
    }

    public void setAzConstParamsBo(AzConstParamsBo azConstParamsBo) {
        this.azConstParamsBo = azConstParamsBo;
    }
}
