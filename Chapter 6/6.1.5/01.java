import org.apache.spark.sql.SparkSession

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("Dynamic Partition Sizing Example")
  .master("local")
  .getOrCreate()

// 原始的键值对RDD
val dataRDD = spark.sparkContext.parallelize(Seq(
  ("A", 10), ("B", 20), ("C", 30), ("D", 40), ("E", 50)
))

// 统计每个键的数据量
val counts = dataRDD.countByKey()

// 获取最大数据量和最小数据量
val maxCount = counts.values.max
val minCount = counts.values.min

// 计算动态调整的分区数
val numPartitions = (maxCount.toDouble / minCount.toDouble).ceil.toInt

// 使用动态调整的分区数重新分区RDD
val resizedRDD = dataRDD.repartition(numPartitions)

// 执行相应的计算操作
// ...

// 关闭SparkSession
spark.stop()