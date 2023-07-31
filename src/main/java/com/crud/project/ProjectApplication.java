package com.crud.project;

import com.crud.project.annotation.EnableSessionConsumer;
import com.crud.project.annotation.EnableSessionPublisher;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@OpenAPIDefinition(info = @Info(title = "Project", version = "1.0"))
@EnableSessionPublisher
@EnableSessionConsumer
@EnableJpaRepositories({"com.crud.project.repository"})
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
