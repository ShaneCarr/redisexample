package com.jersey.resources.db;

import com.jersey.App;
import com.jersey.config.ApplicationConfiguration;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import org.junit.jupiter.api.extension.*;
import org.jdbi.v3.core.Jdbi;

/*
test extension
 */
public class AppTestExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    public AppTestExtension(String configPath) {
        this.configPath = configPath;
    }
    private String configPath;

    private final PostgresTestContainer db = PostgresTestContainer.create();

    final private DropwizardAppExtension<ApplicationConfiguration>  app = new DropwizardAppExtension<>(App.class,
            this.configPath
          //  ,ConfigOverride.config("readOnlyDatabase.jdbcUrl", db.jdbcUrl()),
          //  ConfigOverride.config("readWriteDatabase.jdbcUrl", db.jdbcUrl())
    );

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        db.start();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        db.stop();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        // Perform any setup before each test
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        // Perform any cleanup after each test
    }

//    public DBI dbi() {
//        return db.dbi();
//    }

    public DropwizardAppExtension<ApplicationConfiguration> getApp() {
        return app;
    }
}
