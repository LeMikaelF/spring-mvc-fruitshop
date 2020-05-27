package com.mikaelfrancoeur.controllers;

import com.mikaelfrancoeur.api.v1.dto.CustomerDTO;
import com.mikaelfrancoeur.api.v1.dto.CustomerListDTO;
import com.mikaelfrancoeur.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers/";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public CustomerListDTO getAllCustomers() {
        final CustomerListDTO customerListDTO = customerService.getAllCustomers();
        customerListDTO
                .getCustomers()
                .forEach(customerDTO ->
                        customerDTO.setCustomerUrl(String.format("%s%d", CustomerController.BASE_URL, customerDTO.getId())));
        return customerListDTO;
    }

    @GetMapping("{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        final CustomerDTO customerDTO = customerService.getCustomerById(id);
        customerDTO.setCustomerUrl(String.format("%s%d", CustomerController.BASE_URL, id));
        return customerDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //TODO Add validation error handling
    public CustomerDTO createNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        final CustomerDTO savedCustomer = customerService.createNewCustomer(customerDTO);
        savedCustomer.setCustomerUrl(String.format("%s%d", CustomerController.BASE_URL, savedCustomer.getId()));
        return savedCustomer;
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@Valid @RequestBody CustomerDTO customerDTO,
                                      @PathVariable Long id) {
        final CustomerDTO savedCustomerDto = customerService.saveCustomerByDto(id, customerDTO);
        savedCustomerDto.setCustomerUrl(String.format("%s%d", CustomerController.BASE_URL, savedCustomerDto.getId()));
        return savedCustomerDto;
    }

    @PatchMapping("{id}")
    public CustomerDTO patchCustomer(@RequestBody CustomerDTO customerDTO,
                                     @PathVariable Long id) {
        return customerService.patchCustomerByDto(id, customerDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
