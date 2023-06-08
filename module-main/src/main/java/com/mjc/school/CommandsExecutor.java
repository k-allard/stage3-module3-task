package com.mjc.school;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.impl.NewsController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exceptions.ValidatorException;

import static com.mjc.school.service.exceptions.ExceptionsCodes.VALIDATE_INT_VALUE;


public class CommandsExecutor {

    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController = new NewsController();

    private final TerminalCommandsReader commandsReader = new TerminalCommandsReader();

    public void executeCommand(Command command) {

        if (command == Command.EXIT)
            System.exit(0);

        System.out.print("Operation: ");
        System.out.println(command.description);
        switch (command) {
            case GET_ALL -> {
                for (NewsResponseDto news : newsController.readAll()) {
                    System.out.println(news);
                }
            }
            case GET_BY_ID -> System.out.println(
                    newsController.readById(
                            requestNewsId()
                    ));
            case CREATE -> System.out.println(
                    newsController.create(
                            new NewsRequestDto(
                                    null,
                                    requestNewsTitle(),
                                    requestNewsContent(),
                                    requestAuthorId()
                            )));
            case UPDATE -> System.out.println(
                    newsController.update(
                            new NewsRequestDto(
                                    requestNewsId(),
                                    requestNewsTitle(),
                                    requestNewsContent(),
                                    requestAuthorId()
                            )));
            case REMOVE_BY_ID -> System.out.println(
                    newsController.deleteById(
                            requestNewsId()
                    ));
        }
    }

    private long requestNewsId() {
        try {
            return Long.parseLong(commandsReader.requestResponseByPrompt("Enter news id:"));
        } catch (NumberFormatException e) {
            throw new ValidatorException(
                    String.format(VALIDATE_INT_VALUE.getMessage(), "News id"));
        }
    }

    private long requestAuthorId() {
        try {
            return Long.parseLong(commandsReader.requestResponseByPrompt("Enter author id:"));
        } catch (NumberFormatException e) {
            throw new ValidatorException(
                    String.format(VALIDATE_INT_VALUE.getMessage(), "Author id"));
        }
    }

    private String requestNewsContent() {
        return commandsReader.requestResponseByPrompt("Enter news content:");
    }

    private String requestNewsTitle() {
        return commandsReader.requestResponseByPrompt("Enter news title:");
    }
}
