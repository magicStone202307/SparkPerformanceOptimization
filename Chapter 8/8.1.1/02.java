import org.apache.spark.sql.SparkSession

object SparkApplication {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark Application")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val data = Seq(("Alice", 25), ("Bob", 30), ("Charlie", 35))

    // 使用Seq创建DataFrame
    val df = data.toDF("name", "age")

    // 使用DataFrame进行过滤和转换操作
    val filteredDF = df.filter($"age" > 30)
    val transformedDF = filteredDF.withColumn("ageGroup", when($"age" < 40, "Young").otherwise("Old"))

    // 使用DataFrame API进行聚合操作
    val resultDF = transformedDF.groupBy("ageGroup").count()

    resultDF.show()

    spark.stop()
  }
}