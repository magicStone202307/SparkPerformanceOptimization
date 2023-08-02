import org.apache.spark.sql.SparkSession

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("Order Revenue Calculation")
  .master("local")
  .getOrCreate()

// 读取产品表数据
val productsDF = spark.read.format("csv")
  .option("header", "true")
  .load("path/to/products.csv")

// 读取订单表数据
val salesDF = spark.read.format("csv")
  .option("header", "true")
  .load("path/to/sales.csv")

// 注册临时表
productsDF.createOrReplaceTempView("products")
salesDF.createOrReplaceTempView("sales")

// 执行SQL查询
val resultDF = spark.sql("""
  SELECT SUM(p.price * s.quantity) AS revenue, s.orderId
  FROM sales AS s
  INNER JOIN products AS p ON s.productId = p.productId
  GROUP BY s.orderId
""")

// 显示结果
resultDF.show()