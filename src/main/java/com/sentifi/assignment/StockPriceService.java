package com.sentifi.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StockPriceService {

	public static void main(String[] args) {
		SpringApplication.run(StockPriceService.class, args);
	}
}
