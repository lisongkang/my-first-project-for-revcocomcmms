package com.maywide.biz.extendattr.pojo;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

/**
 * 客户拓展属性编辑页面初始化数据
 * @author zhuangzhitang-pc
 *
 */
public class PrvAttrEditPageInitDataBO {
	//private List<PrvSysparam> city;
	private String city;
	private int flag ;
	private String mnames;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMnames() {
		return mnames;
	}

	public void setMnames(String mnames) {
		this.mnames = mnames;
	}
	
}
