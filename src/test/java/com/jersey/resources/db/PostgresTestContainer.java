package com.jersey.resources.db;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.jdbi.v3.core.Handle;
import org.testcontainers.containers.PostgreSQLContainer;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PostgresTestContainer {
    private static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:9.6.9-alpine");

    private final String changeLogFile;
    private final Map<String, String> params = new HashMap<>();

    private Jdbi dbi;
    private String jdbcUrl;

    private static final Logger logger = LoggerFactory.getLogger(PostgresTestContainer.class);

    private static final String[] TABLES = {
            "table_1", "table_1"
    };

    static {
      //  TimeZone.setDefault(DateTimeZone.UTC.toTimeZone());
    }

    public static PostgresTestContainer create() {
        return new PostgresTestContainer("migrations.xml")
                .withParam("stamp.id", "1");
    }

    private PostgresTestContainer(String changeLogFile) {
        this.changeLogFile = changeLogFile;
    }

    public PostgresTestContainer withParam(String name, String value) {
        params.put(name, value);
        return this;
    }

    public void start() {

        logger.info("enter start.....");
        try {
            db.start();
            logger.info("started db.");
            String baseUrl = db.getJdbcUrl();
            jdbcUrl = String.format("%s%cuser=%s&password=%s",
                    baseUrl, baseUrl.contains("?") ? '&' : '?', db.getUsername(), db.getPassword());
            dbi = Jdbi.create(jdbcUrl);

            dbi.useHandle(handle -> {
                try (Liquibase liquibase = new Liquibase(changeLogFile,
                        new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()))) {
                    params.forEach((name, value) -> liquibase.getChangeLogParameters().set(name, value));
                    liquibase.update(new Contexts());
                }
            });

        } catch (Exception e)
        {
            logger.error("Error start: ", e);
        }

        logger.info("imgration complete .");
    }

    public void stop() {
       db.stop();
    }

    public String jdbcUrl() {
        return jdbcUrl;
    }

    public void truncateTables() {
        try (Handle handle = dbi.open()) {
            handle.execute("TRUNCATE TABLE " + String.join(", ", TABLES) + " RESTART IDENTITY");
        }
    }
}