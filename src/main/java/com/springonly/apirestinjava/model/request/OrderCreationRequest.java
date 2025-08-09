package com.springonly.apirestinjava.model.request;

import com.springonly.apirestinjava.model.request.auxiliar.CreditCreationAuxiliarRequest;
import com.springonly.apirestinjava.model.request.auxiliar.OrderCreationAuxiliarRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationRequest {
    private OrderCreationAuxiliarRequest order;
    private CreditCreationAuxiliarRequest credit;
    private String author;
}