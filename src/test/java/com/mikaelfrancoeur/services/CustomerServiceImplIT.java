package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.CustomerDTO;
import com.mikaelfrancoeur.api.v1.mappers.CustomerMapper;
import com.mikaelfrancoeur.bootstrap.Bootstrap;
import com.mikaelfrancoeur.repositories.CategoryRepository;
import com.mikaelfrancoeur.repositories.CustomerRepository;
import com.mikaelfrancoeur.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
public class CustomerServiceImplIT {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;
    VendorRepository vendorRepository;

    CustomerService customerService;

    @BeforeEach
    public void setup() {
        new Bootstrap(categoryRepository, customerRepository, vendorRepository).run();
        log.debug("Number of customers in repository: {}", customerRepository.count());
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void contextLoads() {

    }

    @Test
    @DisplayName("when patching a customer, transfers the firstName and lastName properties.")
    void patchCustomer() {
        final Long id = getFirstDatabaseId();
        final String newFirstName = "New first name";
        final String newLastName = "new Last name";

        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(newFirstName);
        customerDTO.setLastName(newLastName);

        customerService.patchCustomerByDto(id, customerDTO);
        final CustomerDTO retrieved = customerService.getCustomerById(id);

        assertEquals(newFirstName, retrieved.getFirstName());
        assertEquals(newLastName, retrieved.getLastName());
    }

    Long getFirstDatabaseId() {
        return customerService.getAllCustomers().getCustomers().stream()
                .findFirst().map(CustomerDTO::getId).orElseThrow(() -> new RuntimeException("Could not find any customers in database"));
    }
}
