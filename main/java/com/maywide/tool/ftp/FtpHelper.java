package com.maywide.tool.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpHelper {
	/**
	 * 服务器地址
	 */
	private String hostName;

	/**
	 * 用户名.
	 */
	private String userName;

	/**
	 * 密码.
	 */
	private String password;

	/**
	 * FTP连接.
	 */
	private FTPClient ftpClient;

	/**
	 * FTP当前目录.
	 */
	private String currentPath = "";

	/**
	 * 构造函数.
	 *
	 * @param host
	 *            hostName 服务器名
	 * @param user
	 *            userName 用户名
	 * @param pass
	 *            password 密码
	 */
	public FtpHelper(String host, int port, String user, String pass) {
		this.hostName = host;
		this.userName = user;
		this.password = pass;
		this.ftpClient = new FTPClient();
		this.ftpClient.setDefaultPort(port);
		this.ftpClient.setConnectTimeout(30000);
	}

	/**
	 * 打开FTP服务.
	 *
	 * @throws IOException
	 */
	public void openConnect() throws IOException {
		// 中文转码
		ftpClient.setControlEncoding("UTF-8");
		int reply; // 服务器响应值
		// 连接至服务器
		ftpClient.connect(hostName);
		// 获取响应值
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			// 断开连接
			ftpClient.disconnect();
			throw new IOException("connect fail: " + reply);
		}
		// 登录到服务器
		if (userName != null && !userName.equals("")) {
			ftpClient.login(userName, password);
		} else {
			// 无用户名登录时
			ftpClient.login("anonymous", "123456");
		}
		// 获取响应值，判断登陆结果
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			// 断开连接
			ftpClient.disconnect();
			throw new IOException("connect fail: " + reply);
		} else {
			// 获取登录信息
			FTPClientConfig config = new FTPClientConfig(ftpClient.getSystemType().split(" ")[0]);
			config.setServerLanguageCode("zh");
			ftpClient.configure(config);
			// 使用被动模式设为默认
			// ftpClient.enterLocalPassiveMode();
			// ftpClient.enterLocalActiveMode();
			// ftpClient.enterRemotePassiveMode();
			// // 二进制文件支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		}
	}

	/**
	 * 关闭FTP服务.
	 *
	 * @throws IOException
	 */
	public void closeConnect() throws IOException {
		if (ftpClient != null) {
			// 登出FTP
			ftpClient.logout();
			// 断开连接
			ftpClient.disconnect();
		}
	}

	/**
	 * 获取ftp连接状态
	 *
	 * @throws IOException
	 */
	public boolean isConnect() {
		return ftpClient.isConnected();
	}

	/**
	 * 列出FTP指定文件夹下下所有文件.
	 *
	 * @param remotePath
	 *            ftp文件夹路径
	 * @return FTPFile集合
	 * @throws IOException
	 */
	public List<FTPFile> listFiles(String remotePath) throws IOException {
		List<FTPFile> list = new ArrayList<FTPFile>();
		// 获取文件
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] files = ftpClient.listFiles();
		// 遍历并且添加到集合
		Collections.addAll(list, files);
		return list;
	}

	/**
	 * 下载整个目录
	 *
	 * @param remotePath
	 *            FTP目录
	 * @param fileName
	 *            需要下载的文件名
	 * @param localPath
	 *            本地目录
	 * @return Result
	 * @throws IOException
	 */
	public boolean downloadFile(String remotePath, String fileName, String localPath) throws IOException {
		boolean result = false;
		// 初始化FTP当前目录
		currentPath = remotePath;
		// 更改FTP目录
		ftpClient.changeWorkingDirectory(remotePath);
		// 得到FTP当前目录下所有文件
		FTPFile[] ftpFiles = ftpClient.listFiles();
		// 在本地创建对应文件夹目录
		File localFolder = new File(localPath);
		if (!localFolder.exists()) {
			localFolder.mkdirs();
		}
		// 循环遍历,找到匹配的文件
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.getName().equals(fileName)) {
				// 下载单个文件
				result = downloadSingle(new File(localPath + "/" + ftpFile.getName()), ftpFile);
			}
		}
		return result;
	}

	/**
	 * 下载整个目录
	 *
	 * @param remotePath
	 *            FTP目录
	 * @param localPath
	 *            本地目录
	 * @return Result 成功下载的文件数量
	 * @throws IOException
	 */
	public int downloadFolder(String remotePath, String localPath) throws IOException {
		// 下载的数量
		int fileCount = 0;
		// 初始化FTP当前目录
		currentPath = remotePath;
		// 更改FTP目录
		ftpClient.changeWorkingDirectory(remotePath);
		// 得到FTP当前目录下所有文件
		FTPFile[] ftpFiles = ftpClient.listFiles();
		// 在本地创建对应文件夹目录
		localPath = localPath + "/" + remotePath.substring(remotePath.lastIndexOf("/"));
		File localFolder = new File(localPath);
		if (!localFolder.exists()) {
			localFolder.mkdirs();
		}
		// 循环遍历
		for (FTPFile ftpFile : ftpFiles) {
			if (!ftpFile.getName().equals("..") && !ftpFile.getName().equals(".")) {
				if (ftpFile.isDirectory()) {
					// 下载文件夹
					int count = downloadFolder(currentPath + "/" + ftpFile.getName(), localPath);
					fileCount += count;
				} else if (ftpFile.isFile()) {
					// 下载单个文件
					boolean flag = downloadSingle(new File(localPath + "/" + ftpFile.getName()), ftpFile);
					if (flag) {
						fileCount++;
					}
				}
			}
		}
		return fileCount;
	}

	/**
	 * 下载单个文件,此时ftpFile必须在ftp工作目录下
	 *
	 * @param localFile
	 *            本地目录
	 * @param ftpFile
	 *            FTP文件
	 * @return true下载成功, false下载失败
	 * @throws IOException
	 */
	private boolean downloadSingle(File localFile, FTPFile ftpFile) throws IOException {
		boolean flag;
		// 创建输出流
		OutputStream outputStream = new FileOutputStream(localFile);
		// 下载单个文件
		flag = ftpClient.retrieveFile(ftpFile.getName(), outputStream);
		// 关闭文件流
		outputStream.close();
		return flag;
	}

	/**
	 * 上传.
	 *
	 * @param localFilePath
	 *            需要上传的本地文件路径
	 * @param remotePath
	 *            FTP目录
	 * @return 上传结果
	 * @throws IOException
	 */
	public boolean uploadFile(String localFilePath, String remotePath) throws IOException {
		boolean flag = false;
		// 初始化FTP当前目录
		currentPath = remotePath;
		// 二进制文件支持
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		// 设置模式
		ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
		// 改变FTP目录
		boolean succ = ftpClient.changeWorkingDirectory(currentPath);
		if (!succ) {
			createFolder(currentPath);
			if (!ftpClient.changeWorkingDirectory(currentPath)) {
				throw new IOException("FTP目录不存在");
			}
		}
		File localFile = new File(localFilePath);
		if (localFile.exists() && localFile.isFile()) {
			flag = uploadingSingle(localFile);
		}
		// 返回值
		return flag;
	}

	/**
	 * 上传.
	 *
	 * @param localFolderPath
	 *            需要上传的本地文件夹路径
	 * @param remotePath
	 *            FTP目录
	 * @return 上传结果
	 * @throws IOException
	 */
	public int uploadFolder(String localFolderPath, String remotePath) throws IOException {
		//
		int count = 0;
		boolean flag = false;
		// 初始化FTP当前目录
		currentPath = remotePath;
		// 二进制文件支持
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		// 设置模式
		ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
		// 改变FTP目录
		ftpClient.changeWorkingDirectory(currentPath);
		File localFolder = new File(localFolderPath);
		if (localFolder.exists() && localFolder.isDirectory()) {
			// 先在ftp上创建对应的文件夹
			String ftpFolder = remotePath + "/" + localFolder.getName();
			createFolder(ftpFolder);
			// 改变FTP目录
			ftpClient.changeWorkingDirectory(ftpFolder);
			// 遍历文件夹
			File[] files = localFolder.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					// 如果是文件夹
					int result = uploadFolder(file.getAbsolutePath(), ftpFolder + "/" + file.getName());
					count += result;
				} else if (file.isFile()) {
					flag = uploadingSingle(file);
					if (flag) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * 上传单个文件.
	 *
	 * @param localFile
	 *            本地文件
	 * @return true上传成功, false上传失败
	 * @throws IOException
	 */
	private boolean uploadingSingle(File localFile) throws IOException {
		boolean flag;
		// 创建输入流
		InputStream inputStream = new FileInputStream(localFile);
		// 上传单个文件
		flag = ftpClient.storeFile(localFile.getName(), inputStream);
		// 关闭文件流
		inputStream.close();
		return flag;
	}

	/**
	 * 创建文件夹
	 *
	 * @param path
	 *            文件夹名
	 * @return
	 */

	public boolean createFolder(String path) {
		boolean result = false;
		try {
			result = ftpClient.makeDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean createMultiFolder(String path) {
		boolean bool = false;
		try {
			String[] dirs = path.split("/");
			ftpClient.changeWorkingDirectory("/");
			// 按顺序检查目录是否存在，不存在则创建目录
			for (String dir : dirs) {
				if (dir.isEmpty()) continue;
				if (!ftpClient.changeWorkingDirectory(dir)) {
					if (!ftpClient.makeDirectory(dir)) {
						return false;
					}
					if (!ftpClient.changeWorkingDirectory(dir)) {
						return false;
					}
				}
			}
			bool = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bool;
	}
	
}