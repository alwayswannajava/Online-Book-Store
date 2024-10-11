package com.spring.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequestDto(
        @NotBlank
        @Size(min = 2, message = "is too short")
        @Size(max = 255, message = "is too long")
        String name,

        @NotBlank
        @Size(min = 2, message = "is too short")
        @Size(max = 255, message = "is too long")
        String description
){}
