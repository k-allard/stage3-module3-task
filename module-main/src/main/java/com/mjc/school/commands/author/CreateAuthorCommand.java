package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

import java.lang.reflect.InvocationTargetException;

public class CreateAuthorCommand implements Command {
    private final CommandType commandType = CommandType.CREATE_AUTHOR;
    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
    private final AuthorRequestDto createRequest;

    public CreateAuthorCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
            AuthorRequestDto createRequest) {
        this.authorController = authorController;
        this.createRequest = createRequest;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getAuthorMethod(authorController, commandType)
                    .invoke(authorController, createRequest));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
