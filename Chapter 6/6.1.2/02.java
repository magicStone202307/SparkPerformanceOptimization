import org.apache.spark.sql.SparkSession

// 自定义分区器，根据键的首字母进行分区
class FirstLetterPartitioner(numPartitions: Int) extends org.apache.spark.Partitioner {
  override def numPartitions: Int = numPartitions

  override def getPartition(key: Any): Int = {
    val letter = key.toString.charAt(0).toLower // 获取键的首字母并转为小写
    letter.toInt % numPartitions // 根据首字母的ASCII码对分区数取模
  }
}

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("Custom Partitioner Example")
  .master("local")
  .getOrCreate()

// 原始的键值对RDD
val dataRDD = spark.sparkContext.parallelize(Seq(
  ("Apple", 1), ("Banana", 2), ("Cherry", 3), ("Durian", 4), ("Elderberry", 5)
))

// 使用自定义分区器对键值对RDD进行重分区
val partitionedRDD = dataRDD.partitionBy(new FirstLetterPartitioner(3))

// 打印重分区后的分区数和每个分区的数据
partitionedRDD.glom().foreach { partition =>
  println(s"Partition: ${partition.toList}")
}

// 关闭SparkSession
spark.stop()
