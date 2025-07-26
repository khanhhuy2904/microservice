package com.example.controller;

import com.example.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/check-user/{userId}")
    public String checkUser(@PathVariable Long userId) {
        String url = "http://user-service/users/" + userId;
        try {
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return "Found user: " + response.getBody().getName();
            }
        } catch (Exception e) {
            return "User not found or error.";
        }
        return "Something went wrong.";
    }
}

