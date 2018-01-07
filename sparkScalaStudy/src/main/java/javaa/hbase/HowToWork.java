package javaa.hbase;

/**
 * hbase的原理
 */
public class HowToWork {
    /**
     * 写数据
     * client --> zookeeper -->hregionserver  -->hlog(为了数据持久化和恢复)
     * -->memostore(写到内存中) --> 反馈给client
     *
     * 数据flush 过程
     * 1：memostore数据达到阈值(默认64M)，将数据写到硬盘上，将内存中的数据删除。同时删除hlog中的历史数据
     * 2：并将数据保存到hdfs中
     * 3：在hlog中做标记点
     *
     * hbase的读流程
     * 1：通过zookeeper和 -ROOT- .META.  表定位和regionserver
     * 2：如果数据在内存中，直接将数据读出来，返回
     * 3：如果数据在hdfs上，则从hdfs上读取出来，返回
     *
     * 数据合并过程
     * 1：当数据块达到4块，hbase将数据块加载到本地，进行合并。
     * 2：当合并的数据块超过256M，进行拆分，将拆分的resion分配给不同的hresionserver管理
     * 3：当hregionserver宕机时候，将hregionserver上的hlog拆分，然后分配给不同的hregionserver加载，修改 .META.
     * 4：hlog会同步到hdfs上
     *
     *
     *
     * hmaster的职责
     * 1：管理用户对Table的增删査操作
     * 2：记录region在哪台hregionserver上
     * 3：hrigion split之后，负责新region的分配
     * 4：新机加入时，管理Hregion server的负载均衡，调整region的分布
     * 5：hregion server停机后，负责hregion server的迁移
     *
     */
}
