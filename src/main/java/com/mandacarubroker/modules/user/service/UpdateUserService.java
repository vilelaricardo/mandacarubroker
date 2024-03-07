package com.mandacarubroker.modules.user.service;

import com.mandacarubroker.exception.UserNotFoundException;
import com.mandacarubroker.modules.user.User;
import com.mandacarubroker.modules.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateUserService {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(String userId, User updatedUser) throws Exception {
        // Busca do usuário pelo ID
        User userOptional = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        // Atualiza os dados do usuário
        userOptional.setUsername(updatedUser.getUsername());
        userOptional.setPassword(updatedUser.getPassword());
        userOptional.setEmail(updatedUser.getEmail());
        userOptional.setFirstName(updatedUser.getFirstName());
        userOptional.setLastName(updatedUser.getLastName());
        userOptional.setBirthDate(updatedUser.getBirthDate());
        userOptional.setBalance(updatedUser.getBalance());

        // Salva dados do usuário atualizado
        return userRepository.save(userOptional);
    }
}