package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.addscreenshot.AddScreenshotReq;
import com.maywide.biz.inter.pojo.quescreenshot.QueScreenshotReq;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
public class InterOrderService extends CommonService {

	public ReturnInfo addScreenshot(AddScreenshotReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getOrderid(), "订单号不能为空");
		CheckUtils.checkEmpty(req.getScreenshots(), "屏幕截图不能为空");

		List<Object> paramList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE biz_custorder SET screenshots = ");
		if (req.isAppend()) {
			sql.append(" CASE WHEN screenshots IS NOT NULL AND LENGTH(TRIM( screenshots))>0 ");
			sql.append(" THEN CONCAT(screenshots,',', ? ) ELSE ? END ");
			paramList.add(req.getScreenshots());
			paramList.add(req.getScreenshots());
		} else {
			sql.append(" ? ");
			paramList.add(req.getScreenshots());
		}
		sql.append(" WHERE orderid = ? ");
		paramList.add(req.getOrderid());
		int result = getDAO().executeSql(sql.toString(), paramList.toArray());
		if (result <= 0) {
			throw new BusinessException("添加屏幕截图失败");
		}
		return returnInfo;
	}

	@SuppressWarnings("unchecked")
	public ReturnInfo queScreenshot(QueScreenshotReq req, ArrayList<String> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getOrderid(), "订单号不能为空");

		String sql = "SELECT imagepath FROM biz_photo_list WHERE FIND_IN_SET(fileid ,(SELECT screenshots FROM biz_custorder WHERE orderid = ? )) ";
		List<String> list = getDAO().findObjectList(sql, req.getOrderid());
		if (list != null) {
			resp.addAll(list);
		}
		return returnInfo;
	}

}
