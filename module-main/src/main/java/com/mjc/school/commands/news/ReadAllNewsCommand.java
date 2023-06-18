package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;

public class ReadAllNewsCommand implements Command {

    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;

    public ReadAllNewsCommand(
            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController) {
        this.newsController = newsController;
    }

    @Override
    public void execute() {
        for (NewsResponseDto news : newsController.readAll()) {
            System.out.println(news);
        }
    }
}
