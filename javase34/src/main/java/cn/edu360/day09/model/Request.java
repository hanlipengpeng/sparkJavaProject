package cn.edu360.day09.model;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable{
	//类名
	private String className;
	//方法名
	private String mothedName;
	//参数类型
	private Class[] classType;
	//参数类型对应的参数值
	private Object[] value;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMothedName() {
		return mothedName;
	}
	public void setMothedName(String mothedName) {
		this.mothedName = mothedName;
	}
	public Class[] getClassType() {
		return classType;
	}
	public void setClassType(Class[] classType) {
		this.classType = classType;
	}
	public Object[] getValue() {
		return value;
	}
	public void setValue(Object[] value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Request [className=" + className + ", mothedName=" + mothedName
				+ ", classType=" + Arrays.toString(classType) + ", value="
				+ Arrays.toString(value) + "]";
	}
	public void set(String className, String mothedName, Class[] classType,
			Object[] value) {
		this.className = className;
		this.mothedName = mothedName;
		this.classType = classType;
		this.value = value;
	}

}
