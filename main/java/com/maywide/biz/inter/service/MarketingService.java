package com.maywide.biz.inter.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.PhotoOperCust;
import com.maywide.biz.inter.pojo.queryCustPhoto.QueryCustPhotoReq;
import com.maywide.biz.inter.pojo.queryCustPhoto.QueryCustPhotoResp;
import com.maywide.biz.inter.pojo.saveCustPhoto.SaveCustPhotoReq;
import com.maywide.biz.survey.entity.BizPhotoList;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
/**
 * 中山网格营销相关接口
 * @author 张耀其
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MarketingService extends CommonService {
	@Autowired
	private PersistentService persistentService;
	
	/**
	 * 保存上传的图片
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo saveCustPhoto(SaveCustPhotoReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkNull(req.getCustid(), "匹配条件[cutid]不能为空");
		CheckUtils.checkEmpty(req.getPhoto(), "匹配条件[photo]不能为空");

		BizPhotoList photo = (BizPhotoList) persistentService.find(BizPhotoList.class,
				Long.valueOf(req.getPhoto()));

		CheckUtils.checkNull(photo, "图片路径不存在");

		PhotoOperCust photoOperCust = new PhotoOperCust();
		photoOperCust.setOperid(loginInfo.getOperid());
		photoOperCust.setCustid(req.getCustid());
		photoOperCust.setPhoto(photo.getImagepath());
		photoOperCust.setTime(new Date());
		photoOperCust.setStatus("1");
		photoOperCust.setType("1");

		persistentService.save(photoOperCust);

		return returnInfo;
	}

	public ReturnInfo queryCustPhoto(QueryCustPhotoReq req,QueryCustPhotoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkNull(req.getCustid(), "匹配条件[cutid]不能为空");

		PhotoOperCust photoOperCustParam = new PhotoOperCust();
		photoOperCustParam.setOperid(loginInfo.getOperid());
		photoOperCustParam.setCustid(req.getCustid());
		photoOperCustParam.setStatus("1");
		photoOperCustParam.setType("1");

		List<PhotoOperCust> photoOperCustList = persistentService.find(photoOperCustParam);

		resp.setPhotoOperCustList(photoOperCustList);

		return returnInfo;
	}
	
}
