package com.maywide.biz.prd.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.core.servlet.SysConfig;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 知识库配置图片上传功能的控制类
 *
 */
@InterceptorRefs(value = { @InterceptorRef("fileUploadStack") })
public class SalePhotoFileUploadController extends ActionSupport  implements ModelDriven<Object>{
	
	private static final long serialVersionUID = -4480702698722207724L;
	private final Logger logger = LoggerFactory.getLogger(SalePhotoFileUploadController.class);
	private File salePhoto;     //图片文件
    private String salePhotoFileName;   //文件名称
    private String salePhotoContentType; // 文件格式
	 /** ModelDriven对象 */
    protected Object model = null;
   
    /**
     * 上传文件
     * @throws Exception
     */
    public void uploadFile()throws Exception {
    	JSONObject result = new JSONObject();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response=ServletActionContext.getResponse();
    	//获取限制的文件大小
    	String maxSize = request.getParameter("maxSize");
    	//获取文件限制格式
    	String limitType = request.getParameter("limitType");
    	//获取文件相对路径
    	String relativePath = request.getParameter("relativePath");
    	
    	if(!checkFileType(limitType)){
        	result.put("message", "请上传符合格式的文件");
        }else if(!checkFileMax(maxSize)){
        	result.put("message", "请上传文件小于"+maxSize+"KB");
        }else{
        	 //文件的真实目录路径
	        String  imagepath = getRootPath() + File.separator +relativePath + File.separator +  getRelativePath();  
	        //生成的图片名称
	        String  imageTrueName = createFileName() + getImageExs();
	        //数据库存储的图片路径
	        String  imagePathInSql = getRelativePath() +File.separator+imageTrueName;
	      
	        mkdirFile(imagepath);
	        
	        File imageDiskFile = new File(imagepath + File.separator + imageTrueName);
	        FileUtils.moveFile(salePhoto, imageDiskFile);
	        logger.info("Saving salepkgKnow image file to disk: {}", imageDiskFile.getAbsolutePath());
	       
	        result.put("imagepath","/"+imagePathInSql.replace("\\", "/"));
	        result.put("message", "success");
        }
    	
    	 result.put("success", true);
         response.setContentType("text/html;charset=utf-8");    
         PrintWriter out = response.getWriter();  
         out.println(result.toString());  
         out.flush();  
         out.close();  
    }
    /**
     * 上传知识库配置图片
     * @return
     * @throws Exception 
     */
	public void uploadSalekgKnowPhto() throws Exception {
        JSONObject result = new JSONObject(); 
        //判断是否为图片文件
        if(!isImageFile()){
        	result.put("message", "请上传符合格式的图片文件");
        }else if(!isLowImgMax()){
        	result.put("message", "请上传图片文件小于50KB");
        }else{
	        //知识库配置图片的真实目录路径
	        String  imagepath = getRootPath() + File.separator +"image" + File.separator +  getRelativePath();  
	        //知识库配置缩列图图片的真实目录路径
	        String  iconpath = getRootPath() + File.separator + "icon" + File.separator + getRelativePath();
	        //生成的图片名称
	        String  imageTrueName = createFileName() + getImageExs();
	        //数据库存储的图片路径
	        String  imagePathInSql = getRelativePath() +File.separator+imageTrueName;
	      
	        mkdirFileDir(imagepath, iconpath);
	        
	        File imageDiskFile = new File(imagepath + File.separator + imageTrueName);
	        File iconDiskFile = new File(iconpath + File.separator + imageTrueName);
	        
	        FileUtils.moveFile(salePhoto, imageDiskFile);
	        
	        //将图片原图安装1的比例压缩到指定文件的缩列图
	        if(getImageExs().equals(".jpg")){
	        	Thumbnails.of(imageDiskFile).scale(1.0f).toFile(iconDiskFile); 
	        }else{
	        	 //FileUtils.moveFile(imageDiskFile,iconDiskFile);
	        	FileUtils.copyFile(imageDiskFile,iconDiskFile);
	        }
	       
	        logger.info("Saving salepkgKnow image file to disk: {}", imageDiskFile.getAbsolutePath());
	        logger.info("Saving salepkgKnow icon file to disk: {}", iconDiskFile.getAbsolutePath());
	       
	        result.put("imagepath","/"+imagePathInSql.replace("\\", "/"));
	        result.put("message", "success");
        }
        
        result.put("success", true);
        HttpServletResponse response=ServletActionContext.getResponse();  
        response.setContentType("text/html;charset=utf-8");    
        PrintWriter out = response.getWriter();  
        out.println(result.toString());  
        out.flush();  
        out.close();  
    }
	/**
	 * 判断相关路径的目录是否创建，如果没有创建将创建
	 * @param imagepath
	 */
	private void mkdirFile(String imagepath){
		File imageFileDir = new File(imagepath);
		if (!imageFileDir.exists()) {
            imageFileDir.mkdirs();
        }
	}
	/**
	 * 判断相关路径的目录是否创建，如果没有创建将创建
	 * @param imagepath :知识库配置图片的真实目录路径
	 * @param iconpath  ：知识库配置缩列图图片的真实目录路径
	 */
	private void mkdirFileDir(String imagepath,String iconpath){
		File imageFileDir = new File(imagepath);
        File iconFileDir = new File(iconpath);
        if (!imageFileDir.exists()) {
            imageFileDir.mkdirs();
        }
        if(!iconFileDir.exists()){
        	iconFileDir.mkdirs();
        }
	}
	
	/**
	 * 获取项目的同级目录 Root的路径
	 * @return
	 */
	private String getRootPath(){
//		 //项目路径 例如  E:\网格系统\05代码\grid\src\main\webapp\
//	   	 String webRoot = ServletActionContext.getServletContext().getRealPath("/");     
//	     //获取项目同级的ROOT/image  E:\网格系统\05代码\grid\src\main
//	   	 String rootPath = webRoot.substring(0,webRoot.lastIndexOf(File.separator));
//	   	 rootPath = webRoot.substring(0,rootPath.lastIndexOf(File.separator));
//	   	 rootPath = rootPath+File.separator+"ROOT";
//	   	 return rootPath;
		String rootPath = SysConfig.getFileUploadPath();
		if (StringUtils.isEmpty(rootPath)) {
		   	 String webRoot = ServletActionContext.getServletContext().getRealPath("/");     
		     //获取项目同级的ROOT/image  E:\网格系统\05代码\grid\src\main
		     rootPath = webRoot.substring(0,webRoot.lastIndexOf(File.separator));
		   	 rootPath = webRoot.substring(0,rootPath.lastIndexOf(File.separator));
		   	 rootPath = rootPath+File.separator+"ROOT";
		   	 return rootPath;
		}
		return rootPath;
	}
  
	 /**
     * 使用时间_随机数_cut 创建 文件名
     * @return
     */
   private  String createFileName(){
    	StringBuilder fileName = new StringBuilder();
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String dateStr = sdf.format(date);
    	fileName.append(dateStr);
    	fileName.append("_");
    	int randNum=(int)(Math.random()*990)+10;//10到999的随机数 
    	fileName.append(randNum);
    	fileName.append("_cut");
    	return fileName.toString();
    } 
   
   /**
    * 判断文件格式规则
    * @param fileTypeRule
    * @return
    */
    private boolean checkFileType(String fileTypeRule){
    	boolean flag = false;
    	for (String ext : fileTypeRule.split(",")) {
			if(salePhotoFileName.toLowerCase().endsWith(ext)){
				flag = true;
				break;
			}
		}
    	return flag ;
    }
    
    /**
     * 判断文件大小是否满足条件
     * @param maxSize
     * @return
     */
    private boolean checkFileMax(String maxSize){
       long length = salePhoto.length();
   	   long size = length/1024;
   	   return size<=Long.parseLong(maxSize)? true:false;
    }
    /**
     * 判断文件是否为图片
     * @return
     */
    private boolean isImageFile(){
    	boolean isImage = false;
    	String[] imgExts = { ".jpg", ".jpeg",".bmp", ".png"};
    	for (String ext : imgExts) {
			if(salePhotoFileName.toLowerCase().endsWith(ext)){
				isImage = true;
				break;
			}
		}
    	
    	return isImage;
    }
    
    /**
     *判断图片大于50kB不满足条件
     * @return
     */
    private boolean isLowImgMax(){
    	long length = salePhoto.length();
    	 long size = length/1024;
    	 return size<=50? true:false;
    }
    
    /**
     * 获取文件后缀名
     * @return
     */
    private String getImageExs(){
    	return salePhotoFileName.substring(salePhotoFileName.indexOf("."));
    }
  
    /**
     * 创建相对的目录集 images/upfile/年/月 如： images/upfile/2016/7
     * @return
     */
    public static String getRelativePath(){
    	StringBuilder relativePath= new StringBuilder();
    	DateTime now = new DateTime();
    	relativePath.append("images");
    	relativePath.append(File.separator);
    	relativePath.append("upfile");
    	relativePath.append(File.separator);
    	relativePath.append(now.getYear());
    	relativePath.append(File.separator);
        relativePath.append(now.getMonthOfYear());
    	return relativePath.toString();
    }
    
	@Override
	public Object getModel() {
		return model;
	}
	
	/**
     * 用于子类方法修改设置返回的ModelDriven模型对象
     * @param model
     */
    protected void setModel(Object model) {
        this.model = model;
    }

	public File getSalePhoto() {
		return salePhoto;
	}

	public void setSalePhoto(File salePhoto) {
		this.salePhoto = salePhoto;
	}

	public String getSalePhotoFileName() {
		return salePhotoFileName;
	}

	public void setSalePhotoFileName(String salePhotoFileName) {
		this.salePhotoFileName = salePhotoFileName;
	}

	public String getSalePhotoContentType() {
		return salePhotoContentType;
	}

	public void setSalePhotoContentType(String salePhotoContentType) {
		this.salePhotoContentType = salePhotoContentType;
	}


}
