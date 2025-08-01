package com.example.service.mapper;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO dto);

}


