package com.craig.allpaprojectcountry.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;


@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .apiInfo(info())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo info() {
        return new ApiInfo(
                "Country Service",
                "Country Service",
                "1.0",
                "Terms of service",
                new Contact(
                        "Craig",
                        "www.craig.com",
                        "Apache License Version 2.0"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
            }
        };
    }



}
