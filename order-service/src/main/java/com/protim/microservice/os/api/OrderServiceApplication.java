package com.protim.microservice.os.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}


	@Bean
	@LoadBalanced // Tells Spring Boot that client side load balancing is enabled.
	// This is required for using service names in the url
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
