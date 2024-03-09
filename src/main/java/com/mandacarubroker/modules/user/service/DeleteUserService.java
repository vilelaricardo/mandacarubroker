package com.mandacarubroker.modules.user.service;

import com.mandacarubroker.exception.UserNotFoundException;
import com.mandacarubroker.modules.stockTransaction.StockTransactionRepository;
import com.mandacarubroker.modules.user.User;
import com.mandacarubroker.modules.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Anotação para serviço e transações
@Service
@Transactional
public class DeleteUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public void deleteUser(String userId) throws Exception {
        // Busca do usuário pelo ID
        User userOptional = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        // Deleta transações associadas ao usuário
        stockTransactionRepository.deleteByUserId(userId);

        // Deleta o usuário
        userRepository.delete(userOptional);
    }
}
