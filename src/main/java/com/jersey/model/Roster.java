package com.jersey.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record Roster(
        @JsonProperty("network_id")
        @JsonAlias("network_id")
        long networkId,

        @JsonProperty("roster_name")
        @JsonAlias("roster_name")
        String rosterName,

        @JsonProperty("origin_id")
        @JsonAlias("origin_id")
        String originId,

        @JsonProperty("origin")
        @JsonAlias("origin")
        String originName,

        @JsonProperty("id")
        @JsonAlias("id")
        Long id
) {
  public Roster {
    Objects.requireNonNull(networkId, "networkId must not be null");
    Objects.requireNonNull(rosterName, "rosterName must not be null");
  }

  public long networkId() {
    return networkId;
  }

  public String rosterName() {
    return rosterName;
  }

  public String originId() {
    return originId;
  }

  public String originName() {
    return originName;
  }

  public Long id() {
    return id;
  }

  @Override
  public String toString() {
    return "Roster{" +
            "networkId=" + networkId +
            ", rosterName='" + rosterName + '\'' +
            ", originId='" + originId + '\'' +
            ", originName='" + originName + '\'' +
            ", id=" + id +
            '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Roster roster = (Roster) obj;
    return networkId == roster.networkId &&
            Objects.equals(rosterName, roster.rosterName) &&
            Objects.equals(originId, roster.originId) &&
            Objects.equals(originName, roster.originName) &&
            Objects.equals(id, roster.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(networkId, rosterName, originId, originName, id);
  }
}
