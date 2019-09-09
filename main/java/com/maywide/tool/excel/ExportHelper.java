package com.maywide.tool.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.maywide.core.util.DateUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportHelper {

	
	/**
	 * 报表导出Excel
	 */
	public void exportExcel(String fileName,String[] cols,List<String[]> rowArr, HttpServletResponse response) {
		
		response.reset();// 清空输出流   
		String defaultFileName = fileName+DateUtils.getFormatDateString(new Date(),"yyyyMMdd") + ".xls";
		
		OutputStream stream = null;
		WritableWorkbook wwb = null;
		try {
			String exportName = new String(defaultFileName.getBytes("UTF-8"),"ISO8859_1");
			response.setHeader("Content-disposition", "attachment; filename="+exportName);//设定输出文件头
			response.setContentType("application/x-msdownload");//定义输出类型
			stream = response.getOutputStream();
			wwb = Workbook.createWorkbook(stream);
			
			WritableSheet ws = wwb.createSheet(fileName, 0);
			//ws.setColumnView(2, 50);设置第3列宽度d w
			//给sheet电子版中所有的列设置默认的列的宽度;  
			ws.getSettings().setDefaultColumnWidth(15);
			//显示网格
			ws.getSettings().setShowGridLines(true);
			
			WritableFont font = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
			
			WritableCellFormat normalFormat = new WritableCellFormat(font);
			normalFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);
			//水平方向居中对齐
			normalFormat.setAlignment(Alignment.CENTRE);
            //竖直方向居中对齐
			normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			//设置自动换行;  
			normalFormat.setWrap(true); 
			normalFormat.setBackground(Colour.GRAY_25);
			
			WritableFont font2 = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);  
			WritableCellFormat normalFormat2 = new WritableCellFormat(font2);
			normalFormat2.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);
			 //水平方向居中对齐
			normalFormat2.setAlignment(Alignment.CENTRE);
            //竖直方向居中对齐
			normalFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
			//设置自动换行;  
			normalFormat2.setWrap(true); 
			
			// 创建单元格(Label)对象
			// 第一个参数指定单元格的列数、第二个参数指定单元格的行数，第三个指定写的字符串内容, 第四个参数是样式
			for (int i = 0; i < cols.length; i++) {
				ws.addCell(new Label(i, 0, cols[i], normalFormat));   
			}
			
			int r = 1;
			for (int idx=0;idx<rowArr.size();idx++) {
				for (int i = 0; i < cols.length; i++) {
					ws.addCell(new Label(i, r, rowArr.get(idx)[i], normalFormat2));  
				}
				r++;
			 }
			
			wwb.write();//写入数据
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(wwb != null) {
				try {
					wwb.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					wwb = null;
				}
			}
			if(stream != null) {
				try {
					stream.flush(); //刷新缓冲区
					stream.close(); // 关闭流
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					stream = null;
				}
			}
		}
	}
	
	
	
	public String getDefString(Object obj){
		return String.valueOf(obj==null?"":obj.toString());
	}
	
	
	
	/**
	 * 向前台输出html提示
	 * @return
	 */
	public static void responseHtml(HttpServletResponse response,String msg){
		try {
			response.getWriter().write("<html><head><script language='javascript'>alert('"+msg+"');</script></head><body>"+msg+"</body></html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
}
