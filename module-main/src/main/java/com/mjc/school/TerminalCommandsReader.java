package com.mjc.school;

import com.mjc.school.commands.CommandType;

import java.util.Optional;
import java.util.Scanner;

public class TerminalCommandsReader {

    private static final String PROMPT_ENTER_NUMBER_OF_OPERATION =
            "_____________________________\n" +
            "Enter the number of operation:";
    private static final String NOT_REQUIRED_INFO =
            "Next is NOT required. You can leave it blank or put '-'";
    private final Scanner sc = new Scanner(System.in);

    public Optional<CommandType> getCommand() {
        printCommandPrompt();
        if (sc.hasNextLine()) {
            try {
                int commandCode = Integer.parseInt(sc.nextLine());
                CommandType commandType = getCommandByCode(commandCode);
                return Optional.ofNullable(commandType);
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public void printIfRequired(boolean required) {
        if (!required) {
            System.out.println(NOT_REQUIRED_INFO);
        }
    }

    public String requestResponseByPrompt(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    private CommandType getCommandByCode(int commandCode) {
        for (CommandType cmd : CommandType.values()) {
            if (cmd.code == commandCode)
                return cmd;
        }
        return null;
    }

    private void printCommandPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append(PROMPT_ENTER_NUMBER_OF_OPERATION).append('\n');
        for (CommandType cmd : CommandType.values()) {
            sb
                    .append(cmd.code)
                    .append(" - ")
                    .append(cmd.description)
                    .append('\n');
        }
        System.out.print(sb);
    }
}
