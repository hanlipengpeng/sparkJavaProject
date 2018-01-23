package javaa.hive;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;

public class HiveJDBCThread extends  Thread  {  
   
    @Override  
    public void run() {  
        excSparkSql();  
    }  
       
    public  void  excSparkSql() {  
        String result = "";  
        Connection conn = null;  
        Statement stmt = null;  
        try {  
        	//使用的是hive1.2.1的     之前的能是org.apache.hadoop.hive.jdbc.HiveDriver，是使用的hiveserver
        	//使用hiveserver2的需要使用下面的类，不然会报class not found Exception
            Class.forName("org.apache.hive.jdbc.HiveDriver");  
            conn = DriverManager.getConnection(  
                    "jdbc:hive2://aliyun:10000", "", "");  
            stmt = conn.createStatement();  
            String sql = "show tables";     
            long start = System.currentTimeMillis();  
               
            ResultSet res = stmt.executeQuery(sql);  
            while(res.next()){
            	System.out.println(res.getString(1));
            }

            long cost = System.currentTimeMillis() - start;  
               
            result = Thread.currentThread().getName() + ": " + cost/1000.0f + "s";  
            System.out.println(result);  
            stmt.close();  
            conn.close();  
        } catch (Exception e) {  
               e.printStackTrace();
            try {  
                stmt.close();  
                conn.close();  
            } catch (SQLException e1) {  
                // TODO Auto-generated catch block  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
               
        }  
           
    }
    public static void main(String[] args) {
		new HiveJDBCThread().run();//测试的，没有使用线程
		//数据 高效 海量 计算
	}
       
   
}  