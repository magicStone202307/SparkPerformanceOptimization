import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SparkIntegrationTest extends AnyFunSuite with BeforeAndAfter {

  private var spark: SparkSession = _
  private var input: DataFrame = _

  before {
    // 初始化 SparkSession 对象和输入的 DataFrame（类似于数据表的结构化数据集）
    spark = SparkSession.builder().appName("SparkIntegrationTest").master("local[*]").getOrCreate()
    input = spark.read.csv("input.csv")
  }

  after {
    // 关闭 SparkSession
    spark.stop()
  }

  test("integration test for Spark program") {
    // 调用 Spark 程序进行计算
    val output = MySparkProgram.run(input)

    // 验证结果是否正确
    assert(output.count() == 10) // 确认输出 DataFrame 行数为 10
    assert(output.columns.contains("column1")) // 确认输出 DataFrame 包含 "column1" 列
    assert(output.columns.contains("column2")) // 确认输出 DataFrame 包含 "column2" 列
  }

}