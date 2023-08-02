// 从文件中读取数据，生成一个RDD
val inputRDD = sparkContext.textFile("input.txt")

// 过滤包含特定关键字的行，生成一个新的RDD
val filteredRDD = inputRDD.filter(line => line.contains("keyword"))

// 将每行文本按空格拆分成单词，并映射为(Key, Value)的形式，生成一个新的RDD
val wordCountRDD = filteredRDD.flatMap(line => line.split(" ")).map(word => (word, 1))

// 根据单词进行分组并对每组的值进行累加，生成一个新的RDD
val finalRDD = wordCountRDD.reduceByKey(_ + _)