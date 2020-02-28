package com.nyist.zcg.cleaner.controller;

import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;


public class FlinkPv {

	public static void main(String[] args) throws Exception {
		Properties pro = new Properties();
		
		pro.setProperty("bootstrap.servers", args[0]);
		pro.setProperty("zookeeper.connect", args[1]);
//		pro.setProperty("bootstrap.servers", "localhost:9092");
//		pro.setProperty("zookeeper.connect", "localhost:2181");
		pro.put("group.id", "1");
		
		StreamExecutionEnvironment env = StreamExecutionEnvironment
				.getExecutionEnvironment();
		env.getConfig().disableSysoutLogging();  //设置此可以屏蔽掉日记打印情况
		env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
		env.enableCheckpointing(1000);
		
		// env.timeWindow(Time.seconds(2));
//		DataStream<String> sourceStream = env
//				.addSource(new FlinkKafkaConsumer011(args[2],new SimpleStringSchema() ,pro));
		DataStream<String> sourceStream = env
				.addSource(new FlinkKafkaConsumer010<String>(args[2], new SimpleStringSchema(),pro));
//		DataStream<String> sourceStream = env
//				.addSource(new FlinkKafkaConsumer010<String>("mytopic", new SimpleStringSchema(),pro));
		
		
		/**
		 * 过滤掉没有变更的日志信息
		 * 解析可以放在这里，或者输出到sink进行解析
		 */
		DataStream<String> sourceStreamTra = sourceStream.filter(new FilterFunction<String>() {
			
			@Override
			public boolean filter(String value) throws Exception {
				System.out.println("获取到的："+value);
				if(StringUtils.isBlank(value)){
					return false;
				}else{
					return false;
				}
			}
		});
		
		/**
		 * 有效日志信息，进入操作类
		 */
//		sourceStreamTra.addSink(new SinkFunction<String>() {
//			
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void invoke(String value) throws Exception {
//				System.out.println("======"+value+"======");
//			}
//		});
		
		sourceStreamTra.addSink(new MysqlSink());
		env.execute("data to mysql start");
	}
}
