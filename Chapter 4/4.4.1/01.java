import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.broadcast

// 创建 SparkSession
val spark = SparkSession.builder()
  .appName("Broadcast Join Example")
  .getOrCreate()

// 读取大表和小表数据
val largeTable = spark.read.format("csv")
  .option("header", "true")
  .load("path/to/large_table.csv")

val smallTable = spark.read.format("csv")
  .option("header", "true")
  .load("path/to/small_table.csv")

// 将小表数据广播到所有Executor节点上
val broadcastSmallTable = broadcast(smallTable)

// 执行连接操作
val result = largeTable.join(broadcastSmallTable, Seq("join_key"), "inner")

// 展示连接结果
result.show()

// 关闭 SparkSession
spark.stop()