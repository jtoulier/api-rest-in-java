package com.springonly.apirestinjava.mapper;

import com.springonly.apirestinjava.entity.CreditEntity;
import com.springonly.apirestinjava.entity.OrderEntity;
import com.springonly.apirestinjava.model.dto.CreditDTO;
import com.springonly.apirestinjava.model.dto.OrderDTO;
import com.springonly.apirestinjava.model.request.OrderCreationRequest;
import com.springonly.apirestinjava.model.response.OrderCreationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface OrderCreationMapper {
    // From Request to DTO
    @Mapping(source = "order.businessId", target = "businessId")
    @Mapping(source = "order.businessName", target = "businessName")
    @Mapping(source = "author", target = "author")
    OrderDTO fromOrderCreationRequestToOrderDTO(OrderCreationRequest orderCreationRequest);

    @Mapping(source = "credit.amount", target = "amount")
    @Mapping(source = "credit.interestRate", target = "interestRate")
    @Mapping(source = "credit.dueDate", target = "dueDate")
    @Mapping(source = "author", target = "author")
    CreditDTO fromOrderCreationRequestToCreditDTO(OrderCreationRequest orderCreationRequest);

    // From DTO to Response : Main
    OrderCreationResponse fromOrderIdToOrderCreationResponse(Long orderId);

    // From DTO to Entity
    OrderEntity fromOrderDTOToOrderEntity(OrderDTO orderDTO);
    CreditEntity fromCreditDTOToCreditEntity(CreditDTO creditDTO);
}