package com.example.redis.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.redis.dto.UserDto;
import com.example.redis.entity.User;
import com.example.redis.entity.UserCredential;
import com.example.redis.repository.UserCredentialRepository;
import com.example.redis.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	private ModelMapper modelMapper;

	private UserRepository userRepository;

	private UserCredentialRepository userCredentialRepository;

	public UserDto saveUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		user=userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	public User getUser(long id) {
		System.out.println("db call");
		return userRepository.findById(id).orElse(null);
	}

	public User updateUser(long id, UserDto userDto) {
		System.out.println("db call");
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isPresent()) {
			userOptional.get().setEmail(userDto.getEmail());
			userOptional.get().setPassword(userDto.getPassword());
			return userRepository.save(userOptional.get());
		}
		return null;
	}

	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}

	public void getMatchedToken() {
		String password = "1234567abc";
		List<UserCredential> userTokenList = userCredentialRepository.findAll();
		System.out.println("userTokenList: "+userTokenList);

		userTokenList.stream().forEach(e->{
			if(e.getPassword().equals(password)) {
				System.out.println("isMatch: "+true);
			}else {
				System.out.println("isMatch: "+false);
			}
		});
	}
}
