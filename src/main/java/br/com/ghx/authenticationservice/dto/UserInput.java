package br.com.ghx.authenticationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInput(
        @NotBlank
        String name,
        @NotBlank @Email
        String username,
        @NotBlank
        String password,
        Boolean isAdmin) {
}
