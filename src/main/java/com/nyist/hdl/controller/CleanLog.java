package com.nyist.hdl.controller;

/**
 * 通过sparkSQL对采集到的日志信息进行相关统计分析
 */
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import com.nyist.hdl.util.ParselogsUtil;

import scala.Tuple3;

/**
 * 清洗tomcat日志
 * 
 * @author zhangchenguang
 *
 */
public class CleanLog {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Spark_public");
				//.setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// List<String> data = Arrays.asList("hello","hello world","hello
		// you","hello me","you and me");
		// JavaRDD<String> distData = sc.parallelize(data);
		JavaRDD<String> distData = sc.textFile("file:///Users/zhangchenguang/Desktop/log.txt");

		JavaRDD<Tuple3<String, String, String>> result = distData.map(new Function<String, Tuple3<String, String, String>>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public Tuple3<String, String, String> call(String value) throws Exception {
				
				String[] sub = ParselogsUtil.parseString(value);
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
		
		sc.close();
	}

}
