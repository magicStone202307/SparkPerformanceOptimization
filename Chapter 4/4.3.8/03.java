val skewedData = originalTable.filter(col("key").isin(skewedKeys)) // 获取倾斜键数据

val processedSkewedData = skewedData.withColumn("newPartition", hash(col("key")) % numPartitions) // 使用哈希算法将倾斜键数据分散到多个分区中

val finalTable = partitionedTable.union(processedSkewedData) // 将非倾斜数据和处理后的倾斜数据合并为最终表