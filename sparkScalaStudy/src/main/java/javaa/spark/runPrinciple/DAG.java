package javaa.spark.runPrinciple;
/**
 * DAG 宽依赖和窄依赖
 * 对于内核剖析主要是对一下的内核架构做了解
 * Application
 * spark-submit
 * Driver
 * SparkContext
 * Master
 * Worker
 * Executor
 * Job
 * DAGSheduler
 * TaskScheduler
 * ShuffleMapTask and ResultTsk
 */
public class DAG {
	

}
/*
spark整体架构流程
1：通过spark-submit(shell)方式提交任务
2：执行Driver进程
3：执行我们的Application，也就是我们自己的应用程序代码
4：会初始化SparkContext，SparkContext会创建DAGScheduler和TaskScheduler进程
5：taskScheduler会向Master进行Application注册
6：master收到注册之后，使用自己的资源调度算法，在spark集群的worker上，为这个Application启动多个Executer
7：启动的Executor会反向注册到TaskScheulder上
8：执行程序，每执行到一个action的时候就会使用DAGScheduler进行job划分为多个stage，每一个stage会创建一个TaskSet
9：把taskSet交给TaskScheduler管理，TaskScheduler会把每一个task都提交到executor上执行（task分配算法）；task有两种，ShuffleMapTask和ResultTask，只有追后一个stage是ResultTask，之前的都是ShuffleMapTask。
10：Executor进程里面有一个线程池，Executor每接受一个task，都会用TaskRunner来封装task，然后从线程池里面取出来一个线程，执行这个task；TaskRunner：将我们编写的代码，也就是要执行的段子以及函数，拷贝，反序列化，然后执行Task。

整个spark应用程序的执行，就是stage分批次为taskset提交到executor执行的，每一个task支队RDD的一个partition，执行我们定义的算子函数，以此类推，知道所有操作执行完为止。


 */
