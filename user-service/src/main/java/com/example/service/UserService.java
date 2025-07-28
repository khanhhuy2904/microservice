package com.example.service;

import com.example.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDTO);
}
