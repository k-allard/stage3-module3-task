package com.mjc.school;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class MainApplication {

    private static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found.";
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);

    public static void main(String[] args) {
        TerminalCommandsReader commandsReader = new TerminalCommandsReader();

        CommandsExecutor commandsExecutor = context.getBean(CommandsExecutor.class);

        while (true) {
            commandsReader.getCommand().ifPresentOrElse(cmd ->
                    {
                        try {
                            commandsExecutor.executeCommand(cmd);
                        } catch (Throwable e) {
                            System.out.println(e.getMessage());
                            System.out.println("--------------------------");
                            e.printStackTrace(System.out);
                        }
                    },
                    () -> System.out.println(COMMAND_NOT_FOUND_MESSAGE));
        }

    }
}
