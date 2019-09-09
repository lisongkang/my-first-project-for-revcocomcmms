package com.maywide.biz.ass.topatch.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.store.entity.AssIndexStore;
import com.maywide.biz.ass.topatch.dao.AssIndexTopatchDao;
import com.maywide.biz.ass.topatch.entity.AssIndexPhasenum;
import com.maywide.biz.ass.topatch.entity.AssIndexTopatch;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.TmpIndexPhasenum;
import com.maywide.biz.ass.topatch.entity.TmpIndexTask;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.entity.ResPatch;
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
public class AssIndexTopatchService extends BaseService<AssIndexTopatch,Long>{
    
    @Autowired
    private AssIndexTopatchDao assIndexTopatchDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private DynamicConfigService dynamicConfigService;
    
    @Override
    protected BaseDao<AssIndexTopatch, Long> getEntityDao() {
        return assIndexTopatchDao;
    }
    
    public List getGrids() throws Exception {
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
		
		// 超级管理员可以获取跨地市网格
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
    
    /**
     * 网格树的查询指定网格下所有的片区
     * @param gridid 是网格树查询用，查previd字段
     * @return
     * @throws Exception
     */
    public List<BizGridInfo> getPatchsByGridid(Long gridid) throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	Long operId = loginInfo.getOperid();
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        // SELECT * FROM biz_grid_info WHERE FIND_IN_SET(previd, FatherList(4592)) AND gtype = '1';
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
        sql.append("(select p.patchname from res_patch p where p.patchid=t.patchid) patchname, ");
        sql.append("t.memo ");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
        sql.append(" AND gtype = '1' ");
		if (gridid != null){
			sql.append(" AND FIND_IN_SET(t.previd, FatherList(?)) ");
			paramList.add(gridid);
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
    
    /**
     * 查询统计网格下的片区
     * @param pgridid  是统计查询用，查statid字段
     * @return
     * @throws Exception
     */
    public List<BizGridInfo> getPatchsByPgridid(Long pgridid) throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	Long operId = loginInfo.getOperid();
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        // SELECT * FROM biz_grid_info WHERE FIND_IN_SET(previd, FatherList(4592)) AND gtype = '1';
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
        sql.append("(select p.patchname from res_patch p where p.patchid=t.patchid) patchname, ");
        sql.append("t.memo ");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
        sql.append(" AND gtype = '1' ");
		if (pgridid != null){
			sql.append(" AND t.statid=? ");
			paramList.add(pgridid);
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
    
    
    private List<String> getPatchidList(List<String> patchids) {
    	List<String> retPatchidList = new ArrayList<String>();
    	for (String pid : patchids) {
    		if (pid != null && !"".equals(pid)){
    			// pid格式 ：　patchid~gridid
        		String[] pair = pid.split("~");
        		retPatchidList.add(pair[0]);
    		}
		}
    	
    	return retPatchidList;
    }
    
    public void saveAssIndexTopatchs (AssIndexTopatch topatch) throws Exception{
    	try {
    		AssIndexTopatch saveTopatch = null;
        	
        	List<String> patchids = topatch.getPatchids();
        	
        	List<String> pids = getPatchidList(patchids);
        	
        	
        	// 检查是否重复添加片区
        	saveTopatch = new AssIndexTopatch();
        	saveTopatch.setAssid(topatch.getAssid());
        	List<AssIndexTopatch> queryAssIndexTopatch = persistentService.find(saveTopatch);
        	for (AssIndexTopatch assIndexTopatch : queryAssIndexTopatch) {
    			
        		String patchidString = String.valueOf(assIndexTopatch.getPatchid());
        		if (pids.contains(patchidString)){
        			throw new Exception( "重复添加片区！");
        		}
    		}
        	
        	for (String patchid : patchids) {
        		saveTopatch = new AssIndexTopatch();
            	BeanUtils.copyProperties(topatch, saveTopatch);
            	
            	// patchid格式 ：　patchid~gridid
            	String[] pair = patchid.split("~");
            	saveTopatch.setPatchid(Long.valueOf(pair[0]));
            	saveTopatch.setPgridid(Long.parseLong(pair[1]));
                if (pair.length == 3) {
                    // 批量下发时用到，此时patchid格式 ：　patchid~gridid~revnum
                    saveTopatch.setRevnum(Double.parseDouble(pair[2]));
                }
            	
            	persistentService.save(saveTopatch);
            	
            	Long taskid = saveTopatch.getId();
            	saveAssIndexAssnum(taskid, topatch);
    		}
        	
        	// 删除临时记录
        	deleteTmpIndexAssnum(topatch);
    		
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    }
    
    private void deleteTmpIndexAssnum(AssIndexTopatch topatch) throws Exception{
    	// 只有分期指定才需要删除临时记录。 MODE : 0-每期相同；1-分别指定;9-无固定
    	if (!"1".equals(topatch.getMode())){
    		return;
    	}
    	
    	TmpIndexPhasenum tmpPhasenum = new TmpIndexPhasenum();
    	tmpPhasenum.setSerialno(topatch.getSerialno());
    	List<TmpIndexPhasenum> list = (List<TmpIndexPhasenum>)persistentService.find(tmpPhasenum);
    	
    	// 删除临时记录
		for (TmpIndexPhasenum tmpIndexPhasenum : list) {
			persistentService.delete(tmpIndexPhasenum);
		}
    }
    
    private void saveAssIndexAssnum(Long taskid, AssIndexTopatch topatch) throws Exception{
    	// 0-每期相同；1-分别指定;9-无固定
    	if (!"1".equals(topatch.getMode())){
    		return;
    	}
    	
    	TmpIndexPhasenum tmpPhasenum = new TmpIndexPhasenum();
    	tmpPhasenum.setSerialno(topatch.getSerialno());
    	
    	List<TmpIndexPhasenum> list = (List<TmpIndexPhasenum>)persistentService.find(tmpPhasenum);
    	if (list != null && list.size() > 0){
    		for (TmpIndexPhasenum tmpIndexPhasenum : list) {
    			AssIndexPhasenum assPhasenum = new AssIndexPhasenum();
    			assPhasenum.setTaskid(taskid);
    			assPhasenum.setTtype("1"); // 任务类型，0-下达网格 1-下达片区	
    			assPhasenum.setPno(tmpIndexPhasenum.getPno());
    			assPhasenum.setAssnum(tmpIndexPhasenum.getAssnum());
    			
    			persistentService.save(assPhasenum);
			}
    	}
    }
    
    public List<AssIndexPhasenum> quePhasenumList(AssIndexStore store, Long taskid) throws Exception {
    	AssIndexPhasenum phase = new AssIndexPhasenum();
    	phase.setTaskid(taskid);
    	phase.setTtype("1");// 任务类型，0-下达网格 1-下达片区
    	
    	List<AssIndexPhasenum> list = (List<AssIndexPhasenum>)persistentService.find(phase);
    	
    	if (list != null && list.size() > 0) {
    		for (AssIndexPhasenum assIndexPhasenum : list) {
    			//0-每月，1-每季度
				if ("0".equals(store.getUnit())) {
					assIndexPhasenum.setPnoname(assIndexPhasenum.getPno() + "月");
				} else if ("1".equals(store.getUnit())) {
					assIndexPhasenum.setPnoname(assIndexPhasenum.getPno() + "季度");
				}
			}
    		return list;
    	} else {
    		List<AssIndexPhasenum> emptylist = new ArrayList<AssIndexPhasenum>();
    		return emptylist;
    	}
    } 
    
    public List<TmpIndexPhasenum> queTmpPhasenumList(AssIndexStore store,String serialno) throws Exception {
    	TmpIndexPhasenum phase = new TmpIndexPhasenum();
    	phase.setSerialno(serialno);
    	List<TmpIndexPhasenum> list = (List<TmpIndexPhasenum>)persistentService.find(phase);
    	
    	if (list != null && list.size() > 0) {
    		for (TmpIndexPhasenum assIndexPhasenum : list) {
    			//0-每月，1-每季度
				if ("0".equals(store.getUnit())) { 
					assIndexPhasenum.setPnoname(assIndexPhasenum.getPno() + "月");
				} else if ("1".equals(store.getUnit())) {
					assIndexPhasenum.setPnoname(assIndexPhasenum.getPno() + "季度");
				}
			}
    		return list;
    	} else {
    		List<TmpIndexPhasenum> emptylist = new ArrayList<TmpIndexPhasenum>();
    		return emptylist;
    	}
    }
    
    public PageImpl queTopatchTaskList(Long assid,  Pageable pageable) throws Exception {
    	
    	PageImpl pageResult = null;
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		
		StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.taskid as id, ");
        sql.append(" t.assid, ");
        sql.append(" t.patchid, ");
        sql.append(" t.pgridid, ");
        sql.append(" t.tgridid, ");
        sql.append(" t.bdate, ");
        sql.append(" t.revnum, ");
        sql.append(" t.mode, ");
        sql.append(" code2name(t.mode, 'BIZ_ASS_MODE') modename, ");
        sql.append(" t.assnum, ");
        sql.append(" t.assdate, ");
        sql.append(" t.depart, ");
        sql.append(" t.operator, ");
        sql.append(" (SELECT gridname FROM biz_grid_info k WHERE k.gridid=t.tgridid) gridname, ");
        sql.append(" (SELECT patchname FROM res_patch k WHERE k.patchid=t.patchid) patchname ");
        sql.append(" FROM ass_index_topatch t ");
        sql.append(" WHERE 1 = 1 ");
        
		if (assid != null){
			sql.append(" AND t.assid = ? ");
			paramList.add(assid);
		} 
		 
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssIndexTopatch.class, paramList.toArray());
		
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssIndexTopatch> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<AssIndexTopatch> resultList = new ArrayList<AssIndexTopatch>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    public String saveTmpAssnum(String serialno, String assnumString) throws Exception {
    	// 已存在序列号，则需要重新添加（先删后增加）
    	if (StringUtils.isNotEmpty(serialno.trim())) {
    		TmpIndexPhasenum phase = new TmpIndexPhasenum();
    		phase.setSerialno(serialno.trim());
    		
    		List<TmpIndexPhasenum> plist = persistentService.find(phase);
    		for (TmpIndexPhasenum tmpIndexPhasenum : plist) {
    			persistentService.delete(tmpIndexPhasenum);
			}
    	}
    	
    	String seqNo = StringUtils.isNotEmpty(serialno.trim())? serialno.trim() : getSeqNo();
    	
    	String[] assnumPair = assnumString.split("#");
    	for (String pair : assnumPair) {
    		String[] assnumArray = pair.split("~");
    		String pno = assnumArray[0];
    		String assnum = assnumArray[1];
    		
    		TmpIndexPhasenum phase = new TmpIndexPhasenum();
    		phase.setSerialno(seqNo);
    		phase.setPno(Long.parseLong(pno));
    		phase.setAssnum(Double.parseDouble(assnum));
    		
    		persistentService.save(phase);
		}
    	
    	return seqNo;
    }
    
    private String getSeqNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		String seqNo = sdf.format(new Date());
		return seqNo;
	}
    
    public void deletePatch(String[] ids) throws Exception {
    	
    	for (int i = 0; i < ids.length; i++) {
    		AssIndexTopatch assIndexTopatch = (AssIndexTopatch)persistentService.find(AssIndexTopatch.class, Long.parseLong(ids[i]));
    		String mode = assIndexTopatch.getMode();
    		// 只有分期指定才需要删除记录。 MODE : 0-每期相同；1-分别指定;9-无固定
        	if (!"1".equals(mode)){
        		return;
        	}
        	
        	AssIndexPhasenum assPhasenum = new AssIndexPhasenum();
        	assPhasenum.setTaskid(assIndexTopatch.getId());
        	assPhasenum.setTtype("1");// 任务类型，0-下达网格 1-下达片区
        	
        	List<AssIndexPhasenum> list = (List<AssIndexPhasenum>)persistentService.find(assPhasenum);
        	if (list != null && list.size() > 0){
        		for (AssIndexPhasenum assIndexPhasenum : list) {
        			persistentService.delete(assIndexPhasenum);
        		}
        	}
		}
    	
    }
    
    public List<String> parseToPatchBatTxt(Long tgridid, Long assid, String attachmentId, Long departId, Long operatorId)
            throws Exception {
        List<String> patchIds = new ArrayList<String>();
        BufferedReader br = null;
        try {
            AttachmentFile attachmentFileObj = (AttachmentFile) persistentService.find(AttachmentFile.class,
                    attachmentId);
            String rootPath = dynamicConfigService.getFileUploadRootDir();
            File attachmentFile = new File(rootPath + attachmentFileObj.getFileRelativePath() + File.separator
                    + attachmentFileObj.getDiskFileName());
            br = new BufferedReader(new InputStreamReader(new FileInputStream(attachmentFile)));
            String line = br.readLine();

            boolean isGetPatchInfo = false;
            Map<Long, Long> patchIdMap = new HashMap<Long, Long>();
            Set<Long> patchIdSet = new HashSet<Long>();
            List<String> patchCodeList = new ArrayList<String>();

            List<TmpIndexTask> titList = new ArrayList<TmpIndexTask>();
            Date opdate = new Date();
            String serialNo = DateTimeUtil.formatDate(opdate, "yyyyMMddHHmmss");

            int loopCount = 1;
            while (null != line) {
                if (!isGetPatchInfo) {
                    patchIdMap = getPatchIdMapByGridid(tgridid);
                    if (patchIdMap.size() == 0) {
                        throw new ServiceException("所选网格没有下属片区");
                    }
                    patchIdSet = patchIdMap.keySet();
                    isGetPatchInfo = true;
                }

                Object[] checkInfos = checkTxtValid(line, patchIdSet, patchCodeList, assid, loopCount);
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

                Long patchId = (Long) checkInfos[1];
                Double revnum = (Double) checkInfos[2];
                patchIds.add(patchId + "~" + patchIdMap.get(patchId) + "~" + revnum);
                line = br.readLine();
                loopCount++;
            }

            if (!isGetPatchInfo) {
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

            return patchIds;
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
    private Map<Long, Long> getPatchIdMapByGridid(Long gridid) throws Exception {
        List<BizGridInfo> gridList = (List<BizGridInfo>) getPatchsByGridid(gridid);
        Map<Long, Long> patchIdMap = new HashMap<Long, Long>(gridList.size());
        for (BizGridInfo gridInfo : gridList) {
            patchIdMap.put(gridInfo.getPatchid(), gridInfo.getId());
        }
        return patchIdMap;
    }

    @SuppressWarnings("unchecked")
    private Object[] checkTxtValid(String line, Set<Long> patchIdSet, List<String> patchCodeList, Long assid,
            int loopCount) throws Exception {
        StringBuffer memo = new StringBuffer("第" + loopCount + "行:");

        if ("".equals(line)) {
            return new Object[] { new TmpIndexTask(null, null, false, memo.append("为空行")) };
        }

        String[] patchAndRevnum = line.split(" ");
        if (patchAndRevnum.length < 2) {
            return new Object[] { new TmpIndexTask(null, null, false, memo.append("没有填写片区任务数跳过")) };
        }

        String patchCode = patchAndRevnum[0];
        if ("".equals(patchCode)) {
            return new Object[] { new TmpIndexTask(null, null, false, memo.append("没有填写片区号")) };
        }
        if (patchCodeList.contains(patchCode)) {
            // 片区记录重复，以第一条为准，之后的跳过
            return new Object[] { new TmpIndexTask(patchCode, null, false, memo.append("已有相同片区号记录，以最早出现的为准")) };
        } else {
            patchCodeList.add(patchCode);
        }

        String revnumStr = patchAndRevnum[1];
        Double revnum = -1D;
        try {
            revnum = Double.parseDouble(revnumStr);
        } catch (NumberFormatException nfe) {
            return new Object[] { new TmpIndexTask(patchCode, null, false, memo.append("任务数为非数字")) };
        }
        if (revnum < 0) {
            return new Object[] { new TmpIndexTask(patchCode, revnum, false, memo.append("任务数小于0")) };
        }

        ResPatch tempPatchPojo = new ResPatch();
        tempPatchPojo.setDefcode(patchCode);
        List<ResPatch> patchList = (List<ResPatch>) persistentService.find(tempPatchPojo);
        if (null == patchList || patchList.size() == 0) {
            // 根据片区号找不到片区对象跳过
            return new Object[] { new TmpIndexTask(patchCode, revnum, false, memo.append("片区号").append(patchCode)
                    .append("没有对应片区")) };
        }
        ResPatch patchInDB = patchList.get(0);
        Long patchId = patchInDB.getId();
        if (!patchIdSet.contains(patchId)) {
            return new Object[] { new TmpIndexTask(patchCode, revnum, false, memo.append("片区号").append(patchCode)
                    .append("对应片区不属于所选网格")) };
        }

        AssIndexTopatch tempAssIndexTopatch = new AssIndexTopatch();
        tempAssIndexTopatch.setAssid(assid);
        tempAssIndexTopatch.setPatchid(patchId);
        List<AssIndexTopatch> aitList = (List<AssIndexTopatch>) persistentService.find(tempAssIndexTopatch);
        if (null != aitList && aitList.size() > 0) {
            return new Object[] { new TmpIndexTask(patchCode, revnum, false, memo.append("所选考核内容已下发到片区号")
                    .append(patchCode).append("对应片区")) };
        }

        return new Object[] { new TmpIndexTask(patchCode, revnum, true, memo.append("成功")), patchId, revnum }; // 都验证通过则返回patchId后续使用
    }
}
