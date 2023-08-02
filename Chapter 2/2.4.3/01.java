import org.apache.spark.sql.SparkSession

object PredicatePushdownExample {
  def main(args: Array[String]): Unit = {
    // 创建 SparkSession
    val spark = SparkSession.builder()
      .appName("PredicatePushdownExample")
      .master("local[*]")
      .getOrCreate()

    // 读取数据源
    val df = spark.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("path/to/data.csv")

    // 假设要过滤年龄大于等于 18 岁的用户
    val filteredDf = df.filter("age >= 18")

    // 执行查询操作
    filteredDf.show()

    // 假设要统计过滤后的用户数
    val count = filteredDf.count()

    // 停止 SparkSession
    spark.stop()
  }
}