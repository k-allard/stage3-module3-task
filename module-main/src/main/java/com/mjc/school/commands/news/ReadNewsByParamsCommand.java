package com.mjc.school.commands.news;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.ExtendedController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ReadNewsByParamsCommand implements Command {
    private final CommandType commandType = CommandType.GET_NEWS_BY_PARAMS;

    private final ExtendedController extendedController;
    private final List<Long> tagsIds;
    private final String tagName;
    private final String authorName;
    private final String title;
    private final String content;

    public ReadNewsByParamsCommand(ExtendedController extendedController,
                                   List<Long> tagsIds,
                                   String tagName,
                                   String authorName,
                                   String title,
                                   String content) {
        this.extendedController = extendedController;
        this.tagsIds = tagsIds;
        this.tagName = tagName;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getExtendedMethod(extendedController, commandType)
                    .invoke(extendedController, tagsIds, tagName, authorName, title, content));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
