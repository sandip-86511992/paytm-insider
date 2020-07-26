package com.hackernews.utils;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.hackernews.beans.Item;
import com.hackernews.beans.User;

@Component
public class HackerNewsUtil{
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	private static String baseUrl = "https://hacker-news.firebaseio.com";
	
	public static Item getItem(Integer id) {
		ResponseEntity<Item> response = restTemplate.getForEntity(baseUrl + "/v0/item/" + id + ".json?print=pretty",  Item.class);
		return response.getBody();
	}
	
	public Integer getMaxId() {
		ResponseEntity<Integer> response = restTemplate.getForEntity(baseUrl + "/v0/maxitem.json?print=pretty",  Integer.class);
		return response.getBody();
	}
	
	public static User getUser(String id) {
		ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/v0/user/" + id + ".json?print=pretty",  User.class);
		return response.getBody();
	}
	
	public Integer[] getPastStories() {
		ResponseEntity<Integer[]> response = restTemplate.getForEntity(baseUrl + "/v0/topstories.json?print=pretty",  Integer[].class);
		return response.getBody();
	}
	
	public static ChildCountResult getChildCount(Integer id) {
		AtomicInteger count = new AtomicInteger(0);
		Item item = HackerNewsUtil.getItem(id);
		if (item.getKids() != null) {
			count.addAndGet(item.getKids().size());
			item.getKids().forEach(kid -> count.addAndGet(getChildCount(kid).getCount()));
		}
		return new ChildCountResult(item, count.get());
	}

}
