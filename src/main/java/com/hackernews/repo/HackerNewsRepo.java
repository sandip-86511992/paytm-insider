package com.hackernews.repo;

import java.util.List;

import com.hackernews.beans.CommentResponse;
import com.hackernews.beans.StoryResponse;

public class HackerNewsRepo {

	private static List<StoryResponse> topStories;
	private static List<CommentResponse> commentResponse;

	public static List<StoryResponse> getTopStories() {
		return topStories;
	}
	public static void setTopStories(List<StoryResponse> topStories) {
		HackerNewsRepo.topStories = topStories;
	}
	public static List<CommentResponse> getCommentResponse() {
		return commentResponse;
	}
	public static void setCommentResponse(List<CommentResponse> commentResponse) {
		HackerNewsRepo.commentResponse = commentResponse;
	}


}
