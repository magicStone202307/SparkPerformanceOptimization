import org.apache.spark.sql.functions._

// 假设有一个orders表，包含字段order_id和order_amount，其中order_id存在数据倾斜
val orders = spark.table("orders")

// 检测并获取倾斜的key，这里以order_id为例
val skewedKeys = orders.groupBy("order_id").count().filter("count > 100").select("order_id")

// 生成随机前缀，并与倾斜的key拼接
val randomPrefix = scala.util.Random.nextInt(10000)  // 生成随机前缀
val skewedKeyWithPrefix = skewedKeys.withColumn("key_with_prefix", concat(lit(randomPrefix), col("order_id")))

// 将原始数据和带随机前缀的key进行关联，并重分区
val result = orders.join(skewedKeyWithPrefix, orders("order_id") === skewedKeyWithPrefix("order_id"), "left")
  .select(orders("order_id"), orders("order_amount"))
  .repartition($"key_with_prefix")

// 继续后续操作，如聚合、写入等