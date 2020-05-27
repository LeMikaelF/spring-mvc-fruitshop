package com.mikaelfrancoeur.api.v1.mappers;

import com.mikaelfrancoeur.api.v1.dto.CategoryDTO;
import com.mikaelfrancoeur.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

    class CategoryMapperTest {

    final String categoryName = "category name";
    CategoryDTO categoryDTO;
    CategoryMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = CategoryMapper.INSTANCE;
    }

    @Test
    void categoryToCategoryDTO() {
        //given
        Category category = new Category();
        category.setName(categoryName);

        //when
        final CategoryDTO categoryDTO = mapper.toCategoryDTO(category);

        //then
        assertEquals(category.getName(), categoryDTO.getName());
    }

        final String categoryDtoName = "category dto name";
    @Test
    void categoryDTOToCategory() {
        //given
        final CategoryDTO dto = new CategoryDTO();
        dto.setName(categoryDtoName);

        //when
        Category category = mapper.fromCategoryDTO(dto);

        //then
        assertThat(dto.getName()).isEqualTo(category.getName());
    }
}
