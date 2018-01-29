package com.ng.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.ng.member.mapper")
public class NgMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(NgMemberApplication.class, args);
	}
}
