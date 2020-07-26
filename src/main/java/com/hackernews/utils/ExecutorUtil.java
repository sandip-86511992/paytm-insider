package com.hackernews.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hackernews.beans.Item;
import com.hackernews.beans.User;
import com.hackernews.repo.HackerNewsRepo;
import com.hackernews.services.HackerNewsServiceImpl;

@Component
public class ExecutorUtil {
	
	@Value(value = "${poolSize}")
	private int poolSize;
	
	@Value(value="${apiTimeoutSeconds}")
	private Integer apiTimeoutSeconds;
	
	@Autowired
	HackerNewsServiceImpl hackerNewsServiceImpl;
	
	private ThreadPoolExecutor executor;
	
	@PostConstruct
	private void initializeThreadPool() {
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
		scheduleUpdate();
	}
	
	public List<Future<Item>> getItems(Integer maxId, Integer minId) {
		Function<Integer, Item> function = HackerNewsUtil::getItem;
		List<Future<Item>> results = new ArrayList<>();
		for (Integer index = maxId; index > minId; index --) {
			Integer currentId = index;
			Future<Item> result = executor.submit(() -> function.apply(currentId));
			results.add(result);
		}
		return results;
	}
	
	public List<Future<Item>> getItems(List<Integer> ids) {
		Function<Integer, Item> function = HackerNewsUtil::getItem;
		List<Future<Item>> results = new ArrayList<>();
		for (Integer index = 0; index < ids.size(); index++) {
			Integer currentId = ids.get(index);
			Future<Item> result = executor.submit(() -> function.apply(currentId));
			results.add(result);
		}
		return results;
	}
	
	public List<Future<User>> getUsers(List<String> ids) {
		Function<String, User> function = HackerNewsUtil::getUser;
		List<Future<User>> results = new ArrayList<>();
		for (Integer index = 0; index < ids.size(); index++) {
			String currentId = ids.get(index);
			Future<User> result = executor.submit(() -> function.apply(currentId));
			results.add(result);
		}
		return results;
	}
	
	public Future<ChildCountResult> getChildCountFuture(Integer id) {
		Function<Integer, ChildCountResult> function = HackerNewsUtil::getChildCount;
		return executor.submit(() -> function.apply(id));
	}
	
	private void scheduleUpdate() {
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(() -> HackerNewsRepo.setTopStories(hackerNewsServiceImpl.getTopStories()), 0, 10, TimeUnit.MINUTES);
	}
}
