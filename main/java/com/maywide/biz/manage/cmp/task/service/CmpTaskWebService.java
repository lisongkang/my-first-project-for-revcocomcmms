package com.maywide.biz.manage.cmp.task.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.inter.pojo.queactivitylist.CmpActivityInfo;
import com.maywide.biz.inter.pojo.queactivitylist.QueActivityListReq;
import com.maywide.biz.inter.pojo.queactivitylist.QueActivityListResp;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListReq;
import com.maywide.biz.inter.pojo.quetasklist.QueTaskListResp;
import com.maywide.biz.inter.service.CmpTaskService;
import com.maywide.core.service.PersistentService;

@Service
public class CmpTaskWebService {
	
	@Autowired
	private CmpTaskService cmpTaskService;
	
	@Autowired
	private PersistentService persistentService;
	/**
	 * 查询活动列表
	 * @param req
	 * @param resp
	 * @throws Exception 
	 */
	public  void queActivityList(QueActivityListReq req, QueActivityListResp resp) throws Exception{
		
		cmpTaskService.queActivityList(req, resp);
	}

	/**
	 * 网格任务列表查询
	 * @throws Exception 
	 */
	public void queTaskList(QueTaskListReq req, QueTaskListResp resp) throws Exception {
		
		cmpTaskService.queTaskList(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<CmpActivityInfo> translateToGridCode(List<CmpActivityInfo> datas) throws Exception {
		List<CmpActivityInfo>  list = new ArrayList<CmpActivityInfo>();
		List<BizGridInfo> tmpInfos = new ArrayList<BizGridInfo>();
		if(datas == null){
			return list;
		}
		for(CmpActivityInfo data :datas){
			if(StringUtils.isNotBlank(data.getGridid())){
				BizGridInfo gridInfo = new BizGridInfo();
				gridInfo.setGridcode(data.getGridid());
				tmpInfos = persistentService.find(gridInfo);
			    if(tmpInfos!=null&&tmpInfos.size()>0){
			    	gridInfo = tmpInfos.get(0);
			    }
			    data.setGridname(gridInfo.getGridname());
			}
			 list.add(data);
		}
		
		//排序
		if(list.size()>0){
			  Collections.sort(list, new Comparator<CmpActivityInfo>(){  
				  
		            /*  
		             * int compare(CmpActivityInfo o1, CmpActivityInfo o2) 返回一个基本类型的整型，  
		             * 返回负数表示：o1 小于o2，  
		             * 返回0 表示：o1和o2相等，  
		             * 返回正数表示：o1大于o2。  
		             */  
		            public int compare(CmpActivityInfo o1, CmpActivityInfo o2) {  
		              
		                //按照网格名进行升序排列  
		               if(o1.getGridname()==null||o1.getGridname()==null){
		            	   return 0;
		               }
		               if(o1.getGridname().compareTo(o2.getGridname())>0 ){  
		                    return 1;  
		                } 
		                if(o1.getGridname() == o2.getGridname()){  
		                    return 0;  
		                }  
		                return -1;  
		            }  
		        });   
		}
		return list;
	}
}
