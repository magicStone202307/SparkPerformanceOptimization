import org.apache.spark.sql.SparkSession

// 创建一个SparkSession对象
val spark = SparkSession.builder()
.appName("Spark Event Logs Analysis")
.config("spark.eventLog.enabled", "true")
.config("spark.eventLog.dir", "/path/to/event/logs")
.getOrCreate()

// 从event log中读取数据
val eventLogs = spark.read.format("org.apache.spark.sql.execution.streaming.sources.EventLogSourceProvider")
.option("startingPosition", "earliest") // 从最早的位置开始读取
.option("mode", "PERMISSIVE") // 读取模式为宽松模式
.option("path", "/path/to/event/logs") // event log的路径
.load()