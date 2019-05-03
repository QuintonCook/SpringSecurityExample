package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Car;
import com.example.repository.CarRepository;

@RestController
public class CarController {

	@Autowired
	CarRepository c;
	
	@GetMapping("/get")
	public List<Car> getAllCars() {
		return (List<Car>) c.findAll();
	}
	
	@GetMapping("/getColor/{color}")
	public List<Car> getCarByColor(@PathVariable String color){
		return c.findByColor(color);
	}

}
