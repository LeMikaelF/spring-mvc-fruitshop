package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.VendorDTO;
import com.mikaelfrancoeur.api.v1.mappers.VendorMapper;
import com.mikaelfrancoeur.domain.Vendor;
import com.mikaelfrancoeur.exceptions.NamedResourceNotFoundException;
import com.mikaelfrancoeur.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO findById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::toDTO)
                .orElseThrow(() -> new NamedResourceNotFoundException(Vendor.class, id));
    }

    @Override
    public VendorDTO saveVendor(@Valid VendorDTO vendorDTO) {
        return vendorMapper.toDTO(vendorRepository.save(vendorMapper.fromDto(vendorDTO)));
    }

    @Override
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        final Vendor retrieved = vendorRepository.findById(id)
                .orElseThrow(() -> new NamedResourceNotFoundException(Vendor.class, id));
        if (vendorDTO.getName() != null) {
            retrieved.setName(vendorDTO.getName());
        }
        return vendorMapper.toDTO(vendorRepository.save(retrieved));
    }
}
