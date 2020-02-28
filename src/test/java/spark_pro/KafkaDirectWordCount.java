//package spark_pro;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.api.java.function.VoidFunction;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaPairDStream;
//import org.apache.spark.streaming.api.java.JavaPairInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka.KafkaUtils;
//
//import com.dtwave.zcg.ConnectionPool;
//
//import kafka.serializer.StringDecoder;
//import scala.Tuple2;
//
///**
// * 基于Kafka Direct方式的实时wordcount程序
// * @author zhangchenguang
// *
// */
//public class KafkaDirectWordCount {
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		SparkConf conf = new SparkConf()  
//                .setMaster("local[2]")  
//                .setAppName("KafkaDirectWordCount");    
//        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));  
//          
//        // 首先，要创建一份kafka参数map  
//        Map<String, String> kafkaParams = new HashMap<String, String>();  
//        //kafka自己的broker的结点的端口
////        kafkaParams.put("metadata.broker.list",   
////                "192.168.142.131:9092,192.168.142.132:9092");  
//        kafkaParams.put("metadata.broker.list","localhost:9092");  
//          
//        // 然后，要创建一个set，里面放入，你要读取的topic  
//        // 这个，就是我们所说的，它自己给你做的很好，可以并行读取多个topic  
//        Set<String> topics = new HashSet<String>();  
//        topics.add("WordCount");
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
//        // 执行wordcount操作  
//        JavaDStream<String> words = lines.flatMap(  
//                  
//                new FlatMapFunction<Tuple2<String,String>, String>() {  
//  
//                    private static final long serialVersionUID = 1L;  
//  
//                    @Override  
//                    public Iterable<String> call(Tuple2<String, String> tuple)  
//                            throws Exception {  
//                    	System.out.println(tuple._1+"=="+tuple.toString());
//                        return Arrays.asList(tuple._2.split(" "));    
//                    }  
//                      
//                });  
//          
//        JavaPairDStream<String, Integer> pairs = words.mapToPair(  
//                  
//                new PairFunction<String, String, Integer>() {  
//  
//                    private static final long serialVersionUID = 1L;  
//  
//                    @Override  
//                    public Tuple2<String, Integer> call(String word) throws Exception {  
//                        return new Tuple2<String, Integer>(word, 1);  
//                    }  
//                      
//                });  
//          
//        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(  
//                  
//                new Function2<Integer, Integer, Integer>() {  
//  
//                    private static final long serialVersionUID = 1L;  
//  
//                    @Override  
//                    public Integer call(Integer v1, Integer v2) throws Exception { 
//                        return v1 + v2;  
//                    }  
//                      
//                });  
//        
//        //遍历DStream流中的每个数据，存入到数据库中
//        wordCounts.foreachRDD(new Function<JavaPairRDD<String, Integer>, Void>() {
//            @Override
//            public Void call(JavaPairRDD<String, Integer> stringIntegerJavaPairRDD) throws Exception {
//                stringIntegerJavaPairRDD.foreach(new VoidFunction<Tuple2<String, Integer>>() {
//                    @Override
//                    public void call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
//                        //ConnectionPool的定义与ConnectionPoolTitle的一样
//                        Connection conn = ConnectionPool.getConnection();
//                        conn.setAutoCommit(false);
//                        String sql = "insert into t_kafka_count (name,count) values (?,?)";
//                        PreparedStatement ps = conn.prepareStatement(sql);
//                        ps.setString(1, stringIntegerTuple2._1);
//                        ps.setInt(2, stringIntegerTuple2._2);
//                        ps.executeUpdate();
//                        conn.commit();
//                    }
//                });
//                return null;
//            }
//        });
//        
//        
//        
//        wordCounts.print();
//        jssc.start();  
//        jssc.awaitTermination();  
//        jssc.close();  
//	}
//}