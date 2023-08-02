import org.apache.spark.sql.SparkSession

object SparkParameterOptimizationExample {
  def main(args: Array[String]): Unit = {
    // 创建 SparkSession
    val spark = SparkSession.builder()
      .appName("SparkParameterOptimizationExample")
      .getOrCreate()

    // 读取数据
    val data = spark.read.csv("data.csv").toDF("category")

    // 开始性能优化步骤
    // 步骤1：发现性能瓶颈
    // 在实际运行中观察到任务执行速度较慢，应用程序运行时间过长。

    // 步骤2：定位性能瓶颈
    // 通过观察垃圾回收日志，发现频繁的Full GC和长时间的垃圾回收暂停。

    // 步骤3：调整JVM参数
    // 根据观察到的性能瓶颈，决定调整以下JVM参数：
    // - 增加堆内存大小：-Xmx 参数用于设置堆的最大内存，可以增加该值来减少频繁的垃圾回收。
    // - 调整垃圾回收器：根据垃圾回收日志和应用程序的特点，选择合适的垃圾回收器，并配置相应的参数。

    // 调整JVM参数示例：
    spark.conf.set("spark.executor.memory", "4g") // 设置每个Executor的堆内存大小
    spark.conf.set("spark.executor.memoryOverhead", "1g") // 设置Executor的堆外内存大小

    // 步骤4：解决性能瓶颈
    // 重新运行应用程序并监测性能指标，如任务执行时间、垃圾回收日志等。通过调整JVM参数后，观察性能是否有所改善。

    // 执行数据处理和计算任务
    val result = data.groupBy("category").count()

    // 输出结果
    result.show()

    // 停止 SparkSession
    spark.stop()
  }
}