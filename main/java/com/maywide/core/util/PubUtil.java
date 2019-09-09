package com.maywide.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.maywide.core.cons.Separator;

public class PubUtil {
	
	private PubUtil() {
	}

	/**
	 * 判断是否为数字 可以为+ -开头 可以包含小数点
	 * 
	 * @param temp
	 * @return
	 */
	static public boolean IsNumeric(String temp) {
		// return temp.matches("^[+-]?\\d*[.]?\\d*$");
		return temp.matches("^[+-]?((\\d+[.]?\\d*)|(\\d*[.]?\\d+))$");
	}

	/**
	 * 判断是否为数字 可以为+ -开头 不能包含小数点
	 * 
	 * @param temp
	 * @return
	 */
	static public boolean IsInt(String temp) {
		// return temp.matches("^[+-]?\\d*$");
		return temp.matches("^[+-]?\\d+$");
	}

	/**
	 * 判断是否为数字 不能以+ -开头 可包含小数点
	 * 
	 * @param temp
	 * @return
	 */
	static public boolean IsUnsign(String temp) {
		// return temp.matches("^\\d*[.]?\\d*$");
		return temp.matches("^((\\d+[.]?\\d*)|(\\d*[.]?\\d+))$");
	}

	/**
	 * 将输入字符串中的分隔符替换为新的分隔符, 返回替换后的字符串. <br>
	 * <b>注意:</b> 该方法仅要求<i>替换后的字符串</i>与<i>输入字符串</i> 用各自的分隔符分割后<i>具有相同的字符串数组</i>.
	 * 
	 * @param instr
	 *            输入字符串
	 * @param inSeq
	 *            原分隔符
	 * @param outSeq
	 *            新分隔符
	 * @return 替换后的分隔符
	 */
	public static String replaceAll(final String instr, final String inSeq,
			final String outSeq) {
		String[] aa = PubUtil.split(instr, inSeq);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < aa.length; i++) {
			sb.append(aa[i]).append(outSeq);
		}
		return sb.toString();
	}

	/**
	 * 左补零
	 * 
	 * @deprecated 请使用<code>org.apache.commons.lang.StringUtils.leftPad(instr, len, padStr)</code>替代
	 * @param instr
	 *            被补字符串
	 * @param len
	 *            补零后总长度
	 * @return
	 */
	static public String fillZero(String instr, int len) {
		if (instr == null) {
			instr = "";
		}
		String head = "";
		for (int i = 0; i < len - instr.length(); i++) {
			head = head.concat("0");
		}
		return head + instr;
	}

	/**
	 * 在指写字符串中补到指定长度
	 * 
	 * @param str
	 *            待补字符串
	 * @param len
	 *            指定长度
	 * @param flag
	 *            向左补还是向右补 0为左,1为右
	 * @param padStr
	 *            补加的字符,如果为null时则默认为空格
	 * @return
	 */
	static public String stringPad(String str, int len, String flag,
			String padStr) {
		if (str == null || str.length() <= 0)
			str = " ";
		String fill = "";
		for (int i = 0; i < len - str.getBytes().length; i++) {
			if (padStr == null) {
				fill = fill.concat(" ");
			} else {
				fill = fill.concat(padStr);
			}
		}
		if ("0".equals(flag)) {
			return fill + str;
		} else {
			return str + fill;
		}
	}

	/**
	 * 参数形式：a1,a2,a3, 返回类型：'a1','a2','a3'
	 * 
	 * @param para
	 *            String
	 * @param para
	 *            splitStr 传入参数中的分隔符号
	 * @return String
	 */
	public static String getSqlIn(String para, String splitStr) {
		StringBuffer result = new StringBuffer();
		if (null == para || "".equals(para)) {
			return null;
		}
		String[] array = para.split(splitStr);
		for (int i = 0; i < array.length; i++) {
			result.append("'");
			result.append(array[i]);
			result.append("',");
		}
		// 去掉结尾的","
		if (!"".equals(result)) {
			result = new StringBuffer(result.substring(0, result.length() - 1));
		}
		return result.toString();
	}

	/**
	 * 根据字符串数组的内容匹配另一个字符串 字符串数组内如果为* 返回TRUE 匹配成功返回TRUE 匹配不成功返回FALSE;
	 * 
	 * @param place
	 *            String[]
	 * @param wayID
	 *            String
	 * @return boolean
	 */
	public static boolean getEqualsValue(String[] value, String str) {

		boolean flag = false;
		for (int i = 0; i < value.length; i++) {
			int strLength = str.length();
			int valuelength = value[i].length();
			if (value[i].equals(Separator.STAR)) {
				flag = true;
				return flag;
			}
			if (strLength >= valuelength) {
				if (str.substring(0, valuelength).equals(value[i])) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 分割字符串
	 * 
	 * @param instr
	 *            待分割字符串
	 * @param regex
	 *            分隔符(不支持正则表达式)
	 * @return String[] 分割后的字符串数组
	 */
	public static String[] split(String instr, String regex) {
		if (regex == null || regex.length() == 0) {
			return new String[0];
		}
		if (instr == null || instr.length() == 0) {
			return new String[0];
		}
		int reglen = regex.length();

		if (!instr.endsWith(regex)) {
			instr += regex;
		}

		int start = 0;
		int len = 0;
		while ((start = instr.indexOf(regex, start)) >= 0) {
			start += reglen;
			len++;
		}

		String[] aa = new String[len];
		int pre = 0;
		start = 0;
		len = 0;
		while ((start = instr.indexOf(regex, start)) >= 0) {
			if (len == 0) {
				aa[len] = instr.substring(pre, start);
			} else {
				aa[len] = instr.substring(pre + reglen, start);
			}
			pre = start;
			start += reglen;
			len++;
		}
		return aa;
	}

	/**
	 * 判断value中第posit位的值是否为1
	 * 
	 * @param value
	 * @param posit
	 *            （从0开始）
	 * 
	 * @return 如果该位置的值为1，返回false；反之，返回true
	 */
	public static boolean checkStringIndex(String value, int posit) {
		boolean result = false;
		if (value.length() < posit) {
			return result;
		}
		char[] flagArray = value.toCharArray();
		result = flagArray[posit] == '1' ? false : true;
		return result;
	}

	/**
	 * 判断value中第posit位的值是否为1
	 * 
	 * @param value
	 * 
	 * @param posit
	 *            （从1开始）
	 * 
	 * @return 如果该位置的值为1，返回true；反之，返回false
	 */
	public static boolean checkStrPos(String str, int pos) throws Exception {
		boolean result = false;
		if (pos - 1 < 0) {
			throw new Exception("检查字符串指定位是否为1时,超出字符串的起始范围!");
		}
		if (pos - 1 > str.length()) {
			throw new Exception("检查字符串指定位是否为1时,超出字符串的终止范围!");
		}
		char[] flagArray = str.toCharArray();
		result = flagArray[pos - 1] == '1' ? true : false;
		return result;
	}

	/**
	 * 取出字符串str中第beginlen位到第endlen位的字符串 注释:中文不能分割成半个
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param beginlen
	 *            起始长度(从0开始)
	 * @param endlen
	 *            终止长度(不包括此位置)
	 * @return str
	 */
	public static String getLimitLengthString(String str, int beginlen,
			int endlen) {
		// int counterOfDoubleByte;
		// byte[] b;
		//
		// counterOfDoubleByte = 0;
		// b = str.getBytes();
		// if (b.length <= beginlen + endlen) {
		// return new String(b, beginlen, b.length - beginlen);
		// }
		// for (int i = 0; i < endlen - beginlen; i++) {
		// if (b[i] < 0)
		// counterOfDoubleByte++;
		// }
		//
		// if (counterOfDoubleByte % 2 == 0)
		// return new String(b, beginlen, endlen);
		// else
		// return new String(b, beginlen, endlen - 1);
		return str.substring(beginlen, endlen);
	}

	/**
	 * <B>按规定字节数分拆字符串(采用本地编码)</B> <br/>
	 * <LI>主要针对数据库字段操作,汉字判定长度为2
	 * <LI>如果分拆长度为1,则可能出现乱码问题
	 * 
	 * @param orig
	 *            待分拆字符串
	 * @param length
	 *            分拆长度
	 * @return String[]
	 */
	public static String[] splitByLength(String orig, int length) {
		// if (length <= 0 || orig == null)
		// return null;
		// byte[] bytes = orig.getBytes();
		// ArrayList<String> pieces = new ArrayList<String>();
		//
		// int offset = 0;
		// while (offset < bytes.length) {
		// int counter = 0;
		// boolean isDoubleBytes = false;
		// while (counter < length && counter + offset < bytes.length) {
		// if (bytes[offset + counter++] < 0) {
		// isDoubleBytes = !isDoubleBytes;
		// }
		// }
		// if (isDoubleBytes && counter > 1) {
		// counter--;
		// }
		// pieces.add(new String(bytes, offset, counter));
		// offset += counter;
		// }
		// return (String[]) pieces.toArray(new String[0]);

		if (length <= 0 || orig == null)
			return null;
		byte[] bytes = orig.getBytes();
		ArrayList<String> pieces = new ArrayList<String>();

		for (int offset = 0; offset < bytes.length;) {
			int count = (offset + length < bytes.length)
					? length
					: bytes.length - offset;
			boolean isDoubleBytes = false;
			for (int i = offset + count - 1; i > -1 && bytes[i] < 0; i--)
				isDoubleBytes = !isDoubleBytes;
			if (isDoubleBytes && count > 1)
				count--;
			pieces.add(new String(bytes, offset, count));
			offset += count;
		}

		return pieces.toArray(new String[0]);
	}

	/**
	 * <B>按规定字节数分拆字符串,其实可以设计以哪种编码进行拆分,又以哪种编码进行组合,如果不指定则以操作系统默认编码</B> <br/>
	 * <LI>主要针对数据库字段操作,汉字判定长度为2
	 * <LI>如果分拆长度为1,则可能出现乱码问题
	 * 
	 * @param orig
	 *            待分拆字符串
	 * @param length
	 *            分拆长度
	 * @param original_charsetName
	 *            拆分字符串的编码方式
	 * @param target_charsetName
	 *            组合字符串的编码方式
	 * @return String[]
	 */
	public static String[] splitByLength(String orig, int length,
			String original_charsetName, String target_charsetName)
			throws Exception {
		// if (length <= 0 || orig == null)
		// return null;
		// byte[] bytes = null;
		// if (original_charsetName == null || original_charsetName.length() <=
		// 0) {
		// bytes = orig.getBytes();
		// } else {
		// bytes = orig.getBytes(original_charsetName);
		// }
		// ArrayList<String> pieces = new ArrayList<String>();
		//
		// int offset = 0;
		// while (offset < bytes.length) {
		// int counter = 0;
		// boolean isDoubleBytes = false;
		// while (counter < length && counter + offset < bytes.length) {
		// if (bytes[offset + counter++] < 0) {
		// isDoubleBytes = !isDoubleBytes;
		// }
		// }
		// if (isDoubleBytes && counter > 1) {
		// counter--;
		// }
		// if (target_charsetName == null || target_charsetName.length() <= 0) {
		// pieces.add(new String(bytes, offset, counter));
		// } else {
		// pieces.add(new String(bytes, offset, counter,
		// target_charsetName));
		// }
		// offset += counter;
		// }
		// return (String[]) pieces.toArray(new String[0]);

		if (length <= 0 || orig == null)
			return null;
		byte[] bytes = null;
		if (original_charsetName == null || original_charsetName.length() == 0)
			bytes = orig.getBytes();
		else
			bytes = orig.getBytes(original_charsetName);
		ArrayList<String> pieces = new ArrayList<String>();

		for (int offset = 0; offset < bytes.length;) {
			int count = (offset + length < bytes.length)
					? length
					: bytes.length - offset;
			boolean isDoubleBytes = false;
			for (int i = offset + count - 1; i > -1 && bytes[i] < 0; i--)
				isDoubleBytes = !isDoubleBytes;
			if (isDoubleBytes && count > 1)
				count--;
			if (target_charsetName == null || target_charsetName.length() == 0)
				pieces.add(new String(bytes, offset, count));
			else
				pieces
						.add(new String(bytes, offset, count,
								target_charsetName));
			offset += count;
		}

		return (String[]) pieces.toArray(new String[0]);
	}

	/**
	 * 替换字符串中指定位置到字符
	 * 
	 * @param oldstring
	 *            被替换的字符串
	 * @param replacestring
	 *            替换的字符串
	 * @param index
	 *            被替换的字符串中的位置(从0开始)
	 * @return
	 */
	public static String replaceByIndex(String oldstring, String replacestring,
			int index) {
		StringBuffer newstring = new StringBuffer();
		for (int i = 0; i < oldstring.length(); i++) {
			if (i == index) {
				newstring.append(replacestring);
				continue;
			}
			newstring.append(oldstring.substring(i, i + 1));
		}
		return newstring.toString();
	}

	/**
	 * 转换double小数位数
	 * 
	 * @param doub
	 *            需要转换的数字
	 * @param num
	 *            需要精确到小数位后多少位 如为0则原样返回doub
	 * @return
	 */
	public static double DoubleFormat(double doub, int num) {
		if (num == 0) {
			return doub;
		}
		String formatstr = "#######.";
		for (int i = 0; i < num; i++) {
			formatstr = formatstr + "#";
		}
		DecimalFormat df = new DecimalFormat(formatstr);
		return Double.valueOf(df.format(doub)).doubleValue();
	}

	/**
	 * 将传入的组串信息String[]组成一个字符串
	 * 
	 * @deprecated 请使用<code>org.apache.commons.lang.StringUtils.join(datas, separator)</code>替代
	 * @param datas
	 *            String[]
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String makeDatas(String[] datas, String separator) {
		if (null == datas) {
			return "";
		} else {
			StringBuffer strdata = new StringBuffer(255);
			for (int i = 0; i < datas.length; i++) {
				strdata.append(datas[i]).append(separator);
			}
			return strdata.toString();
		}
	}

	/**
	 * 将Double转成String(小数点第二位之后四舍五进,小数点前只能最长15位)
	 * 
	 * @param d
	 * @return
	 * @author zhengshumin date: 2007-1-4
	 */
	public static String doubleToString(Double d) {
		return d == null ? "0" : new DecimalFormat("###############.##")
				.format(d);
	}

	/**
	 * 组AllDatas串，用＂~＂分隔
	 * 
	 * @deprecated 请使用<code>org.apache.commons.lang.StringUtils.join(datas, Separator.WAVE)</code>替代
	 * 
	 * @param datas
	 * @return
	 */
	public static String makeAllDatasString(String[] datas) {
		return makeDatas(datas, Separator.WAVE);
	}

	/**
	 * 判断字符是否为空或空字符
	 * 
	 * @deprecated 请使用<code>org.apache.commons.lang.StringUtils.isEmpty(str)</code>替代
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals("");
	}

	/**
	 * 判断一个字符串是否包含另一个字符串,对存在分隔符的情况做特殊处理. <br>
	 * 
	 * @param content
	 *            要判断的字符串
	 * @param sub
	 *            子串
	 * @param seperator
	 *            分隔符, 若为<tt>null</tt>则执行<tt>content.contains(sub)</tt>,
	 *            否则执行 <tt>(seperator + content + seperator).contains(sub)</tt>
	 * @return
	 */
	public static boolean extendContains(String content, String sub,
			String seperator) {

		return seperator == null
				? content.contains(sub)
				: (seperator + content + seperator).contains(sub);
	}

//	/**
//	 * MD5加密
//	 * 
//	 * @param orig
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public static String md5(String orig) throws UnsupportedEncodingException {
//		return DigestUtils.md5Hex(orig.getBytes());
//	}

	/**
	 * MD5加密,使用JDK自带工具类
	 * 
	 * @param orig
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5Jdk(String orig) throws NoSuchAlgorithmException {
		byte[] defaultBytes = orig.getBytes();
		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++) {
			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		}
		return hexString.toString();
	}

	/**
	 * MD5加密,针对华为的接口 <BR>
	 * 方式:原始串后加"99991231",Md5后取前8字节
	 * 
	 * @param orig
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String md5Huawei(String original)
			throws NoSuchAlgorithmException {
		String ret = null;
		if (StringUtils.isEmpty(original)) {
			return ret;
		}

		try {
			byte[] id = original.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update("99991231".getBytes());
			byte[] buffer = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < buffer.length; i++) {
				sb.append(Integer.toHexString((int) buffer[i] & 0xff));
			}
			ret = sb.substring(0, 8);

		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 转化成全角的函数
	 * 
	 * @param input
	 * @return string 全角
	 * @author zhuyanjun
	 * @since 2008-03-14
	 */
	public static String toSBC(String input) {
		// 半角转全角：
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	/** 第一个字符转为大写 */
	public static String toTitle(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 转化成半角的函数
	 * 
	 * @param input
	 * @return string 半角
	 * @author zhuyanjun
	 * @since 2008-03-14
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

}
