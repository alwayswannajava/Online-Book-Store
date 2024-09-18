package com.spring.onlinebookstore.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateBookRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @Column(nullable = false, unique = true)
        String isbn,
        @NotNull
        BigDecimal price,
        String description,
        String coverImage
) {
}
