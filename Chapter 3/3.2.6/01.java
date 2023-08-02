import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder
  .appName("YourSparkApplication")
  .config("spark.executor.memory", "4g")
  .getOrCreate()