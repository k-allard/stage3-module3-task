package com.mjc.school;

public enum Command {
    GET_ALL(1, "Get all news."),
    GET_BY_ID(2, "Get news by id."),
    CREATE(3, "Create news."),
    UPDATE(4, "Update news."),
    REMOVE_BY_ID(5, "Remove news by id."),
    EXIT(0, "Exit.");

    public final int code;

    public final String description;

    Command(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
