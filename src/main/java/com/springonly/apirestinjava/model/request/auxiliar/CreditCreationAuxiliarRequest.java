package com.springonly.apirestinjava.model.request.auxiliar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCreationAuxiliarRequest {
    private BigDecimal amount;
    private BigDecimal interestRate;
    private LocalDate dueDate;
}