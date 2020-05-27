package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.CustomerDTO;
import com.mikaelfrancoeur.api.v1.dto.CustomerListDTO;
import com.mikaelfrancoeur.api.v1.mappers.CustomerMapper;
import com.mikaelfrancoeur.domain.Customer;
import com.mikaelfrancoeur.exceptions.ResourceNotFoundException;
import com.mikaelfrancoeur.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Customer.class, id));
    }

    @Override
    public CustomerListDTO getAllCustomers() {
        final List<CustomerDTO> all = customerRepository.findAll().stream()
                .map(customerMapper::toDto).collect(Collectors.toList());
        return new CustomerListDTO(all);
    }

    @Override
    public CustomerDTO createNewCustomer(@Valid CustomerDTO customerDTO) {
        return customerMapper.toDto(customerRepository
                .save(customerMapper.fromDto(customerDTO)));
    }

    @Override
    public CustomerDTO saveCustomerByDto(Long id, CustomerDTO customerDTO) {
        final Customer customer = customerMapper.fromDto(customerDTO);
        customer.setId(id);
        final Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }

    @Override
    public CustomerDTO patchCustomerByDto(Long id, CustomerDTO customerDTO) {
        final Optional<Customer> retrieved = customerRepository.findById(id);
        return retrieved.map(toUpdate -> {
            if (customerDTO.getFirstName() != null) {
                toUpdate.setFirstName(customerDTO.getFirstName());
            }
            if (customerDTO.getLastName() != null) {
                toUpdate.setLastName(customerDTO.getLastName());
            }
            toUpdate.setId(id);
            final Customer savedAndUpdated = customerRepository.save(toUpdate);
            return customerMapper.toDto(savedAndUpdated);
        }).orElseThrow(() -> new ResourceNotFoundException(Customer.class, id));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
