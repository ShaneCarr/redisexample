package com.jersey.resources;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import java.util.Objects;

import com.jersey.config.ApplicationConfiguration;
import com.jersey.model.Employee;
import jakarta.ws.rs.core.Response;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import com.jersey.resources.db.AppTestExtension;
import jakarta.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
public class EmployeeResourceTest {


    private static final AppTestExtension appTestExtension = new AppTestExtension(
            Objects.requireNonNull(EmployeeResourceTest.class.getClassLoader().getResource("test.yml")).getPath()
    );

    static public DropwizardAppExtension<ApplicationConfiguration> app = appTestExtension.getApp();

    @BeforeAll
    public static void setUp() throws Exception {
        app.before();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        app.after();
    }

    @Test
    public void createEmployee() {
        Employee employee = new Employee(1, "John", "Doe", "john.doe@example.com");
        UriBuilder uri = UriBuilder.fromUri(format("http://localhost:%d" + "/employees", app.getLocalPort()));
        Response response = app.client().target(uri)
                .request()
                .post(Entity.entity(employee, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("/employees/1", response.getLocation().getPath());
    }


    @Test
    public void getGroupMembers() {
        Employee employee = new Employee(1, "John", "Doe", "john.doe@example.com");
        UriBuilder uri = UriBuilder.fromUri(format("http://localhost:%d" + "/employees", app.getLocalPort()));
        Response response = app.client().target(uri)
                .request()
                .post(Entity.entity(employee, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("/employees/1", response.getLocation().getPath());
    }

}