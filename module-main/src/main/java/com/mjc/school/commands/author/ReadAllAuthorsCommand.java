package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

public class ReadAllAuthorsCommand implements Command {

    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;

    public ReadAllAuthorsCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController) {
        this.authorController = authorController;
    }

    @Override
    public void execute() {
        for (AuthorResponseDto author : authorController.readAll()) {
            System.out.println(author);
        }
    }
}
