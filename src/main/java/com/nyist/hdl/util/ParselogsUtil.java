package com.nyist.hdl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 解析tomcat 日志情况
 * 
 * Parselogs这个类用来对字符串进行解析
 * 
 * @author zhangchenguang
 *
 */

public class ParselogsUtil{
	
	public ParselogsUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String[] parseString(String str) throws ParseException {
		String str1 = parseIp(str);
		String str2 = parseDate(str);
		String str3 = parseUrl(str);
		// String str4 = parseStatus(str);
		// String str5 = parseFlow(str);
		// String[] str66 = new String[]{str1,str2,str3,str4,str5};
		String[] str66 = new String[] { str1, str2, str3 };// 在这里只获取与本次项目有关的数据
		return str66;
	}

	/**
	 * 解析Ip
	 * 
	 * @param str
	 * @return
	 */
	public static String parseIp(String str) { // 对ip地址进行解析的方法
		String[] splited = str.split(" - - ");// 用指定的正则表达式进行切分，获取我们需要的字段
		return splited[0];
	}

	/**
	 * 解析并转换date
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static String parseDate(String str) throws ParseException {
		String[] splited = str.split(" - - ");// 用指定的正则表达式进行切分，获取我们需要的字段
		int index1 = splited[1].indexOf("[");
		int index2 = splited[1].indexOf("]");
		String substring = splited[1].substring(index1 + 1, index2);// 到此获取了时间字段30/May/2013:17:38:20
																	// +0800
		SimpleDateFormat simple1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);// 匹配我们给定的字符串，并将其解析成对应的时间
		SimpleDateFormat simple2 = new SimpleDateFormat("yyyyMMddHHmmss");// 匹配我们给定的字符串
		Date parse = simple1.parse(substring);
		String format = simple2.format(parse);
		return format;
	}

	/**
	 * 解析访问 URL地址
	 * 
	 * @param str
	 * @return
	 */
	public static String parseUrl(String str) { // 获取访问的url
		int index1 = str.indexOf("]");
		int index2 = str.lastIndexOf("\"");
		String substring = str.substring(index1 + 3, index2);
		return substring;
	}

	/**
	 * 获取访问的状态
	 * 
	 * @param str
	 * @return
	 */
	public static String parseStatus(String str) { // 获取访问的状态
		int index1 = str.lastIndexOf("\"");
		String str2 = str.substring(index1 + 1).trim();
		String[] splited = str2.split(" ");
		return splited[0];
	}

	/**
	 * 获取访问的状态
	 * 
	 * @param str
	 * @return
	 */
	public static String parseFlow(String str) { // 获取访问的状态
		int index1 = str.lastIndexOf("\"");
		String str2 = str.substring(index1 + 1).trim();
		String[] splited = str2.split(" ");
		return splited[1];
	}
}