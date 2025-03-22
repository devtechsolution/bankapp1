package org.as.devtechsolution.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.as.devtechsolution.loans.dto.LoansContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Loan microservice REST API Documentation",
				description = "BankApp Loan microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Aditya Srivastva",
						email = "aditya5srivastva@gmail.com",
						url = "https://github.com/devtechsolution"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/devtechsolution"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "BankApp Loans microservice REST API Documentation",
				url = "https://github.com/devtechsolution/swagger-ui.html"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
