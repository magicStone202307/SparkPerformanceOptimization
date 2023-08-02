val table1 = spark.table("table1").as("t1")
val table2 = spark.table("table2").as("t2")

val partitionedTable1 = table1.repartition(col("partition_key"))
val partitionedTable2 = table2.repartition(col("partition_key"))

val result = partitionedTable1.join(partitionedTable2, col("t1.id") === col("t2.id"))
result.show()