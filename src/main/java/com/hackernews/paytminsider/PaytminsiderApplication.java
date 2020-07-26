package com.hackernews.paytminsider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hackernews")
public class PaytminsiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaytminsiderApplication.class, args);
	}

}
