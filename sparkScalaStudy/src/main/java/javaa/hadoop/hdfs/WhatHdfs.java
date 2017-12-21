package javaa.hadoop.hdfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * NameNode
 * 		主节点，存储文件的元数据，如文件名，文件目录结构，文件属性（生成的时间，副本数，文件权限），以及每个文件的列表和块所在DataNode。
 * 		本地磁盘：fsimage镜像文件，edits 日志编辑文件
 * 格式换HDFS,目的是生成fsimage文件
 * 
 * DataNode
 * 		在本地文件系统存储文件块数据，以及块数据的校验和。
 * 
 * Secondary NameNode 
 * 		用来监控HDFS状态的辅助后台程序，每隔一段时间获取HDFS元数据的快照。
 * 
 * Created by root on 2017/12/20.
 */
public class WhatHdfs {
	public static FileSystem init(){
		//需要 core-site.xml,hdfs-site.xml
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.8.100:9000/");//加入文件之后这个可以省略
		
		FileSystem fileSys = null;
		try {
			fileSys = FileSystem.get(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileSys;
	}
	public static void main(String[] args) throws Exception {
		readFile();
	}
	
	public static void readFile() throws Exception{
		String hadoopFilePath = "/file/nlog.txt";
		FileSystem fileSys = init();
		Path path = new Path(hadoopFilePath);
		FSDataInputStream inPut = fileSys.open(path);
		long lenth = fileSys.getFileStatus(path).getLen();
		FileOutputStream outPut = new FileOutputStream(new File("e:\\hadoop.log")); 
		//lenth 是long型的是读取多少，是int型的是读取buffer
		IOUtils.copyBytes(inPut, outPut, lenth, false);
		
		IOUtils.closeStream(outPut);
		IOUtils.closeStream(inPut);
	}
}
