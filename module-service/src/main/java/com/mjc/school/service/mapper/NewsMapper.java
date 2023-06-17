package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import org.modelmapper.ModelMapper;

public class NewsMapper {
    private final ModelMapper mapper = new ModelMapper();

    public ServiceNewsResponseDto mapModelToResponseDto(NewsModel newsModel) {
        return mapper.map(newsModel, ServiceNewsResponseDto.class);
    }

    public NewsModel mapResponseDtoToModel(ServiceNewsResponseDto news) {
        return mapper.map(news, NewsModel.class);
    }

    public NewsModel mapRequestDtoToModel(ServiceNewsRequestDto news) {
        return mapper.map(news, NewsModel.class);
    }

}
