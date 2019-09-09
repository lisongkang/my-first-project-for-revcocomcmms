package com.maywide.biz.inter.manager;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.pojo.expire.ExpireEntity;
import com.maywide.biz.inter.pojo.queservstinfo.QueServExpireNumReq;
import com.maywide.biz.inter.pojo.queservstinfo.QueServExpireNumResp;
import com.maywide.biz.inter.pojo.queservstinfo.QueSoonExpcustBO;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;
import com.maywide.core.util.EhcacheUtil;

public class ExpriringManager {
	
	private static ExpriringManager instance;
	
	private ExpriringManager(){}
	
	public static ExpriringManager getInstance(){
		if(instance == null){
			synchronized (ExpriringManager.class) {
				if(instance == null){
					instance = new ExpriringManager();
				}
			}
		}
		return instance;
	}
	
	/***
	 * 获取即将到期用户数量
	 * @param loginInfo
	 * @param req
	 * @param daoService
	 * @return
	 * @throws Exception
	 */
	public QueServExpireNumResp getExpireNum(LoginInfo loginInfo,QueServExpireNumReq req,PersistentService daoService) 
			throws Exception{
		String sql = getSql4ExpireNum(loginInfo,req);
		List<ExpireEntity> entities = null;
		Object data = EhcacheUtil.getExipre(sql);
		if(data != null){
			entities = (List<ExpireEntity>)data;
		}else{
			entities = (List<ExpireEntity>) daoService.find(sql, ExpireEntity.class);
			EhcacheUtil.putExipre(sql, entities);
		}
		return getExpireNumResp4Entity(entities);
	}
	
	private String getSql4ExpireNum(LoginInfo loginInfo,QueServExpireNumReq req){
		StringBuffer sb = new StringBuffer();
		sb.append("		select A.TJDATE tjDate,  ETL.CODE2NAME(A.CITY,'PRV_CITY') city,A.Areaid areaid,A.WHGRIDCODE gridCode");
		sb.append("		,A.WHGRIDNAME gridName,A.SERVID serveId,A.CUSTID custId,A.LOGICDEVNO logicDevNo,A.Pid pid");
		sb.append("		,A.PNAME pname,A.Etime etime,DECODE(A.ENDFLAG,'1','已到期','2'，'即将到期')  endFlag,A.Phone phone");
		sb.append("		,A.Name name,ETL.CODE2NAME(A.PAYWAY,'SYS_FEEWAY') payway,A.WHLADDR address");
		sb.append("		FROM  NEDS.TW1_EUDI_DG A");
		sb.append("		where a.city = ");
		sb.append(" '"+loginInfo.getCity()+"'");
		sb.append("		and a.endflag = ");
		sb.append(" '2' ");
		sb.append("		and a.pid = ");
		sb.append(" '"+req.getObjid()+"'");
		sb.append("		and a.whgridcode = ");
		sb.append("	'"+req.getPatchids()+"'");
		return sb.toString();
	}
	
	private QueServExpireNumResp getExpireNumResp4Entity(List<ExpireEntity> entities){
		int curmon = 0;
		int nextmon = 0;
		int latemon = 0;
		for(ExpireEntity entity:entities){
			if(DateUtils.isInSomeMonth(entity.getEtime(),0)){
				curmon++;
			}
			if(DateUtils.isInSomeMonth(entity.getEtime(),1)){
				nextmon++;
			}
			if(DateUtils.isInSomeMonth(entity.getEtime(),2)){
				latemon++;
			}
		}
		
		QueServExpireNumResp resp = new QueServExpireNumResp();
		resp.setCurmon(curmon+"");
		resp.setLatemon(latemon+"");
		resp.setNextmon(nextmon+"");
		return resp;
	}
	
	
	public List<QueSoonExpcustBO> queServExpire(String gridCode,String custName,String objid,String sdate,String edate,String city,
			int pagesize,int currentpage,PersistentService daoService) throws Exception{
		String sql = getSql4queExprieList(city,sdate,edate,objid,gridCode,custName);
		List<QueSoonExpcustBO> datas = null;
		Object data = EhcacheUtil.getExipre(sql+"_"+pagesize+"_"+currentpage);
		if(data != null){
			datas = (List<QueSoonExpcustBO>)data;
		}else{
			Page page = new Page();
			page.setPageSize(pagesize);
			page.setPageNo(currentpage);
			page = daoService.find(page, sql, QueSoonExpcustBO.class);
			datas = page.getResult();
			EhcacheUtil.putExipre(sql+"_"+pagesize+"_"+currentpage, datas);
		}
		
		return datas;
	}
	
	private String getSql4queExprieList(String city,String sdate,String edate,String objid,String gridCode,String custname){
		StringBuffer sb = new StringBuffer();
		sb.append("		select a.servid, a.custid,a.phone mobile,a.whladdr");
		sb.append("		,a.areaid,a.logicdevno,a.whgridcode,0 objtype");
		sb.append("		,a.pid objid,a.pname objname,a.name custname,a.payway feekind");
		sb.append("		,to_char(a.etime,'YYYY-MM-dd') edate,etl.code2name(a.payway,'SYS_PAYWAY') feekindname");
		sb.append("		from NEDS.TW1_EUDI_DG a ");
		sb.append("		where a.city = ");
		sb.append("'"+city+"'");
		sb.append("		and to_char(a.etime,'YYYY-MM-dd') between ");
		sb.append(" '"+sdate+"' ");
		sb.append("	and ");
		sb.append(" '"+edate+"' ");
		sb.append("		and a.pid = ");
		sb.append(" '"+objid+"' ");
		sb.append("		and a.whgridcode = ");
		sb.append(" '"+gridCode+"' ");
		if(StringUtils.isNotBlank(custname)){
			sb.append("		and a.name like ");
			sb.append(" '%"+custname+"%' ");
		}
		sb.append("	order by a.etime");
		return sb.toString();
	}
	
}
