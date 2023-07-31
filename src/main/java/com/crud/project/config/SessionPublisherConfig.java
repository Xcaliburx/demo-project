package com.crud.project.config;

import com.crud.project.service.SessionService;
import com.crud.project.service.impl.SessionServiceImpl;
import com.crud.project.session.SessionPublisher;
import com.crud.project.session.SessionPublisherDbImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan({ "com.crud.project.model" })
public class SessionPublisherConfig {

    @Bean
    public SessionPublisher sessionPublisher() {
        return new SessionPublisherDbImpl();
    }
}
