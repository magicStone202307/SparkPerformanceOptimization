val data = spark.read.parquet("data.parquet")

// 错误示例：连续的Shuffle操作
val shuffledData = data.groupBy("key").agg(sum("value")) // 第一个Shuffle操作
val finalResult = shuffledData.filter($"value" > 100).groupBy("key").count() // 第二个Shuffle操作