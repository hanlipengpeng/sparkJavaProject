package cn.edu360.day09.model;

import java.io.Serializable;

/**
 * 返回结果的封装
 * @author root
 *
 */
public class Response implements Serializable{
	
	private String status;
	private UserResult ret;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserResult getRet() {
		return ret;
	}
	public void setRet(UserResult ret) {
		this.ret = ret;
	}
	@Override
	public String toString() {
		return "Response [status=" + status + ", ret=" + ret + "]";
	}
	
}
