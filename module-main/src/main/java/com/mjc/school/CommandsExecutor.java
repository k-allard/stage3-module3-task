package com.mjc.school;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.commands.author.CreateAuthorCommand;
import com.mjc.school.commands.author.DeleteAuthorCommand;
import com.mjc.school.commands.author.ReadAllAuthorsCommand;
import com.mjc.school.commands.author.ReadAuthorByIdCommand;
import com.mjc.school.commands.author.ReadAuthorByNewsIdCommand;
import com.mjc.school.commands.author.UpdateAuthorCommand;
import com.mjc.school.commands.news.CreateNewsCommand;
import com.mjc.school.commands.news.DeleteNewsCommand;
import com.mjc.school.commands.news.ReadAllNewsCommand;
import com.mjc.school.commands.news.ReadNewsByIdCommand;
import com.mjc.school.commands.news.ReadNewsByParamsCommand;
import com.mjc.school.commands.news.UpdateNewsCommand;
import com.mjc.school.commands.tag.CreateTagCommand;
import com.mjc.school.commands.tag.DeleteTagCommand;
import com.mjc.school.commands.tag.ReadAllTagsCommand;
import com.mjc.school.commands.tag.ReadTagByIdCommand;
import com.mjc.school.commands.tag.ReadTagByNewsIdCommand;
import com.mjc.school.commands.tag.UpdateTagCommand;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtendedController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;
import com.mjc.school.controller.dto.TagDto;
import com.mjc.school.exceptions.IdFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CommandsExecutor {

    private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;
    private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
    private final BaseController<TagDto, TagDto, Long> tagController;
    private final ExtendedController extendedController;
    private final TerminalCommandsReader commandsReader = new TerminalCommandsReader();

    public CommandsExecutor(@Qualifier("newsController")
                            BaseController<NewsRequestDto, NewsResponseDto, Long> newsController,
                            @Qualifier("authorController")
                            BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
                            @Qualifier("tagController")
                            BaseController<TagDto, TagDto, Long> tagController,
                            ExtendedController extendedController) {
        this.newsController = newsController;
        this.authorController = authorController;
        this.tagController = tagController;
        this.extendedController = extendedController;
    }

    public void executeCommand(CommandType commandType) throws Throwable {
        if (commandType == CommandType.EXIT)
            System.exit(0);
        System.out.print("Operation: ");
        System.out.println(commandType.description);
        Command command = getCommandClassImpl(commandType);
        command.execute();
    }

    private Command getCommandClassImpl(CommandType commandType) {
        return switch (commandType) {
            case GET_ALL_NEWS -> new ReadAllNewsCommand(newsController);
            case GET_NEWS_BY_ID -> new ReadNewsByIdCommand(newsController, requestNewsId(true));
            case CREATE_NEWS -> new CreateNewsCommand(newsController,
                    new NewsRequestDto(
                            null,
                            requestNewsTitle(true),
                            requestNewsContent(true),
                            requestAuthorId(false),
                            requestTagsIds(false)));
            case UPDATE_NEWS -> new UpdateNewsCommand(newsController,
                    new NewsRequestDto(
                            requestNewsId(true),
                            requestNewsTitle(false),
                            requestNewsContent(false),
                            requestAuthorId(false),
                            requestTagsIds(false)));
            case REMOVE_NEWS_BY_ID -> new DeleteNewsCommand(newsController, requestNewsId(true));
            case GET_ALL_AUTHORS -> new ReadAllAuthorsCommand(authorController);
            case GET_AUTHOR_BY_ID -> new ReadAuthorByIdCommand(authorController, requestAuthorId(true));
            case CREATE_AUTHOR -> new CreateAuthorCommand(authorController,
                    new AuthorRequestDto(
                            null,
                            requestAuthorName(true)));
            case UPDATE_AUTHOR -> new UpdateAuthorCommand(authorController,
                    new AuthorRequestDto(
                            requestAuthorId(true),
                            requestAuthorName(true)));
            case REMOVE_AUTHOR_BY_ID -> new DeleteAuthorCommand(authorController, requestAuthorId(true));
            case GET_ALL_TAGS -> new ReadAllTagsCommand(tagController);
            case GET_TAG_BY_ID -> new ReadTagByIdCommand(tagController, requestTagId(true));
            case CREATE_TAG -> new CreateTagCommand(tagController, new TagDto(null, requestTagName(true)));
            case UPDATE_TAG -> new UpdateTagCommand(tagController, new TagDto(requestTagId(true), requestTagName(true)));
            case REMOVE_TAG_BY_ID -> new DeleteTagCommand(tagController, requestTagId(true));
            case GET_AUTHOR_BY_NEWS_ID -> new ReadAuthorByNewsIdCommand(extendedController, requestNewsId(true));
            case GET_TAGS_BY_NEWS_ID -> new ReadTagByNewsIdCommand(extendedController, requestNewsId(true));
            case GET_NEWS_BY_PARAMS -> new ReadNewsByParamsCommand(extendedController,
                    requestTagsIds(false),
                    requestTagName(false),
                    requestAuthorName(false),
                    requestNewsTitle(false),
                    requestNewsContent(false));
            default -> throw new IllegalStateException("Unexpected commandType: " + commandType);
        };
    }

    private Long requestNewsId(boolean required) {
        commandsReader.printIfRequired(required);
        String response = commandsReader.requestResponseByPrompt("Enter news id:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty newsId - correct scenario, should be handled");
            return null;
        }
        try {
            return Long.parseLong(response);
        } catch (NumberFormatException e) {
            throw new IdFormatException(
                    "ERROR_CODE: 05 ERROR_MESSAGE: News id should be number");
        }
    }

    private Long requestAuthorId(boolean required) {
        commandsReader.printIfRequired(required);
        String response = commandsReader.requestResponseByPrompt("Enter author id:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty authorId - correct scenario, should be handled");
            return null;
        }
        try {
            return Long.parseLong(response);
        } catch (NumberFormatException e) {
            throw new IdFormatException(
                    "ERROR_CODE: 05 ERROR_MESSAGE: Author id should be number");
        }
    }

    private String requestNewsContent(boolean required) {
        commandsReader.printIfRequired(required);
        String response = commandsReader.requestResponseByPrompt("Enter news content:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty newsContent - correct scenario, should be handled");
            return null;
        }
        return response;
    }

    private String requestNewsTitle(boolean required) {
        commandsReader.printIfRequired(required);
        String response = commandsReader.requestResponseByPrompt("Enter news title:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty newsTitle - correct scenario, should be handled");
            return null;
        }
        return response;
    }

    private String requestAuthorName(boolean required) {
        commandsReader.printIfRequired(required);
        String response =  commandsReader.requestResponseByPrompt("Enter author name:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty newsTitle - correct scenario, should be handled");
            return null;
        }
        return response;
    }

    private List<Long> requestTagsIds(boolean required) {
        commandsReader.printIfRequired(required);
        String response = commandsReader.requestResponseByPrompt("Enter tags ids separated by space:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty tagsIds - correct scenario, should be handled");
            return null;
        }
        String[] ids = response.split(" ");
        List<Long> result = new ArrayList<>();
        for (String id : ids) {
            try {
                result.add(Long.parseLong(id));
            } catch (NumberFormatException e) {
                throw new IdFormatException(
                        "ERROR_CODE: 05 ERROR_MESSAGE: Tag id should be number");
            }
        }
        return result;
    }

    private String requestTagName(boolean required) {
        commandsReader.printIfRequired(required);
        String response = commandsReader.requestResponseByPrompt("Enter tag name:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty tagName - correct scenario, should be handled");
            return null;
        }
        return response;
    }

    private Long requestTagId(boolean required) {
        commandsReader.printIfRequired(required);
        String response = commandsReader.requestResponseByPrompt("Enter tag id:");
        if (!required && (response.length() == 0 || response.equals("-"))) {
            log.debug("Received empty tagId - correct scenario, should be handled");
            return null;
        }
        try {
            return Long.parseLong(response);
        } catch (NumberFormatException e) {
            throw new IdFormatException(
                    "ERROR_CODE: 05 ERROR_MESSAGE: Tag id should be number");
        }
    }
}
