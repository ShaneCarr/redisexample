package com.jersey.cosmos;

import com.azure.cosmos.models.PartitionKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
//import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
//@SuppressFBWarnings(value = {"EI_EXPOSE_REP"})
public class Audience {
  private final String id;
  private final String networkId;
  private final String name;
  private final List<GroupInfo> groupInfoList;
  private final long transitiveMemberCount;
  private final Instant groupPropertiesUpdatedAt;
  private final String createdBy;
  private final Instant createdAt;
  private final String updatedBy;
  private final Instant updatedAt;

  @JsonCreator
  public Audience(
          @JsonProperty("id") String id,
          @JsonProperty("networkId") String networkId,
          @JsonProperty("name") String name,
          @JsonProperty("groupInfoList") List<GroupInfo> groupInfoList,
          @JsonProperty("transitiveMemberCount") long transitiveMemberCount,
          @JsonProperty("groupPropertiesUpdatedAt") Instant groupPropertiesUpdatedAt,
          @JsonProperty("createdBy") String createdBy,
          @JsonProperty("createdAt") Instant createdAt,
          @JsonProperty("updatedBy") String updatedBy,
          @JsonProperty("updatedAt") Instant updatedAt) {
    this.id = id;
    this.networkId = networkId;
    this.name = name;
    this.groupInfoList = groupInfoList;
    this.transitiveMemberCount = transitiveMemberCount;
    this.groupPropertiesUpdatedAt = groupPropertiesUpdatedAt;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.updatedBy = updatedBy;
    this.updatedAt = updatedAt;
  }

  public String getId() {
    return id;
  }

  public String getNetworkId() {
    return networkId;
  }

  public String getName() {
    return name;
  }

  public List<GroupInfo> getGroupInfoList() {
    return groupInfoList;
  }

  public long getTransitiveMemberCount() {
    return transitiveMemberCount;
  }

  public Instant getGroupPropertiesUpdatedAt() {
    return groupPropertiesUpdatedAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  @JsonIgnore
  public static Audience getAudienceWithUpdatedTransitiveMemberCount(Audience audience, long newTransitiveMemberCount) {
    return new Audience(audience.id, audience.networkId, audience.name, List.copyOf(audience.groupInfoList),
            newTransitiveMemberCount, Instant.now(), audience.createdBy, audience.createdAt, audience.updatedBy, audience.updatedAt);
  }

  @JsonIgnore
  public PartitionKey getPartitionKey() {
    return new PartitionKey(id);
  }

  @JsonIgnore
  public static PartitionKey getPartitionKey(String leaderYammerId) {
    return new PartitionKey(leaderYammerId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Audience audience = (Audience) o;
    return transitiveMemberCount == audience.transitiveMemberCount &&
            Objects.equals(id, audience.id) &&
            Objects.equals(networkId, audience.networkId) &&
            Objects.equals(name, audience.name) &&
            Objects.equals(groupInfoList, audience.groupInfoList) &&
            Objects.equals(groupPropertiesUpdatedAt, audience.groupPropertiesUpdatedAt) &&
            Objects.equals(createdBy, audience.createdBy) &&
            Objects.equals(createdAt, audience.createdAt) &&
            Objects.equals(updatedBy, audience.updatedBy) &&
            Objects.equals(updatedAt, audience.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, networkId, name, groupInfoList, transitiveMemberCount, groupPropertiesUpdatedAt, createdBy, createdAt, updatedBy, updatedAt);
  }

  @Override
  public String toString() {
    return "Audience{" +
            "id='" + id + '\'' +
            ", networkId='" + networkId + '\'' +
            ", name='" + name + '\'' +
            ", groupInfoList=" + groupInfoList +
            ", transitiveMemberCount=" + transitiveMemberCount +
            ", groupPropertiesUpdatedAt=" + groupPropertiesUpdatedAt +
            ", createdBy='" + createdBy + '\'' +
            ", createdAt=" + createdAt +
            ", updatedBy='" + updatedBy + '\'' +
            ", updatedAt=" + updatedAt +
            '}';
  }

  public static Audience fromCreateAudience(com.jersey.cosmos.CreateAudience createAudience) {
    return new Audience(
            createAudience.id(),
            createAudience.networkId(),
            createAudience.name(),
            createAudience.groupInfoList(),
            createAudience.transitiveMemberCount(),
            createAudience.groupPropertiesUpdatedAt(),
            "createdBy",
            Instant.now(),
            "updatedBy",
            Instant.now()
    );
  }
}
