package cn.edu360.day09.utiles;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtile {
	static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	/**
	 * 获取QueryRunner
	 * @return
	 */
	public static QueryRunner getQueryRunner(){
		QueryRunner runner = new QueryRunner(dataSource);
		return runner;
	}

}
