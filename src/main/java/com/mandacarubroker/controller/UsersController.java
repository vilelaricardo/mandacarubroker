package com.mandacarubroker.controller;

import com.mandacarubroker.domain.users.BalanceDataTransferObject;
import com.mandacarubroker.domain.users.LoginDataTransferObject;
import com.mandacarubroker.domain.users.RegisterDataTransferObject;
import com.mandacarubroker.domain.users.ResponseDataTransferObject;
import com.mandacarubroker.domain.users.Users;
import com.mandacarubroker.repository.UsersRepository;
import com.mandacarubroker.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

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
  @Operation(summary = "Login a user", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
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
  @Operation(summary = "Register a user", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
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


  @Operation(summary = "Update user data", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @PutMapping("/{id}")
  public Users update(@PathVariable String id, @RequestBody @Valid RegisterDataTransferObject data) {
    return usersService.update(id, data).orElse(null);
  }
  @Operation(summary = "Delete a user", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    usersService.delete(id);
  }
}
