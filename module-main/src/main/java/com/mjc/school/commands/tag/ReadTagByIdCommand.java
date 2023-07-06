package com.mjc.school.commands.tag;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.TagDto;

import java.lang.reflect.InvocationTargetException;

public class ReadTagByIdCommand implements Command {
    private final CommandType commandType = CommandType.GET_TAG_BY_ID;
    private final BaseController<TagDto, TagDto, Long> tagController;
    private final Long id;

    public ReadTagByIdCommand(
            BaseController<TagDto, TagDto, Long> tagController,
            Long id) {
        this.tagController = tagController;
        this.id = id;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getTagMethod(tagController, commandType)
                    .invoke(tagController, id));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
