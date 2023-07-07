package com.mjc.school.commands.tag;

import com.mjc.school.commands.Command;
import com.mjc.school.commands.CommandType;
import com.mjc.school.controller.ExtendedController;

import java.lang.reflect.InvocationTargetException;

public class ReadTagByNewsIdCommand implements Command {
    private final CommandType commandType = CommandType.GET_TAGS_BY_NEWS_ID;
    private final ExtendedController extendedController;
    private final Long id;

    public ReadTagByNewsIdCommand(
            ExtendedController extendedController,
            Long id) {
        this.extendedController = extendedController;
        this.id = id;
    }

    @Override
    public void execute() throws Throwable {
        try {
            System.out.println(getExtendedMethod(extendedController, commandType)
                    .invoke(extendedController, id));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
