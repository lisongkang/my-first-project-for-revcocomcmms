package com.maywide.biz.pay.unify.manager;

import com.maywide.core.exception.BusinessException;

public class ParamsManager{


	private ParamsManager(){}

	public static void isCorrectData(String payway,String paycode) throws Exception{
		if(null==payway&&null==paycode){
			throw new BusinessException("当前数据错误,请联系管理人员或客服!");
		}
		if("33".equals(payway)||"U".equals(payway)){
			if(null==paycode||"000000".equals(paycode)){//说明当前是错误数据,把U或者33(为统一支付平台的一级支付编码),而000000是现金支付  两者同时出现视为错误数据
				throw new BusinessException("当前数据已过期,请更新至最新版本!请移步设置界面更新或重新打开应用更新至最新!");
			}
		}
	}
}
