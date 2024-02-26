package com.mandacarubroker.service;

import com.mandacarubroker.domain.authuser.AuthUser;
import com.mandacarubroker.domain.authuser.RegisterDataTransferObject;
import com.mandacarubroker.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The Auth User service method.
 **/
@Service
public class AuthorizationService implements UserDetailsService {

  private final AuthUserRepository authUserRepository;

  public AuthorizationService(AuthUserRepository authUserRepository) {
    this.authUserRepository = authUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return authUserRepository.findByUsername(username);
  }

  /**
   * The method create a new authorization user.
   *
   *  @param data The auth user data
   *
   **/
  public void create(RegisterDataTransferObject data) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    AuthUser newUser = new AuthUser(data.username(), encryptedPassword, data.role());

    this.authUserRepository.save(newUser);
  }

  public void delete(String id) {
    authUserRepository.deleteById(id);
  }
}
