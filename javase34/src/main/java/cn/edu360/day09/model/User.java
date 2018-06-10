package cn.edu360.day09.model;

import java.io.Serializable;

/**
drop table user;
-- id 用户名 密码 昵称 手机号 年龄 性别 住址。。。
create table user(
	id int(11) PRIMARY KEY auto_increment,
	userName VARCHAR(40) not NULL UNIQUE,
	passWord VARCHAR(40) NOT NULL,
	nikeName VARCHAR(40) ,
	pNum VARCHAR(13),
	age INT(4),
	genender VARCHAR(2),
	addr VARCHAR(100)
);
 */
public class User implements Serializable{
	private int id;
	private String userName;
	private String passWord;
	private String nikeName;
	private String pNum;
	private int age;
	private String genender;
	private String addr;
	
	//id 不需要传
	public void set (String userName, String passWord, String nikeName, String pNum,
			int age, String genender, String addr) {
		this.userName = userName;
		this.passWord = passWord;
		this.nikeName = nikeName;
		this.pNum = pNum;
		this.age = age;
		this.genender = genender;
		this.addr = addr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	public String getpNum() {
		return pNum;
	}
	public void setpNum(String pNum) {
		this.pNum = pNum;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGenender() {
		return genender;
	}
	public void setGenender(String genender) {
		this.genender = genender;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord="
				+ passWord + ", nikeName=" + nikeName + ", pNum=" + pNum
				+ ", age=" + age + ", genender=" + genender + ", addr=" + addr
				+ "]";
	}
}
