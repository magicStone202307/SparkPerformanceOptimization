// 创建SparkSession
val spark = SparkSession.builder()
  .appName("BatchWriteExample")
  .master("local[*]")
  .getOrCreate()

// 创建示例数据DataFrame
val data: DataFrame = spark.range(1, 1000000).toDF("id")

// 批量写入数据到Parquet文件
data.foreachPartition { partition =>
  // 在每个分区上执行批量写入操作
  // 这里可以使用第三方库或自定义逻辑进行批量写入操作
}

// 停止SparkSession
spark.stop()