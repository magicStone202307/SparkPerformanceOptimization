import org.apache.spark.sql.SparkSession
import org.apache.spark.serializer.KryoSerializer

case class DataRecord(id: Int, name: String, age: Int)

object SparkSerializationOptimization {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Serialization Optimization")
      .master("local[*]")
      .config("spark.serializer", classOf[KryoSerializer].getName)
      .getOrCreate()

    spark.conf.registerKryoClasses(Array(classOf[DataRecord]))

    import spark.implicits._
    
    val data = Seq(
      DataRecord(1, "Alice", 25),
      DataRecord(2, "Bob", 30),
      DataRecord(3, "Charlie", 35)
    )

    val df = spark.sparkContext.parallelize(data).toDF()

    // 进行数据处理和计算
    val result = df.select($"id", $"name").collect()

    // 将结果写入文件
    spark.sparkContext.parallelize(result).write.text("output")

    spark.stop()
  }
}