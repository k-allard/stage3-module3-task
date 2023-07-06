package com.mjc.school.commands.tag;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.TagDto;

import java.lang.reflect.InvocationTargetException;

public class UpdateTagCommand implements Command {
    private final CommandType commandType = CommandType.UPDATE_TAG;
    private final BaseController<TagDto, TagDto, Long> tagController;
    private final TagDto updateRequest;

    public UpdateTagCommand(
            BaseController<TagDto, TagDto, Long> tagController,
            TagDto updateRequest) {
        this.tagController = tagController;
        this.updateRequest = updateRequest;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getTagMethod(tagController, commandType)
                    .invoke(tagController, updateRequest));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
