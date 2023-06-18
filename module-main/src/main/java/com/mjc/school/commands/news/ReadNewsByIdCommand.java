package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;

import java.lang.reflect.InvocationTargetException;

public class ReadNewsByIdCommand implements Command {
    private final CommandType commandType = CommandType.GET_NEWS_BY_ID;
    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;
    private final Long id;

    public ReadNewsByIdCommand(
            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController,
            Long id) {
        this.newsController = newsController;
        this.id = id;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getNewsMethod(newsController, commandType)
                    .invoke(newsController, id));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
