package com.example.service.mapper;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO dto);

}


