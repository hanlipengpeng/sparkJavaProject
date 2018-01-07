package javaa.hbase;
/**
 * Hbase介绍
 * @author rf
 *
 */
public class WhatHbase {
    /**
     * 列式数据库和行式数据库的区别
     * 关系型数据库是行存储，hbase是列式存储
     *
     * HMaster   HReginserver
     * 将数据缓存到Hregiserver里面，达到128M再写到文件中
     *
     * 列式的没多表关联
     * rowkey保存为字节数组（二进制存储），最大保存64K，实际上为10-100bytes 存储时候按照rowkey的字典顺序排序存储。设计rowkey的时候要充分的利用这个特性。将经常一起读的行存储到一起（位置相关性 ）
     * columns Family   cell  timeStamp
     * rowkey的检索方式
     * 单个rowkey访问
     * 通过row key 的range（正则匹配）
     * 全表扫描
     *
     *
     *
     */

}
