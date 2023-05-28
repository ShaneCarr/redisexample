package com.jersey.repository;

import com.jersey.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.time.Duration;
import java.util.function.Supplier;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmployeeRepository {

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
//        try {
//          //waitForNextRetry(retryCounter, delayBetweenRetries, withLinearBackoff);
//        } catch (InterruptedException ignored) {
//          // logger.error("RetryInterruptedException: ", ignored);
//        }
      }
    }
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


