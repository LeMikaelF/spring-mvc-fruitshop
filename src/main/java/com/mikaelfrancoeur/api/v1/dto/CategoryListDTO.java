package com.mikaelfrancoeur.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListDTO {
    private List<CategoryDTO> categories;
}
