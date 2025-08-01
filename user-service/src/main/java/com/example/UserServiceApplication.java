package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserServiceApplication.class, args);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "456";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Encoded Password: " + encodedPassword);
    }
}