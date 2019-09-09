package com.maywide.biz.salary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.BillingHttpClientService;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.pojo.BeforehandRealBO;
import com.maywide.biz.salary.pojo.BeforehandRealDetailBO;
import com.maywide.biz.salary.repbo.AdjustmentRep;
import com.maywide.biz.salary.repbo.AuditCentRep;
import com.maywide.biz.salary.repbo.CancelShareRep;
import com.maywide.biz.salary.repbo.CentSumRep;
import com.maywide.biz.salary.repbo.OperatorDetailRep;
import com.maywide.biz.salary.repbo.PreCentQryTotalRep;
import com.maywide.biz.salary.repbo.QueryAdjustmentRep;
import com.maywide.biz.salary.repbo.QueryBeforehandRep;
import com.maywide.biz.salary.repbo.QueryRealRep;
import com.maywide.biz.salary.repbo.QueryShareRep;
import com.maywide.biz.salary.repbo.RankCentRep;
import com.maywide.biz.salary.repbo.RealCentQryTotalRep;
import com.maywide.biz.salary.repbo.ShareRep;
import com.maywide.biz.salary.reqbo.AdjustmentReq;
import com.maywide.biz.salary.reqbo.AuditCentReq;
import com.maywide.biz.salary.reqbo.CancelShareReq;
import com.maywide.biz.salary.reqbo.CentSumReq;
import com.maywide.biz.salary.reqbo.OperatorDetailReq;
import com.maywide.biz.salary.reqbo.PreCentQryTotalReq;
import com.maywide.biz.salary.reqbo.QueryAdjustmentReq;
import com.maywide.biz.salary.reqbo.QueryBeforehandReq;
import com.maywide.biz.salary.reqbo.QueryRealReq;
import com.maywide.biz.salary.reqbo.QueryShareReq;
import com.maywide.biz.salary.reqbo.RankCentReq;
import com.maywide.biz.salary.reqbo.RealCentQryTotalReq;
import com.maywide.biz.salary.reqbo.ShareReq;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;

/**
 * 
 *<p> 
 * 积分说明配置
 *<p>
 *
 */
@Service
@Transactional
public class BeforehandRealService{
	@Autowired
	private GridInfoService gridInfoService;
	@Autowired
	private PersistentService persistentService;
	@Autowired
	private BillingHttpClientService billingHttpClientService;

	public PageImpl<BeforehandRealBO> findByPage(BeforehandRealBO beforehandRealBO,
						  Pageable pageable) throws Exception {
		PageImpl<BeforehandRealBO> pageResult = null;
		//设置接口请求参数
		CentSumReq req = new CentSumReq();
		req.setCycleid(beforehandRealBO.getCycleid());
		req.setOperator(beforehandRealBO.getOperid());
		req.setStatus(beforehandRealBO.getStatus());
		//查询网格编码
		if(StringUtils.isNotEmpty(beforehandRealBO.getGrid())) {
			BizGridInfo grid = gridInfoService.findOne(Long.valueOf(beforehandRealBO.getGrid()));
			req.setWhgridcode(grid.getGridcode());
		}
		req.setPagesize(String.valueOf(pageable.getPageSize()));
		req.setCurrentPage(String.valueOf(pageable.getPageNumber()+1));
		//查询汇总积分
		CentSumRep rep = queryCentSum(req);
		List<BeforehandRealBO> listBO = new ArrayList<BeforehandRealBO>();
		if(rep!=null && "0".equals(rep.getRtcode()) && rep.getCentlist()!=null){
			BeforehandRealBO bo = null;
			for (CentSumRep.Detail detail : rep.getCentlist()) {
				bo = new BeforehandRealBO();
				bo.setCity(beforehandRealBO.getCity());
				bo.setAreaid(beforehandRealBO.getAreaid());
				bo.setGrid(beforehandRealBO.getGrid());
				bo.setOperid(detail.getOperator());
				bo.setCycleid(req.getCycleid());
				bo.setRealcents(detail.getSrccents());
				bo.setSharecents(detail.getSharecents());
				bo.setAdjustcents(detail.getAdjustcents());
				bo.setStatus(detail.getStatus());
				listBO.add(bo);
			}
		}

		if (null != rep && listBO.size()>0) {
			int total = rep.getTotalnums();
			pageResult = new PageImpl<BeforehandRealBO>(listBO, pageable, total);
		}else{
			pageResult = new PageImpl<BeforehandRealBO>(new ArrayList<BeforehandRealBO>(), pageable, 0);
		}
        return pageResult;
	}

	//查询详情页
	public List<BeforehandRealDetailBO> findDetail(OperatorDetailReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(StringUtils.isEmpty(req.getOperator()) || StringUtils.isEmpty(req.getCycleid()) || StringUtils.isEmpty(req.getStatus())){
			throw new Exception("操作员,月份,状态不能为空!");
		}
		List<BeforehandRealDetailBO> result = new ArrayList<BeforehandRealDetailBO>();
		Map map = BeanUtil.beanToMap(req);
		String realRep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.REAL_CENT_QRY_DETAIL,map);
		OperatorDetailRep rep = jsonToBean(realRep,OperatorDetailRep.class);

		if(rep!=null && "0".equals(rep.getRtcode()) && rep.getCentlist()!=null){
			BeforehandRealDetailBO bo = null;
			for (OperatorDetailRep.Detail detail : rep.getCentlist()) {
				bo = new BeforehandRealDetailBO();
				BeanUtils.copyProperties(detail,bo);
				result.add(bo);
			}
		}
		return result;
	}

	/**
	 * 汇总积分查询
	 */
	public CentSumRep queryCentSum(CentSumReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		Map map = BeanUtil.beanToMap(req);
		String realRep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.REAL_CENT_QRY_TOTAL,map);
		return jsonToBean(realRep,CentSumRep.class);
	}
	/**
	 * 实积分查询
	 */
	public QueryRealRep queryReal(QueryRealReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		Map map = BeanUtil.beanToMap(req);
		String realRep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.REAL_CENT_SEARCH,map);
		return jsonToBean(realRep,QueryRealRep.class);
	}
	/**
	 * 预积分查询
	 */
	public QueryBeforehandRep queryBeforehand(QueryBeforehandReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.PRE_CENT_SEARCH,map);
		return jsonToBean(rep,QueryBeforehandRep.class);
	}

	/**
	 * 预积分分享
	 * @throws Exception
	 */
	public ShareRep beforehandShare(ShareReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.SHARE_CENT,map);
		return jsonToBean(rep,ShareRep.class);

	}
	/**
	 * 分享积分查询
	 * @throws Exception
	 */
	public QueryShareRep queryShare(QueryShareReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.SHARE_CENT_SEARCH,map);
		return jsonToBean(rep,QueryShareRep.class);
	}

	/**
	 *  取消分享积分
	 * @param beforehandSrcid  预积分id
	 * @param cycleid  月份
	 * @param operid 网格人员id
	 * @param disreason 失败原因
	 * @return
	 * @throws Exception
	 */
	public CancelShareRep cancelShare(String beforehandSrcid,String cycleid,String operid,String disreason) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(StringUtils.isEmpty(cycleid) || StringUtils.isEmpty(beforehandSrcid) ||
			StringUtils.isEmpty(operid) || StringUtils.isEmpty(disreason)){
			throw new Exception("月份，预积分id,网格人员id,取消原因不能为空!");
		}
		//分享积分查询
		QueryShareReq shareReq = new QueryShareReq();
		shareReq.setCurrentPage("1");
		shareReq.setPagesize(Integer.MAX_VALUE+"");
		shareReq.setCycleid(cycleid);
		shareReq.setSrcid(beforehandSrcid);
		shareReq.setSendoper(operid);
		QueryShareRep shareRep = queryShare(shareReq);
		//
		if(shareRep!=null && shareRep.getSharelist()!=null){
			CancelShareReq req = null;
			for (QueryShareRep.Detail detail : shareRep.getSharelist()) {
				req = new CancelShareReq();
				req.setRecid(detail.getRecid());
				req.setDisreason(disreason);
				Map map = BeanUtil.beanToMap(req);
				String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.CENCEL_SHARE_CENT,map);
				CancelShareRep cancelShareRep = jsonToBean(rep,CancelShareRep.class);
				if(cancelShareRep==null || !"0".equals(cancelShareRep.getRtcode())){
					return cancelShareRep;
				}
			}
		}
		return new CancelShareRep("0","取消成功!");
	}

	/**
	 *  积分调整接口
	 * @throws Exception
	 */
	public AdjustmentRep adjustment(AdjustmentReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(StringUtils.isEmpty(req.getObjvalue())){
			throw new Exception("结算对象id不能为空!");
		}
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.ADJUST_CENT,map);
		return jsonToBean(rep,AdjustmentRep.class);
	}

	/**
	 *  积分调整查询接口
	 * @throws Exception
	 */
	public QueryAdjustmentRep queryAdjustment(QueryAdjustmentReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(StringUtils.isEmpty(req.getObjvalue())){
			throw new Exception("结算对象id不能为空!");
		}
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.ADJUST_CENT_SEARCH,map);
		QueryAdjustmentRep queryAdjustmentRep = jsonToBean(rep,QueryAdjustmentRep.class);
		return queryAdjustmentRep;
	}

	/**
	 *  积分排名接口
	 * @throws Exception
	 */
	public RankCentRep rankCent(RankCentReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(StringUtils.isEmpty(req.getScycleid()) || StringUtils.isEmpty(req.getEcycleid()) ||
			StringUtils.isEmpty(req.getOperators()) || StringUtils.isEmpty(req.getCenttype())){
			throw new Exception("开始结算月份,截至结算月份，操作员，积分类型不能为空!");
		}
		//当为业务区排名时传areaid
		if("1".equals(req.getSorttype())){
			req.setAreaid(loginInfo.getAreaid().toString());
		}else{
			req.setAreaid("-1");
		}
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.RANK_CENT_SEARCH,map);
		return jsonToBean(rep,RankCentRep.class);
	}

	/**
	 * 搜索用户
	 * @param req  名称或账号
	 * @return
	 * @throws Exception
	 */
	public JSONArray searchOperator(String req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		JSONArray result = new JSONArray();
		String sql="select o.operid,o.name,o.loginname,d.deptid,d.name deptname from PRV_OPERATOR o,prv_operdept t,prv_department d " +
				"where o.operid = t.operid and t.depid = d.deptid and d.city=? and o.status='0' and (o.name like '%"+req+"%' or o.loginname like '%"+req+"%')";
		if(StringUtils.isEmpty(req)){
		    return result;
        }
		List<LoginInfo> infos = persistentService.find(sql,LoginInfo.class,loginInfo.getCity());
		JSONObject jb = null;
		if(infos!=null && infos.size()>0){
			for (LoginInfo info : infos) {
				jb = new JSONObject();
				jb.put("operid",info.getOperid());
				jb.put("deptcode",info.getDeptid());
				jb.put("deptname",info.getDeptname());
				jb.put("name",info.getName());
				jb.put("loginname",info.getLoginname());
				result.put(jb);
			}
		}
		return result;
	}

	/**
	 *  积分审核
	 * @throws Exception
	 */
	public AuditCentRep auditCent(AuditCentReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		req.setCycleid(req.getCycleid().replace("-",""));
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.AUDIT_CENT,map);
		return jsonToBean(rep,AuditCentRep.class);
	}
	/**
	 *  预积分场景分组
	 * @throws Exception
	 */
	public PreCentQryTotalRep beforehandGroup(PreCentQryTotalReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.SCENE_PRE_CENT_QRY_TOTAL,map);
		return jsonToBean(rep,PreCentQryTotalRep.class);
	}
	/**
	 *  实积分场景分组
	 * @throws Exception
	 */
	public RealCentQryTotalRep realGroup(RealCentQryTotalReq req) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		Map map = BeanUtil.beanToMap(req);
		String rep= billingHttpClientService.requestPost(loginInfo,SalaryConstants.API_URL.SCENE_REAL_CENT_QRY_TOTAL,map);
		return jsonToBean(rep,RealCentQryTotalRep.class);
	}



	private <T> T jsonToBean(String str,Class<T> c) throws Exception{
		try {
			return (T)BeanUtil.jsonToObject(str,c);
		}catch (Exception e){
			e.printStackTrace();
			throw new BusinessException("接口返回异常，暂无数据返回!");
		}
	}




}
