// 定义分层采样的规则和比例
val samplingRules = Map("Head Users" -> 0.1, "Regular Users" -> 0.5, "New Users" -> 0.2)

// 分别采样不同类型的用户数据
val sampledData = spark.sql("SELECT * FROM users")
  .where(col("user_type").isin(samplingRules.keys.toSeq: _*))
  .stat.sampleBy("user_type", samplingRules, 42L)

// 根据样本数据进行处理
val processedData = spark.sql("SELECT * FROM orders")
  .join(broadcast(sampledData), Seq("user_id"))

// 进行后续的查询操作
val result = processedData.groupBy("user_type").agg(count("*"))