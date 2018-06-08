package cn.edu360.day04;

/**
 * 封装uid和uid对应的avg
 * @author root
 *
 */
public class UidAvgBean {
	private String uid;
	private float avg;
	
	
	
	public void set(String uid, float avg) {
		this.uid = uid;
		this.avg = avg;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	@Override
	public String toString() {
		return "UidAvgBean [uid=" + uid + ", avg=" + avg + "]";
	}
	
	
}
