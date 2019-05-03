package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Car;

public interface CarRepository extends CrudRepository<Car, Integer> {

	List<Car> findByColor(String lastName);
	
}
