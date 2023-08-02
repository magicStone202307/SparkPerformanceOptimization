import org.apache.spark.sql.SparkSession

object Constants {
  val SparkVersion = "2.3" // 将Spark版本定义为常量
  val InputPath = "/path/to/input" // 将输入路径定义为常量
  val OutputPath = "/path/to/output" // 将输出路径定义为常量
  val MaxIterations = 10 // 将最大迭代次数定义为常量
}

object SparkApplication {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark Application")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val inputData = spark.read.csv(Constants.InputPath) // 使用常量作为输入路径

    // 在应用程序中使用常量进行计算和处理
    val result = inputData.filter($"age" > 18).groupBy($"gender").count()

    result.write.csv(Constants.OutputPath) // 使用常量作为输出路径

    spark.stop()
  }
}