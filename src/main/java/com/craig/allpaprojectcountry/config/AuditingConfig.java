package com.craig.allpaprojectcountry.config;

import com.craig.allpaprojectcountry.model.audit.AuditorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class AuditingConfig {

    @Bean
    AuditorAware<String> auditorAware() {
        return new AuditorImpl();
    }
}
