package com.maywide.biz.core.pojo;

import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.prv.entity.PrvOperrole;

public class OperatorInfo extends PrvOperator implements java.io.Serializable {

	private Set<PrvOperrole> roles;
	private Set<PrvDepartment> depts;

	public Set<PrvOperrole> getRoles() {
		return roles;
	}

	public void setRoles(Set<PrvOperrole> roles) {
		this.roles = roles;
	}

	public Set<PrvDepartment> getDepts() {
		return depts;
	}

	public void setDepts(Set<PrvDepartment> depts) {
		this.depts = depts;
	}
	
	public Long getRoleid() {
		if (CollectionUtils.isEmpty(roles)) return -1L;
		return roles.iterator().next().getRoleid();
	}
}
