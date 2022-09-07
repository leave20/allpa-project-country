package com.craig.allpaprojectcountry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AllpaProjectCountryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllpaProjectCountryApplication.class, args);
    }

}
