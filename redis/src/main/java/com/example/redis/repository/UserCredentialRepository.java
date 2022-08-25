package com.example.redis.repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.redis.entity.UserCredential;

import redis.clients.jedis.Jedis;

@SuppressWarnings("unchecked")
@Repository
public class UserCredentialRepository {

	public static final String HASH_KEY = "User";
	
	@Value("${redis.cache.time-to-live}")
	private Integer redisTTL;

	@Autowired
	private RedisTemplate template;

	public UserCredential save(UserCredential token){
		template.opsForHash().put(HASH_KEY,UUID.randomUUID(),token);
		template.expire(token, redisTTL, TimeUnit.HOURS);
		return token;
	}

	public List<UserCredential> findAll(){
		return template.opsForHash().values(HASH_KEY);
	}

	public UserCredential findUserCredentialById(int id){
		return (UserCredential) template.opsForHash().get(HASH_KEY,id);
	}


	public String deleteUserCredential(int id){
		template.opsForHash().delete(HASH_KEY,id);
		return "UserCredential removed !!";
	}
	
	public void clearCacheByKeyName(String key){
		template.delete(key);
	}

	public void clearAllCache(){
		Jedis jedis = new Jedis();
		jedis.flushAll();
		jedis.close();
	}
}
