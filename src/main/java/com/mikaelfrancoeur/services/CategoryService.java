package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.CategoryDTO;

import java.util.List;

public interface CategoryService  {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
