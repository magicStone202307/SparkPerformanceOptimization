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

    // 缓存数据集，避免重复计算
    studentData.cache()

    // 使用DataFrame API进行优化
    val result = studentData
      .groupBy("studentId")
      .agg(avg("score").as("averageScore"))
      .orderBy(desc("averageScore"))
      .collect()

    // 打印结果
    result.foreach(println)

    spark.stop()
  }
}
