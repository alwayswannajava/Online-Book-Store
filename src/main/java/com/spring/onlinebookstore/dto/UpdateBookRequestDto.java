package com.spring.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.ISBN;

public record UpdateBookRequestDto(
        @NotNull
        @NotBlank
        String title,
        @NotNull
        @NotBlank
        String author,
        @NotNull
        @NotBlank
        @ISBN
        String isbn,
        @NotNull
        @Min(0)
        BigDecimal price,
        String description,
        String coverImage
) {}
