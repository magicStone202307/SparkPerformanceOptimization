// 设置spark.sql.shuffle.partitions参数为自定义的分区数
spark.conf.set("spark.sql.shuffle.partitions", "500")

// 进行数据倾斜处理的查询操作
val result = spark.sql("SELECT order_date, sum(order_amount) FROM orders GROUP BY order_date")

// 执行查询操作
result.show()