package com.mandacarubroker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("checkstyle:MissingJavadocType")
@OpenAPIDefinition(info = @Info(
    title = "Mandacaru Broker API",
    version = "1",
    description = "API desenvolvido para simular o funcionamento de um Home Broker")
)
@SpringBootApplication
public class MandacarubrokerApplication {
  public static void main(String[] args) {
    SpringApplication.run(MandacarubrokerApplication.class, args);
  }
}
