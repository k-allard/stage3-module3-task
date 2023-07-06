package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.CommandHandler;
import com.mjc.school.controller.dto.TagDto;
import com.mjc.school.controller.mapper.ServiceToWebDTOMapper;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.ServiceTagDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TagController implements BaseController<TagDto, TagDto, Long> {
    private final BaseService<ServiceTagDto, ServiceTagDto, Long> tagService;
    private final ServiceToWebDTOMapper mapper = new ServiceToWebDTOMapper();

    public TagController(
            @Qualifier("tagService")
            BaseService<ServiceTagDto, ServiceTagDto, Long> tagService
    ) {
        this.tagService = tagService;
    }

    @CommandHandler(code = 11)
    public List<TagDto> readAll() {
        List<TagDto> authorResponseDtoList = new ArrayList<>();
        for (ServiceTagDto serviceTagDto : tagService.readAll()) {
            authorResponseDtoList.add(mapper.mapServiceTagDto(serviceTagDto));
        }
        return authorResponseDtoList;
    }

    @CommandHandler(code = 12)
    public TagDto readById(Long id) {
        return mapper.mapServiceTagDto(tagService.readById(id));
    }

    @CommandHandler(code = 13)
    public TagDto create(TagDto tagDto) {
        return mapper.mapServiceTagDto(
                tagService.create(mapper.mapTagToServiceDto(tagDto)));
    }

    @CommandHandler(code = 14)
    public TagDto update(TagDto dtoRequest) {
        return mapper.mapServiceTagDto(
                tagService.update(mapper.mapTagToServiceDto(dtoRequest)));
    }

    @CommandHandler(code = 15)
    public boolean deleteById(Long id) {
        return tagService.deleteById(id);
    }
}
