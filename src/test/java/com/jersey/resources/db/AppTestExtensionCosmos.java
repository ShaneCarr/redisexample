package com.jersey.resources.db;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.jersey.config.ApplicationConfiguration;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AppTestExtensionCosmos implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

  private final String configPath;
  private DropwizardAppExtension<ApplicationConfiguration> app;

 // private CosmosEmulatorContainer cosmosContainer;

  public AppTestExtensionCosmos(String configPath) {
    this.configPath = configPath;
   // cosmosContainer = new CosmosEmulatorContainer();
   // cosmosContainer.start();

    app = new DropwizardAppExtension<>(com.jersey.App.class,
            configPath//,
        //    ConfigOverride.config("cosmosdb.endpoint", cosmosContainer.getEmulatorEndpoint()),
       //     ConfigOverride.config("cosmosdb.key", cosmosContainer.getEmulatorKey())
    );

       // app = new DropwizardAppExtension<>(com.jersey.App.class,
        //    configPath//,
      //      ConfigOverride.config("cosmosdb.endpoint", cosmosContainer.getEmulatorEndpoint()),
        //    ConfigOverride.config("cosmosdb.key", cosmosContainer.getEmulatorKey())
   // );

  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    app.before();
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    //cosmosContainer.stop();
    app.afterAll(context);
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
