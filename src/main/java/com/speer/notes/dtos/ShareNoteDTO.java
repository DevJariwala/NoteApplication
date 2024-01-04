package com.speer.notes.dtos;

import com.speer.notes.constants.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareNoteDTO {

    @NotBlank(message = "Email is required.")
    @Size(max = 100, message = "Email can not be longer than 100 characters.")
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email format.")
    private String email;

}
