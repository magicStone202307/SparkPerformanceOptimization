import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .master("<master-url>")
  .appName("<application-name>")
  .config("spark.dynamicAllocation.enabled", "true")
  .config("spark.dynamicAllocation.minExecutors", "<min-executors>")
  .config("spark.dynamicAllocation.maxExecutors", "<max-executors>")
  .getOrCreate()

// 使用Spark会话进行作业处理
// ...

// 停止Spark会话
spark.stop()