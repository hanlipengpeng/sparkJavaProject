package javaa.redis;
/**
 * redis
 * 
 * nosql存储形式  一般没有标准的查询语言，很多可以借助其他的数据进行中间转换
 * 键值数据库：redis，Voldemort，Oracle BDB  hash存储形式
 * 列存储数据库：Hbase，Rlak 这部分数据通常是用来应对分布式存储的海量数据，键仍然存在，但是他们的特点是指向了多个列。
 * 文档型存储：MongoDB，CouchDB  文档数据库可以看做键值数据库的升级版，允许之间嵌套键值。而且文档数据比键值数据库的查询效率高
 * 图形数据库：Neo4J,InfoGrid,Infinite Graph   他使用灵活的图形模型。
 * 
 * 特点：
 * 模型比较简单
 * 对数据库性能要求比较高
 * 不需要高度的数据库一致性
 * 
 * 优点：
 * 对数据高并发的读写
 * 对海量数据的高效率的存数和访问
 * 对数据的可扩展性和高可用性
 * 
 * 缺点：
 * redis（ACID处理非常简单）
 * 无法做到太复杂的关系数据库模型
 * 
 * 数据结构：
 * 键可以包含String hash list set zset（有序集合）    支持 push/pop  add/remove等丰富的操作
 * 
 * 可靠性：
 * RDB(周期同步数据)
 * 周期吧数据同步到硬盘上
 * AOF(日志形式)
 * 
 * redis 3种模式
 * 1：主从
 * 2：哨兵
 * 3：集群
 * 
 * redis和memcached性能比较：一般memcached单节点使用，redis强调集群性能。
 * 
 * redis+ssdb（redis是高性能的读，ssdb是高性能的写，可以进行技术融合）
 * 
 * 数据库和redis进行强一致性，可以使用try，catch来进行回滚
 * 
 *
 */
public class WhatRedis {

}
