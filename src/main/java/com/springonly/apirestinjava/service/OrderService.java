package com.springonly.apirestinjava.service;

import com.springonly.apirestinjava.entity.CreditEntity;
import com.springonly.apirestinjava.entity.OrderEntity;
import com.springonly.apirestinjava.mapper.OrderCreationMapper;
import com.springonly.apirestinjava.mapper.OrderRetrievalMapper;
import com.springonly.apirestinjava.model.dto.CreditDTO;
import com.springonly.apirestinjava.model.dto.OrderDTO;
import com.springonly.apirestinjava.model.dto.auxiliar.OrderAndCreditAuxiliarDTO;
import com.springonly.apirestinjava.repository.CreditRepository;
import com.springonly.apirestinjava.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    CreditRepository creditRepository;

    @Inject
    OrderCreationMapper orderCreationMapper;

    @Inject
    OrderRetrievalMapper orderRetrievalMapper;

    public OrderAndCreditAuxiliarDTO getOrder(
        Long orderId
    ) {
        // Logica de lectura
        OrderEntity orderEntity = orderRepository.findById(orderId);
        OrderDTO orderDTO = orderRetrievalMapper.fromOrderEntityToOrderDTO(orderEntity);

        CreditEntity creditEntity = creditRepository.findById(orderId);
        CreditDTO creditDTO = orderRetrievalMapper.fromCreditEntityToCreditDTO(creditEntity);

        OrderAndCreditAuxiliarDTO orderAndCreditAuxiliarDTO = new OrderAndCreditAuxiliarDTO();
        orderAndCreditAuxiliarDTO.setOrder(orderDTO);
        orderAndCreditAuxiliarDTO.setCredit(creditDTO);

        // Retorno
        return orderAndCreditAuxiliarDTO;
    }

    public Long createOrder(
        OrderDTO orderDTO,
        CreditDTO creditDTO
    ) {
        // Order
        OrderEntity orderEntity = orderCreationMapper.fromOrderDTOToOrderEntity(orderDTO);
        orderEntity.setOrderState("EN PROCESO");
        orderEntity.setWrittenAt(LocalDateTime.now());
        orderRepository.persist(orderEntity);

        Long orderId = orderEntity.getOrderId();

        // Credit
        CreditEntity creditEntity = orderCreationMapper.fromCreditDTOToCreditEntity(creditDTO);
        creditEntity.setOrderId(orderId);
        creditEntity.setWrittenAt(LocalDateTime.now());
        creditRepository.persist(creditEntity);

        // Retorno
        return orderId;
    }

    public void approveOrder(
        OrderDTO orderDTO
    ) {
        String sql = "orderState = ?1, writtenAt = ?2, author = ?3 WHERE orderId = ?4";

        orderRepository.update(
            sql,
            "APROBADO",
            LocalDateTime.now(),
            orderDTO.getAuthor(),
            orderDTO.getOrderId()
        );
    }

    public void rejectOrder(
        OrderDTO orderDTO
    ) {
        String sql = "orderState = ?1, writtenAt = ?2, author = ?3 WHERE orderId = ?4";

        orderRepository.update(
                sql,
                "DESAPROBADO",
                LocalDateTime.now(),
                orderDTO.getAuthor(),
                orderDTO.getOrderId()
        );
    }
}