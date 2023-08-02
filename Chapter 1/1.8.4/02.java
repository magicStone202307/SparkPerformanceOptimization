import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

// 选择需要的列
val executorLogs = eventLogs
  .select("Event", "ExecutorID", "TaskEndReason", "TaskInfo")
  .where("Event = 'SparkListenerTaskEnd' and TaskEndReason = 'Success'")

// 定义一个UDF来解析任务持续时间
val parseTaskDuration = udf { taskInfo: String =>
  val duration = taskInfo.split("::")(1).toLong - taskInfo.split("::")(0).toLong
  duration
}

// 根据执行器分组，计算任务平均执行时间和标准差
val executorDurations = executorLogs
  .withColumn("Duration", parseTaskDuration(col("TaskInfo")))
  .groupBy("ExecutorID")
  .agg(avg("Duration").as("AvgDuration"), stddev("Duration").as("StdDevDuration"))

// 显示结果
executorDurations.show()