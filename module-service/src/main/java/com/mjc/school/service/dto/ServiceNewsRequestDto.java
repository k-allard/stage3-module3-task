package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceNewsRequestDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
}
