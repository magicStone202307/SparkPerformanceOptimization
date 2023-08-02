import org.apache.spark.{SparkConf, SparkContext}

// 创建Spark上下文
val conf = new SparkConf().setAppName("Distributed Counter App").setMaster("local")
val sc = new SparkContext(conf)

// 创建一个分布式计数器累加器
val counter = sc.longAccumulator("distributed_counter")

// 并行化创建一个数据集合
val data = sc.parallelize(Seq(1, 2, 3, 4, 5))

// 在每个节点上对数据进行计数操作，并将结果累加到分布式计数器中
data.foreach { value =>
  // 在每个节点上进行计数操作
  val count = 1
  // 将计数结果累加到分布式计数器中
  counter.add(count)
}

// 输出分布式计数器的结果
println("Global count: " + counter.value)