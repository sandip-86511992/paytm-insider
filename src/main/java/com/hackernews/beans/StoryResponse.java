package com.hackernews.beans;

public class StoryResponse {

	private String title;
	private String url;
	private Integer score;
	private Long timeofSubmission;
	private String user;
	
	public StoryResponse(String title, String url, Integer score, Long timeofSubmission, String user) {
		super();
		this.title = title;
		this.url = url;
		this.score = score;
		this.timeofSubmission = timeofSubmission;
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getTimeofSubmission() {
		return timeofSubmission;
	}

	public void setTimeofSubmission(Long timeofSubmission) {
		this.timeofSubmission = timeofSubmission;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StoryResponse [title=");
		builder.append(title);
		builder.append(", url=");
		builder.append(url);
		builder.append(", score=");
		builder.append(score);
		builder.append(", timeofSubmission=");
		builder.append(timeofSubmission);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
