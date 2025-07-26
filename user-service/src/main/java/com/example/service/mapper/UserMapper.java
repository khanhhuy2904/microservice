package com.example.service.mapper;

import com.example.dto.UserDTO;
import com.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping từ entity sang dto
    UserDTO toDTO(User user);

    // Mapping ngược lại
    User toEntity(UserDTO userDTO);
}


