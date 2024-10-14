package com.spring.onlinebookstore.controller;

import com.spring.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.spring.onlinebookstore.dto.category.CategoryDto;
import com.spring.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.spring.onlinebookstore.dto.category.UpdateCategoryRequestDto;
import com.spring.onlinebookstore.service.book.BookService;
import com.spring.onlinebookstore.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get all categories", description = "Get all categories")
    @PreAuthorize("hasRole('USER')")
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "post", description = "POST methods of Book APIs")
    @Operation(summary = "Create new category", description = "Create new category")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto save(@RequestBody
                                @Valid CreateCategoryRequestDto createCategoryRequestDto) {
        return categoryService.save(createCategoryRequestDto);
    }

    @GetMapping("/{id}")
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get category by id", description = "Get category by id")
    @PreAuthorize("hasRole('USER')")
    public CategoryDto findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    @Tag(name = "update", description = "UPDATE methods of Book APIs")
    @Operation(summary = "Update category", description = "Update category")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto update(@PathVariable Long id,
                              @RequestBody @Valid
                              UpdateCategoryRequestDto updateCategoryRequestDto) {
        return categoryService.update(id, updateCategoryRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "delete", description = "DELETE methods of Book APIs")
    @Operation(summary = "Delete category", description = "Delete category")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get all books by category id",
            description = "Get all books by category id")
    @PreAuthorize("hasRole('USER')")
    public Page<BookDtoWithoutCategoryIds> findAllByCategoryId(@PathVariable Long id,
                                                               Pageable pageable) {
        return bookService.findAllByCategoriesId(id, pageable);
    }
}
