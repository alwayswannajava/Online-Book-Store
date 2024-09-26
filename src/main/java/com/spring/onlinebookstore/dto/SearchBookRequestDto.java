package com.spring.onlinebookstore.dto;

public record SearchBookRequestDto(
        String[] title,
        String[] author,
        String[] isbn
) {
}
