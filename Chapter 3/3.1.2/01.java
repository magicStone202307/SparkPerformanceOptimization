import org.apache.spark.sql.SparkSession

object SparkStandaloneExample {
  def main(args: Array[String]): Unit = {
    // 创建SparkSession对象
    val spark = SparkSession.builder()
      .appName("SparkStandaloneExample")
      .master("spark://<master-node>:7077")  // 设置Master节点的URL
      .getOrCreate()

    // 执行一些Spark操作
    val data = Seq(1, 2, 3, 4, 5)
    val sum = spark.sparkContext.parallelize(data).reduce(_ + _)
    println("Sum: " + sum)

    // 关闭SparkSession对象
    spark.stop()
  }
}