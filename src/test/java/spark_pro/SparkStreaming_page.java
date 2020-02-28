//package spark_pro;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.VoidFunction;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaPairInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka.KafkaUtils;
//
//import com.dtwave.zcg.ConnectionPool;
//
//import kafka.serializer.StringDecoder;
//import net.sf.json.JSON;
//import net.sf.json.JSONObject;
//import scala.Tuple2;
//
///**
// * 处理埋点数据，获取相关pv指标
// * 实时pv统计方法:
// * a. 安卓pv
// * 1. topic: AndroidPageEvent 
// * 2.逻辑只需计算里面onPageStartEvent事件个数
// * b.  IOS pv
// * 1. topic: IOSPageEvent 
// * 2.逻辑只需计算里面onPageStartEvent事件个数
// * c.  h5页面 pv
// * 1.topic：logSZTVTopic
// * 2.逻辑:action字段为1,domain字段为“段为“static.scms.sztv.com.cn”的个数”
// * 
// * 	实时统计PV总数
// * @author zhangchenguang
// *
// */
//public class SparkStreaming_page {
//	
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		SparkConf conf = new SparkConf()  
//                .setAppName("SparkStreaming_page");    
//        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));  
//          
//        // 首先，要创建一份kafka参数map  
//        Map<String, String> kafkaParams = new HashMap<String, String>();  
//        //kafka自己的broker的结点的端口
//        kafkaParams.put("metadata.broker.list",args[0]);  
//          
//        // 然后，要创建一个set，里面放入，你要读取的topic ，可以并行读取多个topic  
//        Set<String> topics = new HashSet<String>();  
//        topics.add("AndroidPageEvent");
//        topics.add("IOSPageEvent");
//         
//        // 设置 checkpoint 的文件夹
//        jssc.checkpoint(args[1]);
//        kafkaParams.put("auto.offset.reset", "largest");
//        
//        // 创建输入DStream  
//        JavaPairInputDStream<String, String> lines = KafkaUtils.createDirectStream(  
//                jssc,   
//                String.class,   
//                String.class,   
//                StringDecoder.class,   
//                StringDecoder.class,   
//                kafkaParams,   
//                topics);
//        
//        JavaDStream<String> valueDStream = lines.map(new Function<Tuple2<String, String>, String>() {
//
//        	private static final long serialVersionUID = 1L;
//
//			public String call(Tuple2<String, String> v1) throws Exception {
//                return v1._2();
//            }
//        });
//        
//        // {"referer":"","ip":"124.239.252.147","articleId":"0","catalogCode":"230948869","userAgent":"Mozilla/5.0 (iPhone; CPU iPhone OS 12_0_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16A404 MicroMessenger/6.5.23 NetType/WIFI Language/zh_CN","sessionId":"af3c58ff94834db8a7c5$1540328221565","title":"军情直播间 2018-10-23","deviceId":"4c0f4ade654d1ebd62579b0b8e7c99f2","url":"/zbgat/szwszbgat/jqzbj/28020263.shtml","visitTime":"2018-10-24 13:54:25","catalogId":"5655","atype":"special","domain":"zbgat.scms.sztv.com.cn","action":"2","visitDate":"2018-10-24"}
//        valueDStream.dstream().print();
//        
//        //2、统计各种事件，将pv统计出来
//        JavaDStream<Integer> vsis = valueDStream.flatMap(new FlatMapFunction<String, Integer>() {
//
//        	private static final long serialVersionUID = 1L;
//			private JSONObject json=null;
//			
//			@Override
//			public Iterable<Integer> call(String t) throws Exception {
//				Integer result=0;
//				System.out.println(t);
//				
//				// 解析json串，然后获取相关事件的个数
//				json=new JSONObject();
//				json=JSONObject.fromObject(t);
//				if(json.containsKey("onPageStartEvent")){
//					result++;
//				}
//				return Arrays.asList(result);
//			}
//		});
//        
//        // 遍历结果
//        vsis.foreachRDD(new VoidFunction<JavaRDD<Integer>>() {
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void call(JavaRDD<Integer> t) throws Exception {
//				Long res=0l;
//				List<Integer> tss = t.collect();
//				for (Integer ts : tss) {
//					res+=ts;
//				}
//				
//				System.out.println("最终的计算结果："+res);
//				
//				// 先查询数据库，如果没有数据，就进行插入操作，如果有就进行更新操作
//				if(res!=0){
//					Connection conn = ConnectionPool.getConnection();
//	                conn.setAutoCommit(false);
//	                String sql = "select * from t_kafka_ss where marking_name='onPageStartEvent'";
//	                PreparedStatement ps = conn.prepareStatement(sql);
//	                ResultSet rs = ps.executeQuery();
//	                
//	                if(!rs.next()){
//	                	sql = "insert into t_kafka_ss (marking_name,pv) values (?,?)";
//	                	ps = conn.prepareStatement(sql);
//	                	ps.setString(1,"onPageStartEvent");
//	                	ps.setLong(2,res);
//	                }else{
//	                	Long rs_count = rs.getLong("pv");
//	                	sql = "update t_kafka_ss set pv = ? where marking_name='onPageStartEvent'";
//	                	ps = conn.prepareStatement(sql);
//	                	ps.setLong(1,res+rs_count);
//	                }
//	                ps.execute();
//	                conn.commit();
//	                ps.close();
//	                ConnectionPool.returnConnection(conn);
//				}
//			}
//		});
//        
//        vsis.count();
//        jssc.start();  
//        jssc.awaitTermination();  
//        jssc.close();  
//	}
//}