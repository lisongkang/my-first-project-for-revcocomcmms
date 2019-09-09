package com.maywide.biz.market.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.market.dao.GridDao;
import com.maywide.biz.market.entity.Grid;
import com.maywide.biz.market.entity.GridManager;
import com.maywide.biz.market.entity.GridObj;
import com.maywide.biz.market.entity.ResPatch;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class GridService extends BaseService<Grid,Long>{
	private final static Logger log = LoggerFactory.getLogger(GridService.class);
	private final static ExecutorService executor = Executors.newCachedThreadPool();
    
    @Autowired
    private GridDao gridDao;
    
    @Autowired
	private PersistentService persistentService;
    
    @Autowired
	private ParamService paramService;

    @Override
    protected BaseDao<Grid, Long> getEntityDao() {
        return gridDao;
    }
    
    @Override
    public void delete(Grid entity) {
    	gridDao.delete(entity);
    	GridTask gridTask = new GridTask(entity.getGridid());
    	executor.submit(gridTask);
    }
    
    public void transGridList(List<Grid> list) throws Exception {
    	for (Grid grid : list) {
    		//transGrid(grid);
    		grid.addExtraAttribute("manager", getOpername(grid.getId()));
    		grid.addExtraAttribute("city", paramService.getMname("PRV_CITY", grid.getCity()));
    	}
    }
    
    public void transGrid(Grid grid) throws Exception {
    	grid.addExtraAttribute("patch", getPatchName(grid.getId()));
		grid.addExtraAttribute("manager", getOpername(grid.getId()));
		grid.addExtraAttribute("city", paramService.getMname("PRV_CITY", grid.getCity()));
    }
    
    public void transGridManagerList(List<GridManager> list) throws Exception {
    	String opername = "";
    	PrvOperator oper = null;
    	for (GridManager gridManager : list) {
    		oper = (PrvOperator) persistentService.find(PrvOperator.class, gridManager.getAreamger());
    		if (oper != null) {
    			gridManager.addExtraAttribute("name", oper.getDisplay());
    			gridManager.addExtraAttribute("loginname", oper.getLoginname());
    		}
    	}
    }
    
    public void transPatchList(List<GridObj> list) throws Exception {
    	for (GridObj gridObj : list) {
    		gridObj.addExtraAttribute("areaname", paramService.getMname("PRV_CITY", gridObj.getCity()));
    		
    		ResPatch patch = (ResPatch) persistentService.find(ResPatch.class, gridObj.getObjid());
    		if (patch != null) {
    			gridObj.addExtraAttribute("patchname", patch.getPatchname());
    		}
    	}
    }
    
    public void transPatch(GridObj gridObj) throws Exception {
    	gridObj.addExtraAttribute("patchname", getPatchName(gridObj.getGridid()));
    	gridObj.addExtraAttribute("areaname", paramService.getMname("PRV_CITY", gridObj.getCity()));
    }
    
    public List<PrvArea> findAllArea(PrvArea queryParam) throws Exception {
    	return (List<PrvArea>) persistentService.find(queryParam);
    }
    
    private String getPatchName(Long gridId) throws Exception {
    	StringBuffer retval = new StringBuffer();
    	GridObj queryParam = new GridObj();
    	queryParam.setGridid(gridId);
    	queryParam.setObjtype("0"); //片区
    	
    	List<GridObj> list = persistentService.find(queryParam);
    	ResPatch patch = null;
    	for (GridObj obj : list) {
    		patch = (ResPatch) persistentService.find(ResPatch.class, obj.getObjid());
    		if (patch != null) {
    			retval.append(patch.getPatchname()).append(", ");
    		}
    	}
    	
    	String patchName = retval.toString();
    	if (patchName.length() > 0) {
    		patchName = patchName.substring(0, patchName.length() - 2);
    	}
    	
    	return patchName;
    }
    
    private String getOpername(Long gridId) throws Exception {
    	StringBuffer retval = new StringBuffer();
    	GridManager queryParam = new GridManager();
    	queryParam.setGridid(gridId);
    	
    	List<GridManager> managers = persistentService.find(queryParam);
    	PrvOperator oper = null;
    	
    	for (GridManager manager : managers) {
    		oper = (PrvOperator) persistentService.find(PrvOperator.class, manager.getAreamger());
    		if (oper != null) {
    			retval.append(oper.getLoginname()).append(", ");
    		}
    	}
    	
    	String operName = retval.toString();
    	if (operName.length() > 0) {
    		operName = operName.substring(0, operName.length() - 2);
    	}
    	
    	return operName;
    }
    
    private class GridTask implements Runnable {
    	private Long gridid;
    	
    	public GridTask(Long gridid) {
    		this.gridid = gridid;
    	}
    	
    	public void run() {
    		try {
    			persistentService.executeSql("DELETE FROM biz_gridmanager WHERE gridid = ?", gridid);
    			persistentService.executeSql("DELETE FROM GRID_RELATION WHERE gridid = ?", gridid);
    			persistentService.executeSql("DELETE FROM BIZ_GRID_OBJ WHERE gridid = ?", gridid);
			} catch (Exception e) {
				log.error("删除网格信息出错", e);
			}
    		
    	}
    }
}
