package com.maywide.test;

import com.maywide.biz.pay.weixin.PayCallbackNotify;
import com.maywide.biz.pay.weixin.Util;
import com.maywide.core.util.PinYinUtils;

public class BossInterTest {
	public static void main(String[] args) {
		try {
			/*JSONObject requestContent = new JSONObject(); 
	        requestContent.put("keyno", "8440710109083803");
	        requestContent.put("pagesize", "20");
	        
	        System.out.println(requestContent.toString());
	        RemotecallLog remotecallLog = BossHttpClientImpl.requestPost("QUE_SERVPRDINFO", requestContent.toString());
	        System.out.println(remotecallLog.getResponse());*/
		    
			/*Set set = new HashSet();
			set.add(new Long(1));
			set.add(new Long(2));
			set.add(new Long(3));
			
			System.out.println(set.size());
			
			set.remove(new Long(2));
			
			System.out.println(set.size());*/
			
			/*JSONObject requestContent = new JSONObject(); 
	        requestContent.put("custid", "1304497");
	        
	        System.out.println(requestContent.toString());
	        RemotecallLog remotecallLog = BossHttpClientImpl.requestPost("QUE_BIZLOGBYPAGE", requestContent.toString());
	        System.out.println(remotecallLog.getResponse());*/
			
			//System.out.println(Double.valueOf("79.60").intValue());
			//XmlMapper xmlMapper = new XmlMapper();
			//String content = "<xml><appid><![CDATA[wxa7621a1ce2ed3064]]></appid><attach><![CDATA[test]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1220331001]]></mch_id><nonce_str><![CDATA[72zcp2dbhzczsxxa10033028wha4v4fi]]></nonce_str><openid><![CDATA[oyJ5ot6GCkxDshfJ5i0ojNpagHMQ]]></openid><out_trade_no><![CDATA[1000000002]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[79111538CF141828A624553EA7EAD618]]></sign><time_end><![CDATA[20151010112059]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[1007760614201510101147259346]]></transaction_id></xml>";
			//XmlMapper xmlMapper = new XmlMapper();
			//PayCallbackNotify payCallbackNotify = xmlMapper.readValue(content, PayCallbackNotify.class);
			//PayCallbackNotify payCallbackNotify = (PayCallbackNotify) Util.getObjectFromXML(content, PayCallbackNotify.class);
			
			/*int totalfee = Integer.valueOf("10");
			double fee = totalfee / 100.0;
			
			System.out.println(fee);*/
			
			/*String pinyin1 = PinYinUtils.converterToFirstSpell("21分部");
			String pinyin2 = PinYinUtils.converterToFirstSpell("FG分部");
			System.out.println(pinyin1.compareTo(pinyin2));*/
			
			String path = "http://210.21.65.94:6062/moss/api";
			int pos = path.indexOf("/", 8);
			System.out.println(path.substring(0, pos));
			//System.out.println(path.substring(0, pos));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
