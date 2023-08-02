import org.apache.spark.sql.SparkSession

object SparkOptimizationExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark Optimization Example")
      .master("local")
      .getOrCreate()

    // 读取学生信息数据集
    val studentData = spark.read
      .format("csv")
      .option("header", "true")
      .load("student_data.csv")

    // 转换数据类型并进行聚合计算
    val result = studentData.rdd
      .map(row => (row.getString(0), row.getInt(1)))
      .groupByKey()
      .mapValues(values => values.sum.toDouble / values.size)
      .sortBy(_._2, ascending = false)
      .collect()

    // 打印结果
    result.foreach(println)

    spark.stop()
  }
}