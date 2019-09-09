package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.BizMoRecord;
import com.maywide.biz.inter.pojo.bizModuleMenu.BizModuleMenuReq;
import com.maywide.biz.inter.pojo.queModuleInfo.ModuleMenuInfo;
import com.maywide.biz.inter.pojo.queModuleInfo.QueModuleInfoReq;
import com.maywide.biz.inter.pojo.queModuleInfo.QueModuleInfoResp;
import com.maywide.biz.inter.pojo.queOperModeMenu.OperMenudef;
import com.maywide.biz.inter.pojo.queOperModeMenu.QueOperModeMenuReq;
import com.maywide.biz.inter.pojo.queOperModeMenu.QueOperModeMenuResp;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
public class ModuleService extends CommonService {
	
	@Autowired
	private CustSpreadService tmpService;

	/**
	 * 获取模块下的分类菜单
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queModuleInfo(QueModuleInfoReq req,QueModuleInfoResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		LoginInfo loginInfo = tmpService.getGridTestLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		String ccode = req.getCcode();
		if(StringUtils.isBlank(ccode)){
			ccode = "index";
		}
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.CNAME cName,b.ANAME aName,b.AID aId,c.BIZ_CODE mBizCode,c.DESCTIPT desctipt");
		sb.append("		,c.PNAME pName,c.URLADDR urlAddr");
		sb.append("		,c.SORT sort,d.bizcode bizCode");
		sb.append("		,d.linkurl linkUrl,d.name mName");
		sb.append("		,d.oflag1 oflag1,d.oflag2 oflag2");
		sb.append("		FROM sys_category a,sys_cata_relation b,sys_cat_menu c,prv_menudef d");
		sb.append("		WHERE a.CID = b.CID");
		sb.append("		AND b.AID = c.AID");
		sb.append("		AND c.MENUID = d.menuid");
		sb.append("		AND a.CCODE =  ?");
		params.add(ccode);
		sb.append("		AND c.CITY = ?");
		params.add(loginInfo.getCity());
		sb.append("		GROUP BY b.AID,c.MENUID");
		sb.append("		ORDER BY b.AID,c.SORT");
		
		List<ModuleMenuInfo> menus = getDAO().find(sb.toString(),ModuleMenuInfo.class, params.toArray());
		resp.setDatas(menus);
		return returnInfo;
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queOperModeMenu(QueOperModeMenuReq req,QueOperModeMenuResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		String sysid = req.getSysid();
		if(StringUtils.isBlank(sysid)){
			sysid = "20";
		}
		List params = new ArrayList();
		params.add("menu");
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT b.AID aid,d.menuid  id,d.premenuid premenuid,");
		sb.append("		d.name      name,               d.linkurl   linkurl,");
		sb.append("		d.iconid    iconid,               d.flag      flag,");
		sb.append("		d.prefix    prefix,               d.attr      attr,");
		sb.append("		d.pinyin    pinyin,               d.memo      memo,");
		sb.append("		d.bizcode   bizcode,               d.target    target,");
		sb.append("		d.seqno     seqno,                d.oflag1    oflag1,");
		sb.append("		d.oflag2    oflag2 ");
		sb.append("		FROM sys_category a,sys_cata_relation b,sys_cat_menu c,prv_menudef d");
		sb.append("		WHERE a.CID = b.CID ");
		sb.append("		AND b.AID = c.AID");
		sb.append("		AND a.CCODE = ? ");
		sb.append("		AND c.MENUID = d.menuid");
		sb.append("		AND c.MENUID IN(");
		sb.append("		SELECT     t1.menuid id	FROM");
		sb.append("		 prv_menudef t1,prv_roleprivs t2");
		sb.append("		WHERE t1.menuid = t2.menuid ");
		sb.append("		AND t1.attr = ?");
		params.add("Y");
		sb.append("		AND t1.sysid = ?");
		params.add(sysid);
		sb.append("		AND t2.roleid = ?");
		params.add(loginInfo.getRoleid());
		sb.append("		UNION");
		sb.append("		SELECT t3.menuid id	FROM ");
		sb.append("		prv_menudef t3,prv_operprivs t4");
		sb.append("		WHERE t3.menuid = t4.menuid ");
		sb.append("		AND t3.attr = ?");
		params.add("Y");
		sb.append("		AND t3.sysid = ?");
		params.add(sysid);
		sb.append("		AND t4.operid = ?)");
		params.add(loginInfo.getOperid());
		sb.append("		GROUP BY b.AID,c.MENUID");
		sb.append("		ORDER BY b.AID,c.SORT");
		
		List<OperMenudef> datas = getDAO().find(sb.toString(), OperMenudef.class, params.toArray());
		resp.setDatas(datas);
		return returnInfo;
	}
	
	
	private void handlerBizRecord(Long operid,List<OperMenudef> datas) throws Exception{
		BizMoRecord record = new BizMoRecord();
		record.setOperid(operid);
		List<BizMoRecord> records = getDAO().find(record);
		if(datas == null || datas.isEmpty()) return;
		
	}
	
	/**
	 * 记录客户详情页面中菜单排序
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo bizModuleMenuByOper(BizModuleMenuReq req) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		try{
			BizMoRecord record = new BizMoRecord();
			record.setAid(Long.parseLong(req.getAid()));
			record.setMenuid(Long.parseLong(req.getMenuid()));
			record.setOperid(loginInfo.getOperid());
			
			List<BizMoRecord> datas = getDAO().find(record);
			if(datas != null && !datas.isEmpty()){
				updateRecord(datas.get(0),Integer.parseInt(req.getPosition()));
			}else{
				getDAO().save(record);
			}
		}catch(Exception e){
			log.info("=============");
		}
		return returnInfo;
	}
	
	private void updateRecord(BizMoRecord record,int position) throws Exception{
		record.setSort(position);
		getDAO().save(record);
	}
	
	
	
	
}
