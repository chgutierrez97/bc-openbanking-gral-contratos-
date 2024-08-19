package com.ve.bc.openbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "${api.description}", version = "${api.version}",/* contact = @Contact(name = "Bancaribe", email = "soporteapi@bancaribe.com.ve", url = "https://www.bancaribe.com.ve"),*/ license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), termsOfService = "${tos.uri}"/*, description = "${api.description}"*/),servers = @Server(description = " ",url = "${api.server}"))
public class ConsultaGralServiciosApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsultaGralServiciosApplication.class, args);
	}
}
