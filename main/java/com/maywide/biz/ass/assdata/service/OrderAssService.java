package com.maywide.biz.ass.assdata.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.deleteorder.DeleteOrderBossReq;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class OrderAssService extends CommonService {

	@Autowired
	PersistentService persistentService;

	private static final Log log = LogFactory.getLog(OrderAssService.class);

	public void handlerNoPayOrder() throws Exception {
		List<BizPortalOrder> orderList = getOrderList();
		if (orderList != null && !orderList.isEmpty() && orderList.size() > 0) {
			System.out.println("订单列表长度为：" + orderList.size());
			System.out.println("开始删除订单");
			BaseApiRequest apiRequest = new BaseApiRequest();
//			apiRequest.setBizorderid(String.valueOf(persistentService.getSequence("SEQ_BIZ_CUSTORDER_ID")));
			apiRequest.setBizorderid(getBizorderid());
			for (BizPortalOrder order : orderList) {
				System.out.println("当前为列表的第" + orderList.indexOf(order) + "个");
				if (order.getResporderid() == null || order.getId() == null) {
					log.info("查出来的resporderid为空。。。。。");
					continue;
				}
				System.out.println("查出来的resporderid为：" + order.getResporderid() + "======================");
				CustOrder custOrder = new CustOrder();
				custOrder.setId(order.getId());
				List<CustOrder> datas = getDAO().find(custOrder);
				if (datas != null && datas.size() != 0 && datas.get(0) != null) {
					
					custOrder = datas.get(0);
					PrvDepartment prvDepartment = new PrvDepartment();
					prvDepartment.setId(custOrder.getOprdep());
					System.out.println("查询的部门id为"+custOrder.getOprdep());
					PrvOperator prvOperator = new PrvOperator();
					prvOperator.setId(custOrder.getOperator());
					System.out.println("查询的操作员id为"+custOrder.getOperator());
					List<PrvDepartment> prvDepartments = getDAO().find(prvDepartment);
					List<PrvOperator> prvOperators = getDAO().find(prvOperator);
					System.out.println("查询出来的部门列表长度:"+prvDepartments.size());
					System.out.println("查询出来的操作员列表长度:"+prvOperators.size());
					if ((prvDepartments != null && prvDepartments.size() != 0 && prvDepartments.get(0) != null)
							&& (prvOperators != null && prvOperators.size() != 0 && prvOperators.get(0) != null)) {
						
						System.out.println("查询得到操作员部门");
						System.out.println("查询得到操作员信息");
						prvOperator = prvOperators.get(0);
						prvDepartment = prvDepartments.get(0);
						
						LoginInfo loginInfo = new LoginInfo();
						loginInfo.setOperid(custOrder.getOperator());
						loginInfo.setDeptid(custOrder.getOprdep());
						loginInfo.setLoginname(prvOperator.getLoginname());
						loginInfo.setCity(prvDepartment.getCity());
						DeleteOrderBossReq req = new DeleteOrderBossReq();
						req.setOrderid(order.getResporderid() + "");
						System.out.println("开始调用boss接口");
						String bossResult = getBossHttpInfOutput(apiRequest.getBizorderid(),
								BizConstant.BossInterfaceService.BIZ_DELORDER, req, loginInfo);
						log.info("bossResult处理结果返回：======》" + bossResult);
						if (bossResult == null || bossResult.equals("null")) {
							System.out.println("开始更新网格本地数据库");
							List paramList = new ArrayList();
							StringBuffer sql = new StringBuffer();
							sql.append(" update biz_portal_order bpo ");
							sql.append("    set bpo.status = ? ");
							paramList.add(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_OVERDUE);
							sql.append("  where bpo.orderid = ? ");
							paramList.add(order.getId());
							getDAO().executeSql(sql.toString(), paramList.toArray());
							System.out.println("结束更新网格本地数据库");
						}
						log.info("调用boss接口删除订单==========>结束");
					}else{
						System.out.println("查询出错,查询不出数据");
					}
				} else {
					System.out.println("查询不到订单操作员======================");
				}
			}
		}

	}

	private List<BizPortalOrder> getOrderList() throws Exception {
		System.out.println("定时任务查询订单列表开始");
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT T.*,T.orderid id FROM ");
		sql.append("		BIZ_PORTAL_ORDER T");
		sql.append("		WHERE 1 = 1 ");
		sql.append("		AND T.STATUS = ?");
		sql.append("		AND T.CREATETIME IS NOT NULL");
		sql.append("		AND T.RESPORDERID IS NOT NULL");
		params.add(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		sql.append ("    AND T.CREATETIME < CURDATE() ");
		
		List<BizPortalOrder> orderList = getDAO().find(sql.toString(), BizPortalOrder.class, params.toArray());
		System.out.println("定时任务查询订单列表结束");
		return orderList;
	}

}
