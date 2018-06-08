package cn.edu360.day07.model;

import java.util.Arrays;

public class ResultBean {
	private double[] queryLocation;
	private AddrList[] addrList;
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
	@Override
	public String toString() {
		return "ResultBean [queryLocation=" + Arrays.toString(queryLocation)
				+ ", addrList=" + Arrays.toString(addrList) + "]";
	}

	
}
