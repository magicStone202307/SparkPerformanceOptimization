
// 导入 SparkSession 库
import org.apache.spark.sql.SparkSession

// 创建一个新的 SparkSession
val spark = SparkSession.builder()
    .appName("Test") // 设置应用程序名称
    .master("local[*]") // 设置 master URL 为 local
    .getOrCreate() // 如果 SparkSession 不存在则创建新的，否则使用已有的