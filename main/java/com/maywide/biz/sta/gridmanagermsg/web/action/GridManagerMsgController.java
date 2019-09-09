package com.maywide.biz.sta.gridmanagermsg.web.action;

import java.util.ArrayList;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.sta.gridmanagermsg.bo.GridManagerMsgBo;
import com.maywide.biz.sta.gridmanagermsg.bo.GridManagerMsgParamBo;
import com.maywide.biz.sta.gridmanagermsg.service.GridManagerMsgService;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.web.SimpleController;

public class GridManagerMsgController extends SimpleController {

	private  GridManagerMsgParamBo gridManagerMsgParamBo;
	
	@Autowired
	private GridManagerMsgService gridManagerMsgService;
	
	@MetaData("网格经理信息查询")
	public HttpHeaders queryGridManagerMsgList(){
		 Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		 try{
			 setModel(gridManagerMsgService.queryGridManagerMsg(gridManagerMsgParamBo, pageable));
		 }catch(Exception e){
			 setModel(new PageImpl<GridManagerMsgBo>(new ArrayList<GridManagerMsgBo>(), pageable, 0));
			// throw new WebException(e.getMessage(), e);
		 }
		 return buildDefaultHttpHeaders();
	}

	public GridManagerMsgParamBo getGridManagerMsgParamBo() {
		return gridManagerMsgParamBo;
	}

	public void setGridManagerMsgParamBo(GridManagerMsgParamBo gridManagerMsgParamBo) {
		this.gridManagerMsgParamBo = gridManagerMsgParamBo;
	}
	
	

}
