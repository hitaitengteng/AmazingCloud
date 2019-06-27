package com.whut.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //1。注解启动一个服务注册中心提供给其他应用进行对话
public class DiscoveryApplication {

	  public static void main(String[] args) {
	        SpringApplication.run(DiscoveryApplication.class, args);
	    }

}
