package com.springonly.apirestinjava.mapper;

import com.springonly.apirestinjava.model.dto.OrderDTO;
import com.springonly.apirestinjava.model.request.OrderRejectionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface OrderRejectionMapper {
    // From Request to DTO
    OrderDTO fromOrderRejectionRequestToOrderDTO(OrderRejectionRequest orderRejectionRequest);
}