package com.mandacarubroker.modules.user.service;

import com.mandacarubroker.modules.user.User;
import com.mandacarubroker.modules.user.UserRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadUserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}