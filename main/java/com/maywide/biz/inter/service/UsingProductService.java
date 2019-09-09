package com.maywide.biz.inter.service;

import com.maywide.biz.auth.pojo.queSeqGrant.QueSeqGrantReq;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.usingProduct.UsingProductBossReq;
import com.maywide.biz.inter.pojo.usingProduct.UsingProductReq;
import com.maywide.biz.inter.pojo.usingProduct.UsingProductResp;
import com.maywide.biz.inter.pojo.usingProduct.UsingProductResp.ProdsBean;
import com.maywide.biz.system.entity.BizRuleDet;
import com.maywide.core.cons.Constant;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzy 查询在用产品服务
 */
@Service
public class UsingProductService extends CommonService {

	/**
	 * 查询在用产品接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queryUsingProduct(UsingProductReq req,
			UsingProductResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo,
				QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getPercomb(), "percomb参数不能为空");
		String city = loginInfo.getCity();

		if (!queBizRule(city, req.getPercomb())) {
			return returnInfo;
		}

		List<ProdsBean> prods = new ArrayList<UsingProductResp.ProdsBean>();
		if (req.getServid() != null) {
			String[] servids = req.getServid().split(",");

			if (servids != null) {
				for (String servid : servids) {
					UsingProductBossReq bossReq = new UsingProductBossReq();
					bossReq.setCustid(req.getCustid());
					bossReq.setServid(servid);

					prods = quePro(bossReq, prods, loginInfo);
				}
			}

		}

		resp.setProds(prods);

		// 调boss接口,并将结果存入resp
		// callBossInf4queryUsingProduct(req, resp,loginInfo);
		return returnInfo;
	}

	private List<ProdsBean> quePro(UsingProductBossReq req,
			List<ProdsBean> prods, LoginInfo loginInfo) throws Exception {
//		req.setBizorderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID")
//				.toString());
		req.setBizorderid(getBizorderid());

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_SERVPRDINFO, req,
				loginInfo);

		UsingProductResp bossResp = (UsingProductResp) BeanUtil.jsonToObject(
				bossRespOutput, UsingProductResp.class);
		List<ProdsBean> currentProds = bossResp.getProds();
		if (currentProds != null && currentProds.size() > 0) {
			for (ProdsBean currentPro : currentProds) {
				boolean hasexist = false;
				for (ProdsBean pro : prods) {
					if (pro.getPid().equals(currentPro.getPid())) {
						hasexist = true;
						break;
					}
				}
				if (!hasexist) {
					prods.add(currentPro);
				}
			}
		}
		return prods;

	}

	public boolean queBizRule(String city, String percomb) throws Exception {
		// List params = new ArrayList();
		// StringBuffer sb = new StringBuffer();
		// sb.append("		SELECT count(*) FROM biz_rule");
		// sb.append("		WHERE rule = ?");
		// params.add(Constant.PrvSysparamCode.NEED_UNSUBSCRIBE_CITY);
		// sb.append("		AND city = ?");
		// params.add(city);
		// sb.append("		AND value LIKE ? ");
		// params.add("%"+percomb+"%");
		// Long count = getDAO().count(sb.toString(), params.toArray());
		// if (count>0){
		// return true;
		// }
		// return false;
		String sql = "SELECT RULEID, RULE, RULENAME, CITY, AREAIDS, PERMISSION, VALUE FROM biz_rule WHERE rule=? AND city IN(?,?) ";
		List<Rule> list = getDAO().find(sql, Rule.class,
				Constant.PrvSysparamCode.NEED_UNSUBSCRIBE_CITY, city, "*");
		if (list != null && list.size() > 0) {
			Rule rule = list.get(0);
			String[] valueArray = rule.getValue().split(",");
			for (String value : valueArray) {
				if (percomb.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	private void callBossInf4queryUsingProduct(UsingProductReq req,
			UsingProductResp resp, LoginInfo loginInfo) throws Exception {

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_SERVPRDINFO, req,
				loginInfo);

		UsingProductResp bossResp = (UsingProductResp) BeanUtil.jsonToObject(
				bossRespOutput, UsingProductResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}

}
