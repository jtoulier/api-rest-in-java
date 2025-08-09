package com.springonly.apirestinjava.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "credits")
@Data
public class CreditEntity {
    @Id
    private Long orderId;

    private BigDecimal amount;
    private BigDecimal interestRate;
    private LocalDate dueDate;
    private LocalDateTime writtenAt;
    private String author;
}
