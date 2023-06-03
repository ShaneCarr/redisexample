package com.jersey.cosmos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GroupInfo(String groupOfficeId, boolean serviceCreatedGroup) {
  @JsonCreator
  public GroupInfo(
          @JsonProperty("groupOfficeId") String groupOfficeId,
          @JsonProperty("serviceCreatedGroup") boolean serviceCreatedGroup) {
    this.groupOfficeId = groupOfficeId;
    this.serviceCreatedGroup = serviceCreatedGroup;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupInfo groupInfo = (GroupInfo) o;
    return serviceCreatedGroup == groupInfo.serviceCreatedGroup &&
            Objects.equals(groupOfficeId, groupInfo.groupOfficeId);
  }

  @Override
  public String toString() {
    return "GroupInfo{" +
            "groupOfficeId='" + groupOfficeId + '\'' +
            ", serviceCreatedGroup=" + serviceCreatedGroup +
            '}';
  }
}
