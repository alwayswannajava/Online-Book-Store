package com.spring.onlinebookstore.dto;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @Nonnull
    private String title;
    @Nonnull
    private String author;
    @Column(unique = true)
    @Nonnull
    private String isbn;
    @Nonnull
    private BigDecimal price;
    private String description;
    private String coverImage;
}
