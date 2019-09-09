package com.maywide.biz.prd.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.prd.dao.CatalogDao;
import com.maywide.biz.prd.entity.Catalog;
import com.maywide.biz.prd.entity.CatalogCondtion;
import com.maywide.biz.prd.entity.CatalogItem;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
@Service
@Transactional(rollbackFor = Exception.class)
public class CatalogService extends BaseService<Catalog, Long> {
	public final static String CONDTION_TYPE_OPER = "0";
	public final static String CONDTION_TYPE_OPCODE="2";
	
	@Autowired
	private CatalogDao catalogDao;
	
	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private ParamService paramService;
	
	@Override
    protected BaseDao<Catalog, Long> getEntityDao() {
        return catalogDao;
    }
	
	@Override
	public Catalog save(Catalog entity){
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(entity.isNew()){
			entity.setCreateoper(loginInfo.getOperid());
			entity.setCreatetime(new Date());
		} else {
			entity.setUpdateoper(loginInfo.getOperid());
			entity.setUpdatetime(new Date());
		}
		
		super.save(entity);
		
		return entity;
	}
	
	public void transCondtionList(List<CatalogCondtion> list) throws Exception {
		for (CatalogCondtion condtion : list) {
			transCondtion(condtion);
		}
	}
	
	public void transCondtion(CatalogCondtion condtion) throws Exception {
		condtion.addExtraAttribute("condtionvalue", getCondtionname(condtion.getContiontype(), condtion.getContionvalue()));
	}
	
	public void transKnowList(List<CatalogItem> list) throws Exception {
		for (CatalogItem condtion : list) {
			transKnow(condtion);
		}
	}
	
	public void transKnow(CatalogItem item) throws Exception {
		SalespkgKnow know = (SalespkgKnow) persistentService.find(SalespkgKnow.class, new Long(item.getKnowid().toString()));
		if (know != null) {
			item.addExtraAttribute("knowname", know.getKnowname());
		}
	}
	
	private String getCondtionname(String condtiontype, String condtionvalue) {
		try {
			if (CONDTION_TYPE_OPER.equals(condtiontype)) {
				PrvOperator oper = (PrvOperator) persistentService.find(PrvOperator.class, Long.parseLong(condtionvalue));
				if (oper != null) {
					return oper.getLoginname();
				}
			}//增加业务操作码限制
			else if(CONDTION_TYPE_OPCODE.equals(condtiontype)){
				PrvSysparam param = paramService.getData(BizConstant.SysparamGcode.BIZ_OPCODE,condtionvalue);
				if (param != null) {
					return param.getMname();
				}
			}else {
				PrvDepartment department = (PrvDepartment) persistentService.find(PrvDepartment.class, Long.parseLong(condtionvalue));
				if (department != null) {
					return department.getName();
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "";
	}

	public Map<String, String> findBizopcodesMap() throws Exception {
		Map<String, String> bizopcodeMap = new LinkedHashMap<String, String>();
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		//1.先获取地市的规则
		Rule rule = ruleService.getRule(BizConstant.BizRuleParams.GOODS_BIZCODES,loginInfo.getCity(),BizConstant.BizRuleParams.PERMISSION_TYPE_Y);
		//如果为空，获取默认所有地市的规则
		if(null==rule){
			rule =  ruleService.getRule(BizConstant.BizRuleParams.GOODS_BIZCODES,BizConstant.BizRuleParams.DEFAULTCITY,BizConstant.BizRuleParams.PERMISSION_TYPE_Y);
		}
		CheckUtils.checkNull(rule, "目录关联业务操作规则未配置，请联系管理员先配置");
		
		String opcodeStr = rule.getValue();
		String[] opcodeMcodes = opcodeStr.split(",");
		
		List<PrvSysparam> params = paramService.getData(BizConstant.SysparamGcode.BIZ_OPCODE);
		//bizopcodeMap.put("*", "全部");
		for(String mcode:opcodeMcodes){
			for(PrvSysparam param : params){
				if(param.getMcode().equals(mcode)){
					bizopcodeMap.put(mcode, param.getMname());
					break;
				}
			}
		}
		
		return bizopcodeMap;
	}
	
}
