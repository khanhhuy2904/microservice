package com.example.controller;

import com.example.dto.UserDTO;
import com.example.repository.UserRepository;
import com.example.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        var userEntity = userMapper.toEntity(userDTO);
        var savedUser = userRepository.save(userEntity);
        var savedUserDTO = userMapper.toDTO(savedUser);
        return ResponseEntity.ok(savedUserDTO);
    }
}
