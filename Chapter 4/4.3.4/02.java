import org.apache.spark.sql.functions._

// 假设有一个orders表，包含字段order_id和order_amount，其中order_id存在数据倾斜
val orders = spark.table("orders")

// 检测并获取倾斜的key，这里以order_id为例
val skewedKeys = orders.groupBy("order_id").count().filter("count > 100").select("order_id")

// 对倾斜的key进行哈希映射，使用哈希函数将key映射到不同的分区
val hashPartition = udf { (key: String, numPartitions: Int) => key.hashCode % numPartitions }

// 将原始数据进行重分区，使用哈希映射后的key进行分区
val numPartitions = 100  // 设置分区数
val result = orders.withColumn("hashed_key", hashPartition($"order_id", lit(numPartitions)))
  .repartition($"hashed_key")

// 继续后续操作，如聚合、写入等