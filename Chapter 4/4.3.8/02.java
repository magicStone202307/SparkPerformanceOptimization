val skewedKeys = Seq("skewed_key1", "skewed_key2") // 倾斜键列表

val targetTable = originalTable.filter(!col("key").isin(skewedKeys)) // 过滤掉倾斜键数据，创建新的目标表