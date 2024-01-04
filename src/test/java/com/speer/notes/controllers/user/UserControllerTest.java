package com.speer.notes.controllers.user;

import com.speer.notes.config.JwtHelper;
import com.speer.notes.dtos.SuccessResponseDTO;
import com.speer.notes.dtos.UserDTO;
import com.speer.notes.dtos.UserResponseDTO;
import com.speer.notes.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtHelper jwtHelper;

    @InjectMocks
    private UserController userController;

    @Test
    void signUp() {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("dev@gmail.com");
        userDTO.setPassword("Dev@1234");

        doNothing().when(userService).signUp(any(UserDTO.class));

        ResponseEntity<SuccessResponseDTO> responseEntity = userController.signUp(userDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    void testSignUpFailure() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("dev@gmail.com");

        doThrow(new RuntimeException("Failed to sign up user")).when(userService).signUp(any(UserDTO.class));

        assertThrows(RuntimeException.class, () -> userController.signUp(userDTO));
    }

    @Test
    void testLogin() {
        UserDTO userDTO = new UserDTO();  // Add necessary data for the userDTO
        userDTO.setEmail("dev@gmail.com");
        userDTO.setPassword("Dev@1234");

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtHelper.generateToken(any(UserDetails.class))).thenReturn("mockedToken");
        when(userService.login(any(UserDTO.class), anyString())).thenReturn(mock(UserResponseDTO.class));

        ResponseEntity<UserResponseDTO> responseEntity = userController.login(userDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testLoginFailure() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("dev@gmail.com");
        userDTO.setPassword("Dev@123");

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtHelper.generateToken(any(UserDetails.class))).thenReturn("mockedToken");
        when(userService.login(any(UserDTO.class), anyString())).thenThrow(new RuntimeException("Login failed"));

        assertThrows(RuntimeException.class, () -> userController.login(userDTO));

    }
}