package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

public class DeleteAuthorCommand implements Command {

    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
    private final Long id;

    public DeleteAuthorCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
            Long id) {
        this.authorController = authorController;
        this.id = id;
    }

    @Override
    public void execute() {
        System.out.println(
                authorController.deleteById(id));
    }
}
