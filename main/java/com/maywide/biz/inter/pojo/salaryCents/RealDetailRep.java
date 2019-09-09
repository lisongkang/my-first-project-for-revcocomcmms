package com.maywide.biz.inter.pojo.salaryCents;

import com.maywide.biz.core.pojo.TokenReturnInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealDetailRep extends TokenReturnInfo {

	//前端能够展示的字段
	private static List<Map> showNames = new ArrayList<Map>();
	static {
		Map<String,String> maps = new HashMap<String,String>();

		maps.put("custname","客户名称");
		maps.put("groupcode","积分类型");
		maps.put("optime","办理时间");
		maps.put("cents","积分");
		maps.put("whgridname","网格名称");
		maps.put("pcodename","产品名称");
		maps.put("prepcodename","原产品名称");
		maps.put("srccents","个人积分");
		maps.put("adjustcents","剔除积分");
		maps.put("sharecents","分享积分");
		maps.put("opername","操作员");


		showNames.add(maps);
	}


	public List<Map> getShowNames() {
		return showNames;
	}

	public void setShowNames(List<Map> showNames) {
		this.showNames = showNames;
	}
}
