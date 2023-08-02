import org.apache.spark.sql.SparkSession

def getOrCreateSparkSession(appName: String): SparkSession = {
  SparkSession.builder()
    .appName(appName)
    .enableHiveSupport()
    .getOrCreate()
}

val spark = getOrCreateSparkSession("MyApp")