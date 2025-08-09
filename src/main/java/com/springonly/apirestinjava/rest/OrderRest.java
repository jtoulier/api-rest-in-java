package com.springonly.apirestinjava.rest;

import com.springonly.apirestinjava.mapper.OrderApprovalMapper;
import com.springonly.apirestinjava.mapper.OrderCreationMapper;
import com.springonly.apirestinjava.mapper.OrderRejectionMapper;
import com.springonly.apirestinjava.mapper.OrderRetrievalMapper;
import com.springonly.apirestinjava.model.dto.CreditDTO;
import com.springonly.apirestinjava.model.dto.OrderDTO;
import com.springonly.apirestinjava.model.dto.auxiliar.OrderAndCreditAuxiliarDTO;
import com.springonly.apirestinjava.model.request.OrderApprovalRequest;
import com.springonly.apirestinjava.model.request.OrderCreationRequest;
import com.springonly.apirestinjava.model.request.OrderRejectionRequest;
import com.springonly.apirestinjava.model.response.OrderCreationResponse;
import com.springonly.apirestinjava.model.response.OrderRetrievalResponse;
import com.springonly.apirestinjava.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderRest {

    @Inject
    OrderService orderService;

    @Inject
    OrderRetrievalMapper orderRetrievalMapper;

    @Inject
    OrderCreationMapper orderCreationMapper;

    @Inject
    OrderApprovalMapper orderApprovalMapper;

    @Inject
    OrderRejectionMapper orderRejectionMapper;

    @POST
    @Path("/")
    @Transactional
    public Response createOrder(
        OrderCreationRequest orderCreationRequest
    ) {
        log.info("createOrder REQUEST: orderCreationRequest={}", orderCreationRequest);

        // Map Request to DTO
        OrderDTO orderDTO = orderCreationMapper.fromOrderCreationRequestToOrderDTO(orderCreationRequest);
        CreditDTO creditDTO = orderCreationMapper.fromOrderCreationRequestToCreditDTO(orderCreationRequest);

        // Invoke logic
        Long orderId = orderService.createOrder(orderDTO, creditDTO);

        // Map DTO to Response
        OrderCreationResponse orderCreationResponse = orderCreationMapper.fromOrderIdToOrderCreationResponse(orderId);

        // Return
        log.info("createOrder RESPONSE: orderCreationResponse={}", orderCreationResponse);
        return Response
                .status(Response.Status.CREATED)
                .entity(orderCreationResponse)
                .build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrder(
        @PathParam("orderId") Long orderId
    ) {
        log.info("getOrder REQUEST: orderId={}", orderId);

        // Invoke logic
        OrderAndCreditAuxiliarDTO orderAndCreditAuxiliarDTO = orderService.getOrder(orderId);

        // Map DTO to Response
        OrderRetrievalResponse orderRetrievalResponse =
                orderRetrievalMapper
                        .toOrderRetrievalResponse(
                                orderAndCreditAuxiliarDTO.getOrder(),
                                orderAndCreditAuxiliarDTO.getCredit()
                        );

        // Return
        log.info("getOrder RESPONSE: orderRetrievalResponse={}", orderRetrievalResponse);
        return Response
                .ok(orderRetrievalResponse)
                .build();
    }

    @PATCH
    @Path("/{orderId}/approve")
    @Transactional
    public Response approveOrder(
        @PathParam("orderId") Long orderId,
        OrderApprovalRequest orderApprovalRequest
    ) {
        log.info("approveOrder REQUEST: orderId={} orderApprovalRequest={}", orderId, orderApprovalRequest);

        // From Request to DTO
        OrderDTO orderDTO = orderApprovalMapper.fromOrderApprovalRequestToOrderDTO(orderApprovalRequest);
        orderDTO.setOrderId(orderId);

        // Invocar logica
        orderService.approveOrder(orderDTO);

        // From DTO to Response
        log.info("approveOrder RESPONSE: ");
        return Response
                .noContent()
                .build();
    }

    @PATCH
    @Path("/{orderId}/reject")
    @Transactional
    public Response rejectOrder(
        @PathParam("orderId") Long orderId,
        OrderRejectionRequest orderRejectionRequest
    ) {
        log.info("rejectOrder REQUEST: orderId={} orderRejectionRequest={}", orderId, orderRejectionRequest);

        // From Request to DTO
        OrderDTO orderDTO = orderRejectionMapper.fromOrderRejectionRequestToOrderDTO(orderRejectionRequest);
        orderDTO.setOrderId(orderId);

        // Invocar logica
        orderService.rejectOrder(orderDTO);

        // From DTO to Response
        log.info("rejectOrder RESPONSE: ");
        return Response
                .noContent()
                .build();
    }
}