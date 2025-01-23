package com.batch.exception;

public class ServiceFailureException extends RuntimeException {

	public ServiceFailureException(String message) {
		super(message);
	}
}
