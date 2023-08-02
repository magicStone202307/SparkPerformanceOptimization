import org.apache.spark.sql.SparkSession

// 创建 SparkSession
val spark = SparkSession.builder
  .appName("PredicatePushdownExample")
  .getOrCreate()

// 读取数据源
val df = spark.read.format("csv").load("data.csv")

// 应用过滤条件
val filteredDF = df.filter("age > 30")

// 执行查询操作
val result = filteredDF.select("name")

// 输出结果
result.show()

// 停止 SparkSession
spark.stop()