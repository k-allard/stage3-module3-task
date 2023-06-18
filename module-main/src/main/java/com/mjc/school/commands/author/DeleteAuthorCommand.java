package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

import java.lang.reflect.InvocationTargetException;

public class DeleteAuthorCommand implements Command {
    private final CommandType commandType = CommandType.REMOVE_AUTHOR_BY_ID;
    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
    private final Long id;

    public DeleteAuthorCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
            Long id) {
        this.authorController = authorController;
        this.id = id;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getAuthorMethod(authorController, commandType)
                    .invoke(authorController, id));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
