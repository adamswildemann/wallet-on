package br.com.walleton.api.v1.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotBlank @Size(max = 80) String firstName,

                          @NotBlank @Size(max = 80) String lastName,

                          @NotBlank @Email @Size(max = 60) String email,

                          @NotBlank
                          @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                                  message = "CPF deve estar no formato 000.000.000-00")
                          String cpf,

                          @NotBlank @Size(max = 20) String phoneNumber

) {
}
