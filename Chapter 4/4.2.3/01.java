val table1 = spark.table("table1").as("t1")
val table2 = spark.table("table2").as("t2")

val bucketedTable1 = table1.write.bucketBy(100, "bucket_column").saveAsTable("bucketed_table1")
val bucketedTable2 = table2.write.bucketBy(100, "bucket_column").saveAsTable("bucketed_table2")

val result = spark.table("bucketed_table1").as("t1")
  .join(spark.table("bucketed_table2").as("t2"), col("t1.id") === col("t2.id"))
result.show()