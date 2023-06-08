package com.mjc.school;

public class MainApplication {

    private static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found.";

    public static void main(String[] args) {
        TerminalCommandsReader commandsReader = new TerminalCommandsReader();

        CommandsExecutor commandsExecutor = new CommandsExecutor();

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
}
