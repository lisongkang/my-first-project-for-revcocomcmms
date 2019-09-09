package com.maywide.biz.sta.gridmanagermsg.bo;

import java.util.List;

/**
 *<p> 
 * 网格经理列表查询条件
 *<p>
 * Create at 2016-11-1
 *
 *@autor zhuangzhitang
 */
public class GridManagerMsgParamBo {

	private List<Long> areaids;
    private List<Long> departs;
    private List<Long> operators;
    
	public List<Long> getAreaids() {
		return areaids;
	}
	
	public void setAreaids(List<Long> areaids) {
		this.areaids = areaids;
	}
	
	public List<Long> getDeparts() {
		return departs;
	}
	
	public void setDeparts(List<Long> departs) {
		this.departs = departs;
	}
	
	public List<Long> getOperators() {
		return operators;
	}
	
	public void setOperators(List<Long> operators) {
		this.operators = operators;
	}
    
    
}
