// 检查待写入数据分区是否存在数据
val existingPartitions = spark.sql("SHOW PARTITIONS 推荐请求表").collect().map(.getString(0))
val partitionsToWrite = recommendationRequestTable.select("hour_partition").distinct().collect().map(.getString(0))

// 获取需要写入的分区中已存在的分区
val partitionsWithExistingData = partitionsToWrite.filter(existingPartitions.contains)

// 将已存在数据的分区写入历史分区中
partitionsWithExistingData.foreach(partition => {
spark.sql(s"INSERT OVERWRITE TABLE 历史分区表 PARTITION(hour_partition='$partition') SELECT * FROM 推荐请求表 WHERE hour_partition='$partition'")
})

// 将推荐请求表按小时分区写入Hive表中，使用Overwrite模式
recommendationRequestTable.write.partitionBy("hour_partition").mode("overwrite").saveAsTable("推荐请求表")