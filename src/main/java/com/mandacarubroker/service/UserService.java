package com.mandacarubroker.service;

import com.mandacarubroker.domain.user.RequestUserDTO;
import com.mandacarubroker.domain.user.ResponseUserDTO;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private static final String NOT_FOUND_MSG = "User Not Found";
    
    public List<ResponseUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(ResponseUserDTO::new).toList();
    }

    public ResponseUserDTO getUserById(String id) {
        return userRepository.findById(id).map(ResponseUserDTO::new)
                .orElseThrow(()->new EntityNotFoundException(NOT_FOUND_MSG));
    }

    public ResponseUserDTO createUser(RequestUserDTO data) {
        validateRequestUserDTO(data);
        User newUser = new User(data);
        return new ResponseUserDTO(userRepository.save(newUser));
    }

    private void validateRequestUserDTO(RequestUserDTO data) {
        //validation method
    }

    public ResponseUserDTO updateUser(String id, RequestUserDTO updatedUser) {
        validateRequestUserDTO(updatedUser);
        User updatedUserEntity = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.username());
                    user.setPassword(updatedUser.password());
                    user.setEmail(updatedUser.email());
                    user.setFirstName(updatedUser.firstName());
                    user.setLastName(updatedUser.lastName());
                    user.setBirthDate(updatedUser.birthDate());
                    user.setBalance(updatedUser.balance());
                     return userRepository.save(user);
                }).orElseThrow(()->new EntityNotFoundException(NOT_FOUND_MSG));
        return new ResponseUserDTO(updatedUserEntity);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)){
            throw new EntityNotFoundException(NOT_FOUND_MSG);
        }
        userRepository.deleteById(id);
    }
}
