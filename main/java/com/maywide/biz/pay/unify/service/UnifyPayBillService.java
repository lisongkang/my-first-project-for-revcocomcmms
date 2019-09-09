package com.maywide.biz.pay.unify.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.pay.unify.pojo.UnifyPayBillBean;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.util.DateUtils;
import com.maywide.tool.ftp.FtpHelper;
import com.maywide.tool.sftp.SFtpService;

@Service
public class UnifyPayBillService extends CommonService {

	@Autowired
	private ParamService paramService;

	private static final String CHANNEL_CODE = "1031";

	private static final String SEPARATOR = "~";

	private static final String BILL_FILE_LOCAL_DIR = "BILL_FILE_LOCAL_DIR2";

	private String host;
	private int port;
	private String username;
	private String password;
	private String remoteDirectory;
	private String localDir;
	

	public void uploadYesterdayBillFile() throws Exception {
		uploadBillFile(DateUtils.getYesterday());
	}
	
	public void uploadDateBillFile(String dateStr) throws Exception{
		uploadBillFile(DateUtils.parseDate(dateStr, DateUtils.FORMAT_YYYYMMDD_FORMATER));
	}

	public void uploadBillFile(Date date) throws Exception {
		List<UnifyPayBillBean> payBill = getPayBill(date);

		String fileName = CHANNEL_CODE + "_" + DateUtils.formatDate(date, "yyyyMMdd") + ".txt";
		File dir = new File(getLocalDir());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, fileName);
		FileUtils.write(file, getFileStr(payBill), "UTF-8");

		log.info("----开始上传对账文件：" + fileName + "----");
		/*FtpHelper ftpHelper = getFtpHelper();
		ftpHelper.openConnect();
		boolean succ = ftpHelper.uploadFile(file.getAbsolutePath(), remoteDirectory);*/
		BillSFtpService billService = BillSFtpService.getInstance();
		boolean succ = true;
		try {
			billService.upload(file,file.getName());
		}catch(Exception ex) {
			succ = false;
		}
//		ftpHelper.closeConnect();
		if (!succ) {
			throw new Exception("对账文件上传失败");
		}
		log.info("----上传对账文件成功----");
	}

	private FtpHelper getFtpHelper() throws Exception {
		List<PrvSysparam> params = paramService.getDataNotCompare("BILL_FILE_SERVER");
		host = getParamData(params, "BILL_FILE_SERVER_IP");
		port = Integer.parseInt(getParamData(params, "BILL_FILE_SERVER_PORT"));
		username = getParamData(params, "BILL_FILE_SERVER_USER");
		password = getParamData(params, "BILL_FILE_SERVER_PWD");
		remoteDirectory = getParamData(params, "BILL_FILE_SERVER_DIRECTORY");
		return new FtpHelper(host, port, username, password);
	}
	

	private String getParamData(List<PrvSysparam> params, String mcode) {
		if (mcode == null) return null;
		for (PrvSysparam param : params) {
			if (mcode.equals(param.getMcode())) {
				return param.getData();
			}
		}
		return null;
	}

	private String getLocalDir() throws Exception {
		if (StringUtils.isBlank(localDir)) {
			localDir = paramService.getMcode(BILL_FILE_LOCAL_DIR);
		}
		if (localDir == null) {
			throw new BusinessException("未配置对账本地文件夹");
		}
		return localDir;
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

	@SuppressWarnings("unchecked")
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

}
