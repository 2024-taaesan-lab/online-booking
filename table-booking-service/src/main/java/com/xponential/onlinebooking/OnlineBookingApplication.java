package com.xponential.onlinebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the online booking application.
 * Initializes the Spring Boot application context and starts the application.
 */
@SpringBootApplication
public class OnlineBookingApplication {

	/**
	 * Main method to start the online booking application.
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(OnlineBookingApplication.class, args);
	}
}
