// 添加随机数字前缀到skewedSalesDF的productId
val skewedSalesDFWithPrefix = skewedSalesDF.withColumn("prefixedProductId", concat(lit(rand(1, 5).cast("int")), lit("_"), col("productId")))

// 复制skewedProductsDF数据并添加前缀
val n = 5 // 复制的份数
var multipliedProductsDF = skewedProductsDF
for (i <- 1 until n) {
val prefix = lit(s"${n - i}_")
multipliedProductsDF = multipliedProductsDF.unionAll(skewedProductsDF.withColumn("prefixedProductId", concat(prefix, col("productId"))))
}