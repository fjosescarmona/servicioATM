package com.everis.bc.servicioATM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class ServicioAtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAtmApplication.class, args);
	}

}
