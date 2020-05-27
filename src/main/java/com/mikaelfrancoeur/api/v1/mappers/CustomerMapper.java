package com.mikaelfrancoeur.api.v1.mappers;

import com.mikaelfrancoeur.api.v1.dto.CustomerDTO;
import com.mikaelfrancoeur.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDto(Customer customer);

    Customer fromDto(CustomerDTO customerDTO);
}
