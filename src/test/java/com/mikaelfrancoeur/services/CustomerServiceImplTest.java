package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.CustomerDTO;
import com.mikaelfrancoeur.api.v1.dto.CustomerListDTO;
import com.mikaelfrancoeur.api.v1.mappers.CustomerMapper;
import com.mikaelfrancoeur.domain.Customer;
import com.mikaelfrancoeur.repositories.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.hamcrest.MockitoHamcrest;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

class CustomerServiceImplTest {

    public static final String CUSTOMER_NAME = "customer name";
    public static final int NO_OF_CUSTOMERS = 12;
    final Long CUSTOMER_ID = 12345L;
    CustomerService service;
    @Mock
    CustomerRepository repository;
    @Mock
    CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CustomerServiceImpl(repository, mapper);
    }

    @Test
    void getCustomerById() {
        //given
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.of(new Customer(CUSTOMER_ID)));
        when(mapper.toDto(any())).thenReturn(new CustomerDTO(CUSTOMER_ID));

        //when
        final CustomerDTO customerDTO = service.getCustomerById(CUSTOMER_ID);

        //then
        verify(repository).findById(CUSTOMER_ID);
        verify(mapper).toDto(argThat(hasProperty("id", equalTo(CUSTOMER_ID))));
        assertEquals(CUSTOMER_ID, customerDTO.getId());
    }

    @Test
    void getAllCustomers() {
        //given
        when(repository.findAll())
                .thenReturn(Stream.generate(Customer::new)
                        .limit(NO_OF_CUSTOMERS)
                        .collect(Collectors.toList()));
        when(mapper.toDto(any())).thenReturn(new CustomerDTO());

        //when
        final CustomerListDTO allCustomers = service.getAllCustomers();

        //then
        assertEquals(NO_OF_CUSTOMERS, allCustomers.getCustomers().size());
        verify(repository).findAll();
        verify(mapper, times(NO_OF_CUSTOMERS)).toDto(any());
    }

    @Test
    void createNewCustomer() {
        //given
        final CustomerDTO customerDTO = new CustomerDTO(CUSTOMER_ID);
        final Customer customer = new Customer(CUSTOMER_ID);
        when(repository.save(any())).thenReturn(customer);
        when(mapper.toDto(any())).thenReturn(customerDTO);
        when(mapper.fromDto(any())).thenReturn(customer);

        //when
        final CustomerDTO savedCustomerDto = service.createNewCustomer(customerDTO);

        //then
        assertEquals(CUSTOMER_ID, savedCustomerDto.getId());
        verify(repository).save(any());
        verify(mapper).toDto(any());
        verify(mapper).toDto(any());
    }

    @Test
    void saveCustomerByDto() {
        //given
        final CustomerDTO customerDTO = new CustomerDTO();
        final CustomerDTO customerDTOWithId = new CustomerDTO(CUSTOMER_ID);
        final Customer customer = new Customer();
        final Customer customerWithId = new Customer(CUSTOMER_ID);

        when(repository.save(any())).thenReturn(customerWithId);
        when(mapper.fromDto(any())).thenReturn(customer);
        when(mapper.toDto(any())).thenReturn(customerDTOWithId);

        //when
        final CustomerDTO savedCustomerDto = service.saveCustomerByDto(CUSTOMER_ID, customerDTO);

        //then
        assertEquals(CUSTOMER_ID, savedCustomerDto.getId());
        verify(mapper).fromDto(any());
        verify(mapper).toDto(argThat(hasProperty("id", anything())));
        verify(repository).save(argThat(hasProperty("id", equalTo(CUSTOMER_ID))));
    }

    @Test
    void deleteCustomer() {
        service.deleteCustomer(CUSTOMER_ID);
        verify(repository).deleteById(CUSTOMER_ID);
    }
}
