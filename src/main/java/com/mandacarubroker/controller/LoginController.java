package com.mandacarubroker.controller;

import com.mandacarubroker.controller.exceptions.StandardError;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.dtos.RequestLoginDTO;
import com.mandacarubroker.dtos.ResponseLoginDTO;
import com.mandacarubroker.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody RequestLoginDTO reqDto, HttpServletRequest req){
        Authentication auth = UsernamePasswordAuthenticationToken.unauthenticated(reqDto.username(),reqDto.password());
        try {
            auth = authenticationManager.authenticate(auth);
            String token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok().body(new ResponseLoginDTO("Successful Authorization", token));
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StandardError(Instant.now(), HttpStatus.UNAUTHORIZED.value(),
                    "Authentication Failed","Bad credentials",req.getRequestURI()));
        }
    }
}
