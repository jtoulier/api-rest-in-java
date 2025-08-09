package com.springonly.apirestinjava.model.request.auxiliar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationAuxiliarRequest {
    private String businessId;
    private String businessName;
}