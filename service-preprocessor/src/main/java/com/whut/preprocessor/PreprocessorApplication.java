package com.whut.preprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PreprocessorApplication {

	  public static void main(String[] args) {
	        SpringApplication.run(PreprocessorApplication.class, args);
	    }

}
