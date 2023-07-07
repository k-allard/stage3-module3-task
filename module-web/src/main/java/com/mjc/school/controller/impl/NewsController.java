package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.CommandHandler;
import com.mjc.school.controller.ExtendedController;
import com.mjc.school.controller.dto.AuthorResponseDto;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;
import com.mjc.school.controller.dto.TagDto;
import com.mjc.school.controller.mapper.ServiceToWebDTOMapper;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtendedService;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import com.mjc.school.service.dto.ServiceTagDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController implements BaseController<NewsRequestDto, NewsResponseDto, Long>, ExtendedController {
    private final BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> newsService;
    private final ExtendedService extendedService;
    private final ServiceToWebDTOMapper mapper = new ServiceToWebDTOMapper();

    public NewsController(
            @Qualifier("newsService")
            BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> newsService,
            ExtendedService extendedService) {
        this.newsService = newsService;
        this.extendedService = extendedService;
    }

    @CommandHandler(code = 1)
    public List<NewsResponseDto> readAll() {
        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        for (ServiceNewsResponseDto serviceDto : newsService.readAll()) {
            newsResponseDtoList.add(mapper.mapServiceNewsResponseDto(serviceDto));
        }
        return newsResponseDtoList;
    }

    @CommandHandler(code = 2)
    public NewsResponseDto readById(Long newsId) {
        return mapper.mapServiceNewsResponseDto(
                newsService.readById(newsId));
    }

    @CommandHandler(code = 3)
    public NewsResponseDto create(NewsRequestDto dtoRequest) {
        return mapper.mapServiceNewsResponseDto(
                newsService.create(mapper.mapNewsRequestDto(dtoRequest)));
    }

    @CommandHandler(code = 4)
    public NewsResponseDto update(NewsRequestDto dtoRequest) {
        return mapper.mapServiceNewsResponseDto(
                newsService.update(mapper.mapNewsRequestDto(dtoRequest)));
    }

    @CommandHandler(code = 5)
    public boolean deleteById(Long newsId) {
        return newsService.deleteById(newsId);
    }

    @CommandHandler(code = 16)
    public AuthorResponseDto readAuthorByNewsId(Long id) {
        return mapper.mapServiceAuthorResponseDto(extendedService.readAuthorByNewsId(id));
    }

    @CommandHandler(code = 17)
    public List<TagDto> readTagsByNewsId(Long id) {
        List<ServiceTagDto> serviceTagDtos = extendedService.readTagsByNewsId(id);
        List<TagDto> tagsList = new ArrayList<>();
        for (ServiceTagDto tag : serviceTagDtos) {
            tagsList.add(mapper.mapServiceTagDto(tag));
        }
        return tagsList;
    }

    @CommandHandler(code = 18)
    public List<NewsResponseDto> readNewsByParams(List<Long> tagsIds, String tagName, String authorName, String title, String content) {
        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        for (ServiceNewsResponseDto serviceDto : extendedService.readNewsByParams(tagsIds, tagName, authorName, title, content)) {
            newsResponseDtoList.add(mapper.mapServiceNewsResponseDto(serviceDto));
        }
        return newsResponseDtoList;
    }
}
