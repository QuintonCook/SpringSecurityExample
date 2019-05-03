package com.example;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.model.Car;
import com.example.repository.CarRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CarRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Car("Red", 5));
			repository.save(new Car("Blue", 2));
			repository.save(new Car("Red", 2));
			repository.save(new Car("Green", 5));
			repository.save(new Car("Purple", 1));

			// fetch all customers
		};

	}

}
