package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;

import java.lang.reflect.InvocationTargetException;

public class CreateNewsCommand implements Command {
    private final CommandType commandType = CommandType.CREATE_NEWS;
    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;
    private final NewsRequestDto createRequest;

    public CreateNewsCommand(
            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController,
            NewsRequestDto createRequest) {
        this.newsController = newsController;
        this.createRequest = createRequest;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getNewsMethod(newsController, commandType)
                    .invoke(newsController, createRequest));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
