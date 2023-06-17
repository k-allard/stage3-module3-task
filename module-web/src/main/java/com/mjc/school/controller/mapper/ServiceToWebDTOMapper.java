package com.mjc.school.controller.mapper;

import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;
import com.mjc.school.service.dto.ServiceAuthorRequestDto;
import com.mjc.school.service.dto.ServiceAuthorResponseDto;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import org.modelmapper.ModelMapper;

public class ServiceToWebDTOMapper {

    private final ModelMapper mapper = new ModelMapper();

    public NewsResponseDto mapServiceNewsResponseDto(
            ServiceNewsResponseDto serviceNewsResponseDto) {
        return mapper.map(serviceNewsResponseDto, NewsResponseDto.class);
    }

    public ServiceNewsRequestDto mapNewsRequestDto(NewsRequestDto newsRequestDto) {
        return mapper.map(newsRequestDto, ServiceNewsRequestDto.class);
    }

    public AuthorResponseDto mapServiceAuthorResponseDto(
            ServiceAuthorResponseDto serviceAuthorResponseDto) {
        return mapper.map(serviceAuthorResponseDto, AuthorResponseDto.class);
    }

    public ServiceAuthorRequestDto mapAuthorRequestDto(
            AuthorRequestDto authorRequestDto) {
        return mapper.map(authorRequestDto, ServiceAuthorRequestDto.class);
    }
}
