package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;

public class ReadNewsByIdCommand implements Command {

    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;
    private final Long id;

    public ReadNewsByIdCommand(
            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController,
            Long id) {
        this.newsController = newsController;
        this.id = id;
    }

    @Override
    public void execute() {
        System.out.println(
                newsController.readById(id));
    }
}
