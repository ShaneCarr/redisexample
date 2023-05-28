package com.jersey.resources.db;

import com.jersey.App;
import com.jersey.config.ApplicationConfiguration;

//import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import org.junit.jupiter.api.extension.*;
import org.jdbi.v3.core.Jdbi;

import java.util.function.Supplier;

/*
test extension
 */
public class AppTestExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private final String configPath;

    public AppTestExtension(String configPath) {
        db = PostgresTestContainer.create();
        db.start();
        Supplier<String> readOnlyJdbcUrlSupplier = db::jdbcUrl;

        app = new DropwizardAppExtension<>(App.class,
                configPath,
                ConfigOverride.config("readOnlyDatabase.jdbcUrl", readOnlyJdbcUrlSupplier),
                ConfigOverride.config("readWriteDatabase.jdbcUrl", readOnlyJdbcUrlSupplier)
        );
        this.configPath = configPath;
    }

    private final DropwizardAppExtension<ApplicationConfiguration>  app;

   private final PostgresTestContainer db;

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

    public DropwizardAppExtension<ApplicationConfiguration> getApp() {
        return app;
    }
}
