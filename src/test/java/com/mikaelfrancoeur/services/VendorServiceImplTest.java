package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.VendorDTO;
import com.mikaelfrancoeur.api.v1.mappers.VendorMapper;
import com.mikaelfrancoeur.domain.Vendor;
import com.mikaelfrancoeur.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.hamcrest.MockitoHamcrest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

class VendorServiceImplTest {
    public static final int NO_OF_VENDORS = 8;
    public static final Long VENDOR_ID = 123L;
    public static final String VENDOR_NAME = "vendor name";
    VendorService service;
    @Mock
    VendorRepository vendorRepository;
    @Mock
    VendorMapper vendorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    void getAllVendors() {
        //given
        when(vendorRepository.findAll())
                .thenReturn(Stream.generate(Vendor::new)
                        .limit(NO_OF_VENDORS).collect(Collectors.toList()));

        //when
        final List<VendorDTO> foundVendors = service.getAllVendors();

        //then
        assertEquals(NO_OF_VENDORS, foundVendors.size());
        verify(vendorRepository).findAll();
    }

    @Test
    void findById() {
        //given
        final Vendor savedVendor = new Vendor();
        savedVendor.setId(VENDOR_ID);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(savedVendor));
        final VendorDTO dto = new VendorDTO();
        dto.setId(VENDOR_ID);
        when(vendorMapper.toDTO(any())).thenReturn(dto);

        //when
        final VendorDTO foundVendor = service.findById(VENDOR_ID);

        //then
        verify(vendorRepository).findById(VENDOR_ID);
        assertEquals(VENDOR_ID, foundVendor.getId());
    }

    @Test
    void saveVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(VENDOR_NAME);
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);
        when(vendorRepository.save(any())).thenReturn(vendor);
        when(vendorMapper.fromDto(any())).thenReturn(vendor);
        when(vendorMapper.toDTO(any())).thenReturn(vendorDTO);

        //when
        final VendorDTO savedDto = service.saveVendor(vendorDTO);

        //then
        assertEquals(vendorDTO, savedDto);
        verify(vendorRepository).save(vendor);
        verify(vendorMapper).fromDto(vendorDTO);
        verify(vendorMapper).toDTO(vendor);
    }

    @Test
    void deleteVendor() {
        service.deleteVendor(VENDOR_ID);
        verify(vendorRepository).deleteById(VENDOR_ID);
    }

    @Test
    void patchVendor() {
        //given
        final VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);
        final Vendor vendor = new Vendor();
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any())).thenReturn(vendor);
        when(vendorMapper.toDTO(any())).thenReturn(vendorDTO);

        //when
        final VendorDTO patched = service.patchVendor(VENDOR_ID, vendorDTO);

        //then
        assertEquals(VENDOR_NAME, patched.getName());
        verify(vendorRepository).findById(VENDOR_ID);
        verify(vendorRepository).save(argThat(hasProperty("name", equalTo(VENDOR_NAME))));
    }
}
