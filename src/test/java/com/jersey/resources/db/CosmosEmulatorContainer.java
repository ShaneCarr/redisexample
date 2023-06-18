// package com.jersey.resources.db;

// import org.testcontainers.containers.GenericContainer;

// public class CosmosEmulatorContainer extends GenericContainer<CosmosEmulatorContainer> {

//   private static final int EMULATOR_PORT = 8081;

// private static final String EMULATOR_KEY = "eW91ci1jb3Ntb3NkYi1rZXk="; // Replace with your desired emulator key

//   public CosmosEmulatorContainer() {
//     super("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator");
//     addExposedPort(EMULATOR_PORT);

//   }

//   public String getEmulatorEndpoint() {
//     return String.format("http://%s:%d", getHost(), getMappedPort(EMULATOR_PORT));
//   }

//   public String getEmulatorKey() {
//     return EMULATOR_KEY;
//   }


//   @Override
//   protected void configure() {
//     withExposedPorts(EMULATOR_PORT);
//   }
// }
package com.jersey.resources.db;

import org.testcontainers.containers.GenericContainer;

public class CosmosEmulatorContainer extends GenericContainer<CosmosEmulatorContainer> {

  private static final int EMULATOR_PORT = 8081;

  private static final String EMULATOR_KEY = "eW91ci1jb3Ntb3NkYi1rZXk="; // Replace with your desired emulator key

  public CosmosEmulatorContainer() {
    super("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator");
    addExposedPort(EMULATOR_PORT);
    withEnv("AZURE_COSMOS_EMULATOR_ACCEPT_TERMS", "yes");
   // withCommand("--no-ssl"); // Disable SSL for the emulator
  }

  public String getEmulatorEndpoint() {
    //return String.format("https://%s:%d", getHost(), getMappedPort(EMULATOR_PORT));
      return String.format("https://%s:%d", getHost(), EMULATOR_PORT);
  }

  public String getEmulatorKey() {
    return EMULATOR_KEY;
  }

  @Override
  protected void configure() {
    withExposedPorts(EMULATOR_PORT);
  }
}
