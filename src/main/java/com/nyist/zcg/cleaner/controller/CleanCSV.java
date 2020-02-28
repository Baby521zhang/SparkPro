package com.nyist.zcg.cleaner.controller;

import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

/**
 * 通过sparkSQL对采集到的csv数据进行统计分析，并存入数据库中
 * 
 * @author zhangchenguang
 *
 */
public class CleanCSV {
	
	private static Properties connectionProperties;
	
	static{
		connectionProperties = new Properties();
		connectionProperties.put("user", "root");
		connectionProperties.put("password", "123456");
		connectionProperties.put("driver","com.mysql.jdbc.Driver");
	}
	
	public static void main(String[] args) {
		SparkConf sc = new SparkConf();
		sc.setMaster("local");
		
		SparkSession spark = SparkSession.builder()
				  .config(sc)
				  .appName("CleanCSV")
				  .getOrCreate();
		Dataset<Row> rowData = spark.read().option("inferSchema","true").option("header","true")
				.csv("file:///Users/zhangchenguang/Desktop/testFiles/test_data.txt");
//				spark.read().csv("file:///Users/zhangchenguang/Desktop/testFiles/test_data.txt");
		rowData.createOrReplaceTempView("csv");
		
		/**
		 * 用户事件行为分析
		 */
		Dataset<Row> action_DF = spark.sql("SELECT action,count(1) as counts from csv group by action order by action");
		action_DF.show();
		
		// 写入数据库中
//		action_DF.write()
//		.mode(SaveMode.Append)
//		.jdbc("jdbc:mysql://localhost:3306/t_bigdata", "t_action", connectionProperties);
		
		// 复杂入库步骤采用jdbc操作进行
//		action_DF.foreach(new ForeachFunction() {
//			
//			@Override
//			public void call(Object row) throws Exception {
//				System.out.println(row);
//			}
//
//		});
		
		/**
		 * 年龄分布情况分析
		 */
		Dataset<Row> age_DF = spark.sql("SELECT age_range,count(1) as counts from csv group by age_range order by age_range");
		age_DF.show();
		
		// 写入数据库中
//		age_DF.write()
//		.mode(SaveMode.Append)
//		.jdbc("jdbc:mysql://localhost:3306/t_bigdata", "t_agebyday", connectionProperties);
		
		/**
		 * 性别分布分析
		 */
		Dataset<Row> sex_DF = spark.sql("SELECT gender,count(1) as counts from csv group by gender order by gender");
		sex_DF.show();
		
		// 写入数据库中
//		sex_DF.write()
//		.mode(SaveMode.Append)
//		.jdbc("jdbc:mysql://localhost:3306/t_bigdata", "t_sexbyday", connectionProperties);
		
		/**
		 * 地区分布分析
		 */
		Dataset<Row> pro_DF = spark.sql("SELECT province,count(1) as counts from csv group by province order by province");
		pro_DF.show();
		// 写入数据库中
//		pro_DF.write()
//		.mode(SaveMode.Append)
//		.jdbc("jdbc:mysql://localhost:3306/t_bigdata", "t_probyday", connectionProperties);
	}

}
