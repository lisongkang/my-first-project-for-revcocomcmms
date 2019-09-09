package com.maywide.biz.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.dao.PackageDao;
import com.maywide.biz.core.entity.ApkPackage;
import com.maywide.biz.core.entity.MenuConf;
import com.maywide.biz.core.entity.TabConfig;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.core.cons.Constant;

@Service
@Transactional(rollbackFor = Exception.class)
public class ParamSettingService extends CommonService {
	@Autowired
	private PackageDao packageDao;
	/**
	 * 查询APK包信息，包括版本信息，APK下载信息
	 * @param packages
	 * @return APK包信息                                                                                                                                                                                                                                                                                                                                                                                                                                           
	 * @throws Exception 
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queryPackages(ArrayList<ApkPackage> packages) throws Exception {		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		List<ApkPackage> list = getDAO().find(new ApkPackage());
		packages.addAll(list);
		
		return returnInfo;
	}
	
	/**
	 * 查询页面底部栏目显示配置信息
	 * @param tabs
	 * @return 底部栏目显示配置信息
	 * @throws Exception 
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queryAllTabs(ArrayList<TabConfig> tabs) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		TabConfig paramTab = new TabConfig();
		paramTab.set_orderby_tabIndex(Constant.ORDER_ASC);
		List<TabConfig> list = getDAO().find(paramTab);
		tabs.addAll(list);
	
		return returnInfo;
	}
	
	/**
	 * APK包下载地址
	 * @return APK包下载地址配置信息
	 */
	@Transactional(readOnly = true)
	public ReturnInfo getApkReleaseUrl() {
		ReturnInfo result = new ReturnInfo();
		result.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		result.setMessage(SysConfig.getApkReleaseUrl());
		
		return result;
	}
	
	/**
	 * 是否需要对终端设备验证	 
	 */
	@Transactional(readOnly = true)
	public ReturnInfo isNeedEnrollment() {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		result.setMessage(SysConfig.isNeedEnrollment() + "");
		
		return result;
	}
	
	/**
	 * 通用参数，可以扩展
	 * 0-测试 1-商用	 
	 */
	@Transactional(readOnly = true)
	public ReturnInfo getCommonFlag() {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		result.setMessage(SysConfig.getCommonFlag() + "");
		
		return result;
	}
	
	/**
	 * 查询菜单配置信息
	 * @param opCode
	 * @return 菜单配置信息
	 * @throws Exception 
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queryAllMenus(String opCode, ArrayList<MenuConf> menus) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		
		MenuConf paramMenuConf = new MenuConf();
		paramMenuConf.set_orderby_sortIndex(Constant.ORDER_ASC);
		List<MenuConf> list = getDAO().find(paramMenuConf);
		Set<Long> privIdSet = getOperPriv(opCode, list);
		
		menus.addAll(list);
		MenuConf menuConf = null;
		for (Iterator<MenuConf> it = menus.iterator(); it.hasNext();) {
			menuConf = it.next();
        	if (menuConf.getPrivId().longValue() <= 0L) continue;
        	if (!privIdSet.contains(menuConf.getPrivId())) {
        		it.remove();
        	}
        }
	
		
		return returnInfo;
	}
	
	private Set<Long> getOperPriv(String opCode, List<MenuConf> menus) {
		return new HashSet<Long>();
	}
	
}
