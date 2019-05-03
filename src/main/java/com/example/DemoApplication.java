package com.example;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.model.Car;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.CarRepository;
import com.example.repository.UserRepository;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	UserRepository userRepository;

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
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword("admin");
			admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

			userRepository.save(admin);

					User client = new User();
			client.setUsername("client");
			client.setPassword("client");
			client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

			userRepository.save(client);
		};

	}

}
