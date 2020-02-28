package com.nyist.zcg.cleaner.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple3;

/**
 * 清洗tomcat日志
 * 
 * @author zhangchenguang
 *
 */
public class CleanLog {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Spark_public")//;
				.setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// 获取日志文件
		JavaRDD<String> distData = sc.textFile("file:///Users/zhangchenguang/Desktop/testFiles/log.txt");

		JavaRDD<Tuple3<String, String, String>> result = distData.map(new Function<String, Tuple3<String, String, String>>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public Tuple3<String, String, String> call(String value) throws Exception {
				
				String[] sub = Parselogs.parseString(value);
				if (sub[2].startsWith("GET /static") || sub[2].startsWith("GET /uc_server"))
					return null;// 对于静态的记录直接过滤掉，不进行任何处理
				
				if (sub[2].startsWith("GET /")) {
					sub[2] = sub[2].substring("GET /".length());
				}
				if (sub[2].startsWith("POST /")) {
					sub[2] = sub[2].substring("POST /".length());
				} // 过滤掉了开头和结尾的标志信息
				if (sub[2].endsWith(" HTTP/1.1")) {
					sub[2] = sub[2].substring(0, sub[2].length() - " HTTP/1.1".length());
				}
				if (sub[2].endsWith(" HTTP/1.0")) {
					sub[2] = sub[2].substring(0, sub[2].length() - " HTTP/1.0".length());
				}
				
				Tuple3<String, String, String> resTuple = new Tuple3<String, String, String>(sub[0], sub[1],sub[2]);
				System.out.println("解析后：==> "+resTuple);
				return resTuple;
			}
		});

		result.foreach(new VoidFunction<Tuple3<String,String,String>>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Tuple3<String, String, String> t) throws Exception {
				if(t != null){
					System.out.println("result: "+t._1()+"=="+t._2()+"=="+t._3());
				}
			}
		});
		
		result.saveAsTextFile("hdfs://localhost:9000/log");
//		result.collect();
		sc.close();
	}

}

class Parselogs{
	
	public Parselogs() {
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