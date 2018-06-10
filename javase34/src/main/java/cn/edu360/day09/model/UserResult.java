package cn.edu360.day09.model;

import java.io.Serializable;

/**
 * 返回结果的封装
 * @author root
 *
 */
public class UserResult implements Serializable{
	//是否成功
	private boolean flag;
	//具体说明
	private String des;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@Override
	public String toString() {
		return "UserResult [flag=" + flag + ", des=" + des + "]";
	}

	
}
