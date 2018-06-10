package cn.edu360.day09.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.edu360.day09.dao.UserDao;
import cn.edu360.day09.model.User;
import cn.edu360.day09.utiles.DBUtile;

public class UserDaoImpl implements UserDao{
	/**
	 * 通过用户名查找用户
	 */
	@Override
	public User getUserByName(String userName) {
		QueryRunner runner = DBUtile.getQueryRunner();
		String sql = "select * from user where userName = ?";
		try {
			//runner.query(sql, userName, rsh);
			User query = runner.query(sql,new BeanHandler<User>(User.class), userName);
			return query;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存数据
	 */
	@Override
	public boolean save(User user) {
		QueryRunner runner = DBUtile.getQueryRunner();
		String sql ="insert into user (userName,passWord,nikeName,pNum,age,genender,addr) values (?,?,?,?,?,?,?)";
		try {
			runner.update(sql, user.getUserName(),user.getPassWord(),user.getNikeName(),user.getpNum(),user.getAge(),user.getGenender(),user.getAddr());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
