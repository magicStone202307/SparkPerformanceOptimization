import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder()
  .appName("FilterPushdownExample")
  .master("local[*]")
  .getOrCreate()

// 读取员工表
val employeesDF = spark.read.format("csv")
  .option("header", "true")
  .load("employees.csv")

// 执行筛选和排序操作
val filteredDF = employeesDF.filter("age > 30").orderBy($"salary".desc)

// 显示查询结果
filteredDF.show()