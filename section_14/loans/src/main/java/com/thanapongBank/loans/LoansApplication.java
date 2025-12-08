package com.thanapongBank.loans;

import com.thanapongBank.loans.dto.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.thanapongBank.loans.controller") })
@EnableJpaRepositories("com.thanapongBank.loans.repository")
@EntityScan("com.thanapongBank.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "MaleBank Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Thanapong Y",
						email = "malethanapong@gmail.com",
						url = "https://maletnp.github.io/thanapong-portfolio/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://maletnp.github.io/thanapong-portfolio/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "MaleBank Loans microservice REST API Documentation",
				url = "https://maletnp.github.io/thanapong-portfolio/"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
