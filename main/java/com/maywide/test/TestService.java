package com.maywide.test;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.maywide.biz.core.entity.TabConfig;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.prv.entity.PrvDepartment;

@Service
public class TestService {
	public ReturnInfo queryTest() {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		result.setMessage("aaaaaaaaaaa");
		
		return result;
	}
	
	public ReturnInfo queryTest2(String id) {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		result.setMessage("id = " + id);
		
		return result;
	}
	
	public ReturnInfo queryTest22(Integer id) {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		result.setMessage("id = " + id);
		
		return result;
	}
	
	public ReturnInfo queryTest3(String retval) {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		retval = "cccc";
		result.setMessage("retval = " + retval);
		
		return result;
	}
	
	public ReturnInfo queryTest4(String id, TabConfig retval) {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		retval.setClassName("aaaaaaaaaa");
		retval.setTabIndex(3);
		result.setMessage("id = " + id);
		
		return result;
	}
	
	public ReturnInfo queryDepartment(String loginname, ArrayList<PrvDepartment> departments) {
		ReturnInfo result = new ReturnInfo();
		result.setCode(0L);
		result.setMessage("操作成功");
		PrvDepartment retval = new PrvDepartment();
		retval.setId(188148L);
		retval.setName("大沥营业厅");
		departments.add(retval);
		
		retval = new PrvDepartment();
		retval.setId(188188L);
		retval.setName("大塘营业厅");
		departments.add(retval);
		
		return result;
	}
}
