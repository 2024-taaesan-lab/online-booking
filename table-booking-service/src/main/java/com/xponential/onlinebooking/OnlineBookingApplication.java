package com.xponential.onlinebooking;

import com.xponential.onlinebooking.model.NotEnoughTablesForAllCustomersException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class OnlineBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookingApplication.class, args);

//		reserve2(6, 10);
	}
}
