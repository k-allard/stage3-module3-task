package com.mjc.school.service.impl;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;

import java.util.List;

//TODO implement
public class AuthorService implements BaseService<AuthorRequestDto, AuthorRequestDto, Long> {
    @Override
    public List<AuthorRequestDto> readAll() {
        return null;
    }

    @Override
    public AuthorRequestDto readById(Long id) {
        return null;
    }

    @Override
    public AuthorRequestDto create(AuthorRequestDto createRequest) {
        return null;
    }

    @Override
    public AuthorRequestDto update(AuthorRequestDto updateRequest) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
