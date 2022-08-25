package com.example.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redis.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
