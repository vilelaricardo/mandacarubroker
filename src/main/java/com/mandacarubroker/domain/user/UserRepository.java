package com.mandacarubroker.domain.user;

import com.mandacarubroker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface do Repositório de Usuários para a entidade "Users".
 *
 * @author Equipe de desenvolvimento mandacaru
 * @since 1.0.0
 */

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}