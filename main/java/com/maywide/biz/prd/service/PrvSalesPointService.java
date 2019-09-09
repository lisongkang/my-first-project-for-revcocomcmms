package com.maywide.biz.prd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.prd.dao.PrvSalesPointDao;
import com.maywide.biz.prd.entity.PrvSalesPoint;
import com.maywide.biz.prd.entity.Sales;
import com.maywide.biz.prd.pojo.QuePrvSalesPointParams;
import com.maywide.biz.survey.entity.BizSurveyList;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class PrvSalesPointService extends BaseService<PrvSalesPoint,Long>{
    
    @Autowired
    private PrvSalesPointDao prvSalesPointDao;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<PrvSalesPoint, Long> getEntityDao() {
        return prvSalesPointDao;
    }
    
    public PageImpl<PrvSalesPoint> queryPrvSalesPointList(QuePrvSalesPointParams param, Pageable pageable) throws Exception{
    	PageImpl<PrvSalesPoint> pageResult = null;
    	
    	Page<PrvSalesPoint> page = new Page<PrvSalesPoint>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
        sql.append("SELECT * FROM ("); // 要在最外层加select *，否则框架本身根据构造的语句查总记录数时的sql会错误
        sql.append("SELECT t.sid id,");
        sql.append(" (SELECT s.mname FROM prv_sysparam s WHERE  s.gcode = 'PRV_CITY' AND s.mcode= t.city) cityname,");
     //   sql.append(" (SELECT a.name FROM prv_area a WHERE a.areaid = t.areaid ) areaname,");
        sql.append(" (SELECT p.mname FROM prv_sysparam p WHERE p.gcode = 'BIZ_OPCODE' AND p.mcode = t.opcode) opcodename,");
        sql.append("t.SALESID  salesid,");
        sql.append("t.SALESNAME salesname,");
        sql.append("t.WTYPE wtype,");
        sql.append("t.POINTS points,");
        sql.append("t.ISVALID isvalid,");
        sql.append("t.INTIME intime,");
        sql.append("t.ETIME etime");
        sql.append(" FROM prv_sales_point t WHERE 1=1 ");
        
        if(param.getCity() != null && !param.getCity().trim().equals("")){
       	 sql.append(" AND t.city = ?");
       	 paramList.add(param.getCity());
        }
        
      /*  if(param.getAreaid() != null){
          	 sql.append(" AND t.areaid = ?");
          	 paramList.add(param.getAreaid());
        }*/
        
        if(param.getIsvalid() != null){
         	 sql.append(" AND t.isvalid = ?");
         	 paramList.add(param.getIsvalid());
        }
        
        if(param.getOpcode() != null && !param.getOpcode().trim().equals("")){
        	 sql.append(" AND t.opcode = ?");
        	 paramList.add(param.getOpcode());
        }
        
        if(param.getWtype() != null){
       	 sql.append(" AND t.wtype = ?");
       	 paramList.add(param.getWtype());
       }
        sql.append(" ORDER BY t.INTIME DESC,t.SID DESC");
        sql.append(" ) v ");
        persistentService.clear();
		page = persistentService.find(page, sql.toString(), PrvSalesPoint.class, paramList.toArray());
        
		 if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<PrvSalesPoint> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		 } else {
		    List<PrvSalesPoint> resultList = new ArrayList<PrvSalesPoint>();
		    pageResult = new PageImpl(resultList, pageable, 0);
		 }
		return pageResult;
    }

	public PrvSalesPoint checkSalePoint(PrvSalesPoint prvSalesPoint) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("SELECT t.SID id," +
				       "(SELECT s.mname FROM prv_sysparam s WHERE  s.gcode = 'PRV_CITY' AND s.mcode= t.city) cityname," +
				       "(SELECT p.mname FROM prv_sysparam p WHERE p.gcode = 'BIZ_OPCODE' AND p.mcode = t.opcode) opcodename,t.* " +
				       "FROM prv_sales_point t WHERE 1 = 1 AND t.ISVALID=0");
		if(prvSalesPoint.getCity() != null){
			sql.append(" AND t.CITY = ?");
			paramList.add(prvSalesPoint.getCity());
		}
		if(prvSalesPoint.getOpcode() != null){
			sql.append(" AND t.OPCODE = ?");
		    paramList.add(prvSalesPoint.getOpcode());
		}
		if(prvSalesPoint.getWtype() != null){
			sql.append(" AND t.WTYPE = ?");
			paramList.add(prvSalesPoint.getWtype());
		}
		if(prvSalesPoint.getSalesid() == null){
			sql.append(" AND t.SALESID IS NULL");
		}else {
			sql.append(" AND t.SALESID = ?");
			paramList.add(prvSalesPoint.getSalesid());
		}
		sql.append(" ORDER BY t.SID DESC");
		persistentService.clear();
		
		List<PrvSalesPoint> list = persistentService.find(sql.toString(), PrvSalesPoint.class, paramList.toArray());
	    
		if(list !=null && list.size() !=0){
			return list.get(0);
		}else{
			return null;
		}
	}
    
	public String getSalesname(long saleid) throws Exception{
    	String salename = null;
        Sales sale = new Sales();
        sale.setId(saleid);
    	List<Sales> list = persistentService.find(sale);
    	if(list.size()>0){
    		salename =  list.get(0).getSalesName();
    	}
    	return salename;
    }
    
}
