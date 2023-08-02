import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

// 创建 SparkSession
val spark = SparkSession.builder()
  .appName("Skew Detection Example")
  .getOrCreate()

// 读取表数据
val table: DataFrame = spark.read.format("csv")
  .option("header", "true")
  .load("path/to/table.csv")

// 统计每个键值的出现次数
val keyCounts: DataFrame = table.groupBy("key").count()

// 计算数据倾斜的阈值
val totalRowCount: Long = table.count()
val skewThreshold: Long = totalRowCount / 10 // 假设数据倾斜阈值为总行数的 1/10

// 检测倾斜键
val skewedKeys: Array[String] = keyCounts.filter(col("count") > skewThreshold)
  .select("key")
  .as[String]
  .collect()

// 打印倾斜键
println("Skewed keys:")
skewedKeys.foreach(println)

// 关闭 SparkSession
spark.stop()