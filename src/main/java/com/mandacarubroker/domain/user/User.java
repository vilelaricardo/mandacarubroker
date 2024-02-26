package com.mandacarubroker.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Double balance;

    public User(RequestUserDTO dto){
        setUsername(dto.username());
        setPassword(dto.password());
        setEmail(dto.email());
        setFirstName(dto.firstName());
        setLastName(dto.lastName());
        setBirthDate(dto.birthDate());
        setBalance(dto.balance());
    }

}
