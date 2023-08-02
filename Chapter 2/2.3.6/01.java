// 未优化的代码
val data = spark.read.csv("input.csv")
val result = data.filter($"age" > 30).groupBy($"gender").count()

// 优化后的代码
val data = spark.read.format("csv").option("header", "true").load("input.csv")

// 使用缓存进行数据重用
data.persist(StorageLevel.MEMORY_AND_DISK)

// 优化数据分区数
val repartitionedData = data.repartition(4)

val result = repartitionedData .filter($"age" > 30)
  .groupBy($"gender")
  .count()

// 使用列式存储格式，减少IO压力
result .write.format("parquet").mode("overwrite").save("output.parquet")