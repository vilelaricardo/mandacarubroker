package com.mandacarubroker.modules.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity(name = "users")
@Table(name = "users")
@Data // Se encarrega de gerar GETTERS e SETTERS
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @NotBlank(message = "Campo 'username' não pode estar em branco")
    @Pattern(regexp = "\\S+", message = "Campo 'username' não deve conter espaços")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Campo 'password' não pode estar em branco")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Campo 'email' não pode estar em branco")
    @Email(message = "Endereço de email inválido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Campo 'first name' não pode estar em branco")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Campo 'last name' não pode estar em branco")
    private String lastName;

    @Column(nullable = false)
    @NotNull(message = "Campo 'birth date' não pode ser nulo")
    @Past(message = "Data de nascimento deve ser uma data passada")
    private LocalDate birthDate;

    @Column(nullable = false)
    private float balance;
}