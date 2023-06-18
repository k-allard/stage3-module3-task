package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;

import java.lang.reflect.InvocationTargetException;

public class ReadAllNewsCommand implements Command {
    private final CommandType commandType = CommandType.GET_ALL_NEWS;
    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;

    public ReadAllNewsCommand(
            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController) {
        this.newsController = newsController;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getNewsMethod(newsController, commandType)
                    .invoke(newsController));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
