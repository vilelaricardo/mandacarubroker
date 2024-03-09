package com.mandacarubroker.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface do Repositório de Usuários para a entidade "Users".
 *
 * @author Equipe de desenvolvimento mandacaru
 * @since 1.0.0
 */

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameOrEmail(String username, String email);
}