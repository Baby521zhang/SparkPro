package com.study.example;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

/**
 * 通用方法
 * @author zhangchenguang
 *
 */
public class Spark_public {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Spark_public");
//				.setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		List<String> data = Arrays.asList("hello","hello world","hello you","hello me","you and me");
		JavaRDD<String> distData = sc.parallelize(data);
		
		distData.foreach(new VoidFunction<String>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void call(String t) throws Exception {
				System.out.println("哈哈...."+t);
			}
		});
		
	}

}
