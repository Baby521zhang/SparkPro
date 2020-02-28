package com.nyist.hdl.controller;

import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import com.nyist.hdl.model.MemberFrom;

/**
 * 清洗埋点数据 JSON
 * 
 * @author zhangchenguang
 *
 */
public class CleanJSON {

	public static void main(String[] args) {
		
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", "root");
		connectionProperties.put("password", "123456");
		connectionProperties.put("driver","com.mysql.jdbc.Driver");
		
		SparkConf sc = new SparkConf();
		sc.setMaster("local");
		SparkSession spark = SparkSession.builder()
				  .config(sc)
				  .appName("CleanJSON")
				  .getOrCreate();
		Dataset<Row> rowData = spark.read().json("file:///Users/zhangchenguang/eclispe_workspace/spark_pro/src/main/java/com/nyist/hdl/datas/data.txt");
		rowData.createOrReplaceTempView("data");
		Dataset<Row> action_DF = spark.sql("SELECT actions,count(1) FROM data group by actions");
		action_DF.show();
//		action_DF.write()
//		.mode(SaveMode.Append)
//		.jdbc("jdbc:mysql://localhost:3306/t_spark", "t_action", connectionProperties);
		
//		action_DF.foreach(new ForeachFunction() {
//			
//			@Override
//			public void call(Object row) throws Exception {
//				System.out.println(row);
//			}
//
//		});
		
		Dataset<Row> opLog_DF = spark.sql("SELECT ip,count(1) FROM data group by ip having count(1)>1");
		opLog_DF.show();
//		opLog_DF.write()
//			.mode(SaveMode.Append)
//			.jdbc("jdbc:mysql://localhost:3306/t_spark", "t_oplog", connectionProperties);
		
		Dataset<Row> memberFrom_DF = rowData.select("fromURL");
		
		Dataset<MemberFrom> mfStr_DF = memberFrom_DF.map(new MapFunction<Row, MemberFrom>() {

			/**
			 * 处理来源方式
			 */
			private static final long serialVersionUID = 1L;
			private MemberFrom mf = null;

			@Override
			public MemberFrom call(Row value) throws Exception {
				String rowStr = value.getString(0);
				int index = 0;
				index = rowStr.indexOf("baidu");
				
				mf = new MemberFrom();
				mf.setCount(1);
				
				if(index!=-1){
					mf.setFromName("百度推广");
				}
				index = rowStr.indexOf("tv");
				if(index!=-1){
					mf.setFromName("视频广告");
				}
				index = rowStr.indexOf("nyist");
				if(index!=-1){
					mf.setFromName("直接访问");
				}
				index = rowStr.indexOf("mail");
				if(index!=-1){
					mf.setFromName("邮件营销");
				}
				index = rowStr.indexOf("phone");
				if(index!=-1){
					mf.setFromName("客服电话");
				}
				
				return mf;
			}
		}, Encoders.bean(MemberFrom.class));

		JavaRDD<MemberFrom> jrdd = mfStr_DF.javaRDD();
		
		Dataset<Row> mf_DF = spark.createDataFrame(jrdd, MemberFrom.class);
		
		mf_DF.createOrReplaceTempView("memberFrom");
		Dataset<Row> mfRes_DF = spark.sql("SELECT fromName,count(1) FROM memberFrom group by fromName");
		mfRes_DF.show();
		
//		mfRes_DF.write()
//			.mode(SaveMode.Append)
//			.jdbc("jdbc:mysql://localhost:3306/t_spark", "t_memberfrom", connectionProperties);

	}
}
