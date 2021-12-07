package com.wissen.training.loginsignupspringboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {
  private Long userId;
  private String providerUserId;

  @NotBlank private String displayName;

  @NotBlank private String email;
  private SocialProvider socialProvider;
  @NotBlank private String password;
  private String matchingPassword;

  public SignUpRequest(
      String providerUserId,
      String displayName,
      String email,
      SocialProvider socialProvider,
      String password) {
    this.displayName = displayName;
    this.providerUserId = providerUserId;
    this.email = email;
    this.socialProvider = socialProvider;
    this.password = password;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String providerUserId;
    private String displayName;
    private String email;
    private String password;
    private SocialProvider socialProvider;

    public Builder addProviderUserId(final String providerUserId) {
      this.providerUserId = providerUserId;
      return this;
    }

    public Builder addDisplayName(final String displayName) {
      this.displayName = displayName;
      return this;
    }

    public Builder addEmail(final String email) {
      this.email = email;
      return this;
    }

    public Builder addPassword(final String password) {
      this.password = password;
      return this;
    }

    public Builder addSocialProvider(final SocialProvider socialProvider) {
      this.socialProvider = socialProvider;
      return this;
    }

    public SignUpRequest build() {
      return new SignUpRequest(providerUserId, displayName, email, socialProvider, password);
    }
  }
}
