package com.mandacarubroker.modules.user.service;

import com.mandacarubroker.exception.UserNotFoundException;
import com.mandacarubroker.modules.user.User;
import com.mandacarubroker.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserWithdrawService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User userWithdraw(String userId, float value) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        user.setBalance(user.getBalance() - value);
        return userRepository.save(user);
    }
}