package com.mandacarubroker.service;

import com.mandacarubroker.domain.Role;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.dtos.RequestUserDTO;
import com.mandacarubroker.dtos.ResponseUserDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String NOT_FOUND_MSG = "User Not Found";
    private final Validator validator;
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
        newUser.setPassword(passwordEncoder.encode(data.password()));
        newUser.setRole(Role.NORMAL);
        return new ResponseUserDTO(userRepository.save(newUser));
    }

    public  void validateRequestUserDTO(RequestUserDTO data) {
        Set<ConstraintViolation<RequestUserDTO>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");
            for (ConstraintViolation<RequestUserDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }

    public ResponseUserDTO updateUser(String id, RequestUserDTO updatedUser) {
        validateRequestUserDTO(updatedUser);
        User updatedUserEntity = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.username());
                    user.setPassword(passwordEncoder.encode(updatedUser.password()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found:"+username));
    }
}
