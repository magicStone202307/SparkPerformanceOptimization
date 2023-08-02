import org.apache.spark.sql.SparkSession

// 创建 SparkSession
val spark = SparkSession.builder
.appName("SparkKubernetesExample")
.config("spark.executor.instances", "2") // 设置 Executor 实例数量为 2
.config("spark.executor.cores", "2") // 设置每个 Executor 使用的 CPU 核心数为 2
.config("spark.executor.memory", "2g") // 设置每个 Executor 可用的内存量为 2GB
.config("spark.dynamicAllocation.enabled", "true") // 启用动态资源分配
.config("spark.dynamicAllocation.minExecutors", "1") // 设置动态分配的最小 Executor 数量为 1
.config("spark.dynamicAllocation.maxExecutors", "5") // 设置动态分配的最大 Executor 数量为 5
.config("spark.kubernetes.container.image", "<container-image>")
.config("spark.kubernetes.namespace", "<namespace>")
.getOrCreate()

// 读取数据
val data = spark.read.csv("data.csv").toDF("category")

// 执行数据处理和计算任务
val result = data.groupBy("category").count()

// 输出结果
result.show()

// 停止 SparkSession
spark.stop()