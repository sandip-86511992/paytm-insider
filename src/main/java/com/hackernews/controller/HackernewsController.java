package com.hackernews.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackernews.beans.CommentResponse;
import com.hackernews.beans.StoryResponse;
import com.hackernews.services.HackerNewsServiceImpl;

@RestController
public class HackernewsController {

	@Autowired
	HackerNewsServiceImpl hackerNewsServiceImpl;

	@RequestMapping("/")
	public String test() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/top-stories")
	public List<StoryResponse> getTopStories() {
		return hackerNewsServiceImpl.getCachedTopStories();
	}

	@RequestMapping("/comments/{storyId}")
	public List<CommentResponse> getComments(@PathVariable("storyId") String storyId) {
		return hackerNewsServiceImpl.getTopComments(Integer.parseInt(storyId));
	}

	@RequestMapping("/past-stories")
	public List<StoryResponse> getPastStories() {	
		return hackerNewsServiceImpl.getPastStories();
	}

}
