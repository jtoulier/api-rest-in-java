package com.springonly.apirestinjava.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String businessId;
    private String businessName;
    private String orderState;
    private LocalDateTime writtenAt;
    private String author;
}
