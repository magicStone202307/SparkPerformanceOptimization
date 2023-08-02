import org.apache.spark.{SparkConf, SparkContext}

object YarnExample {
  def main(args: Array[String]): Unit = {
    // 创建Spark配置对象
    val conf = new SparkConf()
      .setMaster("yarn")
      .setAppName("YarnExample")
      .set("spark.executor.memory", "2g")
      .set("spark.executor.instances", "2")
    
    // 创建Spark上下文对象
    val sc = new SparkContext(conf)
    
    // 执行Spark操作
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5))
    val sum = rdd.reduce(_ + _)
    println("Sum: " + sum)
    
    // 关闭Spark上下文对象
    sc.stop()
  }
}