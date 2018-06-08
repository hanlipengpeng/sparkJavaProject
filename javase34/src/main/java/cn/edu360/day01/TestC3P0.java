package cn.edu360.day01;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestC3P0 {
	public static void main(String[] args) throws Exception {
		ComboPooledDataSource source = new ComboPooledDataSource();
		QueryRunner run = new QueryRunner(source);
		String sql = "insert into test values (?)";
		run.update(sql,"lalala");
		
	}

}
