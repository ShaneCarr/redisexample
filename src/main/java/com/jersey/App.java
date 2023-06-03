package com.jersey;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosClientBuilder;
//import com.azure.cosmos.CosmosContainerProperties;
import com.azure.cosmos.CosmosDatabase;

import com.jersey.auth.AppAuthorizer;
import com.jersey.auth.AppBasicAuthenticator;
import com.jersey.auth.User;
import com.jersey.config.ApplicationConfiguration;
import com.jersey.config.ApplicationHealthCheck;
import com.jersey.cosmos.CosmosContainerFactory;
import com.jersey.repository.EmployeeRepository;
import com.jersey.resources.APIController;
import com.jersey.resources.EmployeeResource;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.ws.rs.client.Client;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<ApplicationConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
//    bootstrap.addBundle(new LiquibaseBundle<ApplicationConfiguration>() {
//      @Override
//      public DataSourceFactory getDataSourceFactory(ApplicationConfiguration configuration) {
//        return configuration.getDataSourceFactory();
//      }
//
//      @Override
//      public LiquibaseFactory getLiquibaseFactory(ApplicationConfiguration configuration) {
//        return configuration.getLiquibaseFactory();
//      }
//    });
  }

  @Override
  public void run(ApplicationConfiguration c, Environment e) throws Exception {

    LOGGER.info("Registering Jersey Client");

    final Client client = new JerseyClientBuilder(e)
            .using(c.getJerseyClientConfiguration())
            .build(getName());
    e.jersey().register(new APIController(client));


    LOGGER.info("Registering cosmos");
    // Create the CosmosClient using the configuration properties
    CosmosClient cosmosClient = new CosmosClientBuilder()
            .endpoint(c.getCosmosConfiguration().getEndpoint())
            .key(c.getCosmosConfiguration().getKey())
            .buildClient();

    CosmosContainerFactory containerFactory = new CosmosContainerFactory(
            cosmosClient,
            c.getCosmosConfiguration().getDatabaseName(),
            c.getCosmosConfiguration().getContainerName()
    );

    LOGGER.info("Registering REST resources");
    e.jersey().register(new EmployeeResource(e.getValidator(), new EmployeeRepository(containerFactory.createContainer())));

    LOGGER.info("Registering Application Health Check");
    e.healthChecks().register("application", new ApplicationHealthCheck(client));

    /*LOGGER.info("Registering Apache HttpClient");
    final HttpClient httpClient = new HttpClientBuilder(e)
        .using(c.getHttpClientConfiguration())
        .build(getName());
    e.jersey().register(new APIController(httpClient));*/

    //****** Dropwizard security - custom classes ***********/
    e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new AppBasicAuthenticator())
            .setAuthorizer(new AppAuthorizer())
            .setRealm("BASIC-AUTH-REALM")
            .buildAuthFilter()));
    e.jersey().register(RolesAllowedDynamicFeature.class);
    e.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

  }

  public static void main(String[] args) throws Exception {
    new App().run(args);
  }
}

