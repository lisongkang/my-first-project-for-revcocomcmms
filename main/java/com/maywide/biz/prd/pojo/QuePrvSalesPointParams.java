package com.maywide.biz.prd.pojo;
/**
 * 配置销售积分业务的查询参数
 * @author zhuangzhitang-pc
 *
 */
public class QuePrvSalesPointParams {

	//销售积分首页的查询条件
    private String city;   //
  //  private Long areaid;
    private Long isvalid;
    private String opcode;
    private Long wtype;
   
    //商品id 提交时需判断是否存在相同【业务操作、商品ID、用工类型】且状态为有效的记录
    private Long saleid;
    
    
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Long getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(Long isvalid) {
		this.isvalid = isvalid;
	}
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	public Long getWtype() {
		return wtype;
	}
	public void setWtype(Long wtype) {
		this.wtype = wtype;
	}
	public Long getSaleid() {
		return saleid;
	}
	public void setSaleid(Long saleid) {
		this.saleid = saleid;
	}
    
}
