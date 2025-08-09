package com.springonly.apirestinjava.model.dto.auxiliar;

import com.springonly.apirestinjava.model.dto.CreditDTO;
import com.springonly.apirestinjava.model.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAndCreditAuxiliarDTO {
    private OrderDTO order;
    private CreditDTO credit;
}