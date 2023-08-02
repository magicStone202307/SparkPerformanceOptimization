// 使用SHUFFLE_HASH进行Join，并计算每个prefixedProductId的总销售额
val joinedDF = skewedSalesDFWithPrefix.join(
multipliedProductsDF.hint("SHUFFLE_HASH"),
skewedSalesDFWithPrefix("prefixedProductId") === multipliedProductsDF("prefixedProductId"),
"inner"
).groupBy(skewedSalesDFWithPrefix("prefixedProductId"))
.agg(sum(skewedSalesDFWithPrefix("salesAmount")).alias("totalSalesAmount"))