package com.maywide.biz.inter.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

public class FileUploadUtil {

	/**
	 * 创建单个文件夹。
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public static void createDir(String dir) throws IOException {
	    File file = new File(dir);
	    if (file.exists()) {
	        return;
	    }
	    if (file.mkdir() == false) {
	        throw new IOException("Cannot create the directory = " + dir);
	    }
	}
	
	
	public static List<String> fileUpload(HttpServletRequest request,String path,List<String> filenames) throws Exception{
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1 * 1024 * 1024);//设置缓冲区大小，这里是1Mb 
		factory.setRepository(new File(java.lang.System
				.getProperty("java.io.tmpdir")));
		List<String> fileNames = new ArrayList<String>();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		servletFileUpload.setSizeMax(100 * 1024 * 1024);//设置最大文件尺寸，这里是100MB
		
		List<FileItem> fileItems = servletFileUpload.parseRequest(request);// 得到所有的文件
		if(null != fileItems && !fileItems.isEmpty()){
			for(FileItem item:fileItems){
				String strFileName = item.getName();
				String filePathName ="";
				if(StringUtils.isNotBlank(strFileName)){
					filePathName = getTimeSequence()+"."+getFileNameExtension(strFileName);
				}
				String diskFileName = path+filePathName;
				 File file = new File(diskFileName);
	                if(file.exists()){
	                	file.delete();
	                }
	              item.write(new File(diskFileName));
	              fileNames.add(filePathName);
			}
		}
		
		return fileNames;
	}
	
	/**
	 * 单个文件上传
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 */
	public static String fileUpload(HttpServletRequest request, String path, String fileName)
			throws Exception {
	
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(10 * 1024);//设置缓冲区大小，这里是10kb 
		factory.setRepository(new File(java.lang.System
				.getProperty("java.io.tmpdir")));// 设置临时目录  
	
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		servletFileUpload.setSizeMax(50 * 1024 * 1024);//设置最大文件尺寸，这里是50MB
	
		List<FileItem> fileItems = servletFileUpload.parseRequest(request);// 得到所有的文件
		if(null!=fileItems&&fileItems.size()>0){
            FileItem fi = (FileItem)fileItems.get(0);   
            String strFileName = fi.getName();// 获得文件名，这个文件名包括路径：   
            if (strFileName != null&&!"".endsWith(strFileName)) {  
            	if(!(null!=fileName&&!"".equals(fileName))){
            		fileName=getTimeSequence()+"."+getFileNameExtension(strFileName);
            	}
                String diskFileName = path+fileName;
                File file = new File(diskFileName);
                if(file.exists()){
                	file.delete();
                }
                fi.write(new File(diskFileName));                   
            }   
		}
		
		return fileName;
	}
	
	/**
	 * 从包含路径信息的文件全名中，获得文件的后缀
	 * 
	 * @param fullFileName
	 *            String
	 * @return String
	 */
	public static String getFileNameExtension(String fullFileName) {
		if (fullFileName == null) {
			return null;
		}
	
		int pos = fullFileName.lastIndexOf(".");
	
		if (pos != -1) {
			return fullFileName.substring(pos + 1, fullFileName.length());
		} else {
			return null;
		}
	}
	//按照上传日期自动生成文件名
	public synchronized static String getTimeSequence() {
		Date currentDate = new Date();
		int rd = (int)(Math.random()*1000);
		return currentDate.getTime() + String.valueOf(rd);
	}
	
}
