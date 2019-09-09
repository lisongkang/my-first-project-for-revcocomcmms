package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.pojo.queprocesssequencebycity.QueProcessSequenceByCityInterReq;
import com.maywide.biz.inter.pojo.queprocesssequencebycity.QueProcessSequenceByCityInterResp;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.service.PersistentService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessSequenceService extends CommonService {
	@Autowired
	private PersistentService persistentService;

	/**
	 * 描述：根据city获得业务顺序和是否有权限
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queProcessSequenceByCity(QueProcessSequenceByCityInterReq req,QueProcessSequenceByCityInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		List<PrvSysparam> list = getList(req.getCity());
		resp.setList(list);
		return returnInfo;
	}
	@Transactional(readOnly = true)
	public List<PrvSysparam> getList(String city) throws Exception {
		List<Object> params = new ArrayList();
		params.add(city);
		StringBuffer sql = new StringBuffer();
		sql.append("select s.paramid as id,s.gcode,s.mcode,s.mname,s.data,s.fmt,s.sort,s.scope,s.type,s.memo from prv_sysparam s where s.gcode = 'PROCESS_SEQUENCE' and s.data = ? and s.type = 'Y' ORDER BY s.mname asc");
		
		List<PrvSysparam> list = persistentService.find(sql.toString(), PrvSysparam.class,params.toArray());
		
		return list;
	}

}
