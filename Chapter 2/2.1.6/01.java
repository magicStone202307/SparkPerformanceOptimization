import org.apache.spark.SparkContext

object DistributedCacheExample {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local", "DistributedCacheExample")

    // 将需要缓存的数据加载到 RDD
    val dataRDD = sc.textFile("data.txt")

    // 创建一个需要缓存的广播变量
    val broadcastVar = sc.broadcast(Seq("keyword1", "keyword2", "keyword3"))

    // 在每个分区上对数据进行处理，使用广播变量进行关键词匹配
    val resultRDD = dataRDD.mapPartitions { partition =>
      val keywords = broadcastVar.value
      partition.filter { line =>
        keywords.exists(line.contains)
      }
    }

    // 缓存结果数据
    resultRDD.cache()

    // 对缓存数据进行操作，如计数或保存到文件
    val count = resultRDD.count()
    println("Count: " + count)

    // 在使用完缓存数据后，手动释放缓存
    resultRDD.unpersist()

    sc.stop()
  }
}