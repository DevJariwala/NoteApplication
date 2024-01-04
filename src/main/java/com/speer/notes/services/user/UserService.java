package com.speer.notes.services.user;

import com.speer.notes.dtos.UserDTO;
import com.speer.notes.dtos.UserResponseDTO;
import com.speer.notes.models.User;

public interface UserService {

    void signUp(UserDTO userDTO);

    UserResponseDTO login(UserDTO userDTO, String token);

    User getUserByEmail(String email);

}
