package com.maywide.biz.memo.web.action;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.memo.entity.BizMemoCfg;
import com.maywide.biz.memo.pojo.BizMemoCfgParamBo;
import com.maywide.biz.memo.service.BizMemoCfgService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

@MetaData("[com.maywide].biz.memo.entity.BizMemoCfg管理")
public class BizMemoCfgController extends BaseController<BizMemoCfg, Long> {

    @Autowired
    private BizMemoCfgService bizMemoCfgService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private ParamService paramService;

    // 问卷调查查询条件注入类
    private BizMemoCfgParamBo bizMemoCfgParamBo;

    public BizMemoCfgParamBo getBizMemoCfgParamBo() {
        return bizMemoCfgParamBo;
    }

    public void setBizMemoCfgParamBo(BizMemoCfgParamBo bizMemoCfgParamBo) {
        this.bizMemoCfgParamBo = bizMemoCfgParamBo;
    }

    @Override
    protected BaseService<BizMemoCfg, Long> getEntityService() {
        return bizMemoCfgService;
    }

    @Override
    protected void checkEntityAclPermission(BizMemoCfg entity) {

    }

    @Override
    @MetaData("打开编辑表单")
    public HttpHeaders edit() {
        try{
            String city=AuthContextHolder.getLoginInfo().getCity();
            String id = getParameter("id");
            BizMemoCfg bizMemoCfg=new BizMemoCfg();
            bizMemoCfg.setCity(city);
            if(id != null &&!id.equals("")){
                bizMemoCfg=bizMemoCfgService.findOne(Long.parseLong(id));
            }
            setModel(bizMemoCfg);
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        return buildDefaultHttpHeaders("inputBasic");
    }

    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
        try {
            bindingEntity.setOperator(AuthContextHolder.getLoginInfo().getOperid());
            bindingEntity.setIntime(new Date());

             getEntityService().save(bindingEntity);
            setModel(OperationResult.buildSuccessResult("保存成功", bindingEntity));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData("分页查询")
    public HttpHeaders findByPage() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            PageImpl<BizMemoCfg> list = bizMemoCfgService.queryBizSurveyList(bizMemoCfgParamBo, pageable);
            for (BizMemoCfg bizMemoCfg : list) {
                if (StringUtils.isBlank(bizMemoCfg.getAreanames())) {
                    bizMemoCfg.setAreanames("全部业务区");
                }
            }
            setModel(list);
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    //获取地市
    public Map<String, String> getCityMap() throws Exception {
        return paramService.getSelectData("PRV_CITY");
    }

    //获取业务区
    public Map<String, String> getTownMap() {
        Map<String, String> townMap = new LinkedHashMap<String, String>();
        String hql = "FROM PrvArea WHERE city = ? ORDER BY id";
        List<PrvArea> areaList;
        try {
            areaList = persistentService.find(hql, AuthContextHolder.getLoginInfo().getCity());

            Collections.sort(areaList, new Comparator() {
                public int compare(Object o1, Object o2) {
                    PrvArea param1 = (PrvArea) o1;
                    PrvArea param2 = (PrvArea) o2;

                    String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getName());
                    String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getName());

                    int rtnval = pinyin1.compareTo(pinyin2);

                    return rtnval;
                }
            });
            townMap.put("*", "全部业务区");
            for (PrvArea param : areaList) {
                townMap.put(param.getId().toString(), param.getName());
            }

            return townMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return townMap;

    }


    //获取业务区
    public Map<String, String> getOpcodesMap() throws Exception {
        return paramService.getSelectData("BIZ_OPCODE");
    }

}