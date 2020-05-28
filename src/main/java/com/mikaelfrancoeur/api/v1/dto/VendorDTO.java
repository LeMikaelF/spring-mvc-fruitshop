package com.mikaelfrancoeur.api.v1.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VendorDTO {
    private Long id;
    @NotBlank
    private String name;
}
