package com.maywide.biz.pay.unify.service;

import java.io.File;
import java.util.List;

import com.jcraft.jsch.ChannelSftp;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.tool.sftp.SFtpService;

public class BillSFtpService extends SFtpService {

	private static ChannelSftp sftp;
	private static BillSFtpService busiSftpService;
	private static String host;
	private static int port;
	private static String username;
	private static String password;
	private static String directory;

	private ParamService paramService;

	private BillSFtpService() throws Exception {
		paramService = SpringContextHolder.getBean(ParamService.class);
		List<PrvSysparam> params = paramService.getDataNotCompare("BILL_FILE_SERVER");
		host = getParamData(params, "BILL_FILE_SERVER_IP");
		port = Integer.parseInt(getParamData(params, "BILL_FILE_SERVER_PORT"));
		username = getParamData(params, "BILL_FILE_SERVER_USER");
		password = getParamData(params, "BILL_FILE_SERVER_PWD");
		directory = getParamData(params, "BILL_FILE_SERVER_DIRECTORY");
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

	private void init() {

		try {
			sftp = connect(host, port, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void destory() {
		if (null != sftp) sftp.quit();
		sftp = null;
		super.disconnect();
	}

	public ChannelSftp getSftp() {
		return sftp;
	}

	public static BillSFtpService getInstance() throws Exception {
		if (null == busiSftpService) {
			busiSftpService = new BillSFtpService();
		}
		return busiSftpService;
	}

	public void download(String filename) throws Exception {
		init();
		try {
			this.download(directory, filename, filename, getSftp());
		} catch (Exception e) {
			throw e;
		} finally {
			destory();
		}

	}

	public void upload(File file, String filename) throws Exception {
		init();
		try {
			sftp.cd(directory);
		} catch (Exception e) {
			e.printStackTrace();
			sftp.mkdir(directory);
		}
		try {
			this.upload(directory, file, filename, sftp);
		} catch (Exception e) {
			throw e;
		} finally {
			destory();
		}

	}

}
