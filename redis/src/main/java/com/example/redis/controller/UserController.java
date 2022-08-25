package com.example.redis.controller;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.dto.UserDto;
import com.example.redis.entity.User;
import com.example.redis.entity.UserCredential;
import com.example.redis.repository.UserCredentialRepository;
import com.example.redis.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	private UserCredentialRepository userCredentialRepository;

	@PostMapping("/save")
	public UserDto saveUser(@RequestBody UserDto userDto) {
		return userService.saveUser(userDto);
	}

	@GetMapping("/get/{id}")
	@Cacheable(value = "user", key = "#id")
	public User getUser(@PathVariable long id) {
		return userService.getUser(id);
	}

	@PutMapping("/update/{id}")
	@CachePut(value = "user", key = "#id")
	public User updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
		return userService.updateUser(id, userDto);
	}

	@DeleteMapping("delete/{id}")
	@CacheEvict(value = "user", key="#id")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
	}

	@PostMapping("/saveToken")
	public UserCredential saveToken(@RequestBody UserCredential userCredential) {
		return userCredentialRepository.save(userCredential);
	}

	@GetMapping("/getToken")
	public List<UserCredential> getToken() {
		return userCredentialRepository.findAll();
	}

	@GetMapping("/matchedToken")
	public void getMatchedToken() {
		userService.getMatchedToken();
	}
}
