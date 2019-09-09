package com.maywide.biz.core.pojo;


import java.util.Set;

import com.maywide.biz.system.entity.PrvSysparam;


public class PrvSysparamParam extends PrvSysparam {
	private Set _in_id;
	private Set _in_gcode;
	private Set _in_mcode;

	public Set get_in_id() {
		return _in_id;
	}

	public void set_in_id(Set _in_id) {
		this._in_id = _in_id;
	}

	public Set get_in_gcode() {
		return _in_gcode;
	}

	public void set_in_gcode(Set _in_gcode) {
		this._in_gcode = _in_gcode;
	}

	public Set get_in_mcode() {
		return _in_mcode;
	}

	public void set_in_mcode(Set _in_mcode) {
		this._in_mcode = _in_mcode;
	}

}


