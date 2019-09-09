package com.maywide.tool.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFtpService {

	private Session sshSession;

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public ChannelSftp connect(String host, int port, String username, String password) throws Exception {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshConfig.put("kex", "diffie-hellman-group1-sha1");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			throw e;
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 * @throws Exception
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) throws Exception {
		FileInputStream fileInputStream = null;
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			fileInputStream = new FileInputStream(file);
			sftp.put(fileInputStream, file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fileInputStream) fileInputStream.close();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 * @throws Exception
	 */
	public void upload(String directory, File file, String filename, ChannelSftp sftp) throws Exception {
		FileInputStream fileInputStream = null;
		try {
			sftp.cd(directory);
			fileInputStream = new FileInputStream(file);
			sftp.put(fileInputStream, filename);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fileInputStream) fileInputStream.close();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 * @throws Exception
	 */
	public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp)
			throws Exception {
		FileOutputStream fileOutputStream = null;
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			if (file.exists()) file.delete();
			fileOutputStream = new FileOutputStream(file);
			sftp.get(downloadFile, fileOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != fileOutputStream) {
				fileOutputStream.close();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException {
		return sftp.ls(directory);
	}

	public void disconnect() {
		if (null != sshSession) {
			sshSession.disconnect();
		}
	}

}
