import org.apache.spark.sql.functions.substring

// 去掉前缀
val cleanedJoinedDF = joinedDF.withColumn("productId", substring(col("prefixedProductId"), 3, 100))
.drop("prefixedProductId")