package com.mandacarubroker.service;

import com.mandacarubroker.domain.user.RequestUserDTO;
import com.mandacarubroker.domain.user.ResponseUserDTO;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.validations.CustomValidator;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private static final String NOT_FOUND_MSG = "User Not Found";
    private static final CustomValidator customValidator = new CustomValidator();

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

//    public static void validateRequestUserDTO(RequestUserDTO data) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Set<ConstraintViolation<RequestUserDTO>> violations = factory.getValidator().validate(data);
//        if (!violations.isEmpty()) {
//            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");
//            for (ConstraintViolation<RequestUserDTO> violation : violations) {
//                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
//            }
//            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
//            factory.close();
//            throw new ConstraintViolationException(errorMessage.toString(), violations);
//        }
//        factory.close();
//    }

    public static void validateRequestUserDTO(RequestUserDTO data) {
        Validator validator = customValidator.getValidator(); // Use injected CustomValidator
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