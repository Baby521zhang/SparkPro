//package spark_pro;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaPairInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka.KafkaUtils;
//
//import kafka.serializer.StringDecoder;
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
// * @author zhangchenguang
// *
// */
//public class SparkStreaming_log_test {
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		SparkConf conf = new SparkConf()  
////                .setMaster("local[2]")  
////                .setMaster("yarn-client")  
//                .setAppName("SparkStreaming_log");    
//        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));  
//          
//        // 首先，要创建一份kafka参数map  
//        Map<String, String> kafkaParams = new HashMap<String, String>();  
//        //kafka自己的broker的结点的端口
////        kafkaParams.put("metadata.broker.list",   
////                "192.168.142.131:9092,192.168.142.132:9092");  
//        kafkaParams.put("metadata.broker.list",
//        		"10.85.3.5:9092,"
//        		+ "10.85.3.64:9092,"
//        		+ "10.85.3.65:9092,"
//        		+ "10.85.3.4:9092,"
//        		+ "10.85.3.56:9092,"
//        		+ "10.85.3.31:9092,"
//        		+ "10.85.3.57:9092");  
//          
//        // 然后，要创建一个set，里面放入，你要读取的topic  
//        // 这个，就是我们所说的，它自己给你做的很好，可以并行读取多个topic  
//        Set<String> topics = new HashSet<String>();  
//        topics.add("logSZTVTopic");
////        topics.add("WordCount");
////        topics.add("WordCount1");
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
//            public String call(Tuple2<String, String> v1) throws Exception {
//                return v1._2();
//            }
//        });
//        
//        // {"referer":"","ip":"124.239.252.147","articleId":"0","catalogCode":"230948869","userAgent":"Mozilla/5.0 (iPhone; CPU iPhone OS 12_0_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16A404 MicroMessenger/6.5.23 NetType/WIFI Language/zh_CN","sessionId":"af3c58ff94834db8a7c5$1540328221565","title":"军情直播间 2018-10-23","deviceId":"4c0f4ade654d1ebd62579b0b8e7c99f2","url":"/zbgat/szwszbgat/jqzbj/28020263.shtml","visitTime":"2018-10-24 13:54:25","catalogId":"5655","atype":"special","domain":"zbgat.scms.sztv.com.cn","action":"2","visitDate":"2018-10-24"}
//        valueDStream.dstream().print();
//       
//        
//        jssc.start();  
//        jssc.awaitTermination();  
//        jssc.close();  
//	}
//}