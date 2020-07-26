package com.hackernews.beans;

import java.util.List;

public class Item {
	
	private Integer id;
	private String title;
	private String url;
	private Integer score;
	private Long time;
	private String by;
	private List<Integer> kids;
	private String text;
	private String type;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public List<Integer> getKids() {
		return kids;
	}

	public void setKids(List<Integer> kids) {
		this.kids = kids;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Item(Integer id, String title, String url, Integer score, Long time, String by, List<Integer> kids,
			String text, String type) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.score = score;
		this.time = time;
		this.by = by;
		this.kids = kids;
		this.text = text;
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", url=");
		builder.append(url);
		builder.append(", score=");
		builder.append(score);
		builder.append(", time=");
		builder.append(time);
		builder.append(", by=");
		builder.append(by);
		builder.append(", kids=");
		builder.append(kids);
		builder.append(", text=");
		builder.append(text);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
