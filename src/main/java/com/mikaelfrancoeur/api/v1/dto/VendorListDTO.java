package com.mikaelfrancoeur.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorListDTO {
    private List<VendorDTO> vendors;
}
