package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.VendorDTO;

import javax.validation.Valid;
import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();

    VendorDTO findById(Long id);

    VendorDTO saveVendor(@Valid VendorDTO vendorDTO);

    void deleteVendor(Long id);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
}
