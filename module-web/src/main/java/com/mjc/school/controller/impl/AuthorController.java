package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;
import com.mjc.school.controller.mapper.ServiceToWebDTOMapper;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.ServiceAuthorRequestDto;
import com.mjc.school.service.dto.ServiceAuthorResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorRequestDto, AuthorResponseDto, Long> {
    private final BaseService<ServiceAuthorRequestDto, ServiceAuthorResponseDto, Long> authorService;
    private final ServiceToWebDTOMapper mapper = new ServiceToWebDTOMapper();

    public AuthorController(
            @Qualifier("authorService")
            BaseService<ServiceAuthorRequestDto, ServiceAuthorResponseDto, Long> authorService
    ) {
        this.authorService = authorService;
    }

    public List<AuthorResponseDto> readAll() {
        List<AuthorResponseDto> authorResponseDtoList = new ArrayList<>();
        for (ServiceAuthorResponseDto responseDto : authorService.readAll()) {
            authorResponseDtoList.add(mapper.mapServiceAuthorResponseDto(responseDto));
        }
        return authorResponseDtoList;
    }

    public AuthorResponseDto readById(Long newsId) {
        return mapper.mapServiceAuthorResponseDto(authorService.readById(newsId));
    }

    public AuthorResponseDto create(AuthorRequestDto dtoRequest) {
        return mapper.mapServiceAuthorResponseDto(
                authorService.create(mapper.mapAuthorRequestDto(dtoRequest)));
    }

    public AuthorResponseDto update(AuthorRequestDto dtoRequest) {
        return mapper.mapServiceAuthorResponseDto(
                authorService.update(mapper.mapAuthorRequestDto(dtoRequest)));
    }

    public boolean deleteById(Long newsId) {
        return authorService.deleteById(newsId);
    }
}
