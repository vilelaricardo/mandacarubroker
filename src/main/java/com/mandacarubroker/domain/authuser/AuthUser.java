package com.mandacarubroker.domain.authuser;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Auth User class.
 * */
@Table(name = "auth_users")
@Entity(name = "auth_users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AuthUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String login;
  @Getter
  private String password;

  @Enumerated(EnumType.STRING)
  private AuthUserRole role;

  /**
   * The method set the variables.
   *
   * @param login user login
   * @param password password
   * @param role role
   *
   **/
  public AuthUser(String login, String password, AuthUserRole role) {
    this.login = login;
    this.password = password;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == AuthUserRole.ADMIN) {
      return List.of(
          new SimpleGrantedAuthority(AuthUserRole.ADMIN.getRole()),
          new SimpleGrantedAuthority(AuthUserRole.USER.getRole())
      );
    } else {
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
