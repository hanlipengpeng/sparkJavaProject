package cn.edu360.day04;

public class MovieBean {
	private String movie;
	private int rate;
	private String timeStamp;
	private String uid;
	
	
	
	public String getMovie() {
		return movie;
	}
	public void setMovie(String movie) {
		this.movie = movie;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "MovieBean [movie=" + movie + ", rate=" + rate + ", timeStamp="
				+ timeStamp + ", uid=" + uid + "]";
	}

}
