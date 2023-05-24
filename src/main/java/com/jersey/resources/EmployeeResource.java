package com.jersey.resources;

import com.jersey.model.Employee;
import com.jersey.repository.EmployeeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
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
    Employee e = repository.getEmployee(employee.getId());
    if (e != null) {
      repository.updateEmployee(employee.getId(), employee);
      return Response.created(new URI("/employees/" + employee.getId()))
              .build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  @PUT
  @Path("/{id}")
  public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {
    Employee e = repository.getEmployee(employee.getId());
    if (e != null) {
      employee.setId(id);
      repository.updateEmployee(id, employee);
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
}