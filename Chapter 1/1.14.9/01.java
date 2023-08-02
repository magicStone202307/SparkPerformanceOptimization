import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .appName("Shuffle Performance Analysis")
  .master("local[*]")
  .getOrCreate()

import spark.implicits._

// 读取数据
val inputRDD = spark.sparkContext.textFile("input.txt")
val dataDF = inputRDD.toDF("line")

// 过滤数据
val filteredDF = dataDF.filter($"line".contains("keyword"))

// 切分单词并计数
val wordCountDF = filteredDF
  .flatMap(row => row.getString(0).split(" "))
  .map(word => (word, 1))
  .groupBy("_1")
  .sum("_2")
  .toDF("word", "count")

// 展示结果
wordCountDF.show()