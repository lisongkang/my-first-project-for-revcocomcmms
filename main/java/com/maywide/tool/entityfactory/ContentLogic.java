package com.maywide.tool.entityfactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

public class ContentLogic {
	public static final String RN = "\r\n";
	public static final String TAB_T = "\t";

	public static String getTableContent(String tableName, String dbURL,
			String username, String password) {
		String content = "";
		if ((tableName != null) && (!tableName.equals(""))) {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			DatabaseMetaData dbMetaData = null;  
			ResultSet resultSet = null;
			try {
				conn = DbUtils.getConnection(dbURL, username, password);
				dbMetaData = conn.getMetaData();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from " + tableName);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				String indexStr = DbUtils.getIndexStr(tableName, dbURL,
						username, password);

				String pkStr = DbUtils.getPkStr(tableName, dbURL, username,
						password);
				boolean isEmbeddedId = pkStr.split(";").length > 1;
				

				for (int i = 1; i < columnCount + 1; i++) {
					String colName = rsmd.getColumnName(i);
					String colName2 = rsmd.getColumnName(i);
					String colType = rsmd.getColumnTypeName(i);
					String type = rsmd.getColumnTypeName(i);
					String ss = rsmd.getCatalogName(i);
					resultSet = dbMetaData.getColumns(
							rsmd.getCatalogName(i), rsmd.getSchemaName(i),
							rsmd.getTableName(i), rsmd.getColumnName(i));
					String remarks = rsmd.getColumnName(i);//如果列没有备注，默认为列名称
					while (null != resultSet &&resultSet.next()) {
						if(StringUtils.isNotBlank(resultSet.getString("REMARKS")))
							remarks = resultSet.getString("REMARKS");
					}
					int isNullable = rsmd.isNullable(i);
					int colLength = rsmd.getColumnDisplaySize(i);
					int precision = rsmd.getPrecision(i);
					int scale = rsmd.getScale(i);

					if (indexStr.indexOf(colName2.toUpperCase()) != -1) {
						colName = "index_" + colName;
					}

					if (pkStr.indexOf(colName2) != -1) {
						colName = "pk_" + colName;
						if (isEmbeddedId) {
							colName = "pk_" + colName;
						}
					}

					if ((colType.equalsIgnoreCase("INTEGER"))
							|| (colType.equalsIgnoreCase("int"))) {
						colType = "Long";
					} else if ((colType.equalsIgnoreCase("NUMBER"))
							&& (scale == 0)) {
						colType = "Long";
					} else if ((colType.equalsIgnoreCase("NUMBER"))
							&& (scale != 0)) {
						colType = "Double";
					} else if ((colType.equalsIgnoreCase("VARCHAR"))
							|| ((colType.equalsIgnoreCase("VARCHAR2") | colType
									.equalsIgnoreCase("CHAR")))
							|| (colType.equalsIgnoreCase("LONG VARCHAR"))) {
						colType = "String";
					} else if (colType.equalsIgnoreCase("BIGINT")) {
						colType = "Long";
					} else if (colType.equalsIgnoreCase("FLOAT")) {
						colType = "Double";
					} else if (colType.equalsIgnoreCase("CLOB")) {
						colType = "String";
					} else if ((colType.equalsIgnoreCase("date"))
							|| (colType.equalsIgnoreCase("timestamp"))) {
						colType = "java.util.Date";
						type = "Date";
					} else {
						throw new Exception(colType + "类型还没考虑，请联系管理员");
					}

					if (i == columnCount)
						content = content + colName.toLowerCase() + ":"
								+ colType + ":" + type + ":" + isNullable + ":"
								+ colLength + ":" + precision + ":" + scale + ":" + remarks;
					else
						content = content + colName.toLowerCase() + ":"
								+ colType + ":" + type + ":" + isNullable + ":"
								+ colLength + ":" + precision + ":" + scale + ":" + remarks
								+ "\r\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtils.closeAll(conn, stmt, rs);
			}
		}
		return content;
	}

	public static String genEntityContent(String tableName, String dbURL,
			String username, String password) throws Exception {
		String content = getTableContent(tableName, dbURL, username, password);
		String[] arr = content.split("\r\n");
		StringBuilder entityAttributeStr = new StringBuilder("");
		StringBuilder getterSetterStr = new StringBuilder("");
		StringBuilder entityAttributeStrId = new StringBuilder("");
		StringBuilder getterSetterStrId = new StringBuilder("");
		StringBuilder embeddedIdStr = new StringBuilder("");
		boolean hasEmbeddedId = false;
		for (int i = 0; i < arr.length; i++) {
			String[] att = arr[i].split(":");
			String indexLength = ", unique = false";
			String colLength = "";
			String numberStr = "";
			String nullable = "";
			String attName = att[0];
			String tableAttName = att[0];
			if (att[0].startsWith("pk_")) {
				attName = "id";
				tableAttName = tableAttName.substring(3);
				if (att[0].startsWith("pk_pk_")) {
					tableAttName = tableAttName.substring(3);

					embeddedIdStr.append(";" + tableAttName);

					if (att[0].startsWith("pk_pk_index_")) {
						indexLength = ", unique = true";
						tableAttName = tableAttName.substring(6);
					}
					if ("Date".equals(att[2])) {
						getterSetterStr
								.append("@Temporal(TemporalType.DATE)\r\n");
					}

					if ("String".equals(att[1])) {
						colLength = ", length = " + att[4];
					}
					if ("NUMBER".equals(att[2])) {
						numberStr = ", precision = " + att[5] + ", scale = "
								+ att[6];
					}
					if ("1".equals(att[3]))
						nullable = "true";
					else {
						nullable = "false";
					}
					String embeddedAttName = getAttrName(tableAttName);
					entityAttributeStrId.append("\tprivate " + att[1] + " "
							+ embeddedAttName + ";" + "\r\n");

					if ("Date".equals(att[2])) {
						getterSetterStrId
								.append("\t@Temporal(TemporalType.DATE)\r\n");
					}

					getterSetterStrId.append("\t@Column(name = \""
							+ tableAttName.toUpperCase() + "\", nullable = "
							+ nullable + indexLength
							+ ", insertable = true, updatable = true "
							+ colLength + numberStr + ")" + "\r\n");
					getterSetterStrId.append("\tpublic " + att[1] + " "
							+ getGetter(embeddedAttName) + "{" + "\r\n");
					getterSetterStrId.append("\t\treturn " + attName + ";"
							+ "\r\n" + "\t" + "}" + "\r\n");
					getterSetterStrId.append("\tpublic void "
							+ getSetter(embeddedAttName) + "(" + att[1] + " "
							+ embeddedAttName + "){" + "\r\n");
					getterSetterStrId.append("\t\tthis." + embeddedAttName
							+ " = " + embeddedAttName + ";" + "\r\n" + "\t"
							+ "}" + "\r\n");

					if (hasEmbeddedId) {
						continue;
					}
					att[1] = (getClassName(tableName) + "Id");
					hasEmbeddedId = true;
				}
				if (att[0].startsWith("pk_index_")) {
					indexLength = ", unique = true";
					tableAttName = tableAttName.substring(6);
				}
			} else {
				if (att[0].startsWith("index_")) {
					indexLength = ", unique = true";
					tableAttName = tableAttName.substring(6);
				}
				attName = getAttrName(tableAttName);
			}
			entityAttributeStr.append("\t@MetaData(value = \""+att[7]+"\")\r\n\t@EntityAutoCode\r\n");
			entityAttributeStr.append("\tprivate " + att[1] + " " + attName
					+ ";" + "\r\n\r\n");
			if (att[0].startsWith("pk_")) {
				if (att[0].startsWith("pk_pk_")) {
					getterSetterStr.append("\t@EmbeddedId\r\n");
				} else {
					String serialName = "\"SEQ_" + tableName.toUpperCase()
							+ "_" + tableAttName.toUpperCase() + "\"";
					getterSetterStr.append("\t@Id\r\n");
					getterSetterStr
							.append("\t@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "
									+ serialName + ")" + "\r\n");
					getterSetterStr.append("\t@SequenceGenerator(name = "
							+ serialName + ", sequenceName = " + serialName
							+ ", allocationSize = 1)" + "\r\n");
				}
			}

			if ("Date".equals(att[2])) {
				getterSetterStr.append("\t@Temporal(TemporalType.DATE)\r\n");
			}

			if ("String".equals(att[1])) {
				colLength = ", length = " + att[4];
			}
			if ("NUMBER".equals(att[2])) {
				numberStr = ", precision = " + att[5] + ", scale = " + att[6];
			}
			if ("1".equals(att[3]))
				nullable = "true";
			else {
				nullable = "false";
			}

			if (!att[0].startsWith("pk_pk_")) {
				getterSetterStr.append("\t@Column(name = \""
						+ tableAttName.toUpperCase() + "\", nullable = "
						+ nullable + indexLength
						+ ", insertable = true, updatable = true " + colLength
						+ numberStr + ")" + "\r\n");
			}

			getterSetterStr.append("\tpublic " + att[1] + " "
					+ getGetter(attName) + "{" + "\r\n");
			getterSetterStr.append("\t\treturn " + attName + ";" + "\r\n"
					+ "\t" + "}" + "\r\n");
			getterSetterStr.append("\tpublic void " + getSetter(attName) + "("
					+ att[1] + " " + attName + "){" + "\r\n");
			getterSetterStr.append("\t\tthis." + attName + " = " + attName
					+ ";" + "\r\n" + "\t" + "}" + "\r\n");
		}
		String classStr = "package com.maywide.entity;\r\n\r\nimport javax.persistence.*;\r\n";
		classStr += "import org.hibernate.annotations.Cache;\r\nimport org.hibernate.annotations.CacheConcurrencyStrategy;\r\n";
		classStr += "import org.springframework.data.domain.Persistable;\r\n";
		classStr += "import com.maywide.core.annotation.MetaData;\r\nimport com.maywide.core.entity.PersistableEntity;\r\n";
		classStr += "import com.maywide.core.entity.annotation.EntityAutoCode;\r\nimport javax.persistence.Transient;\r\n\r\n";

		classStr = classStr + "@Entity\r\n@Table(name = \""
				+ tableName.toUpperCase() + "\")" + "\r\n"
				+ "@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)" + "\r\n";
		tableName = tableName.toLowerCase();
		classStr = classStr + "public class " + getClassName(tableName)
				+ " extends PersistableEntity<Long> implements Persistable<Long> ";
		classStr = classStr + "{\r\n\r\n";
		return classStr + entityAttributeStr.toString() + "\r\n"
				+ "\t@Override\r\n\t@Transient\r\n\tpublic String getDisplay() {\r\n\t\treturn null;\r\n\t}\r\n\r\n"
				+ getterSetterStr.toString() + "}" + "\r\n" + "?"
				+ entityAttributeStrId.toString() + "\r\n"
				+ getterSetterStrId.toString() + "}" + "\r\n" + "?"
				+ embeddedIdStr.toString();
	}

	public static String replace_(String name) {
		while (name.indexOf("_") != -1) {
			int index = name.indexOf("_");
			name = name.substring(0, index)
					+ name.substring(index + 1, index + 2).toUpperCase()
					+ name.substring(index + 2);
		}
		return name;
	}

	public static String getClassName(String tableName) {
		tableName = replace_(tableName);
		return tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
	}

	public static String getAttrName(String attr) {
		attr = replace_(attr);
		return attr.substring(0, 1).toLowerCase() + attr.substring(1);
	}

	public static String getIdClassStr(String tableName) {
		StringBuilder idClassStr = new StringBuilder("package entity;\r\n\r\n");
		idClassStr.append("import javax.persistence.*;\r\n\r\n");
		idClassStr.append("@Embeddable\r\n");
		idClassStr.append("@SuppressWarnings(\"serial\")\r\n");
		idClassStr.append("public class " + getClassName(tableName) + "Id"
				+ " implements java.io.Serializable");
		idClassStr.append("{\r\n\r\n");
		return idClassStr.toString();
	}

	public static String getGetter(String atts) {
		return "get" + atts.substring(0, 1).toUpperCase() + atts.substring(1)
				+ "()";
	}

	public static String getSetter(String atts) {
		return "set" + atts.substring(0, 1).toUpperCase() + atts.substring(1);
	}

	public static String genEqualStr(String objName, String EmbeddedIdStr) {
		StringBuilder equalStr = new StringBuilder(
				"\tpublic boolean equals(Object other) {\r\n");

		equalStr.append("\t\tif ((this == other))\r\n");
		equalStr.append("\t\t\treturn true;\r\n");
		equalStr.append("\t\tif ((other == null))\r\n");
		equalStr.append("\t\t\treturn false;\r\n");
		equalStr.append("\t\tif (!(other instanceof " + objName + "))" + "\r\n");
		equalStr.append("\t\t\treturn false;\r\n");
		equalStr.append("\t\t" + objName + " castOther = (" + objName
				+ ") other;" + "\r\n");
		equalStr.append("\t\treturn ");
		String[] atts = EmbeddedIdStr.split(";");
		for (int i = 0; i < atts.length; i++) {
			String getterAtts = getGetter(atts[i]);
			equalStr.append("((this." + getterAtts + " == castOther."
					+ getterAtts + ") || (this." + getterAtts
					+ " != null&& castOther." + getterAtts
					+ " != null && this." + getterAtts + ".equals(castOther."
					+ getterAtts + ")))");
			equalStr.append(" && ");
		}
		return equalStr.toString().substring(0,
				equalStr.toString().length() - 4)
				+ ";" + "\r\n" + "\t" + "}";
	}

	public static String genHashCodeStr(String EmbeddedIdStr) {
		String hashCodeStr = "\tpublic int hashCode() {\r\n";
		hashCodeStr = hashCodeStr + "\t\tint result = 17;";
		String[] atts = EmbeddedIdStr.split(";");
		for (int i = 0; i < atts.length; i++) {
			String getterAtts = getGetter(atts[i]);
			hashCodeStr = hashCodeStr + "\t\tresult = 37 * result + ("
					+ getterAtts + " == null ? 0 : this." + getterAtts
					+ ".hashCode());" + "\r\n";
		}
		hashCodeStr = hashCodeStr + "\t\treturn result;\r\n\t}\r\n";
		return hashCodeStr;
	}
}