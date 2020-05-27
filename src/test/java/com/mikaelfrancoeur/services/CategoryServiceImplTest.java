package com.mikaelfrancoeur.services;

import com.mikaelfrancoeur.api.v1.dto.CategoryDTO;
import com.mikaelfrancoeur.api.v1.mappers.CategoryMapper;
import com.mikaelfrancoeur.domain.Category;
import com.mikaelfrancoeur.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    public static final int SIZE = 5;
    public static final String CATEGORY_NAME = "category name";
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void getAllCategories() {
        //given
        when(categoryRepository.findAll())
                .thenReturn(Stream.generate(Category::new).limit(SIZE).collect(Collectors.toList()));

        //when
        final List<CategoryDTO> foundCategories = categoryService.getAllCategories();

        //then
        assertEquals(SIZE, foundCategories.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryByName() {
        //given
        when(categoryRepository.findByName(CATEGORY_NAME)).thenReturn(new Category(CATEGORY_NAME));
        when(categoryMapper.toCategoryDTO(any())).thenReturn(new CategoryDTO(CATEGORY_NAME));

        //when
        final CategoryDTO foundCategoryDto = categoryService.getCategoryByName(CATEGORY_NAME);

        //then
        assertEquals(CATEGORY_NAME, foundCategoryDto.getName());
        verify(categoryRepository).findByName(CATEGORY_NAME);
    }
}
