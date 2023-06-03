package com.jersey.cosmos;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
//import com.azure.cosmos.CosmosContainerProperties;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosContainerResponse;

public class CosmosContainerFactory {
  private final CosmosClient cosmosClient;
  private final String databaseName;
  private final String containerName;

  public CosmosContainerFactory(CosmosClient cosmosClient, String databaseName, String containerName) {
    this.cosmosClient = cosmosClient;
    this.databaseName = databaseName;
    this.containerName = containerName;
  }

  public CosmosContainer createContainer() {
    CosmosDatabase database = cosmosClient.getDatabase(databaseName);
   // CosmosContainerProperties containerProperties = new CosmosContainerProperties(containerName, "/partitionKeyPath");
   // return database.createContainerIfNotExists(containerProperties).getContainer();
    CosmosContainerResponse response = database.createContainerIfNotExists("containerProperties", "createContainerIfNotExists");
    //CosmosContainerResponse createCampaignThreadMetadataContainerResponse = database.createContainerIfNotExists("", "/networkId");
    var container = database.getContainer(response.getProperties().getId());
    return container;
  }
}
