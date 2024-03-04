package com.mandacarubroker.controller;

import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.dto.RequestUserDTO;
import com.mandacarubroker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciar usuário.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService; // Vínculo CONTROLLER com SERVICES

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Método para RECUPERAR TODOS os usuários existentes.
     *
     * @return Lista de usuários.
     */
    @GetMapping
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    /**
     * Recupera um usuário pelo seu ID.
     * @param id ID da usuário.
     * @return Usuário com o ID especificado.
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id).orElse(null);
    }

    /**
     * Cria um novo usuário.
     * @param data DTO com os dados do novo usuário.
     * @return Usuário criada.
     */
    @PostMapping // Vínculo do método "createUser" com o método "HTTP - POST"
    public ResponseEntity<User> createUser(@Valid @RequestBody RequestUserDTO data) {
        User createdUser = userService.createUser(data);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Atualiza um usuário.
     * @param id ID do usuário.
     * @param updatedUser Usuário com os dados atualizados.
     * @return Usuário atualizado.
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser).orElse(null);
    }

    /**
     * Exclui um usuário.
     * @param id ID do usuário.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuário " + id + " deletado com sucesso!");
    }
}