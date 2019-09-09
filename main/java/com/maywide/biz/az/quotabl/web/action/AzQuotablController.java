package com.maywide.biz.az.quotabl.web.action;

import com.google.common.collect.Maps;
import com.maywide.biz.az.quotabl.entity.ConstSettleRatio;
import com.maywide.biz.az.quotabl.pojo.AzSerchBlParamsBo;
import com.maywide.biz.az.quotabl.service.AzQuotablService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * 定额比列配置模块
 * Created by lisongkang on 2019/4/12 0001.
 */
@Results({
        @Result(name = "successazde", location="/az/quotabl/az-quota-azblindex.jsp")
})
public class AzQuotablController extends BaseController<ConstSettleRatio,Long> {
    @Autowired
    private AzQuotablService azQuotablService;

    private AzSerchBlParamsBo azSerchBlParamsBo;

    @Autowired
    private PersistentService persistentService;

    @Autowired
    private ParamService paramService;
    @Override
    protected BaseService<ConstSettleRatio, Long> getEntityService() {
        return azQuotablService;
    }

    @Override
    protected void checkEntityAclPermission(ConstSettleRatio entity) {
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
        Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
        String quotaratio = getParameter("quotaratio");
        if(Double.parseDouble(quotaratio) > 0.5){
            setModel(OperationResult.buildFailureResult("地市定额比列不能超过省公司规定的0.5", errorMessageMap));
            return buildDefaultHttpHeaders("inputBasic");
        }else{
            return super.doSave();
        }
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
     * 定额比列配置首页
     */
    public String azblindex(){
        return "successazde";
    }
    /**
     *
     * 定额比列配置首页
     */

    public HttpHeaders findAzblByPage(){
        List<ConstSettleRatio> list = new ArrayList<ConstSettleRatio>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        String city = getParameter("AzSerchBlParamsBo.city");
        try{
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
                azSerchBlParamsBo = new AzSerchBlParamsBo();
                azSerchBlParamsBo.setCity(loginInfo.getCity());
            }
            setModel(azQuotablService.findAzblByPage(azSerchBlParamsBo,pageable,city));

        }catch(Exception e){
            e.printStackTrace();
            setModel(new PageImpl<ConstSettleRatio>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, String> getAreaMap() {
        String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
        List<PrvSysparam> areaList;
        Map<String, String> areaMap = new LinkedHashMap<String, String>();
        try {
            areaList = persistentService.find(hql, "PRV_CITY");
            Collections.sort(areaList, new Comparator() {
                public int compare(Object o1, Object o2) {
                    PrvSysparam param1 = (PrvSysparam) o1;
                    PrvSysparam param2 = (PrvSysparam) o2;

                    String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getMname());
                    String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getMname());

                    int rtnval = pinyin1.compareTo(pinyin2);

                    return rtnval;
                }
            });
            areaMap.put("*", "所有");
            for (PrvSysparam param : areaList) {
                areaMap.put(param.getMcode(), param.getDisplay());
            }

            return areaMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaMap;
    }
    public AzSerchBlParamsBo getAzSerchBlParamsBo() {
        return azSerchBlParamsBo;
    }

    public void setAzSerchBlParamsBo(AzSerchBlParamsBo azSerchBlParamsBo) {
        this.azSerchBlParamsBo = azSerchBlParamsBo;
    }
}
