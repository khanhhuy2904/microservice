package com.example.service;

import com.example.dto.UserDTO;
import com.example.entity.User;

import java.util.Optional;

public interface UserService {
    UserDTO getUserById(Long id);
    User createUser(User user);
}
