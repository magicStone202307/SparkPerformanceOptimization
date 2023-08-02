import scala.util.{Try, Success, Failure}

// 从日志文件读取数据并生成DataFrame
val logData = Try(spark.sparkContext.textFile("日志文件路径")) match {
  case Success(data) => data
  case Failure(ex) =>
    println(s"Failed to read log data: ${ex.getMessage}")
    spark.sparkContext.emptyRDD[String]
}

val recommendationRequestTable = logData
  .mapPartitions(itr => {
    itr.flatMap(line => RecommendationRequestTable.parseLog(line))
  })
  .toDF("user_id", "video_id", "...")

// 显示推荐请求表数据
recommendationRequestTable.show()