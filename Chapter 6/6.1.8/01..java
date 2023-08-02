// 1. 采样倾斜key
val skewedKeys = order_table.sample(false, 0.1)  // 采样10%的数据作为倾斜key的样本

// 2. 分拆数据倾斜的表
val skewedOrders = order_table.filter(row => skewedKeys.contains(row.productId))
val nonSkewedOrders = order_table.filter(row => !skewedKeys.contains(row.productId))

// 3. 分拆另一个表
val skewedProducts = product_table.filter(row => skewedKeys.contains(row.productId))
val nonSkewedProducts = product_table.filter(row => !skewedKeys.contains(row.productId))

// 4. 进行join操作
val skewedJoinResult = skewedOrders.join(skewedProducts, "productId")
val nonSkewedJoinResult = nonSkewedOrders.join(nonSkewedProducts, "productId")

// 5. 合并结果
val finalResult = skewedJoinResult.union(nonSkewedJoinResult)