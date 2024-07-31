package com.ve.bc.openbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
//s@EnableFeignClients
@SpringBootApplication

//@OpenAPIDefinition(info = @Info(title = "${api.description}", version = "${api.version}",/* contact = @Contact(name = "Bancaribe", email = "soporteapi@bancaribe.com.ve", url = "https://www.bancaribe.com.ve"),*/ license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), termsOfService = "${tos.uri}"/*, description = "${api.description}"*/),servers = @Server(description = "Servidor de Calidad ",url = "${api.server}"))
public class ContratosGralApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContratosGralApplication.class, args);
	}
}
