package com.mikaelfrancoeur.api.v1.mappers;

import com.mikaelfrancoeur.api.v1.dto.VendorDTO;
import com.mikaelfrancoeur.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {

    public static final Long VENDOR_ID = 12345L;
    public static final String VENDOR_NAME = "vendor name";

    VendorMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = VendorMapper.INSTANCE;
    }

    @Test
    void toDTO() {
        final Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        vendor.setName(VENDOR_NAME);

        final VendorDTO vendorDTO = mapper.toDTO(vendor);
        assertEquals(VENDOR_ID, vendorDTO.getId());
        assertEquals(VENDOR_NAME, vendorDTO.getName());
    }

    @Test
    void fromDto() {
        final VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(VENDOR_ID);
        vendorDTO.setName(VENDOR_NAME);

        final Vendor vendor = mapper.fromDto(vendorDTO);
        assertEquals(VENDOR_ID, vendor.getId());
        assertEquals(VENDOR_NAME, vendor.getName());
    }
}
