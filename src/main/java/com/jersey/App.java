package com.jersey;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.ThroughputProperties;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosClientBuilder;
//import com.azure.cosmos.CosmosContainerProperties;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.ThroughputProperties;
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
import liquibase.pro.packaged.ll;

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
    // CosmosClient cosmosClient = new CosmosClientBuilder()
    //         .endpoint(c.getCosmosConfiguration().getEndpoint())
    //         .key(c.getCosmosConfiguration().getKey())
    //         .buildClient();

    // CosmosContainerFactory containerFactory = new CosmosContainerFactory(
    //         cosmosClient,
    //         c.getCosmosConfiguration().getDatabaseName(),
    //         c.getCosmosConfiguration().getContainerName()
    // );

    
    // Create the CosmosClient using the configuration properties
CosmosClient cosmosClient = new CosmosClientBuilder()
        .endpoint(c.getCosmosConfiguration().getEndpoint()) // Use HTTP endpoint
        .key(c.getCosmosConfiguration().getKey())
        .buildClient();

CosmosContainerFactory containerFactory = new CosmosContainerFactory(
        cosmosClient,
        c.getCosmosConfiguration().getDatabaseName(),
        c.getCosmosConfiguration().getContainerName()
);
        ThroughputProperties throughputProperties = ThroughputProperties.createManualThroughput(400);
cosmosClient.createDatabaseIfNotExists(c.getCosmosConfiguration().getDatabaseName(), throughputProperties);

    CosmosDatabase database = cosmosClient.getDatabase(c.getCosmosConfiguration().getDatabaseName());
    // Check if the database exists
    if (database == null) {
        LOGGER.error("Database '{}' does not exist.");
        
    }

            CosmosContainerProperties containerProperties = new CosmosContainerProperties( c.getCosmosConfiguration().getDatabaseName(), "/partitionKey");
        

    CosmosContainerResponse response = database.createContainerIfNotExists(containerProperties, throughputProperties);

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


/*
 



# Change to the directory where you want to save the certificate
cd /path/to/your/directory

# Download the certificate with curl
curl -k -o emulator.pem https://localhost:8081/_explorer/emulator.pem

# Convert .pem to .crt
openssl x509 -outform der -in emulator.pem -out emulator.crt

# Add certificate to the trusted store (you will be asked for your password)
sudo cp emulator.crt /usr/local/share/ca-certificates/
sudo update-ca-certificates

# Clean up the downloaded certificate file
rm emulator.pem emulator.crt



---------------------------------------------------------- you need to do this for windows

@echo off

REM Change to the directory where you want to save the certificate
cd C:\Users\shcarr\Downloads

REM Download the certificate with curl
curl -k -o emulator.pem https://localhost:8081/_explorer/emulator.pem

REM Import the certificate
certutil -addstore -f "ROOT" emulator.pem

REM Clean up the downloaded certificate file
del emulator.pem


java in windows *linux will be different

@echo off

:: Variables
set CERT_URL=https://localhost:8081/_explorer/emulator.pem
set CERT_FILE=emulator.pem
set ALIAS=CosmosEmulator
set JAVA_HOME=C:\Progra~1\Microsoft\jdk-17.0.2.8-hotspot
set CACERTS_PATH=%JAVA_HOME%\lib\security\cacerts
set STOREPASS=changeit

:: Download the certificate using curl (ignoring SSL validation)
curl -k -o %CERT_FILE% %CERT_URL%

:: Import the certificate to the Java's cacerts truststore
"%JAVA_HOME%\bin\keytool.exe" -import -trustcacerts -keystore "%CACERTS_PATH%" -storepass %STOREPASS% -noprompt -alias %ALIAS% -file %CERT_FILE%

:: Delete the certificate file
del %CERT_FILE%

echo.
echo Done!


https://localhost:8081/_explorer/emulator.pem

 */