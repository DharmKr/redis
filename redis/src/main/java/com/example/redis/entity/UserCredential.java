package com.example.redis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("User")
public class UserCredential implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	private LocalDateTime date = LocalDateTime.now();
}
