package com.jersey.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReadWriteDatabaseConfiguration {

  @NotEmpty
  private String jdbcUrl;

  @NotNull
  private Integer maximumPoolSize;

  @NotNull
  private Integer connectionTimeout;

  @NotNull
  private Integer minimumIdle;

  @NotNull
  private Integer maxLifetime;

  @NotNull
  private Integer validationQueryTimeout;

  @NotNull
  private Boolean readOnly;

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public Integer getMaximumPoolSize() {
    return maximumPoolSize;
  }

  public void setMaximumPoolSize(Integer maximumPoolSize) {
    this.maximumPoolSize = maximumPoolSize;
  }

  public Integer getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(Integer connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public Integer getMinimumIdle() {
    return minimumIdle;
  }

  public void setMinimumIdle(Integer minimumIdle) {
    this.minimumIdle = minimumIdle;
  }

  public Integer getMaxLifetime() {
    return maxLifetime;
  }

  public void setMaxLifetime(Integer maxLifetime) {
    this.maxLifetime = maxLifetime;
  }

  public Integer getValidationQueryTimeout() {
    return validationQueryTimeout;
  }

  public void setValidationQueryTimeout(Integer validationQueryTimeout) {
    this.validationQueryTimeout = validationQueryTimeout;
  }

  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }
}
