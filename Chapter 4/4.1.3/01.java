val data = List(("A", 1), ("B", 2), ("A", 3), ("B", 4), ("C", 5))
val rdd = spark.sparkContext.parallelize(data)
val result = rdd.reduceByKey(_ + _)
result.foreach(println)