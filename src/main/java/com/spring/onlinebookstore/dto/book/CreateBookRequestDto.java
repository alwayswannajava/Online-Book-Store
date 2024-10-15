package com.spring.onlinebookstore.dto.book;

import com.spring.onlinebookstore.model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.ISBN;

public record CreateBookRequestDto(
        @NotBlank
        @Size(min = 2, message = "is too short")
        @Size(max = 255, message = "is too long")
        String title,
        @NotBlank
        @Size(min = 2, message = "is too short")
        @Size(max = 255, message = "is too long")
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
