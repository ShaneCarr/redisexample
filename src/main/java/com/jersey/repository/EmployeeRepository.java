package com.jersey.repository;

import com.jersey.api.Audience.Audience;
import com.jersey.cosmos.CreateAudience;
import com.jersey.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.time.Duration;
import java.util.function.Supplier;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.implementation.ConflictException;
import com.azure.cosmos.implementation.NotFoundException;
import com.azure.cosmos.models.CosmosBulkOperationResponse;
import com.azure.cosmos.models.CosmosBulkOperations;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosPatchOperations;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.models.SqlParameter;

public class EmployeeRepository {

  private CosmosContainer container;

  public EmployeeRepository(CosmosContainer container) {

    this.container = container;
  }
  public static HashMap<Integer, Employee> employees = new HashMap<>();

  static {
    employees.put(1, new Employee(1, "Lokesh", "Gupta", "India"));
    employees.put(2, new Employee(2, "John", "Gruber", "USA"));
    employees.put(3, new Employee(3, "Melcum", "Marshal", "AUS"));
  }

  public static List<Employee> getEmployees() {
    return new ArrayList<Employee>(employees.values());
  }

  public static Employee getEmployee(Integer id) {
    return employees.get(id);
  }

  public static void updateEmployee(Integer id, Employee employee) {
    employees.put(id, employee);
  }

  public static void removeEmployee(Integer id) {
    employees.remove(id);
  }

  public static <T> T retry(int maxRetries, Duration delayBetweenRetries, boolean withLinearBackoff,
                            Supplier<T> function) {
    int retryCounter = 0;
    while (true) {
      try {
        return function.get();
      } catch (Exception ex) {
       // logger.error("retry exception: ", ex);
        if (retryCounter >= maxRetries) {
          throw ex;
        }
        retryCounter++;
      }
    }
  }

  public Audience addAudience(///@NonNull UserPrincipal userPrincipal,
                                     com.jersey.cosmos.Audience audience) {
    //ensureAutorizationForAudienceMgmt(userPrincipal, networkId, leaderId);
    return createAudience(audience);
  }

  public Audience createAudience(com.jersey.cosmos.Audience audience) {
    var item = container.createItem(audience, audience.getPartitionKey(), new CosmosItemRequestOptions());

    // stub to compile
    return Audience.builder()
            .id("audience123")
            .displayName("My Audience")
            .isDefaultAudience(true)
            .transitiveMemberCount(1000L)
            .build();

////    try {
////      return cosmosMetrics
////              .observeCreate(() -> container.createItem(audience, audience.getPartitionKey(), new CosmosItemRequestOptions()))
////              .getStatusCode();
////    } catch (ConflictException e) {
////      log.info("Audience{} already exists for leader {}", audience.getId(), audience.getLeaderYammerId(), e);
////      return e.getStatusCode();
////    }
//    return 0;
  }


  private static void waitForNextRetry(int numberOfTries, Duration delayBetweenRetries,
                                       boolean withLinearBackoff) throws InterruptedException {
    long duration = Math.min(delayBetweenRetries.toMillis(), Long.MAX_VALUE);
    if (withLinearBackoff) {
      duration = getLinearDurationToWait(numberOfTries, delayBetweenRetries);
    }

    Thread.sleep(duration);
  }

  private static long getLinearDurationToWait(int numberOfTries, Duration delayBetweenRetries) {
    double result = numberOfTries * delayBetweenRetries.toMillis();
    return (long) Math.min(result, Long.MAX_VALUE);
  }
}


