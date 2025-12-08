package com.thanapongBank.accounts;

import com.thanapongBank.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableFeignClients
/*@ComponentScans({ @ComponentScan("com.thanapongBank.accounts.controller") })
@EnableJpaRepositories("com.thanapongBank.accounts.repository")
@EntityScan("com.thanapongBank.accounts.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "MaleBank Accounts microservice REST API Documentation",
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
				description = "MaleBank Accounts microservice REST API Documentation",
				url = "https://maletnp.github.io/thanapong-portfolio/"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}
}
