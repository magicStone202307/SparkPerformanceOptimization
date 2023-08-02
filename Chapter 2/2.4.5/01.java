// 创建SparkSession
val spark = SparkSession.builder()
  .appName("BatchWriteExample")
  .master("local[*]")
  .getOrCreate()

// 创建示例数据DataFrame
val data: DataFrame = spark.range(1, 1000000).toDF("id")

// 单条数据写入方式
data.write
  .mode("overwrite")
  .format("parquet")
  .save("hdfs://path/to/destination")

// 停止SparkSession
spark.stop()