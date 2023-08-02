import org.apache.spark.sql.SparkSession

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("Key-Value Pair Redistribution Example")
  .master("local")
  .getOrCreate()

// 原始的键值对RDD
val dataRDD = spark.sparkContext.parallelize(Seq(
  ("A", 1), ("B", 2), ("C", 3), ("D", 4), ("E", 5)
))

// 使用哈希分区器对键值对RDD进行重分区
val partitionedRDD = dataRDD.partitionBy(new org.apache.spark.HashPartitioner(2))

// 打印重分区后的分区数和每个分区的数据
partitionedRDD.glom().foreach { partition =>
  println(s"Partition: ${partition.toList}")
}

// 关闭SparkSession
spark.stop()