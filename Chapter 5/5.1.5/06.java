// 添加当日天级和小时级分区列
val recommendationRequestTable = recommendationRequestDF.withColumn("day_partition", date_format($"timestamp", "yyyy-MM-dd"))
.withColumn("hour_partition", date_format($"timestamp", "HH"))