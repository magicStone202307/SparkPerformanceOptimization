// 将nonSkewedSalesDF和nonSkewedProductsDF进行Join，并计算每个订单的总销售额
val nonSkewedJoinedDF = nonSkewedSalesDF.join(
nonSkewedProductsDF.hint("SHUFFLE_HASH"),
nonSkewedSalesDF("productId") === nonSkewedProductsDF("productId"),
"inner"
).groupBy(nonSkewedSalesDF("orderId"))
.agg(sum(nonSkewedSalesDF("salesAmount")).alias("orderTotalSales"))