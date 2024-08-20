package com.aws.hacker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AwsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsApplication.class, args);
    }

}
