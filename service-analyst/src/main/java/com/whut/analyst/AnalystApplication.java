package com.whut.analyst;

import com.whut.analyst.service.LSTM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AnalystApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnalystApplication.class, args);
		new LSTM().run();
	}
}
