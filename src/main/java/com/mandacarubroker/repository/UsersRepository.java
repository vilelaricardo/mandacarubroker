package com.mandacarubroker.repository;

import com.mandacarubroker.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
  Users findUsersByUsername(String username);
}
