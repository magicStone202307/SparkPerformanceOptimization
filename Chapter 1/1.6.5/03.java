//使用filter函数和isInstanceOf方法筛选出所有的SparkListenerTaskEnd类型的事件，并将其转换成SparkListenerTaskEnd类型
val taskEvents = events.filter(_.isInstanceOf[SparkListenerTaskEnd]).map(_.asInstanceOf[SparkListenerTaskEnd])

//从每个任务的度量信息中提取出任务度量指标，并返回一个由度量信息构成的列表taskMetrics
val taskMetrics = taskEvents.map(task => task.taskMetrics)	

//将所有任务的executorCpuTime字段相加，得到总CPU时间
val executorCpuTime = taskMetrics.map(_.executorCpuTime).sum

//将所有任务的executorRunTime字段相加，得到总运行时间
val executorRunTime = taskMetrics.map(_.executorRunTime).sum

//计算出CPU使用率，即总CPU时间占总运行时间的比例
val cpuRatio = executorCpuTime.toDouble / executorRunTime.toDouble

//计算出内存使用率，即1减去CPU使用率的值
val memRatio = 1 - cpuRatio

//将CPU和内存的使用率打印出来
println(s"CPU Ratio: $cpuRatio, Memory Ratio: $memRatio")