package com.hackernews.beans;

public class User {

	private String id;
	private Long created;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String id, Long created) {
		super();
		this.id = id;
		this.created = created;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", created=");
		builder.append(created);
		builder.append("]");
		return builder.toString();
	}
	
	
}
