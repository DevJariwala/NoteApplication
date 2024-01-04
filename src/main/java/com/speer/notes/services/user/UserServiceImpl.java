package com.speer.notes.services.user;

import com.speer.notes.dtos.UserDTO;
import com.speer.notes.dtos.UserResponseDTO;
import com.speer.notes.exception.BadRequestException;
import com.speer.notes.exception.ResourceNotFoundException;
import com.speer.notes.exception.UnauthorizedActionException;
import com.speer.notes.exception.UserAlreadyExistException;
import com.speer.notes.models.User;
import com.speer.notes.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signUp(UserDTO userDTO) {
        if (doesUserWithEmailExists(userDTO.getEmail())) {
            throw new UserAlreadyExistException("User with this email already exist.");
        }
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        userRepository.save(user);
    }

    @Override
    public UserResponseDTO login(UserDTO userDTO, String token) {

        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UnauthorizedActionException("Invalid email or password"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid Password");
        }

        return converUserToUserResponseDTO(user, token);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for the email : " + email));
    }

    private boolean doesUserWithEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private UserResponseDTO converUserToUserResponseDTO(User user, String token) {
        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .token(token)
                .build();
        return userResponseDTO;
    }

}
