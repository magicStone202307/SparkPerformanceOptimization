import org.apache.spark.sql.SparkSession

// 创建 SparkSession
val spark = SparkSession.builder()
  .appName("Map Join Example")
  .master("local")
  .getOrCreate()

// 原始的两个键值对 RDD
val bigDataRDD = spark.sparkContext.parallelize(Seq(
  ("A", 10), ("B", 20), ("C", 30), ("D", 40), ("E", 50)
))
val smallDataRDD = spark.sparkContext.parallelize(Seq(
  ("A", "Apple"), ("B", "Banana"), ("C", "Cherry")
))

// 将小数据集转换为广播变量
val smallDataBroadcast = spark.sparkContext.broadcast(smallDataRDD.collectAsMap())

// 对大数据集进行 Map join
val resultRDD = bigDataRDD.map { case (key, value) =>
  val smallValue = smallDataBroadcast.value.get(key)
  (key, (value, smallValue))
}

// 打印计算结果
resultRDD.collect().foreach(println)

// 关闭 SparkSession
spark.stop()