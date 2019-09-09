package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Transient;

import com.maywide.biz.inter.entity.CustBizNetWorkOrderPool;
import com.maywide.biz.inter.pojo.queBindSales.*;
import com.maywide.biz.inter.pojo.queFitInfo.*;
import com.maywide.biz.inter.pojo.queNetBusOrderPool.QueSalesNetBusOrderReq;
import com.maywide.biz.inter.pojo.queNetBusOrderPool.QueSalesNetBusOrderResp;
import com.maywide.biz.inter.pojo.wechatBinding.WechatBindingReq;
import com.maywide.biz.inter.pojo.wechatBinding.WechatBindingResp;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.test.InterTest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizCustorderOrderstatus;
import com.maywide.biz.cons.BizConstant.BizCustorderSyncmode;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.cons.BizConstant.PORTAL_ORDER_STATUS;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.inter.pojo.bizPartSales.BizPartSalesBossReq;
import com.maywide.biz.inter.pojo.bizPartSales.BizPartSalesBossResp;
import com.maywide.biz.inter.pojo.bizPartSales.BizPartSalesReq;
import com.maywide.biz.inter.pojo.bizPartSales.BizPartSalesResp;
import com.maywide.biz.inter.pojo.bizSaleCmit.BizSaleCommitBossReq;
import com.maywide.biz.inter.pojo.bizSaleCmit.BizSaleCommitReq;
import com.maywide.biz.inter.pojo.queCityClzPam.QueClzParamChildResp;
import com.maywide.biz.inter.pojo.queCityClzPam.QueClzParamListResp;
import com.maywide.biz.inter.pojo.queCityClzPam.ResChildParamBean;
import com.maywide.biz.inter.pojo.queCityClzPam.ResParamBean;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.pay.unify.manager.ParamsManager;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;


@Service
public class InterResSaleService extends CommonService {
	private static Logger log = LoggerFactory.getLogger(InterResSaleService.class);

	@Autowired
	CustSpreadService temService;
	
	@Autowired
	PubService pubService;

	@Autowired
	private PersistentService persistentService;

	@Autowired
	RuleService ruleService;
	
	private final String CITY_RES_USEPORP_RULE = "CITY_RES_USEPORP";
	
	/**
	 * 查询网格地市资源配置数据(待测试)(queCityResList)
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queClzParamList(QueClzParamListResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.KIND kind,a.SUBKIND subkind,a.SUBNAME subname,b.mname kindname");
		sb.append("	FROM city_class_param a,prv_sysparam b");
		sb.append("	WHERE  a.KIND = b.mcode");
		sb.append("	AND b.gcode = ?");
		sb.append("	AND a.city = ?");
		
		List params = new ArrayList();
		params.add("RES_KIND");
		params.add(loginInfo.getCity());
		List<ResParamBean> clzParams = getDAO().find(sb.toString(), ResParamBean.class, params.toArray());
		handleParamList(clzParams,resp);
		return returnInfo;
	}

	/**
	 * 组网订单池办理业务查询网格地市资源配置数据
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queNetWorkClzParamList(QueClzParamListResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.KIND kind,a.SUBKIND subkind,a.SUBNAME subname,b.mname kindname");
		sb.append("	FROM city_class_param_zw a,prv_sysparam b");
		sb.append("	WHERE  a.KIND = b.mcode");
		sb.append("	AND b.gcode = ?");
		sb.append("	AND a.city = ?");

		List params = new ArrayList();
		params.add("RES_KIND");
		params.add(loginInfo.getCity());
		List<ResParamBean> clzParams = getDAO().find(sb.toString(), ResParamBean.class, params.toArray());
		handleParamList(clzParams,resp);
		return returnInfo;
	}


	private void handleParamList(List<ResParamBean> clzParams,QueClzParamListResp resp){
		if(clzParams == null || clzParams.isEmpty()){
			return;
		}
		List<QueClzParamChildResp> datas = getQueClzParamChildResp(clzParams);
		resp.setDatas(datas);
	}
	
	private List<QueClzParamChildResp> getQueClzParamChildResp(List<ResParamBean> clzParams){
		List<QueClzParamChildResp> datas = new ArrayList<QueClzParamChildResp>();
		Map<String, List<ResParamBean>> map = new HashMap<String, List<ResParamBean>>();
		for(ResParamBean bean:clzParams){
			String key = bean.getKind()+"_"+bean.getKindname();
			List<ResParamBean> beans = map.get(key);
			if(beans == null){
				beans = new ArrayList<ResParamBean>();
				map.put(key, beans);
			}
			beans.add(bean);
		}
		Iterator<Entry<String, List<ResParamBean>>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, List<ResParamBean>> entry = iterator.next();
			QueClzParamChildResp child = getQueClzParamBean(entry.getValue());
			datas.add(child);
		}
		return datas;
	}
	
	private QueClzParamChildResp getQueClzParamBean(List<ResParamBean> clzParams){
		List<ResChildParamBean> datas = new ArrayList<ResChildParamBean>();
		for(ResParamBean bean:clzParams){
			datas.add(new ResChildParamBean(bean.getSubkind(), bean.getSubname()));
		}
		QueClzParamChildResp data = new QueClzParamChildResp(clzParams.get(0).getKind(),
				clzParams.get(0).getKindname(), datas);
		return data;
	}
	
	/**
	 * 调用BOSS查询配件接口queFitInfo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queFitInfo(QueFitInfoReq req,QueFitInfoResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo2();
		QueFitInfoReq req2Boss = new QueFitInfoReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		req2Boss.setAreaid(loginInfo.getAreaid());
		String response = getBossHttpInfOutput(req.getBizorderid(), 
				BossInterfaceService.QUE_FITTING_INFO_NEW, req2Boss, loginInfo);
		QueFitInfoBossResp bossResp = (QueFitInfoBossResp) BeanUtil.jsonToObject(response, QueFitInfoBossResp.class);
		handleBossResp(bossResp,resp,loginInfo.getCity());
		return returnInfo;
	}

	/**
	 * 调用BOSS查询配件接口queFitInfo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queNetWorkFitInfo(QueFitInfoReq req, QueNetWorkFitInfoResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		QueFitInfoReq req2Boss = new QueFitInfoReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		req2Boss.setAreaid(loginInfo.getAreaid());
		String response = getBossHttpInfOutput(req.getBizorderid(),
				BossInterfaceService.QUE_FITTING_INFO_NEW, req2Boss, loginInfo);
		QueFitInfoBossResp bossResp = (QueFitInfoBossResp) BeanUtil.jsonToObject(response, QueFitInfoBossResp.class);
		//handleBossResp(bossResp,resp,loginInfo.getCity());

			resp.setFittingList(bossResp.getFittingList());

		return returnInfo;
	}
	private void handleBossResp(QueFitInfoBossResp bossResp,QueFitInfoResp resp,String city) throws Exception{
		if(bossResp.getFittingList() != null && !bossResp.getFittingList().isEmpty()){
			List<QueFitInfoBossChildResp> list = addRuleHandle(bossResp,city);
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i < list.size();i++){
				QueFitInfoBossChildResp bean = bossResp.getFittingList().get(i);
				sb.append(bean.getUseprop());
				if(i != bossResp.getFittingList().size()-1){
					sb.append(",");
				}
			}
			QueFitInfoBossChildResp bean = new QueFitInfoBossChildResp();
			BeanUtils.copyProperties(bean, bossResp.getFittingList().get(0));
			bean.setUseprop(sb.toString());
			//ywp  fittingList中没有数据
			/*List<QueFitInfoBossChildResp> data = new ArrayList<QueFitInfoBossChildResp>();
			if(null!=bean){
				data.add(bean);
			}*/
			resp.setData(bean);
		}
	}
	
	private List<QueFitInfoBossChildResp> addRuleHandle(QueFitInfoBossResp bossResp,String city) throws Exception{
		List<QueFitInfoBossChildResp> fittingList = new ArrayList<QueFitInfoBossChildResp>();
		BeanUtils.copyProperties(fittingList, bossResp.getFittingList());
		Rule rule = ruleService.getRule(city, CITY_RES_USEPORP_RULE);
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			List<QueFitInfoBossChildResp> tmpList = new ArrayList<QueFitInfoBossChildResp>();
			String[] values = rule.getValue().split(",");
			for(QueFitInfoBossChildResp resp:fittingList){
				for(String s:values){
					if(resp.getUseprop().equals(s)){
						tmpList.add(resp);
						continue;
					}
				}
			}
			fittingList.removeAll(tmpList);
		}
		return fittingList;
	}
	
	/**
	 * 调用BOSS配件销售接口(待测试)
	 * @return
	 * @throws Exception 
	 */
	@Transient
	public ReturnInfo bizPartSales(BizPartSalesReq req,BizPartSalesResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		Date date = new Date();
		registerCustOrder(loginInfo,req,date);
		BizPartSalesBossReq req2Boss = new BizPartSalesBossReq(req.getCustid(),req.getHouseid(), 
				req.getDeviceSalesBOList());
		String result = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.BIZ_PARTS_SALES, req2Boss, loginInfo);
		BizPartSalesBossResp bossResp = (BizPartSalesBossResp) BeanUtil.jsonToObject(result, BizPartSalesBossResp.class);
		registerBizPortalOrder(date,bossResp.getOrdertype(),bossResp.getSums(),bossResp.getFeename(),Long.parseLong(req.getBizorderid()),Long.parseLong(bossResp.getOrderid()),req);
		resp.setCustOrderid(req.getBizorderid());
		return returnInfo;
	}


	/**
	 * 调用BOSS配件销售接口（组网业务订单池跳转过来)
	 * @return
	 * @throws Exception
	 */
	@Transient
	public ReturnInfo bizNetWorkPartSales(BizPartSalesReq req,BizPartSalesResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		Date date = new Date();
		registerCustOrder(loginInfo,req,date);
		BizPartSalesBossReq req2Boss = new BizPartSalesBossReq(req.getCustid(),req.getHouseid(),
				req.getDeviceSalesBOList(),req.getPreacceptserialno());
		String result = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.BIZ_PARTS_SALES, req2Boss, loginInfo);
		BizPartSalesBossResp bossResp = (BizPartSalesBossResp) BeanUtil.jsonToObject(result, BizPartSalesBossResp.class);
		registerBizPortalOrder(date,bossResp.getOrdertype(),bossResp.getSums(),bossResp.getFeename(),Long.parseLong(req.getBizorderid()),Long.parseLong(bossResp.getOrderid()),req);
		resp.setCustOrderid(req.getBizorderid());
		return returnInfo;
	}
	
	private void registerCustOrder(LoginInfo loginInfo,BizPartSalesReq req,Date date) throws Exception{
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAddr(req.getAddress());
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setCustid(req.getCustid());
		custOrder.setName(req.getCustName());
		custOrder.setScreenshots(req.getScreenshots());		//添加截图
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_PARTS_SALES);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(date);
		custOrder.setPortalOrder(null);
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setOrderstatus(BizCustorderOrderstatus.INIT);
		custOrder.setSyncmode(BizCustorderSyncmode.SYNC);
		if(StringUtils.isNotBlank(req.getVerifyphone())){
			custOrder.setVerifyphone(req.getVerifyphone());
		}
		getDAO().save(custOrder);
	}
	
	private void registerBizPortalOrder(Date date,String ordertype,String sums,
			String feename,Long orderid,Long responeid,BizPartSalesReq req) throws Exception{
		BizPortalOrder portalOrder = new BizPortalOrder();
		portalOrder.setCreatetime(date);
		portalOrder.setFees(sums);
		portalOrder.setFeestr(feename);
		portalOrder.setId(orderid);
		portalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		if(StringUtils.isNotBlank(ordertype)){
			portalOrder.setOrdertype(ordertype);
		}
		portalOrder.setResporderid(responeid);
		getDAO().save(portalOrder);
		//将订单号和订单池表关联起来
		if(req.getPreacceptserialno()!= null && StringUtils.isNotBlank(req.getPreacceptserialno())){
			//更新订单池订单对应状态
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE biz_networkbus_custorder_pool set salesorder = ?,salesfees = ? where preserialno = ? ");
			try {
				persistentService.clear();
				int i = persistentService.executeSql(sql.toString(),orderid,sums,req.getPreacceptserialno());
				if(i<=0){
					log.error("关联失败");
				}

			}catch (Exception e){
				log.error("关联异常");
			}
		}
	}
	
	/**
	 * 配件销售确认接口
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Transient
	public ReturnInfo bizSaleCommit(BizSaleCommitReq req) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		Date date = new Date();
		CheckUtils.checkEmpty(req.getPayway(), "支付方式不能为空");
		CheckUtils.checkEmpty(req.getPaycode(), "支付编码不能为空");
		CheckUtils.checkNull(getDAO().find(CustOrder.class,Long.parseLong(req.getOrderid())), "查询不到该订单信息");
		CheckUtils.checkNull(getDAO().find(BizPortalOrder.class,Long.parseLong(req.getOrderid())), "查询不到该订单信息");
		CustOrder custOrder = (CustOrder) getDAO().find(CustOrder.class,Long.parseLong(req.getOrderid()));
		BizPortalOrder portalOrder = (BizPortalOrder) getDAO().find(BizPortalOrder.class,Long.parseLong(req.getOrderid()));
		CheckUtils.checkNull(portalOrder.getResporderid(),"BOSS订单为空,请联系管理人员校验数据");
		CheckUtils.checkNull(custOrder.getCustid(), "订单客户编号为空,请联系管理人员校验数据");
		String multipay = "N";
        if(StringUtils.isNotBlank(req.getMultipaywayflag()) && req.getMultipaywayflag().equalsIgnoreCase("Y")) {
        	if(StringUtils.isNotBlank(req.getCashe())) {
        		double payFees = Double.parseDouble(portalOrder.getFees()) - Double.parseDouble(req.getCashe());
        		if(payFees > 0.0) {
        			portalOrder.setMultipay("Y");
        			multipay = "Y";
        		}
        		portalOrder.setPayFees(Double.toString(payFees));
        	}
        }
		BizSaleCommitBossReq req2Boss = getReq2Boss(req, custOrder.getCustid(), portalOrder.getResporderid(),multipay);
		ParamsManager.isCorrectData(req.getPaycode(),req.getPaycode());
		String result = getBossHttpInfOutput(req.getBizorderid(), 
				BossInterfaceService.BIZ_SALES_COMMIT, req2Boss, loginInfo);
		portalOrder.setPayway(req.getPayway());
		if(StringUtils.isNotBlank(req.getPayway())){
			portalOrder.setWgpayway(req.getPayway());
		}
		portalOrder.setPaycode(req.getPaycode());
		portalOrder.setPaytime(date);
		portalOrder.setPayreqid(req.getPayreqid());
		portalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
		getDAO().update(portalOrder);
		if(req.getOrderid() != null){
			updateBizNwtworkPool(req);
		}

		return returnInfo;
	}

	private void updateBizNwtworkPool(final BizSaleCommitReq req){
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					//先查是不是和订单池关联的订单
					StringBuffer sqlBuffer = new StringBuffer();
					sqlBuffer.append("SELECT a.biznetorder_id as id,a.custid,a.custname,a.preserialno,a.link_Addr as linkAddr,a.createoper ");
					sqlBuffer.append("	,a.createtime,a.pretime,a.def_Name defName ,a.opcode,a.defdesc,a.remark,a.applydept,a.payway,a.result ");
					sqlBuffer.append("	,a.datadesc,a.whgridcode,a.applyoperid,a.operid,a.optime,a.areaid,a.status as status  ");
					sqlBuffer.append(" from biz_networkbus_custorder_pool a WHERE  salesorder = ? ");
					List params = new ArrayList();
					params.add(req.getOrderid());
					try {
						List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList = getDAO().find(sqlBuffer.toString(), CustBizNetWorkOrderPool.class,
								params.toArray());
						if(custBizNetWorkOrderPoolList==null || custBizNetWorkOrderPoolList.size() < 1 ){
							return;
						}
					}catch (Exception e){
						log.error("查询异常");
					}
					//更新组网订单池状态为 1
					//更新订单池订单对应状态
					StringBuffer sql = new StringBuffer();
					sql.append(" UPDATE biz_networkbus_custorder_pool set status = 1 where salesorder = ? ");
					try {
						persistentService.clear();
						int i = persistentService.executeSql(sql.toString(),req.getOrderid());
						if(i<=0){
							log.error("更新失败");
						}

					}catch (Exception e){
						log.error("更新异常");
					}
				}
			}).start();

		}catch (Exception e){
			log.info("update file " + e.getMessage());
		}
	}
	
	private BizSaleCommitBossReq getReq2Boss(BizSaleCommitReq req,Long custid,Long orderid,String multipaywayflag){
		BizSaleCommitBossReq req2Boss = new BizSaleCommitBossReq();
		req2Boss.setBankaccno(req.getBankaccno());
		req2Boss.setCustid(custid);
		req2Boss.setOrderid(orderid);
		req2Boss.setPaycode(req.getPaycode());
		req2Boss.setPayreqid(req.getPayreqid());
		req2Boss.setPayway(req.getPayway());
		req2Boss.setMultipaywayflag(multipaywayflag);
		return req2Boss;
	}

	/**
	 * 调用BOSS接口查询绑定配件列表
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBindSalesinfo(QueBindSalesInterReq req, QueBindSalesInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
        //LoginInfo loginInfo = InterTest.getGridTestLoginInfo2();
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getSubkind(),"没有请求设备子类型参数");
		QueBindSalesBossReq bossReq = new QueBindSalesBossReq();
		bossReq.setSubkind(req.getSubkind());
		//调用BOSS接口
		String serverCode = BizConstant.BossInterfaceService.QUE_BINDSALES;
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		bossRespOutput = "{\"salesBossInfoList\":" + bossRespOutput + "}";
		QueBindSalesBossResp bossResp =(QueBindSalesBossResp) BeanUtil.jsonToObject(bossRespOutput,QueBindSalesBossResp.class);
		resp.setSalesBossInfoList(bossResp.getSalesBossInfoList());
		return returnInfo;
	}


	/**
	 * 根鋸配件列表接口返回的 兩個類型 去獲取配件信息及單價
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBindSalesPriceinfo(QueBindSalesPriceInterReq req,QueBindSalesPriceInterResp resp)throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
        //LoginInfo loginInfo = InterTest.getGridTestLoginInfo3();
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkNull(req.getBindSalesPriceList(), "请求对象不能为空");
		List<QueFitInfoBossChildResp> queFitInfoBossChildRespList = new ArrayList<QueFitInfoBossChildResp>();
		//循环获取每个配件的单价信息
		for (bindSalesPrice bindSalesPrice:req.getBindSalesPriceList()
			 ) {
		    CheckUtils.checkEmpty(bindSalesPrice.getBindkind(),"配件类型为空");
		    CheckUtils.checkEmpty(bindSalesPrice.getBindsubkind(),"配件子类为空");
		    CheckUtils.checkEmpty(bindSalesPrice.getUseprop(),"请选择设备来源");
			QueFitInfoReq req2Boss = new QueFitInfoReq();
			req2Boss.setKind(bindSalesPrice.getBindkind());
			req2Boss.setSubkind(bindSalesPrice.getBindsubkind());
			req2Boss.setAreaid(loginInfo.getAreaid());
			String response = getBossHttpInfOutput(req.getBizorderid(),
					BossInterfaceService.QUE_FITTING_INFO_NEW, req2Boss, loginInfo);
			QueFitInfoBossResp bossResp = (QueFitInfoBossResp) BeanUtil.jsonToObject(response, QueFitInfoBossResp.class);
			//前端选择来源  与获取的列表来源对比  取这个单价   没有则为0
            if(bossResp.getFittingList() != null && !bossResp.getFittingList().isEmpty()){
                StringBuffer sb = new StringBuffer();
                List<QueFitInfoBossChildResp> list = bossResp.getFittingList();
                sign:
                for(int i = 0;i < list.size();i++){
                    QueFitInfoBossChildResp bean = bossResp.getFittingList().get(i);
                    sb.append(bean.getUseprop());
                    if(i != bossResp.getFittingList().size()-1){
                        sb.append(",");
                    }
                    if(bean.getUseprop().equals(bindSalesPrice.getUseprop())){
                        //list.get(i).setSubkind(a);
                        queFitInfoBossChildRespList.add(list.get(i));
                        break sign;
                    }
                }
                //如果返回列表没有相应的选择设备来源   则单价为0
                if(!bindSalesPrice.getUseprop().contains(sb)){
                    QueFitInfoBossChildResp queFitInfoBossChildResp = new QueFitInfoBossChildResp();
                    queFitInfoBossChildResp.setVoucherprice("0");
                    queFitInfoBossChildResp.setSubkind(bindSalesPrice.getBindsubkind());
                    queFitInfoBossChildResp.setPid(bossResp.getFittingList().get(0).getPid());
                    queFitInfoBossChildResp.setPlace(bossResp.getFittingList().get(0).getPlace());
					queFitInfoBossChildResp.setReshavno("boss没有配置单价,请联系配置");
					queFitInfoBossChildRespList.add(queFitInfoBossChildResp);
                }

            }
            else {
                QueFitInfoBossChildResp queFitInfoBossChildResp = new QueFitInfoBossChildResp();
                queFitInfoBossChildResp.setVoucherprice("0");
                queFitInfoBossChildResp.setSubkind(bindSalesPrice.getBindsubkind());
				queFitInfoBossChildResp.setPname("boss没有配置单价,请联系配置");
				queFitInfoBossChildRespList.add(queFitInfoBossChildResp);
            }

		}
		resp.setQueFitInfoBossChildRespList(queFitInfoBossChildRespList);
		return returnInfo;
	}

	/**
	 * 查询客户订单池有没有已经销售设备的订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSalesNetBusOrder(QueSalesNetBusOrderReq req, QueSalesNetBusOrderResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getCustid(),"请输入客户编号");

		//查询数据  查询以及销售设备的订单
		List params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.biznetorder_id as id,a.custid,a.custname,a.preserialno,a.link_Addr as linkAddr,a.createoper ");
		sqlBuffer.append("	,a.createtime,a.pretime,a.def_Name defName ,a.opcode,a.defdesc,a.remark,a.applydept,a.payway,a.result ");
		sqlBuffer.append("	,a.datadesc,a.whgridcode,a.applyoperid,a.operid,a.optime,a.areaid,a.status as status  ");
		sqlBuffer.append(" from biz_networkbus_custorder_pool a WHERE a.custid = ? and a.status = ?  ");
		params.add(req.getCustid());
		params.add("1");

		List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList = getDAO().find(sqlBuffer.toString(), CustBizNetWorkOrderPool.class,params.toArray());
		if(custBizNetWorkOrderPoolList!= null && custBizNetWorkOrderPoolList.size() > 0){
			resp.setCustBizNetWorkOrderPoolList(custBizNetWorkOrderPoolList);
		}

		return returnInfo;
	}

}
