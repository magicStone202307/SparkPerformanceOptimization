import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._

// 创建SparkSession
val spark = SparkSession.builder()
  .appName(s"${this.getClass.getSimpleName}")
  .enableHiveSupport()
  .getOrCreate()

// 从日志文件读取数据并生成DataFrame
val logData = spark.sparkContext.textFile("日志文件路径")

// 定义推荐请求表的模式
val recommendationRequestSchema = StructType(Seq(
  StructField("user_id", StringType, nullable = false),
  StructField("video_id", StringType, nullable = false),
  // 其他字段的模式定义
  // ...
))

// 解析日志文件并生成DataFrame
val recommendationRequestDF = spark.createDataFrame(
  logData.mapPartitions(itr => {
    itr.flatMap(line => RecommendationRequestTable.parseLog(line))
  }),
  recommendationRequestSchema
)

// 添加当日小时分区列
val recommendationRequestTable = recommendationRequestDF.withColumn("hour_partition", date_format($"timestamp", "yyyy-MM-dd HH"))

// 将推荐请求表写入Hive表中，按照当日小时分区
recommendationRequestTable.write.partitionBy("hour_partition").mode("append").saveAsTable("推荐请求表")

// 显示推荐请求表数据
recommendationRequestTable.show()