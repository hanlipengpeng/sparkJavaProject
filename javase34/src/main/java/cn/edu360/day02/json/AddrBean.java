package cn.edu360.day02.json;

public class AddrBean {
	private double[] queryLocation;
	
	private AddrList[]  addrList;

	public double[] getQueryLocation() {
		return queryLocation;
	}

	public void setQueryLocation(double[] queryLocation) {
		this.queryLocation = queryLocation;
	}

	public AddrList[] getAddrList() {
		return addrList;
	}

	public void setAddrList(AddrList[] addrList) {
		this.addrList = addrList;
	}

}
