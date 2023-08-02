val data = spark.read.parquet("data.parquet")

// 错误示例：过度缓存不常被访问的数据集
val processedData = processData(data) // 对数据集进行处理
processedData.cache() // 过度缓存处理后的数据集
// 后续操作
// ...

val result = processedData.filter(...) // 对缓存的数据集进行进一步操作