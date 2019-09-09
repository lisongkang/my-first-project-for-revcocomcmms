package com.maywide.biz.inter.pojo.sendrandomcode;

public class AlibabaSMSReq {

	private String extend;               //公共回传参数，在“消息返回”中会透传回该参数；
	
	private String sms_param;			//短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
	
	private String rec_num;				//短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。
	
	private String sms_type = "normal";  //短信类型，传入值请填写normal
	
	private String sms_free_sign_name; //短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
	
	private String sms_template_code;   //短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。

	public String getRec_num() {
		return rec_num;
	}

	public void setRec_num(String rec_num) {
		this.rec_num = rec_num;
	}

	public String getSms_type() {
		return sms_type;
	}

	public void setSms_type(String sms_type) {
		this.sms_type = sms_type;
	}

	public String getSms_free_sign_name() {
		return sms_free_sign_name;
	}

	public void setSms_free_sign_name(String sms_free_sign_name) {
		this.sms_free_sign_name = sms_free_sign_name;
	}

	public String getSms_template_code() {
		return sms_template_code;
	}

	public void setSms_template_code(String sms_template_code) {
		this.sms_template_code = sms_template_code;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getSms_param() {
		return sms_param;
	}

	public void setSms_param(String sms_param) {
		this.sms_param = sms_param;
	}
	
	
	
}
