package com.jersey.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class CosmosDbConfiguration {

  @NotNull
  @JsonProperty
  private String endpoint;

  @NotNull
  @JsonProperty
  private String key;

  @NotNull
  @JsonProperty
  private String databaseName;

  @NotNull
  @JsonProperty
  private String containerName;

  public String getEndpoint() {
    return endpoint;
  }

  public String getKey() {
    return key;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public String getContainerName() {
    return containerName;
  }
}
