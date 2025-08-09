package com.springonly.apirestinjava.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String businessId;
    private String businessName;
    private String orderState;
    private LocalDateTime writtenAt;
    private String author;
}