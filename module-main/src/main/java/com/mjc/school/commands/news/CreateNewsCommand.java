package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;

public class CreateNewsCommand implements Command {

    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;

    private final NewsRequestDto newsRequestDto;

    public CreateNewsCommand(
            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController,
            NewsRequestDto newsRequestDto) {
        this.newsController = newsController;
        this.newsRequestDto = newsRequestDto;
    }

    @Override
    public void execute() {
        System.out.println(
                newsController.create(newsRequestDto));
    }
}
