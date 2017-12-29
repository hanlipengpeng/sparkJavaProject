package javaa.redis;
/**
 * redis的shell操作
 * redis的五种类型：String hash set list zset
 * @author rf
 *
 */
public class RedisShell {
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

}
