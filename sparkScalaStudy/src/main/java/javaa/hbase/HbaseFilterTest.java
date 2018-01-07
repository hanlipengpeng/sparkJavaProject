package javaa.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 1：hbase的增删改查
 * 2：hbase的过滤器，单个过滤器使用和多个过滤器混合使用
 */
public class HbaseFilterTest {
    /**
     * 配置ss
     */
    static Configuration config = null;
    private Connection connection = null;
    private Table table = null;

    @Before
    public void init() throws Exception{
        config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum","master1,master2,master3");//zookeeper地址
        config.set("hbase.zookeeper.property.clientPort","2181");//zookeeper connection  port
        connection = ConnectionFactory.createConnection(config);
        table = connection.getTable(TableName.valueOf("user"));
    }

    /**
     * 创建一个表
     */
    @Test
    public void createTable() throws Exception{
        HBaseAdmin admin = new HBaseAdmin(config);

        TableName tableName = TableName.valueOf("test1");

        HTableDescriptor desc = new HTableDescriptor(tableName);

        HColumnDescriptor family = new HColumnDescriptor("info");
        HColumnDescriptor family2 = new HColumnDescriptor("info2");
        desc.addFamily(family);
        desc.addFamily(family2);
        admin.createTable(desc);
        admin.close();
    }

    /**
     * 删除表
     */
    @Test
    public void deleteTable() throws Exception{
        HBaseAdmin admin = new HBaseAdmin(config);
        admin.disableTable("test1");
        admin.deleteTable("text1");
        admin.close();
    }

    /**
     * 添加数据
     */
    public void insertData() throws Exception{
        Put put = new Put(Bytes.toBytes("rowkeyma_1234"));
        put.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("name"),Bytes.toBytes("hanlipeng"));
        put.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("age"),Bytes.toBytes("18"));
        put.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("sex"),Bytes.toBytes("man"));

        table.put(put);
        List<Put> list = new ArrayList<>();
        table.put(list);

    }

    /**
     * 修改
     */
    public void pudateData(){
        //更新数据同添加数据
    }

    /**
     * 删除数据
     */
    public void deleteData() throws Exception{

        Delete delete = new Delete(Bytes.toBytes("rowkey001"));
        //delete.addColumn()
        table.delete(delete);

    }

    /**
     * 单条查询
     */
    public void scanDataByFilter1() throws Exception{
        Get get = new Get(Bytes.toBytes("rowkey001"));
        //get.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("name"));
        Result result = table.get(get);
        //得到rowkey
        System.out.println(Bytes.toString(result.getRow()));

        byte[] name = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("name"));
        byte[] age = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("age"));
        System.out.println(Bytes.toString(name));
    }

    /**
     * 全表扫描
     */
    public void scanData() throws Exception{
        //设置全表扫描
        Scan scan = new Scan();
        //使用的是字典序，不需要固定的值
        scan.setStartRow(Bytes.toBytes("rowkey_"));
        scan.setStopRow(Bytes.toBytes("rowk"));
        //指定返回哪些数据
        //scan.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("name"));

        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result: resultScanner) {
            byte[] name = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("name"));
            byte[] age = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("age"));
            System.out.println(Bytes.toString(name));
            System.out.println(Bytes.toString(age));
        }
    }


    /**
     * 过滤器,使用的是全表扫描（scan设置扫描器）
     * 过滤器的种类
     * 1：列值过滤器--SingleColumnValueFilter      过滤列值相等，不等，范围等
     * 2：列名前缀过滤器--ColumnPrefixFilter    过滤指定前缀的列名
     * 3：多个列名前缀过滤器--MultipleColumnPrefixFilter    过滤多个指定前缀的列名
     * 4：rowkey过滤器--RowFilter    通过正则，过滤rowKey值
     */
    public void scanDataByFilter2() throws Exception{
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes("info1"),Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes("hanlipeng"));
        Scan scan = new Scan();
        scan.setFilter(singleColumnValueFilter);
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result: resultScanner) {
            byte[] name = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("name"));
            byte[] age = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("age"));
            System.out.println(Bytes.toString(name));
            System.out.println(Bytes.toString(age));
        }
        resultScanner.close();
    }

    /**
     * rowkey过滤器，优先考虑使用scan.setStartRow的这种形式的过滤器
     * @throws Exception
     */
    public void scanDataByFilter3() throws Exception{
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("^rowkey_0000"));//匹配前缀
        //SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes("info1"),Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes("hanlipeng"));
        Scan scan = new Scan();
        scan.setFilter(rowFilter);
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result: resultScanner) {
            byte[] name = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("name"));
            byte[] age = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("age"));
            System.out.println(Bytes.toString(name));
            System.out.println(Bytes.toString(age));
        }
        resultScanner.close();
    }


    /**
     * 过个过滤器的使用，使用的是Filterlist
     * @throws Exception
     */
    public void scanDataByFilter4() throws Exception{
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);//设置and 或者是 or
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("^rowkey_0000"));//匹配前缀
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes("info1"),Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes("hanlipeng"));
        Scan scan = new Scan();

        list.addFilter(rowFilter);
        list.addFilter(singleColumnValueFilter);
        scan.setFilter(list);
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result: resultScanner) {
            byte[] name = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("name"));
            byte[] age = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("age"));
            System.out.println(Bytes.toString(name));
            System.out.println(Bytes.toString(age));
        }
        resultScanner.close();
    }






}
