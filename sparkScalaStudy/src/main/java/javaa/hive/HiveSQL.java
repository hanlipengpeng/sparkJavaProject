package javaa.hive;
/**
 * HiveSQL
 * @author hanlipeng
 *
 */
public class HiveSQL {

/**
 * hive [-hiveconf x=y]* [<-i filename>]* [<-f filename>|<-e query-string>] [-S]
 * 说明：
 * 1、-i 从文件初始化HQL。
 * 2、-e从命令行执行指定的HQL
 * 3、-f 执行HQL脚本 
 * 4、-v 输出执行的HQL语句到控制台 
 * 5、-p <port> connect to Hive Server on port number
 * 6、-hiveconf x=y Use this to set hive/hadoop configuration variables.
 * 
 * 
 * 显示当前会话有多少函数可用 
 * SHOW FUNCTIONS;
 * 
 * 显示函数的描述信息 
 * DESC FUNCTION concat;
 * 
 */
	
/**
 * DDL
 * 
 * 1:创建表
 * CREATE [EXTERNAL] TABLE [IF NOT EXISTS] table_name 
 *    [(col_name data_type [COMMENT col_comment], ...)] 
 *    [COMMENT table_comment] 
 *    [PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)] 
 *    [CLUSTERED BY (col_name, col_name, ...) 
 *    [SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS] 
 *    [ROW FORMAT row_format] 
 *    [STORED AS file_format] 
 *    [LOCATION hdfs_path]
 * 创建带分区的表
 * create table student_p(Sno int,Sname string,Sex string,Sage int,Sdept string) 
 * partitioned by(part string) row format delimited fields terminated by ','stored as textfile;
 * 
 * 创建带桶的表
 * create table student(id int,age int,name string) partitioned by(stat_data string)
 * clustered by(id) sorted by(age) into 2 buckets row format delimited fields terminates by '\t';
 * 
 * 2:修改表
 * 增加/删除分区
 * ALTER TABLE table_name ADD [IF NOT EXISTS] partition_spec [ LOCATION 'location1' ] partition_spec [ LOCATION 'location2' ] ...
 * partition_spec:
 * : PARTITION (partition_col = partition_col_value, partition_col = partiton_col_value, ...)
 * 
 * ALTER TABLE table_name DROP partition_spec, partition_spec,...
 * 
 * 实例：alter table student_p add partition(part='a') partition(part='b');
 * 
 * 3：重命名表
 * ALTER TABLE table_name RENAME TO new_table_name
 * 
 * 4：增加/更新列
 * ALTER TABLE table_name ADD|REPLACE COLUMNS (col_name data_type [COMMENT col_comment], ...) 
 * 注：ADD是代表新增一字段，字段位置在所有列后面(partition列前)，REPLACE则是表示替换表中所有字段。
 * 
 * ALTER TABLE table_name CHANGE [COLUMN] col_old_name col_new_name column_type [COMMENT col_comment] [FIRST|AFTER column_name]
 * 
 */
	
	
/**
 * DML
 * 
 * 1:Load
 * LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO 
 * TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)]
 * 
 * 2:Insert
 * INSERT OVERWRITE TABLE tablename1 [PARTITION (partcol1=val1, partcol2=val2 ...)] select statement1 FROM from_statement
 * 
 * 多插入模式
 * FROM from_statement 
 * INSERT OVERWRITE TABLE tablename1 [PARTITION (partcol1=val1, partcol2=val2 ...)] select_statement1
 * [INSERT OVERWRITE TABLE tablename2 [PARTITION ...] select_statement2] ...
 * 
 * 自动分区模式
 * INSERT OVERWRITE TABLE tablename PARTITION (partcol1[=val1], partcol2[=val2] ...) select_statement FROM from_statement
 * 
 * 导出数据
 * INSERT OVERWRITE [LOCAL] DIRECTORY directory1 SELECT ... FROM ...
 * 
 * 3：SELECT
 * SELECT [ALL | DISTINCT] select_expr, select_expr, ... 
 * FROM table_reference
 * [WHERE where_condition] 
 * [GROUP BY col_list [HAVING condition]] 
 * [CLUSTER BY col_list 
 *   | [DISTRIBUTE BY col_list] [SORT BY| ORDER BY col_list] 
 * ] 
 * [LIMIT number]
 * 
 * 1、order by 会对输入做全局排序，因此只有一个reducer，会导致当输入规模较大时，需要较长的计算时间。
 * 2、sort by不是全局排序，其在数据进入reducer前完成排序。因此，如果用sort by进行排序，并且设置mapred.reduce.tasks>1，则sort by只保证每个reducer的输出有序，不保证全局有序。
 * 3、distribute by根据distribute by指定的内容将数据分到同一个reducer。
 * 4、Cluster by 除了具有Distribute by的功能外，还会对该字段进行排序。因此，常常认为cluster by = distribute by + sort by
 * 
 */

	
/**
 * Hive join
 * table_reference JOIN table_factor [join_condition]
 *   | table_reference {LEFT|RIGHT|FULL} [OUTER] JOIN table_reference join_condition
 *   | table_reference LEFT SEMI JOIN table_reference join_condition
 * 
 * Hive 支持等值连接（equality joins）、外连接（outer joins）和（left/right joins）。Hive 不支持非等值的连接，因为非等值连接非常难转化到 map/reduce 任务。
 * 另外，Hive 支持多于 2 个表的连接。
 * 
 * 
 */
	
/**
 * 显示命令
 * show tables
 * show databases
 * show partitions
 * show functions
 * desc extended t_name;
 * desc formatted table_name;
 * 
 */
/**
 * 创建表说明
 * 
 * 
 * 1、CREATE TABLE 创建一个指定名字的表。如果相同名字的表已经存在，则抛出异常；用户可以用 IF NOT EXISTS 选项来忽略这个异常。
 * 2、EXTERNAL关键字可以让用户创建一个外部表，在建表的同时指定一个指向实际数据的路径（LOCATION），Hive 创建内部表时，会将数据移动到数据仓库指向的路径；若创建外部表，仅记录数据所在的路径，不对数据的位置做任何改变。在删除表的时候，内部表的元数据和数据会被一起删除，而外部表只删除元数据，不删除数据。
 * 3、LIKE 允许用户复制现有的表结构，但是不复制数据。
 * 4、ROW FORMAT 
 * DELIMITED [FIELDS TERMINATED BY char] [COLLECTION ITEMS TERMINATED BY char] 
 *         [MAP KEYS TERMINATED BY char] [LINES TERMINATED BY char] 
 *    | SERDE serde_name [WITH SERDEPROPERTIES (property_name=property_value, property_name=property_value, ...)]
 * 用户在建表的时候可以自定义 SerDe 或者使用自带的 SerDe。如果没有指定 ROW FORMAT 或者 ROW FORMAT DELIMITED，将会使用自带的 SerDe。在建表的时候，用户还需要为表指定列，用户在指定表的列的同时也会指定自定义的 SerDe，Hive通过 SerDe 确定表的具体的列的数据。
 * 5、STORED AS 
 * SEQUENCEFILE|TEXTFILE|RCFILE
 * 如果文件数据是纯文本，可以使用 STORED AS TEXTFILE。如果数据需要压缩，使用 STORED AS SEQUENCEFILE。
 * 
 * 6、CLUSTERED BY
 * 对于每一个表（table）或者分区， Hive可以进一步组织成桶，也就是说桶是更为细粒度的数据范围划分。Hive也是 针对某一列进行桶的组织。Hive采用对列值哈希，然后除以桶的个数求余的方式决定该条记录存放在哪个桶当中。 
 * 把表（或者分区）组织成桶（Bucket）有两个理由：
 * （1）获得更高的查询处理效率。桶为表加上了额外的结构，Hive 在处理有些查询时能利用这个结构。具体而言，连接两个在（包含连接列的）相同列上划分了桶的表，可以使用 Map 端连接 （Map-side join）高效的实现。比如JOIN操作。对于JOIN操作两个表有一个相同的列，如果对这两个表都进行了桶操作。那么将保存相同列值的桶进行JOIN操作就可以，可以大大较少JOIN的数据量。
 * （2）使取样（sampling）更高效。在处理大规模数据集时，在开发和修改查询的阶段，如果能在数据集的一小部分数据上试运行查询，会带来很多方便。	
 * 
 */
<<<<<<< Updated upstream
	
	
	
	
	
	
	
	

	
}
=======

}
>>>>>>> Stashed changes
