package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.addCart.AddCartReq;
import com.maywide.biz.inter.pojo.addCart.AddCartResp;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessBossReq;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessInterReq;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessInterResp;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.cartInfo.CartInfoBean;
import com.maywide.biz.inter.pojo.cartInfo.CartInfoReq;
import com.maywide.biz.inter.pojo.cartInfo.CartInfoResp;
import com.maywide.biz.inter.pojo.cartInfo.CartParentBean;
import com.maywide.biz.inter.pojo.editCart.EditCartReq;
import com.maywide.biz.inter.pojo.editCart.EditCartResp;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitInterReq;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitInterResp;
import com.maywide.biz.inter.pojo.payCart.PayCartReq;
import com.maywide.biz.inter.pojo.payCart.PayCartResp;
import com.maywide.biz.inter.pojo.queCartOrder.CartOrderInterReq;
import com.maywide.biz.inter.pojo.queCartOrder.CartOrderProductResp;
import com.maywide.biz.inter.pojo.queCartOrder.CartOrderTmpBean;
import com.maywide.biz.inter.pojo.queCartOrder.QueCartOrderInterReq;
import com.maywide.biz.inter.pojo.queCartOrder.QueCartOrderInterResp;
import com.maywide.biz.inter.pojo.quecustorder.ApplyProductBO;
import com.maywide.biz.inter.pojo.quecustorder.CustordersBO;
import com.maywide.biz.inter.pojo.quecustorder.QueCustorderInterReq;
import com.maywide.biz.inter.pojo.quecustorder.QueCustorderInterResp;
import com.maywide.biz.inter.pojo.removeCart.RemoveCartBean;
import com.maywide.biz.inter.pojo.removeCart.RemoveCartReq;
import com.maywide.biz.inter.pojo.sumbitCart.SumbitCartOrderParam;
import com.maywide.biz.inter.pojo.sumbitCart.SumbitCartReq;
import com.maywide.biz.inter.pojo.sumbitCart.SumbitCartResp;
import com.maywide.biz.prv.entity.PrvCartGoods;
import com.maywide.biz.prv.entity.PrvCartInfo;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;



@Service
@Transactional(rollbackFor = Exception.class)
public class CartService extends CommonService{
	
	@Autowired
	InterApplyService applyService;
	
	@Autowired
	PubService pubService;
	
	@Autowired
	InterPrdService interPrdService;
	
	
	public ReturnInfo queCartInfo(CartInfoReq req,ArrayList<CartInfoResp> resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(),"客户id不能为空");
		
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add(loginInfo.getOperid());
		params.add(req.getCustid());
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.GOODSID knowId,A.NUMBER cycle,");
		sb.append("		A.MINNUMBER minCycle,A.MAXNUMBER maxCycle,B.CARTID cartid,");
		sb.append("		A.SALECODES salecodes,A.CATALOGID catalogid,A.SELECTNAMES selectname,");
		sb.append("		A.SELECTPCODE pcodes,A.stime,B.KEYNO keyno,B.WHLADDR keyAddr,B.CUSTID custid,");
		sb.append("		C.KNOWNAME knowName,C.IMAGE img,C.BRIEF brief,C.PRICE price,C.WORDEXP introduce,C.ICON icon");
		sb.append("		FROM PRV_CART_GOODS A,PRV_CART_INFO B,PRD_SALESPKG_KNOW C");
		sb.append("		WHERE 1 = 1 AND ");
		sb.append("		A.ID = B.CARTID AND ");
		sb.append("		A.GOODSID = C.KNOWID AND");
		sb.append("		B.CITY = ? AND ");
		sb.append("		B.OPERID = ? AND ");
		sb.append("		B.CUSTID = ? ");
		
		List<CartInfoBean> datas = getDAO().find(sb.toString(), CartInfoBean.class, params.toArray());
		if(datas != null && datas.size() > 0){
			Map<CartParentBean, List<CartInfoBean>> dataMap = new HashMap<CartParentBean, List<CartInfoBean>>();
			for(int i = 0;i<datas.size() ;i++){
				CartInfoBean child = datas.get(i);
				if(child.getMaxCycle() == child.getMinCycle()){
					child.setEditAble(false);
				}else{
					child.setEditAble(true);
				}
				CartParentBean bean = new CartParentBean(child.getKeyNo(),child.getKeyAddr());
				List<CartInfoBean> data = dataMap.get(bean);
				if(data == null){
					data = new ArrayList<CartInfoBean>();
					data.add(child);
					dataMap.put(bean, data);
				}else{
					data.add(child);
				}
			}
				Iterator<Entry<CartParentBean, List<CartInfoBean>>> iterator = dataMap.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<CartParentBean, List<CartInfoBean>> entry = iterator.next();
					CartInfoResp infoResp = new CartInfoResp();
					infoResp.setAddr(entry.getKey().getAddr());
					infoResp.setDeviceNo(entry.getKey().getKeyNo());
					infoResp.setDatas(entry.getValue());
					resp.add(infoResp);
				}
		}			
		return returnInfo;
	}
	
	
	
	public ReturnInfo addKnowToCart(AddCartReq req,AddCartResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(), "客户不能为空");
		CheckUtils.checkEmpty(req.getGoodsid(),"产品id不能为空");
		CheckUtils.checkEmpty(req.getCatalogId(),"产品目录id不能为空");
		CheckUtils.checkEmpty(req.getKeyNo(), "设备信息不能为空");
		CheckUtils.checkEmpty(req.getPathid(), "片区id不能为空");
		CheckUtils.checkEmpty(req.getHouseid(), "地址id不能为空");
		
		if(!isNotExist(req,loginInfo)){
			CheckUtils.checkNull(null, "购物车已有该商品存在!");
		}
		
		BizPreprocessBossReq req2Boss = applyService.getBizPreprocessBossReq2Boss(getBizPreprocessInterReq(req),loginInfo);
		String result = "添加成功,可以在购物车查看该产品";
		
		try{
			String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.BIZ_PREPROCESS;
			}else{
				serverCode = BizConstant.BossInterfaceService.BIZ_PREPROCESS;
			}
			String respones = getBossHttpInfOutput(req.getBizorderid(), serverCode,
					req2Boss, loginInfo);
			addRecord2Cart(req,loginInfo);
		}catch(Exception e){
			result = "添加失败 :"+e.getMessage();
		}
		resp.setResult(result);
		return returnInfo; 
	}
	
	
	private boolean isNotExist(AddCartReq req,LoginInfo info) throws Exception{
		PrvCartInfo cartInfo = new PrvCartInfo();
		cartInfo.setAreaid(info.getAreaid());
		cartInfo.setCity(info.getCity());
		cartInfo.setCustid(Long.parseLong(req.getCustid()));
		cartInfo.setHouseid(req.getHouseid());
		cartInfo.setKeyno(req.getKeyNo());
		cartInfo.setServid(req.getServid());
		cartInfo.setPathid(req.getPathid());
		cartInfo.setWhladdr(req.getWhladdr());
		cartInfo.setCustname(req.getName());
		List<PrvCartInfo> cartInfos = getDAO().find(cartInfo);
		if(cartInfos == null || cartInfos.isEmpty()) return true;
		List<PrvCartGoods> goods = new ArrayList<PrvCartGoods>();
		for(PrvCartInfo child:cartInfos){
			if(child.getGoods() != null){
				goods.add(child.getGoods());
			}
		}
		int index = -1;
		for(int i = 0; i < goods.size() ;i++){
			PrvCartGoods good = goods.get(i);
			if(req.getGoodsid().equals(Long.toString(good.getGoodsid()))){
				index = i;
				break;
			}
		}
		if(index != -1){
			return false;
		}
		return true;
	}
	
	private BizPreprocessInterReq getBizPreprocessInterReq(AddCartReq req){
        BizPreprocessInterReq interReq = new BizPreprocessInterReq();
        interReq.setCustid(req.getCustid());
        interReq.setDescrip("");
        interReq.setGdno("");
        interReq.setGdnoid("");
        interReq.setHouseid(req.getHouseid());
        interReq.setIscrtorder("N");
        interReq.setName(req.getName());
        List<OrderParamsInterBO> orderParams = new ArrayList<OrderParamsInterBO>();
        OrderParamsInterBO bo = new OrderParamsInterBO();
        bo.setCount(req.getNumber()+"");
        bo.setIspostpone("N");
        bo.setKeyno(req.getKeyNo());
        bo.setKnowid(req.getGoodsid());
        bo.setOrdertype(req.getType()+"");
        bo.setSalescode(req.getSalescode());
        bo.setServid(req.getServid());
        bo.setUnit(req.getUnit());
        bo.setSelpcodes("");
        bo.setIspostpone(req.getIsPostpone());
        bo.setStime(req.getStime());
        orderParams.add(bo);
        interReq.setOrderparams(orderParams);
        return interReq;
	}
	
	private void addRecord2Cart(AddCartReq req,LoginInfo info) throws Exception{
		PrvCartInfo cartInfo = new PrvCartInfo();
		cartInfo.setAreaid(info.getAreaid());
		cartInfo.setCity(info.getCity());
		cartInfo.setCustid(Long.parseLong(req.getCustid()));
		cartInfo.setHouseid(req.getHouseid());
		cartInfo.setOperid(info.getOperid());
		cartInfo.setKeyno(req.getKeyNo());
		cartInfo.setServid(req.getServid());
		cartInfo.setPathid(req.getPathid());
		cartInfo.setWhladdr(req.getWhladdr());
		cartInfo.setCustname(req.getName());
		List<PrvCartInfo> cartInfos = getDAO().find(cartInfo);
		if(cartInfos != null && !cartInfos.isEmpty()){//若购物车表中存在该购物信息,则更新
			List<PrvCartGoods> goods = new ArrayList<PrvCartGoods>();
			for(PrvCartInfo child:cartInfos){
				if(child.getGoods() != null){
					goods.add(child.getGoods());
				}
			}
			int index = -1;
			for(int i = 0; i < goods.size() ;i++){
				PrvCartGoods good = goods.get(i);
				if(req.getGoodsid().equals(Long.toString(good.getGoodsid()))){
					index = i;
					break;
				}
			}
			if(index != -1){
				PrvCartGoods goodInfo = goods.get(index);
				updateGoods(req,goodInfo);
			}else{
				insertRecord2Cart(cartInfo, req);
			}
		}else{//添加记录在表中
			insertRecord2Cart(cartInfo,req);
		}
	}
	
	private void updateGoods(AddCartReq req,PrvCartGoods goodinfo) throws Exception{
		int number = goodinfo.getNumber()+req.getNumber();
		goodinfo.setNumber(number);
		goodinfo.setStime(req.getStime());
		getDAO().update(goodinfo);
	}
	
	private void insertRecord2Cart(PrvCartInfo cartInfo,AddCartReq req) throws Exception{
		PrvCartGoods goodsInfo = new PrvCartGoods();
		goodsInfo.setCatalogid(Long.parseLong(req.getCatalogId()));
		goodsInfo.setMaxNum(req.getMaxUnit());
		goodsInfo.setMinNum(req.getMinUnit());
		goodsInfo.setNumber(req.getNumber());
		goodsInfo.setObjtype(req.getType()+"");
		goodsInfo.setSalescode(req.getSalescode());
		goodsInfo.setSelectids(req.getSelectids());
		goodsInfo.setSelectNames(req.getSelectNames());
		goodsInfo.setPcodes(req.getPcodes());
		goodsInfo.setGoodsid(Long.parseLong(req.getGoodsid()));
		goodsInfo.setUnit(req.getUnit());
		goodsInfo.setIsPostpone(req.getIsPostpone());
		goodsInfo.setStime(req.getStime());
		getDAO().save(goodsInfo);
		cartInfo.setGoods(goodsInfo);
		
		getDAO().save(cartInfo);
	}
	
	public ReturnInfo removeCart(RemoveCartReq req,AddCartResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(),"客户id不能为空");
		String result = "已成功将该商品从购物车删除";
		for(RemoveCartBean bean:req.getDeteleParams()){
			CheckUtils.checkEmpty(bean.getGoodsId(),"商品id不能为空");
			CheckUtils.checkNull(bean.getCartid(), "购物车商品id不能为空");
			PrvCartGoods goodsInfo = new PrvCartGoods();
			goodsInfo.setId(bean.getCartid());
			goodsInfo.setGoodsid(Long.parseLong(bean.getGoodsId()));
			goodsInfo.setCatalogid(Long.parseLong(bean.getCatalogId()));
			List<PrvCartGoods> goods = getDAO().find(goodsInfo);
			if(goods == null || goods.isEmpty()){
				throw new BusinessException("购物车中查询不到该商品"+bean.getGoodsId()+"的记录");
			}else{
				goodsInfo = goods.get(0);
				PrvCartInfo cartInfo = new PrvCartInfo();
				cartInfo.setGoods(goodsInfo);
				List<PrvCartInfo> cartInfos = getDAO().find(cartInfo);
				if(cartInfos == null || cartInfos.isEmpty()){
					CheckUtils.checkNull(null, "购物车中查询不到该商品"+bean.getGoodsId()+"的记录");
				}else{
					cartInfo = cartInfos.get(0);
					try{
						getDAO().delete(cartInfo);
						getDAO().delete(goodsInfo);
					}catch(Exception e){
						log.error(e.getMessage());
					}
				}
			}
		}
		resp.setResult(result);
		return returnInfo;
	}
	
	public ReturnInfo editCart(EditCartReq req,EditCartResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE); 
		CheckUtils.checkNull(req.getCartid(),"购物车商品id不能为空");
		CheckUtils.checkEmpty(req.getCustid(),"客户id不能为空");
		CheckUtils.checkNull(req.getGoodsid(),"产品id不能为空");
		
		String result = "编辑成功";
		
		if(req.getNumber() < 0){
			throw new IllegalAccessError("订购周期不能低于0");
		}
		
		PrvCartGoods goodsInfo = new PrvCartGoods();
		goodsInfo.setId(req.getCartid());
		List<PrvCartGoods> goods = getDAO().find(goodsInfo);
		if(goods == null || goods.isEmpty()){
			result = "找不到该商品记录";
		}else{
			goodsInfo = goods.get(0);
			goodsInfo.setNumber(req.getNumber());
			try{
				getDAO().update(goodsInfo);
				resp.setSuccess(true);
			}catch(Exception e){
				result = "更新该商品记录失败,请稍后重试";
				resp.setSuccess(false);
			}
		}
		resp.setResult(result);
		return returnInfo;
	}
	
	
	private List<BizPreprocessInterReq> getSumbiCartReq2BizPreprocessInterReq(SumbitCartReq req,LoginInfo loginInfo,BaseApiRequest baseApi)
			throws Exception{
		Map<CartParentBean, List<CartInfoBean>> map = new HashMap<CartParentBean, List<CartInfoBean>>();
		for(SumbitCartOrderParam param:req.getParams()){
			CartInfoBean cartInfoBean = getCartInfoById(param.getCartid(), loginInfo);
			CartParentBean bean = new CartParentBean(cartInfoBean.getKeyNo(),cartInfoBean.getKeyAddr());
			List<CartInfoBean> childs = map.get(bean);
			if(childs == null){
				childs = new ArrayList<CartInfoBean>();
				childs.add(cartInfoBean);
				map.put(bean, childs);
			}else{
				childs.add(cartInfoBean);
			}
		}
		return getBizPreprocessinterReqs(map,req);
	}
	
	private List<BizPreprocessInterReq> getBizPreprocessinterReqs(Map<CartParentBean, List<CartInfoBean>> map,SumbitCartReq baseApi){
		List<BizPreprocessInterReq> interReqs = new ArrayList<BizPreprocessInterReq>();
		Iterator<Entry<CartParentBean, List<CartInfoBean>>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<CartParentBean, List<CartInfoBean>> entry = iterator.next();
			CartParentBean bean = entry.getKey();
			List<CartInfoBean> cartInfoBeans = entry.getValue();
			BizPreprocessInterReq req = new BizPreprocessInterReq();
			req.setBizorderid(baseApi.getBizorderid());
			req.setBuff("");
			req.setCustid(cartInfoBeans.get(0).getCustid()+"");
			req.setDescrip("");
			req.setGdno("");
			req.setGdnoid("");
			req.setWhladdr(bean.getAddr());
			req.setIscrtorder("Y");
			req.setHouseid(cartInfoBeans.get(0).getHouseid());
			req.setName(cartInfoBeans.get(0).getCustName());
			req.setPatchid(cartInfoBeans.get(0).getPathid());
			req.setAreaid(cartInfoBeans.get(0).getAreaid().toString());
			req.setVerifyphone(baseApi.getVerifyphone());
			List<OrderParamsInterBO> paramsInterBOs = new ArrayList<OrderParamsInterBO>();
			for(CartInfoBean cartBean:cartInfoBeans){
				OrderParamsInterBO bo = new OrderParamsInterBO();
				bo.setCount(cartBean.getCycle().toString());
				bo.setKeyno(cartBean.getKeyNo());
				bo.setKnowid(cartBean.getKnowId().toString());
				bo.setOrdertype(cartBean.getObjType());
				bo.setSalescode(cartBean.getSalescode());
				bo.setSelpcodes(cartBean.getSelpCodes());
				bo.setServid(cartBean.getServid());
				bo.setUnit(cartBean.getUnit());
				bo.setIspostpone(cartBean.getIspostpone());
				bo.setStime(cartBean.getStime());
				paramsInterBOs.add(bo);
			}
			req.setOrderparams(paramsInterBOs);
			interReqs.add(req);
		}
		return interReqs;
	}
	
	
	private CartInfoBean getCartInfoById(String cartid,LoginInfo loginInfo) throws Exception{
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add(cartid);
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.GOODSID knowId,A.NUMBER cycle,A.UNIT unit,B.HOUSEID houseid,B.CUSTNAME custName,");
		sb.append("		A.MINNUMBER minCycle,A.MAXNUMBER maxCycle,B.CARTID cartid,B.PATHID pathid,A.SALECODES salescode,");
		sb.append("		A.SALECODES salecodes,A.CATALOGID catalogid,A.SELECTNAMES selectname,B.AREAID areaid,B.SERVID servid,");
		sb.append("		A.SELECTPCODE selpCodes,B.KEYNO keyno,B.WHLADDR keyAddr,B.CUSTID custid,A.isPostpone ispostpone,A.stime");
		sb.append("		FROM PRV_CART_GOODS A,PRV_CART_INFO B,PRD_SALESPKG_KNOW C");
		sb.append("		WHERE 1 = 1 AND ");
		sb.append("		A.ID = B.CARTID AND ");
		sb.append("		A.GOODSID = C.KNOWID AND");
		sb.append("		B.CITY = ? AND ");
		sb.append("		A.ID = ?");
		List<CartInfoBean> datas = getDAO().find(sb.toString(), CartInfoBean.class, params.toArray());
		if(datas == null || datas.isEmpty()){
			CheckUtils.checkNull(null, "购物车内查询不到该商品信息");
		}
		return datas.get(0);
	}
	
	/**
	 * 提交购物车内产品并进行订购,返回订单编号
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo sumbitProductOrder(SumbitCartReq req,SumbitCartResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		if(req.getParams() == null || req.getParams().isEmpty()){
			CheckUtils.checkNull(null, "购物车参数不能为空");
		}
		String notice = "";
		List<BizPreprocessInterReq> bizPreprocessInterReqs = getSumbiCartReq2BizPreprocessInterReq(req,loginInfo,req);
		List<BizPreprocessInterResp> orderids = new ArrayList<BizPreprocessInterResp>();
		for(int i = 0;i < bizPreprocessInterReqs.size() ;i++){
			try{
				BizPreprocessInterReq childReq = bizPreprocessInterReqs.get(i);
				BizPreprocessInterResp childResp = new BizPreprocessInterResp();
				applyService.bizPreprocess(childReq, childResp);
				orderids.add(childResp);
			}catch(Exception e){
				notice = e.getMessage();
				break;
			}
		}
		if(StringUtils.isBlank(notice)){
			try{
				clearCart(req.getParams());
			}catch(Exception e){
				notice = e.getMessage();
			}
		}
		if(StringUtils.isNotBlank(notice)){
			CheckUtils.checkNull(null, notice);
		}
		resp.setOrderids(orderids);
		resp.setNotice(notice);
		return returnInfo;
	}	
	
	private void clearCart(List<SumbitCartOrderParam> cartids) throws Exception{
		for(int i = 0 ; i < cartids.size(); i++){
			String cartid = cartids.get(i).getCartid();
			PrvCartGoods good = new PrvCartGoods();
			good.setId(Long.parseLong(cartid));
			List<PrvCartGoods> tmpGoods = getDAO().find(good);
			if(tmpGoods != null && !tmpGoods.isEmpty()){
				good = tmpGoods.get(0);
				PrvCartInfo cartInfo = new PrvCartInfo();
				cartInfo.setGoods(good);
				List<PrvCartInfo> tmpInfos = getDAO().find(cartInfo);
				if(tmpInfos != null && !tmpInfos.isEmpty()){
					cartInfo = tmpInfos.get(0);
					getDAO().delete(cartInfo);
					getDAO().delete(good);
				}
			}
		}
	}
	
	
	/**
	 * 查询购物车订单列表
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo queCartOrderList(QueCartOrderInterReq req,QueCartOrderInterResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		List<QueCustorderInterReq> orderInterReqs = getQueCustorderInterReqs(req,loginInfo);
		List<QueCustorderInterResp> orderInterResps  = new ArrayList<QueCustorderInterResp>();
		for(QueCustorderInterReq orderReq:orderInterReqs){
			QueCustorderInterResp interResp = new QueCustorderInterResp();
			pubService.queCustorder(orderReq, interResp);
			orderInterResps.add(interResp);
		}
		if(!orderInterResps.isEmpty()){
			List<CartOrderTmpBean> beans =  handlerOrderData(orderInterResps);
			resp.setDatas(beans);
		}
		
		return returnInfo;
	}
	
	private List<CartOrderTmpBean>  handlerOrderData(List<QueCustorderInterResp> datas){
		List<CartOrderTmpBean> parents = new ArrayList<CartOrderTmpBean>();
		for(int i = 0 ;i < datas.size() ;i++){
			CartOrderTmpBean bean = new CartOrderTmpBean();
			CustordersBO custorderBo = datas.get(i).getCustorders().get(0);
			bean.setOrderid(custorderBo.getCustorderid());
			bean.setOrdercode(custorderBo.getOrdercode());
			bean.setAddr(custorderBo.getAddr());
			bean.setCustname(custorderBo.getCustname());
			bean.setCustorderid(custorderBo.getCustorderid());
			bean.setFees(Double.parseDouble(custorderBo.getFees()));
			bean.setLogicdevno(custorderBo.getLogicdevno());
			bean.setPaystatusname(custorderBo.getPaystatusname());
			bean.setTime(custorderBo.getOptime());
			bean.setProducts(getCartOrderProducts(custorderBo.getCustorderDet().getApplyProducts()));
			parents.add(bean);
		}
		
		return parents;
	}
	
	private List<CartOrderProductResp> getCartOrderProducts(List<ApplyProductBO> products){
		List<CartOrderProductResp> datas = new ArrayList<CartOrderProductResp>();
		for(ApplyProductBO bo:products){
			CartOrderProductResp data = new CartOrderProductResp();
			data.setCount(Integer.parseInt(bo.getCount()));
			data.setKnowname(bo.getKnowname());
			data.setUnitname("月");
			datas.add(data);
		}
		return datas;
	}
	
	private List<QueCustorderInterReq> getQueCustorderInterReqs(QueCartOrderInterReq req,LoginInfo loginInfo){
		List<QueCustorderInterReq> reqs = new ArrayList<QueCustorderInterReq>();
		for(CartOrderInterReq interReq:req.getOrderIds()){
			QueCustorderInterReq orderInterReq = new QueCustorderInterReq();
			orderInterReq.setBizorderid(req.getBizorderid());
			orderInterReq.setCity(loginInfo.getCity());
			orderInterReq.setCurrentPage("1");
			orderInterReq.setCustid(req.getCustid());
			orderInterReq.setCustname(req.getCustname());
			orderInterReq.setCustorderid(interReq.getOrderid());
			orderInterReq.setOprdep(loginInfo.getDeptid().toString());
			orderInterReq.setPagesize("1");
			reqs.add(orderInterReq);
		}
		return reqs;
	}
	
	/**
	 * 购物车支付订单接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo payCartOrderList(PayCartReq req,PayCartResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getOrderInfos(), "用户订单信息不能为空");
		
		List<OrderCommitInterReq> orderCommitReqs = getCommitInterReqs(req);
		if(orderCommitReqs == null){
			CheckUtils.checkNull(null, "用户订单信息不能为空");
		}
		List<OrderCommitInterResp> commitResps = new ArrayList<OrderCommitInterResp>();
		for(OrderCommitInterReq commitReq : orderCommitReqs){
			OrderCommitInterResp commitResp = new OrderCommitInterResp();
			interPrdService.doOrderCommit(commitReq, commitResp);
			commitResps.add(commitResp);
		}
		resp.setDatas(commitResps);
		return returnInfo;
	}
	
	private List<OrderCommitInterReq> getCommitInterReqs(PayCartReq req){
		if(req.getOrderInfos().isEmpty()) return null;
		List<OrderCommitInterReq> reqs = new ArrayList<OrderCommitInterReq>();
		for(int i = 0;i < req.getOrderInfos().size() ;i++){
			OrderCommitInterReq commitReq = new OrderCommitInterReq();
			commitReq.setBizorderid(req.getBizorderid());
			commitReq.setBankaccno(req.getBankaccno());
			commitReq.setMobile(req.getMobile());
			commitReq.setPaycode(req.getPaycode());
			commitReq.setPayreqid(req.getPayreqid());
			commitReq.setPayway(req.getPayway());
			commitReq.setCustorderid(req.getOrderInfos().get(i).getCustorderid());
			commitReq.setSmartCardNo(req.getOrderInfos().get(i).getSmartCardNo());
			reqs.add(commitReq);
		}
		return reqs;
	}
	
}
