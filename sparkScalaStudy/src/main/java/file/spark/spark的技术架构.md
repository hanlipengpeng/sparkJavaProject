driver：driver进程是main方法就是在driver上面进行运行的，
master：master负责自愿的调度，集群的监控
worker：一：存储rdd，二：启动其他进程和线程来处理partition数据
executor：负责执行的，
task：执行的，

一个executor里面可以同时运行多个task，一般task的并行度是跟核心数一样的