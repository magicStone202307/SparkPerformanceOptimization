import org.apache.spark.sql.functions._

// 过滤user_id为空或者video_id为空的数据
val filteredData = recommendationRequestTable.filter(col("user_id").isNull || col("video_id").isNull)

// 将过滤后的数据写入推荐请求表非法数据表对应的分区中
filteredData.write.partitionBy("day_partition", "hour_partition").mode("overwrite").saveAsTable("推荐请求表非法数据表")

// 将过滤后的数据进行正常写入
val validData = recommendationRequestTable.filter(col("user_id").isNotNull && col("video_id").isNotNull)
validData.write.partitionBy("day_partition", "hour_partition").mode("overwrite").saveAsTable("推荐请求表")