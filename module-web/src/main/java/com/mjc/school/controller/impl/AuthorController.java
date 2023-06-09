package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorRequestDto, AuthorResponseDto, Long> {
    private final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService;

    public AuthorController(
            @Qualifier("authorService")
            BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService
    ) {
        this.authorService = authorService;
    }

    public List<AuthorResponseDto> readAll() {
        return authorService.readAll();
    }

    public AuthorResponseDto readById(Long newsId) {
        return authorService.readById(newsId);
    }

    public AuthorResponseDto create(AuthorRequestDto dtoRequest) {
        return authorService.create(dtoRequest);
    }

    public AuthorResponseDto update(AuthorRequestDto dtoRequest) {
        return authorService.update(dtoRequest);
    }

    public boolean deleteById(Long newsId) {
        return authorService.deleteById(newsId);
    }
}
