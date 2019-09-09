package com.maywide.biz.inter.service;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.pojo.queoperator.OperatorBO;
import com.maywide.biz.inter.pojo.queoperator.QueryOperatorReq;
import com.maywide.biz.inter.pojo.queoperator.QueryOperatorResp;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.prv.entity.PrvOperrole;
import com.maywide.biz.prv.service.PrvOperroleService;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterOperService extends CommonService {
	@Autowired
	private PersistentService persistentService;
	@Autowired
    private PrvOperroleService prvOperroleService;
	
	@Transactional(readOnly = true)
	public ReturnInfo queryOper(QueryOperatorReq req, QueryOperatorResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        if (StringUtils.isBlank(req.getLoginname()) && StringUtils.isBlank(req.getName())) {
        	returnInfo.setCode(IErrorDefConstant.ERROR_PROPERTY_MISSING_CODE);
        	returnInfo.setMessage("匹配条件[Loginname]和[Name]不能同时为空");
        	
        	return returnInfo;
        }
        
        List<OperatorBO> opers = Lists.newArrayList();
        
        PrvOperator param = new PrvOperator();
        if (StringUtils.isNotBlank(req.getLoginname())) {
        	param.setLoginname(req.getLoginname());
        }
        if (StringUtils.isNotBlank(req.getName())) {
        	param.setName(req.getName());
        }
        
        Page page = new Page();
        page.setPageSize(req.getPagesize());
        page.setPageNo(req.getCurrentPage());
        
        page = persistentService.find(page, param);
        List<PrvOperator> list = page.getResult();
        List<PrvOperrole> roles = null;
        OperatorBO operBO = new OperatorBO();
        
        for (PrvOperator oper : list) {
        	try {
        		BeanUtils.copyProperties(operBO, oper);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	roles = persistentService.find("FROM PrvOperrole WHERE operid = ?", oper.getId());
        	String departname = "";
        	for (PrvOperrole role : roles) {
        		departname += role.getDepartment().getName() + ",";
        	}
        	if (departname.length() > 0) {
        		departname = departname.substring(0, departname.length() - 1);
        	}
        	operBO.setDepartments(departname);
        	opers.add(operBO);
        }
        
        resp.setOpers(opers);
        
        return returnInfo;
	}
}
