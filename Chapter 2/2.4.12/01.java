import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .appName("ReduceByKeyExample")
  .master("local[*]")
  .getOrCreate()

val data = spark.sparkContext.parallelize(Seq("apple", "banana", "apple", "orange", "banana"))

// 将单词转换为键值对，初始计数为1
val wordCounts = data.map(word => (word, 1))
  // 按键进行聚合
  .reduceByKey(_ + _)

// 打印单词计数结果
wordCounts.collect().foreach(println)

spark.stop()