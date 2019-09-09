package com.maywide.biz.survey.web.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.survey.entity.BizSurveyList;
import com.maywide.biz.survey.pojo.BizSurveyListParamBo;
import com.maywide.biz.survey.service.BizSurveyListService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.survey.entity.BizSurveyList管理")
public class BizSurveyListController extends BaseController<BizSurveyList, Long> {

    @Autowired
    private BizSurveyListService bizSurveyListService;

    @Autowired
    private PersistentService    persistentService;
    
    // 问卷调查查询条件注入类
    private BizSurveyListParamBo bizSurveyListParamBo;

    public BizSurveyListParamBo getBizSurveyListParamBo() {
        return bizSurveyListParamBo;
    }

    public void setBizSurveyListParamBo(BizSurveyListParamBo bizSurveyListParamBo) {
        this.bizSurveyListParamBo = bizSurveyListParamBo;
    }

    @Override
    protected BaseService<BizSurveyList, Long> getEntityService() {
        return bizSurveyListService;
    }

    @Override
    protected void checkEntityAclPermission(BizSurveyList entity) {

    }

    @Override
    @MetaData("打开编辑表单")
    public HttpHeaders edit() {
        try {
            bindingEntity.setAreaids(bindingEntity.getAreaid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders("inputBasic");
    }

    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
        try {
            boolean isAddQuestion = bindingEntity.isNew();
            if (isAddQuestion) {
                bindingEntity.setOperator(AuthContextHolder.getLoginInfo().getOperid());
                bindingEntity.setIntime(new Date());
                bindingEntity.setStatus(0);
            }

            bindingEntity.setAreaid(bindingEntity.getAreaid().replaceAll(" ", "")); // 要先去掉页面传的分隔符中的空格
            if (bindingEntity.getAreaid().contains("*")) {
                bindingEntity.setAreaid("*");
            }
            getEntityService().save(bindingEntity);
            if (isAddQuestion) {
                bizSurveyListService.bindSurveyQuestion(bindingEntity.getId(), bindingEntity.getQids());
            }
            setModel(OperationResult.buildSuccessResult("问卷保存成功", bindingEntity));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        try {
            bizSurveyListService.deleteSurveyQuestion(getParameterIds());
            return super.doDelete();
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            PageImpl<BizSurveyList> list = bizSurveyListService.queryBizSurveyList(bizSurveyListParamBo, pageable);
            for (BizSurveyList bizSurveyList : list) {
                if (StringUtils.isBlank(bizSurveyList.getAreanames())) {
                    bizSurveyList.setAreanames("全部业务区");
                }
            }
            setModel(list);
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("把问卷状态改为已截止")
    public HttpHeaders stopSurveyBySid() {
        try {
            bizSurveyListService.stopSurveyBySid(getParameter("sid"));
            setModel(OperationResult.buildSuccessResult("问卷已截止", bindingEntity));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    /**
     * 获取问卷调查的状态
     * 
     * @return
     */
    public Map<String, String> getSurveyStatusMap() {
        Map<String, String> statusMap = new HashMap<String, String>();
        String using = BizConstant.SurveyStatus.USING;
        String stop = BizConstant.SurveyStatus.STOP;
        statusMap.put(using, "使用中");
        statusMap.put(stop, "已截止");
        return statusMap;
    }

    /**
     * 获取实名制的状态
     * 
     * @return
     */
    public Map<String, String> getRealNameStutusMap() {
        Map<String, String> statuMap = new HashMap<String, String>();
        String yes = BizConstant.RealNameStatus.YES;
        String no = BizConstant.RealNameStatus.NO;
        statuMap.put(yes, "是");
        statuMap.put(no, "否");
        return statuMap;
    }

    @SuppressWarnings("unchecked")
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
            for (PrvSysparam param : areaList) {
                areaMap.put(param.getMcode(), param.getDisplay());
            }

            return areaMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaMap;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getTownMap() {
        Map<String, String> townMap = new LinkedHashMap<String, String>();
        townMap.put("*", "全部业务区");

        String hql = "FROM PrvArea WHERE city = ? ORDER BY id";
        List<PrvArea> areaList;
        try {
            areaList = persistentService.find(hql, bindingEntity.getCity());

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

            for (PrvArea param : areaList) {
                townMap.put(param.getId().toString(), param.getName());
            }

            return townMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return townMap;
    }
    
    public HttpHeaders showQuestion(){
    	 String sid = getParameter("sid");
    	 getRequest().setAttribute("sid",sid);
    	 return buildDefaultHttpHeaders("quetionlist");
    }
}