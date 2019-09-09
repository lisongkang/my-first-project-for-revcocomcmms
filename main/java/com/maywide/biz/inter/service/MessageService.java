package com.maywide.biz.inter.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.GoodsBySeiesBo;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.QueGoodsBySeriesReq;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.QueGoodsBySeriesResp;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.GoodsNodeBean;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.GoodsSeriesNameBean;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.QueGoodsNodesReq;
import com.maywide.biz.inter.pojo.goods.queGoodsNodes.QueGoodsNodesResp;
import com.maywide.biz.inter.pojo.pubpost.PubPostReadReq;
import com.maywide.biz.inter.pojo.pubpost.QueryPubPostResp;
import com.maywide.biz.inter.pojo.queBusOrderInfo.GridSysPrams;
import com.maywide.biz.inter.pojo.queBusOrderInfo.QueBusOrderInfoResp;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.pubpost.entity.PubPost;
import com.maywide.common.pubpost.entity.PubPostRead;
import com.maywide.common.pubpost.service.PubPostReadService;
import com.maywide.common.pubpost.service.PubPostService;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;
import com.maywide.test.InterTest;

@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService extends CommonService {
	@Autowired
    private PubPostService pubPostService;
	@Autowired
    private PubPostReadService pubPostReadService;
	
	@Transactional(readOnly = true)
	public ReturnInfo queryPubPost(QueryPubPostResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        
        if (loginInfo == null) {
        	returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_LOGIN_CODE);
        	returnInfo.setMessage(CommonNotice.LOGIN_OUT_NOTICE);
        	
        	return returnInfo;
        }
        
        List<PubPost> notifyList = getUnreadPubPost(loginInfo);
        
        resp.setPubPostList(notifyList);
        
        return returnInfo;
	}
	
	public ReturnInfo viewPubPost(PubPostReadReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        
        if (loginInfo == null) {
        	returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_LOGIN_CODE);
        	returnInfo.setMessage(CommonNotice.LOGIN_OUT_NOTICE);
        	
        	return returnInfo;
        }
        
        PrvOperator oper = new PrvOperator();
        oper.setId(req.getOperid());
        
        PubPost pubPost = pubPostService.findOne(req.getPubPostid());
        PubPostRead pubPostRead = pubPostReadService.findReaded(oper, pubPost);
        
        if (pubPostRead == null) {
            pubPostRead = new PubPostRead();
            pubPostRead.setFirstReadTime(new Date());
            pubPostRead.setReadTotalCount(1);
            pubPostRead.setReadUser(oper);
            pubPostRead.setPubPost(pubPost);

            if (pubPost.getReadUserCount() == null) {
            	pubPost.setReadUserCount(1);
            } else {
            	pubPost.setReadUserCount(pubPost.getReadUserCount() + 1);
            }
        } else {
            pubPostRead.setLastReadTime(new Date());
            pubPostRead.setReadTotalCount(pubPostRead.getReadTotalCount() + 1);
        }
        
        pubPostReadService.save(pubPostRead);
        pubPostService.save(pubPost);
        
        return returnInfo;
	}
	
	public ReturnInfo deletePubPost(PubPostReadReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        
        if (loginInfo == null) {
        	returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_LOGIN_CODE);
        	returnInfo.setMessage(CommonNotice.LOGIN_OUT_NOTICE);
        	
        	return returnInfo;
        }
        
        PrvOperator oper = new PrvOperator();
        oper.setId(req.getOperid());
        
        PubPost pubPost = pubPostService.findOne(req.getPubPostid());
        PubPostRead pubPostRead = pubPostReadService.findReaded(oper, pubPost);
        
        if (pubPostRead != null) {
        	pubPostRead.setReadTotalCount(-1);
        } else {
        	pubPostRead = new PubPostRead();
            pubPostRead.setFirstReadTime(new Date());
            pubPostRead.setReadTotalCount(-1);
            pubPost.setReadUserCount(1);
            pubPostRead.setReadUser(oper);
            pubPostRead.setPubPost(pubPost);
        }
        pubPostReadService.save(pubPostRead);
        
        return returnInfo;
	}
	
	private List<PubPost> getUnreadPubPost(LoginInfo loginInfo) throws Exception {
		List<PubPost> pubPosts = pubPostService.findFrontPublished(loginInfo);
        List<PubPost> notifyList = Lists.newArrayList();
        Map<Serializable, Boolean> idMaps = Maps.newHashMap();
        Set<String> deleteSet = Sets.newHashSet();
        
        if (CollectionUtils.isNotEmpty(pubPosts)) {
        	PrvOperator oper = new PrvOperator();
        	oper.setId(loginInfo.getOperid());
        	List<PubPostRead> pubPostReads = pubPostReadService.findReaded(oper, pubPosts);
        	
        	for (PubPost pubPost : pubPosts) {
                idMaps.put(pubPost.getId(), Boolean.FALSE);
                for (PubPostRead pubPostRead : pubPostReads) {
                    if (pubPostRead.getPubPost().equals(pubPost)) {
                    	if (pubPostRead.getReadTotalCount() < 0) {
                    		deleteSet.add(pubPost.getId());
                    	}
                        idMaps.put(pubPost.getId(), Boolean.TRUE);
                        break;
                    }
                }
            }
        	
        	for (PubPost pubPost : pubPosts) {
        		if (deleteSet.contains(pubPost.getId())) {
        			continue;
        		}
        		pubPost.setIsRead(idMaps.get(pubPost.getId()));
                notifyList.add(pubPost);
            }
        }
        
        return notifyList;
	}
	
	@Transactional(readOnly = true)
	public ReturnInfo queGoodsNodes(QueGoodsNodesReq req, QueGoodsNodesResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
//    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
        CheckUtils.checkNull(req, "请求信息不能为空");
//		CheckUtils.checkEmpty(req.getCustid(), "匹配条件[custid]不能为空");
//		CheckUtils.checkEmpty(req.getStepcode(), "匹配条件[stepcode]不能为空");
//        
        String goodsCode = req.getGoodsCode();
    	List params = new ArrayList();
//		params.add(bean.getOperid());
//		params.add(bean.getAreaid());

		StringBuffer sb = new StringBuffer();
		sb.append(
				"		SELECT a.id  nodeId,a.prenodeid preNodeId,a.rootnodeid rootNodeId,c.rule_name ruleName,if(c.value_source = 'static', ");
		sb.append("		 (select mname from prv_sysparam where gcode = c.`value` and mcode = a.node_value),");
		sb.append("		'') as nodeName, a.node_value nodeValue , ");
//		sb.append("		CONCAT('新商品',FLOOR(RAND() * 10000)) ) as nodeName, ");
		sb.append("     IF(c.value_source='sql',CONCAT(c.`value`,a.node_value),'') as valueSql, ");
		sb.append("		(select percentage from goods_percentage_rule where node_id = a.id limit 1 ) as percentage ");
		sb.append("		 FROM goods_node_sub a ,goods_rule c WHERE FIND_IN_SET(a.id,  ");
		sb.append("		 getGoodsSubNodeByRootNode((select id from goods_node_root a where a.city = 'ZS' and a.node_code = 'AAA' and a.`status` = '1')))  ");
		sb.append("		  and c.rule_code = a.node_code  ");
		
		System.out.println(sb.toString());
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
        
//		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(),  BizConstant.BossInterfaceService.WFL_QUE_EQUIPINFO, req, loginInfo);
//
//		resp = (WflQueEquipInfoResp) this.copyBossResp2InterResp(resp,
//				WflQueEquipInfoResp.class, bossRespOutput);
//        
        return returnInfo;
	}
	
	private void loopGoodsNodeList(GoodsNodeBean node,List<GoodsNodeBean> goodsNodeList){
		for (GoodsNodeBean nodeItem:goodsNodeList) {
			if (node.getNodeId().equals(nodeItem.getPreNodeId())) {
//				if (node.getChildNodeList()==null) {
//					node.getChildNodeList() = new ArrayList();
//				}
				node.getChildNodeList().add(nodeItem);
				loopGoodsNodeList(nodeItem, goodsNodeList);
			}
		}
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
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

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
}
