package com.speer.notes.controllers.user;

import com.speer.notes.config.JwtHelper;
import com.speer.notes.controllers.RateLimited;
import com.speer.notes.dtos.SuccessResponseDTO;
import com.speer.notes.dtos.UserDTO;
import com.speer.notes.dtos.UserResponseDTO;
import com.speer.notes.services.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RateLimited(value = 10)
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponseDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        userService.signUp(userDTO);
        SuccessResponseDTO successResponseDTO = SuccessResponseDTO.builder()
                .message("User is created, please Log in !!")
                .build();
        return new ResponseEntity<>(successResponseDTO, HttpStatus.CREATED);
    }

    @RateLimited(value = 10)
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserDTO userDTO) {
        doAuthenticate(userDTO.getEmail(), userDTO.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getEmail());
        String token = jwtHelper.generateToken(userDetails);

        UserResponseDTO userResponseDTO = userService.login(userDTO, token);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

}
