import org.apache.spark.sql.SparkSession

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("PartialAggregationExample")
  .master("local")
  .getOrCreate()

// 原始数据RDD
val numbersRDD = spark.sparkContext.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

// 将数据分成两个分区
val partitionedRDD = numbersRDD.repartition(2)

// 在每个分区上进行部分聚合
val partialAggregationRDD = partitionedRDD.mapPartitions { numbers =>
  var sum = 0
  numbers.foreach { number =>
    sum += number
  }
  Iterator(sum)
}

// 对部分聚合结果进行全局聚合
val finalResult = partialAggregationRDD.reduce(_ + _)

// 打印最终结果
println(s"Final Result: $finalResult")

// 关闭SparkSession
spark.stop()
