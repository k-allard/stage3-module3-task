package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;
import com.mjc.school.controller.mapper.ServiceToWebDTOMapper;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController implements BaseController<NewsRequestDto, NewsResponseDto, Long> {
    private final BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> newsService;

    private final ServiceToWebDTOMapper mapper = new ServiceToWebDTOMapper();

    public NewsController(
            @Qualifier("newsService")
            BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> newsService
    ) {
        this.newsService = newsService;
    }

    public List<NewsResponseDto> readAll() {
        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        for (ServiceNewsResponseDto serviceDto : newsService.readAll()) {
            newsResponseDtoList.add(mapper.mapServiceNewsResponseDto(serviceDto));
        }
        return newsResponseDtoList;
    }

    public NewsResponseDto readById(Long newsId) {
        return mapper.mapServiceNewsResponseDto(
                newsService.readById(newsId));
    }

    public NewsResponseDto create(NewsRequestDto dtoRequest) {
        return mapper.mapServiceNewsResponseDto(
                newsService.create(mapper.mapNewsRequestDto(dtoRequest)));
    }

    public NewsResponseDto update(NewsRequestDto dtoRequest) {
        return mapper.mapServiceNewsResponseDto(
                newsService.update(mapper.mapNewsRequestDto(dtoRequest)));
    }

    public boolean deleteById(Long newsId) {
        return newsService.deleteById(newsId);
    }
}
