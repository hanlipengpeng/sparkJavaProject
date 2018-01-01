package javaa.redis;

/**
 * 高级的shell命令使用
 */
public class RedisShellHight {
    /**
     * keys * 返回满足的所有键（可以模糊匹配）
     * exists 是否存在指定的key
     * expire 设置某个指定key的过期时间，使用ttl查看剩余时间
     * persist取消过期时间
     * select 原则数据库，数据库为0-15，默认进入的是0数据库
     * move key 【数据库下标】 当前数据中的key转移到其他数据库中
     * readomkey 随机的返回数据库里面的一个key
     * rename 重命名key
     *
     */

    /**
     * redis 事务支持
     * 事务比较简单：事务不能很好的回滚、
     * 使用multi方法打开事务，然后就你行设置，这时设置的数据会放到队列里进行保存，最后使用exec执行，
     * 把数据依次存放到redis中，使用discard方法取消事务。
     *
     */


    /**
     * 数据持久化，持久化有两种方式。
     * 1：定时的保存，默认，900秒内有一条数据发生写操作，60秒内发生1000次写操作都会进行保存。（rdb）
     * 2：写入日志文件中，写的策略也有好几种，中有每次写记录都记录日志的方式才能保证数据的不丢失。（aof）
     */


    /**
     * redis 有发布订阅的功能。
     * subscribe 【频道】 进行订阅一个频道
     * publish 【频道】 【message】 发布消息
     *
     */

    /**
     * redis分片
     */

}
