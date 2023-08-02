
val rdd = sparkContext.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2)

// 使用map算子
val result1 = rdd.map(x => x * 2).collect()

// 使用mapPartitions算子
val result2 = rdd.mapPartitions(iter => iter.map(x => x * 2)).collect()