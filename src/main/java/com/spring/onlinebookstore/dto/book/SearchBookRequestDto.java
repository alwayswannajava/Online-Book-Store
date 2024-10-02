package com.spring.onlinebookstore.dto.book;

public record SearchBookRequestDto(
        String[] title,
        String[] author,
        String[] isbn
) {
}
