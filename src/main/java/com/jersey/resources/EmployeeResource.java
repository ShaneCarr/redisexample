package com.jersey.resources;

import com.jersey.api.Audience.Audience;
import com.jersey.cosmos.CreateAudience;
import com.jersey.model.Employee;
import com.jersey.repository.EmployeeRepository;
import io.dropwizard.auth.Auth;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {
  private Validator validator;
  private EmployeeRepository repository;
  public EmployeeResource(Validator validator, EmployeeRepository repository) {
    this.validator = validator;
    this.repository = repository;
  }
  @GET
  public Response getEmployees() {
    return Response.ok(repository.getEmployees()).build();
  }
  @GET
  @Path("/{id}")
  public Response getEmployeeById(@PathParam("id") Integer id) {
    Employee employee = repository.getEmployee(id);
    if (employee != null) {
      return Response.ok(employee).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  @POST
  public Response createEmployee(Employee employee) throws URISyntaxException {
    Employee e = repository.getEmployee(employee.id());
    if (e != null) {
      repository.updateEmployee(employee.id(), employee);
      return Response.created(new URI("/employees/" + employee.id()))
              .build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  @PUT
  @Path("/{id}")
  public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {
    Employee e = repository.getEmployee(employee.id());
    if (e != null) {
      Employee updatedEmployee = new Employee.Builder()
              .id(id)
              .firstName(employee.firstName())
              .lastName(employee.lastName())
              .email(employee.email())
              .build();

      repository.updateEmployee(id, updatedEmployee);
      return Response.ok(employee).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  @DELETE
  @Path("/{id}")
  public Response removeEmployeeById(@PathParam("id") Integer id) {
    Employee employee = repository.getEmployee(id);
    if (employee != null) {
      repository.removeEmployee(id);
      return Response.ok().build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{networkId}/{userId}/audiences/ofGroup/{groupId}")
  public Audience addAudience(
         // @Auth final UserPrincipal userPrincipal,
          @PathParam("networkId") long networkId,
          @PathParam("userId") long userId,
          CreateAudience createAudience) {
    com.jersey.cosmos.Audience cosmosAudience = com.jersey.cosmos.Audience.fromCreateAudience(createAudience);
    return repository.addAudience(cosmosAudience);
  }

}