package com.mjc.school.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NewsRequestDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
}
