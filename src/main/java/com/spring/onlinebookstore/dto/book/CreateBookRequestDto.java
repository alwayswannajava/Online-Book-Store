package com.spring.onlinebookstore.dto.book;

import com.spring.onlinebookstore.model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.ISBN;

public record CreateBookRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        @ISBN
        String isbn,
        @NotNull
        @Min(0)
        BigDecimal price,
        String description,
        String coverImage,
        @NotEmpty
        List<Category> categoryIds
) {
}
