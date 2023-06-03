package com.jersey.api.Audience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Audience(
        @JsonProperty("audienceId") String id,
        @JsonProperty("audienceName") String displayName,
        @JsonProperty("isDefault") boolean isDefaultAudience,
        @JsonProperty("memberCount") long transitiveMemberCount
) {
  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String id;
    private String displayName;
    private boolean isDefaultAudience;
    private long transitiveMemberCount;

    private Builder() {
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder displayName(String displayName) {
      this.displayName = displayName;
      return this;
    }

    public Builder isDefaultAudience(boolean isDefaultAudience) {
      this.isDefaultAudience = isDefaultAudience;
      return this;
    }

    public Builder transitiveMemberCount(long transitiveMemberCount) {
      this.transitiveMemberCount = transitiveMemberCount;
      return this;
    }

    public Audience build() {
      return new Audience(id, displayName, isDefaultAudience, transitiveMemberCount);
    }
  }
}
