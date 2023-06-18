package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

import java.lang.reflect.InvocationTargetException;

public class UpdateAuthorCommand implements Command {
    private final CommandType commandType = CommandType.UPDATE_AUTHOR;
    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
    private final AuthorRequestDto updateRequest;

    public UpdateAuthorCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
            AuthorRequestDto updateRequest) {
        this.authorController = authorController;
        this.updateRequest = updateRequest;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getAuthorMethod(authorController, commandType)
                    .invoke(authorController, updateRequest));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
