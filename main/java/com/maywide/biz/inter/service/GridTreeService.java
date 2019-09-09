package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.quegridtree.GridTreeNode;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
@SuppressWarnings("unchecked")
public class GridTreeService extends CommonService {

	public ReturnInfo queGridTree(ArrayList<GridTreeNode> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();

		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		String sql = "SELECT gridid,gridcode,gridname,gtype,previd FROM biz_grid_info WHERE city= ? AND gtype IN (0,2) AND previd IS NOT NULL ";
		List<GridTreeNode> gridList = getDAO().find(sql, GridTreeNode.class, loginInfo.getCity());

		sql += " AND gridid IN (SELECT gridid FROM biz_grid_manager WHERE operid = ? ) ";
		List<GridTreeNode> operGrids = getDAO().find(sql, GridTreeNode.class, loginInfo.getCity(), loginInfo.getOperid());

		List<GridTreeNode> nodeList = new ArrayList<GridTreeNode>();
		for (GridTreeNode gridInfo : operGrids) {
			GridTreeNode node = buildChain(gridList, gridInfo);
			if (!isExistNode(node, nodeList)) {
				nodeList.add(node);
			}
		}
		if (nodeList != null) {
			for (GridTreeNode node : nodeList) {
				setLevel4Node(node, 1);
			}
			resp.addAll(nodeList);
		}
		return returnInfo;
	}

	private GridTreeNode buildChain(List<GridTreeNode> gridList, GridTreeNode gridInfo) {
		buildPrevChain(gridList, gridInfo);
		GridTreeNode rootNode = gridInfo;
		while (rootNode.getPrev() != null) {
			rootNode = rootNode.getPrev();
		}
		if (BizConstant.BizGridObjObjtype.PATCH.equals(gridInfo.getgType())) {
			buildNextChain(gridList, gridInfo);
		}
		return rootNode;
	}

	private void setLevel4Node(GridTreeNode node, int level) {
		node.setLevel(level);
		List<GridTreeNode> childNodes = node.getChildNodes();
		if (childNodes != null && !childNodes.isEmpty()) {
			int nextLevel = level + 1;
			for (GridTreeNode childNode : childNodes) {
				setLevel4Node(childNode, nextLevel);
			}
		}
	}

	private void buildNextChain(List<GridTreeNode> gridList, GridTreeNode node) {
		List<GridTreeNode> nextNodes = findNextNodes(gridList, node);
		if (nextNodes != null && !nextNodes.isEmpty()) {
			List<GridTreeNode> childNodes = node.getChildNodes();
			if (childNodes == null) {
				childNodes = new ArrayList<GridTreeNode>();
				node.setChildNodes(childNodes);
			}
			for (GridTreeNode nextNode : nextNodes) {
				if (!isExistNode(nextNode, childNodes)) {
					childNodes.add(nextNode);
				}
				buildNextChain(gridList, nextNode);
			}
		}
	}

	private List<GridTreeNode> findNextNodes(List<GridTreeNode> gridList, GridTreeNode grid) {
		if (BizConstant.BizGridObjObjtype.ADDR.equals(grid.getgType())) {
			return null;
		}
		List<GridTreeNode> list = new ArrayList<GridTreeNode>();
		for (GridTreeNode gridInfo : gridList) {
			if (grid.getGridId().equals(gridInfo.getPrevId())) {
				list.add(gridInfo);
			}
		}
		return list;
	}

	private void buildPrevChain(List<GridTreeNode> gridList, GridTreeNode grid) {
		if (grid.getPrev() != null || grid.getPrevId().equals(-1L)) {
			return;
		}
		GridTreeNode prevGrid = findPrevGrid(gridList, grid);
		if (prevGrid != null) {
			grid.setPrev(prevGrid);
			List<GridTreeNode> childNodes = prevGrid.getChildNodes();
			if (childNodes == null) {
				childNodes = new ArrayList<GridTreeNode>();
				prevGrid.setChildNodes(childNodes);
			}
			if (!isExistNode(grid, childNodes)) {
				childNodes.add(grid);
			}
			buildPrevChain(gridList, prevGrid);
		}
	}

	private boolean isExistNode(GridTreeNode node, List<GridTreeNode> nodes) {
		for (GridTreeNode gridTreeNode : nodes) {
			if (gridTreeNode.getGridId().equals(node.getGridId())) {
				return true;
			}
		}
		return false;
	}

	private GridTreeNode findPrevGrid(List<GridTreeNode> gridList, GridTreeNode grid) {
		if (grid.getPrevId() == null || grid.getPrevId().equals(-1L)) {
			return null;
		}
		for (GridTreeNode gridInfo : gridList) {
			if (gridInfo.getGridId().equals(grid.getPrevId())) {
				return gridInfo;
			}
		}
		return null;
	}

}
