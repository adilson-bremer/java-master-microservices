package com.study.accounts;

import com.study.accounts.dtos.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
			title = "Accounts microservice REST API documentation",
			description = "JAVA microservices course REST API documentation",
			version = "v1",
			contact = @Contact(
					name = "Lorem Ipsum",
					email = "lorem.ipsum@lipsum.com",
					url = "http://lipsum.com"
			),
			license = @License(
					name = "Lorem Ipsum 2.0",
					url = "http://lipsum.com"
			)
		),
		externalDocs = @ExternalDocumentation(
				description = "JAVA microservices course REST API documentation",
				url = "http://localhost:8080/swagger-ui/index.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
