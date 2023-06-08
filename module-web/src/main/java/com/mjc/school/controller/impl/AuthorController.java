package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.impl.AuthorService;

import java.util.List;

public class AuthorController implements BaseController<AuthorRequestDto, AuthorResponseDto, Long> {
  private final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService = new AuthorService();

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
