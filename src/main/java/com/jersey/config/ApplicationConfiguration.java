package com.jersey.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.core.Configuration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ApplicationConfiguration extends Configuration {

  @Valid
  @NotNull
  private HttpClientConfiguration httpClient = new HttpClientConfiguration();

  @JsonProperty("httpClient")
  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }

  @JsonProperty("httpClient")
  public void setHttpClientConfiguration(HttpClientConfiguration httpClient) {
    this.httpClient = httpClient;
  }

  @Valid
  @NotNull
  private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

  @JsonProperty("jerseyClient")
  public JerseyClientConfiguration getJerseyClientConfiguration() {
    return jerseyClient;
  }

  @JsonProperty("jerseyClient")
  public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
    this.jerseyClient = jerseyClient;
  }
//
//  @Valid
//  @NotNull
//  @JsonProperty("server")
//  private ServerFactory serverFactory = new DefaultServerFactory();

  @Valid
  @NotNull
  @JsonProperty("readWriteDatabase")
  private ReadWriteDatabaseConfiguration readWriteDatabaseConfiguration;

//  @Valid
//  @NotNull
//  @JsonProperty("logging")
//  private LoggingConfiguration loggingConfiguration;
//
//  public ServerFactory getServerFactory() {
//    return serverFactory;
//  }

//  public void setServerFactory(ServerFactory serverFactory) {
//    this.serverFactory = serverFactory;
//  }

  public ReadWriteDatabaseConfiguration getReadWriteDatabaseConfiguration() {
    return readWriteDatabaseConfiguration;
  }

  public void setReadWriteDatabaseConfiguration(
          ReadWriteDatabaseConfiguration readWriteDatabaseConfiguration) {
    this.readWriteDatabaseConfiguration = readWriteDatabaseConfiguration;
  }

  @Valid
  @NotNull
  @JsonProperty("readOnlyDatabase")
  private ReadOnlyDatabaseConfiguration readOnlyDatabaseConfiguration;

  @JsonProperty("readOnlyDatabase")
  public ReadOnlyDatabaseConfiguration getReadOnlyDatabaseConfiguration() {
    return readOnlyDatabaseConfiguration;
  }

  @JsonProperty("readOnlyDatabase")
  public void setReadOnlyDatabaseConfiguration(ReadOnlyDatabaseConfiguration readOnlyDatabaseConfiguration) {
    this.readOnlyDatabaseConfiguration = readOnlyDatabaseConfiguration;
  }


//  public LoggingConfiguration getLoggingConfiguration() {
//    return loggingConfiguration;
//  }
//
//  public void setLoggingConfiguration(LoggingConfiguration loggingConfiguration) {
//    this.loggingConfiguration = loggingConfiguration;
//  }

  @Valid
  @NotNull
  @JsonProperty("cosmosdb")
  private CosmosDbConfiguration cosmosConfiguration;

  @JsonProperty("cosmosdb")
  public CosmosDbConfiguration getCosmosConfiguration() {
    return cosmosConfiguration;
  }

  @JsonProperty("cosmosdb")
  public void setCosmosConfiguration(CosmosDbConfiguration cosmosConfiguration) {
    this.cosmosConfiguration = cosmosConfiguration;
  }

}
