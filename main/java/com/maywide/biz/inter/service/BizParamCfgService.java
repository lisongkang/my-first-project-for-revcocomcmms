package com.maywide.biz.inter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.QueryCustInfoReq;
import com.maywide.biz.inter.pojo.QueryUserInfoReq;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeInterReq;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeInterResp;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeUapReq;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeUapResp;
import com.maywide.biz.inter.pojo.bizchgoperatorpwd.BizChgOperatorPwdBossReq;
import com.maywide.biz.inter.pojo.bizchgoperatorpwd.BizChgOperatorPwdInterReq;
import com.maywide.biz.inter.pojo.bizchgoperatorpwd.BizChgOperatorPwdInterResp;
import com.maywide.biz.inter.pojo.createcust.CreateCustBossReq;
import com.maywide.biz.inter.pojo.createcust.CreateCustBossResp;
import com.maywide.biz.inter.pojo.createcust.CreateCustInterReq;
import com.maywide.biz.inter.pojo.createcust.CreateCustInterResp;
import com.maywide.biz.inter.pojo.createcust.CustAttrInterInfo;
import com.maywide.biz.inter.pojo.createcust.CustInfoBO;
import com.maywide.biz.inter.pojo.createcust.CustPubInfoInterInfo;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeInterReq;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeInterResp;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeUapReq;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeUapResp;
import com.maywide.biz.inter.pojo.queapplyknowdet.ApplyKnowDetBO;
import com.maywide.biz.inter.pojo.queapplyknowdet.QueApplyKnowDetInterReq;
import com.maywide.biz.inter.pojo.queapplyknowdet.QueApplyKnowDetInterResp;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetBossReq;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetBossResp;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetInterReq;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetInterResp;
import com.maywide.biz.inter.pojo.quecustbank.QueCustbankBossReq;
import com.maywide.biz.inter.pojo.quecustbank.QueCustbankBossResp;
import com.maywide.biz.inter.pojo.quecustbank.QueCustbankInterReq;
import com.maywide.biz.inter.pojo.quecustbank.QueCustbankInterResp;
import com.maywide.biz.inter.pojo.quecustorder.ApplyArrearBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyBankBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyInstallBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyProductBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyRefreshBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyTmpopenBO;
import com.maywide.biz.inter.pojo.quecustorder.CustorderDetBO;
import com.maywide.biz.inter.pojo.quecustorder.CustordersBO;
import com.maywide.biz.inter.pojo.quecustorder.QueCustorderInterReq;
import com.maywide.biz.inter.pojo.quecustorder.QueCustorderInterResp;
import com.maywide.biz.inter.pojo.quecustorder.SortConditionsBO;
import com.maywide.biz.inter.pojo.quedata.QueDataInterReq;
import com.maywide.biz.inter.pojo.quedata.QueDataInterResp;
import com.maywide.biz.inter.pojo.quegridmanager.GridmanagersBO;
import com.maywide.biz.inter.pojo.quegridmanager.QueGridmanagerInterReq;
import com.maywide.biz.inter.pojo.quegridmanager.QueGridmanagerInterResp;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseBossReq;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseBossResp;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseInterReq;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseInterResp;
import com.maywide.biz.inter.pojo.querysalespkgknow.KnowObjDet;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgDetailBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgSelectDetsBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgSelectsBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgSoftsBO;
import com.maywide.biz.inter.pojo.queservstinfo.QueConditionsBO;
import com.maywide.biz.inter.pojo.queservstinfo.QueServstInfoBossReq;
import com.maywide.biz.inter.pojo.queservstinfo.QueServstInfoBossResp;
import com.maywide.biz.inter.pojo.queservstinfo.QueServstInfoInterReq;
import com.maywide.biz.inter.pojo.queservstinfo.QueServstInfoInterResp;
import com.maywide.biz.inter.pojo.quetempopenplan.QueTempopenPlanBossReq;
import com.maywide.biz.inter.pojo.quetempopenplan.QueTempopenPlanBossResp;
import com.maywide.biz.inter.pojo.quetempopenplan.QueTempopenPlanInterReq;
import com.maywide.biz.inter.pojo.quetempopenplan.QueTempopenPlanInterResp;
import com.maywide.biz.inter.pojo.queuserpkg.QueUserPkgBossReq;
import com.maywide.biz.inter.pojo.queuserpkg.QueUserPkgBossResp;
import com.maywide.biz.inter.pojo.queuserpkg.QueUserPkgInterReq;
import com.maywide.biz.inter.pojo.queuserpkg.QueUserPkgInterResp;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossReq;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossResp;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdInterReq;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdInterResp;
import com.maywide.biz.inter.pojo.sendrandomcode.SendRandomCodeInterReq;
import com.maywide.biz.inter.pojo.sendrandomcode.SendRandomCodeInterResp;
import com.maywide.biz.inter.pojo.sendrandomcode.SendRandomCodeUapReq;
import com.maywide.biz.inter.pojo.sendrandomcode.SendRandomCodeUapResp;
import com.maywide.biz.market.entity.ApplyProduct;
import com.maywide.biz.market.entity.ApplyProductSelect;
import com.maywide.biz.market.entity.ApplyProductSoft;
import com.maywide.biz.market.entity.Grid;
import com.maywide.biz.market.entity.GridManager;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.MD5;

@Service
@Transactional(rollbackFor = Exception.class)
public class BizParamCfgService extends CommonService {
    @Autowired
    private ParamService paramService;

    // 获取业务配置参数 begin
    public String getBizParamCfg(String gcode, String objid, LoginInfo loginInfo)
            throws Exception {
        // 通过city~areaid~deptid~operid~objid配置业务特定的配置参数，gcode=参数名，mcode=city~areaid~deptid~operid,mname=参数值
        CheckUtils.checkNull(loginInfo, "获取业务配置参数:登录信息不能为空");
        CheckUtils.checkEmpty(loginInfo.getCity(), "获取业务配置参数:登录地市不能为空");
        CheckUtils.checkNull(loginInfo.getOperid(), "获取业务配置参数:登录操作员id不能为空");
        CheckUtils.checkNull(loginInfo.getAreaid(), "获取业务配置参数:登录业务区id不能为空");
        CheckUtils.checkNull(loginInfo.getDeptid(), "获取业务配置参数:登录部门id不能为空");
        CheckUtils.checkNull(gcode, "获取业务配置参数:参数名不能为空");

        // 需要额外参与参数控制的对象id
        String defObjid = "*";
        if (StringUtils.isNotBlank(objid)) {
            defObjid = objid;
        }

        String paramCtlLevel = BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_DEFAUL;
        String ctlLevel = getBizParamCtlLevel(gcode);
        if (StringUtils.isNotBlank(ctlLevel)) {
            paramCtlLevel = ctlLevel;
        }

        String retBizAcceptRight = "";
        if (BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_DEFAUL
                .equals(paramCtlLevel)) {
            // 查操作员级别的
            retBizAcceptRight = paramService.getMname(
                    gcode,
                    loginInfo.getCity() + "~" + loginInfo.getAreaid() + "~"
                            + loginInfo.getDeptid() + "~"
                            + loginInfo.getOperid() + "~" + defObjid);
            if (StringUtils.isBlank(retBizAcceptRight)) {
                // 查部门级别的
                retBizAcceptRight = paramService.getMname(gcode,
                        loginInfo.getCity() + "~" + loginInfo.getAreaid() + "~"
                                + loginInfo.getDeptid() + "~*~" + defObjid);
            }
            if (StringUtils.isBlank(retBizAcceptRight)) {
                // 查业务区级别的
                retBizAcceptRight = paramService.getMname(gcode,
                        loginInfo.getCity() + "~" + loginInfo.getAreaid()
                                + "~*~*~" + defObjid);
            }
            if (StringUtils.isBlank(retBizAcceptRight)) {
                // 查业地市级别的
                retBizAcceptRight = paramService.getMname(gcode,
                        loginInfo.getCity() + "~*~*~*~" + defObjid);
            }

        } else {
            String mcode = "";
            String defCity = "*";
            String defArea = "*";
            String defDept = "*";
            String defOper = "*";
            // String defObjid = "*";
            String seperator = "~";
            if (BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_CITY
                    .equals(paramCtlLevel)) {

                defCity = loginInfo.getCity();
            } else if (BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_AREA
                    .equals(paramCtlLevel)) {

                defArea = loginInfo.getAreaid().toString();
            } else if (BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_DEPT
                    .equals(paramCtlLevel)) {

                defDept = loginInfo.getDeptid().toString();
            } else if (BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_OPER
                    .equals(paramCtlLevel)) {

                defOper = loginInfo.getOperid().toString();
            } else if (BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_DEPTOPER
                    .equals(paramCtlLevel)) {

                defDept = loginInfo.getDeptid().toString();
                defOper = loginInfo.getOperid().toString();
            } else if (BizConstant.BizParamCtlLevel.BIZ_PARAM_CTL_LEVEL_OBJID
                    .equals(paramCtlLevel)) {
                defObjid = objid;
            }

            mcode = defCity + seperator + defArea + seperator + defDept
                    + seperator + defOper + seperator + defObjid;

            retBizAcceptRight = paramService.getMname(gcode, mcode);

        }

        if (StringUtils.isBlank(retBizAcceptRight)) {
            // 再查询一次默认值配置
            retBizAcceptRight = paramService.getMname(gcode, "*~*~*~*~*");
        }

        // if (StringUtils.isBlank(retBizAcceptRight)) {
        // throw new BusinessException("业务参数有误，请联系管理员");
        // }

        return retBizAcceptRight;

    }

    private String getBizParamCtlLevel(String paramGcode) throws Exception {
        String gcode = BizConstant.BizParamCfgName.BIZ_PARAM_CTL_LEVEL;
        String retBizParamCtlLevel = paramService.getMname(gcode, paramGcode);

        return retBizParamCtlLevel;
    }

    // 获取业务配置参数 end
    
    public String getGridTypeBiztypeByCfg(LoginInfo loginInfo) throws Exception {
        // 不同的地市，先只区分到city级别，通过参数BIZ_GRIDOBJ_TYPE_CFG来配置
        // 后续可新增一个配置关系表 biz_gridobj_type_cfg(city、areaid、objtype)

        String gcode = BizConstant.BizParamCfgName.BIZ_GRID_TYPE_BIZTYPE_CFG;
        String retGridypeBiztype = getBizParamCfg(gcode, null, loginInfo);

//先注释掉
//        if (StringUtils.isBlank(retGridypeBiztype)) {
//            throw new BusinessException("默认业务网格类型["
//                    + BizConstant.BizParamCfgName.BIZ_GRID_TYPE_BIZTYPE_CFG
//                    + "]配置参数配置有误，请联系管理员");
//        }

        return retGridypeBiztype;

    }

    public String getGridobjTypeByCfg(LoginInfo loginInfo) throws Exception {
        // 不同的地市，先只区分到city级别，通过参数BIZ_GRIDOBJ_TYPE_CFG来配置
        // 后续可新增一个配置关系表 biz_gridobj_type_cfg(city、areaid、objtype)

        String gcode = BizConstant.BizParamCfgName.BIZ_GRID_OBJTYPE_CFG;
        String retGridobjType = getBizParamCfg(gcode, null, loginInfo);

        if (StringUtils.isBlank(retGridobjType)) {
            throw new BusinessException("网格对象类型参数["
                    + BizConstant.BizParamCfgName.BIZ_GRID_OBJTYPE_CFG
                    + "]配置参数配置有误，请联系管理员");
        }

        return retGridobjType;

    }

    public String getBizAcceptRight(LoginInfo loginInfo) throws Exception {
        // --先通过参数来配置
        // 不同的地市，先只区分到city级别，通过参数BIZ_OPERATOR_BIZRIGHT_CFG来配置
        // 后续可以新增一个配置关系表BIZ_OPERATOR_RIGHT_CFG

        String gcode = BizConstant.BizParamCfgName.BIZ_RIGHT_BIZACCEPT_CFG;
        String retBizAcceptRight = getBizParamCfg(gcode, null, loginInfo);

        if (StringUtils.isBlank(retBizAcceptRight)) {
            throw new BusinessException("操作员受理权限参数["
                    + BizConstant.BizParamCfgName.BIZ_RIGHT_BIZACCEPT_CFG
                    + "]配置有误，请联系管理员");
        }

        return retBizAcceptRight;

    }

    // 操作员点通次数据控制
    public String getTmpopenNumRight(LoginInfo loginInfo) throws Exception {

        // --先通过参数来配置
        // 不同的地市，先只区分到city级别，通过参数BIZ_OPERATOR_BIZRIGHT_CFG来配置
        // 后续可以新增一个配置关系表BIZ_OPERATOR_RIGHT_CFG

        String gcode = BizConstant.BizParamCfgName.BIZ_RIGHT_TMPOPEN_NUM_CFG;
        String retTmpopenNum = getBizParamCfg(gcode, null, loginInfo);

        if (StringUtils.isBlank(retTmpopenNum)) {
            throw new BusinessException("操作员点通次数参数["
                    + BizConstant.BizParamCfgName.BIZ_RIGHT_TMPOPEN_NUM_CFG
                    + "]配置有误，请联系管理员");
        }

        return retTmpopenNum;

    }

    // 操作员同一方案点通次数据控制
    public String getSamePlanTmpopenNumRight(String objid, LoginInfo loginInfo)
            throws Exception {

        // --先通过参数来配置
        // 不同的地市，先只区分到city级别，通过参数BIZ_OPERATOR_BIZRIGHT_CFG来配置
        // 后续可以新增一个配置关系表BIZ_OPERATOR_RIGHT_CFG

        String gcode = BizConstant.BizParamCfgName.BIZ_RIGHT_SAMEPLAN_TMPOPEN_NUM_CFG;
        String retTmpopenNum = getBizParamCfg(gcode, objid, loginInfo);

        if (StringUtils.isBlank(retTmpopenNum)) {
            throw new BusinessException(
                    "操作员同一方案点通次数参数["
                            + BizConstant.BizParamCfgName.BIZ_RIGHT_SAMEPLAN_TMPOPEN_NUM_CFG
                            + "]配置有误，请联系管理员");
        }

        return retTmpopenNum;

    }

}
