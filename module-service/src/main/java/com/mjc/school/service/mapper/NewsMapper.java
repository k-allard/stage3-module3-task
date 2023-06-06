package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.modelmapper.ModelMapper;

public class NewsMapper {
    private final ModelMapper mapper = new ModelMapper();

    public NewsResponseDto mapModelToResponseDto(NewsModel newsModel) {
        return mapper.map(newsModel, NewsResponseDto.class);
    }

    public NewsModel mapResponseDtoToModel(NewsResponseDto news) {
        return mapper.map(news, NewsModel.class);
    }

    public NewsModel mapRequestDtoToModel(NewsRequestDto news) {
        return mapper.map(news, NewsModel.class);
    }

}
