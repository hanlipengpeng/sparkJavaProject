package cn.edu360.day08;

import org.jsoup.nodes.Document;

/**
 * 页面信息
 * @author root
 *
 */
public class Page {
	private Document document;
	private String nextPageUrl;
	private boolean hasNextPage;
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	@Override
	public String toString() {
		return "Page [document=" + document + ", nextPageUrl=" + nextPageUrl
				+ ", hasNextPage=" + hasNextPage + "]";
	}
	
}
