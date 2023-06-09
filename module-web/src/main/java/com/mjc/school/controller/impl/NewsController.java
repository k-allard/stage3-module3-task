package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsRequestDto, NewsResponseDto, Long> {
  private final BaseService<NewsRequestDto, NewsResponseDto, Long> newsService;

  public NewsController(
          @Qualifier("newsService")
          BaseService<NewsRequestDto, NewsResponseDto, Long> newsService
  ) {
    this.newsService = newsService;
  }

  public List<NewsResponseDto> readAll() {
    return newsService.readAll();
  }

  public NewsResponseDto readById(Long newsId) {
    return newsService.readById(newsId);
  }

  public NewsResponseDto create(NewsRequestDto dtoRequest) {
    return newsService.create(dtoRequest);
  }

  public NewsResponseDto update(NewsRequestDto dtoRequest) {
    return newsService.update(dtoRequest);
  }

  public boolean deleteById(Long newsId) {
    return newsService.deleteById(newsId);
  }
}
