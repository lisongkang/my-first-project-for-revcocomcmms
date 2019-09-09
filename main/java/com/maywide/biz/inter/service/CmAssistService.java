package com.maywide.biz.inter.service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.CmAssist.CmAssistReq;
import com.maywide.biz.inter.pojo.CmAssist.CmAssistResp;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import org.springframework.stereotype.Service;

/**
 * @author wzy
 */
@Service
public class CmAssistService extends CommonService {

    /**
     * 查询宽带信息接口
     *
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queryCmAssist(CmAssistReq req, CmAssistResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);

        // 调boss接口,并将结果存入resp
        callBossInf4queryCmAssist(req, resp,loginInfo);
        return returnInfo;
    }

    private void callBossInf4queryCmAssist(CmAssistReq req, CmAssistResp resp,LoginInfo loginInfo)
            throws Exception {


        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                BizConstant.BossInterfaceService.QUE_CMASSIST, req, loginInfo);

        if (bossRespOutput!=null){
            CmAssistResp bossResp= (CmAssistResp) BeanUtil.jsonToObject(bossRespOutput,
                    CmAssistResp.class);
            BeanUtils.copyProperties(resp, bossResp);
        }

    }
}
