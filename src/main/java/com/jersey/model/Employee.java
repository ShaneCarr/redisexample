package com.jersey.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.util.Objects;


public record Employee(
        @NotNull
        Integer id,

        @NotBlank
        @Size(min = 2, max = 255)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 255)
        String lastName,

        @Pattern(regexp = ".+@.+\\.[a-z]+")
        String email
) {
  public Employee {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(firstName, "firstName must not be null");
    Objects.requireNonNull(lastName, "lastName must not be null");
    Objects.requireNonNull(email, "email must not be null");
  }

  public static class Builder {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Employee build() {
      return new Employee(id, firstName, lastName, email);
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}
