package com.mikaelfrancoeur.api.v1.dto;

import com.mikaelfrancoeur.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerListDTO {
    private List<CustomerDTO> customers;
}
