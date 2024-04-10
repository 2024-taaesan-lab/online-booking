package com.xponential.onlinebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Main entry point for the online booking application.
 * Initializes the Spring Boot application context and starts the application.
 */
@SpringBootApplication
@EnableAsync
public class OnlineBookingApplication {

	/**
	 * Main method to start the online booking application.
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(OnlineBookingApplication.class, args);
	}

	@Bean("customTableExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("customExecutor-");
		executor.initialize();
		return executor;
	}
}
