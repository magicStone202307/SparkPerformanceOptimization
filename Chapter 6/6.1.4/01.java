import org.apache.spark.sql.SparkSession

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("Broadcast Variables Example")
  .master("local")
  .getOrCreate()

// 创建一个需要广播的共享变量
val sharedData = Array(("A", 1), ("B", 2), ("C", 3))
val broadcastData = spark.sparkContext.broadcast(sharedData)

// 原始的键值对RDD
val dataRDD = spark.sparkContext.parallelize(Seq(
  ("A", 10), ("B", 20), ("C", 30), ("D", 40), ("E", 50)
))

// 使用广播变量来优化数据倾斜
val resultRDD = dataRDD.map { case (key, value) =>
  val sharedValue = broadcastData.value.find(_._1 == key).map(_._2).getOrElse(0)
  (key, value + sharedValue)
}

// 打印计算结果
resultRDD.collect().foreach(println)

// 关闭SparkSession
spark.stop()
