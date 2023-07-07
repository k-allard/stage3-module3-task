package com.mjc.school.commands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.CommandHandler;
import com.mjc.school.controller.ExtendedController;
import com.mjc.school.controller.dto.AuthorRequestDto;
import com.mjc.school.controller.dto.AuthorResponseDto;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;
import com.mjc.school.controller.dto.TagDto;

import java.lang.reflect.Method;

public interface Command {
    void execute() throws Throwable;

    default Method getAuthorMethod(
            BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller,
            CommandType commandType
    ) {
        for (final Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CommandHandler.class)) {
                if (method.getAnnotation(CommandHandler.class).code() == commandType.code) {
                    return method;
                }
            }
        }
        throw new IllegalStateException("Unexpected commandType: " + commandType);
    }

    default Method getNewsMethod(
            BaseController<NewsRequestDto, NewsResponseDto, Long> controller,
            CommandType commandType
    ) {
        for (final Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CommandHandler.class)) {
                if (method.getAnnotation(CommandHandler.class).code() == commandType.code) {
                    return method;
                }
            }
        }
        throw new IllegalStateException("Unexpected commandType: " + commandType);
    }

    default Method getTagMethod(
            BaseController<TagDto, TagDto, Long> controller,
            CommandType commandType
    ) {
        for (final Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CommandHandler.class)) {
                if (method.getAnnotation(CommandHandler.class).code() == commandType.code) {
                    return method;
                }
            }
        }
        throw new IllegalStateException("Unexpected commandType: " + commandType);
    }

    default Method getExtendedMethod(
            ExtendedController controller,
            CommandType commandType
    ) {
        for (final Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CommandHandler.class)) {
                if (method.getAnnotation(CommandHandler.class).code() == commandType.code) {
                    return method;
                }
            }
        }
        throw new IllegalStateException("Unexpected commandType: " + commandType);
    }
}
