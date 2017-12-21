package javaa.hadoop.mapreduce;

/**
 * MR
 * 将计算氛围两个阶段
 * 		map阶段并行处理输入数据
 * 		reduce阶段对map结果进行汇总
 * shuffle链接map和reduce两个阶段
 * 		map task将数据写到本地磁盘
 * 		reduce task 从每个map task上读取一份数据
 * 仅适合离线批处理
 * 		具有很好的容错性和扩展性
 * 		适合简单的批处理
 * 缺点明显
 * 		启动开销大，过多食用磁盘导致效率低下
 * 
 * 
 * Created by root on 2017/12/20.
 */
public class WhatMapReduce {
}
