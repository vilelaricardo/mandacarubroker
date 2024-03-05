package com.mandacarubroker.service;

import com.mandacarubroker.domain.users.LoginDataTransferObject;
import com.mandacarubroker.domain.users.RegisterDataTransferObject;
import com.mandacarubroker.domain.users.Users;
import com.mandacarubroker.repository.UsersRepository;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@SuppressWarnings("checkstyle:MissingJavadocType")
@Service
public class UsersService {
  private final UsersRepository usersRepository;

  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  /**
   * The method create a new user.
   *
   * @param data User data
   *
   **/
  public void create(RegisterDataTransferObject data) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    Users newUser = new Users(data, encryptedPassword);

    this.usersRepository.save(newUser);
  }

  /**
   * The method get a user.
   *
   * @param data user login data
   *
   **/
  public Users get(LoginDataTransferObject data) throws BadCredentialsException {
    Users user = this.usersRepository.findUsersByUsername(data.username());
    if (user == null) {
      return null;
    }

    boolean isValidPassword = new BCryptPasswordEncoder()
      .matches(data.password(), user.getPassword());
    if (isValidPassword) {
      return user;
    } else {
      throw new BadCredentialsException("Invalid Credentials");
    }

  }


  /**
   * The method update a user.
   *
   * @param id User id
   * @param updatedData user data
   *
   **/
  public Optional<Users> update(String id, RegisterDataTransferObject updatedData) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(updatedData.password());

    return this.usersRepository.findById(id)
        .map(user -> {
          user.setUsername(updatedData.username());
          user.setPassword(encryptedPassword);
          user.setEmail(updatedData.email());
          user.setFirstName(updatedData.firstName());
          user.setLastName(updatedData.lastName());
          user.setBirthData(updatedData.birthData());
          user.setBalance(updatedData.balance());

          return this.usersRepository.save(user);
        });
  }

  public Optional<Users> withdraw(String id, Double value, Users data) {
    Double newBalance = data.getBalance() + value;

    return this.usersRepository.findById(id)
        .map(user -> {
          user.setUsername(data.getUsername());
          user.setPassword(data.getPassword());
          user.setEmail(data.getEmail());
          user.setFirstName(data.getFirstName());
          user.setLastName(data.getLastName());
          user.setBirthData(data.getBirthData());
          user.setBalance(newBalance);

          return this.usersRepository.save(user);
        });
  }

  public Optional<Users> deposit(String id, Double value, Users data) {
    Double newBalance = data.getBalance() - value;

    return this.usersRepository.findById(id)
        .map(user -> {
          user.setUsername(data.getUsername());
          user.setPassword(data.getPassword());
          user.setEmail(data.getEmail());
          user.setFirstName(data.getFirstName());
          user.setLastName(data.getLastName());
          user.setBirthData(data.getBirthData());
          user.setBalance(newBalance);

          return this.usersRepository.save(user);
        });
  }

  /**
   * The method delete a user.
   *
   * @param id user id
   *
   **/
  public void delete(String id) {
    this.usersRepository.deleteById(id);
  }
}
