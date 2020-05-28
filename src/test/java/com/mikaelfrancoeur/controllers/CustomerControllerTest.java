package com.mikaelfrancoeur.controllers;

import com.mikaelfrancoeur.api.v1.dto.CustomerDTO;
import com.mikaelfrancoeur.api.v1.dto.CustomerListDTO;
import com.mikaelfrancoeur.exceptions.NamedResourceNotFoundException;
import com.mikaelfrancoeur.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mikaelfrancoeur.RestTestUtils.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    public static final int NO_OF_CUSTOMERS = 9;
    public static final Long CUSTOMER_ID = 456L;
    public static final String CUSTOMER_LAST_NAME = "customer last name";
    public static final String CUSTOMER_FIRST_NAME = "customer first name";
    @Mock
    CustomerService customerService;
    CustomerController controller;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestExceptionHandler()).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        //given
        when(customerService.getAllCustomers())
                .thenReturn(new CustomerListDTO(Stream.generate(CustomerDTO::new)
                        .limit(NO_OF_CUSTOMERS)
                        .collect(Collectors.toList())));

        //when
        mockMvc.perform(get(CustomerController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customers", hasSize(NO_OF_CUSTOMERS)));

        //then
        verify(customerService).getAllCustomers();
    }

    @Test
    void getCustomerById() throws Exception {
        //given
        when(customerService.getCustomerById(CUSTOMER_ID))
                .thenReturn(new CustomerDTO(CUSTOMER_ID));

        //when
        mockMvc.perform(get(String.format("%s%d", CustomerController.BASE_URL, CUSTOMER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(CUSTOMER_ID.intValue())))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + CUSTOMER_ID)));

        //then
        verify(customerService).getCustomerById(CUSTOMER_ID);
    }

    @Test
    void getCustomerNotFound() throws Exception {
        //given
        when(customerService.getCustomerById(anyLong())).thenThrow(NamedResourceNotFoundException.class);

        //when
        mockMvc.perform(get(CustomerController.BASE_URL + CUSTOMER_ID))
                .andExpect(status().isNotFound());

        //then
        verify(customerService).getCustomerById(anyLong());
    }

    @Test
    void createNewCustomer() throws Exception {
        //given
        final CustomerDTO customerDTO = new CustomerDTO(CUSTOMER_ID);
        customerDTO.setFirstName(CUSTOMER_FIRST_NAME);
        customerDTO.setLastName(CUSTOMER_LAST_NAME);
        when(customerService.createNewCustomer(any())).thenReturn(customerDTO);

        //when
        mockMvc.perform(post(CustomerController.BASE_URL)
                .content(asJsonString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(CUSTOMER_ID.intValue())))
                .andExpect(jsonPath("$.lastname", equalTo(CUSTOMER_LAST_NAME)));

        //then
        verify(customerService).createNewCustomer(any());
    }

    @Test
    void updateCustomer() throws Exception {
        //given
        final CustomerDTO customerDTO = new CustomerDTO(CUSTOMER_ID);
        final String firstName = "Mikael";
        customerDTO.setFirstName(firstName);
        customerDTO.setLastName("Francoeur");

        when(customerService.saveCustomerByDto(eq(CUSTOMER_ID), any())).thenReturn(customerDTO);

        //when
        mockMvc.perform(put(String.format("%s%d", CustomerController.BASE_URL, CUSTOMER_ID))
                .content(asJsonString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstname", equalTo(firstName)));
        //then
        verify(customerService).saveCustomerByDto(eq(CUSTOMER_ID), any());
    }

    @Test
    void patchCustomer() throws Exception {
        //given
        final CustomerDTO updatedDto = new CustomerDTO(CUSTOMER_ID);
        updatedDto.setFirstName(CUSTOMER_FIRST_NAME);
        updatedDto.setLastName(CUSTOMER_LAST_NAME);
        final CustomerDTO requestDto = new CustomerDTO(CUSTOMER_ID);

        when(customerService.patchCustomerByDto(CUSTOMER_ID, requestDto)).thenReturn(updatedDto);

        //when
        mockMvc.perform(patch(String.format("%s%d", CustomerController.BASE_URL, CUSTOMER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(CUSTOMER_FIRST_NAME)));

        //then
        verify(customerService).patchCustomerByDto(CUSTOMER_ID, requestDto);
    }

    @Test
    void deleteCustomer() throws Exception {
        //when
        mockMvc.perform(delete(String.format("%s%d", CustomerController.BASE_URL, CUSTOMER_ID)))
                .andExpect(status().isOk());

        //then
        verify(customerService).deleteCustomer(CUSTOMER_ID);
    }
}
