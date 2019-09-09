package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.entity.PrvOperPhoto;
import com.maywide.biz.inter.pojo.quePromotion.QuePromotionReq;
import com.maywide.biz.inter.pojo.quePromotion.QuePromotionResp;
import com.maywide.biz.inter.pojo.queUserOnline.QueBossUserOnlineReq;
import com.maywide.biz.inter.pojo.queUserOnline.QueUserOnlineReq;
import com.maywide.biz.inter.pojo.queUserOnline.QueUserOnlineResp;
import com.maywide.biz.inter.pojo.queUserOnlineUrl.QueUserOnlineUrlResp;
import com.maywide.biz.inter.pojo.queuserprd.DivideInfo;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossReq;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossResp;
import com.maywide.biz.inter.pojo.recordOperPhoto.RecordOperPhotoReq;
import com.maywide.biz.inter.pojo.recordOperPhoto.RecordOperPhotoResp;
import com.maywide.biz.prv.service.PrvDepartmentService;
import com.maywide.biz.survey.entity.BizPhotoList;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
public class InterUserStatusService extends CommonService {

	
	@Autowired
	private PrvDepartmentService departmentService;
	
	@Autowired
	private ParamService paramService;
	
	
	private final String MAC_STATUC_URL_SYS_PARAM = "MAC_STATUC_URL";
	
	/**
	 * 查询宽带用户在线情况
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queUserOnline(QueUserOnlineReq req,QueUserOnlineResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getLoginname(), "用户宽带帐号不能为空");
		QueBossUserOnlineReq req2Boss = new QueBossUserOnlineReq(req.getLoginname());
		
		String result = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_USER_ONLINE, req2Boss, loginInfo);
		resp = (QueUserOnlineResp) BeanUtil.jsonToObject(result, QueUserOnlineResp.class);
		return returnInfo;
	}
	
	
	/**
	 * 端对端网络地址开发
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queUserOnlineUrl(QueUserOnlineUrlResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		List<PrvSysparam> sysparam =  paramService.getData(MAC_STATUC_URL_SYS_PARAM);
		if(sysparam == null || sysparam.isEmpty() || StringUtils.isBlank(sysparam.get(0).getData())){
			resp.setAddr("http://210.21.65.132:2031/modules/as/pre/mobile/detectMo.jsp");
		}else{
			resp.setAddr(sysparam.get(0).getData());
		}
		String departName = departmentService.queCompleteDepartment(loginInfo.getDeptid());
		resp.setDepartName(departName);
		resp.setOpName(loginInfo.getName());
		return returnInfo;
	}
	
	/**
	 * 保存头像接口
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo recordOperPhoto(RecordOperPhotoReq req,RecordOperPhotoResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		CheckUtils.checkEmpty(req.getPhotoid().toString(), "头像路径不能为空");
		
		BizPhotoList photo = (BizPhotoList) getDAO().find(BizPhotoList.class, req.getPhotoid());
		
		CheckUtils.checkNull(photo, "头像路径不存在");
		
		PrvOperPhoto prvOperPhoto = new PrvOperPhoto();
		prvOperPhoto.setId(loginInfo.getOperid());
		prvOperPhoto.setHeadImg(photo.getImagepath());
		
		getDAO().saveOrUpdate(prvOperPhoto);
		
		resp.setImgPath(photo.getImagepath());
		
		return returnInfo;
	}
	
	/**
	 * 查询促销优惠接口
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queUserPromotion(QuePromotionReq req,QuePromotionResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req);
		
		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_USERPRDINFO, req2Boss, loginInfo);
		System.out.println(bossRespOutput);
		QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil.jsonToObject(bossRespOutput, QueUserPrdBossResp.class);
		if(bossResp == null || bossResp.getDivideinfo() == null || bossResp.getDivideinfo().isEmpty()){
			resp.setDatas(null);
		}else{
			Set<DivideInfo> sets = new HashSet<DivideInfo>(bossResp.getDivideinfo());
			resp.setDatas(new ArrayList<DivideInfo>(sets));
		}
		return returnInfo;
	}
	
	private QueUserPrdBossReq getQueUserPrdReq2Boss(QuePromotionReq req) {
		QueUserPrdBossReq queUserPrdBossReq = new QueUserPrdBossReq();
		queUserPrdBossReq.setCustid(req.getCustid());
		queUserPrdBossReq.setPermark(req.getPermark());
		queUserPrdBossReq.setPstatus(req.getPstatus());
		queUserPrdBossReq.setServid(req.getServid());

		return queUserPrdBossReq;
	}

	
	
}
