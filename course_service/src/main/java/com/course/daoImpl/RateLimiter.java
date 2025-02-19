package com.course.daoImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

	private int MAX_REQUEST;
	private long timeWindow;
	private Map<String, Deque<Long>> userRequestMap;

	RateLimiter(int max_request, int timeWindow) {
		this.MAX_REQUEST = max_request;
		this.timeWindow = timeWindow * 1000l;
		this.userRequestMap = new ConcurrentHashMap<>();
	}

	public synchronized boolean allowRequest(String user) {
		Long currentTime = System.currentTimeMillis();
		userRequestMap.putIfAbsent(user, new ArrayDeque<>());

		Deque<Long> requestTime = userRequestMap.get(user);

		System.out.println("current time " + currentTime + " request time " + requestTime +"Window Time "+ timeWindow);
		while (!requestTime.isEmpty() && (currentTime - requestTime.peekFirst() > timeWindow)) {
			requestTime.pollFirst();
		}

		if (requestTime.size() < MAX_REQUEST) {
			requestTime.offerFirst(currentTime);
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws InterruptedException {

		RateLimiter rateLimiter = new RateLimiter(3, 10);

		System.out.println(rateLimiter.allowRequest("user1"));
		System.out.println(rateLimiter.allowRequest("user1"));
		System.out.println(rateLimiter.allowRequest("user1"));
		System.out.println(rateLimiter.allowRequest("user1")); // false

		Thread.sleep(10000);
		System.out.println(rateLimiter.allowRequest("user1")); // allowed
	}
}
