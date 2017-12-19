package javaa.spark.runPrinciple;
/**
 * blockManager原理剖析与源码分析
 * driver上运行机制    driver --> DAGScheduler --> BlockManagerMaster --> BlockManagerInfo --> BlockStatus
 * 
 * BlockManagerMaster, 他的功能就是负责BlockManager内部元素的数据的元数据进行维护，比如block的增删改等操作，多会在这里维护元数据的改变
 * 
 * BlockManager 有 DiskStore MemoryStore BlockManagerWorker ConnectionManager
 * 1：DiskStore，负责对磁盘上的数据进行读写
 * 2：MemoryStore，负责对内存中的数据进行读写
 * 3：BlockManagerWorker，负责对远程其他节点的BlockManager数据的读写
 * 4：ConnectionManager，负责建立BlockManager到远程的其他节点的BlockManager的网络连接
 */
public class BlocakManagerRun {

}
