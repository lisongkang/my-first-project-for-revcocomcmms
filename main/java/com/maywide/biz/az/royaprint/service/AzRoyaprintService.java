package com.maywide.biz.az.royaprint.service;

import com.maywide.biz.az.royaprint.dao.AzRoysprintDao;
import com.maywide.biz.az.royaprint.entity.BizApplyConstBO;
import com.maywide.biz.az.royaprint.pojo.AzRoyaSerchParamsBO;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.entity.BizApplication;
import com.maywide.biz.inter.pojo.queApplicationAllinfo.ApplicationAccfileidsBO;
import com.maywide.biz.inter.service.PubService;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ls.LSSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/12 0001.
 */
@Service
@Transactional
public class AzRoyaprintService extends BaseService<BizApplication,Long> {
    @Autowired
    private AzRoysprintDao azRoysprintDao;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private ParamService paramService;
    @Autowired
    private PubService pubService;
    @Override
    protected BaseDao<BizApplication,Long> getEntityDao(){return azRoysprintDao;}

    @SuppressWarnings("unchecked")
    public PageImpl<BizApplication> querySaleOrderList(AzRoyaSerchParamsBO param, Pageable pageable)
            throws Exception {
        PageImpl<BizApplication> pageResult = null;
        Page<BizApplication> page = new Page<BizApplication>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append(" SELECT a.proid,a.pronum,a.proname,a.city,a.buildunit,a.endtime,a.prostatus,a.constructors from biz_application a where 1=1 ");
        String city = param.getCity();
        Long areaid = param.getAreaid();
        String prostartus = param.getProstatus();
        String proname = param.getProname();
        String constructor = param.getConstructors();
        if (null != city && !"".equals(city)) {
            sql.append(" AND a.city = ? ");
            paramList.add(city);
        }
        if (null != areaid && !"".equals(areaid)) {
            //areaid 找 name
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append("SELECT name from prv_area where areaid = ? ");
            params.add(areaid);
            persistentService.clear();
            String patchName = persistentService.findUniqueObject(sqlBuffer.toString(),params.toArray()).toString();
            sql.append(" AND a.buildunit = ?");
            paramList.add(patchName);
        }
        if (null != prostartus && !"".equals(prostartus)) {
            sql.append(" AND a.prostatus = ? ");
            paramList.add(prostartus);
        }
        if (null != proname && !"".equals(proname)) {
            sql.append(" AND a.proname like ? ");
            paramList.add("%" + proname + "%");
        }
        if (null != constructor && !"".equals(constructor)) {
            sql.append(" AND a.constructors like ? ");
            paramList.add("%" + constructor + "%");
        }
        if(param.getStime()!= null && param.getEtime() != null){
            String dateFormat = "'%Y-%m-%d %H:%i:%s'";
            sql.append(" AND a.endtime>=STR_TO_DATE(?,").append(dateFormat).append(")");
            sql.append(" AND a.endtime<=STR_TO_DATE(?,").append(dateFormat).append(")");
            sql.append(" ORDER BY a.endtime DESC ");
            paramList.add(param.getStime());
            paramList.add(param.getEtime());
        }
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), BizApplication.class, paramList.toArray());

        List<BizApplication> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<BizApplication>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<BizApplication>(new ArrayList<BizApplication>(), pageable, 0);
        }
        return pageResult;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public BizApplication queApplication(String sid) throws Exception{
        StringBuffer sqlBuffer = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sqlBuffer.append("SELECT a.proid as id,a.proname,a.pronum,a.buildaddr,a.buildunit,a.builddetp,a.prodetail,a.needdept,a.appopinion,a.prostatus,a.fileids,a.constcost,a.totalprice,a.procost,a.creator,a.creatorid,a.approveor,a.approveid,a.editid,a.planstarttime,a.starttime,a.endtime,a.applicationtime,a.constructors,a.planendtime,a.operation,a.accopinion,a.acceptername,a.accepterid,a.city,a.applyrecallopinion,a.applyacceptance from biz_application a where pronum= ? ");
        paramList.add(sid);
        persistentService.clear();
        List<BizApplication> bizApplicationList = persistentService.find(sqlBuffer.toString(),BizApplication.class,paramList.toArray());
        return bizApplicationList.get(0);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<BizApplyConstBO> queBizApplyInfos(String sid) throws Exception{
        List<BizApplyConstBO> bizApplyConstBOList = null;
        StringBuffer sqlBuffer = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sqlBuffer.append(" SELECT a.proid as proid,a.constructorid as constructorid,a.constructorname as constructorname,a.royalty as royalty,b.idcard as idcard,b.bankcard as bankcard ");
        sqlBuffer.append("  FROM biz_application_distribution a ,prv_operator_idbankcard b where a.constructorid = b.operid and a.proid = ? ");
        paramList.add(sid);
        persistentService.clear();
        bizApplyConstBOList = persistentService.find(sqlBuffer.toString(),BizApplyConstBO.class,paramList.toArray());
        for (BizApplyConstBO bizApplyConstBO:bizApplyConstBOList
             ) {
            List<Object> paramListTemp = new ArrayList<Object>();
            String operid = bizApplyConstBO.getConstructorid();
            sqlBuffer.delete(0,sqlBuffer.length());
            sqlBuffer.append(" SELECT group_concat(p.name) as deptids from  prv_department p where p.deptid in (");
            sqlBuffer.append(" select t.depid from prv_operdept  t where t.operid in ");
            sqlBuffer.append(" (select t1.OPERID from  prv_operator  t1  where t1.operid = ? ))");
            paramListTemp.add(operid);
            persistentService.clear();
            String deptids = persistentService.findUniqueObject(sqlBuffer.toString(),paramListTemp.toArray()).toString();
            bizApplyConstBO.setDepts(deptids);
        }
        return bizApplyConstBOList;
    }


    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<ApplicationAccfileidsBO> queBizappFileInfo(String sid) throws Exception{
        List<ApplicationAccfileidsBO> accfileidsBOS = null;
        StringBuffer sqlBuffer = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sqlBuffer.append("SELECT proid,filepath,aplytime,realfilename,isimage from biz_application_acceptfileis where proid= ? ");
        paramList.add(sid);
        persistentService.clear();
        accfileidsBOS = persistentService.find(sqlBuffer.toString(),ApplicationAccfileidsBO.class,paramList.toArray());
        for (ApplicationAccfileidsBO applicationAccfileidsBO:accfileidsBOS
             ) {
            if(applicationAccfileidsBO.getIsimage().equals("1")){
                applicationAccfileidsBO.setRealfilename(
                        applicationAccfileidsBO.getFilepath().substring(applicationAccfileidsBO.getFilepath().lastIndexOf("/") + 1)
                );
            }
        }
        return accfileidsBOS;
    }

}
