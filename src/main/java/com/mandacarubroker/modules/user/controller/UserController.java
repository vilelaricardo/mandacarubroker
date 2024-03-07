package com.mandacarubroker.modules.user.controller;

import com.mandacarubroker.exception.UserNotFoundException;
import com.mandacarubroker.modules.user.User;
import com.mandacarubroker.modules.user.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciar usuários.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CreateUserService createUserService;
    @Autowired
    private ReadUserService readUserService;
    @Autowired
    private DeleteUserService deleteUserService;
    @Autowired
    private UpdateUserService updateUserService;
    @Autowired
    private UserDepositService userDepositService;
    @Autowired
    private UserWithdrawService userWithdrawService;


    /**
     * Recupera todos os usuários.
     *
     * @return lista de todos os usuários
     */
    @GetMapping("/")
    public ResponseEntity<Object> findAllUsers() {
        return ResponseEntity.ok(readUserService.findAll());
    }


    /**
     * Recupera um usuário pelo ID.
     *
     * @param userId ID do usuário
     * @return Usuário encontrado
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Object> findUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(readUserService.findById(userId));
    }


    /**
     * Cria um novo usuário.
     *
     * @param user dados do novo usuário.
     * @return Usuário criada.
     */
    @PostMapping("/") // Vínculo do método "createUser" com o método "HTTP - POST"
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        try {
            var result = this.createUserService.execute(user);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Atualiza um usuário.
     *
     * @param userId      ID do usuário
     * @param updatedUser Dados atualizados do usuário
     * @return Usuário atualizado
     * @throws UserNotFoundException
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") String userId,
                                             @Valid @RequestBody User updatedUser) throws Exception {
        try {
            User userUpdated = updateUserService.updateUser(userId, updatedUser);
            return ResponseEntity.ok().body(userUpdated);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Deleta um usuário.
     *
     * @param userId identificador do usuário
     * @return Usuário deletado.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId) throws Exception {
        try {
            this.deleteUserService.deleteUser(userId);
            return ResponseEntity.ok().body("Usuário deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Realiza o depósito de um valor no saldo do usuário.
     *
     * @param userId identificador do usuário
     * @param value valor a ser creditado
     * @return Saldo atualizado.
     */
    @PutMapping("/{userId}/deposit")
    public ResponseEntity<Object> deposit(@PathVariable("userId") String userId, @RequestBody float value) throws Exception {
        try {
            User user = userDepositService.userDeposit(userId, value);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Realiza o saque de um valor no saldo do usuário.
     *
     * @param userId identificador do usuário
     * @param value valor a ser debitado
     * @return Saldo atualizado.
     */
    @PutMapping("/{userId}/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable("userId") String userId, @RequestBody float value) throws Exception {
        try {
            User user = userWithdrawService.userWithdraw(userId, value);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}