package com.maywide.biz.ass.togrid.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.togrid.dao.AssIndexTogridDao;
import com.maywide.biz.ass.togrid.entity.AssIndexTogrid;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.TmpIndexTask;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.common.ctx.DynamicConfigService;
import com.maywide.common.pubpost.entity.AttachmentFile;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateTimeUtil;

@Service
@Transactional
public class AssIndexTogridService extends BaseService<AssIndexTogrid,Long>{
    
    @Autowired
    private AssIndexTogridDao assIndexTogridDao;
    
    @Autowired
    private PersistentService persistentService;

    @Autowired
    private DynamicConfigService dynamicConfigService;
    
    @Override
    protected BaseDao<AssIndexTogrid, Long> getEntityDao() {
        return assIndexTogridDao;
    }
    
    
    public void saveAssIndexTogrids (AssIndexTogrid togrid) throws Exception{
    	try {
    		AssIndexTogrid saveTogrid = null;
        	
        	List<String> gridids = togrid.getGridids();
        	
        	saveTogrid = new AssIndexTogrid();
        	saveTogrid.setAssid(togrid.getAssid());
        	@SuppressWarnings("unchecked")
			List<AssIndexTogrid> queryAssIndexTogrid = persistentService.find(saveTogrid);
        	for (AssIndexTogrid assIndexTogrid : queryAssIndexTogrid) {
    			
        		if (gridids.contains(String.valueOf(assIndexTogrid.getGridid()))){
        			throw new Exception( "重复添加网格！");
        		}
    		}
        	
        	for (String gridid : gridids) {
        		
        		
        		saveTogrid = new AssIndexTogrid();
            	BeanUtils.copyProperties(togrid, saveTogrid);
            	
            	String[] pair = gridid.split("~");
            	
            	 if (pair.length == 2) {
                     // 批量下发时用到，此时网格格式 ：　gridid~revnum
            		 saveTogrid.setGridid(Long.parseLong(pair[0]));
            		 saveTogrid.setAssnum(Double.parseDouble(pair[1]));
                 }else{
                	 saveTogrid.setGridid(Long.parseLong(gridid));
                 }
            	persistentService.save(saveTogrid);
    		}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageImpl queTogridTaskList(Long assid,  Pageable pageable) throws Exception {
    	
    	
		PageImpl pageResult = null;
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		
		StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.taskid as id, ");
        sql.append(" t.assid, ");
        sql.append(" t.gridid, ");
        sql.append(" t.bdate, ");
        sql.append(" (SELECT gridcode FROM biz_grid_info k WHERE k.gridid=t.gridid) gridcode, ");
     //   sql.append(" t.cyclenum, ");
        sql.append(" t.assnum, ");
        sql.append(" t.assdate, ");
        sql.append(" t.depart, ");
        sql.append(" (SELECT gridname FROM biz_grid_info k WHERE k.gridid=t.gridid) gridname ");
        sql.append(" FROM ass_index_togrid t ");
        sql.append(" WHERE 1 = 1 ");
        
		if (assid != null){
			sql.append(" AND t.assid = ? ");
			paramList.add(assid);
		} 
		 
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssIndexTogrid.class, paramList.toArray());
		
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssIndexTogrid> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<AssIndexTogrid> resultList = new ArrayList<AssIndexTogrid>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getGrids() throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	Long operId = loginInfo.getOperid();
    	
    	StringBuffer sql = new StringBuffer();
     
		List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.gridid as id, ");
        sql.append("t.gridcode, ");
        sql.append("t.gridname, ");
        sql.append("t.gtype, ");
        sql.append("t.mnrid, ");
        sql.append("t.prio, ");
        sql.append("t.previd, ");
        sql.append("t.statid, ");
        sql.append("t.patchid, ");
        sql.append("t.memo ");
        sql.append(" FROM biz_grid_info t, biz_grid_manager m ");
        sql.append(" WHERE t.gridid=m.gridid  ");
        
		if (operId != null){
			sql.append(" AND m.operid = ? ");
			paramList.add(operId);
		} 
		
		// 超级管理员可以查看所有管理考核指标
		if (!"GZCYKFA0001".equals(loginInfo.getLoginname())) {
			sql.append(" AND t.city = ? ");
			paramList.add(loginInfo.getCity());
		}
		sql.append(" ) v ");
		
		persistentService.clear();
		List<BizGridInfo> gridlist = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
		if (gridlist != null && gridlist.size() > 0) {
			return gridlist;
    	} else {
    		List emptylist = new ArrayList();
    		return emptylist;
    	}
    	
	}

	public List<String> parseToGridBatTxt(Long tgridid, Long assid,
			String attachmentId, Long departId, Long operatorId) throws Exception {
		List<String> gridIds = new ArrayList<String>();
        BufferedReader br = null;
        try {
            AttachmentFile attachmentFileObj = (AttachmentFile) persistentService.find(AttachmentFile.class,
                    attachmentId);
            String rootPath = dynamicConfigService.getFileUploadRootDir();
            File attachmentFile = new File(rootPath + attachmentFileObj.getFileRelativePath() + File.separator
                    + attachmentFileObj.getDiskFileName());
            br = new BufferedReader(new InputStreamReader(new FileInputStream(attachmentFile)));
            String line = br.readLine();

            boolean isGetGridInfo = false;
            Map<Long,String> gridIdMap = new HashMap<Long,String>();
            Set<Long> gridIdSet = new HashSet<Long>();
            List<String> gridCodeList = new ArrayList<String>();

            List<TmpIndexTask> titList = new ArrayList<TmpIndexTask>();
            Date opdate = new Date();
            String serialNo = DateTimeUtil.formatDate(opdate, "yyyyMMddHHmmss");

            int loopCount = 1;
            while (null != line) {
                if (!isGetGridInfo) {
                    gridIdMap = getGridIdMapByGridid(tgridid);
                    if (gridIdMap.size() == 0) {
                        throw new ServiceException("所选网格没有下属网格");
                    }
                    gridIdSet = gridIdMap.keySet();
                    isGetGridInfo = true;
                }

                Object[] checkInfos = checkTxtValid(line, gridIdSet, gridCodeList, assid, loopCount);
                if (null != checkInfos[0]) {
                    TmpIndexTask tit = (TmpIndexTask) checkInfos[0];
                    tit.setSerialno(serialNo);
                    tit.setDepart(departId);
                    tit.setOperator(operatorId);
                    tit.setOpdate(opdate);
                    titList.add(tit);
                    if ("N".equals(tit.getIspass())) {
                        line = br.readLine();
                        loopCount++;
                        continue;
                    }
                }

                Long gridId = (Long) checkInfos[1];
                Double revnum = (Double) checkInfos[2];
                gridIds.add(gridId + "~" + revnum);
                line = br.readLine();
                loopCount++;
            }

            if (!isGetGridInfo) {
                // 循环没有开始，即文件为空，保存错误信息
                TmpIndexTask tit = new TmpIndexTask();
                tit.setSerialno(serialNo);
                tit.setDepart(departId);
                tit.setOperator(operatorId);
                tit.setOpdate(opdate);
                titList.add(tit);
            }

            // 保存文件解析信息
            for (TmpIndexTask tmpIndexTask : titList) {
                persistentService.save(tmpIndexTask);
            }

            return gridIds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex.getMessage());
        } finally {
            if (null != br) {
                br.close();
            }
        }
	}


	@SuppressWarnings("unchecked")
	private Object[] checkTxtValid(String line, Set<Long> gridIdSet,
			List<String> gridCodeList, Long assid, int loopCount) throws Exception {
		    StringBuffer memo = new StringBuffer("第" + loopCount + "行:");

	        if ("".equals(line)) {
	            return new Object[] { new TmpIndexTask(null, null, false, memo.append("为空行")) };
	        }

	        String[] gridAndRevnum = line.split(" ");
	        if (gridAndRevnum.length < 2) {
	            return new Object[] { new TmpIndexTask(null, null, false, memo.append("没有填写网格任务数跳过")) };
	        }

	        String gridCode = gridAndRevnum[0];
	        if ("".equals(gridCode)) {
	            return new Object[] { new TmpIndexTask(null, null, false, memo.append("没有填写网格编号")) };
	        }
	        if (gridCodeList.contains(gridCode)) {
	            // 网格记录重复，以第一条为准，之后的跳过
	            return new Object[] { new TmpIndexTask(gridCode, null, false, memo.append("已有相同片区号记录，以最早出现的为准")) };
	        } else {
	        	gridCodeList.add(gridCode);
	        }

	        String revnumStr = gridAndRevnum[1];
	        Double revnum = -1D;
	        try {
	            revnum = Double.parseDouble(revnumStr);
	        } catch (NumberFormatException nfe) {
	            return new Object[] { new TmpIndexTask(gridCode, null, false, memo.append("任务数为非数字")) };
	        }
	        if (revnum < 0) {
	            return new Object[] { new TmpIndexTask(gridCode, revnum, false, memo.append("任务数小于0")) };
	        }

	        BizGridInfo tempGridPojo = new BizGridInfo();
	        tempGridPojo.setGridcode(gridCode);
	        List<BizGridInfo> gridList = (List<BizGridInfo>) persistentService.find(tempGridPojo);
	        if (null == gridList || gridList.size() == 0) {
	            // 根据网格号找不到网格对象跳过
	            return new Object[] { new TmpIndexTask(gridCode, revnum, false, memo.append("网格编号号").append(gridCode)
	                    .append("没有对应网格")) };
	        }
	        BizGridInfo gridInDB = gridList.get(0);
	        Long gridId = gridInDB.getId();
	        if (!gridIdSet.contains(gridId)) {
	            return new Object[] { new TmpIndexTask(gridCode, revnum, false, memo.append("网格编号").append(gridCode)
	                    .append("对应网格不属于所选网格")) };
	        }

	        AssIndexTogrid tempAssIndexTogrid = new AssIndexTogrid();
	        tempAssIndexTogrid.setAssid(assid);
	        tempAssIndexTogrid.setGridid(gridId);
	        List<AssIndexTogrid> aitList = (List<AssIndexTogrid>) persistentService.find(tempAssIndexTogrid);
	        if (null != aitList && aitList.size() > 0) {
	            return new Object[] { new TmpIndexTask(gridCode, revnum, false, memo.append("所选考核内容已下发到网格编号")
	                    .append(gridCode).append("对应网格")) };
	        }

	        return new Object[] { new TmpIndexTask(gridCode, revnum, true, memo.append("成功")), gridId, revnum }; // 都验证通过则返回gridId后续使用
	}


	private Map<Long, String> getGridIdMapByGridid(Long gridid) throws Exception {
		 List<BizGridInfo> gridList = (List<BizGridInfo>) getGridsByGridid(gridid);
         Map<Long,String > gridIdMap = new HashMap<Long,String>(gridList.size());
         for (BizGridInfo gridInfo : gridList) {
        	 gridIdMap.put(gridInfo.getId(), gridInfo.getGridcode());
         }
         return gridIdMap;
	}





	  /**
     * 网格树的查询指定网格下所有的网格
     * @param gridid 是网格树查询用，查previd字段
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public List<BizGridInfo> getGridsByGridid(Long gridid) throws Exception {
    	
    	StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.gridid as id, ");
        sql.append("t.gridcode, ");
        sql.append("t.gridname, ");
        sql.append("t.gtype, ");
        sql.append("t.mnrid, ");
        sql.append("t.prio, ");
        sql.append("t.previd, ");
        sql.append("t.statid, ");
        sql.append("t.patchid, ");
        sql.append("t.memo ");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
        sql.append(" AND gtype in ('0','2') ");
		if (gridid != null){
			//FIND_IN_SET效率太 慢 换成 in
			String fatherList = callSqlFunToFatherList(gridid);
			String[] subGrids = fatherList.split(",");
			sql.append("AND t.previd IN(");
			//从 index=1 开始遍历  index=0 是默认的$ ,不了解 可看数据库FatherList函数 
			for(int i=1; i < subGrids.length ; i++){
				sql.append(Long.parseLong(subGrids[i]));
				if(i!=(subGrids.length-1)){
					 sql.append(",");
				}
			}
			sql.append(") ");
			//sql.append(" AND FIND_IN_SET(t.previd, FatherList(?)) ");
			//paramList.add(gridid);
		} 
		
		sql.append(" ) v ");
		
		persistentService.clear();
		List<BizGridInfo> gridlist = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
		if (gridlist != null && gridlist.size() > 0) {
			return gridlist;
    	} else {
    		List<BizGridInfo> emptylist = new ArrayList<BizGridInfo>();
    		return emptylist;
    	}
    	
    }

    private String callSqlFunToFatherList(Long gridid) throws Exception {
		String sql = "select FatherList(?)  AS gridcode";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(gridid);
		List<BizGridInfo> list = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
		return list.get(0).getGridcode();
	}


	/**
     * 获取操作员关联网格下的所有网格
     * @return
     * @throws Exception 
     */
	public List<BizGridInfo> findGridsAndSubGrids() throws Exception {
		List<BizGridInfo> grids = new ArrayList<BizGridInfo>();
		List<BizGridInfo> superGrids = (List<BizGridInfo>) getGrids();
		if(superGrids.size()>0){
			for(BizGridInfo grid :superGrids ){
				List<BizGridInfo> subGrids = getGridsByGridid(grid.getId());
				if(subGrids.size() > 0){
					grids.addAll(subGrids);
				}
			}
		}
		return grids;
	}
    
	
}
