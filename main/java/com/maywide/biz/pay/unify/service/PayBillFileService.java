package com.maywide.biz.pay.unify.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.pay.unify.pojo.UnifyPayBillBean;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.util.DateUtils;

@Service
public class PayBillFileService extends CommonService{
	
	@Autowired
	private ParamService paramService;

	private static final String CHANNEL_CODE = "1031";

	private static final String SEPARATOR = "~";

	private static final String BILL_FILE_LOCAL_DIR2 = "BILL_FILE_LOCAL_DIR2";
	
	private String localDir;
	
	/**
	 * 生成对账文件
	 * @param date
	 * @throws Exception
	 */
	public void produceLogFile(String dateStr) throws Exception {
		Date date = DateUtils.parseDate(dateStr, DateUtils.FORMAT_YYYYMMDD_FORMATER);
		List<UnifyPayBillBean> payBill = getPayBill(date);
		String fileName = CHANNEL_CODE + "_" + DateUtils.formatDate(date, "yyyyMMdd") + ".txt";
		File dir = new File(getLocalDir());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, fileName);
		FileUtils.write(file, getFileStr(payBill), "UTF-8");
	}
	
	public List<UnifyPayBillBean> getPayBill(Date date) throws Exception {
		String stime = DateUtils.formatDate(date);
		String etime = DateUtils.plusOneDay(date);

		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT po.orderid, po.resporderid, co.custid, po.paytime, po.fees, po.status ");
		sb.append(" FROM biz_portal_order po");
		sb.append(" INNER JOIN biz_custorder co ON po.orderid = co.orderid ");
		sb.append(" WHERE po.resporderid IS NOT NULL AND (po.wgpayway = ? or po.wgpayway = ?)");
		params.add(BizConstant.SysPayway.SYS_PAYWAY_UNIFY);
		params.add(BizConstant.SysPayway.SYS_PAYWAY_UNIFY2);
		sb.append(" AND po.paytime >= ? AND po.paytime < ? ");
		params.add(stime);
		params.add(etime);
		List<UnifyPayBillBean> list = getDAO().find(sb.toString(), UnifyPayBillBean.class, params.toArray());
		return list;
	}
	
	public String getFileStr(List<UnifyPayBillBean> list) throws Exception {
		if (list == null || list.isEmpty()) return "0";
		StringBuilder sb = new StringBuilder();
		sb.append(list.size());
		for (UnifyPayBillBean bean : list) {
			sb.append("\r\n").append(bean.getOrderid()).append(SEPARATOR).append(bean.getResporderid())
					.append(SEPARATOR).append(bean.getCustid()).append(SEPARATOR).append(bean.getPaytimeStr())
					.append(SEPARATOR).append(bean.getFeesStr()).append(SEPARATOR).append(bean.getPayStatus())
					.append(SEPARATOR).append("0"); // 0：包月产品
		}
		return sb.toString();
	}

	private String getLocalDir() throws Exception {
		if (StringUtils.isBlank(localDir)) {
			localDir = paramService.getMcode(BILL_FILE_LOCAL_DIR2);
		}
		if (localDir == null) {
			throw new BusinessException("未配置对账本地文件夹");
		}
		return localDir;
	}
}
