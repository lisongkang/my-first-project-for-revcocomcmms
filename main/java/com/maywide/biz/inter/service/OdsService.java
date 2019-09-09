package com.maywide.biz.inter.service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.queryOdsLoginInfo.OdsBean;
import com.maywide.biz.inter.pojo.queryOdsLoginInfo.QueryOdsLoginInfoReq;
import com.maywide.biz.inter.pojo.queryOdsLoginInfo.QueryOdsLoginInfoResp;

import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OdsService extends CommonService {


    public ReturnInfo queryOdsLoginInfo(QueryOdsLoginInfoReq req,
                                        QueryOdsLoginInfoResp resp) throws Exception{

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);

        //查询数据库
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList();
        sb.append("	select a.operid bossId ,");
        sb.append("	a.loginname bossLoginname, ");
        sb.append("	b.staff_id ossId, ");
        sb.append("	b.username ossLoginname, ");
        sb.append("	a.UNIONNAME unionName");
        sb.append("	from BOSS_CRM.prv_operator a,");
        sb.append("	GDOSS.UOS_STAFF b,");
        sb.append("	GDOSS.oss_STAFF_info c ");
        sb.append("	where b.staff_id=c.system_account and ");
        sb.append(" a.loginname = ? and");
        sb.append("	c.union_id=a.UNIONNAME and ");
        sb.append("	a.UNIONNAME is not null ");

//        params.add(loginInfo.getOperid());

        params.add(req.getOperid());

        List<OdsBean> odsBeanList =  SpringBeanUtil.getOdsContext().find(sb.toString(),
                OdsBean.class,params.toArray());

        if(odsBeanList ==null || odsBeanList.size()==0){
            throw new BusinessException("当前工号未关联OSS账号，无法使用OSS易运营，请在主数据平台设置OSS账号关联！");
        }

        resp.setOdsBeanList(odsBeanList);
        resp.setOssLoginId(odsBeanList.get(0).getOssId());

        return returnInfo;
    }



    public ReturnInfo queryServiceInfo(QueryOdsLoginInfoReq req,
                                        QueryOdsLoginInfoResp resp) throws Exception{

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);

        //查询数据库
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList();
        sb.append("	select a.operid bossId ,");
        sb.append("	a.loginname bossLoginname, ");
        sb.append("	d.staff_id ossId, ");
        sb.append("	d.staff_id ossLoginname, ");
        sb.append("	a.UNIONNAME unionName");
        sb.append("	from BOSS_CRM.prv_operator a,");
        sb.append("	GDDSW.SYS_STAFF d");
        sb.append("	where a.UNIONNAME=d.alias and");
        sb.append(" a.loginname = ? and");
        sb.append("	a.unionname is not null");


//        params.add(loginInfo.getLoginname());

        params.add(req.getOperid());

        List<OdsBean> odsBeanList =  SpringBeanUtil.getOdsContext().find(sb.toString(),
                OdsBean.class,params.toArray());

        if(odsBeanList ==null || odsBeanList.size()==0){
            throw new BusinessException("当前工号未关联OSS账号，无法使用OSS易运营，请在主数据平台设置OSS账号关联！");
        }

        resp.setOdsBeanList(odsBeanList);
        resp.setServiceId(odsBeanList.get(0).getOssLoginname());

        return returnInfo;
    }

}
