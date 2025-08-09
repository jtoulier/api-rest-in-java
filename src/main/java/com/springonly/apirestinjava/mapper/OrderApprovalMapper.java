package com.springonly.apirestinjava.mapper;

import com.springonly.apirestinjava.model.dto.OrderDTO;
import com.springonly.apirestinjava.model.request.OrderApprovalRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface OrderApprovalMapper {
    OrderDTO fromOrderApprovalRequestToOrderDTO(OrderApprovalRequest orderApprovalRequest);
}
