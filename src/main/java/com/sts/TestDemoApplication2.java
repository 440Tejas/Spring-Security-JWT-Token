package com.sts;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestDemoApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(TestDemoApplication2.class, args);
	}
    
	@Bean
	public ModelMapper modalMapper() {
		return new ModelMapper();
	}
}
