假设有一个包含订单信息的表，其中包含订单号（order_id）和订单金额（amount）两个字段。
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .appName("Spark SQL Data Skew Optimization")
  .getOrCreate()

// 读取订单信息表
val ordersDF = spark.read
  .format("csv")
  .option("header", "true")
  .load("orders.csv")

// 创建临时视图
ordersDF.createOrReplaceTempView("orders")

// 步骤1：预先聚合部分数据
val preAggregatedDF = spark.sql("""
  SELECT order_id, SUM(amount) AS pre_aggregated_amount
  FROM orders
  GROUP BY order_id
""")

// 创建临时视图
preAggregatedDF.createOrReplaceTempView("pre_aggregated_orders")

// 步骤2：使用预先聚合的结果进行最终聚合
val finalAggregatedDF = spark.sql("""
  SELECT order_id, SUM(pre_aggregated_amount) AS final_amount
  FROM pre_aggregated_orders
  GROUP BY order_id
""")

// 显示最终结果
finalAggregatedDF.show()