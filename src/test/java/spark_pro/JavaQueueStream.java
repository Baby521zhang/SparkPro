//package spark_pro;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.VoidFunction;
//import org.apache.spark.streaming.Duration;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//
//import com.dtwave.zcg.ConnectionPool;
//import com.dtwave.zcg.DateUtil;
//
//import net.sf.json.JSONObject;
//
//public final class JavaQueueStream {
//  private JavaQueueStream() {
//  }
//
//  public static void main(String[] args) throws Exception {
//
//    SparkConf sparkConf = new SparkConf().setAppName("JavaQueueStream").setMaster("local[2]");
//      
//
//    // Create the context
//    JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, new Duration(1000));
//
//    // Create the queue through which RDDs can be pushed to
//    // a QueueInputDStream
//
//    // Create and push some RDDs into the queue
////    List<Integer> list = new ArrayList<>();
////    for (int i = 0; i < 1000; i++) {
////      list.add(i);
////    }
//
////    Queue<JavaRDD<Integer>> rddQueue = new LinkedList<>();
////    for (int i = 0; i < 30; i++) {
////      rddQueue.add(ssc.sparkContext().parallelize(list));
////    }
////
////    // Create the QueueInputDStream and use it do some processing
////    JavaDStream<Integer> inputStream = ssc.queueStream(rddQueue);
////    JavaPairDStream<Integer, Integer> mappedStream = inputStream.mapToPair(
////        i -> new Tuple2<>(i % 10, 1));
////    JavaPairDStream<Integer, Integer> reducedStream = mappedStream.reduceByKey(
////        (i1, i2) -> i1 + i2);
////
////    reducedStream.print();
//    
//    
//    
//    
//    List<String> lists = new ArrayList<>();
//    lists.add("{\"onPageStartEvent\":\"https://m.baidu.com/from\u003d1019023c/bd_page_type\u003d1/ssid\u003d0/uid\u003d0/pu\u003dusm%402%2Csz%40320_1001%2Cta%40iphone_2_8.0_3_537/baiduid\u003dAEFD1925285D39DF9C1AC5744B552C92/w\u003d0_10_/t\u003diphone/l\u003d1/tc?ref\u003dwww_iphone\",\"ip\":\"183.40.3.206, 14.215.161.121\",\"articleId\":\"27598793\",\"catalogCode\":\"001973000008000003\",\"userAgent\":\"Mozilla/5.0 (Linux; Android 8.0.0; MHA-AL00 Build/HUAWEIMHA-AL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36\",\"sessionId\":\"7b9bd5d62f17441ba10c$1537003976481\",\"title\":\"光明：玉塘办事处表彰社区网格管理先进集体和个人\",\"deviceId\":\"7450b086e48fec10db05988e1a729d45\",\"url\":\"/ysz/dsdb/ggpd/gqxwlb/27598793.shtml\",\"visitTime\":\"2018-10-24 13:54:25\",\"catalogId\":\"8203\",\"atype\":\"1\",\"domain\":\"static.scms.sztv.com.cn\",\"action\":\"2\",\"visitDate\":\"2018-10-24\"}");
//    lists.add("{\"onPageStartEvent\":\"https://m.baidu.com/from\u003d1019023c/bd_page_type\u003d1/ssid\u003d0/uid\u003d0/pu\u003dusm%402%2Csz%40320_1001%2Cta%40iphone_2_8.0_3_537/baiduid\u003dAEFD1925285D39DF9C1AC5744B552C92/w\u003d0_10_/t\u003diphone/l\u003d1/tc?ref\u003dwww_iphone\",\"ip\":\"183.40.3.206, 14.215.161.121\",\"articleId\":\"27598793\",\"catalogCode\":\"001973000008000003\",\"userAgent\":\"Mozilla/5.0 (Linux; Android 8.0.0; MHA-AL00 Build/HUAWEIMHA-AL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36\",\"sessionId\":\"7b9bd5d62f17441ba10c$1537003976481\",\"title\":\"光明：玉塘办事处表彰社区网格管理先进集体和个人\",\"deviceId\":\"7450b086e48fec10db05988e1a729d45\",\"url\":\"/ysz/dsdb/ggpd/gqxwlb/27598793.shtml\",\"visitTime\":\"2018-10-24 13:54:25\",\"catalogId\":\"8203\",\"atype\":\"1\",\"domain\":\"static.scms.sztv.com.cn\",\"action\":\"2\",\"visitDate\":\"2018-10-24\"}");
//    lists.add("{\"onPageStartEvent\":\"https://m.baidu.com/from\u003d1019023c/bd_page_type\u003d1/ssid\u003d0/uid\u003d0/pu\u003dusm%402%2Csz%40320_1001%2Cta%40iphone_2_8.0_3_537/baiduid\u003dAEFD1925285D39DF9C1AC5744B552C92/w\u003d0_10_/t\u003diphone/l\u003d1/tc?ref\u003dwww_iphone\",\"ip\":\"183.40.3.206, 14.215.161.121\",\"articleId\":\"27598793\",\"catalogCode\":\"001973000008000003\",\"userAgent\":\"Mozilla/5.0 (Linux; Android 8.0.0; MHA-AL00 Build/HUAWEIMHA-AL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36\",\"sessionId\":\"7b9bd5d62f17441ba10c$1537003976481\",\"title\":\"光明：玉塘办事处表彰社区网格管理先进集体和个人\",\"deviceId\":\"7450b086e48fec10db05988e1a729d45\",\"url\":\"/ysz/dsdb/ggpd/gqxwlb/27598793.shtml\",\"visitTime\":\"2018-10-24 13:54:25\",\"catalogId\":\"8203\",\"atype\":\"1\",\"domain\":\"static.scms.sztv.com.cn\",\"action\":\"2\",\"visitDate\":\"2018-10-24\"}");
//    
//    Queue<JavaRDD<String>> rddQueue = new LinkedList<>();
//    rddQueue.add(ssc.sparkContext().parallelize(lists));
//    
//    JavaDStream<String> valueDStream = ssc.queueStream(rddQueue);
//    
//	valueDStream.dstream().print();
//    // 获取到kafka中的数据以后如何处理？
//    // 1、将结果存储到hdfs中,问题？出现了许多小文件
//    valueDStream.dstream().saveAsTextFiles("hdfs://10.85.3.52:8020/user/deploy/hiveextdb/"+args[0]+"/"+args[1]+"/"+DateUtil.formatDate(new Date()), "test");
//    
//    //2、统计各种事件，将pv统计出来
//    JavaDStream<Integer> vsis = valueDStream.flatMap(new FlatMapFunction<String, Integer>() {
//		private JSONObject json=null;
//		@Override
//		public Iterable<Integer> call(String t) throws Exception {
//			Integer result=0;
//			System.out.println(t);
//			// 解析json串，然后获取相关事件的个数
//			json=new JSONObject();
//			json=JSONObject.fromObject(t);
//			if(json.containsKey("onPageStartEvent")){
//				result++;
//			}
//			return Arrays.asList(result);
//		}
//	});
//    
//    // 遍历结果
//    vsis.foreachRDD(new VoidFunction<JavaRDD<Integer>>() {
//
//		@Override
//		public void call(JavaRDD<Integer> t) throws Exception {
//			Long res=0l;
//			List<Integer> tss = t.collect();
//			for (Integer ts : tss) {
//				res+=ts;
//			}
//			
//			System.out.println("最终的计算结果："+res);
//			// 先查询数据库，如果没有数据，就进行插入操作，如果有就进行更新操作
//			if(res!=0){
//				System.out.println("1--------");
//				Connection conn = ConnectionPool.getConnection();
//                conn.setAutoCommit(false);
//                String sql = "select * from t_kafka_ss where marking_name='onPageStartEvent'";
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery();
//                
//                if(!rs.next()){
//                	System.out.println("2--------");
//                	sql = "insert into t_kafka_ss (marking_name,pv) values (?,?)";
//                	ps = conn.prepareStatement(sql);
//                	ps.setString(1,"onPageStartEvent");
//                	ps.setLong(2,res);
//                }else{
//                	System.out.println("3--------");
//                	Long rs_count = rs.getLong("pv");
//                	sql = "update t_kafka_ss set pv = ? where marking_name='onPageStartEvent'";
//                	ps = conn.prepareStatement(sql);
//                	ps.setLong(1,res+rs_count);
//                }
//                ps.execute();
//                conn.commit();
//                ps.close();
//                ConnectionPool.returnConnection(conn);
//			}
//		}
//	});
//    
//    
//    ssc.start();
//    ssc.awaitTermination();
//  }
//}