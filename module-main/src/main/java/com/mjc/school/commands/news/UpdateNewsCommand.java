package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;

public class UpdateNewsCommand implements Command {

    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;
    private final NewsRequestDto updateRequest;

    public UpdateNewsCommand(
            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController,
            NewsRequestDto updateRequest) {
        this.newsController = newsController;
        this.updateRequest = updateRequest;
    }

    @Override
    public void execute() {
        System.out.println(
                newsController.update(updateRequest));
    }
}
