package com.nyist.zcg.cleaner.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.tools.ant.taskdefs.optional.Cab;

/**
 * 格式化日期
 * 
 * @author zhangchenguang
 *
 */
public class DateUtil {

	public static void main(String[] args) {
//		Date date=new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String res = sdf.format(date);
//		System.out.println(res);
		System.out.println(formatDate(new Date()));
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(new Date());
		
		System.out.println(formatDate(calendar1.getTime(),1));
	}
	
	/**
	 * 将当前时间转换为年月日
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String res = sdf.format(date);
		return  res;
	}
	
	
	public static String formatDateToString(Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义文字显示格式，不显示毫秒
		String res = df.format(timestamp);//time为要转换的时间。
		return  res;
	}
	
	/**
	 * 将当前时间转换为  年月日 时分秒
	 * @param date
	 * @param flag
	 * @return
	 */
	public static String formatDate(Date date,int flag) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String res = sdf.format(date);
		return  res;
	}
}
