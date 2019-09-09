package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.GoodsBySeiesBo;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.QueGoodsBySeriesReq;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.QueGoodsBySeriesResp;
import com.maywide.biz.inter.pojo.goods.queGoodsListZs.QueGoodsListZsReq;
import com.maywide.biz.inter.pojo.goods.queGoodsListZs.QueGoodsListZsResp;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.GoodsNodeBean;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.GoodsSeriesNameBean;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.QueGoodsNodesReq;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.QueGoodsNodesResp;
import com.maywide.biz.inter.pojo.queryProduct.QuePkgListResp;
import com.maywide.biz.inter.pojo.querysalespkgknow.Cycle;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossReq;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossResp;
import com.maywide.biz.inter.pojo.queuserprd.UserPrdsBO;
import com.maywide.biz.inter.pojo.seqGrantLog.SeqGrantLogReq;
import com.maywide.biz.inter.pojo.seqGrantLog.SeqGrantLogResp;
import com.maywide.biz.prv.service.PrvOperroleService;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.test.InterTest;
/**
 * 中山新商品相关接口
 * @author 张耀其
 *
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsService extends CommonService {
	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private InterPrdService interPrdService;

	/**
	 * 中山滚轮
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queGoodsNodes(QueGoodsNodesReq req, QueGoodsNodesResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
//    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//    	LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
//		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
        CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getGoodsCode(), "匹配条件[goodCode]不能为空");
		CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空");
//		CheckUtils.checkEmpty(req.getCustid(), "匹配条件[custid]不能为空");
//        
    	
    	
//    	QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req.getCustid());
//		String serverCode = "";
//		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
//			serverCode = BizConstant.ServerCityBossInterface.QUE_USERPRDINFO;
//		}else{
//			serverCode = BizConstant.BossInterfaceService.QUE_USERPRDINFO;
//		}
//		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
//				serverCode, req2Boss,loginInfo);
//		if(StringUtils.isNotBlank(bossRespOutput)){
//			 QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil
//		                .jsonToObject(bossRespOutput, QueUserPrdBossResp.class);
//			 List<UserPrdsBO> prods = bossResp.getProds();
////			 String[] seriesIds = new String[]{};
//			 String[] pids = new String[]{};
//			 for (int i = 0; i < prods.size(); i++) {
//				pids[i] = prods.get(i).getPid();
//			 }
//			 
////			 for(QuePkgListResp product:resp){
////				 for(UserPrdsBO prd:bossResp.getProds()){
////					 if(prd.getPid().equals(product.getPid())){
////						 product.setOrdered(true);
////						 break;
////					 }
////				 }
////			 }
//		}
		List params = new ArrayList();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"		SELECT a.id  nodeId,a.prenodeid preNodeId,a.rootnodeid rootNodeId,c.rule_name ruleName,if(c.value_source = 'static', ");
		sb.append("		 (select mname from prv_sysparam where gcode = c.`value` and mcode = a.node_value),");
		sb.append("		'') as nodeName, a.node_value nodeValue , ");
		sb.append("     IF(c.value_source='sql',CONCAT(c.`value`,a.node_value),'') as valueSql, ");
		sb.append("		(select percentage from goods_percentage_rule where node_id = a.id and status = '1' limit 1 ) as percentage ");
		sb.append("		 FROM goods_node_sub a ,goods_rule c WHERE c.status = '1' and FIND_IN_SET(a.id,  ");
		sb.append("		 getGoodsSubNodeByRootNode((select id from goods_node_root a where a.city = ? and a.node_code = ? and a.`status` = '1')))  ");
		sb.append("		  and c.rule_code = a.node_code  ");
		
		System.out.println(sb.toString());
		params.add(req.getCity());
		params.add(req.getGoodsCode());
		List<GoodsNodeBean> goodsNodeList = getDAO().find(sb.toString(), GoodsNodeBean.class, params.toArray());

		
		List<GoodsNodeBean> rootGoodsNodeList = new ArrayList<GoodsNodeBean>();
		if (goodsNodeList!=null) {
			for(GoodsNodeBean node : goodsNodeList){
				if (StringUtils.isEmpty(node.getNodeName())&&StringUtils.isNotEmpty(node.getValueSql())) {
					List<GoodsSeriesNameBean> seriesNameList = getDAO().find(node.getValueSql(),GoodsSeriesNameBean.class);
					if (seriesNameList!=null&&seriesNameList.size()>0) {
						node.setValueSql(null);
						node.setNodeName(seriesNameList.get(0).getSeriesName());
					}
				}
				
				if (StringUtils.isEmpty(node.getPreNodeId())) {
					loopGoodsNodeList(node, goodsNodeList);
					rootGoodsNodeList.add(node);
					resp.setGoodsNodeList(rootGoodsNodeList);
				}
			}
		}
        return returnInfo;
	}
	
	private void loopGoodsNodeList(GoodsNodeBean node,List<GoodsNodeBean> goodsNodeList){
		for (GoodsNodeBean nodeItem:goodsNodeList) {
			if (node.getNodeId().equals(nodeItem.getPreNodeId())) {
				node.getChildNodeList().add(nodeItem);
				loopGoodsNodeList(nodeItem, goodsNodeList);
			}
		}
	}
	
	 private QueUserPrdBossReq getQueUserPrdReq2Boss(String custid) {
	        QueUserPrdBossReq queUserPrdBossReq = new QueUserPrdBossReq();
	        queUserPrdBossReq.setCustid(custid);
	        queUserPrdBossReq.setPermark("");
	        queUserPrdBossReq.setPstatus("");
	        queUserPrdBossReq.setServid("");

	        return queUserPrdBossReq;
	    }
	
	/**
	 * 下销售单根据商品系列查询商品和orderid
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGoodsBySeries(QueGoodsBySeriesReq req,QueGoodsBySeriesResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
//		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getSeriesId(), "匹配条件[seriesId]不能为空");
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("		select * from goods_series_item a ,prd_salespkg_know b  ");
		sb.append("		where a.series_id = ? and a.knowid = b.KNOWID");

		List<GoodsBySeiesBo> goods = getDAO().find(sb.toString(), GoodsBySeiesBo.class, req.getSeriesId());
		
		resp.setGoods(goods);
		
//		resp.setOrderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		resp.setOrderid(getBizorderid());
		return returnInfo;
	}
	
	
	/**
	 * 商品订购查询商品--中山
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGoodsListZs(QueGoodsListZsReq req,ArrayList<QueGoodsListZsResp> resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getSeriesId(), "匹配条件[seriesId]不能为空");
		
		List params = new ArrayList();
		
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT  DISTINCT (D.KNOWID) KNOWID,");
		sb.append("		D.KNOWNAME,D.IMAGE,D.PRICE,D.OBJID OBJID,D.OBJTYPE OBJTYPE,D.ICON ICON,");
		sb.append("		D.WORDEXP INTRODUCE,D.BRIEF BRIEF,E.PERMARK perMark,C.PID pid");
		sb.append("		FROM goods_series A,goods_series_item B,PRD_SALES C,PRD_SALESPKG_KNOW D ,PRD_PCODE E");
		sb.append("		WHERE 1 = 1 ");
		sb.append("		AND C.PID = E.PID");
		sb.append("		AND A.series_id = B.series_id");
		sb.append("		AND B.KNOWID = D.KNOWID");
		sb.append("		AND C.SALESID = D.OBJID");
//		sb.append("		AND A.CATALOGSTATUS = ?");
//		sb.append("		AND A.CITY LIKE ?");
//		params.add("Y");
//		params.add("%"+loginInfo.getCity()+"%");
		if (StringUtils.isNotBlank(req.getSeriesId())) {
			sb.append("		AND A.series_id = ?");
			params.add(req.getSeriesId());
		}
//		if (StringUtils.isNotBlank(req.getSalespkgcode())) {
//			sb.append("		AND C.salescode = ?");
//			params.add(req.getSalespkgcode());
//		}
		sb.append("		");
		sb.append("		UNION ");
		sb.append("		(SELECT  (D1.KNOWID) KNOWID,");
		sb.append("		");
		sb.append("		");
		sb.append("		D1.KNOWNAME,D1.IMAGE,D1.PRICE,D1.OBJID OBJID,");
		sb.append("		D1.OBJTYPE OBJTYPE,D1.ICON ICON,D1.WORDEXP INTRODUCE,");
		sb.append("		D1.BRIEF BRIEF,'' perMark,F1.SALESPKGID pid");
		sb.append("		FROM");
		sb.append("		goods_series A1,goods_series_item B1,PRD_SALESPKG_KNOW D1,prd_salespkg F1");
		sb.append("		WHERE A1.series_id = B1.series_id");
		sb.append("		AND B1.KNOWID = D1.KNOWID");
		sb.append("		AND F1.SALESPKGID = D1.OBJID");
//		sb.append("		AND A1.CATALOGSTATUS = ?");
//		sb.append("		AND A1.CITY LIKE ?");
//		params.add("Y");
//		params.add("%"+loginInfo.getCity()+"%");
		if (StringUtils.isNotBlank(req.getSeriesId())) {
			sb.append("		AND A1.series_id = ?");
			params.add(req.getSeriesId());
		}
//		if (StringUtils.isNotBlank(req.getSalespkgcode())) {
//			sb.append("		AND F1.salespkgcode = ?");
//			params.add(req.getSalespkgcode());
//		}
		sb.append("		)");
		List<QueGoodsListZsResp> datas = getDAO().find(sb.toString(), QueGoodsListZsResp.class, params.toArray());
		if(datas != null){
			datas = new ArrayList<QueGoodsListZsResp>(new HashSet<QueGoodsListZsResp>(datas));
		}
		
		for(QueGoodsListZsResp data:datas){
			if(data.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)||
					data.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)){
				data.setUnitName("月");
			}else{
				Cycle cycle = interPrdService.gettPkgUnit(Long.parseLong(data.getObjId()));
				if(cycle != null){
					data.setUnitName(cycle.getUnitName());
				}else{
					data.setUnitName("周期");
				}
			}
			
		}
		resp.addAll(datas);
		if(StringUtils.isNotBlank(req.getCustid())){
			QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req.getCustid());
			String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.QUE_USERPRDINFO;
			}else{
				serverCode = BizConstant.BossInterfaceService.QUE_USERPRDINFO;
			}
			String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
					serverCode, req2Boss,loginInfo);
			if(StringUtils.isNotBlank(bossRespOutput)){
				 QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil
			                .jsonToObject(bossRespOutput, QueUserPrdBossResp.class);
				 for(QueGoodsListZsResp product:resp){
					 for(UserPrdsBO prd:bossResp.getProds()){
						 if(prd.getPid().equals(product.getPid())){
							 product.setOrdered(true);
							 break;
						 }
					 }
				 }
			}
		}
		return returnInfo;
	}
	
	
	
}
