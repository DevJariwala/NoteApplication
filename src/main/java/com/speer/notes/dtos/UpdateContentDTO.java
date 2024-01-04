package com.speer.notes.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContentDTO {

    @NotBlank(message = "Content required")
    private String content;

}
