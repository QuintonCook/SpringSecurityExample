package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Car;
import com.example.model.User;
import com.example.repository.CarRepository;
import com.example.repository.UserRepository;
import com.example.token.security.TokenProvider;

@RestController
public class CarController {

	@Autowired
	CarRepository c;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenProvider tokenProvider;
	
	@GetMapping("/get")
	public List<Car> getAllCars() {
		return (List<Car>) c.findAll();
	}
	
	@GetMapping("/getColor/{color}")
	public List<Car> getCarByColor(@PathVariable String color){
		return c.findByColor(color);
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		User tmp = userRepository.findByUsernameAndPassword(username, password);
		
		if(tmp != null) {
			return tokenProvider.grantToken(username,tmp.getRoles()); 
		}else {
			return null;
		}
	}

}
