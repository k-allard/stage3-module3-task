package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

public class CreateAuthorCommand implements Command {

    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
    private final AuthorRequestDto createRequest;

    public CreateAuthorCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
            AuthorRequestDto createRequest) {
        this.authorController = authorController;
        this.createRequest = createRequest;
    }

    @Override
    public void execute() {
        System.out.println(
                authorController.create(createRequest));
    }
}
