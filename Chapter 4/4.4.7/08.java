// 计算每个订单的总销售额
val skewedJoinedDF = cleanedJoinedDF.groupBy("productId")
.agg(sum("totalSalesAmount").alias("orderTotalSales"))