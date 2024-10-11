package com.spring.onlinebookstore.service.category;

import com.spring.onlinebookstore.dto.category.CategoryDto;
import com.spring.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.spring.onlinebookstore.dto.category.UpdateCategoryRequestDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.CategoryMapper;
import com.spring.onlinebookstore.model.Category;
import com.spring.onlinebookstore.repository.category.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category not found with id " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto save(CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = categoryMapper.toModel(createCategoryRequestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, UpdateCategoryRequestDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category not found with id " + id));
        Category updatedCategory = categoryMapper.toModel(categoryDto, category);
        return categoryMapper.toDto(categoryRepository.save(updatedCategory));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
