val smallTable = spark.table("small_table").as("s")
val largeTable = spark.table("large_table").as("l")

val result = largeTable.join(broadcast(smallTable), col("l.id") === col("s.id"))
result.show()