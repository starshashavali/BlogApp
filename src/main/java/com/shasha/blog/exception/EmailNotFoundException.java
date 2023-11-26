package com.shasha.blog.exception;

public class EmailNotFoundException  extends RuntimeException{
	public EmailNotFoundException(String msg) {
		super(msg);
	}

}
