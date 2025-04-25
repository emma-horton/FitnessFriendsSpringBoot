package com.fitnessfriends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.fitnessfriends")
public class FitnessFriendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessFriendsApplication.class, args);
    }
}