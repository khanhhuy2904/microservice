package com.example.order_service.service.mapper;


import com.example.order_service.dto.OrderRequest;
import com.example.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "totalPrice", target = "totalPrice")
    OrderRequest toDTO(Order order);

    Order toEntity(OrderRequest dto);
}

