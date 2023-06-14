package com.mjc.school;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class MainApplication {

    private static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found.";
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);

    public static void main(String[] args) {
        printBeans(); //TODO remove
        TerminalCommandsReader commandsReader = new TerminalCommandsReader();

        CommandsExecutor commandsExecutor = context.getBean(CommandsExecutor.class);

        while (true) {
            commandsReader.getCommand().ifPresentOrElse(cmd ->
                    {
                        try {
                            //TODO Use Command pattern to call operations
                            commandsExecutor.executeCommand(cmd);
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    },
                    () -> System.out.println(COMMAND_NOT_FOUND_MESSAGE));
        }

    }

    private static void printBeans() {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(MainApplication.class)) {
            String[] singletonNames = context.getDefaultListableBeanFactory().getSingletonNames();
            for (String singleton : singletonNames) {
                System.out.println(singleton);
            }
        }
    }
}
