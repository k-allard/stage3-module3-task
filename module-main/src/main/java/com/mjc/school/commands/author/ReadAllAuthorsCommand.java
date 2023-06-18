package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

import java.lang.reflect.InvocationTargetException;

public class ReadAllAuthorsCommand implements Command {
    private final CommandType commandType = CommandType.GET_ALL_AUTHORS;
    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;

    public ReadAllAuthorsCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController) {
        this.authorController = authorController;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getAuthorMethod(authorController, commandType)
                    .invoke(authorController));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
