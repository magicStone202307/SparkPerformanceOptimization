import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

// 创建Spark配置
val conf = new SparkConf().setAppName("StreamingExample")
// 创建StreamingContext，设置批处理间隔为5秒
val ssc = new StreamingContext(conf, Seconds(5))

// 设置日志级别为WARN或ERROR，以减少输出信息
ssc.sparkContext.setLogLevel("WARN")

// 创建DStream，从TCP Socket接收数据
val lines = ssc.socketTextStream("localhost", 9999)

// 在DStream上进行一些操作，这里只是简单地打印每行文本
lines.foreachRDD { rdd =>
  rdd.foreach(println)
}

// 启动流式处理
ssc.start()
ssc.awaitTermination()