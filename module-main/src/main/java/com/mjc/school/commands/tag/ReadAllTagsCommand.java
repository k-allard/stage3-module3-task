package com.mjc.school.commands.tag;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.TagDto;

import java.lang.reflect.InvocationTargetException;

public class ReadAllTagsCommand implements Command {
    private final CommandType commandType = CommandType.GET_ALL_TAGS;
    private final BaseController<TagDto, TagDto, Long> tagController;

    public ReadAllTagsCommand(
            BaseController<TagDto, TagDto, Long> tagController) {
        this.tagController = tagController;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getTagMethod(tagController, commandType)
                    .invoke(tagController));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
