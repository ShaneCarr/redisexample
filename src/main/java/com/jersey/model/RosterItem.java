package com.jersey.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RosterItem(
        @JsonProperty("id")
        Long id,

        @JsonProperty("remote_id")
        String remoteId,

        @JsonProperty("emailaddress")
        String emailAddress,

        @JsonProperty("roster_id")
        Long rosterId
) {
  public RosterItem {
    Objects.requireNonNull(remoteId, "remoteId must not be null");
    Objects.requireNonNull(emailAddress, "emailAddress must not be null");
  }

  public Long id() {
    return id;
  }

  public String remoteId() {
    return remoteId;
  }

  public String emailAddress() {
    return emailAddress;
  }

  public Long rosterId() {
    return rosterId;
  }

  @Override
  public String toString() {
    return "RosterItem{" +
            "id=" + id +
            ", remoteId='" + remoteId + '\'' +
            ", emailAddress='" + emailAddress + '\'' +
            ", rosterId=" + rosterId +
            '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    RosterItem that = (RosterItem) obj;
    return Objects.equals(id, that.id) &&
            Objects.equals(remoteId, that.remoteId) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(rosterId, that.rosterId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, remoteId, emailAddress, rosterId);
  }
}
