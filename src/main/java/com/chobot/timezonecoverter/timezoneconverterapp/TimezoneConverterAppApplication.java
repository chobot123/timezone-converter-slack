package com.chobot.timezonecoverter.timezoneconverterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TimezoneConverterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimezoneConverterAppApplication.class, args);
	}

}
