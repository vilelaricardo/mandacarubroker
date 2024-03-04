package com.mandacarubroker.service;

import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.dto.RequestUserDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Service;

/**
 * Sistema de serviços para os usuários.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Recupera todos os usuários.
     * @return lista de todos os usuários
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    /**
     * Recupera um usuário pelo ID.
     * @param id ID do usuário
     * @return usuário encontrado, ou Optional.empty() se não encontrado
     */
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Cria um novo usuário.
     *
     * @param data DTO contendo os dados do novo usuário
     * @return usuário criado
     * @throws ConstraintViolationException se a validação dos dados falhar
     */
    public User createUser(RequestUserDTO data) {
        User newUser = new User(data);
        validateRequestUserDTO(data);
        return userRepository.save(newUser);
    }


    /**
     * Atualiza um usuário.
     * @param id identificador do usuário
     * @param updatedUser dados atualizados da ação
     * @return usuário atualizado, ou Optional.empty() se não encontrado
     */
    public Optional<User> updateUser(String id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setEmail(updatedUser.getEmail());
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setBirthDate(updatedUser.getBirthDate());
                    user.setBalance(updatedUser.getBalance());

                    return userRepository.save(user);
                });
    }

    /**
     * Deleta um usuário.
     * @param id identificador do usuário
     */
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    /**
     * Valida os dados de um DTO de requisição de usuário.
     * @param data DTO contendo os dados do usuário
     * @throws ConstraintViolationException se a validação dos dados falhar
     */
    public static void validateRequestUserDTO(RequestUserDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
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

    /**
     * Valida e cria um novo usuário.
     * @param data DTO contendo os dados do novo usuário
     * @throws ConstraintViolationException se a validação dos dados falhar
     */
    public void validateAndCreateUser(RequestUserDTO data) {
        validateRequestUserDTO(data);

        User newUser = new User(data);
        userRepository.save(newUser);
    }
}