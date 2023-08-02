import org.apache.spark.{SparkConf, SparkContext}

// 创建Spark上下文
val conf = new SparkConf().setAppName("Local App").setMaster("local")
val sc = new SparkContext(conf)

// 创建一个累加器来进行全局结果的累加
val globalAccumulator = sc.longAccumulator("global_accumulator")

// 并行化创建一个数据集合
val data = sc.parallelize(Seq(1, 2, 3, 4, 5))

// 在各个节点上进行局部聚合操作，并将结果累加到全局累加器中
def localAggregation(value: Int): Unit = {
  // 在各个节点上进行局部聚合操作
  val localResult = value * 2
  // 将局部结果累加到全局累加器中
  globalAccumulator.add(localResult)
}

// 对数据集合中的每个元素应用局部聚合操作
data.foreach(localAggregation)

// 输出全局累加器的结果
println("Global result: " + globalAccumulator.value)