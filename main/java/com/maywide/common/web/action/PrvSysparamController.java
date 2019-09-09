package com.maywide.common.web.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.core.security.AuthContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.web.SimpleController;

public class PrvSysparamController extends SimpleController{
	private static final String UNDEFINED = "undefined";

	@Autowired
	private ParamService paramService;
	
	public HttpHeaders selectParamList() {
		String gcode = getParameter("gcode");
		String mcode = getParameter("mcode");
		String mcodeSeparator = getParameter("mcodeSeparator");
		if (StringUtils.isEmpty(gcode)) {
			return new DefaultHttpHeaders();
		}
		try {
			setModel(paramService.getData(gcode, mcode, null, mcodeSeparator));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new DefaultHttpHeaders();
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HttpHeaders selectParamListByMcodes() {
        String gcode = getParameter("gcode");
        String mcodes = getParameter("mcodes");
        if (StringUtils.isEmpty(gcode)) {
            return new DefaultHttpHeaders();
        }
        try {
            List paramList = new ArrayList();
            for (String mcode : mcodes.split(",")) {
                paramList.addAll(paramService.getData(gcode, mcode, null, null));
            }
            setModel(paramList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DefaultHttpHeaders();
    }

	public HttpHeaders selectParamMname(){
		String gcode = getParameter("gcode");
		String mcode = getParameter("mcode");
		if (StringUtils.isEmpty(gcode)) {
			return new DefaultHttpHeaders();
		}
		try {
			List<PrvSysparam> list = paramService.getData(gcode, mcode, null, null);
			if(null != list && list.size()>0)
				setModel(list.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders();
	}
	
	public HttpHeaders getSelectData(){
        String gcode = getParameter("gcode");
        String mcode = getParameter("mcode");
		String mcodeSeparator = getParameter("mcodeSeparator");
        if (StringUtils.isEmpty(gcode)) {
            return new DefaultHttpHeaders();
        }
		if (StringUtils.isEmpty(mcodeSeparator)) {
			mcodeSeparator = null;
		}
        Map<String, String> map = new LinkedHashMap<String, String>();
        try {
        	List<PrvSysparam> list = paramService.getData(gcode,mcode,null,mcodeSeparator);
        	if(null != list && list.size()>0){
        		for(PrvSysparam param : list){
        			map.put(param.getMcode(),param.getMname());
        		}
        	}
            setModel(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new DefaultHttpHeaders();
        
    }
    //加操作员的权限配置
	public HttpHeaders selectParamAuthMap() {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String gcode = getParameter("gcode");
		String mcode = getParameter("mcode");

		PrvSysparam param = new PrvSysparam();
		Map<Long, String> paramMap = new LinkedHashMap<Long, String>();

		if (StringUtils.isEmpty(gcode)) {
			return new DefaultHttpHeaders();
		} else {
			param.setGcode(gcode);
		}

		if (StringUtils.isNotBlank(mcode) && !UNDEFINED.equals(mcode)) {
			param.setMcode(mcode);
		}

		try {
			List<PrvSysparam> list = paramService.getAuthRoleInfoData(param,loginInfo.getRolelevel());
			setModel(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new DefaultHttpHeaders();
	}


	public HttpHeaders selectParamMap() {
		String gcode = getParameter("gcode");
		String mcode = getParameter("mcode");
		
		PrvSysparam param = new PrvSysparam();
		Map<Long, String> paramMap = new LinkedHashMap<Long, String>();
		
		if (StringUtils.isEmpty(gcode)) {
			return new DefaultHttpHeaders();
		} else {
			param.setGcode(gcode);
		}
		
		if (StringUtils.isNotBlank(mcode) && !UNDEFINED.equals(mcode)) {
			param.setMcode(mcode);
		}
		
		try {
			List<PrvSysparam> list = paramService.getData(param);
			setModel(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new DefaultHttpHeaders();
	}
}
