package com.whu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@SpringBootApplication
@MapperScan(value = "com.whu.mapper")
public class FcxevaluationApplication
{
	public static void main(String[] args) {
		SpringApplication.run(FcxevaluationApplication.class, args);
	}
}
