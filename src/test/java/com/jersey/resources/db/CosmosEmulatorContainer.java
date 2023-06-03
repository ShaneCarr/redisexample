package com.jersey.resources.db;

import org.testcontainers.containers.GenericContainer;

public class CosmosEmulatorContainer extends GenericContainer<CosmosEmulatorContainer> {

  private static final int EMULATOR_PORT = 8081;

  private final String cosmosEndpoint;
  private final String cosmosKey;
//  private final String cosmosDatabaseName;
//  private final String cosmosContainerName;

  public CosmosEmulatorContainer() {
    super("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator");
    addExposedPort(EMULATOR_PORT);

    // i need to override  the keys iwth these
    this.cosmosEndpoint = this.getEmulatorEndpoint();
    this.cosmosKey = this.getEmulatorKey();

//    The setCommand() method in the Testcontainers library allows you to specify
//    the command that will be executed when the container starts.
//    It sets the command that is run inside the container's runtime environment.
    setCommand("--enable-multiple-write-locations");
  }

  public String getEmulatorEndpoint() {
    return String.format("https://%s:%d", getHost(), getMappedPort(EMULATOR_PORT));
  }

  public String getEmulatorKey() {
    return cosmosKey;
  }

//  public String getEmulatorDatabaseName() {
//    return cosmosDatabaseName;
//  }
//
//  public String getEmulatorContainerName() {
//    return cosmosContainerName;
//  }

  @Override
  protected void configure() {
    withExposedPorts(EMULATOR_PORT);
  }
}
