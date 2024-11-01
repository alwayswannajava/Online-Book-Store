package com.spring.onlinebookstore.service.book;

import static com.spring.onlinebookstore.Constants.CATEGORY_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.CATEGORY_NAME;
import static com.spring.onlinebookstore.Constants.CORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.Constants.ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_CATEGORY_MESSAGE;
import static com.spring.onlinebookstore.Constants.INCORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.Constants.UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.UPDATE_CATEGORY_REQUEST_DTO_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.spring.onlinebookstore.dto.category.CategoryDto;
import com.spring.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.spring.onlinebookstore.dto.category.UpdateCategoryRequestDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.CategoryMapper;
import com.spring.onlinebookstore.model.Category;
import com.spring.onlinebookstore.repository.category.CategoryRepository;
import com.spring.onlinebookstore.service.category.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryMapper categoryMapper;

    private CreateCategoryRequestDto createCategoryRequestDto;
    private UpdateCategoryRequestDto updateCategoryRequestDto;
    private Category category;
    private CategoryDto categoryDto;
    private CategoryDto updateCategoryDto;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        createCategoryRequestDto = new CreateCategoryRequestDto(
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );

        updateCategoryRequestDto = new UpdateCategoryRequestDto(
                UPDATE_CATEGORY_REQUEST_DTO_NAME,
                UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION
        );

        category = new Category();
        category.setId(CORRECT_CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);

        categoryDto = new CategoryDto(
                CORRECT_CATEGORY_ID,
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );

        updateCategoryDto = new CategoryDto(
                CORRECT_CATEGORY_ID,
                UPDATE_CATEGORY_REQUEST_DTO_NAME,
                UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION
        );

        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Test save() method")
    void save_ValidCreateCategoryRequestDto_ReturnsCategoryDto() {
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        when(categoryMapper.toModel(createCategoryRequestDto)).thenReturn(category);

        CategoryDto expected = categoryDto;
        CategoryDto actual = categoryService.save(createCategoryRequestDto);

        assertEquals(expected, actual);

        verify(categoryRepository).save(category);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Test findAll() method")
    void findAll_CategoryExists_ReturnsListCategoryDto() {
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        when(categoryRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(category)));

        List<CategoryDto> expected = List.of(categoryDto);
        List<CategoryDto> actual = categoryService.findAll(pageRequest);

        assertEquals(expected, actual);
        verify(categoryRepository).findAll(pageRequest);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Test findById() method")
    void findById_CategoryExists_ReturnsCategoryDto() {
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        when(categoryRepository.findById(CORRECT_CATEGORY_ID))
                .thenReturn(Optional.ofNullable(category));

        CategoryDto expected = categoryDto;
        CategoryDto actual = categoryService.findById(CORRECT_CATEGORY_ID);

        assertEquals(expected, actual);
        verify(categoryRepository).findById(CORRECT_CATEGORY_ID);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Test findById() non-existent category")
    void findById_CategoryNotExists_ShouldThrowEntityNotFoundException() {
        when(categoryRepository.findById(INCORRECT_CATEGORY_ID)).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException =
                assertThrows(EntityNotFoundException.class,
                        () -> categoryService.findById(INCORRECT_CATEGORY_ID));

        String actualMessage = entityNotFoundException.getMessage();

        assertEquals(ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_CATEGORY_MESSAGE, actualMessage);
    }

    @Test
    @DisplayName("Test update() method")
    void update_ValidUpdateCategoryRequestDto_ReturnsCategoryDto() {
        when(categoryMapper.toDto(category)).thenReturn(updateCategoryDto);
        when(categoryRepository.findById(CORRECT_CATEGORY_ID))
                .thenReturn(Optional.ofNullable(category));
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto expected = updateCategoryDto;
        CategoryDto actual = categoryService.update(CORRECT_CATEGORY_ID, updateCategoryRequestDto);

        assertEquals(expected, actual);
        verify(categoryRepository).findById(CORRECT_CATEGORY_ID);
    }

    @Test
    @DisplayName("Test update() non-existent book")
    void update_NonExistentCategory_ShouldThrowEntityNotFoundException() {
        when(categoryRepository.findById(INCORRECT_CATEGORY_ID)).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException =
                assertThrows(EntityNotFoundException.class, () ->
                        categoryService.update(INCORRECT_CATEGORY_ID, updateCategoryRequestDto));

        String actualMessage = entityNotFoundException.getMessage();

        assertEquals(ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_CATEGORY_MESSAGE, actualMessage);
    }
}
