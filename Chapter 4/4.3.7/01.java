import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .appName("Spark SQL Data Skew Handling with Sorting Strategy")
  .getOrCreate()

// 读取订单信息表
val ordersDF = spark.read
  .format("csv")
  .option("header", "true")
  .load("orders.csv")

// 创建临时视图
ordersDF.createOrReplaceTempView("orders")

// 步骤1：按订单号进行数据分区
val partitionedDF = spark.sql("""
  SELECT *, CAST(order_id AS INT) % 10 AS partition_id
  FROM orders
""")

// 创建临时视图
partitionedDF.createOrReplaceTempView("partitioned_orders")

// 步骤2：对存在倾斜的键进行排序
val sortedDF = spark.sql("""
  SELECT *
  FROM partitioned_orders
  ORDER BY order_id
""")

// 创建临时视图
sortedDF.createOrReplaceTempView("sorted_orders")

// 步骤3：重分区并进行聚合
val finalAggregatedDF = spark.sql("""
  SELECT order_id, SUM(amount) AS total_amount
  FROM sorted_orders
  GROUP BY order_id
""")

// 显示最终结果
finalAggregatedDF.show()