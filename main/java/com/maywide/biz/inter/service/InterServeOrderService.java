package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.DEP_KIND;
import com.maywide.biz.cons.BizConstant.DEP_LEVEL;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.entity.BizBossorderImage;
import com.maywide.biz.inter.pojo.bizservorder.BaseServeOrderReq;
import com.maywide.biz.inter.pojo.querydepartment.DepartmentInterInfo;
import com.maywide.biz.market.entity.CustOrder;

@SuppressWarnings("unchecked")
@Service
public class InterServeOrderService extends CommonService {

	@Autowired
	PubService pubService;

	public String getDeptsStrForLevel(LoginInfo loginInfo) throws Exception {
		List<DepartmentInterInfo> depts = getDeptsForLevel(loginInfo);
		if (depts == null || depts.isEmpty()) {
			return String.valueOf(loginInfo.getDeptid());
		}
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (DepartmentInterInfo dept : depts) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(",");
			}
			sb.append(dept.getId());
		}
		return sb.toString();
	}

	public List<DepartmentInterInfo> getDeptsForLevel(LoginInfo loginInfo) throws Exception {
		List<DepartmentInterInfo> depts = new ArrayList<DepartmentInterInfo>();
		if (DEP_LEVEL.LEVEL_2.equals(loginInfo.getDeplevel())) { // 片区经理
			depts = getDeptsForLevel2(loginInfo);
		} else if (DEP_LEVEL.LEVEL_3.equals(loginInfo.getDeplevel())) { // 大网格经理
			depts = getDeptsForLevel3(loginInfo);
		} else if (DEP_LEVEL.LEVEL_4.equals(loginInfo.getDeplevel()) && DEP_KIND.SUPPORT.equals(loginInfo.getDeptkind())) {
			depts = getDeptsForSupport(loginInfo);
		} else {
			DepartmentInterInfo dept = new DepartmentInterInfo();
			dept.setId(String.valueOf(loginInfo.getDeptid()));
			dept.setName(loginInfo.getDeptname());
			depts.add(dept);
		}
		return depts;
	}

	public List<DepartmentInterInfo> getDeptsForLevel2(LoginInfo loginInfo) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT deptid AS id, name FROM ( ");
		sql.append("SELECT * FROM prv_department WHERE preid =? AND areaid =? AND kind IN('3','C') AND deplevel =? ");
		params.add(loginInfo.getDeptid());
		params.add(loginInfo.getAreaid());
		params.add(DEP_LEVEL.LEVEL_3);
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM prv_department a WHERE a.preid  IN ( ");
		sql.append("SELECT deptid FROM prv_department n WHERE n.preid =? ) ");
		sql.append("AND a.areaid=? AND a.kind IN('3','C') AND a.deplevel=?  ");
		sql.append(")a;");
		params.add(loginInfo.getDeptid());
		params.add(loginInfo.getAreaid());
		params.add(DEP_LEVEL.LEVEL_4);

		List<DepartmentInterInfo> depts = getDAO().find(sql.toString(), DepartmentInterInfo.class, params.toArray());
		return depts;
	}

	public List<DepartmentInterInfo> getDeptsForLevel3(LoginInfo loginInfo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT deptid AS id, name FROM ( ");
		sql.append("SELECT * FROM prv_department WHERE ");
		sql.append("preid =? AND areaid =? AND kind IN('3','C') AND deplevel =? ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM prv_department a WHERE deptid =? AND kind IN('3','C') ");
		sql.append(")a;");
		Object[] params = { loginInfo.getDeptid(), loginInfo.getAreaid(), DEP_LEVEL.LEVEL_4, loginInfo.getDeptid() };
		List<DepartmentInterInfo> depts = getDAO().find(sql.toString(), DepartmentInterInfo.class, params);
		return depts;
	}

	public List<DepartmentInterInfo> getDeptsForSupport(LoginInfo loginInfo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT deptid AS id, name FROM ( ");
		sql.append("SELECT * FROM prv_department WHERE ");
		sql.append("preid = (SELECT preid FROM prv_department WHERE deptid =? ) AND areaid =? AND kind IN('3','C') AND deplevel =? ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM prv_department a WHERE deptid = (SELECT preid FROM prv_department WHERE deptid =? ) AND kind IN('3','C') ");
		sql.append(")a;");
		Object[] params = { loginInfo.getDeptid(), loginInfo.getAreaid(), DEP_LEVEL.LEVEL_4, loginInfo.getDeptid() };
		List<DepartmentInterInfo> depts = getDAO().find(sql.toString(), DepartmentInterInfo.class, params);
		return depts;
	}

	public List<DepartmentInterInfo> getDeptsForGridApply(LoginInfo loginInfo) throws Exception {
		// 大网格经理才有工单委托
		if (!DEP_LEVEL.LEVEL_3.equals(loginInfo.getDeplevel())) {
			return new ArrayList<DepartmentInterInfo>();
		}
		List<DepartmentInterInfo> depts = getDeptsForLevel3(loginInfo);
		return depts;
	}

	public List<DepartmentInterInfo> getDeptsForChangeDept(LoginInfo loginInfo) throws Exception {
		List<DepartmentInterInfo> depts = new ArrayList<DepartmentInterInfo>();
		if (DEP_LEVEL.LEVEL_3.equals(loginInfo.getDeplevel())) { // 大网格经理才能转单

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT deptid AS id, name  FROM PRV_DEPARTMENT  WHERE PREID = ? ");
			sql.append("AND  areaid =? AND kind IN('3','C') AND deplevel =? ;");
			Object[] params = { loginInfo.getDeptid(), loginInfo.getAreaid(), DEP_LEVEL.LEVEL_4 };
			depts = getDAO().find(sql.toString(), DepartmentInterInfo.class, params);
		}
		return depts;
	}

	public CustOrder saveCustOrder(BaseServeOrderReq req, LoginInfo loginInfo, String opcode) throws Exception {
		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setBossserialno(req.getCustorderid());
		bizCustOrder.setCanceltime(null);
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setDescrip(null);
		bizCustOrder.setLockoper(null);
		bizCustOrder.setName(req.getName());
		bizCustOrder.setOpcode(opcode);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);
		bizCustOrder.setVerifyphone(null);
		getDAO().save(bizCustOrder);
		return bizCustOrder;
	}

	public List<BizBossorderImage> queExemptImgs(String servids) throws Exception {
		String sql = "SELECT servorderid id,bossorderid,imagecatalog1,imagecatalog2,imagecatalog3 FROM biz_bossorder_image WHERE servorderid IN (?);";
		List<BizBossorderImage> imgs = getDAO().find(sql, BizBossorderImage.class, servids);
		return imgs;
	}

}
