package com.mandacarubroker.repository;

import com.mandacarubroker.domain.authuser.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Auth User Repository interface.
 **/
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
  AuthUser findByUsername(String username);
}
