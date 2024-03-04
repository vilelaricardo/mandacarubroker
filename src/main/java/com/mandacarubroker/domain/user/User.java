package com.mandacarubroker.domain.user;

import com.mandacarubroker.dto.RequestUserDTO;
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

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private double balance;

    // ---------- GETTERS ---------- //
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public double getBalance() { return balance; }

    // ---------- SETTERS ---------- //

    public void setId(String id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setBalance(double balance) { this.balance = balance; }


    /**
     * Construtor que cria uma instância de User a partir de um RequestCreateUserDTO.
     *
     * @param requestUserDTO o DTO contendo os dados do novo usuário.
     */
    public User(RequestUserDTO requestUserDTO) {
        this.username = requestUserDTO.username();
        this.password  = requestUserDTO.password();
        this.email  = requestUserDTO.email();
        this.firstName  = requestUserDTO.firstName();
        this.lastName  = requestUserDTO.lastName();
        this.birthDate  = requestUserDTO.birthDate();
    }
}