package com.mikaelfrancoeur.api.v1.mappers;

import com.mikaelfrancoeur.api.v1.dto.VendorDTO;
import com.mikaelfrancoeur.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO toDTO(Vendor vendor);

    Vendor fromDto(VendorDTO vendorDTO);
}
