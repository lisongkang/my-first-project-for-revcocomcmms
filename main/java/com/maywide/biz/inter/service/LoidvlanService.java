package com.maywide.biz.inter.service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.queLoid.*;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoidvlanService extends CommonService {


    public ReturnInfo queLoid(QueLoidReq req, QueLoidResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求信息不能为空");

        if (StringUtils.isBlank(req.getDevno()) && StringUtils.isBlank(req.getServid())) {
            CheckUtils.checkNull(null, "用户编号和设备编号两个必须传一个");
        }


        String result = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_LOID, req, loginInfo);


        getRespByBoss(resp, result);


        return returnInfo;
    }


    private void getRespByBoss(QueLoidResp resp, String json) throws Exception {

        QueLoidBossResp bossResp = (QueLoidBossResp) BeanUtil.jsonToObject(json, QueLoidBossResp.class);

        List<QueLoidData> queLoidDataList = new ArrayList<>();
        if (bossResp != null && StringUtils.isNotBlank(bossResp.getLoid())) {
            QueLoidData queLoidData = new QueLoidData();
            queLoidData.setLoidName("Loid:");
            queLoidData.setValue(bossResp.getLoid());
            queLoidData.setType("1");

            queLoidDataList.add(queLoidData);

            for (VlanInfo vlan : bossResp.getVlanlist()) {
                QueLoidData queLoidData1 = new QueLoidData();
                queLoidData1.setLoidName("Vlan:");
                queLoidData1.setValue(vlan.getVlan());
                queLoidData1.setType("2");
                queLoidData1.setPermark(vlan.getPermark());

                queLoidDataList.add(queLoidData1);
            }

        }

        resp.setQueLoidDataList(queLoidDataList);


    }
}
