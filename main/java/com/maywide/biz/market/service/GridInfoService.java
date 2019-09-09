package com.maywide.biz.market.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.maywide.biz.ass.store.entity.AssIndexStore;
import com.maywide.biz.ass.togrid.entity.AssIndexTogrid;
import com.maywide.biz.ass.topatch.entity.AssIndexPhasenum;
import com.maywide.biz.ass.topatch.entity.AssIndexTopatch;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.market.dao.GridInfoDao;
import com.maywide.biz.market.entity.ResPatch;
import com.maywide.biz.market.pojo.BizGridHouse;
import com.maywide.biz.market.pojo.GridTreeInfo;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateTimeUtil;
import com.maywide.core.util.SimpleSqlCreator;

@Service
@Transactional
public class GridInfoService extends BaseService<BizGridInfo, Long> {
    private final static Logger          log      = LoggerFactory.getLogger(GridInfoService.class);
    private final static ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private GridInfoDao                  gridInfoDao;

    @Autowired
    private PersistentService            persistentService;
    
    @Autowired
	private ParamService paramService;

    @Override
    protected BaseDao<BizGridInfo, Long> getEntityDao() {
        return gridInfoDao;
    }

    /**
     * 查询第一个pidName=-1且最高排序优先级的网格
     * 
     * @param pidName 用于识别父网格的字段名
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public BizGridInfo getFirstRootGrid(String pidName) throws Exception {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(-1);
        StringBuffer sql = SimpleSqlCreator.createSelectAllFieldSql(entityClass);
        sql.append(" WHERE o.").append(pidName).append("=?");

        LoginInfo logininfo = AuthContextHolder.getLoginInfo();
        String city = logininfo.getCity();
        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(logininfo.getLoginname())) {
            sql.append(" AND o.city=?");
            paramList.add(city);
        }
        sql.append(" ORDER BY o.prio DESC,o.gridname");
        List<BizGridInfo> gridList = persistentService.find(sql.toString(), entityClass, paramList.toArray());
        if (null != gridList && gridList.size() > 0) {
            return gridList.get(0);
        } else {
            throw new ServiceException("找不到" + pidName + "=-1的根网格！");
        }
    }

    @Override
    public void delete(BizGridInfo entity) {
        gridInfoDao.delete(entity);
        GridTask gridTask = new GridTask(entity.getId());
        executor.submit(gridTask);
    }

    private class GridTask implements Runnable {
        private Long gridid;

        public GridTask(Long gridid) {
            this.gridid = gridid;
        }

        public void run() {
            try {
                persistentService.executeSql("DELETE FROM " + SimpleSqlCreator.getEntityTableName(BizGridManager.class)
                        + " WHERE gridid = ?", gridid);
            } catch (Exception e) {
                log.error("删除网格关联信息出错", e);
            }
        }
    }

    public void transGridList(List<BizGridInfo> list, String pidName) throws Exception {
        for (BizGridInfo grid : list) {
            grid.addExtraAttribute("manager", getOpername(grid.getId(), null, null));
            grid.setGridPath(findGridPath(grid, "previd".equals(pidName) ? 0 : 1));
        }
    }

    /**
     * 根据gridid查负责人
     * 
     * @param gridId
     * @param type 0-不过滤负责人，1-只查operid对应的负责人，2-查operid之外的负责人
     * @param operid
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private String getOpername(Long gridId, Integer type, Long operid) throws Exception {
        if (null == type) {
            type = 0;
        }
        String sql = SimpleSqlCreator.createSelectAllFieldSql(BizGridManager.class)
                .append(" WHERE o.gridid=? ORDER BY o.isMain DESC").toString();
        List<BizGridManager> managers = persistentService.find(sql, BizGridManager.class, new Object[] { gridId });

        StringBuffer retval = new StringBuffer();
        PrvOperator oper = null;
        for (BizGridManager manager : managers) {
            Long operidInDB = manager.getOperid();
            if (type == 1 && !operidInDB.equals(operid)) {
                // 只查operid
                continue;
            } else if (type == 2 && operidInDB.equals(operid)) {
                // 不包含operid
                continue;
            }

            if (null != operidInDB) {
                oper = (PrvOperator) persistentService.find(PrvOperator.class, operidInDB);
            } else {
                oper = new PrvOperator();
                oper.setName("异常空操作员");
            }
            if (oper != null) {
                retval.append(oper.getName());
                String isMain = manager.getIsMain();
                if (null != isMain && "Y".equals(isMain)) {
                    retval.append("(主)");
                }
                retval.append(", ");
            }
        }

        String operName = retval.toString();
        if (operName.length() > 0) {
            operName = operName.substring(0, operName.length() - 2); // 逗号后面有空格，所以-2
        }

        return operName;
    }

    @SuppressWarnings("unchecked")
    public List<GridTreeInfo> findGridTree(int treeType) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT NEW com.maywide.biz.market.pojo.GridTreeInfo(");
        sql.append("o.id,o.gridcode,o.gridname,o.gtype,o.previd,o.statid,o.city)");
        sql.append(" FROM BizGridInfo o WHERE 1=1");
        sql.append(" AND o.").append(treeType == 1 ? "previd" : "statid").append(" IS NOT NULL");

        List<Object> paramList = new ArrayList<Object>();
        LoginInfo logininfo = AuthContextHolder.getLoginInfo();
        String city = logininfo.getCity();
        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(logininfo.getLoginname())) {
            // 超级管理员查询全部地市的网格
            sql.append(" AND o.city = ?");
            paramList.add(city);
        }

        sql.append(" ORDER BY o.gtype,o.prio DESC,o.gridname");

        List<GridTreeInfo> gridTreeList = persistentService.find(sql.toString(), paramList.toArray());

        if (null == gridTreeList || gridTreeList.size() == 0) {
            return new ArrayList<GridTreeInfo>();
        } else {
            return gridTreeList;
        }
    }

    public String getPreGridName(Long previd) throws Exception {
        if (null != previd) {
            BizGridInfo preGridInfo = findOne(previd);
            if (null != preGridInfo) {
                return preGridInfo.getGridname();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public String checkCanDelete(BizGridInfo entity) {
        if (entity.isNew()) {
            return "未保存数据";
        } else {
            try {
                Long girdId = entity.getId();
                int gtype = entity.getGtype().intValue();
                String msg = "网格【" + entity.getGridname() + "】";

                if (gtype == BizConstant.GridType.MANAGE_GRID || gtype == BizConstant.GridType.ADDRESS_GRID) {
                    // 管理网格需要判断是否有子网格或考核任务
                    String sql = SimpleSqlCreator.createSelectAllFieldSql(entityClass)
                            .append(" WHERE o.previd=? OR o.statid=?").toString();
                    List<BizGridInfo> childGridList = (List<BizGridInfo>) persistentService.find(sql, entityClass,
                            new Object[] { girdId, girdId });
                    if (null != childGridList && childGridList.size() > 0) {
                        if (childGridList.get(0).getPrevid().equals(girdId)) {
                            return msg + "有子网格，请先删除或转移其子网格";
                        } else {
                            return msg + "有子数据网格，请先删除或转移其子数据网格";
                        }
                    }

                    AssIndexTogrid queryAssParam = new AssIndexTogrid();
                    queryAssParam.setGridid(girdId);
                    List<AssIndexTogrid> gridAssList = (List<AssIndexTogrid>) persistentService.find(queryAssParam);
                    if (null != gridAssList && gridAssList.size() > 0) {
                        return msg + "有考核任务，请先取消考核任务";
                    }

                    if (gtype == BizConstant.GridType.ADDRESS_GRID) {
                        // 地址网格要另外判断是否有关联地址
                        String sql2 = new StringBuffer("SELECT * FROM biz_grid_house_").append(entity.getCity())
                                .append(" WHERE gridid=?").toString();
                        List<BizGridHouse> gridHoustList = persistentService.find(sql2, BizGridHouse.class,
                                new Object[] { girdId });
                        if (null != gridHoustList && gridHoustList.size() > 0) {
                            return msg + "有关联地址，请先删除关联地址";
                        }
                    }
                } else if (gtype == BizConstant.GridType.PATCH_GRID) {
                    // 判断片区网格是否有考核任务
                    AssIndexTopatch queryAssParam = new AssIndexTopatch();
                    queryAssParam.setPgridid(girdId); // toPatch表的pgridid即可确定是哪个小区网格的任务，patchid会有重复
                    List<AssIndexTopatch> patchAssList = (List<AssIndexTopatch>) persistentService.find(queryAssParam);
                    if (null != patchAssList && patchAssList.size() > 0) {
                        return msg + "有考核任务，请先取消考核任务";
                    }
                }
                return null;
            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<BizGridInfo> findBindedPatchByPreGridid(Long gridid) throws Exception {
        Class<BizGridInfo> gridClass = BizGridInfo.class;

        StringBuffer tempSql = new StringBuffer();
        tempSql.append("SELECT DISTINCT g.city");
        tempSql.append(" FROM ").append(SimpleSqlCreator.getEntityTableName(gridClass)).append(" g");
        tempSql.append(" WHERE g.previd=? LIMIT 1");

        StringBuffer sql = new StringBuffer("SELECT * FROM(");
        sql.append(SimpleSqlCreator.createSelectAllFieldSql(gridClass));
        sql.append(" WHERE o.gtype=? AND o.city=(");
        sql.append(tempSql);
        sql.append(")) n");

        List<BizGridInfo> bindedPatchList = persistentService.find(sql.toString(), gridClass,
                new Object[] { Long.valueOf(BizConstant.GridType.PATCH_GRID), gridid });
        if (null == bindedPatchList || bindedPatchList.size() == 0) {
            bindedPatchList = new ArrayList<BizGridInfo>();
        }
        return bindedPatchList;
    }

    public List<BizGridInfo> bindPatch(String[] patchIds, Long gridid) throws Exception {
        BizGridInfo preGridInfo = findOne(gridid);
        String city = preGridInfo.getCity();
        List<BizGridInfo> patchGridList = new ArrayList<BizGridInfo>();
        for (String patchid : patchIds) {
            ResPatch patch = (ResPatch) persistentService.find(ResPatch.class, Long.parseLong(patchid));
            Long patchId = patch.getId();
            String patchName = patch.getPatchname();

            BizGridInfo existPatch = findByProperty("patchid", patchId);
            if (existPatch != null) {
                // 因为BizGridInfo表建了patchid的唯一索引，所以要做限制
                throw new BusinessException("小区【" + patchName + "】已被其它网格关联！");
            }

            BizGridInfo patchGrid = new BizGridInfo();
            patchGrid.setGridcode(patchId.toString()); // 小区网格的id默认为小区的id，可修改
            patchGrid.setGridname(patchName);
            patchGrid.setGtype(1L); // 小区网格
            patchGrid.setPrio(100L);
            patchGrid.setPrevid(gridid);
            patchGrid.setStatid(gridid); // 暂时statid与previd一样，后续修改
            patchGrid.setPatchid(patchId);
            patchGrid.setCity(city); // 使用父网格的city
            getEntityDao().save(patchGrid);
            patchGridList.add(patchGrid);
        }
        return patchGridList;
    }

    @SuppressWarnings("unchecked")
    public List<BizGridManager> findAllManagerByGridid(Long gridid) throws Exception {
        BizGridManager quereParam = new BizGridManager();
        quereParam.setGridid(gridid);
        return (List<BizGridManager>) persistentService.find(quereParam);
    }

    public void bindManager(String[] operIds, long gridid) throws Exception {
        Date updateTime = new Date();
        for (String operid : operIds) {
            BizGridManager manager = new BizGridManager();
            manager.setGridid(gridid);
            manager.setOperid(Long.parseLong(operid));
            manager.setIsMain("N"); // 默认不是主网格经理
            manager.setUpdatetime(updateTime);
            persistentService.save(manager);
        }
    }

    /**
     * 根据网格id查询其考核任务
     * 
     * @param gridInfo
     * @param isManageGrid
     * @param pageable
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PageImpl queryAssByGridid(Long gridid, boolean isManageGrid, Pageable pageable) throws Exception {
        Class assToClass = isManageGrid ? AssIndexTogrid.class : AssIndexTopatch.class;

        PageImpl pageResult = null;
        Page page = new Page();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer tempSql = new StringBuffer(SimpleSqlCreator.createSelectAllFieldSql(assToClass));
        tempSql.append(",").append(SimpleSqlCreator.getEntityTableName(AssIndexStore.class)).append(" ais")
                .append(" WHERE o.assid=ais.assid").append(" AND ais.expdate>=STR_TO_DATE(?,'%Y-%m-%d')");
        tempSql.append(" AND o.").append(isManageGrid ? "gridid" : "pgridid").append("=?");
        tempSql.append(" ORDER BY ais.expdate DESC");
        tempSql.replace(0, 7, "SELECT ais.asscontent,ais.unit AS assStoreUnit,"); // 替换SELECT

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM(");
        sql.append(tempSql);
        sql.append(") n");

        String expdate = DateTimeUtil.formatDate(new Date());

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), assToClass, new Object[] { expdate, gridid });

        if (page != null && page.getResult() != null) {
            int total = page.getTotalCount();
            pageResult = new PageImpl(page.getResult(), pageable, total);
        } else {
            pageResult = new PageImpl(new ArrayList(), pageable, 0);
        }
        return pageResult;
    }

    @SuppressWarnings("rawtypes")
    public void transAssList(List assList, boolean isManageGrid) throws Exception {
        String modeEachSet = "1";
        for (Object ass : assList) {
            if (isManageGrid) {
                AssIndexTogrid assToGrid = (AssIndexTogrid) ass;
                if (modeEachSet.equals(assToGrid.getMode())) {
                    assToGrid.addExtraAttribute("assNums", getAssNum(assToGrid.getId(), isManageGrid));
                } else {
                    assToGrid.addExtraAttribute("assNums", assToGrid.getAssnum());
                }
            } else {
                AssIndexTopatch assToPatch = (AssIndexTopatch) ass;
                if (modeEachSet.equals(assToPatch.getMode())) {
                    assToPatch.addExtraAttribute("assNums", getAssNum(assToPatch.getId(), isManageGrid));
                } else {
                    assToPatch.addExtraAttribute("assNums", assToPatch.getAssnum());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private String getAssNum(Long taskId, boolean isManageGrid) throws Exception {
        String sql = SimpleSqlCreator.createSelectAllFieldSql(AssIndexPhasenum.class)
                .append(" WHERE o.taskid=? AND o.ttype=? ORDER BY o.pno").toString();
        List<AssIndexPhasenum> aipList = persistentService.find(sql, AssIndexPhasenum.class, new Object[] { taskId,
                isManageGrid ? "0" : "1" });

        StringBuffer retval = new StringBuffer();
        for (AssIndexPhasenum aip : aipList) {
            retval.append(aip.getAssnum()).append(", ");
        }

        String assNums = retval.toString();
        if (assNums.length() > 0) {
            assNums = assNums.substring(0, assNums.length() - 2); // 逗号后面有空格，所以-2
        }
        return assNums;
    }

    public void updatePreDataGrid(Long newStatid, Collection<BizGridInfo> toUpdateGrids) throws Exception {
        for (BizGridInfo bizGrid : toUpdateGrids) {
            bizGrid.setStatid(newStatid);
            save(bizGrid);
        }
    }

	public void updatePreBaseGrid(Long newPreid,Collection<BizGridInfo> toUpdateGrids) {
		for (BizGridInfo bizGrid : toUpdateGrids) {
            bizGrid.setPrevid(newPreid);
            save(bizGrid);
        }
	}
    /**
     * 查找当前片区对应的网格路径
     * 
     * @param patchid
     * @param gridType 0-基础网格，1-数据网格
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String findPatchPath(Long patchid, int gridType) throws Exception {
        BizGridInfo patchGridInfo = new BizGridInfo();
        patchGridInfo.setPatchid(patchid);
        List<BizGridInfo> list = persistentService.find(patchGridInfo);
        if (null != list && list.size() > 0) {
            return findGridPath(list.get(0), gridType);
        } else {
            return null;
        }
    }

    /**
     * 查找当前网格对应的网格路径
     * 
     * @param gridInfo
     * @param gridType 0-基础网格，1-数据网格
     * @return
     * @throws Exception
     */
    public String findGridPath(BizGridInfo gridInfo, int gridType) throws Exception {
        String gridPath = (String) persistentService.findUniqueObject("SELECT PrevGridNamePath(?,?)", new Object[] {
                gridInfo.getId(), gridType });
        if (StringUtils.isBlank(gridPath)) {
            gridPath = null;
        }
        return gridPath;
    }

    @SuppressWarnings("unchecked")
    public PageImpl<BizGridInfo> queryGridsByOperid(String operid, Pageable pageable, String orderField, String sortType)
            throws Exception {
        CheckUtils.checkNull(operid, "操作员id不能为空");
        Long operidLong = Long.parseLong(operid);

        PageImpl<BizGridInfo> pageResult = null;
        Page<BizGridInfo> page = new Page<BizGridInfo>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();

        StringBuffer tempSql = SimpleSqlCreator.createSelectAllFieldSql(entityClass);
        tempSql.insert(tempSql.indexOf(" FROM"), ",(SELECT PrevGridNamePath(o.gridid,0)) AS gridPath");

        sql.append("SELECT * FROM(");
        sql.append(tempSql).append(",");
        sql.append(SimpleSqlCreator.getEntityTableName(BizGridManager.class)).append(" bgm");
        sql.append(" WHERE o.gridid=bgm.gridid AND bgm.operid=?");
        paramList.add(operidLong);

        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField);
            sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
        } else {
            sql.append(" ORDER BY o.gtype,o.prio DESC,o.gridname) n");
        }

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<BizGridInfo> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            for (BizGridInfo grid : resultList) {
                grid.addExtraAttribute("manager", getOpername(grid.getId(), 2, operidLong));
            }
            pageResult = new PageImpl<BizGridInfo>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<BizGridInfo>(new ArrayList<BizGridInfo>(), pageable, 0);
        }
        return pageResult;
    }

    /**
     * 根据网格id查询关联地址
     * 
     * @param gridInfo
     * @param pageable
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public PageImpl<BizGridHouse> queryAddressByGridid(Long gridid, Pageable pageable) throws Exception {
        BizGridInfo gridInfo = findOne(gridid);
        String city = gridInfo.getCity();

        PageImpl<BizGridHouse> pageResult = null;
        Page<BizGridHouse> page = new Page<BizGridHouse>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        if (BizConstant.GridType.ADDRESS_GRID != gridInfo.getGtype().intValue()) {
            // 非地址网格的直接返回空记录
            return new PageImpl<BizGridHouse>(new ArrayList<BizGridHouse>(), pageable, 0);
        }

        StringBuffer sql = getQueryAddressSql(city);
        sql.append(" WHERE o.whgridcode=?) n");

        persistentService.clear();
        page = persistentService
                .find(page, sql.toString(), BizGridHouse.class, new Object[] { gridInfo.getGridcode() });

        if (page != null && page.getResult() != null) {
            int total = page.getTotalCount();
            addExtraAddressName(page.getResult(), city);
            pageResult = new PageImpl<BizGridHouse>(page.getResult(), pageable, total);
        } else {
            pageResult = new PageImpl<BizGridHouse>(new ArrayList<BizGridHouse>(), pageable, 0);
        }
        return pageResult;
    }

    @SuppressWarnings("unchecked")
    public PageImpl<BizGridHouse> findUnbindAddressByGridId(String gridId, Pageable pageable, String orderField,
            String sortType) throws Exception {
        BizGridInfo gridInfo = findOne(Long.parseLong(gridId));
        String city = gridInfo.getCity();

        StringBuffer sql = getQueryAddressSql(city);
        sql.append(" WHERE o.whgridcode IS NULL");

        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField);
            sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
        } else {
            sql.append(" ORDER BY o.houseid) n");
        }

        PageImpl<BizGridHouse> pageResult = null;
        Page<BizGridHouse> page = new Page<BizGridHouse>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        page = persistentService.find(page, sql.toString(), BizGridHouse.class);

        if (page != null && page.getResult() != null) {
            int total = page.getTotalCount();
            addExtraAddressName(page.getResult(), city);
            pageResult = new PageImpl<BizGridHouse>(page.getResult(), pageable, total);
        } else {
            pageResult = new PageImpl<BizGridHouse>(new ArrayList<BizGridHouse>(), pageable, 0);
        }
        return pageResult;
    }

    /**
     * 地址表太大，不在sql中关联查询patchName和areaName
     * 
     * @param addressList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void addExtraAddressName(List<BizGridHouse> addressList, String city) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(",biz_grid_house_").append(city).append(" h");

        StringBuffer sql1 = new StringBuffer();
        sql1.append(SimpleSqlCreator.createSelectAllFieldSql(ResPatch.class));
        sql1.append(sql);
        sql1.append(" WHERE o.patchid=h.patchid AND h.houseid=?");

        StringBuffer sql2 = new StringBuffer();
        sql2.append(SimpleSqlCreator.createSelectAllFieldSql(PrvArea.class));
        sql2.append(sql);
        sql2.append(" WHERE o.areaid=h.areaid AND h.houseid=?");

        for (BizGridHouse bizGridHouse : addressList) {
            Long houseid = bizGridHouse.getHouseid();
            List<ResPatch> patchList = persistentService
                    .find(sql1.toString(), ResPatch.class, new Object[] { houseid });
            if (null != patchList && patchList.size() > 0) {
                bizGridHouse.setPatchName(patchList.get(0).getPatchname());
            }

            List<PrvArea> areaList = persistentService.find(sql2.toString(), PrvArea.class, new Object[] { houseid });
            if (null != areaList && areaList.size() > 0) {
                bizGridHouse.setAreaName(areaList.get(0).getName());
            }
        }
    }

    private StringBuffer getQueryAddressSql(String city) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM(");
        sql.append("SELECT o.houseid,o.addrid,o.status,o.netstruct,o.whladdr,");
        sql.append("o.whgridcode,o.ywgridcode,o.gridid,o.patchid,o.areaid");
        sql.append(" FROM biz_grid_house_").append(city).append(" o");
        return sql;
    }

    public void bindAddress(String[] addressIds, long gridid) throws Exception {
        BizGridInfo gridInfo = findOne(gridid);
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE biz_grid_house_").append(gridInfo.getCity());
        sql.append(" SET whgridcode=?,ywgridcode=?,gridid=? WHERE houseid=?");
        for (String addressId : addressIds) {
            persistentService.executeSql(sql.toString(), new Object[] { gridInfo.getGridcode(), gridInfo.getCity(),
                    gridid, Long.parseLong(addressId) });
        }
    }

    public Map<Long, String> doDeleteAddress(String[] addressIds, long gridid) {
        Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
        BizGridInfo gridInfo = findOne(gridid);

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE biz_grid_house_").append(gridInfo.getCity());
        sql.append(" SET whgridcode=NULL,ywgridcode=NULL,gridid=? WHERE houseid=?"); // 删除关联地址实际为把gridid设为-1
        for (String addressId : addressIds) {
            Long addressIdLong = Long.parseLong(addressId);
            try {
                persistentService.executeSql(sql.toString(), new Object[] { -1, addressIdLong });
            } catch (Exception e) {
                errorMessageMap.put(addressIdLong, e.getMessage());
            }
        }
        return errorMessageMap;
    }
    
    
    public Map<String, String> getGridTypeMap() {
		 Map<String, String> gridTypeMap = new LinkedHashMap<String, String>();
		 try {
	           List<PrvSysparam> datas =  paramService.getData(BizConstant.SysparamGcode.GRID_TYPE);
	           for(PrvSysparam data :datas){
	        	   gridTypeMap.put(data.getMcode(), data.getMname());
	           }
        } catch (Exception e) {
           e.printStackTrace();
       }
		 return gridTypeMap;
	}


}