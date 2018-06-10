package cn.edu360.day09.utiles;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

/**
 * 测试数据工具类
 * @author root
 *
 */
public class TestDBUtile {
	public static void main(String[] args) throws Exception {
		QueryRunner queryRunner = DBUtile.getQueryRunner();
		String sql = "insert into user (userName,passWord) values (?,?)";
		queryRunner.update(sql, "鹏鹏","123");
	}

}
