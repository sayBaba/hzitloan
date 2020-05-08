package com.hzit.repay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableHystrix
@EnableFeignClients(basePackages = "com.hzit.repay.client")
@SpringBootApplication
//@MapperScan("com.hzit.repay.mapper")
public class RepayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepayApplication.class, args);
	}

}
