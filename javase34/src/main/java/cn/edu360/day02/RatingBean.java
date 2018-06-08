package cn.edu360.day02;

public class RatingBean {
	private String movie;
	private int rate;
	private String timeStamp;
	private String uid;

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		return "RatingBean [movie=" + movie + ", rate=" + rate + ", timeStamp="
				+ timeStamp + ", uid=" + uid + "]";
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	

}
