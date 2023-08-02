import org.apache.spark.sql.SparkSession

// 创建 SparkSession
val spark = SparkSession.builder
  .appName("NarrowDependencyExample")
  .getOrCreate()

// 读取数据源
val df1 = spark.read.format("csv").load("data1.csv")
val df2 = spark.read.format("csv").load("data2.csv")

// 执行转换操作
val transformedDF = df1.join(df2, "id")

// 输出结果
transformedDF.show()

// 停止 SparkSession
spark.stop()