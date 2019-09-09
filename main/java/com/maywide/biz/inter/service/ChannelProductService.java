package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.queChannelByProduct.QueChannelByProductReq;
import com.maywide.biz.inter.pojo.queChannelByProduct.QueChannelByProductResp;
import com.maywide.biz.inter.pojo.queProductByChannel.ChannelProductBossResp;
import com.maywide.biz.inter.pojo.queProductByChannel.ChannelProductRespBean;
import com.maywide.biz.inter.pojo.queProductByChannel.ChildChannelBean;
import com.maywide.biz.inter.pojo.queProductByChannel.QueProductByChannelReq;
import com.maywide.biz.inter.pojo.queProductByChannel.QueProductByChannelResp;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;


@Service
public class ChannelProductService extends CommonService {

	
	/**
	 * 根据频道查商品
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queProductByChannel(QueProductByChannelReq req,QueProductByChannelResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		CheckUtils.checkEmpty(req.getChannelname(), "频道名称不能为空!");
		
		QueProductByChannelReq req2Boss = new QueProductByChannelReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		
		String responseStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_PRDSALES_CHANNELS, req2Boss, loginInfo);
		
		ChannelProductBossResp bossResp = (ChannelProductBossResp) BeanUtil.jsonToObject(responseStr, ChannelProductBossResp.class);
		if(bossResp != null){
			handlerUnSelf(loginInfo, bossResp.getSalesByAreaid(), resp);
			handlerHadBuy(bossResp.getSalesByCustid(),resp);
		}
		
		return returnInfo;
	}
	
	private void handlerUnSelf(LoginInfo loginInfo,List<String> salesByAreaid,QueProductByChannelResp resp) throws Exception{
		if(salesByAreaid == null || salesByAreaid.isEmpty()){
			return;
		}
		List params = new ArrayList();
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT b.KNOWID,b.KNOWNAME,a.SALESID FROM prd_sales a,prd_salespkg_know b");
		sb.append("		WHERE a.SALESID = b.OBJID");
		sb.append("		AND b.CITY = ?");
		params.add(loginInfo.getCity());
		sb.append("		and a.SALESID in (");
		for(int i = 0; i < salesByAreaid.size() ;i++){
			sb.append("?");
			if(i != salesByAreaid.size() -1){
				sb.append(",");
			}
			params.add(salesByAreaid.get(i));
		}
		sb.append("	)");
		
		List<ChannelProductRespBean> datas = getDAO().find(sb.toString(), ChannelProductRespBean.class, params.toArray());
		resp.setDatas(datas);
	}
	
	private void handlerHadBuy(List<ChildChannelBean> salesByCustid,QueProductByChannelResp resp){
		if(salesByCustid == null || salesByCustid.isEmpty()) return;
		if(resp.getDatas() == null){
			resp.setDatas(new ArrayList<ChannelProductRespBean>());
		}
		List<ChannelProductRespBean> hadBuys = new ArrayList<ChannelProductRespBean>();
		for(ChildChannelBean name:salesByCustid){
			boolean same = false;
			for(ChannelProductRespBean bean:resp.getDatas()){
				if(bean != null && StringUtils.isNotBlank(bean.getSalesid()) && 
						bean.getSalesid().equalsIgnoreCase(name.getSalesid())){
					bean.setHadBuy(true);
					same = true;
					break;
				}
			}
			if(!same){
				hadBuys.add(new ChannelProductRespBean(name.getSalesname(), true));
			}
			
		}
		resp.getDatas().addAll(0,hadBuys);
	}
	
	
	public ReturnInfo queChannelByProduct(QueChannelByProductReq req,QueChannelByProductResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		CheckUtils.checkEmpty(req.getSalesid(), "商品编码不能为空!");
		
		QueChannelByProductReq req2Boss = new QueChannelByProductReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		
		String responseStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_PRDSALES_CHANNELS, req2Boss, loginInfo);
		
		QueChannelByProductResp bossresp = (QueChannelByProductResp) BeanUtil.jsonToObject(responseStr, QueChannelByProductResp.class);
		if(bossresp != null && bossresp.getChannelnames() != null){
			resp.setChannelnames(bossresp.getChannelnames());
		}
		return returnInfo;
	}
	
}
