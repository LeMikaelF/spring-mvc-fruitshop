package com.mikaelfrancoeur.api.v1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(String name) {
        this.name = name;
    }
}
