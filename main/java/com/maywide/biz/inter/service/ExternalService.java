package com.maywide.biz.inter.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.inter.pojo.gridinfo.GridMgInfo;
import com.maywide.core.service.PersistentService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExternalService {

	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private RuleService ruleService;
	
	public String queryGridManager(String gcode,String city,String num) throws Exception{
		String sql = getGridSql(city, num);
		List<GridMgInfo> datas = persistentService.find(sql, GridMgInfo.class, gcode);
		String dataStr = new Gson().toJson(datas);
		return dataStr;
	}
	
	private String getGridSql(String city,String num) throws Exception{
		Rule rule = ruleService.getRule("SPE_GRID_CITY");
		String sql = "";
		if(rule != null && StringUtils.isNotBlank(rule.getValue()) && rule.getValue().contains(city)){
			sql = partSpecicalGrid(city,num);
		}else{
			sql = partNormalGrid(city, num);
		}
		return sql;
	}
	/**
	 * TV厅用来显示网格经理的信息接口
	 * @param city
	 * @param num
	 * @return
	 */
	private String partSpecicalGrid(String city,String num){
		String tableName = "biz_grid_manager_"+city.toLowerCase();
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT a.gridcode,a.gridname,b.operid,b.callno,b.phone,d.name,d.loginname,c.head_img headImg");
		sql.append("		FROM  "+tableName +"	a");
		sql.append("		INNER JOIN prv_operinfo b");
		sql.append("		ON 1 = 1 ");
		sql.append("		AND a.operid = b.operid");
		sql.append("		AND a.gridcode = ?");
		sql.append("		LEFT JOIN prv_operator d ON a.operid = d.operid");
		sql.append("		AND b.operid = a.operid");
		sql.append("		LEFT JOIN prv_oper_photo c ON a.operid = c.operid");
		sql.append("		AND b.operid = c.operid");
		sql.append("		ORDER BY b.operid desc");
		sql.append("		LIMIT "+num);
		
		return sql.toString();
	}
	
	private String partNormalGrid(String city,String num){
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT a.gridcode,a.gridname,b.operid,f.loginname,f.name,d.head_img headImg,e.callno,e.phone");
		sql.append("		FROM biz_grid_info a INNER JOIN biz_grid_manager b");
		sql.append("		ON 1 = 1 ");
		sql.append("		AND a.gridid = b.gridid");
		sql.append("		AND a.city = '"+city+"'");
		sql.append("		AND a.gridcode = ?");
		sql.append("		INNER JOIN prv_operator f");
		sql.append("		ON f.operid = b.operid");
		sql.append("		LEFT JOIN prv_oper_photo d ON d.operid = b.operid");
		sql.append("		LEFT JOIN prv_operinfo e ON e.operid = b.operid");
		sql.append("		ORDER BY b.operid desc");
		sql.append("		LIMIT "+num);
		return sql.toString();
	}
}
