package com.maywide.biz.inter.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.core.pojo.ResponseWrapper;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.survey.entity.BizPhotoList;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;

public class FileUploadServlet extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadServlet.class);
	
	private ObjectMapper jsonMapper = null;
	
	PersistentService persistentService;

	private static final long serialVersionUID = -7744625344830285257L;
	private ServletContext sc;
	private String savePath;
	private String imgDirPath;
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	public void init(ServletConfig config) {
		if (null == jsonMapper) {
            jsonMapper = new ObjectMapper();
            jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
		persistentService = SpringContextHolder.getBean(PersistentService.class);
		// 在web.xml中设置的一个初始化参数
		savePath = config.getInitParameter("savePath");
		sc = config.getServletContext();
		
		imgDirPath = SysConfig.getFileUploadPath()+ File.separator + "image";
		if (StringUtils.isEmpty(imgDirPath)) {
			imgDirPath = new File(sc.getRealPath("/")).getParent() + File.separator + "ROOT" + File.separator
					+ "image";
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<String> fileIds = new ArrayList<String>();
		
		try {
			List items = upload.parseRequest(request);
			boolean returnPath = Boolean.parseBoolean(getFormParameter(items, "returnPath"));
			String relativePath = getFormParameter(items, "relativePath");
			if (StringUtils.isBlank(relativePath)) {
				relativePath = savePath;
			}
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					System.out.println("表单参数名:" + item.getFieldName() + "，表单参数值:" + item.getString("UTF-8"));
				} else {
					if (StringUtils.isNotBlank(item.getName())) {

						if(relativePath.equals("application")){
							//小额工程多文件上传
							String fileName = getUnique();
							File tempFile = new File(item.getName());
							fileName = fileName + tempFile.getName().substring(tempFile.getName().lastIndexOf("."));
							String relativePathStr = getRelativePath(relativePath);
							String imagePathInSql = relativePathStr + File.separator + fileName;
							File saveDir = new File(imgDirPath + relativePathStr);
							if (!saveDir.exists()) {
								saveDir.mkdirs();
							}
							File file = new File(saveDir, fileName);
							item.write(file);
							request.setAttribute("upload.message", "上传文件成功！");

							if (returnPath) {
								fileIds.add(imagePathInSql.replace("\\", "/"));
							} else {
								String filePath = file.getAbsolutePath();
								BizPhotoList photoList = new BizPhotoList();
								photoList.setFilename(filePath.replace("\\","/"));
								photoList.setImagepath(imagePathInSql.replace("\\", "/"));
								//将中文文件名入库为UUID字段
								String name = tempFile.getName();
								photoList.setUuid(name);
								persistentService.save(photoList);
								fileIds.add(String.valueOf(photoList.getId()));
							}
						}else {
							File tempFile = new File(item.getName());
							// 上传文件的保存路径
							//  /usr1/tomcat6_moss/webapps/grid/

							String relativePathStr = getRelativePath(relativePath);
							String imagePathInSql = relativePathStr + File.separator + tempFile.getName();
							File saveDir = new File(imgDirPath + relativePathStr);
							if (!saveDir.exists()) {
								saveDir.mkdirs();
							}
							File file = new File(saveDir, tempFile.getName());
							item.write(file);
							request.setAttribute("upload.message", "上传文件成功！");

							if (returnPath) {
								fileIds.add(imagePathInSql.replace("\\", "/"));
							} else {
								String filePath = file.getAbsolutePath();
								BizPhotoList photoList = new BizPhotoList();
								photoList.setFilename(filePath);
								photoList.setImagepath(imagePathInSql.replace("\\", "/"));
								persistentService.save(photoList);
								fileIds.add(String.valueOf(photoList.getId()));
							}
						}
					} else {
						request.setAttribute("upload.message", "没有选择上传文件！");
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			request.setAttribute("upload.message", "上传文件失败！");
		}
		
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(jsonMapper.writeValueAsString(fileIds));
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
		
		// 返回响应报文
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setReturnInfo(returnInfo);
        String responseJSON = jsonMapper.writeValueAsString(responseWrapper);
        
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter writer = response.getWriter();
        writer.print(responseJSON);
	}

	private String getFormParameter(List<FileItem> items, String fieldName)
			throws UnsupportedEncodingException {
		if (fieldName == null) return null;
		for (FileItem fileItem : items) {
			if (fileItem.isFormField() && fieldName.equals(fileItem.getFieldName())) {
				return fileItem.getString("UTF-8");
			}
		}
		return null;
	}

	private String getUnique(){
		String idStr;
		try {
			Thread thread = Thread.currentThread();
			long id = thread.getId();
			idStr = String.valueOf(id);
			if (idStr.length() < 3) {
				StringBuffer idSb = new StringBuffer(idStr);

				int length = idSb.toString().length();
				for (int i = 0; i < 3 - length; i++) {
					idSb.append(new Random().nextInt(10));
				}
				idStr = idSb.toString();
			} else {
				idStr = idStr.substring(idStr.length() - 3, idStr.length());
			}
		} catch (Exception e) {
			idStr = "000";
		}

		String bizorderid = System.currentTimeMillis()+idStr;
		return bizorderid;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doPost(request, response);
	}

    public static String getRelativePath(String path){
    	StringBuilder relativePath= new StringBuilder();
    	DateTime now = new DateTime();
    	relativePath.append(File.separator);
    	relativePath.append("images");
    	relativePath.append(File.separator);
    	relativePath.append(path);
    	relativePath.append(File.separator);
    	relativePath.append(now.getYear());
    	relativePath.append(File.separator);
        relativePath.append(now.getMonthOfYear());
    	relativePath.append(File.separator);
        relativePath.append(now.getDayOfMonth());
    	return relativePath.toString();
    }


}
