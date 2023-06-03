package com.jersey.cosmos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateAudience(String id, String networkId, String name,
                             List<GroupInfo> groupInfoList, long transitiveMemberCount,
                             Instant groupPropertiesUpdatedAt, String createdBy) {
  @JsonCreator
  public CreateAudience(
          @JsonProperty("id") String id,
          @JsonProperty("networkId") String networkId,
          @JsonProperty("name") String name,
          @JsonProperty("groupInfoList") List<GroupInfo> groupInfoList,
          @JsonProperty("transitiveMemberCount") long transitiveMemberCount,
          @JsonProperty("groupPropertiesUpdatedAt") Instant groupPropertiesUpdatedAt,
          @JsonProperty("createdBy") String createdBy) {
    this.id = id;
    this.networkId = networkId;
    this.name = name;
    this.groupInfoList = groupInfoList;
    this.transitiveMemberCount = transitiveMemberCount;
    this.groupPropertiesUpdatedAt = groupPropertiesUpdatedAt;
    this.createdBy = createdBy;
  }

  @JsonProperty("id")
  public String id() {
    return id;
  }

  @JsonProperty("networkId")
  public String networkId() {
    return networkId;
  }

  @JsonProperty("name")
  public String name() {
    return name;
  }

  @JsonProperty("groupInfoList")
  public List<GroupInfo> groupInfoList() {
    return groupInfoList;
  }

  @JsonProperty("transitiveMemberCount")
  public long transitiveMemberCount() {
    return transitiveMemberCount;
  }

  @JsonProperty("groupPropertiesUpdatedAt")
  public Instant groupPropertiesUpdatedAt() {
    return groupPropertiesUpdatedAt;
  }

  @JsonProperty("createdBy")
  public String createdBy() {
    return createdBy;
  }
}
