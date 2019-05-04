package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	boolean existsByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

	User findByUsername(String username);

}
