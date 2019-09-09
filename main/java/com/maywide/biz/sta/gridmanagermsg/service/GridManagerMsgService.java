package com.maywide.biz.sta.gridmanagermsg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.inter.pojo.portal.PortalUser;
import com.maywide.biz.inter.service.PortalService;
import com.maywide.biz.sta.gridmanagermsg.bo.GridManagerMsgBo;
import com.maywide.biz.sta.gridmanagermsg.bo.GridManagerMsgParamBo;
import com.maywide.biz.sta.marketingresult.bo.StaMarketingResultBO;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Transactional
@Service
public class GridManagerMsgService extends CommonService  {

	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private PortalService portalService;
	
	public  PageImpl<GridManagerMsgBo> queryGridManagerMsg(GridManagerMsgParamBo param, Pageable pageable) throws Exception{
		CheckUtils.checkNull(param, "查询条件不能为空");
		
	    if(!isAccessPortal()){
        	 throw new Exception("系统未开通接通portal信息接口");
		}
        List<Long> areaids = param.getAreaids();
        if (null == areaids || areaids.size() == 0) {
            throw new Exception("业务区不能为空");
        }
		PageImpl<GridManagerMsgBo> pageResult = null;
        Page<GridManagerMsgBo> page = new Page<GridManagerMsgBo>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        
     
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        
        sql.append("SELECT * FROM ("); // 要在最外层加select *，否则框架本身根据构造的语句查总记录数时的sql会错误
        sql.append(" SELECT p.name AS name, ");
        sql.append(" p.loginname AS loginname,");
        sql.append(" (SELECT mname FROM prv_sysparam s WHERE s.gcode='PRV_CITY' AND s.`mcode`=d.city) AS cityname, ");
        sql.append(" a.name AS areaname,");
        sql.append(" d.name AS departname");
        sql.append(" FROM prv_operator p ,prv_operrole o,prv_department d,prv_area a ");
        sql.append(" WHERE 1=1 AND");
        sql.append(" p.operid=o.operid ");
        sql.append(" AND o.deptid=d.deptid AND d.areaid = a.areaid ");
        
        sql.append(" AND a.areaid IN(");
        for (int i = 0, size = areaids.size(); i < size; i++) {
            sql.append("?");
            paramList.add(areaids.get(i));
            if (i < size - 1) {
                sql.append(",");
            }
        }
        sql.append(")");
        
        List<Long> departs = param.getDeparts();
        if (null != departs && departs.size() > 0) {
            sql.append(" AND d.deptid IN(");
            for (int i = 0, size = departs.size(); i < size; i++) {
                sql.append("?");
                paramList.add(departs.get(i));
                if (i < size - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }
        
        
        List<Long> operators = param.getOperators();
        if (null != operators && operators.size() > 0) {
            sql.append(" AND p.operid IN(");
            for (int i = 0, size = operators.size(); i < size; i++) {
                sql.append("?");
                paramList.add(operators.get(i));
                if (i < size - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }

        sql.append(" ORDER BY p.operid DESC) v");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), GridManagerMsgBo.class, paramList.toArray());

        List<GridManagerMsgBo> resultList = page.getResult();
        
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            addExtraInfo(resultList);//添加portal账号
            pageResult = new PageImpl<GridManagerMsgBo>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<GridManagerMsgBo>(new ArrayList<GridManagerMsgBo>(), pageable, 0);
        }
        return pageResult;

	}

	private void addExtraInfo(List<GridManagerMsgBo> resultList) throws Exception {
		 for (GridManagerMsgBo bo : resultList) {
			PortalUser portalUser =  portalService.queryPortalUserByloginname(bo.getLoginname());
			bo.setPortalnum(portalUser.getOperid());
		 }
	}
	
	/**
	 * 是否允许接通portal
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public boolean isAccessPortal() throws Exception{
		Rule rule = new Rule ();
		rule.setRule("ACCESS_PORTAL");
		persistentService.clear();
		List<Rule> list = persistentService.find(rule);
		if(list.size()>0){
			rule =list.get(0);
		}
		if(rule!=null&&rule.getPerMission().equals("Y")&&rule.getValue().equals("Y")){
			return true;
		}
		else {
			return false;
		}
	}
	
}
