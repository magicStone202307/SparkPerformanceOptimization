val spark = SparkSession.builder()
  .appName("CacheExample")
  .master("local[*]")
  .getOrCreate()

// 读取数据集
val data = spark.read.csv("data.csv")

// 执行一系列转换操作
val filteredData = data.filter($"age" > 30)
val transformedData = filteredData.withColumn("newColumn", $"age" * 2)
val groupedData = transformedData.groupBy("gender").avg("newColumn")

// 缓存数据集
groupedData.cache()

// 执行查询操作
val result1 = groupedData.count()
val result2 = groupedData.collect()

// 关闭 SparkSession
spark.stop()