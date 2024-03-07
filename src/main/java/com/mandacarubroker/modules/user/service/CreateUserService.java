package com.mandacarubroker.modules.user.service;

import com.mandacarubroker.exception.UserFoundException;
import com.mandacarubroker.modules.user.User;
import com.mandacarubroker.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    @Autowired
    private UserRepository userRepository;

    public User execute(User user) {
        // Variável para verificar se usuário existe
        this.userRepository
                .findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .ifPresent((useraux) -> {
                    throw new UserFoundException();
                });
        return this.userRepository.save(user);
    }
}