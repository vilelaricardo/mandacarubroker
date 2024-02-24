package com.mandacarubroker.controller;

import com.mandacarubroker.domain.user.RequestUserDTO;
import com.mandacarubroker.domain.user.ResponseUserDTO;
import com.mandacarubroker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody RequestUserDTO data, HttpServletRequest http) {
        ResponseUserDTO createdUser = userService.createUser(data);
        URI uri = UriComponentsBuilder.fromUriString(http.getRequestURI()).path("/{id}").buildAndExpand(createdUser.id()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> updateUser(@PathVariable String id, @RequestBody RequestUserDTO updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
}
