package com.example.boot_azure;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCosmosRepositories
public class BootAzureApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootAzureApplication.class, args);
	}

}
