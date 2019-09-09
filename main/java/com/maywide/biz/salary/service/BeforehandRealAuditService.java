package com.maywide.biz.salary.service;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.dao.BeforehandRealAuditDao;
import com.maywide.biz.salary.entity.BeforehandRealAudit;
import com.maywide.biz.salary.pojo.BeforehandRealBO;
import com.maywide.biz.salary.repbo.AuditCentRep;
import com.maywide.biz.salary.repbo.CentSumRep;
import com.maywide.biz.salary.reqbo.AuditCentReq;
import com.maywide.biz.salary.reqbo.CentSumReq;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlrpc.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BeforehandRealAuditService extends BaseService<BeforehandRealAudit,Long> {
    @Autowired
    private BeforehandRealAuditDao beforehandRealAuditDao;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private GridInfoService gridInfoService;
    @Autowired
    private BeforehandRealService beforehandRealService;
    @Override
    protected BaseDao<BeforehandRealAudit, Long> getEntityDao() {
        return beforehandRealAuditDao;
    }

    @SuppressWarnings("unchecked")
    public PageImpl<BeforehandRealBO> findByPage(BeforehandRealAudit audit,
                                                 Pageable pageable) throws Exception {
        PageImpl<BeforehandRealBO> pageResult = null;
        Page<BeforehandRealAudit> page = new Page<BeforehandRealAudit>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("select id,city,areaid,grid,operid,date_month dateMonth,status from salary_beforehand_real_audit where audit_user=?");
        paramList.add(AuthContextHolder.getLoginInfo().getOperid());
        if(audit!=null){
            if(StringUtils.isNotEmpty(audit.getCity())){
                sql.append(" and city=?");
                paramList.add(audit.getCity());
            }
            if(StringUtils.isNotEmpty(audit.getAreaid())){
                sql.append(" and areaid=?");
                paramList.add(audit.getAreaid());
            }
            if(StringUtils.isNotEmpty(audit.getGrid())){
                sql.append(" and grid=?");
                paramList.add(audit.getGrid());
            }
            if(audit.getOperid()!=null){
                sql.append(" and operid=?");
                paramList.add(audit.getOperid());
            }
            if(StringUtils.isNotEmpty(audit.getDateMonth())){
                sql.append(" and date_month=?");
                paramList.add(audit.getDateMonth());
            }
            if(StringUtils.isNotEmpty(audit.getStatus())){
                sql.append(" and status=?");
                paramList.add(audit.getStatus());
            }
        }
        sql.append(" order by date_month desc");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
        List<BeforehandRealAudit> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            List<BeforehandRealBO> boList = new ArrayList<BeforehandRealBO>();
            BeforehandRealBO bo = null;
            for (BeforehandRealAudit beforehandRealAudit : resultList) {
                CentSumReq req = new CentSumReq();
                req.setCycleid(beforehandRealAudit.getDateMonth());
                req.setOperator(beforehandRealAudit.getOperid()+"");
                req.setStatus(beforehandRealAudit.getStatus());
                //查询网格编码
                BizGridInfo grid = gridInfoService.findOne(Long.valueOf(beforehandRealAudit.getGrid()));
                req.setWhgridcode(grid.getGridcode());
                req.setPagesize(String.valueOf(pageable.getPageSize()));
                req.setCurrentPage(String.valueOf(pageable.getPageNumber()+1));
                //查询汇总数据
                CentSumRep rep = beforehandRealService.queryCentSum(req);
                if(rep!=null && "0".equals(rep.getRtcode()) && rep.getCentlist()!=null){
                    bo = new BeforehandRealBO();
                    bo.setId(beforehandRealAudit.getId());
                    bo.setCity(beforehandRealAudit.getCity());
                    bo.setAreaid(beforehandRealAudit.getAreaid());
                    bo.setGrid(beforehandRealAudit.getGrid());
                    bo.setOperid(rep.getCentlist().get(0).getOperator());
                    bo.setCycleid(rep.getCentlist().get(0).getCycleid());
                    bo.setRealcents(rep.getCentlist().get(0).getSrccents());
                    bo.setSharecents(rep.getCentlist().get(0).getSharecents());
                    bo.setAdjustcents(rep.getCentlist().get(0).getAdjustcents());
                    bo.setStatus(rep.getCentlist().get(0).getStatus());
                    boList.add(bo);
                }
            }
            pageResult = new PageImpl<BeforehandRealBO>(boList, pageable, total);
        }else{
            pageResult = new PageImpl<BeforehandRealBO>(new ArrayList<BeforehandRealBO>(), pageable, 0);
        }
        return pageResult;
    }

    public boolean update(BeforehandRealAudit audit) throws Exception{
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        persistentService.update(audit);
        BeforehandRealAudit ab =this.findOne(audit.getId());
        //同步更新审核状态
        AuditCentReq req = new AuditCentReq();
        req.setObjvalue(ab.getOperid()+"");
        req.setCycleid(ab.getDateMonth());
        req.setStatus(ab.getStatus());
        req.setOperator(loginInfo.getOperid()+"");
//        req.setAreaid();
        return auditCent(req);
    }
    public boolean exists(String grid,Long operid,String dateMonth) throws Exception{
        if(StringUtils.isEmpty(dateMonth) || operid==null || StringUtils.isEmpty(grid)){
            throw new Exception("参数不完整,无法校验!");
        }
        String sql="select * from salary_beforehand_real_audit where status='0' and grid=? and operid=? and date_month=?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(grid);
        paramList.add(operid);
        paramList.add(dateMonth);
        Long count = persistentService.count(sql.toString(), paramList.toArray());
        if(count>0){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    /**
     * 查询有审核菜单权限的人
     * @return
     * @throws Exception
     */
    public List<PrvOperator> findAduitOperator() throws Exception {
        String sql="select operid as id,t1.name from prv_operator t1 where t1.operid in(" +
                "select DISTINCT o.operid from prv_roleprivs s,prv_operrole o where s.roleid=o.roleid " +
                " and s.menuid in(select menuid from prv_menudef where name='实积分审核'))";
        return persistentService.find(sql,PrvOperator.class);
    }

    /**
     *  积分审核
     * @throws Exception
     */
    public Boolean auditCent(AuditCentReq req) throws Exception{
        if(StringUtils.isEmpty(req.getCycleid()) || StringUtils.isEmpty(req.getObjvalue())){
            throw new Exception("结算月份,结算对象不能为空!");
        }
        AuditCentRep auditCentRep = beforehandRealService.auditCent(req);
        if(!"0".equals(auditCentRep.getRtcode())){
            throw new Exception("审核失败:"+auditCentRep.getMessage());
        }
        return true;
    }

}
