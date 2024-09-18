package com.spring.onlinebookstore.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @Column(nullable = false, unique = true)
    private String isbn;
    @NotNull
    private BigDecimal price;
    private String description;
    private String coverImage;
}
