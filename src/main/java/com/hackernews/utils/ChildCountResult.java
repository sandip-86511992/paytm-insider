package com.hackernews.utils;

import com.hackernews.beans.Item;

public class ChildCountResult {
	
	Item item;
	Integer count;
	
	public ChildCountResult(Item item, Integer count) {
		super();
		this.item = item;
		this.count = count;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
