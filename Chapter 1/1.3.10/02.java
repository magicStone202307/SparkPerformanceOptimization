
// 定义数据集合
val data = Seq("a b", "c d e", "f g h i")

// 通过 SparkSession 获取 SparkContext，并使用 parallelize 方法创建 RDD
val rdd = spark.sparkContext.parallelize(data)

// 将 RDD 内容保存到指定的 HDFS 文件中
rdd.saveAsTextFile("hdfs://localhost:9000/test-data")