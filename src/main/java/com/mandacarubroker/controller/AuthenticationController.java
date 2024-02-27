package com.mandacarubroker.controller;

import com.mandacarubroker.domain.authuser.AuthUser;
import com.mandacarubroker.domain.authuser.AuthenticationDataTransferObject;
import com.mandacarubroker.domain.authuser.LoginResponseDataTransferObject;
import com.mandacarubroker.domain.authuser.RegisterDataTransferObject;
import com.mandacarubroker.infra.security.TokenService;
import com.mandacarubroker.repository.AuthUserRepository;
import com.mandacarubroker.service.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("checkstyle:MissingJavadocType")
@RestController
@RequestMapping("auth")
public class AuthenticationController {
  private final AuthUserRepository authUserRepository;
  private final AuthorizationService authorizationService;
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public AuthenticationController(
      AuthUserRepository authUserRepository,
      AuthorizationService authorizationService,
      AuthenticationManager authenticationManager,
      TokenService tokenService
  ) {
    this.authUserRepository = authUserRepository;
    this.authorizationService = authorizationService;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Operation(summary = "Log in to the system", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "login successful"),
      @ApiResponse(responseCode = "400", description = "Invalid credentials"),
      @ApiResponse(responseCode = "401", description = "No register"),
  })
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDataTransferObject> login(
      @RequestBody @Valid AuthenticationDataTransferObject data
  ) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
    var auth = authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateSystemToken((AuthUser) auth.getPrincipal());

    return ResponseEntity.ok(new LoginResponseDataTransferObject(token));
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleUserNotFoundException(AuthenticationException ex) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Credenciais inválidas.");
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Credenciais inválidas. Por favor, verifique seu nome de usuário e senha.");
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Operation(summary = "Register in the system", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Register successful"),
      @ApiResponse(responseCode = "409", description = "Already registered user")
  })
  @PostMapping("/register")
  public ResponseEntity<ResponseStatus> register(@RequestBody @Valid RegisterDataTransferObject data) {
    if (this.authUserRepository.findByLogin(data.login()) != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    authorizationService.create(data);

    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Delete registration in the system", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delete successful"),
  })
  @DeleteMapping("/{id}")
  public void deleteAuthUser(@PathVariable String id) {
    authorizationService.delete(id);
  }
}
