package com.mjc.school.commands.author;

import com.mjc.school.commands.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;

public class UpdateAuthorCommand implements Command {

    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
    private final AuthorRequestDto updateRequest;

    public UpdateAuthorCommand(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
            AuthorRequestDto updateRequest) {
        this.authorController = authorController;
        this.updateRequest = updateRequest;
    }

    @Override
    public void execute() {
        System.out.println(
                authorController.update(updateRequest));
    }
}
