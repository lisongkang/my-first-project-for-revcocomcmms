package com.maywide.biz.inter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.entity.BizApplicationConstruction;
import com.maywide.biz.inter.entity.BizApplicationFileid;
import com.maywide.biz.inter.pojo.applyRelaImg.ApplyRelaImgReq;
import com.maywide.biz.inter.pojo.applyRelaImg.ApplyRelaImgResp;
import com.maywide.biz.inter.pojo.editbizapplication.EditBizapplicationReq;
import com.maywide.biz.inter.pojo.editbizapplication.EditBizapplicationResp;
import com.maywide.biz.inter.pojo.queApplicationAllinfo.*;
import com.maywide.biz.inter.pojo.queAuditor.queAuditorInfo;
import com.maywide.biz.inter.pojo.queAuditor.queAuditorInterReq;
import com.maywide.biz.inter.pojo.queAuditor.queAuditorInterResp;
import com.maywide.biz.inter.pojo.queConstructor.queConstructorInterReq;
import com.maywide.biz.inter.pojo.queConstructor.queConstructorInterResp;
import com.maywide.biz.inter.pojo.savebizapplication.SaveBizapplicationInterReq;
import com.maywide.biz.survey.entity.BizPhotoList;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.entity.BizApplication;
import com.maywide.biz.inter.entity.BizOpinion;
import com.maywide.biz.inter.pojo.quebizapplication.QueBizapplicationInterReq;
import com.maywide.biz.inter.pojo.quebizapplication.QueBizapplicationInterResp;
import com.maywide.biz.inter.pojo.savebizapplication.SaveBizapplicationInterResp;
import com.maywide.core.util.DateUtils;
@Service
@Transactional(rollbackFor = Exception.class)
public class SmallMicroProService extends CommonService {
	@Autowired
	private ParamService paramService;
	@Autowired
	private PersistentService persistentService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private SmallMicroProSettlementService smallMicroProSettlementService;
	/**
	 * 1.1 新建或者修改申请单
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo saveBizapplication(SaveBizapplicationInterReq req, SaveBizapplicationInterResp resp) throws Exception{
		CheckUtils.checkNull(req, "请求信息不能为空");
		BizApplication item = req.getBizApplication();
		CheckUtils.checkEmpty(item.getProname(),"项目名称不能为空");
		CheckUtils.checkEmpty(item.getBuildaddr(),"建设地址不能为空");
		CheckUtils.checkEmpty(item.getApproveor(),"审批人不能为空");
		CheckUtils.checkEmpty(item.getBuilddetp(),"施工部门不能为空");
		CheckUtils.checkEmpty(item.getPlanstarttime(),"开工日期不能为空");
		CheckUtils.checkEmpty(item.getPlanendtime(),"完工日期不能为空");
		CheckUtils.checkNull(req.getBizApplicationConstructionList(),"请填写施工预算明细");
		CheckUtils.checkEmpty(req.getProcost(),"施工预算不能为空");
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		String operation = item.getOperation();
		//提交审批
		if(operation.equals("1")){
			item.setProstatus("1");
			item.setProcost(req.getProcost());
			//通过录入的业务区  找到业务区编码
			List<Object> params = new ArrayList();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" SELECT AREAID from prv_area where name = ? ");
			params.add(item.getBuildunit());
			String areaid =  getDAO().findUniqueObject(sqlBuffer.toString(),params.toArray()).toString();
			item.setBuildunitareaid(areaid);
			initObject(item,loginInfo);
			getDAO().saveOrUpdate(item);
			//插入流水轨迹表
			String operationtype = "申请人新建申请单";
			String operationresult = "1";
			smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(item.getId()),"",loginInfo.getOperid(),operationtype,operationresult);

		}
		// 修改后提交审批
		if(operation.equals("2")){
			String edittime = DateUtils.formatTimeNow();
			item.setProcost(req.getProcost());
			item.setEdittime(edittime);
			item.setEditid(loginInfo.getOperid());
			item.setProstatus("1");
			//通过录入的业务区  找到业务区编码
			List<Object> params = new ArrayList();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" SELECT AREAID from prv_area where name = ? ");
			params.add(item.getBuildunit());
			String areaid =  getDAO().findUniqueObject(sqlBuffer.toString(),params.toArray()).toString();
			item.setBuildunitareaid(areaid);
			getDAO().update(item);
			//插入流水轨迹表
			String operationtype = "申请人修改申请单";
			String operationresult = "1";
			smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(item.getId()),"",loginInfo.getOperid(),operationtype,operationresult);

		}
		resp.setBizApplication(item);
		//流水表插入施工预算
		//先清理以前的数据，防止重复提交导致数据重复
		StringBuffer sqlconst = new StringBuffer();
		sqlconst.append("DELETE FROM biz_application_construction where proid  = '"+item.getId()+"' ");
		persistentService.clear();
		persistentService.executeSql(sqlconst.toString());
		//插入施工服务流水表
		if(req.getBizApplicationConstructionList()!= null){

			List<BizApplicationConstruction> bizApplicationConstructionList = req.getBizApplicationConstructionList();
			for (BizApplicationConstruction BizApplicationConstruction : bizApplicationConstructionList
			) {
				String edittime = DateUtils.formatTimeNow();
				BizApplicationConstruction.setOperatetime(edittime);
				BizApplicationConstruction.setProid(String.valueOf(item.getId()));
				getDAO().saveOrUpdate(BizApplicationConstruction);
			}
		}

		return returnInfo;
	}
	private void initObject(BizApplication item,LoginInfo loginInfo){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDDHHmmss");
		String city = loginInfo.getCity();
		String pronum = city + "-"+simpleDateFormat.format(new Date());
		item.setPronum(pronum);
		item.setCity(loginInfo.getCity());
		item.setCreatorid(loginInfo.getOperid());
		item.setCreator(loginInfo.getName());
	}
	/**
	 * 1.2查询申请单
	 */
	public ReturnInfo queBizapplication(QueBizapplicationInterReq req,QueBizapplicationInterResp resp) throws Exception{
		CheckUtils.checkNull(req, "请求信息不能为空");
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		int entry = req.getEntry();
		if(entry == 0){
			// 申请人查询
			List<BizApplication> bizApplications = queApplyApplication(req,loginInfo);
			resp.setBizApplications(bizApplications);
		}
		if(entry == 1){
			// 审核及验收人查询
			List<BizApplication> bizApplications = queAudtioApplication(req,loginInfo);
			resp.setBizApplications(bizApplications);
		}
		//返回最大施工费用
		Rule rule = ruleService.getRule("MAX_CONST_COST");
		resp.setMaxConstCost(rule.getValue());
		return returnInfo;
	}

	/**
	 * 地市领导查看结算的申请单
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSettledBizapplication(QueBizapplicationInterReq req,QueBizapplicationInterResp resp) throws Exception{
		CheckUtils.checkNull(req, "请求信息不能为空");
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();

		// 分管领导查询限制同业务区
		List<BizApplication> bizApplications = queApplySettledApplication(req,loginInfo);
		resp.setBizApplications(bizApplications);

		return returnInfo;
	}

	public List<BizApplication> queApplySettledApplication(QueBizapplicationInterReq req,LoginInfo loginInfo) throws Exception{
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		if(req.getProstatus() == null && req.getSearchKeywords() == null ){
			//查所有
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance ,a.settlementopinion,a.permacceptopinion  ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.buildunitareaid  = '"+loginInfo.getAreaid()+"'");
			sqlBuffer.append(" AND a.prostatus in ('7','11','12') ");
			sqlBuffer.append(" ORDER BY a.proid desc ");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}else if(req.getProstatus() != null && !"".equals(req.getProstatus())){
			//按转态查
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance ,a.settlementopinion,a.permacceptopinion  ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.buildunitareaid  = '"+loginInfo.getAreaid()+"'");
			if(req.getProstatus().equals("7") || req.getProstatus().equals("11")){
				sqlBuffer.append(" AND a.prostatus in ('7','11') ");
			}else {
				sqlBuffer.append(" AND a.prostatus = ? ");
				params.add(req.getProstatus());
			}
			sqlBuffer.append(" ORDER BY a.proid desc ");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}else {
			//按搜索关键字查
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance,a.settlementopinion ,a.permacceptopinion ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.buildunitareaid  = '"+loginInfo.getAreaid()+"'");
			sqlBuffer.append(" AND a.prostatus in ('7','11','12') ");
			sqlBuffer.append(" AND (a.creator like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" OR a.proname like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" OR a.pronum like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" )ORDER BY a.proid desc");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}

	}

	public List<BizApplication> queApplyApplication(QueBizapplicationInterReq req,LoginInfo loginInfo) throws Exception{
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		if(req.getProstatus() == null && req.getSearchKeywords() == null ){
			//查所有
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance,a.settlementopinion,a.permacceptopinion ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.creatorid  = '"+loginInfo.getOperid()+"'");
			sqlBuffer.append(" ORDER BY a.proid desc ");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}else if(req.getProstatus() != null && !"".equals(req.getProstatus())){
			//按转态查
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance ,a.settlementopinion,a.permacceptopinion  ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.creatorid  = '"+loginInfo.getOperid()+"'");
			if(req.getProstatus().equals("1") || req.getProstatus().equals("5")){
				sqlBuffer.append(" AND a.prostatus in ('1','5') ");
			}
			else if(req.getProstatus().equals("3") || req.getProstatus().equals("4") || req.getProstatus().equals("8")){
				sqlBuffer.append(" AND a.prostatus in ('3','4','8','10') ");
			}
			else if(req.getProstatus().equals("6") || req.getProstatus().equals("7")){
				sqlBuffer.append(" AND a.prostatus in ('6','11') ");
			}
			else {
				sqlBuffer.append(" AND a.prostatus = ? ");
				params.add(req.getProstatus());
			}
			sqlBuffer.append(" ORDER BY a.proid desc ");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}else {
			//按搜索关键字查
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance ,a.settlementopinion,a.permacceptopinion  ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.creatorid  = '"+loginInfo.getOperid()+"'");
			sqlBuffer.append(" AND (a.creator like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" OR a.proname like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" OR a.pronum like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" )ORDER BY a.proid desc");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}

	}

	public List<BizApplication> queAudtioApplication(QueBizapplicationInterReq req,LoginInfo loginInfo) throws Exception{
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		if(req.getProstatus() == null && req.getSearchKeywords() == null ){
			//查所有
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance,a.settlementopinion,a.permacceptopinion   ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.approveid  = '"+loginInfo.getOperid()+"'");
			sqlBuffer.append(" ORDER BY a.proid desc ");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}else if(req.getProstatus() != null && !"".equals(req.getProstatus())){
			//按转态查
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance ,a.settlementopinion,a.permacceptopinion  ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.approveid  = '"+loginInfo.getOperid()+"'");
			if(req.getProstatus().equals("1") || req.getProstatus().equals("5") || req.getProstatus().equals("9")){
				sqlBuffer.append(" AND a.prostatus in ('1','5','9') ");
			}
			else if(req.getProstatus().equals("3") || req.getProstatus().equals("4") || req.getProstatus().equals("8")){
				sqlBuffer.append(" AND a.prostatus in ('3','4','8','10') ");
			}
			else if(req.getProstatus().equals("6") || req.getProstatus().equals("7")){
				sqlBuffer.append(" AND a.prostatus in ('6','7','11') ");
			}
			else {
				sqlBuffer.append(" AND a.prostatus = ? ");
				params.add(req.getProstatus());
			}
			sqlBuffer.append(" ORDER BY a.proid desc ");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}else {
			//按搜索关键字查
			sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance,a.settlementopinion,a.permacceptopinion   ");
			sqlBuffer.append(" FROM biz_application a where 1 = 1");
			sqlBuffer.append(" AND a.approveid  = '"+loginInfo.getOperid()+"'");
			sqlBuffer.append(" AND (a.creator like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" OR a.proname like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" OR a.pronum like '%").append(req.getSearchKeywords()).append("%'");
			sqlBuffer.append(" )ORDER BY a.proid desc ");
			List<BizApplication> bizApplications = getDAO().find(sqlBuffer.toString(),
					BizApplication.class, params.toArray());
			return bizApplications;
		}

	}

	/**
	 * 1.4 审批,验收,退单申请单等操作
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo editBizapplication(EditBizapplicationReq req, EditBizapplicationResp resp) throws Exception{
		CheckUtils.checkNull(req,"请求信息不能为空");
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		//审批流程
		if(req.getStatus().equals("2") || req.getStatus().equals("5")){
			String approvetime = DateUtils.formatTimeNow();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BIZ_APPLICATION SET prostatus = '"+ req.getStatus() + "',appopinion= '" + req.getAppopinion() + "',approveid= '" + req.getApproveid() + "',approveor= '"+ req.getApproveor() + "',edittime='"+ approvetime + "' WHERE proid = " + req.getId() );
			persistentService.clear();
			persistentService.executeSql(sql.toString());
			//插入审批流水
			if(req.getAppopinion()!= null){
				//插入流水轨迹表
				String operationtype = "审核人审批";
				String operationresult = "";
				if(req.getStatus().equals("2")){
					operationresult = "1";
				}
				if(req.getStatus().equals("5")){
					operationresult = "0";
				}
				smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(req.getId()),req.getAppopinion(),loginInfo.getOperid(),operationtype,operationresult);
			}
		}
		else if(req.getStatus().equals("4") || req.getStatus().equals("6")){
			String approvetime = DateUtils.formatTimeNow();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BIZ_APPLICATION SET prostatus = '"+ req.getStatus() + "',accopinion= '" + req.getAccopinion() + "',accepterid= '" + req.getApproveid() + "',acceptername= '"+ req.getApproveor() + "',edittime='"+ approvetime + "' WHERE proid = " + req.getId() );
			persistentService.clear();
			persistentService.executeSql(sql.toString());
			//插入验收流水
			if(req.getAccopinion()!= null){
				//插入流水轨迹表
				String operationtype = "验收人验收";
				String operationresult = "";
				if(req.getStatus().equals("4")){
					operationresult = "1";
				}
				if(req.getStatus().equals("6")){
					operationresult = "0";
				}
				smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(req.getId()),req.getAccopinion(),loginInfo.getOperid(),operationtype,operationresult);
			}
		}
		else if(req.getStatus().equals("10")){//允许验收操作
			String approvetime = DateUtils.formatTimeNow();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BIZ_APPLICATION SET prostatus = '"+ req.getStatus() + "',permacceptopinion= '" + req.getAccopinion() + "',edittime='"+ approvetime + "' WHERE proid = " + req.getId() );
			persistentService.clear();
			persistentService.executeSql(sql.toString());
			if(req.getAccopinion()!= null){
				String operationtype = "允许验收";
				String operationresult = "1";
				smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(req.getId()),req.getAccopinion(),loginInfo.getOperid(),operationtype,operationresult);
			}
		}
		else {
			String edittime = DateUtils.formatTimeNow();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BIZ_APPLICATION SET prostatus ='"+ req.getStatus() + "',edittime='"+ edittime + "' WHERE proid = " + req.getId() );
			persistentService.clear();
			persistentService.executeSql(sql.toString());

		}
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance,a.settlementopinion,a.permacceptopinion FROM biz_application a WHERE 1=1 AND a.proid=?");
		params.add(req.getId());
		List<BizApplication> bizApplicationList = getDAO().find(sqlBuffer.toString(),
				BizApplication.class, params.toArray());
		resp.setBizApplications(bizApplicationList);
		return returnInfo;
	}

	/**
	 * 分管领导审批
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo chargeApplyBizapplication(EditBizapplicationReq req, EditBizapplicationResp resp) throws Exception{
		CheckUtils.checkNull(req,"请求信息不能为空");
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		//审批流程
		if(req.getStatus().equals("12") || req.getStatus().equals("11")){
			String approvetime = DateUtils.formatTimeNow();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BIZ_APPLICATION SET prostatus = '"+ req.getStatus() + "',settlementopinion= '" + req.getSettlementopinion()  + "',edittime='"+ approvetime + "' WHERE proid = " + req.getId() );
			persistentService.clear();
			persistentService.executeSql(sql.toString());
			//插入流水轨迹表
			String operationtype = "分管领导结算审批";
			String operationresult = "";
			if(req.getStatus().equals("12")){
				 operationresult = "1";
			}
			if(req.getStatus().equals("11")){
				 operationresult = "0";
			}
			if(req.getSettlementopinion()!= null){
				smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(req.getId()),req.getSettlementopinion(),loginInfo.getOperid(),operationtype,operationresult);
			}

		}
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance,a.settlementopinion,a.permacceptopinion FROM biz_application a WHERE 1=1 AND a.proid=?");
		params.add(req.getId());
		List<BizApplication> bizApplicationList = getDAO().find(sqlBuffer.toString(),
				BizApplication.class, params.toArray());
		resp.setBizApplications(bizApplicationList);
		return returnInfo;
	}

	/**
	 * 根据申请单id或者编号查询申请单所有的结单信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queApplicationAllInfo(queApplicationAllInterReq req, queApplicationAllInterResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求对象不能为空");
		Long proid = req.getId();
		//查找申请单信息
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance,a.settlementopinion,a.permacceptopinion from biz_application a where proid= ? ");
		params.add(proid);
		List<BizApplication> bizApplicationList = getDAO().find(sqlBuffer.toString(),
				BizApplication.class, params.toArray());
		BizApplication bizApplication = bizApplicationList.get(0);
		resp.setBizApplication(bizApplication);
		//Object o = getDAO().findUniqueObject(sqlBuffer.toString(),params.toArray());
		//查找物料集合
		sqlBuffer.delete(0,sqlBuffer.length());
		sqlBuffer.append("SELECT proid,city,measname,corpcode,corpname,invcode,invname,nums,nprice,cost from biz_application_materiel where proid= ? ");
		List<ApplicationMaterielInfoBO> applicationMaterielInfoBOList = getDAO().find(sqlBuffer.toString(),
				ApplicationMaterielInfoBO.class, params.toArray());
		if(applicationMaterielInfoBOList.size() > 0){
			resp.setApplicationMaterielInfoBOList(applicationMaterielInfoBOList);
		}
		//查找施工信息集合
		sqlBuffer.delete(0,sqlBuffer.length());
		sqlBuffer.append("SELECT proid,company,constprice,constid,constname,nums,cost from biz_application_construction where proid= ? ");
		List<ApplicationConstInfoBO> applicationConstInfoBOList = getDAO().find(sqlBuffer.toString(),
				ApplicationConstInfoBO.class, params.toArray());
		if(applicationConstInfoBOList.size() > 0){
			resp.setApplicationConstInfoBOList(applicationConstInfoBOList);
		}
		//查找提成分配集合
		sqlBuffer.delete(0,sqlBuffer.length());
		sqlBuffer.append("SELECT proid,constructorid,constructorname,royalty from biz_application_distribution where proid= ? ");
		List<ApplicationDistributionInfoBO> applicationDistributionInfoBOList = getDAO().find(sqlBuffer.toString(),
				ApplicationDistributionInfoBO.class, params.toArray());
		if(applicationDistributionInfoBOList.size() > 0){
			resp.setApplicationDistributionInfoBOList(applicationDistributionInfoBOList);
		}
		//查找关联验收凭证
		sqlBuffer.delete(0,sqlBuffer.length());
		sqlBuffer.append("SELECT proid,filepath,aplytime,realfilename from biz_application_acceptfileis where proid= ? and isimage = '1' ");
		List<ApplicationAccfileidsBO> applicationAccfileidsBOList = getDAO().find(sqlBuffer.toString(),
				ApplicationAccfileidsBO.class, params.toArray());
		if(applicationAccfileidsBOList.size() > 0){
			resp.setApplicationAccfileidsBOList(applicationAccfileidsBOList);
		}
		return returnInfo;
	}

	/**
	 * 查找审核人员集合
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */

	public ReturnInfo queAuditor(queAuditorInterReq req, queAuditorInterResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求对象不能为空");

		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT operid,loginname,name from prv_operator WHERE status = '0' and  operid in ( ");
		sqlBuffer.append(" SELECT DISTINCT(operid) from ( ");
		sqlBuffer.append(" SELECT * from PRV_OPERROLE  where deptid in( ");
		sqlBuffer.append(" SELECT DISTINCT(deptid) from prv_department where city = ? )) a where a.roleid in ( ");
		sqlBuffer.append(" SELECT roleid from prv_roleprivs where menuid = '16663')) ");
		params.add(req.getCity());
		List<queAuditorInfo> queAuditorInfoList  = getDAO().find(sqlBuffer.toString(),
				queAuditorInfo.class, params.toArray());
		resp.setQueAuditorInfoList(queAuditorInfoList);
		return returnInfo;
	}

	/**
	 * 查找施工人员集合
	 * @param req
	 * @param resp
	 * @return
	 */
	public ReturnInfo queConstructor(queConstructorInterReq req, queConstructorInterResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求对象不能为空");

		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT operid,loginname,name from prv_operator where status = '0' and  operid in ( ");
		sqlBuffer.append(" SELECT DISTINCT(operid) from PRV_OPERROLE  where deptid in( ");
		sqlBuffer.append(" SELECT DISTINCT(deptid) from prv_department where city = ? and areaid = ? )) ");
		params.add(req.getCity());
		params.add(loginInfo.getAreaid());
		List<queAuditorInfo> queAuditorInfoList  = getDAO().find(sqlBuffer.toString(),
				queAuditorInfo.class, params.toArray());
		resp.setQueAuditorInfoList(queAuditorInfoList);
		return returnInfo;
	}


	/**
	 * 验收图片和申请单关联接口
	 * @param req
	 * @param resp
	 * @return
	 */
	public ReturnInfo applyRelaImg(ApplyRelaImgReq req, ApplyRelaImgResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkNull(req.getProid(),"申请单号不能为空");
		BizApplication bizApplication = (BizApplication)getDAO().find(BizApplication.class,req.getProid());
		if(null == bizApplication){
			throw new BusinessException("biz_application中找不到"+req.getProid()+"订单");
		}
		List<String> fileids = req.getFileidList();//图片集合
		List<String> nonPicturesList = req.getNonPicturesList();//非图片集合
		Iterator iterator = fileids.iterator();
		while (iterator.hasNext()){
			BizApplicationFileid bizApplicationFileid = new BizApplicationFileid();
			String imagepath = (String) iterator.next();
			BizPhotoList photo = (BizPhotoList) getDAO().find(BizPhotoList.class, Long.parseLong(imagepath));
			System.out.println("xegcysfilepath=="+photo.getImagepath());
			bizApplicationFileid.setIsimage("1");
			bizApplicationFileid.setFilename(photo.getFilename());
			bizApplicationFileid.setFilepath(photo.getImagepath());
			bizApplicationFileid.setRealfilename(photo.getUuid());
			String edittime = DateUtils.formatTimeNow();
			bizApplicationFileid.setAplytime(edittime);
			bizApplicationFileid.setProid(String.valueOf(req.getProid()));
			DAO.saveOrUpdate(bizApplicationFileid);
		}
		Iterator iteratorNoimage = nonPicturesList.iterator();
		while (iteratorNoimage.hasNext()){
			BizApplicationFileid bizApplicationFileid = new BizApplicationFileid();
			String imagepath = (String) iteratorNoimage.next();
			BizPhotoList photo = (BizPhotoList) getDAO().find(BizPhotoList.class, Long.parseLong(imagepath));
			System.out.println("xegcysfilepath=="+photo.getImagepath());
			bizApplicationFileid.setIsimage("0");
			bizApplicationFileid.setFilename(photo.getFilename());
			bizApplicationFileid.setFilepath(photo.getImagepath());
			bizApplicationFileid.setRealfilename(photo.getUuid());
			String edittime = DateUtils.formatTimeNow();
			bizApplicationFileid.setAplytime(edittime);
			bizApplicationFileid.setProid(String.valueOf(req.getProid()));
			DAO.saveOrUpdate(bizApplicationFileid);
		}

		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		params.add(req.getProid());
		sqlBuffer.append("SELECT proid,filepath,aplytime,realfilename from biz_application_acceptfileis where proid= ? ");
		List<ApplicationAccfileidsBO> applicationAccfileidsBOList = getDAO().find(sqlBuffer.toString(),
				ApplicationAccfileidsBO.class, params.toArray());
		if(applicationAccfileidsBOList.size() <= 0){
			//插入录入验收资料失败
			String operationtype = "录入验收资料";
			String operationresult = "0";
			smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(req.getProid()),"",loginInfo.getOperid(),operationtype,operationresult);
			CheckUtils.checkNull(null, "上传失败");
			return returnInfo;
		}
		resp.setApplicationAccfileidsBOList(applicationAccfileidsBOList);
		bizApplication.setProstatus("3");
		getDAO().saveOrUpdate(bizApplication);
		//插入录入验收资料失败
		String operationtype = "录入验收资料";
		String operationresult = "1";
		smallMicroProSettlementService.addBizApplicationTrack(String.valueOf(req.getProid()),"",loginInfo.getOperid(),operationtype,operationresult);

		return returnInfo;
	}

}
