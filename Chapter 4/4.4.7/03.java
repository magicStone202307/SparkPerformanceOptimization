// 获取倾斜的keys和不倾斜的keys
val skewedKeys = skewedKeysDF.select("productId").collect().map(_.getString(0)).toList
val skewedKeysBroadcast = spark.sparkContext.broadcast(skewedKeys)

// 拆分productsDF表
val skewedProductsDF: DataFrame = productsDF.filter(col("productId").isin(skewedKeysBroadcast.value: _*))
val nonSkewedProductsDF: DataFrame = productsDF.filter(!col("productId").isin(skewedKeysBroadcast.value: _*))

// 拆分salesDF表
val skewedSalesDF: DataFrame = salesDF.filter(col("productId").isin(skewedKeysBroadcast.value: _*))
val nonSkewedSalesDF: DataFrame = salesDF.filter(!col("productId").isin(skewedKeysBroadcast.value: _*))
