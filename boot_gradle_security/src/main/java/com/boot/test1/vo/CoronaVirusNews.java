package com.boot.test1.vo;

public class CoronaVirusNews {
	
	private String title; // 뉴스제목
	private String originallink; // 오리지날 링크
	private String link; // 링크
	private String description; // 설명..
	private String pubDate; // 게시일
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOriginallink() {
		return originallink;
	}
	public void setOriginallink(String originallink) {
		this.originallink = originallink;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	@Override
	public String toString() {
		return "CoronaVirusNews [title=" + title + ", originallink=" + originallink + ", link=" + link
				+ ", description=" + description + ", pubDate=" + pubDate + "]";
	}

}
