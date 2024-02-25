package com.mandacarubroker.domain.authuser;

/**
 * The Auth User role enum.
 **/
public enum AuthUserRole {
  ADMIN("admin"),
  USER("user");

  private String role;

  AuthUserRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
