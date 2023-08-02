import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

// 创建SparkSession
val spark = SparkSession.builder()
  .appName("OrderProcessing")
  .master("local[*]")
  .getOrCreate()

// 定义订单数据的Schema
val schema = StructType(Seq(
  StructField("orderNumber", StringType, nullable = false),
  StructField("userID", StringType, nullable = false),
  StructField("productID", StringType, nullable = false),
  StructField("quantity", IntegerType, nullable = false),
  StructField("amount", DoubleType, nullable = false)
))

// 读取并压缩订单数据
val ordersDF = spark.read
  .format("csv")
  .option("header", "false")
  .option("delimiter", ",")
  .option("inferSchema", "false")
  .option("compression", "gzip")  // 指定压缩格式为gzip

  .schema(schema)
  .load("path/to/orders.txt")
  .coalesce(4)  // 控制并行度

// 解析订单数据并进行处理
val processedDF = ordersDF.map { row =>
  val orderNumber = row.getString(0)
  val userID = row.getString(1)
  val productID = row.getString(2)
  val quantity = row.getInt(3)
  val amount = row.getDouble(4)

  // 对订单数据进行业务处理...

  (userID, amount)
}.toDF("userID", "amount")

// 对用户订单金额进行统计分析
val resultDF = processedDF.groupBy("userID").sum("amount")

// 输出结果
resultDF.show()

// 停止SparkSession
spark.stop()