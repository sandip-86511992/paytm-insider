package com.hackernews.beans;

public class CommentResponse {

	private String text;
	private String userHandle;
	private Long userHNAge;
	
	public CommentResponse(String text, String userHandle, Long userHNAge) {
		super();
		this.text = text;
		this.userHandle = userHandle;
		this.userHNAge = userHNAge;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserHandle() {
		return userHandle;
	}

	public void setUserHandle(String userHandle) {
		this.userHandle = userHandle;
	}

	public Long getUserHNAge() {
		return userHNAge;
	}

	public void setUserHNAge(Long userHNAge) {
		this.userHNAge = userHNAge;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommentResponse [text=");
		builder.append(text);
		builder.append(", userHandle=");
		builder.append(userHandle);
		builder.append(", userHNAge=");
		builder.append(userHNAge);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
