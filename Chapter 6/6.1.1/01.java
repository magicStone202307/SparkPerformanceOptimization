import org.apache.spark.{SparkConf, SparkContext}

// 创建SparkContext
val conf = new SparkConf().setAppName("AccumulatorExample").setMaster("local")
val sc = new SparkContext(conf)

// 创建一个累加器来统计正数个数
val positiveCountAccumulator = sc.longAccumulator("PositiveCountAccumulator")
// 创建一个累加器来统计负数个数
val negativeCountAccumulator = sc.longAccumulator("NegativeCountAccumulator")

// 原始数据RDD
val numbersRDD = sc.parallelize(Seq(-1, 2, -3, 4, -5, 6, -7, 8, -9, 10))

// 使用累加器进行全局聚合
numbersRDD.foreach { number =>
  if (number > 0) {
    positiveCountAccumulator.add(1) // 正数累加器加1
  } else if (number < 0) {
    negativeCountAccumulator.add(1) // 负数累加器加1
  }
}

// 打印累加器的结果
println(s"Positive Count: ${positiveCountAccumulator.value}")
println(s"Negative Count: ${negativeCountAccumulator.value}")

// 关闭SparkContext
sc.stop()
