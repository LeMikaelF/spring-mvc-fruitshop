package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.CustomerDTO;
import com.mikaelfrancoeur.api.v1.dto.CustomerListDTO;

public interface CustomerService {
    CustomerDTO getCustomerById(Long id);

    CustomerListDTO getAllCustomers();

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByDto(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomerByDto(Long id, CustomerDTO customerDTO);

    void deleteCustomer(Long id);
}
