package com.example.vic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = "com.example.vic")
@SpringBootApplication
public class VicApplication {

	public static void main(String[] args) {
		SpringApplication.run(VicApplication.class, args);
	}

}
