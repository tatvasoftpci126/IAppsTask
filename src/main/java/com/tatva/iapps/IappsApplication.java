package com.tatva.iapps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class IappsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IappsApplication.class, args);
	}

}
