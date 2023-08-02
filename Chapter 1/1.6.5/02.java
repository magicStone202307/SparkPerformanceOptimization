import org.apache.spark.scheduler._
import org.apache.spark.scheduler.TaskLocality._
import org.apache.spark.sql.execution._
import org.apache.spark.sql.execution.ui._
import org.apache.spark.sql.streaming.ui._

// 获取事件日志目录并获取事件日志文件路径
val eventLogDir = sparkConf.get("spark.eventLog.dir")
val eventLogPath = EventLoggingListener.getLogPath(eventLogDir, None)

// 创建事件日志读取器并打开事件日志
val eventLogReader = new EventLogReader()
val eventLog = eventLogReader.openEventLog(eventLogPath)

// 反序列化事件并将其存储在 events 变量中
val events = eventLog.map(EventLogHelpers.deserializeEvent)