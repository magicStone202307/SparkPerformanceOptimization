import org.apache.spark.sql.{DataFrame, SparkSession}

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("ErrorHandlingExample")
  .master("local[*]")
  .getOrCreate()

// 创建示例数据DataFrame，包括正常数据和异常数据
val data: DataFrame = spark.createDataFrame(Seq(
  (1, "John", 25),
  (2, "Mary", 30),
  (3, "Invalid", null)
)).toDF("id", "name", "age")

// 进行数据处理，过滤掉异常数据
val filteredData: DataFrame = data.filter("age IS NOT NULL")

// 容错处理，处理异常数据
val errorRecords: DataFrame = data.filter("age IS NULL")

// 记录异常数据，可以进行日志记录或其他处理
errorRecords.foreach(row => {
  val id = row.getAs[Int]("id")
  val name = row.getAs[String]("name")
  println(s"Error record found: id=$id, name=$name")
})

// 继续对过滤后的数据进行其他操作
filteredData.show()

// 停止SparkSession
spark.stop()