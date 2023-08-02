val data = spark.read.parquet("data.parquet")

// 改进示例：避免连续的Shuffle操作
val intermediateResult = data.groupBy("key").agg(sum("value")) // 执行单个Shuffle操作

val finalResult = intermediateResult.filter($"value" > 100).groupBy("key").count() // 对缓存的结果进行进一步操作