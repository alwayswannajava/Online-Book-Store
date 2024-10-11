package com.spring.onlinebookstore.mapper;

import com.spring.onlinebookstore.config.MapperConfig;
import com.spring.onlinebookstore.dto.category.CategoryDto;
import com.spring.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.spring.onlinebookstore.dto.category.UpdateCategoryRequestDto;
import com.spring.onlinebookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto createCategoryRequestDto);

    Category toModel(UpdateCategoryRequestDto updateCategoryRequestDto,
                     @MappingTarget Category category);
}
