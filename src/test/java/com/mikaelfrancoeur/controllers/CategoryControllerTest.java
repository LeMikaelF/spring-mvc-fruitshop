package com.mikaelfrancoeur.controllers;

import com.mikaelfrancoeur.api.v1.dto.CategoryDTO;
import com.mikaelfrancoeur.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    public static final int NO_OF_CATEGORIES = 4;
    public static final String CATEGORY_NAME = "category name";
    public static final int CATEGORY_ID = 12345;
    CategoryController controller;
    @Mock
    CategoryService categoryService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CategoryController(categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAllCategories() throws Exception {
        //given
        when(categoryService.getAllCategories())
                .thenReturn(Stream.generate(CategoryDTO::new)
                        .limit(NO_OF_CATEGORIES)
                        .collect(Collectors.toList()));

        //when
        mockMvc.perform(get(CategoryController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categories", hasSize(NO_OF_CATEGORIES)));

        //then
        verify(categoryService).getAllCategories();
    }

    @Test
    void getCategoryByName() throws Exception {
        //given
        when(categoryService.getCategoryByName(CATEGORY_NAME))
                .thenReturn(new CategoryDTO(CATEGORY_NAME));

        //when
        mockMvc.perform(get(CategoryController.BASE_URL + CATEGORY_NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(CATEGORY_NAME)));

        //then
        verify(categoryService).getCategoryByName(CATEGORY_NAME);
    }
}
