package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import org.modelmapper.ModelMapper;

public class NewsMapper {
    private final ModelMapper mapper = new ModelMapper();

    public ServiceNewsResponseDto mapModelToResponseDto(News newsModel) {
        return mapper.map(newsModel, ServiceNewsResponseDto.class);
    }

    public News mapResponseDtoToModel(ServiceNewsResponseDto news) {
        return mapper.map(news, News.class);
    }

    public News mapRequestDtoToModel(ServiceNewsRequestDto news) {
        return mapper.map(news, News.class);
    }

}
