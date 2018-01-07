package javaa.hbase;

/**
 * Hbase Shell
 */
public class HbaseShell {
    /**
     * hbase shell 进入shell窗口
     * 一般操作：
     * 1：查询服务器状态  status
     * 2：查询hive版本  version
     *
     *
     *
     */

    /**
     * hbase为什么依赖zookeeper
     * 1：保存hmaster 地址和 backup-master地址
     * hmaster“
     *      管理hregionserver
     *      做增删改查表的节点
     *      管理hregionServer中的表分配
     * 2：保存-ROOT-的地址
     * hbase的默认根表，检索表
     *
     * 3：HRegionServer列表
     * 表的增删改查数据
     * 和hdfs交互，存取数据
     *
     */

}
