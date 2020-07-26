package com.hackernews.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hackernews.beans.CommentResponse;
import com.hackernews.beans.Item;
import com.hackernews.beans.StoryResponse;
import com.hackernews.beans.User;
import com.hackernews.repo.HackerNewsRepo;
import com.hackernews.utils.ChildCountResult;
import com.hackernews.utils.ExecutorUtil;
import com.hackernews.utils.HackerNewsUtil;

@Component
public class HackerNewsServiceImpl implements HackerNewsService {

	@Autowired
	private HackerNewsUtil hackerNewsUtil;

	@Autowired
	private ExecutorUtil executorUtil;

	@Value(value = "${poolSize}")
	private int poolSize;
	
	@Value(value="${apiTimeoutSeconds}")
	private Integer apiTimeoutSeconds;

	List<Item> stories = new ArrayList<Item>();

	@Override
	public List<StoryResponse> getCachedTopStories() {
		return HackerNewsRepo.getTopStories();
	}

	@Override
	public List<CommentResponse> getTopComments(Integer storyId) {
		List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
		try {
			Item item = HackerNewsUtil.getItem(storyId);
			Map<Integer, Future<ChildCountResult>> commentFutureMap = new HashMap<Integer, Future<ChildCountResult>>();
			Map<Integer, ChildCountResult> commentMap = new HashMap<Integer, ChildCountResult>();
			if (item.getKids() != null) { 
				for (int index = 0; index <	item.getKids().size(); index ++) {
					int id = item.getKids().get(index);
					commentFutureMap.put(id, executorUtil.getChildCountFuture(id));
				} 
			}
			commentFutureMap.forEach((key, value) -> updateMap(commentMap, key, value));
			List<Item> sortedItems = commentMap.values().stream().map(childCountResult -> childCountResult.getItem()).collect(Collectors.toList());
			List<String> userIds = sortedItems.stream().map(item1 -> item1.getBy()).collect(Collectors.toList());
			List<Future<User>> userFutures = executorUtil.getUsers(userIds);
			for (int index = 0; index < sortedItems.size(); index++) {
				Item itemInner = sortedItems.get(index);
				User user = userFutures.get(index).get(apiTimeoutSeconds, TimeUnit.SECONDS);
				commentResponses.add(new CommentResponse(itemInner.getText(), itemInner.getBy(), System.currentTimeMillis()/1000 - user.getCreated()));
			}
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentResponses;
	}
	
	public void updateMap(Map<Integer, ChildCountResult> commentMap, Integer key, Future<ChildCountResult> value) {
		try {
			commentMap.put(key, value.get(apiTimeoutSeconds, TimeUnit.SECONDS));
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<Integer, ChildCountResult> sortMap(Map<Integer, ChildCountResult> initialMap) {
		return initialMap.entrySet().stream().sorted((e1, e2) -> e2.getValue().getCount().compareTo(e1.getValue().getCount())).limit(10).collect(
				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	public int getChildCount(Integer id) {
		AtomicInteger count = new AtomicInteger(0);
		Item item = HackerNewsUtil.getItem(id);
		if (item.getKids() != null) {
			count.addAndGet(item.getKids().size());
			item.getKids().forEach(kid -> count.addAndGet(getChildCount(kid)));
		}
		return count.get();
	}

	public List<StoryResponse> getTopStories() {
		List<StoryResponse> storyResponses = new ArrayList<StoryResponse>(); 
		try {
			int maxId = hackerNewsUtil.getMaxId();
			int lastIndex;
			AtomicBoolean expired = new AtomicBoolean(false);
			do {
				lastIndex = maxId - poolSize;
				executorUtil.getItems(maxId, lastIndex).forEach(item -> {if (!checkAndAddStory(item)) expired.set(true); } );
				maxId = lastIndex;
			}while(stories.size() < 10 && !expired.get());
		} catch(Exception e) {
			e.printStackTrace();
		}
		stories.sort((i1, i2) -> i2.getScore() - i1.getScore());
		stories =  stories.stream().limit(10).collect(Collectors.toList());
		for (int index = 0; index < stories.size(); index++) {
			Item story = stories.get(index);
			storyResponses.add(new StoryResponse(story.getTitle(), story.getUrl(), story.getScore(), story.getTime(), story.getBy()));
		}
		return storyResponses;

	}
	
	@Override
	public List<StoryResponse> getPastStories() {
		List<StoryResponse> storyResponses = new ArrayList<StoryResponse>(); 
		Integer[] pastItemIds = hackerNewsUtil.getPastStories();
		executorUtil.getItems(Arrays.asList(pastItemIds)).forEach(itemFuture -> {
			Item item;
			try {
				item = itemFuture.get(apiTimeoutSeconds, TimeUnit.SECONDS);
				if (item.getType().equals("story")) 
					storyResponses.add(new StoryResponse(item.getTitle(), item.getUrl(), item.getScore(), item.getTime(), item.getBy()));
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println(storyResponses);
		return storyResponses;
	}

	private boolean checkExired(Item item) {
		if (System.currentTimeMillis()/1000 - item.getTime() > 600) {
			return true;
		}
		return false;
	}

	private boolean checkAndAddStory(Future<Item> itemFuture) {
		try {
			Item item = itemFuture.get(apiTimeoutSeconds, TimeUnit.SECONDS);
			if (!checkExired(item)) {
				if (item.getType().equals("story")) {
					stories.add(item);
				}
				return true;
			}
			return false;
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}

}
