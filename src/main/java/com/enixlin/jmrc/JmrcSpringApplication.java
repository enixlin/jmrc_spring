package com.enixlin.jmrc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@EnableJpaRepositories
@MapperScan("com.exinlin.jmrc.mapper")
//@EnableScheduling

public class JmrcSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmrcSpringApplication.class, args);
	}
}
