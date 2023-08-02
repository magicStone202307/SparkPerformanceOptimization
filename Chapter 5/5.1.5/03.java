import org.apache.spark.sql.SparkSession

def getOrCreateSparkSession(appName: String): SparkSession = {
SparkSession.builder()
.appName(appName)
.config("parquet.strings.signed-min-max.enabled", "true")
.config("spark.sql.parquet.compression.codec", "snappy")
.enableHiveSupport()
.getOrCreate()
}

val spark = getOrCreateSparkSession("MyApp")