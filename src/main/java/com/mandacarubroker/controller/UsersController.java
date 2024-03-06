package com.mandacarubroker.controller;

import com.mandacarubroker.domain.users.BalanceDataTransferObject;
import com.mandacarubroker.domain.users.LoginDataTransferObject;
import com.mandacarubroker.domain.users.RegisterDataTransferObject;
import com.mandacarubroker.domain.users.ResponseDataTransferObject;
import com.mandacarubroker.domain.users.Users;
import com.mandacarubroker.repository.UsersRepository;
import com.mandacarubroker.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UsersController {
  private final UsersRepository usersRepository;
  private final UsersService usersService;

  @SuppressWarnings("checkstyle:MissingJavadocType")
  public UsersController(
      UsersRepository usersRepository,
      UsersService usersService
  ) {
    this.usersRepository = usersRepository;
    this.usersService = usersService;
  }

  @SuppressWarnings("checkstyle:MissingJavadocType")
  @PostMapping("/login")
  public ResponseEntity<ResponseDataTransferObject> login(
      @RequestBody @Valid LoginDataTransferObject data
  ) {
    Users user = this.usersService.get(data);

    if (user == null) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDataTransferObject(
              false,
              "Unregistered user.",
              null
          ));
    }

    return ResponseEntity.ok(new ResponseDataTransferObject(true, null, user));
  }
  @SuppressWarnings("checkstyle:MissingJavadocType")
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ResponseDataTransferObject> handleBadCredentialsException(
      BadCredentialsException ex
  ) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(new ResponseDataTransferObject(false, ex.getMessage(), null));
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseStatus> register(
      @RequestBody @Valid RegisterDataTransferObject data
  ) {
    if (this.usersRepository.findUsersByUsername(data.username()) != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    usersService.create(data);

    return ResponseEntity.ok().build();
  }
  @PutMapping("/deposit/{id}")
  public ResponseEntity<ResponseDataTransferObject> deposit(
    @PathVariable String id,
    @RequestBody @Valid BalanceDataTransferObject data
  ) {
    var user = this.usersRepository.findUsersById(id);

    if(user == null) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDataTransferObject(
              false,
              "There was an error",
              null
          ));
    }

    if (data.value() < 0) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDataTransferObject(
              false,
              "Invalid value",
              null
          ));
    }

    var response = this.usersService.deposit(id, data.value(), user);
    if (response.isEmpty()) {
      return ResponseEntity
          .status(500)
          .body(new ResponseDataTransferObject(
              false,
              "There was an error",
              null
          ));
    }

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            null,
            response
        ));
  }

  @PutMapping("/withdraw/{id}")
  public ResponseEntity<ResponseDataTransferObject> withdraw(
      @PathVariable String id,
      @RequestBody @Valid BalanceDataTransferObject data
  ) {
    var user = this.usersRepository.findUsersById(id);

    if(user == null) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDataTransferObject(
              false,
              "There was an error",
              null
          ));
    }

    if (data.value() < 0) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDataTransferObject(
              false,
              "Invalid value",
              null
          ));
    }

    var response = this.usersService.withdraw(id, data.value(), user);
    if (response.isEmpty()) {
      return ResponseEntity
          .status(500)
          .body(new ResponseDataTransferObject(
              false,
              "There was an error",
              null
          ));
    }

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            null,
            response
        ));
  }


  @PutMapping("/{id}")
  public Users update(@PathVariable String id, @RequestBody @Valid RegisterDataTransferObject data) {
    return usersService.update(id, data).orElse(null);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    usersService.delete(id);
  }
}
