package cn.edu360.day03;

/**
 * 封装ip数据，这个可以根据需求自己封装
 * @author root
 *
 */
public class IpBean {
	private String startIp;
	private String endIp;
	private Long startDecIp;
	private Long endDecIp;
	private String province;
	private String city;
	private String optioner;
	
	

	public void set(String startIp, String endIp, Long startDecIp, Long endDecIp,
			String province, String city, String optioner) {
		this.startIp = startIp;
		this.endIp = endIp;
		this.startDecIp = startDecIp;
		this.endDecIp = endDecIp;
		this.province = province;
		this.city = city;
		this.optioner = optioner;
	}
	@Override
	public String toString() {
		return "IpBean [startIp=" + startIp + ", endIp=" + endIp
				+ ", startDecIp=" + startDecIp + ", endDecIp=" + endDecIp
				+ ", province=" + province + ", city=" + city + ", optioner="
				+ optioner + "]";
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getEndIp() {
		return endIp;
	}
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}
	public Long getStartDecIp() {
		return startDecIp;
	}
	public void setStartDecIp(Long startDecIp) {
		this.startDecIp = startDecIp;
	}
	public Long getEndDecIp() {
		return endDecIp;
	}
	public void setEndDecIp(Long endDecIp) {
		this.endDecIp = endDecIp;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	public String getOptioner() {
		return optioner;
	}
	public void setOptioner(String optioner) {
		this.optioner = optioner;
	}
	

}
