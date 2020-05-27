package com.mikaelfrancoeur.controllers;

import com.mikaelfrancoeur.api.v1.dto.CategoryDTO;
import com.mikaelfrancoeur.api.v1.dto.CategoryListDTO;
import com.mikaelfrancoeur.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public final static String BASE_URL = "/api/v1/categories/";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CategoryListDTO listAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("{name}")
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}
