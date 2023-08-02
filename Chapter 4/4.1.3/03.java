val data = List(("A", 1), ("B", 2), ("A", 3), ("B", 4), ("C", 5))
val rdd = spark.sparkContext.parallelize(data)
val result = rdd.aggregateByKey((0, 0))(
  (acc, value) => (acc._1 + value, acc._2 + 1),
  (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
)
result.foreach(println)