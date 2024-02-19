package com.example.servertracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages={
		"com.example.servertracker"})
@RestController
public class ServerTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerTrackerApplication.class, args);
	}
	@GetMapping("/hello")
	public String helloWorld(){

		return "Hello HACKATHLON";
	}

}
