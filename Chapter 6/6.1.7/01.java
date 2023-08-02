
import org.apache.spark.sql.SparkSession
import scala.util.Random

// 创建 SparkSession
val spark = SparkSession.builder()
  .appName("Random Prefix and Repartition Example")
  .master("local")
  .getOrCreate()

// 原始的两个键值对 RDD
val bigDataRDD = spark.sparkContext.parallelize(Seq(
  ("A", 10), ("B", 20), ("C", 30), ("D", 40), ("E", 50)
))
val smallDataRDD = spark.sparkContext.parallelize(Seq(
  ("A", "Apple"), ("B", "Banana"), ("C", "Cherry")
))

// 为较大数据集添加随机前缀
val randomPrefixRDD = bigDataRDD.map { case (key, value) =>
  val randomPrefix = Random.nextInt(10)
  (randomPrefix.toString + "_" + key, value)
}

// 扩容较小数据集
val expandedRDD = smallDataRDD.flatMap { case (key, value) =>
  (0 to 9).map(i => (i + "_" + key, value))
}

// 进行join操作
val resultRDD = expandedRDD.join(randomPrefixRDD)

// 打印计算结果
resultRDD.collect().foreach(println)

// 关闭 SparkSession
spark.stop()