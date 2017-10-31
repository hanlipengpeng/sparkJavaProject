package com.xiaopengpeng.sparkproject.dao.factory;

import com.xiaopengpeng.sparkproject.dao.ITaskDAO;
import com.xiaopengpeng.sparkproject.impl.TaskDAOImpl;



/**
 * DAO工厂类
 * @author Administrator
 *
 */
public class DAOFactory {


	public static ITaskDAO getTaskDAO() {
		return new TaskDAOImpl();
	}
	
}
