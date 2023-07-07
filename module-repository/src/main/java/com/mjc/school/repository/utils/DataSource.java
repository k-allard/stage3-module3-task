package com.mjc.school.repository.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSource {
    public static final String DB_URL = "jdbc:postgresql://localhost:5430/demoDB";
    public static final String DB_USERNAME = "usr";
    public static final String DB_PASSWORD = "pwd";

    public DataSource() {
        new MigrationsExecutorFlyway(DB_URL, DB_USERNAME, DB_PASSWORD).executeMigrations();
    }
}
