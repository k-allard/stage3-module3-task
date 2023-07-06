package com.mjc.school.commands.tag;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.TagDto;

import java.lang.reflect.InvocationTargetException;

public class CreateTagCommand implements Command {
    private final CommandType commandType = CommandType.CREATE_TAG;
    private final BaseController<TagDto, TagDto, Long> tagController;
    private final TagDto createRequest;

    public CreateTagCommand(
            BaseController<TagDto, TagDto, Long> tagController,
            TagDto createRequest) {
        this.tagController = tagController;
        this.createRequest = createRequest;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getTagMethod(tagController, commandType)
                    .invoke(tagController, createRequest));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
