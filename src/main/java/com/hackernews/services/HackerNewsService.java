package com.hackernews.services;

import java.util.List;

import com.hackernews.beans.CommentResponse;
import com.hackernews.beans.StoryResponse;

public interface HackerNewsService {
	public List<CommentResponse> getTopComments(Integer storyId);
	public List<StoryResponse> getCachedTopStories();
	public List<StoryResponse> getPastStories();
}
