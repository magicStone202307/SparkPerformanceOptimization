import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .master("<master-url>")
  .appName("<application-name>")
  .config("spark.executor.cores", "<num-cores>")
  .config("spark.executor.memory", "<memory>")
  .config("spark.executor.instances", "<num-executors>")
  .getOrCreate()