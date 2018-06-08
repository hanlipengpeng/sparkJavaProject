package cn.edu360.day01;

/**
 * 封装key-value的
 * @author root
 *
 */
public class UidCountBean {
	private String uid;
	private Integer count;
	
	
	
	public UidCountBean() {
		super();
	}
	public UidCountBean(String uid, Integer count) {
		super();
		this.uid = uid;
		this.count = count;
	}
	@Override
	public String toString() {
		return "uid=" + uid + ", count=" + count;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	

}
