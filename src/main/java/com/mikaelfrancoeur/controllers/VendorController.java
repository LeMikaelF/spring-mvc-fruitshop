package com.mikaelfrancoeur.controllers;

import com.mikaelfrancoeur.api.v1.dto.VendorDTO;
import com.mikaelfrancoeur.api.v1.dto.VendorListDTO;
import com.mikaelfrancoeur.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public final static String BASE_URL = "/api/v1/vendors/";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO saveVendor(VendorDTO vendorDTO) {
        return vendorService.saveVendor(vendorDTO);
    }

    @DeleteMapping("{id}")
    public void deleteMapping(@PathVariable Long id) {
        vendorService.deleteVendor(id);
    }

    @GetMapping("{id}")
    public VendorDTO getVendor(@PathVariable Long id) {
        return vendorService.findById(id);
    }

    @PatchMapping("{id}")
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }

    @PutMapping("{id}")
    public VendorDTO putVendor(@PathVariable Long id, @Valid @RequestBody VendorDTO vendorDTO) {
        vendorDTO.setId(id);
        return vendorService.saveVendor(vendorDTO);
    }
}
