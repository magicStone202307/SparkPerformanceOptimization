
// 定义输入路径
val inputPath = "hdfs://localhost:9000/test-data"

// 定义期望的输出结果
val expectedOutput = Seq(Seq("a", "b"), Seq("c", "d", "e"), Seq("f", "g", "h", "i"))

// 读取指定路径下的文本文件，使用 map 方法将每行文本按空格拆分为 Seq，并使用 collect 方法获取 RDD 内容
val result = spark.read.textFile(inputPath).map(_.split(" ").toSeq).collect()

// 使用 assert 方法比较 result 和 expectedOutput 是否相等
assert(result === expectedOutput)