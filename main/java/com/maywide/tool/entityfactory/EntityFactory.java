package com.maywide.tool.entityfactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.ResourceBundle;

public class EntityFactory {
	public static void genFiles(String fileName, String content) {
		RandomAccessFile mm = null;
		try {
			File dirfile = new File("");
			String path = dirfile.getAbsolutePath();
			File dirFile = new File(path + "\\target\\entity-codes\\");
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			File file = new File(path + "\\target\\entity-codes\\" + fileName + ".java");
			if (!file.exists()) {
				file.createNewFile();
			}
			mm = new RandomAccessFile(file, "rw");
			//处理中文问题
			mm.writeBytes(new String(content.getBytes("UTF-8"),"ISO8859_1"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			if (mm != null)
				try {
					mm.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
		} catch (IOException e) {
			e.printStackTrace();

			if (mm != null)
				try {
					mm.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
		} finally {
			if (mm != null)
				try {
					mm.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
		}
	}

	public static void writeFile(String tableName, String dbURL,
			String username, String password) {
		try {
			String[] entitys = ContentLogic.genEntityContent(tableName, dbURL,
					username, password).split("\\?");
			String entityContent = entitys[0];
			String entityIdContent = entitys[1];
			String fileName = ContentLogic.getClassName(tableName);
			genFiles(fileName, entityContent);
			if (entityIdContent.indexOf("private") != -1) {
				entityIdContent = ContentLogic.getIdClassStr(tableName)
						+ entityIdContent;
				fileName = fileName + "Id";
				String EmbeddedIdStr = entitys[2];
				genFiles(
						fileName,
						entityIdContent.substring(0,
								entityIdContent.lastIndexOf("}"))
								+ ContentLogic.genEqualStr(fileName,
										EmbeddedIdStr.substring(1))
								+ "\r\n"
								+ ContentLogic.genHashCodeStr(EmbeddedIdStr
										.substring(1)) + "\r\n" + "}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValueFromProperites(String properies,
			String attribute) {
		try {
			ResourceBundle myResource = ResourceBundle.getBundle("com.maywide.tool.entityfactory."+properies);
			return myResource.getString(attribute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String properies = "db";
		String dbURL = getValueFromProperites(properies, "dbURL");
		String username = getValueFromProperites(properies, "username");
		String password = getValueFromProperites(properies, "password");
		String tableName = getValueFromProperites(properies, "tableName");
		tableName = tableName.toLowerCase();
		if (tableName.indexOf(",") != -1) {
			String[] tableNames = tableName.split(",");
			for (int i = 0; i < tableNames.length; i++)
				if ((tableNames[i] != null) && (!"".equals(tableNames[i])))
					writeFile(tableNames[i], dbURL, username, password);
		} else {
			writeFile(tableName, dbURL, username, password);
		}
		System.out.println("实体生成成功，请到entity目录下查看\r\n");
	}
}