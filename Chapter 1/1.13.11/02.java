import org.apache.spark.{SparkConf, SparkContext}

// 定义一个 WordCount 对象
object WordCount {

  // 程序的入口点
  def main(args: Array[String]): Unit = {

    // 创建 SparkConf 对象
    val conf = new SparkConf().setAppName("Word Count")

    // 创建 SparkContext 对象
    val sc = new SparkContext(conf)

    // 从命令行参数中获取输入路径和输出路径
    val inputFile = args(0)
    val outputFile = args(1)

    // 读取输入文件
    val textFile = sc.textFile(inputFile)

    // 对每一行进行分词，并映射成 (word, 1) 的形式
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))

    // 根据单词进行累加计数
    val wordCounts = counts.reduceByKey(_ + _)

    // 将结果保存到输出路径
    wordCounts.saveAsTextFile(outputFile)

    // 停止 SparkContext
    sc.stop()
  }
}

