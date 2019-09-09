package com.maywide.biz.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.Rule;
import com.maywide.core.service.PersistentService;


@Service
@Transactional(rollbackFor = Exception.class)
public class RuleService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PersistentService dao;

	public List<Rule> getRuleData(String ruleName) throws Exception {
		Rule rule = new Rule();
		rule.setRuleName(ruleName);
		List<Rule> datas = dao.find(rule, true);
		return datas;
	}

	public List<Rule> getRuleData(Rule rule) throws Exception {
		List<Rule> datas = dao.find(rule, true);
		return datas;
	}

	public Rule getRuleById(Long id) throws Exception {
		Rule rule = new Rule();
		rule.setRuleId(id);
		List<Rule> datas = dao.find(rule, true);
		if (datas == null || datas.size() == 0) {
			return null;
		}
		return datas.get(0);
	}

	public Rule getRuleByName(String ruleName) throws Exception {
		List<Rule> datas = getRuleData(ruleName);
		if (datas == null || datas.size() == 0) {
			return null;
		}
		return datas.get(0);
	}

	public List<Rule> getCityRule(String city) throws Exception {
		List<Rule> datas = dao.findByProperty(Rule.class, "CITY", city);
		return datas;
	}

	public Rule getRuleByAreaId(String city, String areaid) throws Exception {
		Rule rule = new Rule();
		rule.setCity(city);
		List<Rule> datas = dao.find(rule);
		if (datas == null || datas.size() == 0) {
			return null;
		}
		rule.setAreaIds(areaid);
		datas = dao.find(rule);
		if (datas == null || datas.size() == 0) {
			return null;
		}
		return datas.get(0);
	}

	public Rule getRule(String city, String rule) throws Exception {
		Rule ruleData = new Rule();
		ruleData.setCity(city);
		ruleData.setRule(rule);
		List<Rule> datas = dao.find(ruleData, false);
		if (datas == null || datas.size() == 0) {
			return null;
		}
		return datas.get(0);
	}
	
	public Rule getRule(String ruleStr) throws Exception{
		Rule rule = new Rule();
		rule.setRule(ruleStr);
		List<Rule> datas = dao.find(rule);
		if(datas == null || datas.isEmpty()){
			return null;
		}
		return datas.get(0);
	}
	
	public Rule getRule(String ruleStr,String city,String permission) throws Exception{
		Rule rule = new Rule();
		rule.setCity(city);
		rule.setPerMission(permission);
		rule.setRule(ruleStr);
		List<Rule> datas = dao.find(rule);
		if(datas == null || datas.isEmpty()){
			return null;
		}
		return datas.get(0);
	}
	
	public Rule getCityRuleOrDefault(String ruleStr,String city) throws Exception {
		Rule rule = getRule(city,ruleStr);
		if(null == rule) {
			rule = getRule("*",ruleStr);
		}
		return rule;
	}

}
