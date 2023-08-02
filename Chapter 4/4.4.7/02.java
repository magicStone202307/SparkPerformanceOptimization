// 执行SQL查询获取倾斜的keys
val skewedKeysDF = spark.sql("""
  SELECT s.productId, COUNT(*) AS count
  FROM sales AS s
  INNER JOIN products AS p ON s.productId = p.productId
  GROUP BY s.productId
  HAVING COUNT(*) >= 5
""")