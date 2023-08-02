import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.mllib.random.RandomRDDs

// 定义User类
case class User(userId: Int, age: Int, gender: String)

object TestDataSet {

  def generateUsers(spark: SparkSession, numUsers: Long): Seq[User] = {
    // 生成用户ID
    val userIds = RandomRDDs.randomLongRDD(spark.sparkContext, numUsers).map(_.toInt)
    // 生成年龄
    val ages = RandomRDDs.normalRDD(spark.sparkContext, numUsers).map(_.toInt.abs % 120)
    // 生成性别
    val genders = RandomRDDs.randomRDD(spark.sparkContext, UniformGenerator(0.0, 1.0))
                      .map(x => if (x < 0.5) "M" else "F")
    // 将三个RDD合并为一个RDD，并转换为Seq[User]类型
    userIds.zip(ages).zip(genders).map{ case ((id, age), gender) => User(id, age, gender)}
                                  .collect().toSeq
  }

  def main(args: Array[String]): Unit = {
    // 创建SparkSession
    val spark = SparkSession.builder()
                            .appName("TestDataSet")
                            .master("local[*]")
                            .getOrCreate()

    // 生成用户数据，并转换为DataFrame输出
    val users = generateUsers(spark, 100)
    val usersDF = spark.createDataFrame(spark.sparkContext.parallelize(users))
    usersDF.show()
    
    
    spark.stop()
  }
}
