package com.mikaelfrancoeur.api.v1.mappers;

import com.mikaelfrancoeur.api.v1.dto.CategoryDTO;
import com.mikaelfrancoeur.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toCategoryDTO(Category category);

    Category fromCategoryDTO(CategoryDTO categoryDTO);
}
