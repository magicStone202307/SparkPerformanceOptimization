import org.apache.spark.SparkConf  
import org.apache.spark.scheduler._ 
import org.apache.spark.sql.SparkSession 

// 设置event log文件路径
val logFile = "file:/path/to/eventLogs"

// 设置Spark配置
val sparkConf = new SparkConf()
  .setAppName("EventLogAnalysis")
  .setMaster("local[*]")
  .set("spark.eventLog.enabled", "true")
  .set("spark.eventLog.dir", logFile)

// 创建SparkSession实例
val spark = SparkSession.builder().config(sparkConf).getOrCreate()

// 获取SparkContext实例
val sc = spark.sparkContext