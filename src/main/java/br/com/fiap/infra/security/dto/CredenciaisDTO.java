package br.com.fiap.infra.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CredenciaisDTO(
        @NotNull @Email String username,
        @NotNull @NotBlank String password
) {

}
