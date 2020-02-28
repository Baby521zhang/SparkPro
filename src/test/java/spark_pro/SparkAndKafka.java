//package spark_pro;
//
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.api.java.function.VoidFunction;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaPairDStream;
//import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka.KafkaUtils;
//
//import com.dtwave.zcg.ConnectionPool;
//
//import net.sf.json.JSON;
//import net.sf.json.JSONObject;
//import scala.Tuple2;
//
///**
// * 
// * 将kafka的数据接入到sparkStreaming 然后输出到hive和mysql里面。
// * 
// * @author zhangchenguang
// *
// */
//
//public class SparkAndKafka {
//
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		/*
//		 * kafka所注册的zk集群
//		 */
//		String zkQuorum = "localhost:2181";
////		String zkQuorum = "10.93.21.21:2181,10.93.18.34:2181,10.93.18.35:2181";
//
//		/*
//		 * spark-streaming消费kafka的topic名称, 多个以逗号分隔
//		 */
//		String topics = "kafka_spark";
////		String topics = "kafka_spark,kafka_spark2";
//
//		/*
//		 * 消费组 group
//		 */
//		String group = "bigdata_qa";
//
//		/*
//		 * topic的分区数
//		 */
//		int numThreads = 1;
//
//		/*
//		 * 选用yarn队列模式, spark-streaming程序的app名称是"order profit"
//		 */
//		SparkConf sparkConf = new SparkConf().setMaster("yarn-client").setAppName("order profit");
//
//		/*
//		 * 创建sc, 全局唯一, 设置logLevel可以打印一些东西到控制台
//		 */
//		JavaSparkContext sc = new JavaSparkContext(sparkConf);
//		sc.setLogLevel("WARN");
//
//		/*
//		 * 创建jssc, spark-streaming的batch是每5s划分一个
//		 */
//		JavaStreamingContext jssc = new JavaStreamingContext(sc, Durations.seconds(5));
//
//		/*
//		 * 准备topicMap
//		 */
//		Map<String, Integer> topicMap = new HashMap<String, Integer>();
//		for (String topic : topics.split(",")) {
//			topicMap.put(topic, numThreads);
//		}
//
//		/*
//		 * kafka数据流
//		 */
//		List<JavaPairReceiverInputDStream<String, String>> streams = new ArrayList<JavaPairReceiverInputDStream<String, String>>();
//		for (int i = 0; i < numThreads; i++) {
//			streams.add(KafkaUtils.createStream(jssc, zkQuorum, group, topicMap));
//		}
//		/*
//		 * 从kafka消费数据的RDD
//		 */
//		JavaPairDStream<String, String> streamsRDD = streams.get(0);
//		for (int i = 1; i < streams.size(); i++) {
//			streamsRDD = streamsRDD.union(streams.get(i));
//		}
//
//		/*
//		 * kafka消息形如: {"id": ${uuid}, "type": 1, "profit": 35} 统计结果, 以type分组的总收益
//		 * mapToPair, 将kafka消费的数据, 转化为type-profit key-value对 reduceByKey,
//		 * 以type分组, 聚合profit
//		 */
//		JavaPairDStream<Integer, Integer> profits = streamsRDD
//				.mapToPair(new PairFunction<Tuple2<String, String>, Integer, Integer>() {
//					@Override
//					public Tuple2<Integer, Integer> call(Tuple2<String, String> s_tuple2) throws Exception {
//						JSONObject jsonObject = JSONObject.fromObject(s_tuple2._2);
//						return new Tuple2<Integer, Integer>((Integer)jsonObject.get("type"),
//								(Integer)jsonObject.get("profit"));
//					}
//				}).reduceByKey(new Function2<Integer, Integer, Integer>() {
//					@Override
//					public Integer call(Integer i1, Integer i2) throws Exception {
//						return i1 + i2;
//					}
//				});
//
//		/*
//		 * 输出结果到MySQL 需要为每一个partition创建一个MySQL句柄, 使用foreachPartition
//		 */
////		profits.foreachRDD(new Function<JavaPairRDD<Integer, Integer>, Void>() {
////			@Override
////			public Void call(JavaPairRDD<Integer, Integer> integerIntegerJavaPairRDD) throws Exception {
////
////				integerIntegerJavaPairRDD.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, Integer>>>() {
////					@Override
////					public void call(Iterator<Tuple2<Integer, Integer>> tuple2Iterator) throws Exception {
////						Connection connection = ConnectionPool.getConnection();
////						Statement stmt = connection.createStatement();
////						long timestamp = System.currentTimeMillis();
////						while (tuple2Iterator.hasNext()) {
////							Tuple2<Integer, Integer> tuple = tuple2Iterator.next();
////							Integer type = tuple._1;
////							Integer profit = tuple._2;
////							String sql = String.format(
////									"insert into `order` (`type`, `profit`, `time`) values (%s, %s, %s)", type, profit,
////									timestamp);
////							stmt.executeUpdate(sql);
////						}
////						ConnectionPool.returnConnection(connection);
////					}
////				});
////				return null;
////			}
////		});
//		
//		profits.foreachRDD(new Function<JavaPairRDD<Integer,Integer>, Void>() {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Void call(JavaPairRDD<Integer, Integer> integerIntegerJavaPairRDD) throws Exception {
//				integerIntegerJavaPairRDD.foreachPartition(new VoidFunction<Iterator<Tuple2<Integer, Integer>>>() {
//					/**
//					 * 
//					 */
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public void call(Iterator<Tuple2<Integer, Integer>> tuple2Iterator) throws Exception {
//						Connection connection = ConnectionPool.getConnection();
//						Statement stmt = connection.createStatement();
//						long timestamp = System.currentTimeMillis();
//						while (tuple2Iterator.hasNext()) {
//							Tuple2<Integer, Integer> tuple = tuple2Iterator.next();
//							Integer type = tuple._1;
//							Integer profit = tuple._2;
//							String sql = String.format(
//									"insert into `order` (`type`, `profit`, `time`) values (%s, %s, %s)", type, profit,
//									timestamp);
//							stmt.executeUpdate(sql);
//						}
//						ConnectionPool.returnConnection(connection);
//					}
//				});
//				return null;
//			}
//		});
//
//		/*
//		 * 开始计算, 等待计算结束
//		 */
//		jssc.start();
//		try {
//			jssc.awaitTermination();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			jssc.close();
//		}
//	}
//}