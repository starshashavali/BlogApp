package com.shasha.blog.payload;

import lombok.Data;

@Data
public class AuthRequest {
	
	private String email;

	private String pwd;
}
