package com.mandacarubroker.service;

import com.mandacarubroker.dtos.RequestUserDTO;
import com.mandacarubroker.dtos.ResponseUserDTO;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder mockBcrypt;
    @Mock
    private Validator validator;
    @InjectMocks
    private UserService userService;
    private User user;
    private RequestUserDTO validRequestUserDto;
    private String existingId,nonExistingId;
    
    @BeforeEach
    void setup() throws IOException, SAXException {
        user = new User("peaga","password","peaga@mail.com"
                ,"Paulo","Herbert", LocalDate.parse("2004-04-05"),500.0);
        user.setId("1");
        validRequestUserDto = new RequestUserDTO(user.getUsername(),"password"
                ,user.getEmail(), user.getFirstName(), user.getLastName(),user.getBirthDate(),user.getBalance());
        existingId = "1";
        nonExistingId = "null";
        when(userRepository.save(any())).thenReturn(user);
        when(mockBcrypt.encode(anyString())).thenReturn("password");
        when(validator.validate(any())).thenReturn(Set.of());
    }

    @Test
    void findAllShouldReturnAResponseUserDTOList(){
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<ResponseUserDTO> users = userService.getAllUsers();
        verify(userRepository).findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    void getUserByIdShouldReturnAResponseUserDTOWhenExistingId(){
        when(userRepository.findById(existingId)).thenReturn(Optional.of(user));

        ResponseUserDTO created = userService.getUserById(existingId);

        verify(userRepository,only()).findById(any());
        
        assertNotNull(created);
        assertEquals(user.getId(),created.id());
        assertEquals(user.getUsername(),created.username());
        assertEquals(user.getEmail(),created.email());
        assertEquals(user.getFirstName(),created.firstName());
        assertEquals(user.getLastName(),created.lastName());
        assertEquals(user.getBalance(),created.balance());
        assertEquals(user.getBirthDate(),created.birthDate());
    }

    @Test
    void getUserByIdShouldThrowEntityNotFoundWhenNonExistingId(){
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->userService.getUserById(nonExistingId));
        verify(userRepository,only()).findById(any());
    }

    @Test
    void createShouldReturnAResponseUserDTO(){
        ResponseUserDTO created = userService.createUser(validRequestUserDto);
        assertNotNull(created);
        assertNotNull(created.id());
        assertEquals(validRequestUserDto.username(),created.username());
        assertEquals(validRequestUserDto.email(),created.email());
        assertEquals(validRequestUserDto.firstName(),created.firstName());
        assertEquals(validRequestUserDto.lastName(),created.lastName());
        assertEquals(validRequestUserDto.balance(),created.balance());
        assertEquals(validRequestUserDto.birthDate(),created.birthDate());

    }

    @Test
    void updateShouldReturnAResponseUserDTOWhenExistingId(){
        when(userRepository.findById(existingId)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any())).thenReturn(user);
        ResponseUserDTO updatedUser = userService.updateUser(existingId,validRequestUserDto);
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.id());
        assertEquals(validRequestUserDto.username(),updatedUser.username());
        assertEquals(validRequestUserDto.email(),updatedUser.email());
        assertEquals(validRequestUserDto.firstName(),updatedUser.firstName());
        assertEquals(validRequestUserDto.lastName(),updatedUser.lastName());
        assertEquals(validRequestUserDto.balance(),updatedUser.balance());
        assertEquals(validRequestUserDto.birthDate(),updatedUser.birthDate());
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionWhenNonExistingIdAndValidRequestBody(){
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class,()->userService.updateUser(nonExistingId,validRequestUserDto));
    }

    @Test
    void deleteShouldDoNothing(){
        when(userRepository.existsById(existingId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(anyString());

        assertDoesNotThrow(()->userService.deleteUser(existingId));
    }

    @Test
    void deleteShouldThrowEntityNotFoundExceptionWhenNonExistingId(){
        doReturn(false).when(userRepository).existsById(nonExistingId);
        assertThrows(EntityNotFoundException.class,()->userService.deleteUser(existingId));
        verify(userRepository,never()).deleteById(anyString());
    }

}
