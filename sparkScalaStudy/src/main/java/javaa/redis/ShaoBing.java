package javaa.redis;

/**
 * 哨兵
 */
public class ShaoBing {
    /**
     * 从redis的2.6
     *
     * 需要启动一个或多个哨兵服务
     *
     * 哨兵服务首先需要copy文件sentinel.conf 到 redis/etc目录下，
     * 修改sentinel.conf 文件 sentinel monitor mymaster 192.168.10.100 6397 1 #名称，ip，端口，投票选举次数
     * sentinel down-after-milliseconds mymaster 5000 #默认是1秒  ，这里配置超时5000毫秒为宕机
     *
     * 启动哨兵服务 redis-server sentinel.conf --sentinel &
     * 查看哨兵的相关信息命令 redis-cli -h 192.168.10.100 -p 26397 info Sentinel
     * 关闭主服务查看集群信息： redis-cli -h 192.168.10.100 -p 6397 shutdown
     *
     */

}
