package javaa.redis;
/**
 * redis的shell操作
 * redis的五种类型：String hash set list zset
 * @author rf
 *
 */
public class RedisShell {

	//flushDB清空数据

	/**
	 * String操作：
	 * set name han  增加，修改
	 * get name  查询
	 * del name  删除
	 * setnx(set not exit)
	 * setex（set expired） name 10 han  （设置过期时间）
	 * setrange email 10 @163 （从哪个位置进行替换）
	 * 
	 * 
	 * mset k1 11 k2 22 k3 33    同时设置多个
	 * mget k1 k2 k3   同时获取很多值
	 * incr age  递增
	 * decr age  递减
	 * incrby age 3          按照字长递增
	 * decyby age 4          按照定义自减
	 * append name lipeng   字符串加方法
	 * strlen name    获取字符串的长度
	 */
	
	/**
	 * hash操作，hash相当于map 比较重要，常用
	 * hset myhash field1 hello  （hash的设置，hast是hash集合，myhash是集合的名字 filed1是字段名 hello是值）
	 * hget myhash filed1
	 * hmset myhash name han sex men age 18
	 * hmget myhash name sex
	 * hsetnx myhash name han
	 * 
	 * hincrby 和 hdecrby 集合递增和递减
	 * hexists 是否存在key，如果存在则返回，不存在则返回0
	 * hlen 返回hash集合里的所有的键数值
	 * hdel 删除指定的field
	 * hkays 返回hash里所有value
	 * hgetall 返回hash里卖弄多有的key和value
	 * 
	 */


	/**
	 * list操作，
	 * list是链表结构的集合，其主要功能有push，pop获取元素等
	 * 更详细的说，list类型是一个双向链表的结构，我们可以通过相关操作进行集合的头部或者尾部添加删除元素
	 * list的设计非常轻巧，既可以作为栈，又可以作为队列。
	 * ipush方法：从头部加入元素（栈） 先进后出
	 * rpush方法：从尾部加入元素（队列） 先进后出
	 * linsert方法：插入元素
	 * lset方法：将制定下标的元素替换
	 * lrem方法：删除元素 返回删除的个数
	 * ltrim方法：保留制定key的值范围内的数据
	 * lpop方法：从list的头部删除元素，并返回删除元素
	 * rpop方法：从list的尾部删除元素，并返回删除元素
	 * rpoplpush方法：第一个从尾部删除元素，然后第二步从他头部加入元素
	 * lindex方法：返回名称为key的list的index位置的元素
	 * llen方法：返回元素的个数
	 *
	 *
	 * lrange 取值
	 * lrange list1 0 -1
	 */

	/**
	 * set集合是string类型的无需集合，set是通过hashtable实现的，对集合我们可以取交际，并集，差集
	 * sadd方法：项名称为key的set中添加元素
	 * srem：删除set集合元素
	 * scard：查看元素
	 * spop方法：随机返回删除的key
	 * adiff：返回两个集合的不同元素
	 * sdiffstore方法：将返回的不同元素存储到另一个集合里
	 *
	 * sinter：返回集合的交集
	 * sinterstore：返回交集结果，存入set3中
	 * sunlon：取并集
	 * sunionstore方法：取的并集，存入set3中
	 */

}
