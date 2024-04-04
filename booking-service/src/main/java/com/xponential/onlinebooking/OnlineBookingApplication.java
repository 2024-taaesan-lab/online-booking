package com.xponential.onlinebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OnlineBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookingApplication.class, args);


//		List<List<Integer>> tables = new ArrayList<>(5);
//
//		for (int i = 0; i < 5; i++) {
//			tables.add(new ArrayList<>());
//		}
//
//		System.out.println(tables);
	}

}
