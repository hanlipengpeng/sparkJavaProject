driver：driver进程是main方法就是在driver上面进行运行的，
master：master负责自愿的调度，集群的监控
worker：一：存储rdd，二：启动其他进程和线程来处理partition数据
executor：负责执行的，
task：执行的，

一个executor里面可以同时运行多个task，一般task的并行度是跟核心数一样的


transformation 转化操作，数据还在集群上
map filter flatmap groupByKey reduceBykey sourceBykey join cogroup

action 进行action操作，一般是对rdd的最后操作，例如遍历，reduce，保存文件，并可以返回结果给driver程序
reduce collect count take(n) saveAsTestFile countByKey foreach