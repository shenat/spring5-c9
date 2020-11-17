package com.sat.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.IntegrationComponentScan;

import com.sat.flow.file.FileWriterGateway;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ComponentScan(basePackages = {"com.sat"})
@IntegrationComponentScan(basePackages = {"com.sat"})
@EntityScan(basePackages = "com.sat")
@SpringBootApplication
public class TacoSimpleFlowC9Application {

	public static void main(String[] args) {
		SpringApplication.run(TacoSimpleFlowC9Application.class, args);
	}
	
	@Bean
	public CommandLineRunner writeData(FileWriterGateway gateway, Environment env) {
		log.info("开始writeData");
		return args -> {
			gateway.writeToFile("simple.txt", "sat-test");
		};
	}

}
