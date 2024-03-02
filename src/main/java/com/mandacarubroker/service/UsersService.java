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
  public Optional<Users> update(String id, Users updatedData) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(updatedData.getPassword());

    return this.usersRepository.findById(id)
        .map(user -> {
          user.setUsername(updatedData.getUsername());
          user.setPassword(encryptedPassword);
          user.setEmail(updatedData.getEmail());
          user.setFirst_name(updatedData.getFirst_name());
          user.setLast_name(updatedData.getLast_name());
          user.setBirth_data(updatedData.getBirth_data());
          user.setBalance(updatedData.getBalance());

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
