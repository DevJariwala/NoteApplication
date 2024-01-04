package com.speer.notes.dtos;

import com.speer.notes.constants.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Email is required.")
    @Size(max = 100, message = "Email can not be longer than 100 characters.")
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(max = 16, message = "Password can not be longer than 16 characters.")
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Invalid password format.")
    private String password;

}
