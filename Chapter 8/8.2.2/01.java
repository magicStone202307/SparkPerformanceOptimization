val data = spark.read.parquet("data.parquet")

// 错误示例：频繁对数据集进行缓存
for (i <- 1 to 10) {
  val processedData = processData(data) // 对数据集进行处理
  processedData.cache() // 缓存处理后的数据集
  // 进行后续操作
  // ...
}